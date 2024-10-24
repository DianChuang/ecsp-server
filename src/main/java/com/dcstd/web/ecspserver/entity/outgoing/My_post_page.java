package com.dcstd.web.ecspserver.entity.outgoing;

import lombok.Data;

import java.util.ArrayList;

/**
 * @FileName My_post_page
 * @Description
 * @Author fazhu
 * @date 2024-09-24
 **/
@Data
public class My_post_page {
    private Integer pages;//页数
    ArrayList<My_post> posts;//动态
}
