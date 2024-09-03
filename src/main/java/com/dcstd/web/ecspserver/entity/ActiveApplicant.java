package com.dcstd.web.ecspserver.entity;

import cn.hutool.core.date.DateTime;
import lombok.Data;

import java.util.Date;

@Data
public class ActiveApplicant {
    private Integer id;
    private Integer uid;
    private String name_active;
    private Integer id_belong;
    private Integer id_group;
    private Integer id_category;
    private String name_applicant;
    private String contact_applicant;
    private String content;
    private Integer status;
    private Integer num_vote;
    private Date time;
}
