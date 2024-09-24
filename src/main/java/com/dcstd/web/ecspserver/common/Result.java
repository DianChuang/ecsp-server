package com.dcstd.web.ecspserver.common;

//Result.java
import com.dcstd.web.ecspserver.exception.CustomException;
import com.dcstd.web.ecspserver.exception.GlobalException;
import lombok.Data;

@Data
public class Result<T> {
    //状态码
    private static final int SUCCESS = 200;//成功
    private static final int ERROR_SERVER = 500;//服务器异常

    //统一返回值
    private int code = 200;
    private String msg = "操作成功";
    private String tips;
    private T data = null;

    /**
     * 构造方法
     * @param code 状态码
     * @param msg 消息
     */
    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

    /**
     * 构造方法
     * @param code 状态码
     * @param msg 消息
     * @param tips 提示词
     */
    public Result(int code, String msg, String tips) {
        this.code = code;
        this.msg = msg;
        this.tips = tips;
        this.data = null;
    }

    /**
     * 构造方法
     * @param code 状态码
     * @param msg 消息
     * @param data 数据
     */
    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 构造方法
     * @param code 状态码
     * @param msg 回执消息
     * @param tips 提示词
     * @param data 数据
     */
    public Result(int code, String msg, String tips, T data) {
        this.code = code;
        this.msg = msg;
        this.tips = tips;
        this.data = data;
    }


    //成功
    /**
     * 无参成功
     * @return Result
     */
    public static <T> Result success() {
        Result result = new Result(SUCCESS, "操作成功", "操作完成噜~");
        return result;
    }

    /**
     * 含提示词的无数据成功
     * @param tips 提示词
     * @return Result
     * @param <T> 可返回任意类型
     */
    public static <T> Result success(String tips) {
        Result result = new Result(SUCCESS, "操作成功", tips);
        return result;
    }

    /**
     * 含数据的无提示词成功
     * @param data 数据
     * @return Result
     * @param <T> 可返回任意类型
     */
    public static <T> Result success(T data) {
        Result result = new Result(SUCCESS, "操作成功", data);
        return result;
    }

    /**
     * 含提示词和数据的成功
     * @param tips 提示词
     * @param data 数据
     * @return Result
     * @param <T> 可返回任意类型
     */
    public static <T> Result success(String tips, T data) {
        Result result = new Result(SUCCESS, "操作成功", tips, data);
        return result;
    }

    /**
     * 含消息、提示词和数据的成功
     * @param msg 回执消息
     * @param tips 提示词
     * @param data 数据
     * @return Result
     * @param <T> 可返回任意类型
     */
    public static <T> Result success(String msg, String tips, T data) {
        Result result = new Result(SUCCESS, msg, tips, data);
        return result;
    }

    //异常

    /**
     * 异常
     * @return
     * @param <T>
     */
    public static <T> Result error() {
        Result result = new Result(ERROR_SERVER, "服务器异常", "寄，服务器出现问题了喵T^T");
        return result;
    }
    public static <T> Result error(String msg) {
        Result result = new Result(ERROR_SERVER, msg);
        return result;
    }
    public static <T> Result error(int code, String msg) {
        Result result = new Result(code, msg);
        return result;
    }
    public static <T> Result error(int code, String msg, String tips) {
        Result result = new Result(code, msg, tips);
        return result;
    }
    public static <T> Result error(CustomException customException) {
        Result result = new Result(customException.getCode(), customException.getMsg(), customException.getTips());
        return result;
    }
}
