package com.dcstd.web.ecspserver.entity.outgoing;

import lombok.Data;

/**
 * @FileName Course_form
 * @Description
 * @Author fazhu
 * @date 2024-10-22
 **/
@Data
public class Course_form {
    private String title;
    private String cover;//封面
    private String source;//来源
    private Integer view_num;//浏览量
}
