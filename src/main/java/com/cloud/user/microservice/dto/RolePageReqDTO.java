package com.cloud.user.microservice.dto;

import java.io.Serializable;

/**
 * 角色分页查询请求
 *
 * @author Jon_China
 * @create 2018/1/1
 */
public class RolePageReqDTO implements Serializable {
    private static final long serialVersionUID = -4317374749351757521L;

    private Integer pageIndex;

    private Integer pageSize;

    private String roleName;

    private String appId;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
