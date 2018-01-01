package com.cloud.user.microservice.service.impl;

import com.cloud.user.microservice.dao.IAuthorityDao;
import com.cloud.user.microservice.dto.AuthorityReqDTO;
import com.cloud.user.microservice.dto.BaseRespDTO;
import com.cloud.user.microservice.dto.MenuRespDTO;
import com.cloud.user.microservice.enums.ResultCode;
import com.cloud.user.microservice.model.Authority;
import com.cloud.user.microservice.model.vo.AuthoritiesVO;
import com.cloud.user.microservice.model.vo.AuthorityVO;
import com.cloud.user.microservice.service.AuthorityService;
import com.cloud.user.microservice.utils.EmptyChecker;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        if(EmptyChecker.notEmpty(request.getParentId())){
            request.setDeep(1);
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
     * @return
     */
    @Override
    public MenuRespDTO getAllMenus(String appName) {
        AuthorityReqDTO reqDTO = new AuthorityReqDTO();
        reqDTO.setAppName(appName);
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
}
