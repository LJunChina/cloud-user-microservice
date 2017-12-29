package com.cloud.user.microservice.service.impl;

import com.cloud.user.microservice.dao.IUserDao;
import com.cloud.user.microservice.dto.BaseRespDTO;
import com.cloud.user.microservice.dto.UserDetailRespDTO;
import com.cloud.user.microservice.dto.UserSearchRespDTO;
import com.cloud.user.microservice.enums.ResultCode;
import com.cloud.user.microservice.model.TokenInfo;
import com.cloud.user.microservice.model.User;
import com.cloud.user.microservice.service.TokenService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.cloud.user.microservice.service.UserService;
import com.cloud.user.microservice.utils.EmptyChecker;
import com.cloud.user.microservice.utils.RSAEncrypt;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.interfaces.RSAPrivateKey;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
        byte[] passwordByte = RSAEncrypt.decrypt(privateKey,Base64.decode(password));
        if(EmptyChecker.isEmpty(passwordByte)){
            return new BaseRespDTO(ResultCode.FAIL);
        }
        String realPassword = DigestUtils.sha256Hex(new String(passwordByte));
        //查询用户信息
        User param = new User();
        param.setUserName(userName);
        User currentUser = this.userDao.getUserInfo(param);
        if(EmptyChecker.isEmpty(currentUser)){
            return new BaseRespDTO(ResultCode.USER_NAME_OR_PASSWORD_ERROR);
        }
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
        PageInfo<User> userPage = PageHelper.startPage(request.getPageNum(),request.getPageSize())
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
}
