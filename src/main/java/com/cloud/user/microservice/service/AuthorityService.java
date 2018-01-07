package com.cloud.user.microservice.service;

import com.cloud.user.microservice.dto.requestDTO.AuthorityReqDTO;
import com.cloud.user.microservice.dto.responseDTO.BaseRespDTO;
import com.cloud.user.microservice.dto.responseDTO.MenuRespDTO;


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

    /**
     * 系统权限信息分页查询
     * @param request
     * @return
     */
    BaseRespDTO getAllAuthoritiesByPage(AuthorityReqDTO request);

    /**
     * 角色权限分配
     * @param roleId
     * @param authIds
     * @return
     */
    BaseRespDTO allocationAuth(String roleId, String authIds);
}
