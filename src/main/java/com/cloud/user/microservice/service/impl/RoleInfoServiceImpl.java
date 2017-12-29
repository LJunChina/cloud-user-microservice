package com.cloud.user.microservice.service.impl;

import com.cloud.user.microservice.dao.IRoleInfoDao;
import com.cloud.user.microservice.dto.BaseRespDTO;
import com.cloud.user.microservice.enums.ResultCode;
import com.cloud.user.microservice.model.RoleInfo;
import com.cloud.user.microservice.service.RoleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     *@param roleName
     * @return
     */
    @Override
    public BaseRespDTO saveRoleInfo(String roleName) {
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setId(UUID.randomUUID().toString());
        roleInfo.setRoleName(roleName);
        int row = this.roleInfoDao.addRoleInfo(roleInfo);
        if(1 == row){
            return new BaseRespDTO();
        }
        return new BaseRespDTO(ResultCode.FAIL);
    }
}
