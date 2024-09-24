package com.dcstd.web.ecspserver.entity;

import cn.hutool.core.date.DateTime;
import lombok.Data;

import java.util.Date;
import java.util.List;

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
    private Integer id_cover; // 封面id
    private String cover; //封面链接(自定义)
    private List<ActiveApplicantImageLib> show_image;
    private String content;
    private Integer status;
    private Integer num_vote;
    private Date time;
}
