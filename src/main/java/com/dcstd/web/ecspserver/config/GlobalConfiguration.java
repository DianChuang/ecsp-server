package com.dcstd.web.ecspserver.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Data
@Component
public class GlobalConfiguration {

    /* 版本信息 */
    @Value("${custom-config.app-info.appName}")
    private String appName;
    @Value("${custom-config.app-info.appVersion}")
    private String appVersion;

    /* 小程序信息 */
    @Value("${custom-config.app-info.appid}")
    private String appid;
    @Value("${custom-config.app-info.secret}")
    private String secret;
    @Value("${custom-config.app-info.grantType}")
    private String grantType;

    /* URL信息 */
    @Value("${custom-config.url.wxLoginURL}")
    private String wxLoginURL;

}

