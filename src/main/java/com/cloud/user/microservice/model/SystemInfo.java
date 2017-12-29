package com.cloud.user.microservice.model;

import java.io.Serializable;

/**
 * 系统信息
 *
 * @author Jon_China
 * @create 2017/12/23
 */
public class SystemInfo implements Serializable {
    private static final long serialVersionUID = 6386100303017554252L;

    private String id;

    private String systemName;

    private String systemChn;

    private String systemHost;

    private String systemContext;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemChn() {
        return systemChn;
    }

    public void setSystemChn(String systemChn) {
        this.systemChn = systemChn;
    }

    public String getSystemHost() {
        return systemHost;
    }

    public void setSystemHost(String systemHost) {
        this.systemHost = systemHost;
    }

    public String getSystemContext() {
        return systemContext;
    }

    public void setSystemContext(String systemContext) {
        this.systemContext = systemContext;
    }
}
