package com.dcstd.web.ecspserver.entity;

import lombok.Data;

@Data
public class UserInfo {
    private Integer uid;//uid
    private String avatar;//头像
    private String nickname;//昵称
    private String name;//真实姓名
    private Integer id_student;//学号
    private String id_card;//身份证号
    private String profile_intro;//简介(个性签名)
    private String gender;//性别
    private String email;//邮箱
    private String specialized;//专业
    private String birthday;//生日
    private String time_join_school;//入学时间
    private String college;//学院
}
