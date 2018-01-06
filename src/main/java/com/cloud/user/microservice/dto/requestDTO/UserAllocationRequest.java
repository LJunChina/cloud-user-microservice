package com.cloud.user.microservice.dto.requestDTO;

import java.io.Serializable;
import java.util.List;

/**
 * 角色分配用户响应实体
 *
 * @author Jon_China
 * @create 2018/1/6
 */
public class UserAllocationRequest implements Serializable {
    private static final long serialVersionUID = 2512034276632758837L;

    private String roleId;

    private List<String> userIds;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }
}
