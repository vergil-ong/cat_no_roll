package com.github.ong.vo.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultVo {

    public static final Integer STATUS_SUCCESS = 200;
    public static final Integer STATUS_FAIL = 500;

    private Integer status;

    private String message;

    public Object data;

    public static ResultVo success() {
        ResultVo resultVo = new ResultVo();
        resultVo.setStatus(STATUS_SUCCESS);
        resultVo.setMessage("OK");
        return resultVo;
    }

    public static ResultVo fail() {
        ResultVo resultVo = new ResultVo();
        resultVo.setStatus(STATUS_FAIL);
        return resultVo;
    }
}
