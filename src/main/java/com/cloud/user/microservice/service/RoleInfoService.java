package com.cloud.user.microservice.service;

import com.cloud.common.dto.BaseRespDTO;
import com.cloud.user.microservice.dto.requestDTO.RolePageReqDTO;


public interface RoleInfoService {
    /**
     * 添加角色信息
     * @param roleName
     * @param roleType
     * @param appId
     * @param describe
     * @return
     */
    BaseRespDTO saveRoleInfo(String roleName, String roleType, String appId, String describe);

    /**
     * 角色信息分页查询
     * @param request
     * @return
     */
    BaseRespDTO getAllRoleInfo(RolePageReqDTO request);

    /**
     * 角色分配用户信息
     * @param userId
     * @param roleIds
     * @return
     */
    BaseRespDTO allocationUsers(String userId,String roleIds);

    /**
     * 角色详情查询
     * @param id
     * @return
     */
    BaseRespDTO getRoleInfo(String id);

    /**
     * 更新角色信息
     * @param roleName
     * @param appId
     * @param roleType
     * @param describe
     * @param id
     * @return
     */
    BaseRespDTO updateRoleInfo(String roleName,String appId,String roleType,String describe,String id);

    /**
     * 根据id删除角色信息
     * @param id
     * @return
     */
    BaseRespDTO deleteRoleInfoById(String id);
}
