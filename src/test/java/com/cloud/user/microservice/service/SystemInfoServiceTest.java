package com.cloud.user.microservice.service;

import com.cloud.user.microservice.UserMicroserviceApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 业务系统信息业务层单元测试
 *
 * @author Jon_China
 * @create 2018/1/29
 */
public class SystemInfoServiceTest extends UserMicroserviceApplicationTests {

    @Autowired
    private SystemInfoService systemInfoService;

    @Test
    public void testDeleteSystemInfo(){
        super.assertResultCode(this.systemInfoService.deleteSystemInfo("a6d27b4f-9acd-455b-80fd-0a3e1e301896"));
    }
}
