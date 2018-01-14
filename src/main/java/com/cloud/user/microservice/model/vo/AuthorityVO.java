package com.cloud.user.microservice.model.vo;

import com.cloud.user.microservice.model.Authority;

import java.util.List;

/**
 * 菜单返回
 *
 * @author Jon_China
 * @create 2017/12/17
 */
public class AuthorityVO extends Authority {
    private static final long serialVersionUID = -6366052011717780392L;

    private List<Authority> child;

    public List<Authority> getChild() {
        return child;
    }

    public void setChild(List<Authority> child) {
        this.child = child;
    }
}
