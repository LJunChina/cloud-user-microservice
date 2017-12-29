package com.cloud.user.microservice.web;

import com.alibaba.fastjson.JSONObject;
import com.cloud.user.microservice.dto.AuthorityReqDTO;
import com.cloud.user.microservice.dto.BaseRespDTO;
import com.cloud.user.microservice.dto.MenuRespDTO;
import com.cloud.user.microservice.enums.ResultCode;
import com.cloud.user.microservice.service.AuthorityService;
import com.cloud.user.microservice.utils.EmptyChecker;
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
}
