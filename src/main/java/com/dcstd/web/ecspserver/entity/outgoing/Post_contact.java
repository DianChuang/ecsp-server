package com.dcstd.web.ecspserver.entity.outgoing;

import lombok.Data;

/**
 * @FileName Contact
 * @Description
 * @Author fazhu
 * @date 2024-08-19
 **/
@Data
public class Post_contact {
    private String contact_name;//姓名
    private String contact_type;//账号属性
    private Integer contact_number;//号码
}
