package com.cloud.user.microservice.dao;

import com.cloud.user.microservice.UserMicroserviceApplicationTests;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * IUserRoleDao单元测试
 *
 * @author Jon_China
 * @create 2018/1/20
 */
public class UserRoleDaoTest extends UserMicroserviceApplicationTests {

    @Autowired
    private IUserRoleDao userRoleDao;

    @Test
    public void testGetRoleIdsByUserId(){
        Assert.assertNotNull(this.userRoleDao.getRoleIdsByUserId("342dc4fd-ddeb-42ad-87b8-a53ab1ab45ea"));
    }
}
