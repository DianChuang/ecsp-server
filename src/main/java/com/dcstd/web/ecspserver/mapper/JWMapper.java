package com.dcstd.web.ecspserver.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.net.HttpCookie;
import java.util.List;

@Mapper
public interface JWMapper {
    @Insert("INSERT INTO jw_info (uid, cookie_login) VALUES (#{uid}, #{cookie_login})")
    void insertJWInfo(Integer uid, String cookie_login);

    @Update("UPDATE jw_info SET cookie_login = #{cookies} WHERE uid = #{uid}")
    void updateCookiesData(Integer uid, String cookies);

    @Select("SELECT cookie_login FROM jw_info WHERE uid = #{id}")
    String getCookies(Integer id);
}
