package com.dcstd.web.ecspserver.entity.incoming;

import cn.hutool.core.date.DateTime;
import lombok.Data;

/**
 * @FileName Report
 * @Description
 * @Author fazhu
 * @date 2024-08-17
 **/
@Data
public class Report {
    private Integer uid_p;//举报人id
    private Integer uid_b;//被举报人id
    private Integer id_post;//相关文章id
    private Integer id_comment;//相关评论id
    private DateTime time;//举报时间
    private String  reason;//举报原因
}
