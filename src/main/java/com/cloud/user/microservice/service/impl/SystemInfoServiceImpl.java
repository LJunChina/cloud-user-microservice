package com.cloud.user.microservice.service.impl;

import com.cloud.common.dto.BaseRespDTO;
import com.cloud.common.enums.ResultCode;
import com.cloud.common.util.EmptyChecker;
import com.cloud.user.microservice.dao.ISystemInfoDao;
import com.cloud.user.microservice.model.SystemInfo;
import com.cloud.user.microservice.service.SystemInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Jon_China
 * @create 2017/12/23
 */
@Service(value = "systemInfoService")
public class SystemInfoServiceImpl implements SystemInfoService {

    private static final String ERROR_MSG_ID_NOT_NULL = "ID不能为空";

    @Autowired
    private ISystemInfoDao systemInfoDao;

    /**
     * 业务系统分页查询
     *
     * @param systemName 业务系统名称
     * @param pageIndex  页号
     * @param pageSize   页大小
     * @return
     */
    @Override
    public BaseRespDTO getSystemInfoByName(String systemName, int pageIndex, int pageSize) {
        PageInfo<SystemInfo> systemInfoPageInfo = PageHelper.startPage(pageIndex,pageSize)
                .doSelectPageInfo(() -> this.systemInfoDao.getSystemInfoByName(systemName));
        BaseRespDTO baseRespDTO = new BaseRespDTO();
        baseRespDTO.setData(systemInfoPageInfo);
        return baseRespDTO;
    }

    /**
     * 保存业务系统
     * @param systemName
     * @param systemChn
     * @param systemHost
     * @param systemContext
     * @return
     */
    @Override
    public BaseRespDTO saveSystemInfo(String systemName,String systemChn,String systemHost,String systemContext) {
        SystemInfo systemInfo = new SystemInfo();
        systemInfo.setId(UUID.randomUUID().toString());
        systemInfo.setSystemName(systemName);
        systemInfo.setSystemHost(systemHost);
        systemInfo.setSystemContext(systemContext);
        systemInfo.setSystemChn(systemChn);
        int row = this.systemInfoDao.saveSystemInfo(systemInfo);
        if(row == 1){
            return new BaseRespDTO();
        }
        return new BaseRespDTO(ResultCode.FAIL);
    }

    @Override
    public BaseRespDTO deleteSystemInfo(String id) {
        if(EmptyChecker.isEmpty(id)){
            return new BaseRespDTO(ResultCode.FAIL.getCode(),ERROR_MSG_ID_NOT_NULL);
        }
        int effectRow = this.systemInfoDao.deleteSystemInfo(id);
        if(effectRow == 1){
            return new BaseRespDTO();
        }
        return new BaseRespDTO(ResultCode.FAIL);
    }

    /**
     * 根据id查询系统信息
     *
     * @param id
     * @return
     */
    @Override
    public BaseRespDTO selectSystemInfo(String id) {
        if(EmptyChecker.isEmpty(id)){
            return new BaseRespDTO(ResultCode.PARAMS_NOT_FOUND.getCode(),"id不能为空");
        }
        SystemInfo systemInfo = this.systemInfoDao.getSystemInfoById(id);
        BaseRespDTO respDTO = new BaseRespDTO();
        respDTO.setData(systemInfo);
        return respDTO;
    }

    /**
     * 更新系统信息
     *
     * @param systemName
     * @param systemChn
     * @param systemHost
     * @param systemContext
     * @param id
     * @return
     */
    @Override
    public BaseRespDTO updateSystemInfo(String systemName, String systemChn, String systemHost, String systemContext, String id) {
        if(EmptyChecker.isEmpty(id)){
            return new BaseRespDTO(ResultCode.PARAMS_NOT_FOUND);
        }
        //查询系统信息
        SystemInfo currentSystem = this.systemInfoDao.getSystemInfoById(id);
        if(EmptyChecker.isEmpty(currentSystem)){
            return new BaseRespDTO(ResultCode.FAIL.getCode(),"未查询到任何数据");
        }
        currentSystem.setSystemName(systemName);
        currentSystem.setSystemChn(systemChn);
        currentSystem.setSystemHost(systemHost);
        currentSystem.setSystemContext(systemContext);
        int effectRow = this.systemInfoDao.updateSystemInfo(currentSystem);
        if(effectRow == 1){
            return new BaseRespDTO();
        }
        return new BaseRespDTO(ResultCode.FAIL);
    }
}
