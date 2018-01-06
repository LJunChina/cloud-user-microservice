package com.cloud.user.microservice.dto.responseDTO;

import com.cloud.user.microservice.model.vo.AuthorityVO;

import java.util.List;

/**
 * 菜单返回报文
 *
 * @author Jon_China
 * @create 2017/12/17
 */
public class MenuRespDTO extends BaseRespDTO {
    private static final long serialVersionUID = -7439474630330583854L;

    private List<AuthorityVO> resultData;

    public List<AuthorityVO> getResultData() {
        return resultData;
    }

    public void setResultData(List<AuthorityVO> resultData) {
        this.resultData = resultData;
    }
}
