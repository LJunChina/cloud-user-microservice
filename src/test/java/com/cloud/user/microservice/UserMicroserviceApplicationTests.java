package com.cloud.user.microservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@MapperScan(value = "com.cloud.user.microservice.dao")
@SpringBootTest
public class UserMicroserviceApplicationTests {

	@Test
	public void contextLoads() {
	}

}
