package com.cloud.user.microservice.service;

import com.cloud.user.microservice.dto.BaseRespDTO;

public interface RoleInfoService {
    /**
     * 添加角色信息
     * @param roleName
     * @return
     */
    BaseRespDTO saveRoleInfo(String roleName);
}
