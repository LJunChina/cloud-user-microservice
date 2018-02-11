package com.cloud.user.microservice.web;

import com.alibaba.fastjson.JSONObject;
import com.cloud.common.dto.BaseRespDTO;
import com.cloud.common.enums.ResultCode;
import com.cloud.common.util.EmptyChecker;
import com.cloud.user.microservice.dto.requestDTO.AuthorityReqDTO;
import com.cloud.user.microservice.dto.responseDTO.MenuRespDTO;
import com.cloud.user.microservice.model.Authority;
import com.cloud.user.microservice.service.AuthorityService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * 权限API
 *
 * @author Jon_China
 * @create 2017/11/19
 */
@RestController
@RequestMapping(value = "/auth")
public class AuthorityController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorityController.class);
    @Autowired
    private AuthorityService authorityService;

    /**
     * 保存权限信息
     * @param body
     * @return
     */
    @PostMapping(value = "/save-auth")
    public String saveAuthority(@RequestBody String body){
        logger.info("the params of saveAuthority is : {}",body);
        if(EmptyChecker.isEmpty(body)){
            return new BaseRespDTO(ResultCode.PARAMS_NOT_FOUND).toString();
        }
        try {
            AuthorityReqDTO request = JSONObject.parseObject(body,AuthorityReqDTO.class);
            BaseRespDTO baseRespDTO = this.authorityService.saveAuthority(request);
            String result = baseRespDTO.toString();
            logger.info("result of the saveAuthority is :{}",request);
            return result;
        }catch (Exception e){
            logger.error("exception occurred in saveAuthority",e);
            return new BaseRespDTO(ResultCode.ERROR).toString();
        }
    }

    /**
     * 获取系统菜单
     * @param appName
     * @return
     */
    @GetMapping(value = "/get-all-menus/{appName}/{userId}")
    public String getAllMenus(@PathVariable(value = "appName") String appName,@PathVariable(value = "userId") String userId){
        logger.info("the params of getAllMenus,appName: {},userId: {}",appName,userId);
        if(EmptyChecker.isEmpty(appName) || EmptyChecker.isEmpty(userId)){
            return new BaseRespDTO(ResultCode.PARAMS_NOT_FOUND).toString();
        }
        try {
            MenuRespDTO respDTO = this.authorityService.getAllMenus(appName,userId);
            String result = respDTO.toString();
            logger.info("result of the getAllMenus is :{}",result);
            return result;
        }catch (Exception e){
            logger.error("exception occurred in getAllMenus",e);
            return new BaseRespDTO(ResultCode.ERROR).toString();
        }
    }

    /**
     * 获取所有菜单、操作信息
     * @param name 菜单/操作名称
     * @param pageIndex 页号
     * @param pageSize 页大小
     * @param appName 所属系统
     * @param itemType 类型
     * @return
     */
    @GetMapping(value = "/get-all-auth")
    public String getAllAuthoritiesByPage(@RequestParam(value = "name",defaultValue = StringUtils.EMPTY) String name,
                                          @RequestParam(value = "pageIndex",defaultValue = "1")Integer pageIndex,
                                          @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
                                          @RequestParam(value = "appName",defaultValue = StringUtils.EMPTY)String appName,
                                          @RequestParam(value = "itemType",defaultValue = StringUtils.EMPTY)String itemType,
                                          @RequestParam(value = "roleId",defaultValue = StringUtils.EMPTY) String roleId,
                                          @RequestParam(value = "appId",defaultValue = StringUtils.EMPTY) String appId){
        logger.info("the params of getAllAuthoritiesByPage,name:{},pageIndex:{},pageSize:{},appName:{},itemType:{},roleId:{},appId:{}"
                ,name,pageIndex,pageSize,appName,itemType,roleId,appId);
        if(EmptyChecker.isEmpty(itemType)){
            return new BaseRespDTO(ResultCode.PARAMS_NOT_FOUND).toString();
        }
        try {
            AuthorityReqDTO request = new AuthorityReqDTO();
            request.setAppName(appName);
            request.setPageIndex(pageIndex);
            request.setPageSize(pageSize);
            request.setItemType(itemType);
            request.setName(name);
            request.setRoleId(roleId);
            request.setAppId(appId);
            BaseRespDTO respDTO = this.authorityService.getAllAuthoritiesByPage(request);
            String result = respDTO.toString();
            logger.info("result of the getAllAuthoritiesByPage is :{}",result);
            return result;
        }catch (Exception e){
            logger.error("exception occurred in getAllAuthoritiesByPage",e);
            return new BaseRespDTO(ResultCode.ERROR).toString();
        }
    }

    /**
     * 角色权限分配
     * @param roleId
     * @param authIds
     * @return
     */
    @PostMapping(value = "/allocation-auth")
    public String allocationAuth(@RequestParam(value = "roleId")String roleId,@RequestParam(value = "authIds")String authIds,@RequestParam(value = "itemType") String itemType){
        logger.info("params of allocationAuth,roleId:{},authIds:{},itemType:{}",roleId,authIds,itemType);
        try {
            BaseRespDTO baseRespDTO = this.authorityService.allocationAuth(roleId,authIds,itemType);
            String result = baseRespDTO.toString();
            logger.info("result of allocationAuth:{}",result);
            return result;
        }catch (Exception e){
            logger.error("exception occurred in allocationAuth",e);
            return new BaseRespDTO(ResultCode.ERROR).toString();
        }
    }

    /**
     * 用户权限查询
     * @param appId 系统id
     * @param userId 用户id
     * @param uri 请求uri
     * @return
     */
    @GetMapping(value = "/check-privilege")
    public String checkUserPrivileges(@RequestParam(value = "appId")String appId,
                                      @RequestParam(value = "userId")String userId,
                                      @RequestParam(value = "uri")String uri){
        logger.info("params of checkUserPrivileges,appId:{},userId:{},uri:{}",appId,userId,uri);
        try {
            BaseRespDTO baseRespDTO = this.authorityService.checkUserPrivileges(userId,appId,uri);
            String result = baseRespDTO.toString();
            logger.info("result of checkUserPrivileges:{}",result);
            return result;
        }catch (Exception e){
            logger.error("exception occurred in checkUserPrivileges",e);
            return new BaseRespDTO(ResultCode.ERROR).toString();
        }
    }

    /**
     * 根据id删除菜单/权限信息
     * @param id
     * @return
     */
    @PostMapping(value = "/delete/{id}")
    public String deleteAuthorityById(@PathVariable(value = "id") String id){
        logger.info("param of deleteAuthorityById,id:{}",id);
        try {
            BaseRespDTO respDTO = this.authorityService.deleteAuthorityById(id);
            String result = respDTO.toString();
            logger.info("result of deleteAuthorityById:{}",result);
            return result;
        }catch (Exception e){
            logger.error("exception occurred in deleteAuthorityById",e);
            return new BaseRespDTO(ResultCode.ERROR).toString();
        }
    }


    /**
     * 更新菜单/权限信息
     * @param id
     * @param body
     * @return
     */
    @PostMapping(value = "/update/{id}")
    public String updateAuthority(@PathVariable(value = "id") String id,@RequestBody String body){
        logger.info("param of deleteAuthorityById,id:{},body:{}",id,body);
        try {
            Authority authority = JSONObject.parseObject(body,Authority.class);
            authority.setId(id);
            BaseRespDTO respDTO = this.authorityService.updateAuthority(authority);
            String result = respDTO.toString();
            logger.info("result of updateAuthority:{}",result);
            return result;
        }catch (Exception e){
            logger.error("exception occurred in updateAuthority",e);
            return new BaseRespDTO(ResultCode.ERROR).toString();
        }
    }

    /**
     * 菜单/权限详情查询api
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public String getAuthorityInfo(@PathVariable(value = "id") String id){
        logger.info("param of getAuthorityInfo,id:{",id);
        try {
            BaseRespDTO respDTO = this.authorityService.getAuthorityById(id);
            String result = respDTO.toString();
            logger.info("result of getAuthorityInfo:{}",result);
            return result;
        }catch (Exception e){
            logger.error("exception occurred in getAuthorityInfo",e);
            return new BaseRespDTO(ResultCode.ERROR).toString();
        }
    }
}
