package com.dcstd.web.ecspserver.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dcstd.web.ecspserver.config.GlobalConfiguration;
import com.dcstd.web.ecspserver.entity.User;
import com.dcstd.web.ecspserver.mapper.UserMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class TokenUtils {
    private static UserMapper staticUserMapper;
    private static GlobalConfiguration staticglobalConfiguration;

    @Resource
    UserMapper userMapper;

    @Resource
    GlobalConfiguration globalConfiguration;

    @PostConstruct
    public void init() {
        staticUserMapper = userMapper;
        staticglobalConfiguration = globalConfiguration;
    }

    /**
     * 生成token
     * @param uid 用户id
     * @param sign 加密密钥
     * @return token
     */
    public static String createToken(String uid, String sign) {
        if(staticglobalConfiguration.getTokenExpireTime() <= 0){
            return JWT.create().withAudience(uid)
                    .sign(Algorithm.HMAC256(sign));
        }
        return JWT.create().withAudience(uid)
                .withExpiresAt(DateUtil.offsetSecond(DateUtil.date(), staticglobalConfiguration.getTokenExpireTime()))
                .sign(Algorithm.HMAC256(sign));
    }

    /**
     * 获取当前用户信息
     * @return 用户信息
     */
    public static User getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("Authorization");
            if(StrUtil.isNotBlank(token)){
                String uid = JWT.decode(token).getAudience().get(0);
                return staticUserMapper.selectById(uid);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

}
