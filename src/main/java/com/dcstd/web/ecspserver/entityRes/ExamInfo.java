package com.dcstd.web.ecspserver.entityRes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class ExamInfo {
    private int id; // id
    private String type; // 考试类型
    private String date; // 考试日期
    private String time; // 考试时间
    @JsonIgnore
    private String monthNumber; // 星期
    private String semester; // 学期
    private String name; // 考试名称
    private String position; // 考试地点
    private String teacher; // 考试老师
}
