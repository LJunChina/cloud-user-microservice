package com.cloud.user.microservice.service.impl;

import com.cloud.common.dto.BaseRespDTO;
import com.cloud.common.enums.ResultCode;
import com.cloud.common.enums.YesOrNoEnum;
import com.cloud.common.util.EmptyChecker;
import com.cloud.user.microservice.dao.IRoleInfoDao;
import com.cloud.user.microservice.dao.IUserRoleDao;
import com.cloud.user.microservice.dto.requestDTO.UserAllocationRequest;
import com.cloud.user.microservice.dto.requestDTO.RolePageReqDTO;
import com.cloud.user.microservice.model.RoleInfo;
import com.cloud.user.microservice.model.UserRole;
import com.cloud.user.microservice.model.vo.RolePageVO;
import com.cloud.user.microservice.service.RoleInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Jon_China
 * @create 2017/11/18
 */
@Service(value = "roleInfoService")
public class RoleInfoServiceImpl implements RoleInfoService {

    @Autowired
    private IRoleInfoDao roleInfoDao;
    @Autowired
    private IUserRoleDao userRoleDao;
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
        if(EmptyChecker.isEmpty(roleType) || roleType.length() > 2){
            return new BaseRespDTO(ResultCode.PARAMS_NOT_FOUND.getCode(),"非法参数");
        }
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

        //根据userId查询用户角色信息
        List<UserRole> userRoles = this.userRoleDao.getRoleIdsByUserId(request.getUserId());
        if(EmptyChecker.notEmpty(userRoles)){
            //若用户角色列表包含返回列表值则选中
            List<String> roleList = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
            result.getList().forEach(r -> {
                if(roleList.contains(r.getId())){
                    r.setSelected(YesOrNoEnum.YES.getCode());
                }else {
                    r.setSelected(YesOrNoEnum.NO.getCode());
                }
            });
        }
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
        //删除用户角色信息
        this.roleInfoDao.deleteRoleForUserId(userId);
        int row = this.roleInfoDao.allocationUsers(request);
        if(row >= 1){
            return new BaseRespDTO();
        }
        return new BaseRespDTO(ResultCode.FAIL);
    }

    /**
     * 角色详情查询
     *
     * @param id
     * @return
     */
    @Override
    public BaseRespDTO getRoleInfo(String id) {
        if(EmptyChecker.isEmpty(id)){
            return new BaseRespDTO(ResultCode.PARAMS_NOT_FOUND.getCode(),"id不能为空");
        }
        RoleInfo roleInfo = this.roleInfoDao.getRoleInfoById(id);
        BaseRespDTO respDTO = new BaseRespDTO();
        respDTO.setData(roleInfo);
        return respDTO;
    }

    /**
     * 更新角色信息
     *
     * @param roleName
     * @param appId
     * @param roleType
     * @param describe
     * @param id
     * @return
     */
    @Override
    public BaseRespDTO updateRoleInfo(String roleName, String appId, String roleType, String describe, String id) {
        if(EmptyChecker.isEmpty(id)){
            return new BaseRespDTO(ResultCode.PARAMS_NOT_FOUND.getCode(),"id不能为空");
        }
        //查询角色信息
        RoleInfo roleInfo = this.roleInfoDao.getRoleInfoById(id);
        if(EmptyChecker.isEmpty(roleInfo)){
            return new BaseRespDTO(ResultCode.NO_DATA_FOUND);
        }
        roleInfo.setRoleType(roleType);
        roleInfo.setRoleName(roleName);
        roleInfo.setDescribe(describe);
        roleInfo.setAppId(appId);
        int effectRow = this.roleInfoDao.updateRoleInfo(roleInfo);
        if(effectRow == 1){
            return new BaseRespDTO();
        }
        return new BaseRespDTO(ResultCode.FAIL);
    }

    /**
     * 根据id删除角色信息
     *
     * @param id
     * @return
     */
    @Override
    public BaseRespDTO deleteRoleInfoById(String id) {
        if(EmptyChecker.isEmpty(id)){
            return new BaseRespDTO(ResultCode.PARAMS_NOT_FOUND.getCode(),"id不能为空");
        }
        int effectRow = this.roleInfoDao.deleteRoleInfoById(id);
        if(effectRow == 1){
            return new BaseRespDTO();
        }
        return new BaseRespDTO(ResultCode.FAIL);
    }
}
