package com.dcstd.web.ecspserver.entityRes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Transcript {
    private int id; // 成绩id key?
    private String semester; // 学期 xnmmc-(12：第二学期，3：第一学期)
    private String name; // 名称 kcmc（ksxz）
    private String characteristic; // 属性 kcxzmc
    private String type; // 类型 kclbmc
    private String testMode; // 考试方式 khfsmc
    private String credit; // 学分 xf
    private String point; // 绩点 jd
    private String score; // 成绩 cj
    private String grade; // 学分绩点 xfjd
}
