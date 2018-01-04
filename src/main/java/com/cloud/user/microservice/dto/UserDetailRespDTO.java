package com.cloud.user.microservice.dto;

import com.cloud.user.microservice.model.User;
import com.cloud.user.microservice.model.vo.UserInfoVO;
import net.sf.cglib.beans.BeanCopier;

/**
 * 用户详情返回报文
 *
 * @author Jon_China
 * @create 2017/11/18
 */
public class UserDetailRespDTO extends BaseRespDTO {
    private static final long serialVersionUID = 7934128005931334749L;


    public void setData(User data) {
        UserInfoVO userInfoVO = new UserInfoVO();
        final BeanCopier copier = BeanCopier.create(User.class,UserInfoVO.class,false);
        copier.copy(data,userInfoVO,null);
        super.setData(userInfoVO);
    }
}
