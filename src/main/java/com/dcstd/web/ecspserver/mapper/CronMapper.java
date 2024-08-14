package com.dcstd.web.ecspserver.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @FileName CronMapper
 * @Description
 * @Author fazhu
 * @date 2024-08-13
 **/
@Mapper
//查询定时的时间
public interface CronMapper {

    @Select("select cron from scheduled where cron_id = #{id}")
    public String getCron(int id);
}
