package com.dcstd.web.ecspserver.entity.outgoing;

import lombok.Data;
import org.apache.ibatis.annotations.Param;

/**
 * @FileName Pages
 * @Description
 * @Author fazhu
 * @date 2024-08-24
 **/
@Data
public class Pages {
    private Integer pages;
    private Object date;
}
