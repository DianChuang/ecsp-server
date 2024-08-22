package com.dcstd.web.ecspserver.mapper;

import com.dcstd.web.ecspserver.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    //根据账号查询用户数据
    @Select("select * from user where account = #{account}")
    User selectByAccount(String account);

    //根据微信openid查询用户数据
    @Select("select * from user where wxoid = #{wxoid}")
    User selectByWxoid(String wxoid);

    //根据id查询用户数据
    @Select("select id, wxoid, wxsession, account, password, status from user where id = #{id}")
    User selectUserById(String uid);

    @Insert("insert into user(wxoid, wxsession, account, password) values(#{wxoid}, #{wxsession}, #{account}, #{password})")
    void insertUser(@Param("account") String account,
                    @Param("password") String password,
                    @Param("wxoid") String wxoid,
                    @Param("wxsession") String wxsession);
}
