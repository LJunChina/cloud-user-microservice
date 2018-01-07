package com.cloud.user.microservice.dto.requestDTO;

import java.io.Serializable;
import java.util.List;

/**
 * 角色权限分配
 *
 * @author Jon_China
 * @create 2018/1/7
 */
public class AllocationAuthRequest implements Serializable {
    private static final long serialVersionUID = -2815380863592030455L;

    private String roleId;

    private List<String> authIds;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public List<String> getAuthIds() {
        return authIds;
    }

    public void setAuthIds(List<String> authIds) {
        this.authIds = authIds;
    }
}
