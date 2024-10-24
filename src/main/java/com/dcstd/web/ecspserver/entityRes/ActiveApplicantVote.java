package com.dcstd.web.ecspserver.entityRes;

import lombok.Data;

import java.util.Date;

@Data
public class ActiveApplicantVote {
    private Integer id;
    private Integer uid;
    private String name_active;
    private Integer id_cover; // 封面id
    private String cover; //封面链接(自定义)
    private Integer num_vote;
    private Date time;
}
