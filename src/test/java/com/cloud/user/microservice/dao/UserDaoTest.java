package com.cloud.user.microservice.dao;

import com.cloud.user.microservice.UserMicroserviceApplicationTests;
import com.cloud.user.microservice.model.User;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;


public class UserDaoTest extends UserMicroserviceApplicationTests {

    @Autowired
    private IUserDao userDao;

    @Test
    public void testSaveUser(){
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName("test");
        user.setPassword("123456");
        Assert.assertEquals(1,this.userDao.saveUser(user));
    }

    @Test
    public void testGetUserInfo(){
        User user = new User();
        user.setId("342dc4fd-ddeb-42ad-87b8-a53ab1ab45ea");
        user.setName("test");
        Assert.assertNotNull(this.userDao.getUserInfo(user));
    }

    @Test
    public void testUpdateUserById(){
        User user = new User();
        user.setId("342dc4fd-ddeb-42ad-87b8-a53ab1ab45ea");
        user = this.userDao.getUserInfo(user);
        user.setUserName("JUnit");
        user.setEmail("3131@qq.com");
        Assert.assertEquals(1,this.userDao.updateUserById(user));
    }

    @Test
    public void testGetUserListByPage(){
        Page<User> page = PageHelper.startPage(1,10).doSelectPage(() -> this.userDao.getUserListByPage(null));
        Assert.assertNotNull(page.getResult());
    }
}
