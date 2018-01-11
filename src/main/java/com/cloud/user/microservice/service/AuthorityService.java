package com.cloud.user.microservice.service;

import com.cloud.common.dto.BaseRespDTO;
import com.cloud.user.microservice.dto.requestDTO.AuthorityReqDTO;
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
     * @param userId
     * @return
     */
    MenuRespDTO getAllMenus(String appName,String userId);

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

    /**
     * 查询当前登录用户权限
     * @param userId
     * @param appId
     * @param uri
     * @return
     */
    BaseRespDTO checkUserPrivileges(String userId,String appId,String uri);
}
