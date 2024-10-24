package com.dcstd.web.ecspserver.entity.outgoing;

import lombok.Data;

import java.util.ArrayList;

/**
 * @FileName My_active_page
 * @Description
 * @Author fazhu
 * @date 2024-09-24
 **/
@Data
public class My_active_page {
    private Integer pages;
    private ArrayList<My_active> actives;
}
