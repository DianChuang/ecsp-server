package com.dcstd.web.ecspserver.common;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.dcstd.web.ecspserver.config.GlobalConfiguration;
import com.dcstd.web.ecspserver.exception.CustomException;
import com.dcstd.web.ecspserver.mapper.JWMapper;
import com.dcstd.web.ecspserver.utils.B64;
import com.dcstd.web.ecspserver.utils.RSAUtils;
import com.dcstd.web.ecspserver.utils.TokenUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class TDJW {
    //TDJW = 通达教务 :P

    static GlobalConfiguration staticGlobalConfiguration;
    static JWMapper staticJWMapper;

    @Resource
    GlobalConfiguration GlobalConfiguration;

    @Resource
    JWMapper jwMapper;

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
        staticJWMapper = jwMapper;
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
        return initRequest.split("<input type=\"hidden\" id=\"csrftoken\" name=\"csrftoken\" value=\"")[1].split("\"/>")[0].split(",")[0];
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
    @LogAnnotation(module = "TDJW", operator = "教务系统登录")
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
                .form("_t=", System.currentTimeMillis())//不是哥们你是铸币吧这么啥比的错误都能犯(.form写成.body)
                .execute()) {
            //登录成功后信息
            userInfoRequest = HttpUtil.createGet(staticGlobalConfiguration.getJw() + staticGlobalConfiguration.getJwUserInfo())
                    .addHeaders(jwHeader)
                    .cookie(loginRequest.getCookies())
                    .execute();
            if(!userInfoRequest.isOk()) {
                throw new CustomException(userInfoRequest.getStatus(), "教务系统登录失败");
            }
        } catch (Exception e) {
            throw new CustomException(500, "教务系统登录失败", e.getMessage());
        }
        return userInfoRequest;
    }

    /**
     * 获取登录成功后的cookies
     * @param username 用户名
     * @param password 密码
     * @return 返回值
     */
    public List<HttpCookie> getCookies(String username, String password) {
        try {
            HttpResponse userInfoRequest = login(username, password);
            if(!userInfoRequest.isOk()) { //如果登录失败
                throw new CustomException(userInfoRequest.getStatus(), "教务系统登录失败");
            }

            //否则存入数据库并返回
            if(staticJWMapper.getCookies(TokenUtils.getCurrentUser().getId()) == null) {
                staticJWMapper.insertJWInfo(TokenUtils.getCurrentUser().getId(), userInfoRequest.getCookies().toString());
            } else {
                try {
                    staticJWMapper.updateCookiesData(TokenUtils.getCurrentUser().getId(), userInfoRequest.getCookies().toString());
                } catch (Exception e) {
                    throw new CustomException(e.getMessage());
                }
            }
            return userInfoRequest.getCookies();
        } catch (CustomException e) {
            throw new CustomException(e.getCode(), e.getMsg(), e.getTips());
        }
    }

    public void updateDatabaseCookie(List<HttpCookie> cookies) {
        staticJWMapper.insertJWInfo(TokenUtils.getCurrentUser().getId(), cookies.toString());
    }

    /**
     * 获取本地数据库中的cookie
     * @return
     */
    public List<HttpCookie> getCookies() {
        List<HttpCookie> cookies = new ArrayList<>();
        String cookieStr;
        try {
            cookieStr = staticJWMapper.getCookies(TokenUtils.getCurrentUser().getId());
            if(cookieStr == null) {
                try {
                    return getCookies(TokenUtils.getCurrentUser().getAccount(), RSAUtils.decrypt(TokenUtils.getCurrentUser().getPassword()));
                } catch (Exception ex) {
                    throw new CustomException("在线获取cookie异常");
                }
            }
            String[] cookiePairs = cookieStr.replace("[", "").replace("]", "").split(", "); // 分割字符串
            for (String cookiePair : cookiePairs) {
                String[] nameValue = cookiePair.split("=", 2); // 按等号分割，但只分割一次
                if (nameValue.length == 2) {
                    String name = nameValue[0].trim();
                    String value = nameValue[1].trim();
                    // 创建Cookie对象（注意：这里使用的是Cookie，如果你有HttpCookie类，请替换）
                    HttpCookie cookie = new HttpCookie(name, value);
                    cookies.add(cookie);
                }
            }
            return cookies;
        } catch (CustomException e) {
            throw new CustomException(e.getCode(), e.getMsg(), e.getTips());
        }
    }



}
