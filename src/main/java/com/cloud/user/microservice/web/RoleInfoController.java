package com.cloud.user.microservice.web;

import com.cloud.user.microservice.dto.BaseRespDTO;
import com.cloud.user.microservice.enums.ResultCode;
import com.cloud.user.microservice.service.RoleInfoService;
import com.cloud.user.microservice.utils.EmptyChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public String saveRoleInfo(@RequestParam(value = "roleName",defaultValue = "") String roleName){
        logger.info("the params of saveRoleInfo is : {} ",roleName);
        if(EmptyChecker.isEmpty(roleName)){
            return new BaseRespDTO(ResultCode.PARAMS_NOT_FOUND).toString();
        }
        try {
            BaseRespDTO baseRespDTO = this.roleInfoService.saveRoleInfo(roleName);
            String result = baseRespDTO.toString();
            logger.info("the result of saveRoleInfo is : {}",result);
            return result;
        }catch (Exception e){
            logger.error("exception occurred in saveRoleInfo",e);
            return new BaseRespDTO(ResultCode.ERROR).toString();
        }
    }
}
