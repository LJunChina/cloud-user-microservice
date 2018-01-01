package com.cloud.user.microservice.web;

import com.cloud.user.microservice.dto.BaseRespDTO;
import com.cloud.user.microservice.dto.RolePageReqDTO;
import com.cloud.user.microservice.enums.ResultCode;
import com.cloud.user.microservice.service.RoleInfoService;
import com.cloud.user.microservice.utils.EmptyChecker;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 角色API
 *
 * @author Jon_China
 * @create 2017/11/18
 */
@RestController
@RequestMapping(value = "/role")
public class RoleInfoController {

    private static final Logger logger = LoggerFactory.getLogger(RoleInfoController.class);

    @Autowired
    private RoleInfoService roleInfoService;


    /**
     * 新增角色信息
     * @param roleName
     * @return
     */
    @PostMapping(value = "/save-role")
    public String saveRoleInfo(@RequestParam(value = "roleName",defaultValue = StringUtils.EMPTY) String roleName,
                               @RequestParam(value = "roleType",defaultValue = "0")String roleType,
                               @RequestParam(value = "appId",defaultValue = StringUtils.EMPTY)String appId,
                               @RequestParam(value = "describe",defaultValue = StringUtils.EMPTY)String describe){
        logger.info("the params of saveRoleInfo,roleName:{},roleType:{},appId:{},describe:{} ",roleName,roleType,appId,describe);
        if(EmptyChecker.isEmpty(roleName) || EmptyChecker.isEmpty(roleType) || EmptyChecker.isEmpty(appId)){
            return new BaseRespDTO(ResultCode.PARAMS_NOT_FOUND).toString();
        }
        try {
            BaseRespDTO baseRespDTO = this.roleInfoService.saveRoleInfo(roleName,roleType,appId,describe);
            String result = baseRespDTO.toString();
            logger.info("the result of saveRoleInfo is : {}",result);
            return result;
        }catch (Exception e){
            logger.error("exception occurred in saveRoleInfo",e);
            return new BaseRespDTO(ResultCode.ERROR).toString();
        }
    }


    /**
     * 角色分页查询
     * @param roleName
     * @param appId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/get-roles")
    public String getAllRoleInfo(@RequestParam(value = "roleName",defaultValue = StringUtils.EMPTY) String roleName,
                                 @RequestParam(value = "appId",defaultValue = StringUtils.EMPTY) String appId,
                                 @RequestParam(value = "pageIndex",defaultValue = "1") Integer pageIndex,
                                 @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        logger.info("the params of getAllRoleInfo,roleName:{},appId:{},pageIndex:{},pageSize:{} ",roleName,appId,pageIndex,pageSize);
        try {
            RolePageReqDTO request = new RolePageReqDTO();
            request.setRoleName(roleName);
            request.setAppId(appId);
            request.setPageIndex(pageIndex);
            request.setPageSize(pageSize);
            BaseRespDTO baseRespDTO = this.roleInfoService.getAllRoleInfo(request);
            String result = baseRespDTO.toString();
            logger.info("the result of getAllRoleInfo is : {}",result);
            return result;
        }catch (Exception e){
            logger.error("exception occurred in getAllRoleInfo",e);
            return new BaseRespDTO(ResultCode.ERROR).toString();
        }
    }
}
