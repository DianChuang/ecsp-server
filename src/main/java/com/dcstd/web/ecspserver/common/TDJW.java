package com.dcstd.web.ecspserver.common;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.dcstd.web.ecspserver.config.GlobalConfiguration;
import com.dcstd.web.ecspserver.exception.CustomException;
import com.dcstd.web.ecspserver.utils.B64;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;

@Component
public class TDJW {
    //TDJW = 通达教务 :P

    static GlobalConfiguration staticGlobalConfiguration;

    @Resource
    GlobalConfiguration GlobalConfiguration;

    //Cookie
    List<HttpCookie> cookies = null;
    //教务系统请求头
    HashMap <String, String> jwHeader = new HashMap<>();

    //公钥请求
    HttpResponse publicKeyRequest;

    //初始化请求
    HttpResponse initRequest;

    /**
     * 全局初始化
     */
    @PostConstruct
    public void init() {
        staticGlobalConfiguration = GlobalConfiguration;
        jwHeader = getJwHeader();
    }

    /**
     * 获取请求头
     * @return 请求头
     */
    public HashMap<String, String> getJwHeader() {
        HashMap<String, String> jwHeader = new HashMap<>();
        jwHeader.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        jwHeader.put("Accept-Encoding", "gzip, deflate");
        jwHeader.put("Accept-Language", "zh-CN,zh;q=0.9");
        jwHeader.put("Cache-Control", "max-age=0");

        return jwHeader;
    }

    /**
     * 获取初始化请求
     * @return 初始化请求
     */
    public HttpResponse getInitRequest() {
        initRequest = HttpUtil.createPost(staticGlobalConfiguration.getJw() + staticGlobalConfiguration.getJwLogin()).addHeaders(jwHeader).execute();;//初始化请求
        return initRequest;
    }

    /**
     * 获取csrftoken
     * @param initRequest 初始化请求
     * @return csrftoken值
     */
    public String getCsrftoken(String initRequest) {
        return initRequest.split("<input type=\"hidden\" id=\"csrftoken\" name=\"csrftoken\" value=\"", 66)[1].split("\"/>")[0];
    }

    /**
     * 获取公钥
     * @param cookies cookies
     * @param jwHeader 请求头
     * @return 公钥键值对
     */
    public HashMap<String, String> getPublicKey(List<HttpCookie> cookies, HashMap<String, String> jwHeader) {
        publicKeyRequest = HttpUtil.createGet(staticGlobalConfiguration.getJw() + staticGlobalConfiguration.getJwGetPublicKey()).addHeaders(jwHeader).cookie(cookies).execute();
        JSONObject jsonObject = JSONObject.parseObject(publicKeyRequest.body());
        HashMap<String, String> publicKey = new HashMap<>();
        publicKey.put("modulus", jsonObject.getString("modulus"));
        publicKey.put("exponent", jsonObject.getString("exponent"));

        return publicKey;
    }

    /**
     * 密码加密
     * @param password 密码
     * @param modulus 公钥
     * @param exponent e
     * @return 加密后的密码
     */
    public String encryptPassword(String password, String modulus, String exponent) {
        //加密后的密码
        String encryptPassword = "";
        try {
            encryptPassword = TDJWRSAEncode.RSAEncrypt(password, B64.b64tohex(modulus), B64.b64tohex(exponent));
            encryptPassword = B64.hex2b64(encryptPassword);
        } catch (Exception e) {
            throw new CustomException(500, "教务系统密码加密转换失败", e.getMessage());
        }
        return encryptPassword;
    }

    /**
     * 登录并获取登录数据
     * @param username 用户名
     * @param password 密码
     * @return 用户信息
     */
    public HttpResponse login(String username, String password) {
        jwHeader = getJwHeader();
        HttpResponse initRequest = getInitRequest();
        cookies = initRequest.getCookies();
        String csrftoken = getCsrftoken(initRequest.body());
        HashMap<String, String> publicKey = getPublicKey(cookies, jwHeader);
        String mm = encryptPassword(password, publicKey.get("modulus"), publicKey.get("exponent"));

        //登录请求
        HttpResponse userInfoRequest;
        try (HttpResponse loginRequest = HttpUtil.createPost(staticGlobalConfiguration.getJw() + staticGlobalConfiguration.getJwLogin())
                .addHeaders(jwHeader)
                .cookie(cookies)
                .form("csrftoken", csrftoken)
                .form("yhm", username)
                .form("mm", mm)
                .form("mm", mm)
                .execute()) {
            //登录成功后信息
            userInfoRequest = HttpUtil.createGet(staticGlobalConfiguration.getJw() + staticGlobalConfiguration.getJwUserInfo())
                    .addHeaders(jwHeader)
                    .cookie(loginRequest.getCookies())
                    .execute();
        }
        return userInfoRequest;
    }

}
