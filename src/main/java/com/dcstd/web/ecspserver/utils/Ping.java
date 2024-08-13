package com.dcstd.web.ecspserver.utils;

import com.dcstd.web.ecspserver.common.Result;
import com.dcstd.web.ecspserver.config.GlobalConfiguration;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@RestController
public class Ping {
    @Resource
    GlobalConfiguration globalConfiguration;

    @RequestMapping("/ping")
    public Result ping() {
        HashMap<String, String> AppInfo = new HashMap<>();
        AppInfo.put("AppName", globalConfiguration.getAppName());
        AppInfo.put("AppVersion", globalConfiguration.getAppVersion());
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        AppInfo.put("ServerTime", formatter.format(date));
        return Result.success(AppInfo);
    }
}
