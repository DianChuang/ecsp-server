package com.dcstd.web.ecspserver.controller;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.dcstd.web.ecspserver.common.AuthAccess;
import com.dcstd.web.ecspserver.common.Result;
import com.dcstd.web.ecspserver.common.TDJW;
import com.dcstd.web.ecspserver.config.GlobalConfiguration;
import com.dcstd.web.ecspserver.entity.User;
import com.dcstd.web.ecspserver.exception.CustomException;
import com.dcstd.web.ecspserver.exception.GlobalException;
import com.dcstd.web.ecspserver.mapper.UserMapper;
import com.dcstd.web.ecspserver.utils.RSAUtils;
import com.dcstd.web.ecspserver.utils.TokenUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class UserLogin {
    @Resource
    UserMapper userMapper;

    @Resource
    GlobalConfiguration globalConfiguration;

    /**
     * 用户注册
     * @param request 网络请求
     * @return 结果
     */
    @AuthAccess
    @PostMapping("/register")
    public Result register(HttpServletRequest request) {
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String code = request.getParameter("code");

        //判断用户是否存在
        User user;
        user = userMapper.selectByAccount(account);
        if(user != null){
            throw new CustomException(GlobalException.ERROR_REPEAT_REGISTER);
        }

        //判断教务系统账号密码是否正确
        TDJW tdjw = new TDJW();
        try (HttpResponse loginRequest = tdjw.login(account, password)) {
            JSONObject userInfoJsonObject = JSONObject.parseObject(loginRequest.body());
            //判断是否成功登录
            if(userInfoJsonObject == null || userInfoJsonObject.get("xh") == null || !account.equals(userInfoJsonObject.get("xh"))){
                throw new CustomException(GlobalException.ERROR_REGISTER);
            }
        } catch (CustomException e){
            throw new CustomException(500, "教务系统登录请求异常", e.getTips());
        } //教务系统登录成功

        //获取微信id及session信息
        HashMap<String, String> wxInfo = getWxInfo(code); //微信登录成功

        //用户信息写入数据库
        User newUser = new User();
        newUser.setAccount(account);
        //密码加密
        try {
            newUser.setPassword(RSAUtils.encrypt(password));
        } catch (Exception e) {
            throw new CustomException(500, "密码加密失败!");
        }
        newUser.setWxoid(wxInfo.get("wxoid"));
        newUser.setWxsession(wxInfo.get("wxsession"));

        userMapper.insertUser(newUser.getAccount(), newUser.getPassword(), newUser.getWxoid(), newUser.getWxsession());
        return Result.success("注册成功噜~");
    }

    @AuthAccess
    @PostMapping("/login")
    public Result login(HttpServletRequest request) {
        String code = request.getParameter("code");//微信code
        HashMap<String, String> wxInfo = getWxInfo(code);//获取微信id及session信息
        User dbUser;//数据库用户
        try {
            //查询数据库用户
            dbUser = userMapper.selectByWxoid(wxInfo.get("wxoid"));
            //用户是否存在
            if(dbUser == null){
                throw new CustomException(GlobalException.ERROR_LOGIN);
            }
            //判断用户状态
            if(!dbUser.getStatus().equals("0") && !dbUser.getStatus().equals("-1")){
                throw new CustomException(GlobalException.BAN_LOGIN);
            }
        } catch (Exception e) {
            throw new CustomException(500, e.getMessage());
        }
        //生成token
        dbUser.setToken(TokenUtils.createToken(String.valueOf(dbUser.getId()), dbUser.getPassword()));
        //脱敏
        dbUser.setPassword("*");
        dbUser.setWxoid("*");
        dbUser.setWxsession("*");
        return Result.success("登录成功~", dbUser);
    }

    private HashMap<String, String> getWxInfo(String code) {
        HashMap<String, String> wxInfo = new HashMap<>();
        String wxoid = null;
        String wxsession = null;
        try (HttpResponse wxLoginRequest = HttpUtil.createPost(globalConfiguration.getWxLogin())
                .form("appid", globalConfiguration.getAppid())
                .form("secret", globalConfiguration.getSecret())
                .form("js_code", code)
                .form("grant_type", globalConfiguration.getGrantType())
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
            throw new CustomException(500, "微信登录接口请求出错");
        } //微信登录成功

        wxInfo.put("wxoid", wxoid);
        wxInfo.put("wxsession", wxsession);

        return wxInfo;
    }
}
