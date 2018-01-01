package com.cloud.user.microservice.model.vo;

import java.io.Serializable;

/**
 * 查询菜单实体及对应父级菜单
 *
 * @author Jon_China
 * @create 2018/1/1
 */
public class AuthoritiesVO implements Serializable {

    private static final long serialVersionUID = 3122860147822451606L;

    private String Id;

    private String name;

    private String icon;

    private String appName;

    private String itemUri;

    private String parentName;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getItemUri() {
        return itemUri;
    }

    public void setItemUri(String itemUri) {
        this.itemUri = itemUri;
    }
}
