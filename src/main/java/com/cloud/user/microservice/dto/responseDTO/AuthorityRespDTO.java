package com.cloud.user.microservice.dto.responseDTO;


import com.cloud.user.microservice.model.Authority;

import java.util.List;

/**
 * 权限返回报文
 *
 * @author Jon_China
 * @create 2017/11/23
 */
public class AuthorityRespDTO extends BaseRespDTO {
    private static final long serialVersionUID = 5128630144803532097L;

    private List<Authority> resultData;

    public List<Authority> getResultData() {
        return resultData;
    }

    public void setResultData(List<Authority> resultData) {
        this.resultData = resultData;
    }
}
