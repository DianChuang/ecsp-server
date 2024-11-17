package com.dcstd.web.ecspserver.exception;

//GlobalExceptionHandle.java
import com.dcstd.web.ecspserver.common.Result;
import com.dcstd.web.ecspserver.utils.LogUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public <T> Result handle(Exception e) {
        if(e instanceof CustomException){
            CustomException globalCustomException = (CustomException) e;
            LogUtils.thrExceptLog(globalCustomException, e);
            return Result.error(globalCustomException);
        }
        LogUtils.thrExceptLog(null, e);
        return Result.error(500, "服务器内部异常："+e.getMessage(), "服务器内部异常");
    }
}
