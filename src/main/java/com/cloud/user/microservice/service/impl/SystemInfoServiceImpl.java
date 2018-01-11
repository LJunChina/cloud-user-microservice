package com.cloud.user.microservice.service.impl;

import com.cloud.common.dto.BaseRespDTO;
import com.cloud.common.enums.ResultCode;
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
}
