package com.cloud.user.microservice;

import com.cloud.common.dto.BaseRespDTO;
import com.cloud.common.enums.ResultCode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@MapperScan(value = "com.cloud.user.microservice.dao")
//使用@Transactional @Rollback注解进行自动回滚
@Transactional
@Rollback
@SpringBootTest
public class UserMicroserviceApplicationTests {

	@Test
	public void contextLoads() {
	}

	protected void assertResultCode(BaseRespDTO result){
        Assert.assertTrue(ResultCode.OK.getCode().equals(result.getCode()));
    }

}
