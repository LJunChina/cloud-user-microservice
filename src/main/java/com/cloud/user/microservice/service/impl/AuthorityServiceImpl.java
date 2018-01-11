package com.cloud.user.microservice.service.impl;

import com.cloud.common.dto.BaseRespDTO;
import com.cloud.common.enums.ResultCode;
import com.cloud.common.enums.YesOrNoEnum;
import com.cloud.common.util.EmptyChecker;
import com.cloud.user.microservice.dao.IAuthorityDao;
import com.cloud.user.microservice.dto.requestDTO.AllocationAuthRequest;
import com.cloud.user.microservice.dto.requestDTO.AuthorityReqDTO;
import com.cloud.user.microservice.dto.responseDTO.MenuRespDTO;
import com.cloud.user.microservice.enums.AuthorityItemTypeEnum;
import com.cloud.user.microservice.model.Authority;
import com.cloud.user.microservice.model.vo.AuthoritiesVO;
import com.cloud.user.microservice.model.vo.AuthorityVO;
import com.cloud.user.microservice.service.AuthorityService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Jon_China
 * @create 2017/11/18
 */
@Service(value = "authorityService")
public class AuthorityServiceImpl implements AuthorityService {


    @Autowired
    private IAuthorityDao authorityDao;

    @Override
    public BaseRespDTO saveAuthority(AuthorityReqDTO request) {
        request.setId(UUID.randomUUID().toString());
        request.setAvailable(YesOrNoEnum.YES.getCode());
        if(EmptyChecker.notEmpty(request.getParentId())){
            request.setDeep(1);
        }
        //若为操作类型,则设置父级id为root节点的id
        if(AuthorityItemTypeEnum.OPERATION_TYPE.getCode().equals(request.getItemType()) || EmptyChecker.isEmpty(request.getParentId())){
            AuthorityReqDTO params = new AuthorityReqDTO();
            params.setName("root");
            Authority root = this.authorityDao.getAuthorityInfo(params);
            request.setParentId(root.getId());
        }
        int row = this.authorityDao.addAuthority(request);
        if(1 == row){
            return new BaseRespDTO();
        }
        return new BaseRespDTO(ResultCode.FAIL);
    }

    /**
     * 获取系统所有菜单信息
     *
     * @param appName 系统名称
     * @param userId 当前登录用户id
     * @return
     */
    @Override
    public MenuRespDTO getAllMenus(String appName,String userId) {
        AuthorityReqDTO reqDTO = new AuthorityReqDTO();
        reqDTO.setAppName(appName);
        reqDTO.setItemType(AuthorityItemTypeEnum.MENU_TYPE.getCode());
        //查询用户角色信息
        if(!"874b0e21-dd87-49ee-b0b9-142cc365618c".equals(userId)){
            reqDTO.setUserId(userId);
        }
        MenuRespDTO baseRespDTO = new MenuRespDTO();
        buildMenus(baseRespDTO,this.authorityDao.getAllAuthorities(reqDTO));
        return baseRespDTO;
    }

    /**
     * 构造菜单树
     * @param respDTO
     * @param items
     */
    private void buildMenus(MenuRespDTO respDTO, List<Authority> items){
        if(EmptyChecker.isEmpty(items)){
            return;
        }
        List<AuthorityVO> resultData = new ArrayList<>();
        items.forEach(i -> {
            AuthorityVO authorityVO = new AuthorityVO();
            authorityVO.setItem(i);
            //过滤出child
            //只添加顶级菜单
            if(i.getDeep().equals(0)){
                authorityVO.setChild(items.stream()
                        .filter(s -> s.getParentId().equals(i.getId()))
                        .collect(Collectors.toList()));
                resultData.add(authorityVO);
            }
        });
        respDTO.setResultData(resultData);
    }

    /**
     * 系统权限信息分页查询
     *
     * @param request
     * @return
     */
    @Override
    public BaseRespDTO getAllAuthoritiesByPage(AuthorityReqDTO request) {
        PageInfo<AuthoritiesVO> result = PageHelper.startPage(request.getPageIndex()
                ,request.getPageSize()).doSelectPageInfo(() -> this.authorityDao.getAllAuthorityInfo(request));
        BaseRespDTO respDTO = new BaseRespDTO();
        respDTO.setData(result);
        return respDTO;
    }

    /**
     * 角色权限分配
     *
     * @param roleId
     * @param authIds
     * @return
     */
    @Override
    public BaseRespDTO allocationAuth(String roleId, String authIds) {
        if(EmptyChecker.isEmpty(roleId) || EmptyChecker.isEmpty(authIds)){
            return new BaseRespDTO(ResultCode.PARAMS_NOT_FOUND);
        }
        //数据处理
        List<String> ids = Arrays.asList(authIds.split(","));
        AllocationAuthRequest request = new AllocationAuthRequest();
        request.setRoleId(roleId);
        request.setAuthIds(ids);
        int row = this.authorityDao.allocationAuth(request);
        if(row >= 1){
            return new BaseRespDTO();
        }
        return new BaseRespDTO(ResultCode.FAIL);
    }

    /**
     * 查询当前登录用户权限
     *
     * @param userId
     * @param appId
     * @param uri
     * @return
     */
    @Override
    public BaseRespDTO checkUserPrivileges(String userId, String appId,String uri) {
        List<Authority> list = this.authorityDao.getUserPrivileges(userId,appId);
        if(EmptyChecker.isEmpty(uri) || EmptyChecker.isEmpty(list)){
            return new BaseRespDTO(ResultCode.NO_PRIVILEGE);
        }
        //权限列表中是否存在该请求uri
        long resultCount = list.stream().filter(l -> uri.equals(l.getItemUri())).count();
        if(resultCount == 0L){
            return new BaseRespDTO(ResultCode.NO_PRIVILEGE);
        }
        return new BaseRespDTO();
    }
}
