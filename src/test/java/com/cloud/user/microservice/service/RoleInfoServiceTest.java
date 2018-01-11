package com.cloud.user.microservice.service;

import com.cloud.common.dto.BaseRespDTO;
import com.cloud.common.enums.ResultCode;
import com.cloud.user.microservice.UserMicroserviceApplicationTests;
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
