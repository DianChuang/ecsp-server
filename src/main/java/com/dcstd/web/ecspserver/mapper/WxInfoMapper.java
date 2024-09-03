package com.dcstd.web.ecspserver.mapper;
import org.apache.ibatis.annotations.*;

import java.util.Date;

@Mapper
public interface WxInfoMapper {

    @Select("select access_token from wx_info limit 1")
    String selectAccessToken();

    @Update("update wx_info set access_token = '' limit 1")
    void clearAccessToken();

    @Update("update wx_info set access_token = #{access_token} limit 1")
    void updateAccessToken(@Param("access_token") String access_token);

    @Select("select time_update from wx_info limit 1")
    Date selectUpdateTime();
}
