package com.dcstd.web.ecspserver.entity;

import lombok.Data;

@Data
public class ActiveInfo {
    private Integer id;// 活动id
    private Integer id_applicant;// 活动申请id
    private String time_join;// 报名时间
    private String position_detail;// 活动详细地址
    private Integer id_category;// 活动类别id
    private Integer id_belong;// 活动归属id
    private Integer id_group;// 负责组织id
    private Integer id_superintendent;// 负责人id
    private String number_group;// 相关群号
    private String qrcode;// 二维码地址
    private String code_invite;// 邀请码/签到标识码

}
