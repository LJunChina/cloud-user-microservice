package com.cloud.user.microservice.model;


import java.io.Serializable;

public class AuthorityRule implements Serializable {


    private static final long serialVersionUID = -4735760305787725832L;

    private String id;
    /**
     * 
     * authority_rule.auth_id
     */
    private String authId;

    /**
     * 
     * authority_rule.role_id
     */
    private String roleId;


    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
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
