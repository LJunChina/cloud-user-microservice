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
import net.sf.cglib.beans.BeanCopier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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
        BeanCopier copier = BeanCopier.create(Authority.class,AuthorityVO.class,false);
        List<AuthorityVO> originList = new ArrayList<>();
        items.forEach(o ->{
            AuthorityVO temp = new AuthorityVO();
            copier.copy(o,temp,null);
            originList.add(temp);
        });
        List<AuthorityVO> resultData = new ArrayList<>();
        originList.forEach(i -> {
            if(i.getDeep().equals(0)){
                resultData.add(findChildren(i,originList));
            }
        });
        respDTO.setResultData(resultData);
    }

    /**
     * 递归查找子节点
     * @param authoritiesVO
     * @param menus
     * @return
     */
    private AuthorityVO findChildren(AuthorityVO authoritiesVO,List<AuthorityVO> menus){
        for (AuthorityVO vo : menus){
            if(authoritiesVO.getId().equals(vo.getParentId())){
                if(EmptyChecker.isEmpty(authoritiesVO.getChild())){
                    authoritiesVO.setChild(new ArrayList<>());
                }
                authoritiesVO.getChild().add(findChildren(vo,menus));
            }
        }
        return authoritiesVO;
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
        if(EmptyChecker.notEmpty(request.getRoleId())){
            //查询当前角色的权限信息
            List<Authority> authorityList = this.authorityDao.getAuthoritiesByRoleId(request.getRoleId());
            //设置包含角色权限的对应记录为选中
            if(EmptyChecker.notEmpty(authorityList)){
                List<String> authIds = authorityList.stream().map(Authority::getId).collect(Collectors.toList());
                result.getList().stream().filter(f -> authIds.contains(f.getId())).forEach(r -> r.setSelected(YesOrNoEnum.YES.getCode()));
            }

        }
        BaseRespDTO respDTO = new BaseRespDTO();
        respDTO.setData(result);
        return respDTO;
    }

    /**
     * 角色权限分配
     *
     * @param roleId
     * @param authIds
     * @param itemType
     * @return
     */
    @Override
    public BaseRespDTO allocationAuth(String roleId, String authIds,String itemType) {
        if(EmptyChecker.isEmpty(roleId) || EmptyChecker.isEmpty(authIds)){
            return new BaseRespDTO(ResultCode.PARAMS_NOT_FOUND);
        }
        //删除以前的数据
        this.authorityDao.deleteAuthoritiesByRoleId(Collections.singletonList(roleId),itemType);
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
        //临时处理
        if("874b0e21-dd87-49ee-b0b9-142cc365618c".equals(userId)){
            return new BaseRespDTO();
        }
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

    /**
     * 根据id删除权限/菜单信息
     *
     * @param id
     * @return
     */
    @Override
    public BaseRespDTO deleteAuthorityById(String id) {
        if(EmptyChecker.isEmpty(id)){
            return new BaseRespDTO(ResultCode.PARAMS_NOT_FOUND.getCode(),"id不能为空");
        }
        int effectRow = this.authorityDao.deleteAuthorityById(id);
        if(effectRow == 1){
            return new BaseRespDTO();
        }
        return new BaseRespDTO(ResultCode.FAIL);
    }


    /**
     * 更新权限/菜单信息
     *
     * @param authority
     * @return
     */
    @Override
    public BaseRespDTO updateAuthority(Authority authority) {
        if(EmptyChecker.isEmpty(authority) || EmptyChecker.isEmpty(authority.getId())){
            return new BaseRespDTO(ResultCode.PARAMS_NOT_FOUND);
        }
        Authority newAuthority = this.authorityDao.getAuthoritiesById(authority.getId());
        if(EmptyChecker.notEmpty(authority.getName())){
            newAuthority.setName(authority.getName());
        }
        if(EmptyChecker.notEmpty(authority.getAppName())){
            newAuthority.setAppName(authority.getAppName());
        }
        if(EmptyChecker.notEmpty(authority.getAvailable())){
            newAuthority.setAvailable(authority.getAvailable());
        }
        if(EmptyChecker.notEmpty(authority.getIcon())){
            newAuthority.setIcon(authority.getIcon());
        }
        if(EmptyChecker.notEmpty(authority.getItemType())){
            newAuthority.setItemType(authority.getItemType());
        }
        if(EmptyChecker.notEmpty(authority.getItemUri())){
            newAuthority.setItemUri(authority.getItemUri());
        }
        if(EmptyChecker.notEmpty(authority.getStyle())){
            newAuthority.setStyle(authority.getStyle());
        }
        if(EmptyChecker.notEmpty(authority.getParentId())){
            newAuthority.setParentId(authority.getParentId());
        }
        if(EmptyChecker.notEmpty(authority.getDeep())){
            newAuthority.setDeep(authority.getDeep());
        }
        if(EmptyChecker.notEmpty(authority.getSortNum())){
            newAuthority.setSortNum(authority.getSortNum());
        }
        int effectRow = this.authorityDao.updateAuthority(newAuthority);
        if(effectRow == 1){
            return new BaseRespDTO();
        }
        return new BaseRespDTO(ResultCode.FAIL);
    }

    /**
     * 根據id查询菜单/权限信息
     *
     * @param id
     * @return
     */
    @Override
    public BaseRespDTO getAuthorityById(String id) {
        if(EmptyChecker.isEmpty(id)){
            return new BaseRespDTO(ResultCode.PARAMS_NOT_FOUND.getCode(),"id不能为空");
        }
        AuthorityReqDTO request = new AuthorityReqDTO();
        request.setId(id);
        Authority authority = this.authorityDao.getAuthorityInfo(request);
        BaseRespDTO baseRespDTO = new BaseRespDTO();
        baseRespDTO.setData(authority);
        return baseRespDTO;
    }
}
