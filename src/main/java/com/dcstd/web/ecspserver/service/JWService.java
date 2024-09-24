package com.dcstd.web.ecspserver.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.dcstd.web.ecspserver.common.TDJW;
import com.dcstd.web.ecspserver.config.GlobalConfiguration;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.net.HttpCookie;
import java.util.*;

@Service
public class JWService {
    @Resource
    private TDJW tdjw;

    static GlobalConfiguration staticGlobalConfiguration;

    @Resource
    GlobalConfiguration GlobalConfiguration;

    //初始化
    @PostConstruct
    public void init() {
        staticGlobalConfiguration = GlobalConfiguration;
    }

    /**
     * 获取用户登录cookies
     * @param username 用户名
     * @param password 密码
     * @return List<HttpCookie>
     */
    public List<HttpCookie> getUserCookies(String username, String password) {
        return tdjw.login(username, password).getCookies();
    }

    /**
     * 获取成绩信息
     * @param cookies 登录cookies
     * @return JSONObject
     */
    public JSONObject getTranscript(List<HttpCookie> cookies) {
        HashMap <String, Object> params = new HashMap<>();
        params.put("xnm", "");//学年名
        params.put("xqm", "");//学期名
        params.put("kcbj", "");//课程班级
        params.put("_search", "false");//是否搜索
        params.put("queryModel.showCount", "5000");
        params.put("queryModel.currentPage", "1");
        params.put("queryModel.sortName", "");
        params.put("queryModel.sortOrder", "asc");
        params.put("time", "1");
        HttpResponse data = HttpUtil.createPost(staticGlobalConfiguration.getJw() + staticGlobalConfiguration.getJwTranscriptInfo()).cookie(cookies).form(params).execute();
        return JSONObject.parseObject(data.body());
    }

    /**
     * 获取考试信息
     * @param cookies 登录cookie
     * @return JSONObject
     */
    public JSONObject getExamInfo(List<HttpCookie> cookies) {
        HashMap <String, Object> params = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        params.put("xnm", calendar.get(Calendar.YEAR));//学年名
        params.put("xqm", "");//学期名
        params.put("ksmcdmb_id", "");//考试名称代码
        params.put("_search", "false");//是否搜索
        params.put("queryModel.showCount", "5000");
        params.put("queryModel.currentPage", "1");
        params.put("queryModel.sortName", "");
        params.put("queryModel.sortOrder", "asc");
        params.put("time", "1");
        HttpResponse data = HttpUtil.createPost(staticGlobalConfiguration.getJw() + staticGlobalConfiguration.getJwExamInfo()).cookie(cookies).form(params).execute();
        return JSONObject.parseObject(data.body());
    }
}
