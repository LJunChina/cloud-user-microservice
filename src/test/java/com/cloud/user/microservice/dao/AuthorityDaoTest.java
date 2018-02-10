package com.cloud.user.microservice.dao;

import com.cloud.common.enums.YesOrNoEnum;
import com.cloud.user.microservice.UserMicroserviceApplicationTests;
import com.cloud.user.microservice.dto.requestDTO.AllocationAuthRequest;
import com.cloud.user.microservice.dto.requestDTO.AuthorityReqDTO;
import com.cloud.user.microservice.model.Authority;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * AuthorityDao测试类
 *
 * @author Jon_China
 * @create 2017/11/18
 */
public class AuthorityDaoTest extends UserMicroserviceApplicationTests {

    @Autowired
    private IAuthorityDao authorityDao;

    @Test
    public void testAddAuthority(){
        AuthorityReqDTO authority = new AuthorityReqDTO();
        authority.setId(UUID.randomUUID().toString());
        authority.setName("用户管理1");
        authority.setAvailable(YesOrNoEnum.YES.getCode());
        authority.setIcon("phone");
        authority.setParentId("0");
        authority.setSortNum(1);
        authority.setStyle("large");
        authority.setAppName("cloud_mall");
        Assert.assertEquals(1,this.authorityDao.addAuthority(authority));
    }

    @Test
    public void testGetAllAuthorities(){
        AuthorityReqDTO reqDTO = new AuthorityReqDTO();
        reqDTO.setAppName("cloud_mall");
        Assert.assertNotNull(this.authorityDao.getAllAuthorities(reqDTO));
    }

    @Test
    public void testGetAllAuthorityInfo(){
        AuthorityReqDTO reqDTO = new AuthorityReqDTO();
        reqDTO.setAppName("基础信息管理平台");
        Assert.assertNotNull(this.authorityDao.getAllAuthorityInfo(reqDTO));
    }

    @Test
    public void testGetAuthorityInfo(){
        AuthorityReqDTO reqDTO = new AuthorityReqDTO();
        reqDTO.setName("root");
        Assert.assertNotNull(this.authorityDao.getAuthorityInfo(reqDTO));
    }

    @Test
    public void testAllocationAuth(){
        AllocationAuthRequest request = new AllocationAuthRequest();
        request.setRoleId(UUID.randomUUID().toString());
        List<String> authIds = new ArrayList<>();
        authIds.add(UUID.randomUUID().toString());
        request.setAuthIds(authIds);
        Assert.assertEquals(1,this.authorityDao.allocationAuth(request));
    }

    @Test
    public void testGetUserPrivileges(){
        Assert.assertNotNull(this.authorityDao.getUserPrivileges("bb659ce6-d158-424e-a1a4-8d7f30d9c09b",null));
    }

    @Test
    public void testDeleteAuthoritiesByRoleId(){
        List<String> roleIds = new ArrayList<>();
        roleIds.add("88953374-5c7d-4106-ab5f-fc7e1501ec5f");
        Assert.assertTrue(this.authorityDao.deleteAuthoritiesByRoleId(roleIds,"1") >= 1);
    }

    @Test
    public void testGetAuthoritiesByRoleId(){
        Assert.assertNotNull(this.authorityDao.getAuthoritiesByRoleId("d5a6f90e-99b5-409c-a01d-d6f92992285e"));
    }

    @Test
    public void testUpdateAuthority(){
        AuthorityReqDTO request = new AuthorityReqDTO();
        request.setId("7a57e08b-ecf1-4906-8e73-dfd26faa66cf");
        Authority authority = this.authorityDao.getAuthorityInfo(request);
        authority.setName("修改测试");
        Assert.assertTrue(this.authorityDao.updateAuthority(authority) == 1);
    }

    @Test
    public void testGetAuthoritiesById(){
        Assert.assertNotNull(this.authorityDao.getAuthoritiesById("7a57e08b-ecf1-4906-8e73-dfd26faa66cf"));
    }

    @Test
    public void testDeleteAuthorityById(){
        Assert.assertTrue(1 == this.authorityDao.deleteAuthorityById("f8179337-6f09-4fed-b79a-337fcb1dce4f"));
    }
}
