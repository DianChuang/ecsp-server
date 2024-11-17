package com.dcstd.web.ecspserver.mapper;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.dcstd.web.ecspserver.entity.outgoing.*;
import com.dcstd.web.ecspserver.entity.outgoing.Course_category;
import com.dcstd.web.ecspserver.entity.outgoing.Post_contact;
import com.dcstd.web.ecspserver.entity.outgoing.Post;
import com.dcstd.web.ecspserver.entity.outgoing.Report_cause;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.*;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName PostMapper
 * @Description
 * @Author fazhu
 * @date 2024-08-15
 **/

@Mapper
public interface PostMapper extends BaseMapper<Post> {
    @Select("select Last_INSERT_ID()")
    Integer getLast_insert_id();

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
    @Insert("insert into `comment` value(null,#{uid},#{content},#{id_post},#{id_parent},#{id_reply},0,#{time})")
    void comment_insert(
            @Param("uid") Integer uid,//发评论者
            @Param("content") String content,//正文
            @Param("id_post") Integer id_post,//所属动态id
            @Param("id_parent") Integer id_parent,//父内容id
            @Param("id_reply") Integer id_reply,//回复id
            @Param("time") DateTime time//评论时间
    );

    //发评论增加评论数
    @Update("update post set comment_num=comment_num+1 where id=#{id_post}")
    void comment_insert_num(
            @Param("id_post") Integer id_post//所属动态id
    );

    //发评论增加图片
    @Insert("insert into `comment_image_lib` value(null,(select last_insert_id()),#{path},null,#{sort})")
    void comment_image_lib(
            @Param("path") String path,//图片地址
            @Param("sort") Integer sort//图片地址
    );

    //评论点赞
    @Insert("insert into `comment_tsan` value(null,#{uid},#{id_comment},#{time})")
    void comment_tsan(
            @Param("uid") Integer uid,//点赞者id
            @Param("id_comment") Integer id_comment,//点赞评论id
            @Param("time") DateTime time//点赞时间
    );

    @Update("update `comment_tsan`set tsan_num=tsan_num+1 where id=#{id_post}")
    void comment_tsan_num(
            @Param("id_comment") Integer id_comment//点赞评论id
    );

    //帖子点赞
    @Insert("insert into `post_tsan` value(null,#{uid},#{id_post},#{time})")
    void post_tsan(
            @Param("uid") Integer uid,//点赞者id
            @Param("id_post") Integer id_post,//点赞帖子id
            @Param("time") DateTime time//点赞时间
    );

    //帖子点赞2
    @Update("update post set tsan_num=tsan_num+1 where id=#{id_post}")
    void post_tsan_num(
            @Param("id_post") Integer id_post//点赞帖子id
    );

    //查询帖子是否点过赞，有返回1，没有则返回零
    @Select("select exists (select 1 from post_tsan where uid=#{uid} and id_post=#{id_post})")
    Integer post_tsan_select(
            @Param("uid") Integer uid,//点赞者id
            @Param("id_post") Integer id_post//被点赞的帖子
    );

    //查询评论是否点过赞
    @Select("select exists (select 1 from comment_tsan where uid=#{uid} and id_comment=#{id_comment})")
    Integer comment_tsan_select(
            @Param("uid") Integer uid,//点赞者id
            @Param("id_comment") Integer id_comment//被点赞的帖子
    );

    //删帖
    @Update("update `post` set status=-2 where id=#{post_id}")
    void post_delete(
            @Param("post_id") Integer post_id//删的id
    );
    //删帖子的点赞记录
    @Delete("delete from post_tsan where id_post=#{post_id}")
    void post_delete_tsan(
            @Param("post_id") Integer post_id//删帖子的id
    );
    //获取post对应的评论id
    @Select("select id from comment where id_post=#{post_id} and status!=-2")
    ArrayList<Integer> post_comment_id(
            @Param("post_id") Integer post_id
    );
    //删帖子的评论
    @Update("update `comment` set  status=-2 where id_post=#{post_id}")
    void post_delete_comment(
            @Param("post_id") Integer post_id//删帖子的id
    );
    //删评论的点赞
    @Delete("delete from comment_tsan where id_comment=(select id from comment where id_post=#{post_id} and status=-2)")
    void comment_delete_tsan(
            @Param("post_id") Integer post_id//删评论的id
    );

