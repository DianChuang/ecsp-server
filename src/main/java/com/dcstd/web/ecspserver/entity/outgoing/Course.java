package com.dcstd.web.ecspserver.entity.outgoing;

import lombok.Data;
import net.sf.jsqlparser.parser.StringProvider;

/**
 * @FileName Course
 * @Description
 * @Author fazhu
 * @date 2024-10-21
 **/
@Data
public class Course {
    private String title;
    private String cover;//封面
    private String subject;//科目
    private Integer view_num;//浏览量
}
