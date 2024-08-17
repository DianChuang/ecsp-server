package com.dcstd.web.ecspserver.mapper;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dcstd.web.ecspserver.entity.outgoing.Post;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.*;

/**
 * @FileName PostMapper
 * @Description
 * @Author fazhu
 * @date 2024-08-15
 **/
@Mapper
public interface PostMapper extends BaseMapper {

    //查询帖子所需要的参数
    @Select("select a.id,a.content,a.tsan_num,a.tsan_num,a.comment_num" +
            ",a.tag,a.time,b.avatar,b.nickname" +
            "from post as a" +
            "join userinfo as b" +
            "on a.uid=b.uid")
    Post post();

    //发帖
    @Insert("insert into `post` value (null,#{uid},#{content},0,0,#{id_category},null,0,#{time},#{is_anonymity},#{contact_name},#{contact_type},#{contact_number})")
    void post_insert(
            @Param("uid") Integer uid,//用户id
            @Param("content") String content,//正文
            @Param("id_category") Integer id_category,//分类
            @Param("time") DateTime time,//发布时间
            @Param("is_anonymity") Integer is_anonymity,//是否匿名
            @Param("contact_name") String contact_name,//联系方式——姓名
            @Param("contact_type") String contact_type,//号码类型
            @Param("contact_number") Integer contact_number//号码
    );

    //发帖增加图片
    @Insert("insert into `post_image_lib` value(null,(select last_insert_id()),null,#{path})")
    void post_insert_image(
            @Param("path") String path//图片地址
    );

    //发评论
    @Insert("insert into `comment` value(null,#{content},#{id_post},#{id_parent},#{id_reply},0,#{time})")
    void comment_insert(
            @Param("content") String content,//正文
            @Param("id_post") Integer id_post,//所属动态id
            @Param("id_parent") Integer id_parent,//父内容id
            @Param("id_reply") Integer id_reply,//回复id
            @Param("time") DateTime time//评论时间
    );

    //发评论增加图片
    @Insert("insert into `comment_image_lib` value(null,(select last_insert_id()),null,#{path})")
    void comment_image_lib(
            @Param("path") String path//图片地址
    );

    //评论点赞
    @Insert("insert into `comment_tsan` value(null,#{uid},#{id_comment},#{time})")
    void comment_tsan(
            @Param("uid") Integer uid,//点赞者id
            @Param("id_comment") Integer id_comment,//点赞评论id
            @Param("time") DateTime time//点赞时间
    );

    //帖子点赞
    @Insert("insert into `post_tsan` value(null,#{uid},#{id_post},#{time})")
    void post_tsan(
            @Param("uid") Integer uid,//点赞者id
            @Param("id_post") Integer id_post,//点赞帖子id
            @Param("time") DateTime time//点赞时间
    );

    //删帖
    @Delete("delete from `post` where id=#{post_id}")
    void post_delete(
            @Param("post_id") Integer post_id//删的id
    );

    //查询帖子发布者id
    @Select("select `uid` from `post` where id=#{post_id} ")
    Integer post_uid(
            @Param("post_id") Integer post_id//查询的id
    );

    //删评论
    @Delete("delete from `comment` where id=#{comment}")
    void comment_delete(
            @Param("comment_id") Integer comment_id//删的评论id
    );

    //查询帖子发布者id
    @Select("select `uid` from `comment` where id=#{comment_id} ")
    Integer comment_uid(
            @Param("comment_id") Integer comment_id//查询的id
    );

    //举报
    @Insert("insert into `report` value(null,#{uid_p},#{uid_b},#{id_post},#{id_comment},#{time},#{reason},0,null,null) ")
    void report(
            @Param("uid_p") Integer uid_p,//举报人id
            @Param("uid_b") Integer uid_b,//被举报人id
            @Param("id_post") Integer id_post,//相关帖子id
            @Param("id_comment") Integer id_comment,//相关评论id
            @Param("time") DateTime time,//举报时间
            @Param("reason") String reason//举报原因
    );
}
