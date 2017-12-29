package com.cloud.user.microservice.dao;

import com.cloud.user.microservice.model.RoleInfo;
import org.springframework.stereotype.Repository;

@Repository(value = "userInfoDao")
public interface IRoleInfoDao {
    int addRoleInfo(RoleInfo roleInfo);
}
