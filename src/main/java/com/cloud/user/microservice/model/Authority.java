package com.cloud.user.microservice.model;

import java.io.Serializable;

public class Authority implements Serializable {


    private static final long serialVersionUID = -2266059186608916384L;
    /**
     * 
     * authority.id
     */
    private String Id;

    /**
     * 
     * authority.name
     */
    private String name;

    /**
     * 
     * authority.parentid
     */
    private String parentId;

    /**
     * 
     * authority.sortnum
     */
    private Integer sortNum;

    /**
     * 
     * authority.style
     */
    private String style;

    /**
     * 
     * authority.icon
     */
    private String icon;

    /**
     * 
     * authority.available
     */
    private String available;

    private String appName;

    private Integer deep;

    public Integer getDeep() {
        return deep;
    }

    public void setDeep(Integer deep) {
        this.deep = deep;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
