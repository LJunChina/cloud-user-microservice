package com.cloud.user.microservice.dao;

import com.cloud.user.microservice.dto.requestDTO.RolePageReqDTO;
import com.cloud.user.microservice.dto.requestDTO.UserAllocationRequest;
import com.cloud.user.microservice.model.RoleInfo;
import com.cloud.user.microservice.model.vo.RolePageVO;
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

}
