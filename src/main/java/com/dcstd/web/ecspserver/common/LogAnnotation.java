package com.dcstd.web.ecspserver.common;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    String module() default ""; // 模块
    String operator() default ""; // 操作
    String msg() default "";// 备注消息
}
