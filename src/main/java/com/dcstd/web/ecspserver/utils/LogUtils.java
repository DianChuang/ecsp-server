package com.dcstd.web.ecspserver.utils;

import cn.hutool.core.date.DateTime;
import com.dcstd.web.ecspserver.exception.CustomException;
import jakarta.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;

public class LogUtils {
    private static final String LOG_RED = "\u001B[31m"; //红色
    private static final String LOG_BASIC = "\33[0m"; //默认
    private static final String LOG_YELLOW = "\u001B[33m"; //黄色
    private static final String LOG_GREEN = "\u001B[42m"; //绿色(背景色)
    private static final String LOG_BLUE = "\u001B[46m"; //蓝色(背景色)

    /**
     * 日志添加颜色
     * @param msg 消息
     * @param color 颜色
     * @return (String)
     */
    private static String ColorLog(String msg, String color) {
        return color + msg + LOG_BASIC;
    }

    /**
     * 异常日志
     * @param globalCustomException 自定义异常
     * @param e 原始异常
     */
    public static void thrExceptLog(@Nullable CustomException globalCustomException, Exception e) {
        System.out.println("——————————————" + ColorLog(" E R R O R ", LOG_RED) + "——————————————");
        if(globalCustomException!=null) {

            System.out.println(ColorLog("错误码: ", LOG_RED) + globalCustomException.getCode());
            System.out.println(ColorLog("异常信息: ", LOG_RED) + globalCustomException.getMsg());
            System.out.println(ColorLog("异常提示: ", LOG_RED) + globalCustomException.getTips());
            System.out.println(ColorLog("异常堆栈(信息较长,已折叠为):\n", LOG_RED) + Arrays.toString(globalCustomException.getStackTrace()));
        } else {
            System.out.println("异常堆栈(信息较长,已折叠为):\n" + LOG_BASIC + Arrays.toString(e.getStackTrace()));
        }
        System.out.println(ColorLog("原始异常", LOG_RED) + ColorLog("(非主动抛出异常时存在)", LOG_YELLOW) + ColorLog(": ", LOG_RED) + e.getMessage());
        System.out.println(ColorLog("记录日期：", LOG_YELLOW) + DateTime.now().toString("yyyy-MM-dd hh:mm:ss"));
        System.out.println("———————————————————————————————————————");
    }

    /**
     * 异常日志
     * @param Title 主题
     * @param msg 消息
     * @param e 错误信息
     */
    public static void thrExceptLog(String Title, String msg, Exception e) {
        System.out.println("——————————————" + ColorLog(" E R R O R ", LOG_RED) + "——————————————");
        System.out.println(ColorLog("异常主题: ", LOG_RED) + Title);
        System.out.println(ColorLog("异常提示: ", LOG_RED) + msg);
        System.out.println(ColorLog("原始异常", LOG_RED) + ColorLog("(非主动抛出异常时存在)", LOG_YELLOW) + ColorLog(": ", LOG_RED) + e.getMessage());
        System.out.println(ColorLog("记录日期：", LOG_YELLOW) + DateTime.now().toString("yyyy-MM-dd hh:mm:ss"));
        System.out.println("———————————————————————————————————————");
    }

    /**
     * 一般成功日志
     * @param Title 主题
     * @param msg 消息
     */
    public static void successLog(Object Title, Object msg) {
        System.out.println("——————————————" + ColorLog(" S U C C E S S ", LOG_GREEN) + "——————————————");
        System.out.println(ColorLog("接口：", LOG_YELLOW) + Title);
        System.out.println(ColorLog("消息：", LOG_YELLOW) + msg);
        System.out.println("———————————————————————————————————————————");
    }

    /**
     * AOP日志记录
     * @param module 模块
     * @param operator 功能
     * @param method 方法
     * @param params 参数
     * @param executeTime 执行时间
     * @param msg 备注
     */
    public static void successLog(String module, String operator, String method, Object params, String executeTime, @Nullable String msg, @Nullable String ip) {
        System.out.println("——————————————" + ColorLog(" S U C C E S S ", LOG_GREEN) + "——————————————");
        System.out.println(ColorLog("执行模块：", LOG_YELLOW) + ColorLog(module, LOG_RED));
        System.out.println(ColorLog("执行操作：", LOG_YELLOW) + ColorLog(operator, LOG_RED));
        System.out.println(ColorLog("执行方法：", LOG_YELLOW) + method);
        System.out.println(ColorLog("来源-ip：", LOG_YELLOW) + ip);
        System.out.println(ColorLog("执行参数：", LOG_YELLOW) + params);
        System.out.println(ColorLog("执行时间：", LOG_YELLOW) + executeTime);
        System.out.println(ColorLog("备注消息：", LOG_YELLOW) + (!Objects.equals(msg, "") ? msg : "无"));
        System.out.println(ColorLog("记录日期：", LOG_YELLOW) + DateTime.now().toString("yyyy-MM-dd hh:mm:ss"));
        System.out.println("———————————————————————————————————————————");
    }



}
