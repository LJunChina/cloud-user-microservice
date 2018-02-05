package com.cloud.user.microservice.service.impl;

import com.cloud.common.dto.BaseRespDTO;
import com.cloud.common.encrypt.EncryptUtil;
import com.cloud.common.encrypt.RSAEncrypt;
import com.cloud.common.enums.ResultCode;
import com.cloud.common.enums.YesOrNoEnum;
import com.cloud.common.util.EmptyChecker;
import com.cloud.user.microservice.dao.IUserDao;
import com.cloud.user.microservice.dto.responseDTO.UserDetailRespDTO;
import com.cloud.user.microservice.dto.responseDTO.UserSearchRespDTO;
import com.cloud.user.microservice.model.TokenInfo;
import com.cloud.user.microservice.model.User;
import com.cloud.user.microservice.model.vo.UserPageVO;
import com.cloud.user.microservice.service.TokenService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.cloud.user.microservice.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.interfaces.RSAPrivateKey;
import java.util.Base64;
import java.util.UUID;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String INI_PASSWORD = "123456";

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IUserDao userDao;

    @Override
    public BaseRespDTO userLogin(String userName, String password) throws Exception {
        if(EmptyChecker.isEmpty(userName)){
            return new BaseRespDTO(ResultCode.USER_NAME_NOT_ALLOW_EMPTY);
        }
        if(EmptyChecker.isEmpty(password)){
            return new BaseRespDTO(ResultCode.PASSWORD_NOT_ALLOW_EMPTY);
        }
        //解密
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("privateKey.keystore");
        InputStreamReader reader = new InputStreamReader(inputStream);
        String privateKeyStr = RSAEncrypt.loadKeyByFile(reader);
        RSAPrivateKey privateKey = RSAEncrypt.loadPrivateKeyByStr(privateKeyStr);
        byte[] passwordByte = RSAEncrypt.decrypt(privateKey, Base64.getDecoder().decode(password));
        if(EmptyChecker.isEmpty(passwordByte)){
            return new BaseRespDTO(ResultCode.FAIL);
        }
        //查询用户信息
        User param = new User();
        param.setUserName(userName);
        User currentUser = this.userDao.getUserInfo(param);
        if(EmptyChecker.isEmpty(currentUser)){
            return new BaseRespDTO(ResultCode.USER_NAME_OR_PASSWORD_ERROR);
        }
        String realPassword = EncryptUtil.encryptSha512(new String(passwordByte) + currentUser.getUserName());
        //加密
        String originPassword = currentUser.getPassword();
        //密码比对
        if(!originPassword.equals(realPassword)){
            return new BaseRespDTO(ResultCode.USER_NAME_OR_PASSWORD_ERROR);
        }
        //写入redis
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setAppName("user-micriservice");
        tokenInfo.setUserId(currentUser.getId());
        String tokenId = this.tokenService.addTokenInfo(tokenInfo);
        //更新登录token
        currentUser.setLoginToken(tokenId);
        this.userDao.updateUserById(currentUser);
        BaseRespDTO baseRespDTO = new BaseRespDTO();
        baseRespDTO.setData(tokenId);
        return baseRespDTO;
    }

    @Override
    public BaseRespDTO getPublicKey() throws Exception {
        BaseRespDTO baseRespDTO = new BaseRespDTO();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("publicKey.keystore");
        InputStreamReader reader = new InputStreamReader(inputStream);
        baseRespDTO.setData(RSAEncrypt.loadKeyByFile(reader));
        return baseRespDTO;
    }

    @Override
    public BaseRespDTO getUserList(UserSearchRespDTO request) {
        PageInfo<UserPageVO> userPage = PageHelper.startPage(request.getPageIndex(),request.getPageSize())
                .doSelectPageInfo(() -> this.userDao.getUserListByPage(request));
        BaseRespDTO respDTO = new BaseRespDTO();
        respDTO.setData(userPage);
        return respDTO;
    }

    @Override
    public UserDetailRespDTO getUserInfo(String userId) {
        UserDetailRespDTO userDetailRespDTO = new UserDetailRespDTO();
        User params = new User();
        params.setId(userId);
        userDetailRespDTO.setData(this.userDao.getUserInfo(params));
        return userDetailRespDTO;
    }

    /**
     * 保存用户信息
     *
     * @param user
     * @return
     */
    @Override
    public BaseRespDTO saveUserInfo(User user) {
        //必填字段检测
        if(EmptyChecker.isEmpty(user.getUserName())){
            return new BaseRespDTO(ResultCode.PARAMS_NOT_FOUND);
        }
        //初始化密码、状态信息
        user.setIsAdmin(YesOrNoEnum.NO.getCode());
        user.setPassword(EncryptUtil.encryptSha512(INI_PASSWORD + user.getUserName()));
        int row = this.userDao.saveUser(user);
        if(row > 0){
            return new BaseRespDTO();
        }
        return new BaseRespDTO(ResultCode.FAIL);
    }

    /**
     * 根据id删除用户信息
     *
     * @param id
     * @return
     */
    @Override
    public BaseRespDTO deleteUserInfo(String id) {
        if(EmptyChecker.isEmpty(id)){
            return new BaseRespDTO(ResultCode.PARAMS_NOT_FOUND.getCode(),"id不能为空");
        }
        int effectRow = this.userDao.deleteUserById(id);
        if(effectRow == 1){
            return new BaseRespDTO();
        }
        return new BaseRespDTO(ResultCode.FAIL);
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @Override
    public BaseRespDTO updateUserInfo(User user) {
        if(EmptyChecker.isEmpty(user) || EmptyChecker.isEmpty(user.getId())){
            return new BaseRespDTO(ResultCode.PARAMS_NOT_FOUND);
        }
        int effectRow = this.userDao.updateUserById(user);
        if(effectRow == 1){
            return new BaseRespDTO();
        }
        return new BaseRespDTO(ResultCode.FAIL);
    }
}
