package com.cloud.user.microservice.dto;

import com.cloud.user.microservice.enums.ResultCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
/**
 * 基本返回报文对象
 *
 * @author Jon_China
 * @create 2017/11/6
 */
public class BaseRespDTO implements Serializable {
    private static final long serialVersionUID = -7964635136197468217L;

    private Serializable data;

    private String code;

    private String message;


    public BaseRespDTO() {
        this(ResultCode.OK);
    }

    public BaseRespDTO(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public BaseRespDTO(ResultCode resultCode){
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    public Serializable getData() {
        return data;
    }

    public void setData(Serializable data) {
        this.data = data;
    }

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

    @Override
    public String toString(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return StringUtils.EMPTY;
        }
    }
}