    //查询帖子发布者id
    @Select("select `uid` from `post` where id=#{post_id} ")
    Integer post_uid(
            @Param("post_id") Integer post_id//查询的id
    );

    //删评论
    @Update("Update  `comment` set status=-2 where id=#{comment_id}")
    void comment_delete(
            @Param("comment_id") Integer comment_id//删的评论id
    );
    @Delete("Delete from `comment_tsan` where id_comment=#{comment_id} ")
    void comment_tsan_delete(
            @Param("comment_id") Integer comment_id//删的评论id
    );
    //查询评论发布者id
    @Select("select `uid` from `comment` where id=#{comment_id} ")
    Integer comment_uid(
            @Param("comment_id") Integer comment_id//查询的id
    );

    //举报
    @Insert("insert into `report` value(null,#{uid_p},#{uid_b},#{id_post},#{id_comment},null,#{time},#{reason},0,null,null,#{description}) ")
    void report(
            @Param("uid_p") Integer uid_p,//举报人id
            @Param("uid_b") Integer uid_b,//被举报人id
            @Param("id_post") Integer id_post,//相关帖子id
            @Param("id_comment") Integer id_comment,//相关评论id
            @Param("time") DateTime time,//举报时间
            @Param("reason") String reason,//举报原因id
            @Param("description") String description//更多描述
    );

    //找帖子主人
    @Select("select uid from post where id=#{id_post}")
    Integer post_user_id(
            @Param("id_post") Integer id_post
    );

    //找评论主人
    @Select("select uid from comment where id=#{id_comment}")
    Integer comment_user_id(
            @Param("id_comment") Integer id_comment
    );

    //获取举报原因
    @Select("select id,name from `report_info` where status= 1")
    List<Report_cause> report_cause();

    //获取分类类别
    @Select("select * from `course_category`")
    List<Course_category> course_category();

    //取联系方式
    @Select("select contact_number,contact_name,contact_type from `post` where id=#{post_id} and status=1")
    Post_contact contact(
            @Param("post_id") Integer post_id
    );

    //首页今日热榜
    @Select("select content from post where id=(" +//查询正文
            "select `id_post`from post_tsan " +
            "where time>now()  -INTERVAL 1 DAY   " +//只查最近一天的点赞
            "GROUP BY `id_post` " +
            "order by `id_post`ASC limit 0,1" +//筛选最高的第一个
            ")")
    String first();

    //今日热榜
    @Select("select `id_post`from post_tsan as d " +
            "    where time>now()  -INTERVAL 1 DAY " +
            "    GROUP BY `id_post` " +
            "    order by COUNT(*) desc " +
            " limit ${page},${limit}")
    ArrayList<Integer> hot(
            @Param("page") Integer page,
            @Param("limit") Integer limit
    );

    //统计行数
    @Select("select  COUNT(*)  from `post_tsan` where time>now()  -INTERVAL 1 DAY")
    Integer row();


    //按照排序查询帖子
    @Select("select a.id,a.content,a.tsan_num ,a.comment_num,a.time,b.avatar,b.nickname,c.path, " +
            "(select exists (select 1 from post_tsan where uid=b.uid and id_post=a.id)) AS tsan_state " +
            "from post as a join user_info as b " +
            "left join post_image_lib as c " +
            "on a.uid=b.uid and c.id_post=a.id " +
            "where a.id in (${id})  " +
            "order by field(a.id,${id})  ")
    ArrayList<Post> hot_list(
            @Param("id") String id
    );

    //校园墙列表_1
    @Select("select a.id,a.content,a.tsan_num ,a.comment_num,a.time,b.avatar,b.nickname,c.path," +
            "(select exists (select 1 from post_tsan where uid=b.uid and id_post=a.id)) AS tsan_state " +
            "from post as a " +
            "join user_info as b " +
            "left join post_image_lib as c " +
            "on a.uid=b.uid and c.id_post=a.id " +
            "where a.status=1 " +
            "order by  a.time desc")
    IPage<Post> post_list_1(Page<Post> page);

    //校园墙列表_2
    @Select("select a.id,a.content,a.tsan_num ,a.comment_num,a.time,b.avatar,b.nickname,c.path," +
            "(select exists (select 1 from post_tsan where uid=b.uid and id_post=a.id)) AS tsan_state " +
            "from post as a " +
            "join user_info as b " +
            "left join post_image_lib as c " +
            "on a.uid=b.uid and c.id_post=a.id " +
            "where a.status=1 and a.id_category=1 " +
            "order by  a.tsan_num desc")
    IPage<Post> post_list_2(Page<Post> page);

