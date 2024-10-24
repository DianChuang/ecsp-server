package com.dcstd.web.ecspserver.entity.incoming;

import cn.hutool.core.date.DateTime;
import lombok.Data;

/**
 * @FileName Comment_tsan
 * @Description
 * @Author fazhu
 * @date 2024-08-17
 **/
@Data
public class Comment_tsan {
    private Integer uid;//用户id
    private Integer id_comment;//评论id
    private DateTime time;//时间
}
