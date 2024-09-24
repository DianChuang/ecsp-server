package com.dcstd.web.ecspserver.entity.outgoing;

import lombok.Data;

/**
 * @FileName My_post
 * @Description
 * @Author fazhu
 * @date 2024-09-23
 **/
@Data
public class My_post {
    private Integer id;//标题
    private String content;//正文
    private Integer tsan_num;//点赞数
    private Integer comment_num;//评论数
    private Integer id_category;//分类id
    private String tag;//标签
}