    //校园墙列表_3
    @Select("select a.id,a.content,a.tsan_num ,a.comment_num,a.time,b.avatar,b.nickname,c.path," +
            "(select exists (select 1 from post_tsan where uid=b.uid and id_post=a.id)) AS tsan_state " +
            "from post as a " +
            "join user_info as b " +
            "left join post_image_lib as c " +
            "on a.uid=b.uid and c.id_post=a.id " +
            "where a.status=1 and a.id_category=2 " +
            "order by  a.tsan_num desc")
    IPage<Post> post_list_3(Page<Post> page);

    //校园墙列表_4
    @Select("select a.id,a.content,a.tsan_num ,a.comment_num,a.time,b.avatar,b.nickname,c.path,(select exists (select 1 from post_tsan where uid=b.uid and id_post=a.id)) AS tsan_state " +
            "from post as a " +
            "join user_info as b " +
            "left join post_image_lib as c " +
            "on a.uid=b.uid and c.id_post=a.id " +
            "where a.status=1 and a.id_category=3 " +
            "order by  a.tsan_num desc")
    IPage<Post> post_list_4(Page<Post> page);

    //校园墙列表_5
    @Select("select a.id,a.content,a.tsan_num ,a.comment_num,a.time,b.avatar,b.nickname,c.path,(select exists (select 1 from post_tsan where uid=b.uid and id_post=a.id)) AS tsan_state " +
            "from post as a " +
            "join user_info as b " +
            "left join post_image_lib as c " +
            "on a.uid=b.uid and c.id_post=a.id " +
            "where a.status=1 and a.id_category=4 " +
            "order by  a.tsan_num desc")
    IPage<Post> post_list_5(Page<Post> page);

    //帖子照片传输
    @Insert("insert into `post_image_lib` (id_post,intro,path) values(#{id_post},#{intro},#{path}) ")
    void insert_post_image(
            @Param("id_post") Integer id_post,//帖子id
            @Param("intro") String intro,//图片介绍
            @Param("path") String path//图片地址
    );

    //评论图片传输
    @Insert("insert into `comment_image_lib` (id_comment,path,intro,sort) values (#{id_comment},#{path},#{intro},#{sort})")
    void insert_comment_image(
            @Param("id_comment") Integer id_comment,//评论id
            @Param("path") String path,//图片地址
            @Param("intro") String intro,//图片介绍
            @Param("sort") Integer sort//图片排序
    );


    //查询父评论数量
    @Select("select COUNT(*) from comment  where id_parent=-1 ")
    Integer parent_num();
    //查询子评论数量
    @Select("select COUNT(*) from comment  where id_parent=#{comment_id} ")
    Integer son_num(
            @Param("comment_id") Integer comment_id//评论id
    );

    //请求父评论
    @Select("select a.id,b.avatar,b.nickname,a.content,a.time,(select COUNT(*) from comment_tsan  where id_comment=a.id) as tsan_num,(select COUNT(*) from comment where  id_parent=a.id)as comment_num " +
            "from comment as a left join user_info as b  " +
            "on a.uid=b.uid " +
            "where a.status=1 and a.id_post=#{post_id} and a.id_parent=-1 " +
            "order by  tsan_num desc " +
            " limit ${page},${limit} ")
    Comment comment_parent(
            @Param("post_id") Integer post_id,
            @Param("page") Integer page,
            @Param("limit") Integer limit
    );

    //请求子评论
    @Select("select a.id,b.avatar,b.nickname,a.content,a.time,(select COUNT(*) from comment_tsan  where id_comment=a.id) as tsan_num,(select COUNT(*) from comment where  id_parent=a.id)as comment_num " +
            "from comment as a left join user_info as b  " +
            "on a.uid=b.uid " +
            "where a.status=1 and a.id_parent=#{comment_id} " +
            "order by  tsan_num desc " +
            " limit ${page},${limit} ")
    ArrayList<Comment> comment_son(
            @Param("comment_id") Integer comment_id,
            @Param("page") Integer page,
            @Param("limit") Integer limit
    );




}
