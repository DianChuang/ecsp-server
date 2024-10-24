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
    @Value("${custom-config.app-info.grant-type-login}")
    private String grantTypeLogin;//授权类型登录
    @Value("${custom-config.app-info.grant-type-token}")
    private String grantTypeToken;//授权类型Token

    /* URL信息 */
    @Value("${custom-config.url.base-url}")
    private String baseUrl;//基础地址
    @Value("${custom-config.url.file-url}")
    private String fileUrl;//文件地址
    @Value("${custom-config.url.wx-login}")
    private String wxLogin;//微信登录接口登录地址
    @Value("${custom-config.url.wx-token}")
    private String wxToken;//微信token获取地址
    @Value("${custom-config.url.wx-user-info}")
    private String wxUserInfo;//微信用户信息地址
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
    @Value("${custom-config.url.jw-transcript-info}")
    private String jwTranscriptInfo;
    @Value("${custom-config.url.jw-exam-info}")
    private String jwExamInfo;
    @Value("${custom-config.url.jw-curriculum-info}")
    private String jwCurriculumInfo;
    @Value("${custom-config.url.jw-curriculum-table-info}")
    private String jwCurriculumTableInfo;

    /* 时间信息 */
    @Value("${custom-config.time.token-expire-time}")
    private int tokenExpireTime;//token超时时间(秒)
    @Value("${custom-config.time.cors-expire-time}")
    private int corsExpireTime;//当前跨域请求最大有效时长(秒)

    /* 加密信息 */
    @Value("${custom-config.Encrypt.filePath}")
    private String filePath;//基础文件路径
    @Value("${custom-config.Encrypt.publicFileName}")
    private String publicFileName;//公钥文件名
    @Value("${custom-config.Encrypt.privateFileName}")
    private String privateFileName;//私钥文件名

    /* 默认信息 */
    @Value("${custom-config.default-info.file-user-avatar}")
    private String FileUserAvatar;//默认头像地址
    @Value("${custom-config.default-info.file-active-cover}")
    private String FileActiveCover;
    @Value("${custom-config.default-info.max-application-number}")
    private int maxApplicationNumber;//默认最大申请数
    @Value("${custom-config.default-info.max-vote-number-per-day}")
    private int maxVoteNumberPerDay;

    /* 路径配置 */
    @Value("${custom-config.path.path-image-user}")
    private String pathImageUser;
    @Value("${custom-config.path.path-image-cover}")
    private String pathImageCover;
    @Value("${custom-config.path.path-image}")
    private String pathImage;
}
