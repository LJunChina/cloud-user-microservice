package com.cloud.user.microservice.service;

import com.cloud.user.microservice.dto.BaseRespDTO;
import com.cloud.user.microservice.model.TokenInfo;

/**
 * token服务
 *
 * @author Jon_China
 * @create 2017/11/11
 */
public interface TokenService {

    String addTokenInfo(TokenInfo tokenInfo);

    boolean deleteTokenInfo(String tokenId);

    BaseRespDTO refreshTokenInfo(String tokenId);

}
