package com.dcstd.web.ecspserver.entity.outgoing;

import lombok.Data;

/**
 * @FileName User_info
 * @Description
 * @Author fazhu
 * @date 2024-09-22
 **/
@Data
public class User_info {
    private String avatar;//头像
    private String nickname;//昵称
    private String gender; //性别
    private Integer id_student;//学号
    private String profile_intro;//简介
}
