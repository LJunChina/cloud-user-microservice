package com.cloud.user.microservice.service;

import com.cloud.common.enums.ResultCode;
import com.cloud.user.microservice.UserMicroserviceApplicationTests;
import com.cloud.user.microservice.dto.responseDTO.MenuRespDTO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthorityServiceTest extends UserMicroserviceApplicationTests {

    @Autowired
    private AuthorityService authorityService;

    @Test
    public void getAllMenus() throws Exception {
        MenuRespDTO authorityRespDTO = this.authorityService.getAllMenus("cloud_base","874b0e21-dd87-49ee-b0b9-142cc365618c");
        Assert.assertEquals(ResultCode.OK.getCode(),authorityRespDTO.getCode());
    }

}