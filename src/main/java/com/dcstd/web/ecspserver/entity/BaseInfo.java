package com.dcstd.web.ecspserver.entity;

import lombok.Data;

@Data
public class BaseInfo {
    private String version; //版本号
    private String logo; //网站LOGO
    private String agreement_user_service; //用户协议
    private String agreement_user_privacy; //隐私政策
    private String rule_post; //发帖规则
    private String rule_active; //活动规则
    private String rule_active_applicant; //活动申请规则
    private String icp; //备案号
    private Integer have_check_post; //是否开启帖子审核  1开启 0关闭
    private Integer have_check_comment; //是否开启评论审核  1开启 0关闭
    private Integer have_comment; //是否开启评论 1开启 0关闭

}
