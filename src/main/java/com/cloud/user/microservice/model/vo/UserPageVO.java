package com.cloud.user.microservice.model.vo;

import java.io.Serializable;

/**
 * 用户分页查询返回对象
 *
 * @author Jon_China
 * @create 2018/1/1
 */
public class UserPageVO implements Serializable {
    private static final long serialVersionUID = -1812703245570357820L;

    private String id;

    private String userName;

    private String name;

    private String sex;

    private String status;

    private String email;

    private String idCard;

    private String mobile;

    private String appName;

    private String appChn;

    public String getAppChn() {
        return appChn;
    }

    public void setAppChn(String appChn) {
        this.appChn = appChn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
