package com.dcstd.web.ecspserver.entityRes;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Curriculum {
    private int id; // 课程id
    private String name; // 课程名
    private int week; // 周数
    private int section; // 起始节数
    private int sectionCount; // 节数
    private String address; // 地址
    private String teacher; // 教师
    @JsonIgnore
    private String color; // 颜色
}
