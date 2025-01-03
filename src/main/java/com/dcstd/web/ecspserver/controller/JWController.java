package com.dcstd.web.ecspserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.dcstd.web.ecspserver.common.AuthAccess;
import com.dcstd.web.ecspserver.common.LogAnnotation;
import com.dcstd.web.ecspserver.common.Result;
import com.dcstd.web.ecspserver.common.TDJW;
import com.dcstd.web.ecspserver.entity.User;
import com.dcstd.web.ecspserver.entityRes.Curriculum;
import com.dcstd.web.ecspserver.entityRes.ExamInfo;
import com.dcstd.web.ecspserver.entityRes.Transcript;
import com.dcstd.web.ecspserver.exception.CustomException;
import com.dcstd.web.ecspserver.service.JWService;
import com.dcstd.web.ecspserver.utils.RSAUtils;
import com.dcstd.web.ecspserver.utils.TokenUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpCookie;
import java.util.*;

@RestController
public class JWController {
    private TDJW staticTDJW;

    @Resource
    private JWService jwService;

    @Resource
    private TDJW tdjw;
    @PostConstruct
    public void init() {
        staticTDJW = tdjw;
    }

    /**
     * 获取周数
     * @param week (String)字符串类型周数
     * @return Integer类型数组，包含所有要上课的周
     */
    private ArrayList<Integer> getStartToEndWeek(String week) {
        week = week.replaceAll("周", "");
        ArrayList<Integer> weekList = new ArrayList<>();
        int step = 1;
        if(!week.contains(",")) {
            if(!week.contains("-")) {
                weekList.add(Integer.parseInt(week));
                return weekList;
            }
            if(week.contains("(单)")) {
                week = week.replaceAll(".(单.)", "");
                for(int i = Integer.parseInt(week.split("-")[0]); i <= Integer.parseInt(week.split("-")[1]); i++) {
                    if(i % 2 != 0) {
                        weekList.add(i);
                    }
                }
                return weekList;
            }
            if(week.contains("(双)")) {
                week = week.replaceAll(".(双.)", "");
                for(int i = Integer.parseInt(week.split("-")[0]); i <= Integer.parseInt(week.split("-")[1]); i++) {
                    if(i % 2 == 0) {
                        weekList.add(i);
                    }
                }
                return weekList;
            }
            for(int i = Integer.parseInt(week.split("-")[0]); i <= Integer.parseInt(week.split("-")[1]); i++) {
                weekList.add(i);
            }
        } else {
            for(String item : week.split(",")) {
                if(!item.contains("-")) {
                    weekList.add(Integer.parseInt(item.replace("周", "")));
                } else {
                    if(item.contains("(单)")) {
                        item = item.replaceAll(".(单.)", "");
                        for(int i = Integer.parseInt(item.split("-")[0]); i <= Integer.parseInt(item.split("-")[1]); i++) {
                            if(i % 2 != 0) {
                                weekList.add(i);
                            }
                        }
                        return weekList;
                    }
                    if(item.contains("(双)")) {
                        item = item.replaceAll(".(双.)", "");
                        for(int i = Integer.parseInt(item.split("-")[0]); i <= Integer.parseInt(item.split("-")[1]); i++) {
                            if(i % 2 == 0) {
                                weekList.add(i);
                            }
                        }
                        return weekList;
                    }
                    for(int i = Integer.parseInt(item.split("-")[0]); i <= Integer.parseInt(item.split("-")[1]); i++) {
                        weekList.add(i);
                    }
                }
            }
        }

        return weekList;
    }

    private int[] getStartToEndAttendClass(String attendClass) {
        int startAttend = Integer.parseInt(attendClass.split("-")[0]);
        int endAttend = Integer.parseInt(attendClass.split("-")[1].replace("节", ""));
        return new int[]{startAttend, endAttend};
    }

    /**
     * 获取空教室
     * @param date (String)日期 YYYYMMDD
     * @return
     */
    @GetMapping("/jw/emptyClassRoom")
    @LogAnnotation(module = "JW-CONTROLLER", operator = "获取空教室")
    public Result getEmptyClassRoom(@Param("date") String date) {
        List<HttpCookie> cookies = staticTDJW.getCookies();
        //TODO: 获取空教室
        //JSONObject emptyClassRoom = jwService.getEmptyClassRoom(cookies);
        return Result.success();


    }


