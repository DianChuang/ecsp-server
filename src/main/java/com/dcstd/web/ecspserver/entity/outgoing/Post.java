package com.dcstd.web.ecspserver.entity.outgoing;

import lombok.Data;

/**
 * @FileName Post
 * @Description
 * @Author fazhu
 * @date 2024-08-15
 **/
@Data
public class Post {
    private String id;//动态id
    private String avatar;//头像
    private String nickname;//用户名
    private String content;//动态内容
    private String tsan_num;//点赞数
    private String tag;//标签
    private String comment_num;//评论数
    private String time;//发布时间
    private String path;//图片地址
}
