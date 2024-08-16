package com.dcstd.web.ecspserver.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class GlobalConfiguration {

    /* 版本信息 */
    @Value("${custom-config.app-info.app-name}")
    private String appName;
    @Value("${custom-config.app-info.app-version}")
    private String appVersion;

    /* 小程序信息 */
    @Value("${custom-config.app-info.appid}")
    private String appid;
    @Value("${custom-config.app-info.secret}")
    private String secret;
    @Value("${custom-config.app-info.grant-type}")
    private String grantType;

    /* URL信息 */
    @Value("${custom-config.url.wx-login}")
    private String wxLogin;
    @Value("${custom-config.url.jw}")
    private String jw;
    @Value("${custom-config.url.jw-get-public-key}")
    private String jwGetPublicKey;
    @Value("${custom-config.url.jw-cookie}")
    private String jwCookie;
    @Value("${custom-config.url.jw-kaptcha}")
    private String jwKaptcha;
    @Value("${custom-config.url.jw-login}")
    private String jwLogin;

    /* 时间信息 */
    @Value("${custom-config.time.token-expire-time}")
    private int tokenExpireTime;


}
