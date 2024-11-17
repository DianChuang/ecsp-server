package com.dcstd.web.ecspserver.entity.outgoing;

import lombok.Data;

/**
 * @FileName PostAudit
 * @Description
 * @Author fazhu
 * @date 2024-11-10
 **/
@Data
public class PostAudit {
    private String id;
    private String uid;//用户id
    private String content;//内容
    private String category;//分类

}
