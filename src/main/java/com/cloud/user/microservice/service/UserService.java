package com.cloud.user.microservice.service;

import com.cloud.common.dto.BaseRespDTO;
import com.cloud.user.microservice.dto.responseDTO.UserDetailRespDTO;
import com.cloud.user.microservice.dto.responseDTO.UserSearchRespDTO;
import com.cloud.user.microservice.model.User;

public interface UserService {
    /**
     * 用户登录
     * @param userName
     * @param password
     * @return
     * @throws Exception
     */
    BaseRespDTO userLogin(String userName,String password) throws Exception;

    /**
     * 获取公钥信息
     * @return
     * @throws Exception
     */
    BaseRespDTO getPublicKey() throws Exception;

    /**
     * 用户列表查询
     * @param request
     * @return
     */
    BaseRespDTO getUserList(UserSearchRespDTO request);

    /**
     * 根据userId查询用户信息
     * @param userId
     * @return
     */
    UserDetailRespDTO getUserInfo(String userId);

    /**
     * 保存用户信息
     * @param user
     * @return
     */
    BaseRespDTO saveUserInfo(User user);
}
