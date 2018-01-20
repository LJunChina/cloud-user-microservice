package com.cloud.user.microservice.dao;

import com.cloud.user.microservice.model.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "userRoleDao")
public interface IUserRoleDao {

    List<UserRole> getRoleIdsByUserId(@Param(value = "userId")String userId);
}
