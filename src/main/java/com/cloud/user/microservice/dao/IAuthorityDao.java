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

    /**
     * 根据角色id删除权限信息
     * @param roleIds
     * @param itemType
     * @return
     */
    int deleteAuthoritiesByRoleId(@Param(value = "roleIds") List<String> roleIds,@Param(value = "itemType") String itemType);

    /**
     * 根据角色id查询角色权限列表
     * @param roleId
     * @return
     */
    List<Authority> getAuthoritiesByRoleId(@Param(value = "roleId")String roleId);

    /**
     * 更新权限信息,包括菜单及操作
     * @param authority
     * @return
     */
    int updateAuthority(Authority authority);
}
