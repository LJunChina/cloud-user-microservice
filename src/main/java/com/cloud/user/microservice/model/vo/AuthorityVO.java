package com.cloud.user.microservice.model.vo;

import com.cloud.user.microservice.model.Authority;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单返回
 *
 * @author Jon_China
 * @create 2017/12/17
 */
public class AuthorityVO implements Serializable {
    private static final long serialVersionUID = -6366052011717780392L;

    private Authority item;

    private List<Authority> child;

    public Authority getItem() {
        return item;
    }

    public void setItem(Authority item) {
        this.item = item;
    }

    public List<Authority> getChild() {
        return child;
    }

    public void setChild(List<Authority> child) {
        this.child = child;
    }
}
