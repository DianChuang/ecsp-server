package com.dcstd.web.ecspserver.entity.incoming;

import cn.hutool.core.date.DateTime;
import lombok.Data;

/**
 * @FileName Post_tsan
 * @Description
 * @Author fazhu
 * @date 2024-08-17
 **/
@Data
public class Post_tsan {
    private Integer uid;//用户id
    private Integer id_post;//帖子id
    private DateTime time;//时间
}

