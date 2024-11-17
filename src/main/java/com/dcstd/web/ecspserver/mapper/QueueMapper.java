package com.dcstd.web.ecspserver.mapper;

import com.dcstd.web.ecspserver.entity.outgoing.CommentAudit;
import com.dcstd.web.ecspserver.entity.outgoing.PostAudit;
import org.apache.ibatis.annotations.*;

/**
 * @FileName QueueMapper
 * @Description
 * @Author fazhu
 * @date 2024-11-10
 **/
@Mapper
public interface QueueMapper {

    //查询需要审查post的信息
    @Select("select a.id,a.uid,a.content,b.name from post as a left join post_category as b on a.id_category=b.id where a.id =#{id}")
    PostAudit getPostById(int id);

    //查询需要审查post的信息
    @Select("select id,uid,content from post where id = #{id}")
    CommentAudit getCommentById(int id);

    //更新post状态码
    @Update("update post set status=#{status} where id = #{postId}")
    void updatePostStatus(int postId, int status);

    //更新comment状态码
    @Update("update comment set status=#{status} where id = #{commentId}")
    void updateCommentStatus(int commentId, int status);



}
