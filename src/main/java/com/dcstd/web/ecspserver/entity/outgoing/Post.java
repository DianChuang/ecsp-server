package com.dcstd.web.ecspserver.entity.outgoing;

import cn.hutool.core.date.DateTime;
import lombok.Data;


import java.sql.Date;

/**
 * @FileName Post
 * @Description
 * @Author fazhu
 * @date 2024-08-15
 **/
@Data
public class Post {
    private Integer id;//动态id
    private String avatar;//头像
    private String nickname;//用户名
    private String content;//动态内容
    private Integer tsan_state;//点赞状态
    private Integer tsan_num;//点赞数
    private Integer comment_num;//评论数
    private Integer is_anonymous;//是否匿名
    private String time;//发布时间
    private String path;//图片地址

}
