package com.cloud.user.microservice.model.vo;

import com.cloud.user.microservice.model.User;

/**
 * 用户VO
 *
 * @author Jon_China
 * @create 2017/11/12
 */
public class UserVO extends User {
    private static final long serialVersionUID = -2581494209891247630L;

    private int pageIndex;

    private int pageSize;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


}
