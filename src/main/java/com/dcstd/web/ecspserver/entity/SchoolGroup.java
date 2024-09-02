package com.dcstd.web.ecspserver.entity;

import lombok.Data;

@Data
public class SchoolGroup {
    private Integer id;//id
    private Integer id_belong;//归属id
    private String name;//名称
    private String battle_cry;//口号
    private String intro;//简介
    private String slogan;//标语

}
