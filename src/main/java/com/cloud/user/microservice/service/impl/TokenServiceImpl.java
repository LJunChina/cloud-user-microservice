package com.cloud.user.microservice.service.impl;

import com.cloud.common.dto.BaseRespDTO;
import com.cloud.common.enums.ResultCode;
import com.cloud.common.util.EmptyChecker;
import com.cloud.user.microservice.model.TokenInfo;
import com.cloud.user.microservice.service.TokenService;
import com.cloud.user.microservice.utils.SSOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
/**
 * token服务
 *
 * @author Jon_China
 * @create 2017/11/11
 */
@Service(value = "tokenService")
public class TokenServiceImpl implements TokenService {

    /**
     * 默认失效时间为10分钟
     */
    private static final long TIME_OUT = 600000L;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String addTokenInfo(TokenInfo tokenInfo) {
        tokenInfo.setExpires(System.currentTimeMillis() + TIME_OUT);
        String tokenId = SSOUtil.generatorTokenId();
        redisTemplate.opsForValue().set(tokenId,tokenInfo,TIME_OUT, TimeUnit.MILLISECONDS);
        return tokenId;
    }

    @Override
    public boolean deleteTokenInfo(String tokenId) {
        //查询是否存在
        TokenInfo tokenInfo = (TokenInfo) this.redisTemplate.opsForValue().get(tokenId);
        if (EmptyChecker.isEmpty(tokenInfo)) {
            return true;
        }
        this.redisTemplate.delete(tokenId);
        return true;
    }

    @Override
    public BaseRespDTO refreshTokenInfo(String tokenId) {
        TokenInfo tokenInfo = (TokenInfo) this.redisTemplate.opsForValue().get(tokenId);
        if(EmptyChecker.isEmpty(tokenInfo)){
            return new BaseRespDTO(ResultCode.INVALID_USER);
        }
        //刷新时间
        tokenInfo.setExpires(System.currentTimeMillis() + TIME_OUT);
        this.redisTemplate.opsForValue().set(tokenId,tokenInfo,TIME_OUT,TimeUnit.MILLISECONDS);
        BaseRespDTO respDTO = new BaseRespDTO();
        respDTO.setData(tokenInfo);
        return respDTO;
    }
}
