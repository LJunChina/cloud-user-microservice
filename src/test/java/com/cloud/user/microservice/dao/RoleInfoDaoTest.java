package com.cloud.user.microservice.dao;

import com.cloud.user.microservice.UserMicroserviceApplicationTests;
import com.cloud.user.microservice.dto.requestDTO.RolePageReqDTO;
import com.cloud.user.microservice.dto.requestDTO.UserAllocationRequest;
import com.cloud.user.microservice.model.RoleInfo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * RuleInfoDao 测试类
 *
 * @author Jon_China
 * @create 2017/11/18
 */
public class RoleInfoDaoTest extends UserMicroserviceApplicationTests {

    @Autowired
    private IRoleInfoDao roleInfoDao;

    @Test
    public void testAddRoleInfo(){
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setId(UUID.randomUUID().toString());
        roleInfo.setRoleName("项目经理");
        Assert.assertEquals(1,this.roleInfoDao.addRoleInfo(roleInfo));
    }

    @Test
    public void testGetAllRoleInfo(){
        RolePageReqDTO request = new RolePageReqDTO();
        Assert.assertNotNull(this.roleInfoDao.getAllRoleInfo(request));
    }

    @Test
    public void testAllocationUsers(){
        UserAllocationRequest request = new UserAllocationRequest();
        request.setUserId(UUID.randomUUID().toString());
        List<String> userIds = new ArrayList<>();
        userIds.add(UUID.randomUUID().toString());
        request.setRoleIds(userIds);
        Assert.assertEquals(1,this.roleInfoDao.allocationUsers(request));
    }
}
