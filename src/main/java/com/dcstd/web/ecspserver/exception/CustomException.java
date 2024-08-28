package com.dcstd.web.ecspserver.exception;

import lombok.Getter;

//GlobalCustomException.java
@Getter
public class CustomException extends RuntimeException {
    private int code = 500;
    private String msg = "服务器异常";
    private String tips = "服务器异常";

    public CustomException() {
        super();
    }
    public CustomException(GlobalException globalException)
    {
        super();
        this.code = globalException.getCode();
        this.msg = globalException.getMsg();
        this.tips = globalException.getTips();
    }
    public CustomException(String msg)
    {
        super();
        this.msg = msg;
    }
    public CustomException(int code, String msg)
    {
        super();
        this.code = code;
        this.msg = msg;
    }
    public CustomException(int code, String msg, String tips)
    {
        super();
        this.code = code;
        this.msg = msg;
        this.tips = tips;
    }

}
