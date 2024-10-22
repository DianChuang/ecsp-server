package com.dcstd.web.ecspserver.controller;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.dcstd.web.ecspserver.common.AuthAccess;
import com.dcstd.web.ecspserver.common.Result;
import com.dcstd.web.ecspserver.common.TDJW;
import com.dcstd.web.ecspserver.config.GlobalConfiguration;
import com.dcstd.web.ecspserver.entity.JWInfo;
import com.dcstd.web.ecspserver.entity.User;
import com.dcstd.web.ecspserver.entity.UserInfo;
import com.dcstd.web.ecspserver.exception.CustomException;
import com.dcstd.web.ecspserver.exception.GlobalException;
import com.dcstd.web.ecspserver.mapper.JWMapper;
import com.dcstd.web.ecspserver.mapper.UserMapper;
import com.dcstd.web.ecspserver.utils.B64;
import com.dcstd.web.ecspserver.utils.RSAUtils;
import com.dcstd.web.ecspserver.utils.TokenUtils;
import com.dcstd.web.ecspserver.utils.WxUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.security.SecurityUtil;
import org.eclipse.angus.mail.smtp.DigestMD5;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.net.HttpCookie;
import java.security.Security;
import java.util.HashMap;
import java.util.List;

@RestController
public class UserLogin {
    @Resource
    UserMapper userMapper;

    @Resource
    GlobalConfiguration globalConfiguration;

    @Resource
    JWMapper jwMapper;

    /**
     * 用户注册
     * @param request 网络请求
     * @return 结果
     */
    @AuthAccess
    @PostMapping("/register")
    @Transactional(rollbackFor = Exception.class)
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

        //用户信息数据初始化
        UserInfo newUserInfo = new UserInfo();

        //cookie信息初始化
        JWInfo newJWInfo = new JWInfo();

        //判断教务系统账号密码是否正确
        TDJW tdjw = new TDJW();
        try (HttpResponse loginRequest = tdjw.login(account, password)) {
            JSONObject userInfoJsonObject = JSONObject.parseObject(loginRequest.body());
            //判断是否成功登录
            if(userInfoJsonObject == null || userInfoJsonObject.get("xh") == null || !account.equals(userInfoJsonObject.get("xh").toString())){
                throw new CustomException(GlobalException.ERROR_REGISTER);
            }
            newUserInfo.setId_student(Integer.parseInt(userInfoJsonObject.get("xh").toString()));//用户学号
            newUserInfo.setGender(userInfoJsonObject.get("xbm").toString());//性别
            newUserInfo.setTime_join_school(userInfoJsonObject.get("rxrq").toString());//入学日期
            newUserInfo.setSpecialized(userInfoJsonObject.get("zyh_id").toString());//专业
            newUserInfo.setCollege(userInfoJsonObject.get("zsjg_id").toString());//学院
            newUserInfo.setBirthday(userInfoJsonObject.get("csrq").toString());//生日
            try {
                //存入用户真实姓名
                String name = RSAUtils.encrypt(userInfoJsonObject.get("xm").toString());
                newUserInfo.setName(name);
                //存入用户身份证号
                String id_card = RSAUtils.encrypt(userInfoJsonObject.get("zjhm").toString());
                newUserInfo.setId_card(id_card);
            } catch (Exception e) {
                System.out.println("敏感信息加密失败，放弃存入");
            }
            //获取cookie信息
            newJWInfo.setCookie_login(loginRequest.getCookies().toString());

        } catch (CustomException e){
            throw new CustomException(e.getCode(), "教务系统登录请求异常", e.getTips());
        } //教务系统登录成功


        //获取微信id及session信息
        HashMap<String, String> wxInfo = WxUtils.getWxId(code); //微信登录成功

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

        //插入用户基础信息
        userMapper.insertUser(newUser.getAccount(), newUser.getPassword(), newUser.getWxoid(), newUser.getWxsession());

        //基础数据初始化
        Integer uid = userMapper.selectByWxoid(wxInfo.get("wxoid")).getId();
        newUserInfo.setUid(uid);//uid
        newUserInfo.setAvatar(globalConfiguration.getFileUserAvatar());//头像
        newUserInfo.setNickname("用户"+ uid);//昵称
        newUserInfo.setProfile_intro("没有找到此人的个签ヾ(•ω•`)o");//个签

        //设置Cookie对应uid
        newJWInfo.setUid(uid);

        //插入更多信息
        userMapper.insertUserInfo(newUserInfo.getUid(), newUserInfo.getAvatar(), newUserInfo.getGender(),
                newUserInfo.getName(), newUserInfo.getNickname(), newUserInfo.getId_student(),
                newUserInfo.getId_card(), newUserInfo.getProfile_intro(), newUserInfo.getEmail(),
                newUserInfo.getTime_join_school(), newUserInfo.getCollege(), newUserInfo.getSpecialized(), newUserInfo.getBirthday());

        jwMapper.insertJWInfo(newJWInfo.getUid(), newJWInfo.getCookie_login());

        return Result.success("注册成功噜~");
    }

    @AuthAccess
    @GetMapping("/login")
    public Result login(HttpServletRequest request) {
        String code = request.getParameter("code");//微信code
        HashMap<String, String> wxInfo = WxUtils.getWxId(code);//获取微信id及session信息
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


}
