package com.cloud.user.microservice.service.impl;

import com.cloud.common.dto.BaseRespDTO;
import com.cloud.common.enums.ResultCode;
import com.cloud.common.util.EmptyChecker;
import com.cloud.user.microservice.dao.IRoleInfoDao;
import com.cloud.user.microservice.dto.requestDTO.UserAllocationRequest;
import com.cloud.user.microservice.dto.requestDTO.RolePageReqDTO;
import com.cloud.user.microservice.model.RoleInfo;
import com.cloud.user.microservice.model.vo.RolePageVO;
import com.cloud.user.microservice.service.RoleInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author Jon_China
 * @create 2017/11/18
 */
@Service(value = "roleInfoService")
public class RoleInfoServiceImpl implements RoleInfoService {

    @Autowired
    private IRoleInfoDao roleInfoDao;
    /**
     * 添加角色信息
     * @param roleName
     * @param roleType
     * @param appId
     * @param describe
     * @return
     */
    @Override
    public BaseRespDTO saveRoleInfo(String roleName, String roleType, String appId, String describe) {
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setId(UUID.randomUUID().toString());
        roleInfo.setRoleName(roleName);
        roleInfo.setDescribe(describe);
        roleInfo.setAppId(appId);
        roleInfo.setRoleType(roleType);
        int row = this.roleInfoDao.addRoleInfo(roleInfo);
        if(1 == row){
            return new BaseRespDTO();
        }
        return new BaseRespDTO(ResultCode.FAIL);
    }

    /**
     * 角色信息分页查询
     *
     * @param request
     * @return
     */
    @Override
    public BaseRespDTO getAllRoleInfo(RolePageReqDTO request) {
        PageInfo<RolePageVO> result = PageHelper.startPage(request.getPageIndex(),request.getPageSize())
                .doSelectPageInfo(() -> this.roleInfoDao.getAllRoleInfo(request));
        BaseRespDTO respDTO = new BaseRespDTO();
        respDTO.setData(result);
        return respDTO;
    }

    /**
     * 角色分配用户信息
     * @param userId
     * @param roleIds
     * @return
     */
    @Override
    public BaseRespDTO allocationUsers(String userId,String roleIds) {
        if(EmptyChecker.isEmpty(userId) || EmptyChecker.isEmpty(roleIds)){
            return new BaseRespDTO(ResultCode.PARAMS_NOT_FOUND);
        }
        UserAllocationRequest request = new UserAllocationRequest();
        List<String> roleId = Arrays.asList(roleIds.split(","));
        request.setUserId(userId);
        request.setRoleIds(roleId);
        int row = this.roleInfoDao.allocationUsers(request);
        if(row >= 1){
            return new BaseRespDTO();
        }
        return new BaseRespDTO(ResultCode.FAIL);
    }
}
