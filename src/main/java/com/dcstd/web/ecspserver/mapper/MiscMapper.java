package com.dcstd.web.ecspserver.mapper;

import com.dcstd.web.ecspserver.entity.Festival;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MiscMapper {

    @Select("SELECT * FROM festival WHERE (time_start > CURRENT_TIMESTAMP()) AND (name = '寒假' OR name = '暑假') ORDER BY ABS(DATEDIFF(time_start, CURDATE())) ;")
    Festival getFestival();

    @Select("SELECT * FROM festival WHERE (time_start > CURRENT_TIMESTAMP()) AND (name != '寒假' AND name != '暑假') ORDER BY ABS(DATEDIFF(time_start, CURDATE())) LIMIT 1;")
    Festival getHoliday();
}
