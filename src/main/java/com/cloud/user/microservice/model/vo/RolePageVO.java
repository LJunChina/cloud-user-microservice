package com.cloud.user.microservice.model.vo;

import com.cloud.user.microservice.dto.BaseRespDTO;

import java.io.Serializable;

/**
 * 角色信息分页查询返回报文
 *
 * @author Jon_China
 * @create 2018/1/1
 */
public class RolePageVO implements Serializable {
    private static final long serialVersionUID = -6743290999063191092L;

    private String appName;

    private String appChn;

    private String roleType;

    private String id;

    private String roleName;

    private String describe;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppChn() {
        return appChn;
    }

    public void setAppChn(String appChn) {
        this.appChn = appChn;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
