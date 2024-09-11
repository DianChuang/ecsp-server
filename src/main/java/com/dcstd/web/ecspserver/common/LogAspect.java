package com.dcstd.web.ecspserver.common;


import com.alibaba.fastjson.JSON;
import com.dcstd.web.ecspserver.utils.HttpUtils;
import com.dcstd.web.ecspserver.utils.LogUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Component
@Aspect
public class LogAspect {

    /*
    这里定义了一个切点用于匹配任何标注了 LogAnnotation 注解的方法。
    用于 AOP 中，对标注该注解的方法进行横切关注点的统一处理
     */
    @Pointcut("@annotation(com.dcstd.web.ecspserver.common.LogAnnotation)")
    public void pt() {}


    /*
    使用AOP（面向切面编程）在方法执行前后记录日志：
    1. 记录方法开始执行的时间。
    2. 执行被拦截的方法，并获取其返回结果。
    3. 计算方法执行耗时，并记录日志信息。
    4. 返回被拦截方法的结果。
     */
    @Around("pt()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        Long beginTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long time = System.currentTimeMillis() - beginTime;
        recordLog(joinPoint, time);
        return result;
    }

    /**
     * 记录日志
     * @param joinPoint 切入点
     * @param time 执行时长
     */
    public void recordLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation annotation = method.getAnnotation(LogAnnotation.class);
        // 获取接口信息
        // annotation.module() 模块
        // annotation.operator() 操作
        // annotation.msg() 备注
        String className = joinPoint.getTarget().getClass().getName(); // 获取类名
        String methodName = signature.getName(); // 获取方法名

        //获取参数
        Object[] args = joinPoint.getArgs();
        String params = "没有传递任何参数";
        if (args.length > 0) {
            try{
                params = JSON.toJSONString(args);
            } catch (Exception e) {
                params = "获取参数失败\n" + e + "\n" + Arrays.toString(joinPoint.getArgs());
            }

        }
        //ip来源
        HttpUtils httpUtils = new HttpUtils();
        String clientIp = httpUtils.getClientIp();

        //输出日志
        LogUtils.successLog(annotation.module(), annotation.operator(), (className + "." + methodName + "()"), params, time + " ms", annotation.msg(), clientIp);
    }
}
