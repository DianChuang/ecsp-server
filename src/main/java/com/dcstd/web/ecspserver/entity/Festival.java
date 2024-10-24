package com.dcstd.web.ecspserver.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class Festival {
    private Integer id;

    private String name;
    private Date time_start;
    private Date time_end;
}
