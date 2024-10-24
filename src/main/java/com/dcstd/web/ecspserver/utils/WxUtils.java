package com.dcstd.web.ecspserver.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.dcstd.web.ecspserver.common.Result;
import com.dcstd.web.ecspserver.config.GlobalConfiguration;
import com.dcstd.web.ecspserver.exception.CustomException;
import com.dcstd.web.ecspserver.mapper.UserMapper;
import com.dcstd.web.ecspserver.mapper.WxInfoMapper;
import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

@Component
public class WxUtils {
    private static GlobalConfiguration staticGlobalConfiguration;
    private static WxInfoMapper staticWxInfoMapper;

    @Resource
    GlobalConfiguration globalConfiguration;

    @Resource
    WxInfoMapper wxInfoMapper;


    @PostConstruct
    public void init() {
        staticGlobalConfiguration = globalConfiguration;
        staticWxInfoMapper = wxInfoMapper;
    }


    /**
     * 获取微信token
     * @return (String)token值
     */
    public static String getWxToken() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(staticWxInfoMapper.selectUpdateTime());
        calendar.add(Calendar.MINUTE, 110);
        Date date = calendar.getTime();

        if(DateTime.now().after(date)){
            try (HttpResponse wxLoginRequest = HttpUtil.createGet(staticGlobalConfiguration.getWxToken())
                    .form("grant_type", staticGlobalConfiguration.getGrantTypeToken())
                    .form("appid", staticGlobalConfiguration.getAppid())
                    .form("secret", staticGlobalConfiguration.getSecret())
                    .execute()){
                JSONObject wxLoginRequestJsonObject = JSONObject.parseObject(wxLoginRequest.body());
                setNewWxToken(wxLoginRequestJsonObject.get("access_token").toString());
                //System.out.println(wxLoginRequestJsonObject);
                return wxLoginRequestJsonObject.get("access_token").toString();
            } catch (Exception e) {
                throw new CustomException("获取微信token失败");
            }
        } else if(staticWxInfoMapper.selectAccessToken() != null){
            return staticWxInfoMapper.selectAccessToken();
        }

        throw new CustomException("获取微信token失败");
    }

    /**
     * 设置新的微信token
     * @param token (String)token值
     */
    public static void setNewWxToken(String token) {
        staticWxInfoMapper.clearAccessToken();
        staticWxInfoMapper.updateAccessToken(token);
    }

    /**
     * 获取微信用户openid和session信息
     * @param code (String)微信code
     * @return Hashmap:wxoid, wxsession
     */
    public static HashMap<String, String> getWxId(String code) {
        HashMap<String, String> wxInfo = new HashMap<>();
        String wxoid;
        String wxsession;

        try (HttpResponse wxLoginRequest = HttpUtil.createPost(staticGlobalConfiguration.getWxLogin())
                .form("appid", staticGlobalConfiguration.getAppid())
                .form("secret", staticGlobalConfiguration.getSecret())
                .form("js_code", code)
                .form("grant_type", staticGlobalConfiguration.getGrantTypeLogin())
                .execute()) {
            if (!wxLoginRequest.isOk()) {
                throw new CustomException(500, "微信Code有误");
            }

            //解析微信登录返回信息
            JSONObject wxLoginJsonObject = JSONObject.parseObject(wxLoginRequest.body());
            wxoid = wxLoginJsonObject.getString("openid");
            wxsession = wxLoginJsonObject.getString("session_key");
            if (wxoid == null || wxsession == null) {
                throw new CustomException(500, "微信Code有误");
            }
        } catch (Exception e) {
            throw new CustomException(500, "微信登录接口请求出错", e.getMessage());
        } //微信登录成功

        wxInfo.put("wxoid", wxoid);
        wxInfo.put("wxsession", wxsession);

        return wxInfo;
    }

    /**
     * 获取微信用户信息
     * @return (Result)微信用户信息
     */
    public static HashMap<String, Object> getWxUserInfo() {
        //TODO
        return new HashMap<>();
    }

}
