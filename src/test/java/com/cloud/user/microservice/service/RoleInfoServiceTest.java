package com.cloud.user.microservice.service;

import com.cloud.user.microservice.UserMicroserviceApplicationTests;
import com.cloud.user.microservice.dto.responseDTO.BaseRespDTO;
import com.cloud.user.microservice.enums.ResultCode;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * RoleInfoServiceTest
 *
 * @author Jon_China
 * @create 2017/11/18
 */
public class RoleInfoServiceTest extends UserMicroserviceApplicationTests {

    @Autowired
    private RoleInfoService roleInfoService;

    @Test
    public void testSaveRoleInfo(){
        BaseRespDTO baseRespDTO = this.roleInfoService.saveRoleInfo("产品经理","432","54235","gfdgdf");
        Assert.assertEquals(ResultCode.OK.getCode(),baseRespDTO.getCode());
    }
}
