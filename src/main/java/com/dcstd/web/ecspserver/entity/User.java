package com.dcstd.web.ecspserver.entity;

import lombok.Data;

@Data
public class User {
    private Integer id;// 用户id
    private String wxsession;// 微信session
    private String wxoid;// 微信openid
    private String account;// 账号
    private String password;// 密码
    private String status;// 状态

    private String token;
}
