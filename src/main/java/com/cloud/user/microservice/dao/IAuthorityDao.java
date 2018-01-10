package com.cloud.user.microservice.dao;

import com.cloud.user.microservice.dto.requestDTO.AllocationAuthRequest;
import com.cloud.user.microservice.dto.requestDTO.AuthorityReqDTO;
import com.cloud.user.microservice.model.Authority;
import com.cloud.user.microservice.model.vo.AuthoritiesVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "authorityDao")
public interface IAuthorityDao {
    /**
     * 新增权限
     * @param request
     * @return
     */
    int addAuthority(AuthorityReqDTO request);

    /**
     * 获取所有菜单
     * @param request
     * @return
     */
    List<Authority> getAllAuthorities(AuthorityReqDTO request);

    /**
     * 菜单分页查询列表
     * @param request
     * @return
     */
    List<AuthoritiesVO> getAllAuthorityInfo(AuthorityReqDTO request);

    /**
     * 根据条件查询权限详情
     * @return
     */
    Authority getAuthorityInfo(AuthorityReqDTO request);

    /**
     * 角色权限分配
     * @param request
     * @return
     */
    int allocationAuth(AllocationAuthRequest request);

    /**
     * 获取用户所有权限信息
     * @param userId
     * @param appId
     * @return
     */
    List<Authority> getUserPrivileges(@Param("userId") String userId, @Param("appId")String appId);
}
