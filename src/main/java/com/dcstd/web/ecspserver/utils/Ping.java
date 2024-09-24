package com.dcstd.web.ecspserver.utils;

import com.dcstd.web.ecspserver.common.AuthAccess;
import com.dcstd.web.ecspserver.common.LogAnnotation;
import com.dcstd.web.ecspserver.common.Result;
import com.dcstd.web.ecspserver.config.GlobalConfiguration;
import com.dcstd.web.ecspserver.service.BaseInfoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@RestController
public class Ping {
    @Resource
    GlobalConfiguration globalConfiguration;

    @Resource
    BaseInfoService baseInfoService;

    @AuthAccess
    @RequestMapping("/ping")
    @LogAnnotation(module = "Ping", operator = "连通性测试")
    public Result ping() {
        HashMap<String, String> AppInfo = new HashMap<>();
        AppInfo.put("AppName", globalConfiguration.getAppName());
        AppInfo.put("AppVersion", Optional.ofNullable(baseInfoService.getVersion()).orElse(globalConfiguration.getAppVersion()));
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        AppInfo.put("ServerTime", formatter.format(date));
        //微信Token
        //AppInfo.put("Token", WxUtils.getWxToken());
        return Result.success(AppInfo);
    }
}
