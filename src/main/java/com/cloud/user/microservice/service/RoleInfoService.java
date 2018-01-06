package com.cloud.user.microservice.service;

import com.cloud.user.microservice.dto.responseDTO.BaseRespDTO;
import com.cloud.user.microservice.dto.requestDTO.RolePageReqDTO;

import java.util.List;

public interface RoleInfoService {
    /**
     * 添加角色信息
     * @param roleName
     * @param roleType
     * @param appId
     * @param describe
     * @return
     */
    BaseRespDTO saveRoleInfo(String roleName,String roleType,String appId,String describe);

    /**
     * 角色信息分页查询
     * @param request
     * @return
     */
    BaseRespDTO getAllRoleInfo(RolePageReqDTO request);

    /**
     * 角色分配用户信息
     * @param userIds
     * @param roleId
     * @return
     */
    BaseRespDTO allocationUsers(String userIds,String roleId);
}
