package com.cloud.user.microservice.service;

import com.cloud.user.microservice.UserMicroserviceApplicationTests;
import com.cloud.user.microservice.dto.MenuRespDTO;
import com.cloud.user.microservice.enums.ResultCode;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthorityServicelTest extends UserMicroserviceApplicationTests {

    @Autowired
    private AuthorityService authorityService;

    @Test
    public void getAllMenus() throws Exception {
        MenuRespDTO authorityRespDTO = this.authorityService.getAllMenus("cloud_mall");
        Assert.assertEquals(ResultCode.OK.getCode(),authorityRespDTO.getCode());
    }

}