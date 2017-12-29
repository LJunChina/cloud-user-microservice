package com.cloud.user.microservice.model;

import java.io.Serializable;

public class UserRole implements Serializable {


    private static final long serialVersionUID = 5412105132820137828L;


    private String id;
    /**
     * 
     * user_role.user_id
     */
    private String userId;

    /**
     * 
     * user_role.role_id
     */
    private String roleId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
