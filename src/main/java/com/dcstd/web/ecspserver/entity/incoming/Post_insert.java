package com.dcstd.web.ecspserver.entity.incoming;

import cn.hutool.core.date.DateTime;
import lombok.Data;

/**
 * @FileName Post_insert
 * @Description 发帖子
 * @Author fazhu
 * @date 2024-08-15
 **/
@Data
public class Post_insert {
    private Integer uid;//用户id
    private String content;//动态内容
    private Integer id_category;//分类
    private String path;//图片地址
    private DateTime time;//发布时间
    private Integer is_anonymity;//匿名1/0
    private String contact_name;//联系方式—姓名
    private String contact_type;//号码类型
    private Integer contact_number;//号码

}
