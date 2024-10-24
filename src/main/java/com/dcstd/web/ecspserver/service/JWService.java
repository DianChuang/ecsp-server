package com.dcstd.web.ecspserver.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.dcstd.web.ecspserver.common.TDJW;
import com.dcstd.web.ecspserver.config.GlobalConfiguration;
import com.dcstd.web.ecspserver.exception.CustomException;
import com.dcstd.web.ecspserver.utils.RSAUtils;
import com.dcstd.web.ecspserver.utils.TokenUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.net.HttpCookie;
import java.util.*;

@Service
public class JWService {
    @Resource
    private TDJW tdjw;

    static TDJW staticTdjw;
    static GlobalConfiguration staticGlobalConfiguration;

    @Resource
    GlobalConfiguration GlobalConfiguration;

    //初始化
    @PostConstruct
    public void init() {
        staticGlobalConfiguration = GlobalConfiguration;
        staticTdjw = tdjw;
    }

    private static List<HttpCookie> getCookies() {
        return staticTdjw.getCookies();
    }

    private static List<HttpCookie> updateCookies(String account, String password) {
        try {
            password = RSAUtils.decrypt(password);
        } catch (Exception e) {
            throw new CustomException("RSA解密失败");
        }
        return staticTdjw.getCookies(account, password);
    }

    /**
     * 获取用户登录cookies
     * @param username 用户名
     * @param password 密码
     * @return List<HttpCookie>
     */
    public List<HttpCookie> getUserCookies(String username, String password) {
        return staticTdjw.login(username, password).getCookies();
    }

    /**
     * 获取成绩信息
     * @param cookies 登录cookies
     * @return JSONObject
     */
    public JSONObject getTranscript(List<HttpCookie> cookies) {
        HttpResponse data;

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

        data = HttpUtil.createPost(staticGlobalConfiguration.getJw() + staticGlobalConfiguration.getJwTranscriptInfo()).cookie(getCookies()).form(params).execute();
        if(!data.isOk()) {
            data = HttpUtil.createPost(staticGlobalConfiguration.getJw() + staticGlobalConfiguration.getJwTranscriptInfo()).cookie(updateCookies(TokenUtils.getCurrentUser().getAccount(), TokenUtils.getCurrentUser().getPassword())).form(params).execute();
        }
        return JSONObject.parseObject(data.body());
    }

    /**
     * 获取考试信息
     * @param cookies 登录cookie
     * @return JSONObject
     */
    public JSONObject getExamInfo(List<HttpCookie> cookies) {
        HttpResponse data;
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
        data = HttpUtil.createPost(staticGlobalConfiguration.getJw() + staticGlobalConfiguration.getJwExamInfo()).cookie(getCookies()).form(params).execute();
        if(!data.isOk()) {
            data = HttpUtil.createPost(staticGlobalConfiguration.getJw() + staticGlobalConfiguration.getJwExamInfo()).cookie(updateCookies(TokenUtils.getCurrentUser().getAccount(), TokenUtils.getCurrentUser().getPassword())).form(params).execute();
        }

        return JSONObject.parseObject(data.body());
    }

    /**
     * 获取课表信息
     * @param cookies 登录cookie
     * @return JSONObject
     */
    public JSONObject getCurriculum(List<HttpCookie> cookies) {
        HttpResponse data;
        HashMap <String, Object> params = new HashMap<>();
        Calendar calendar = Calendar.getInstance();

        // 8月之前为上学年，8月之后为当前学年
        params.put("xnm", calendar.get(Calendar.MONTH) < 8 ? calendar.get(Calendar.YEAR) - 1 : calendar.get(Calendar.YEAR));//学年名
        // 次年7月之后至本年2月之前为上学期，2月之后为下学期
        params.put("xqm", ((calendar.get(Calendar.MONTH) < 2) || (calendar.get(Calendar.MONTH) > 7) ? 3 : 12));//学期名(12：第二学期，3：第一学期)
        params.put("kzlx", "ck");

        data = HttpUtil.createPost(staticGlobalConfiguration.getJw() + staticGlobalConfiguration.getJwCurriculumInfo()).cookie(getCookies()).form(params).execute();
        if(!data.isOk()) {
            data = HttpUtil.createPost(staticGlobalConfiguration.getJw() + staticGlobalConfiguration.getJwCurriculumInfo()).cookie(updateCookies(TokenUtils.getCurrentUser().getAccount(), TokenUtils.getCurrentUser().getPassword())).form(params).execute();
        }

        return JSONObject.parseObject(data.body());
    }

}
