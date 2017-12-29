package com.cloud.user.microservice.dao;

import com.cloud.user.microservice.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "userDao")
public interface IUserDao {
    /**
     * 保存用户信息
     * @param user
     * @return
     */
    int saveUser(User user);

    /**
     * 根据条件查询用户信息
     * @param user
     * @return
     */
    User getUserInfo(User user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int updateUserById(User user);

    /**
     * 分页查询用户信息
     * @param user
     * @return
     */
    List<User> getUserListByPage(User user);
}
