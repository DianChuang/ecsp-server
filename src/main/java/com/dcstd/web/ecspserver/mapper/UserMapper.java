package com.dcstd.web.ecspserver.mapper;

import com.dcstd.web.ecspserver.entity.User;
import com.dcstd.web.ecspserver.entity.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.Map;

@Mapper
public interface UserMapper {
    //根据账号查询用户数据
    @Select("select * from user where account = #{account}")
    User selectByAccount(String account);

    //根据微信openid查询用户数据
    @Select("select * from user where wxoid = #{wxoid}")
    User selectByWxoid(String wxoid);

    //根据id查询用户基本数据
    @Select("select * from user where id = #{id}")
    User selectUserById(String uid);

    @Insert("insert into user(wxoid, wxsession, account, password) values(#{wxoid}, #{wxsession}, #{account}, #{password})")
    void insertUser(@Param("account") String account,
                    @Param("password") String password,
                    @Param("wxoid") String wxoid,
                    @Param("wxsession") String wxsession);

    @Insert("insert into user_info(uid, avatar, gender, name, nickname, id_student, " +
            "id_card, profile_intro, email, time_join_school, college, specialized, birthday) " +
            "values (#{uid}, #{avatar}, #{gender}, #{name}, #{nickname}, #{idStudent}, " +
            "#{idCard}, #{profileIntro}, #{email}, #{timeJoinSchool}, #{college}, #{specialized}, #{birthday})")
    void insertUserInfo(Integer uid, String avatar, String gender, String name,
                        String nickname, Integer idStudent, String idCard,
                        String profileIntro, String email, String timeJoinSchool,
                        String college, String specialized, String birthday);


    //根据id查询用户详细信息
    @Select("select * from user_info where uid = #{id}")
    UserInfo selectUserInfoById(Integer id);

}
