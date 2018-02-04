package com.cloud.user.microservice.web;

import com.cloud.common.dto.BaseRespDTO;
import com.cloud.common.enums.ResultCode;
import com.cloud.common.util.EmptyChecker;
import com.cloud.user.microservice.dto.requestDTO.RolePageReqDTO;
import com.cloud.user.microservice.service.RoleInfoService;
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
                                 @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                 @RequestParam(value = "userId") String userId){
        logger.info("the params of getAllRoleInfo,roleName:{},appId:{},pageIndex:{},pageSize:{} ",roleName,appId,pageIndex,pageSize);
        try {
            RolePageReqDTO request = new RolePageReqDTO();
            request.setRoleName(roleName);
            request.setAppId(appId);
            request.setPageIndex(pageIndex);
            request.setPageSize(pageSize);
            request.setUserId(userId);
            BaseRespDTO baseRespDTO = this.roleInfoService.getAllRoleInfo(request);
            String result = baseRespDTO.toString();
            logger.info("the result of getAllRoleInfo is : {}",result);
            return result;
        }catch (Exception e){
            logger.error("exception occurred in getAllRoleInfo",e);
            return new BaseRespDTO(ResultCode.ERROR).toString();
        }
    }


    /**
     * 角色分配用户信息
     * @param userId
     * @param roleIds
     * @return
     */
    @PostMapping(value = "/allocation-users")
    public String allocationUsers(@RequestParam(value = "userId",defaultValue = StringUtils.EMPTY) String userId,
                                 @RequestParam(value = "roleIds",defaultValue = StringUtils.EMPTY) String roleIds){
        logger.info("the params of allocationUsers,userId:{},roleIds:{}",userId,roleIds);
        try {
            BaseRespDTO baseRespDTO = this.roleInfoService.allocationUsers(userId,roleIds);
            String result = baseRespDTO.toString();
            logger.info("the result of allocationUsers is : {}",result);
            return result;
        }catch (Exception e){
            logger.error("exception occurred in allocationUsers",e);
            return new BaseRespDTO(ResultCode.ERROR).toString();
        }
    }

    /**
     * 查询角色详情
     * @param id
     * @return
     */
    @GetMapping(value = "/get/{id}")
    public String getRoleInfo(@PathVariable(value = "id")String id){
        logger.info("getRoleInfo request param,id:{}",id);
        try {
            BaseRespDTO respDTO = this.roleInfoService.getRoleInfo(id);
            String result = respDTO.toString();
            logger.info("the result of getRoleInfo is : {}",result);
            return result;
        }catch (Exception e){
            logger.error("exception occurred in getRoleInfo",e);
            return new BaseRespDTO(ResultCode.ERROR).toString();
        }
    }

    /**
     * 更新角色信息
     * @param roleName
     * @param appId
     * @param roleType
     * @param describe
     * @param id
     * @return
     */
    @PostMapping(value = "/update/{id}")
    public String updateRoleInfo(@RequestParam(value = "roleName",required = false) String roleName,
                                 @RequestParam(value = "appId",required = false) String appId,
                                 @RequestParam(value = "roleType",required = false) String roleType,
                                 @RequestParam(value = "describe") String describe,@PathVariable(value = "id")String id){
        logger.info("updateRoleInfo request param,id:{},roleName:{},appId:{},roleType:{},describe:{}",id,roleName,appId,roleType,describe);
        try {
            BaseRespDTO respDTO = this.roleInfoService.updateRoleInfo(roleName,appId,roleType,describe,id);
            String result = respDTO.toString();
            logger.info("the result of updateRoleInfo is : {}",result);
            return result;
        }catch (Exception e){
            logger.error("exception occurred in updateRoleInfo",e);
            return new BaseRespDTO(ResultCode.ERROR).toString();
        }
    }

    /**
     * 删除角色信息
     * @param id
     * @return
     */
    @PostMapping(value = "/delete/{id}")
    public String deleteRoleInfoById(@PathVariable(value = "id") String id){
        logger.info("deleteRoleInfoById request param,id:{}",id);
        try {
            BaseRespDTO respDTO = this.roleInfoService.deleteRoleInfoById(id);
            String result = respDTO.toString();
            logger.info("the result of deleteRoleInfoById is : {}",result);
            return result;
        }catch (Exception e){
            logger.error("exception occurred in deleteRoleInfoById",e);
            return new BaseRespDTO(ResultCode.ERROR).toString();
        }
    }
}
