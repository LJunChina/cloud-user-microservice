package com.cloud.user.microservice.service;

import com.cloud.user.microservice.dto.AuthorityReqDTO;
import com.cloud.user.microservice.dto.BaseRespDTO;
import com.cloud.user.microservice.dto.MenuRespDTO;

/**
 * Authority
 *
 * @author Jon_China
 * @create 2017/11/18
 */
public interface AuthorityService {
    /**
     * 保存权限信息
     * @return
     */
    BaseRespDTO saveAuthority(AuthorityReqDTO request);

    /**
     * 获取系统所有菜单信息
     * @param appName 系统名称
     * @return
     */
    MenuRespDTO getAllMenus(String appName);
}
