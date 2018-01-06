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

    private String userId;

    private List<String> roleIds;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }
}