    /**
     * 获取成绩单
     * @return
     */
    @GetMapping("/home/hall/transcripts")
    public Result getTranscript() {
        List<HttpCookie> cookies = staticTDJW.getCookies();
        JSONObject transcript = jwService.getTranscript(cookies);

        //返回值
        List<Transcript> res = new ArrayList<>();
        //返回值元素
        Transcript resItem = new Transcript();
        int idCount = 0; // id
        for(Object item : transcript.getJSONArray("items")) {
            JSONObject jsonObject = (JSONObject) item;
            //System.out.println(item);
            resItem = new Transcript();
            resItem.setId(idCount);
            idCount++;
            resItem.setSemester(jsonObject.get("xnmmc").toString() + "-" + (Objects.equals(jsonObject.get("xqm").toString(), "3") ? "1" : "2"));//TODO
            //resItem.setSemester(jsonObject.get("xnmmc").toString() + "-" + (jsonObject.get("xqm").toString() == "3" ? "1" : "2"));
            resItem.setName(jsonObject.get("kcmc").toString() + "(" + jsonObject.get("ksxz").toString() + ")");
            resItem.setCharacteristic(jsonObject.get("kcxzmc").toString());
            resItem.setType(jsonObject.get("kclbmc").toString());
            resItem.setTestMode(Optional.ofNullable(jsonObject.get("khfsmc")).orElse(jsonObject.get("ksxz")).toString());
            resItem.setCredit(jsonObject.get("xf").toString());
            resItem.setPoint(jsonObject.get("jd").toString());
            resItem.setScore(jsonObject.get("cj").toString());
            resItem.setGrade(jsonObject.get("xfjd").toString());
            res.add(resItem);
        }


        return Result.success(res);
    }

    @GetMapping("/jw/exam")
    @LogAnnotation(module = "JW-CONTROLLER", operator = "获取课程表")
    public Result getExam() {
        List<HttpCookie> cookies = staticTDJW.getCookies();
        JSONObject exam = jwService.getExamInfo(cookies);

        //返回值
        List<ExamInfo> res = new ArrayList<>();
        //返回值元素
        ExamInfo resItem = new ExamInfo();
        int idCount = 0; // id
        for(Object item : exam.getJSONArray("items")) {
            JSONObject jsonObject = (JSONObject) item;
            //System.out.println(item);
            resItem = new ExamInfo();
            resItem.setId(idCount);
            idCount++;
            resItem.setSemester(jsonObject.get("ksmc").toString().substring(0, 11));
            resItem.setName(jsonObject.get("kcmc").toString());
            resItem.setTeacher(jsonObject.get("jsxx").toString().split("/")[1] + "(" + jsonObject.get("jsxx").toString().split("/")[0] + ")");
            resItem.setType(jsonObject.get("ksmc").toString().substring(13));
            resItem.setDate(jsonObject.get("kssj").toString().split("[(]")[0]);
            resItem.setTime(jsonObject.get("kssj").toString().split("[(]")[1].split("[)]")[0]);
            resItem.setPosition(jsonObject.get("cdmc").toString());
            res.add(resItem);
        }

        return Result.success(res);
    }

    @GetMapping("/jw/curriculum")
    @LogAnnotation(module = "JW-CONTROLLER", operator = "获取课程表")
    public Result getCurriculum() {
        List<HttpCookie> cookies = staticTDJW.getCookies();
        JSONObject curriculum = jwService.getCurriculum(cookies);

        int idCount = 0; // id

        //返回值
        List<Curriculum> res = new ArrayList<>();
        //返回值元素
        Curriculum resItem = new Curriculum();

        ArrayList<Integer> weekList = new ArrayList<>();

        //实践课数据处理 sjk：实践课
        for (Object sjk : curriculum.getJSONArray("sjkList")) {

            JSONObject jsonObject = (JSONObject) sjk;
            weekList = getStartToEndWeek(jsonObject.get("qsjsz").toString());
            for(int i : weekList) {
                resItem = new Curriculum();
                resItem.setId(idCount);
                idCount++;
                resItem.setName(jsonObject.get("kcmc").toString());
                resItem.setWeek(i);
                resItem.setSection(1);
                resItem.setSectionCount(12);
                resItem.setAddress("请以导员提供的文件为准");
                resItem.setTeacher(jsonObject.get("jsxm").toString());
                res.add(resItem);
            }
        }

        int[] attendList = new int[2];
        //日常数据处理 kb：课表
        for (Object kb : curriculum.getJSONArray("kbList")) {

            JSONObject jsonObject = (JSONObject) kb;
            weekList = getStartToEndWeek(jsonObject.get("zcd").toString());
            attendList = getStartToEndAttendClass(jsonObject.get("jc").toString());
            resItem = new Curriculum();
            for(int i : weekList) {
                resItem.setId(idCount);
                idCount++;
                resItem.setName(jsonObject.get("kcmc").toString());
                resItem.setWeek(i);
                resItem.setSection(attendList[0]);
                resItem.setSectionCount(attendList[1] - attendList[0] + 1);
                resItem.setAddress(jsonObject.get("cdmc").toString());
                resItem.setTeacher(jsonObject.get("xm").toString());
                res.add(resItem);
            }

        }

        return Result.success(res);
    }


}
