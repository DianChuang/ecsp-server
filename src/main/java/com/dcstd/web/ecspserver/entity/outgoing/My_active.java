package com.dcstd.web.ecspserver.entity.outgoing;

import lombok.Data;

/**
 * @FileName My_active
 * @Description
 * @Author fazhu
 * @date 2024-09-24
 **/
@Data
public class My_active {
    private Integer id;//用户ID
    private String cover;//活动封面
    private String name;//活动名称
    private String time_start;//开始时间
    private String time_end;//结束时间
    private String position;//地点
}
