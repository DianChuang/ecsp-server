package com.dcstd.web.ecspserver.entity;

import lombok.Data;

@Data
public class UserInfo {
    private Integer uid;
    private String avatar;
    private String nickname;
    private String name;
    private Integer id_student;
    private String id_card;
    private String profile_intro;
    private String gender;
    private String email;
}
