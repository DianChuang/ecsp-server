package com.dcstd.web.ecspserver.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class GlobalConfiguration {

    /* 版本信息 */
    @Value("${custom-config.app-info.app-name}")
    private String appName;//应用名称
    @Value("${custom-config.app-info.app-version}")
    private String appVersion;//版本号

    /* 小程序信息 */
    @Value("${custom-config.app-info.appid}")
    private String appid;//小程序appid
    @Value("${custom-config.app-info.secret}")
    private String secret;//小程序secret
    @Value("${custom-config.app-info.grant-type}")
    private String grantType;//授权类型

    /* URL信息 */
    @Value("${custom-config.url.wx-login}")
    private String wxLogin;//微信登录接口登录地址
    @Value("${custom-config.url.jw}")
    private String jw;//教务系统地址
    @Value("${custom-config.url.jw-get-public-key}")
    private String jwGetPublicKey;//获取公钥地址
    @Value("${custom-config.url.jw-cookie}")
    private String jwCookie;//获取cookie地址
    @Value("${custom-config.url.jw-kaptcha}")
    private String jwKaptcha;//验证码地址
    @Value("${custom-config.url.jw-login}")
    private String jwLogin;//教务系统登录地址
    @Value("${custom-config.url.jw-user-info}")
    private String jwUserInfo;//教务系统用户信息和获取

    /* 时间信息 */
    @Value("${custom-config.time.token-expire-time}")
    private int tokenExpireTime;//token超时时间(秒)
    @Value("${custom-config.time.cors-expire-time}")
    private int corsExpireTime;//当前跨域请求最大有效时长(秒)

    @Value("${custom-config.Encrypt.filePath}")
    private String filePath;//基础文件路径
    @Value("${custom-config.Encrypt.publicFileName}")
    private String publicFileName;//公钥文件名
    @Value("${custom-config.Encrypt.privateFileName}")
    private String privateFileName;//私钥文件名

}
