package com.dcstd.web.ecspserver.entity.outgoing;

import lombok.Data;

/**
 * @FileName CommentAudit
 * @Description
 * @Author fazhu
 * @date 2024-11-11
 **/
@Data
public class CommentAudit {
    private Integer id;
    private String uid;//用户id
    private String content;//内容
}
