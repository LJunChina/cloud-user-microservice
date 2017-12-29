package com.cloud.user.microservice.service;

import com.cloud.user.microservice.UserMicroserviceApplicationTests;
import com.cloud.user.microservice.dto.BaseRespDTO;
import com.cloud.user.microservice.dto.UserSearchRespDTO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceTest extends UserMicroserviceApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    public void testGetPublicKey() throws Exception{
        Assert.assertNotNull(this.userService.getPublicKey().getData());
    }
    @Test
    public void testUserLogin() throws Exception{
        BaseRespDTO respDTO = this.userService.userLogin("JUnit","123456");
        Assert.assertNotNull(respDTO);
    }
    @Test
    public void testGetUserList(){
        UserSearchRespDTO userVO = new UserSearchRespDTO();
        userVO.setPageNum(1);
        userVO.setPageSize(10);
        Assert.assertNotNull(this.userService.getUserList(userVO).getData());
    }

    @Test
    public void testGetUserInfo(){
        Assert.assertNotNull(this.userService.getUserInfo("342dc4fd-ddeb-42ad-87b8-a53ab1ab45ea"));
    }
}
