package com.dcstd.web.ecspserver.entity.outgoing;

import lombok.Data;

import java.util.ArrayList;

/**
 * @FileName HomeActive
 * @Description
 * @Author fazhu
 * @date 2024-10-21
 **/
@Data
public class HomeActive {
    private Integer pages;
    private ArrayList<ActiveList> activeLists;
}
