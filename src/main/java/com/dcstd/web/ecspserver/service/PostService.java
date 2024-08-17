package com.dcstd.web.ecspserver.service;

import com.dcstd.web.ecspserver.entity.incoming.*;
import com.dcstd.web.ecspserver.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @FileName PostService
 * @Description
 * @Author fazhu
 * @date 2024-08-15
 **/
@Service
public class PostService {
    @Autowired
    private PostMapper postMapper;

    //发帖
    public void post_insert(Post_insert post_insert) {
        postMapper.post_insert(post_insert.getUid(), post_insert.getContent(), post_insert.getId_category(), post_insert.getTime(), post_insert.getIs_anonymity(), post_insert.getContact_name(), post_insert.getContact_type(), post_insert.getContact_number());
        if (post_insert.getPath() != null) {//判断需不需要存图片
            postMapper.post_insert_image(post_insert.getPath());
        }
    }

    //发评论
    public void comment_insert(Comment_Insert comment_insert) {
        postMapper.comment_insert(comment_insert.getContent(), comment_insert.getId_post(), comment_insert.getId_parent(), comment_insert.getId_reply(), comment_insert.getTime());
        if (comment_insert.getPath() != null) {//判断需不需要存图片
            postMapper.comment_image_lib(comment_insert.getPath());
        }
    }

    //评论点赞
    public void comment_tsan(Comment_tsan comment_tsan) {
        postMapper.comment_tsan(comment_tsan.getUid(), comment_tsan.getId_comment(), comment_tsan.getTime());
    }

    //帖子点赞
    public void post_tsan(Post_tsan post_tsan) {
        postMapper.post_tsan(post_tsan.getUid(), post_tsan.getId_post(), post_tsan.getTime());
    }

    //删除帖子
    public void post_delete(Integer post_id) {
        postMapper.post_delete(post_id);
    }

    //删除评论
    public void comment_delete(Integer comment_id) {
        postMapper.comment_delete(comment_id);
    }

    //举报
    public void report(Report report) {
        postMapper.report(report.getUid_p(), report.getUid_b(), report.getId_post(), report.getId_comment(), report.getTime(), report.getReason());
    }
}
