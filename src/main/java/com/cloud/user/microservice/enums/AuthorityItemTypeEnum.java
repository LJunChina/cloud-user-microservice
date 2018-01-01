package com.cloud.user.microservice.enums;

/**
 * 权限类型枚举
 *
 * @author Jon_China
 * @create 2018/1/1
 */
public enum  AuthorityItemTypeEnum {

    MENU_TYPE("1","菜单权限"),
    OPERATION_TYPE("0","操作权限");

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    AuthorityItemTypeEnum(String code,String message){
        this.code = code;
        this.message = message;
    }
}
