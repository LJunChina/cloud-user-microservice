package com.cloud.user.microservice.web;

import com.cloud.user.microservice.dto.BaseRespDTO;
import com.cloud.user.microservice.enums.ResultCode;
import com.cloud.user.microservice.service.TokenService;
import com.cloud.user.microservice.utils.EmptyChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * token服务相关
 *
 * @author Jon_China
 * @create 2017/11/11
 */
@RestController
@RequestMapping(value = "/token")
public class TokenController {

    private static final Logger logger = LoggerFactory.getLogger(TokenController.class);
    @Autowired
    private TokenService tokenService;
    @GetMapping(value = "/check-token/{tokenId}")
    public String checkToken(@PathVariable(value = "tokenId") String tokenId){
        logger.info("The params of checkToken method tokenId is : {}",tokenId);
        if(EmptyChecker.isEmpty(tokenId)){
            return new BaseRespDTO(ResultCode.PARAMS_NOT_FOUND).toString();
        }
        try {
            BaseRespDTO baseRespDTO = this.tokenService.refreshTokenInfo(tokenId);
            String result = baseRespDTO.toString();
            logger.info("result of checkToken is : {}",result);
            return result;
        }catch (Exception e){
            logger.error("exception occurred in checkToken",e);
            return new BaseRespDTO(ResultCode.ERROR).toString();
        }
    }
}
