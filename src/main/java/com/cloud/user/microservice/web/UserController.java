package com.cloud.user.microservice.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.dto.BaseRespDTO;
import com.cloud.common.enums.ResultCode;
import com.cloud.common.util.EmptyChecker;
import com.cloud.user.microservice.dto.responseDTO.UserDetailRespDTO;
import com.cloud.user.microservice.dto.responseDTO.UserSearchRespDTO;
import com.cloud.user.microservice.model.User;
import com.cloud.user.microservice.service.TokenService;
import com.cloud.user.microservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    @PostMapping(value = "/login")
    public String login(@RequestParam(name = "userName")String userName, @RequestParam(name = "password")String password){
        logger.info("the params for login userName:{},password:{}",userName,password);
        try {
            BaseRespDTO result = this.userService.userLogin(userName,password);
            logger.info("the result of user login is :{}",result.toString());
            return result.toString();
        }catch (Exception e){
            logger.error("exception occurred in login",e);
            return new BaseRespDTO(ResultCode.ERROR).toString();
        }
    }

    /**
     * 公钥获取
     * @return
     */
    @GetMapping(value = "/get-public-key")
    public String getPublicKey(){
        try {
            BaseRespDTO result = this.userService.getPublicKey();
            logger.info("this result of getPublicKey is :{}",result.toString());
            return result.toString();
        }catch (Exception e){
            logger.error("exception occurred in getPublicKey",e);
            return new BaseRespDTO(ResultCode.ERROR).toString();
        }
    }

    /**
     * 获取用户列表
     * @return
     */
    @GetMapping(value = "/get-user-list")
    public String getUserListByPage(@RequestParam(value = "message") String message){
        logger.info("the params of getUserListByPage is :{}",message);
        try {
            UserSearchRespDTO userSearchRespDTO = JSONObject.parseObject(message, UserSearchRespDTO.class);
            BaseRespDTO result = this.userService.getUserList(userSearchRespDTO);
            logger.info("this result of getUserListByPage is :{}",result.toString());
            return result.toString();
        }catch (Exception e){
            logger.error("exception occurred in getUserListByPage",e);
            return new BaseRespDTO(ResultCode.ERROR).toString();
        }
    }

    /**
     * 用户详情查询
     * @param userId
     * @return
     */
    @GetMapping(value = "/get-user-detail/{userId}")
    public String getUserDetail(@PathVariable(value = "userId")  String userId){
        logger.info("the params of getUserDetail is :{}",userId);
        if (EmptyChecker.isEmpty(userId)) {
            return new BaseRespDTO(ResultCode.PARAMS_NOT_FOUND).toString();
        }
        try {
            UserDetailRespDTO result = this.userService.getUserInfo(userId);
            logger.info("this result of getUserDetail is :{}",result.toString());
            return result.toString();
        }catch (Exception e){
            logger.error("exception occurred in getUserDetail",e);
            return new BaseRespDTO(ResultCode.ERROR).toString();
        }
    }

    /**
     * 保存内部用户信息
     * @param body
     * @return
     */
    @PostMapping("/save-user")
    public String saveUserInfo(@RequestBody String body){
        logger.info("the params of saveUserInfo is :{}",body);
        try {
            User user = JSON.parseObject(body,User.class);
            BaseRespDTO baseRespDTO = this.userService.saveUserInfo(user);
            String result = baseRespDTO.toString();
            logger.info("this result of saveUserInfo is : {}" ,result);
            return result;
        }catch (Exception e){
            logger.error("exception occurred in saveUserInfo :",e);
            return new BaseRespDTO(ResultCode.ERROR).toString();
        }
    }

    /**
     * 检测用户是否登录
     * @param tokenId
     * @return
     */
    @GetMapping("/is-login/{tokenId}")
    public String getUserIsLogin(@PathVariable(value = "tokenId") String tokenId){
        if(EmptyChecker.isEmpty(tokenId)){
            return new BaseRespDTO(ResultCode.PARAMS_NOT_FOUND).toString();
        }
        logger.info("the params of getUserIsLogin is :{}",tokenId);
        try {
            BaseRespDTO baseRespDTO = this.tokenService.refreshTokenInfo(tokenId);
            String result = baseRespDTO.toString();
            logger.info("this result of getUserIsLogin is : {}" ,result);
            return result;
        }catch (Exception e){
            logger.error("exception occurred in getUserIsLogin :",e);
            return new BaseRespDTO(ResultCode.ERROR).toString();
        }
    }

    /**
     * 用户退出登录
     * @param tokenId
     * @return
     */
    @PostMapping("/logout/{tokenId}")
    public String userLogout(@PathVariable(value = "tokenId") String tokenId){
        if(EmptyChecker.isEmpty(tokenId)){
            return new BaseRespDTO(ResultCode.PARAMS_NOT_FOUND).toString();
        }
        logger.info("the params of userLogout is :{}",tokenId);
        try {
            boolean result = this.tokenService.deleteTokenInfo(tokenId);
            logger.info("this result of userLogout is : {}" ,result);
            BaseRespDTO respDTO = new BaseRespDTO();
            respDTO.setData(result);
            return respDTO.toString();
        }catch (Exception e){
            logger.error("exception occurred in userLogout :",e);
            return new BaseRespDTO(ResultCode.ERROR).toString();
        }
    }

}
