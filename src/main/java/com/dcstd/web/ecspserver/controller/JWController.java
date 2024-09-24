package com.dcstd.web.ecspserver.controller;

import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import com.dcstd.web.ecspserver.common.Result;
import com.dcstd.web.ecspserver.common.TDJW;
import com.dcstd.web.ecspserver.entity.User;
import com.dcstd.web.ecspserver.exception.CustomException;
import com.dcstd.web.ecspserver.service.JWService;
import com.dcstd.web.ecspserver.utils.RSAUtils;
import com.dcstd.web.ecspserver.utils.TokenUtils;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpCookie;
import java.util.List;

@RestController
public class JWController {
    @Resource
    private JWService jwService;

    @GetMapping("/home/hall/transcripts")
    public Result getTranscript() {
        User user = TokenUtils.getCurrentUser();
        String password = user.getPassword();
        List<HttpCookie> cookies;
        try {
            password = RSAUtils.decrypt(password);
            cookies = jwService.getUserCookies(user.getAccount(), password);
        } catch (Exception e) {
            throw new CustomException("尝试登录教务系统失败");
        }

        JSONObject transcript = jwService.getTranscript(cookies);


        return Result.success(transcript);
    }


}
