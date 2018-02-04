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

    @Test
    public void testDeleteRoleForUserId(){
        Assert.assertTrue(this.roleInfoDao.deleteRoleForUserId("26fd50cc-382f-4a6c-a593-983bf584d577") >=1);
    }

    @Test
    public void testUpdateRoleInfo(){
        RoleInfo roleInfo = this.roleInfoDao.getRoleInfoById("fe3abb05-286e-4f94-bc34-b5d2bf4cf6fc");
        Assert.assertNotNull(roleInfo);
        roleInfo.setDescribe("432423");
        roleInfo.setRoleName("42432");
        roleInfo.setRoleType("01");
        Assert.assertTrue(this.roleInfoDao.updateRoleInfo(roleInfo) == 1);
    }

    @Test
    public void testDeleteRoleInfoById(){
        Assert.assertTrue(this.roleInfoDao.deleteRoleInfoById("30a005d9-c69c-4ae0-b573-9c97ac7041f2") == 1);
    }
}
