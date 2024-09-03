package com.dcstd.web.ecspserver.entity;

import cn.hutool.core.date.DateTime;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class WxInfo {
    private String access_token;
    private Date time_update;
}
