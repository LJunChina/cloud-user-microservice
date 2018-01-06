package com.cloud.user.microservice.web;

import com.alibaba.fastjson.JSONObject;
import com.cloud.user.microservice.dto.requestDTO.AuthorityReqDTO;
import com.cloud.user.microservice.dto.responseDTO.BaseRespDTO;
import com.cloud.user.microservice.dto.responseDTO.MenuRespDTO;
import com.cloud.user.microservice.enums.ResultCode;
import com.cloud.user.microservice.service.AuthorityService;
import com.cloud.user.microservice.utils.EmptyChecker;
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
    @GetMapping(value = "/get-all-menus/{appName}")
    public String getAllMenus(@PathVariable(value = "appName") String appName){
        logger.info("the params of getAllMenus is : {}",appName);
        if(EmptyChecker.isEmpty(appName)){
            return new BaseRespDTO(ResultCode.PARAMS_NOT_FOUND).toString();
        }
        try {
            MenuRespDTO respDTO = this.authorityService.getAllMenus(appName);
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
                                          @RequestParam(value = "itemType",defaultValue = StringUtils.EMPTY)String itemType){
        logger.info("the params of getAllAuthoritiesByPage,name:{},pageIndex:{},pageSize:{},appName:{},itemType:{}"
                ,name,pageIndex,pageSize,appName,itemType);
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
            BaseRespDTO respDTO = this.authorityService.getAllAuthoritiesByPage(request);
            String result = respDTO.toString();
            logger.info("result of the getAllAuthoritiesByPage is :{}",result);
            return result;
        }catch (Exception e){
            logger.error("exception occurred in getAllAuthoritiesByPage",e);
            return new BaseRespDTO(ResultCode.ERROR).toString();
        }
    }
}
