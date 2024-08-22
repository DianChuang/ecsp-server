package com.dcstd.web.ecspserver.common;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.dcstd.web.ecspserver.entity.User;
import com.dcstd.web.ecspserver.exception.CustomException;
import com.dcstd.web.ecspserver.exception.GlobalException;
import com.dcstd.web.ecspserver.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class JWTInterceptor implements HandlerInterceptor {

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean preHandle(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Object handler) {
        String token = request.getHeader("Authorization");
        if(StrUtil.isBlank(token)){
            token = request.getParameter("Authorization");
        }

        // 接口含有绕过token注解
        if(handler instanceof HandlerMethod){
            AuthAccess authAccess = ((HandlerMethod) handler).getMethodAnnotation(AuthAccess.class);
            if(authAccess != null){
                return true;
            }
        }
        // 校验token
        // token为空
        if(StrUtil.isBlank(token)){
            throw new CustomException(GlobalException.ERROR_NOT_LOGIN);
        }
        // 获取token中的用户信息
        String uid;
        try {
             uid = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException e) {
            throw new CustomException(GlobalException.ERROR_TOKEN);
        }
        // 根据token中的uid查询数据库用户信息
        User db_user = userMapper.selectUserById(uid);
        if(db_user == null){
            throw new CustomException(GlobalException.ERROR_TOKEN);
        }
        // 用户密码加签名验证token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(db_user.getPassword())).build();
        try {
            jwtVerifier.verify(token);//验证token
        } catch (JWTVerificationException e) {
            throw new CustomException(GlobalException.ERROR_TOKEN);
        }
        return true;
    }
}
