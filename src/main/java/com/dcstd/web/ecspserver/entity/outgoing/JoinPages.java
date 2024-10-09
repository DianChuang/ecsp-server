package com.dcstd.web.ecspserver.entity.outgoing;

import lombok.Data;

import java.util.ArrayList;

/**
 * @FileName JoinPages
 * @Description
 * @Author fazhu
 * @date 2024-09-24
 **/
@Data
public class JoinPages {
    private Integer pages;
    private ArrayList<Object> data;
}
