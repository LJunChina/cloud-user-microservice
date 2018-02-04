package com.cloud.user.microservice.dao;

import com.cloud.user.microservice.dto.requestDTO.RolePageReqDTO;
import com.cloud.user.microservice.dto.requestDTO.UserAllocationRequest;
import com.cloud.user.microservice.model.RoleInfo;
import com.cloud.user.microservice.model.vo.RolePageVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "userInfoDao")
public interface IRoleInfoDao {
    /**
     * 保存角色信息
     * @param roleInfo
     * @return
     */
    int addRoleInfo(RoleInfo roleInfo);

    /**
     * 分页查询角色信息
     * @param request
     * @return
     */
    List<RolePageVO> getAllRoleInfo(RolePageReqDTO request);

    /**
     * 为角色分配用户
     * @param request
     * @return
     */
    int allocationUsers(UserAllocationRequest request);

    /**
     * 根据用户id删除用户所有角色信息
     * @param userId
     * @return
     */
    int deleteRoleForUserId(@Param(value = "userId") String userId);

    /**
     * 更新角色信息
     * @param roleInfo
     * @return
     */
    int updateRoleInfo(RoleInfo roleInfo);

    /**
     * 根据id查询角色详情
     * @param id
     * @return
     */
    RoleInfo getRoleInfoById(@Param(value = "id") String id);

    /**
     * 根据id删除角色信息
     * @param id
     * @return
     */
    int deleteRoleInfoById(@Param(value = "id") String id);

}
