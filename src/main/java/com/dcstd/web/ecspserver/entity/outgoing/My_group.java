package com.dcstd.web.ecspserver.entity.outgoing;

import lombok.Data;

import java.util.ArrayList;

/**
 * @FileName My_group
 * @Description
 * @Author fazhu
 * @date 2024-09-23
 **/
@Data
public class My_group {
    private Integer pages;
    private ArrayList<Group> groups;
}
