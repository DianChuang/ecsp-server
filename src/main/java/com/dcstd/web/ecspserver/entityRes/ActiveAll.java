package com.dcstd.web.ecspserver.entityRes;

import com.dcstd.web.ecspserver.entity.ActiveImageLib;
import lombok.Data;

import java.util.List;

@Data
public class ActiveAll {
    private Integer id;// 活动id
    private String name;// 活动名称
    private String intro;// 活动介绍
    private String cover;// 封面链接(自定义)
    private Integer id_cover; // 封面id
    private List<ActiveImageLib> show_image; // 展示图片(自定义)
    private Integer status;// 活动状态(0：隐藏，1：公开，-1：组织私有，-2：部门私有)
    private String time_start;// 活动开始时间
    private String time_end;// 活动结束时间
    private String position;// 活动地点类别（外勤 / 线上）
    private String limit_num;// 活动人数限制
    private Integer id_applicant;// 活动申请id
    private String time_join;// 报名时间
    private String time_join_end; // 报名结束时间
    private String position_detail;// 活动详细地址
    private Integer id_category;// 活动类别id
    private Integer id_belong;// 活动归属id
    private Integer id_group;// 负责组织id
    private String name_applicant; // 活动申请人姓名
    private Integer id_superintendent;// 负责人id
    private String id_superintendent_phone; // 负责人联系方式
    private String contact_applicant; // 活动申请人联系方式
    private String number_group;// 相关群号
    private String qrcode;// 二维码地址
    private String code_invite;// 邀请码/签到标识码
    private String num_vote; // 投票数量
    private String category; // 活动类别
    private String belong; // 活动归属
    private String group; // 负责组织
}
