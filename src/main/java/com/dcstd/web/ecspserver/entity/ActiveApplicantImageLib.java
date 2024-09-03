package com.dcstd.web.ecspserver.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ActiveApplicantImageLib {
    private Integer id; // 图片id
    private Integer id_active; // 活动id
    private String path; // 图片名称+后缀
    private String intro; // 图片介绍
    private Integer sort; // 排序
    private Date time;
    private Integer status; // 状态 1：展示，0：隐藏，-1：删除

    private String url; // 图片地址
}
