package com.dcstd.web.ecspserver.mapper;

import com.dcstd.web.ecspserver.entity.outgoing.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

/**
 * @FileName UserInfoMapper
 * @Description
 * @Author fazhu
 * @date 2024-09-22
 **/
@Mapper
public interface UserInfoMapper {
    //查询帖子点赞数
    @Select("select tsan_num from post where uid=#{user_id}")
    ArrayList<Integer> getPostId(Integer user_id);

    //查询用户发的评论的点赞数
    @Select("select count(*) from comment_tsan where id_comment in (select id from comment where uid=#{user_id})")
    Integer getCommentNum(Integer user_id);

    //查询粉丝数
    @Select("select count(*) from follow where id_follow=#{user_id} and status!=-1")
    Integer getFanNum(Integer user_id);

    //查询关注数
    @Select("select count(*) from follow where uid=#{user_id} and status!=-1")
    Integer getFollowNum(Integer user_id);

    //查询用户个人信息
    @Select("select id_student,profile_intro,avatar,nickname,gender,specialized from user_info where uid=#{user_id}")
    User_info getUserInfo(Integer user_id);

    //更改户个人信息
    @Update("update user_info set id_student = #{id_student}, profile_intro = #{profile_intro}, nickname = #{nickname}, gender = #{gender} where uid = #{user_id}")
    void updateUserInfo(
            Integer user_id,
            Integer id_student,
            String profile_intro,
            String nickname,
            String gender
    );

    //更改头像
    @Update("update user_info set avatar = #{avatar} where uid = #{user_id}")
    void updateAvatar(String avatar, Integer user_id);

    //查询动态总数
    @Select("select count(*) from post where uid=#{user_id}")
    Integer getPostNum(Integer user_id);

    //查询动态
    @Select("select id,content,tsan_num,comment_num,id_category,tag from post where uid=#{user_id} and status=1  order by time desc limit #{page},#{limit}")
    ArrayList<My_post> getPost(Integer user_id, Integer page, Integer limit);

    //查询部落总数
    @Select("select count(*) from user_group where uid=#{user_id}  ")
    Integer getGroupNum(Integer user_id);

    //查询部落
    @Select("select a.name,b.path,c.name as belong from school_group as a left join group_image_lib as b  on a.id=b.id_group left join active_belong  as c on a.id=c.id where a.id in(select id from user_group where uid=#{user_id}) limit #{page},#{limit}")
    ArrayList<Group> getGroup(Integer user_id, Integer page, Integer limit);

    //查询活动总数
    @Select("select count(*) from active_participate where uid=#{user_id}")
    Integer getActiveNum(Integer user_id);

    //查询所有参加的活动
    @Select("select a.id,b.path,a.name,a.time_start,a.time_end,a.position from active as a left join active_image_lib  as b on a.id=b.id_active  where a.id in(select id from active_participate where uid=#{user_id} ) limit #{page},#{limit}")
    ArrayList<My_active> getActive(Integer user_id, Integer page, Integer limit);
}
