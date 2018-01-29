package com.cloud.user.microservice.dao;

import com.cloud.user.microservice.model.SystemInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository(value = "systemInfoDao")
public interface ISystemInfoDao {
    /**
     * 根据系统名称查询
     * @param systemName
     * @return
     */
    List<SystemInfo> getSystemInfoByName(@Param(value = "name") String systemName);

    /**
     * 保存系统信息
     * @param systemInfo
     * @return
     */
    int saveSystemInfo(SystemInfo systemInfo);

    /**
     * 根据id删除系统信息
     * @param id
     * @return
     */
    int deleteSystemInfo(@Param(value = "id") String id);
}
