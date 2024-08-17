package com.dcstd.web.ecspserver.entity.incoming;

import cn.hutool.core.date.DateTime;
import lombok.Data;

/**
 * @FileName CommentInsert
 * @Description
 * @Author fazhu
 * @date 2024-08-17
 **/
@Data
public class Comment_Insert {
    private Integer uid;//用户id
    private String content;//评论内容
    private Integer id_post;//所属动态id
    private Integer id_parent;//父内容id
    private Integer id_reply;//父内容id
    private String path;//图片地址
    private DateTime time;//时间
}
