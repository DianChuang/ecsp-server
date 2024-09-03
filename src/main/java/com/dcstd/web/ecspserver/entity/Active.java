package com.dcstd.web.ecspserver.entity;

import jdk.jfr.Unsigned;
import lombok.Data;

@Data
public class Active {
    private Integer id;// 活动id
    private String name;// 活动名称
    private String intro;// 活动介绍
    private String cover;// 封面
    private String time_start;// 活动开始时间
    private String time_end;// 活动结束时间
    private String position;// 活动地点类别（外勤 / 线上）
    private String limit_num;// 活动人数限制
    private Integer status;// 活动状态(0：隐藏，1：公开，-1：组织私有，-2：部门私有)

}
