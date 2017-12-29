package com.cloud.user.microservice.model;

import java.io.Serializable;


public class RoleInfo implements Serializable {


    private static final long serialVersionUID = 6552293961016941483L;
    /**
     * 
     * rule_info.id
     */
    private String id;

    /**
     * 
     * rule_info.role_name
     */
    private String roleName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
