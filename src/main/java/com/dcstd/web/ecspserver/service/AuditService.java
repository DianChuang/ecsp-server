package com.dcstd.web.ecspserver.service;

import com.dcstd.web.ecspserver.entity.outgoing.CommentAudit;
import com.dcstd.web.ecspserver.entity.outgoing.PostAudit;
import com.dcstd.web.ecspserver.mapper.QueueMapper;
import com.dcstd.web.ecspserver.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @FileName RedisService
 * @Description
 * @Author fazhu
 * @date 2024-11-10
 **/
@Service
public class AuditService {

    @Autowired
    private QueueMapper queueMapper;
    @Autowired
    private RedisUtils redis;

    private final static String post="post_audit";//post审查队列
    private final static String comment="comment_audit";//comment审查队列

    //将post_id存入队列
    public void post_audit_set(int post_id) {
        redis.lSet(post, post_id);
    }

    //将comment_id存入队列
    public void comment_audit_set(int comment_id) {
        redis.lSet(comment, comment_id);
    }

    //从队列中获取post_id返回需要审查的信息
    public PostAudit post_audit_get()  {
        int post_id= 0;
        try {
            post_id = (int)redis.lGet(post);
        } catch (Exception e) {
            return null;
        }
        try {
            return queueMapper.getPostById(post_id);
        } catch (Exception e) {
            post_audit_set(post_id);//重新写入队列
            throw new RuntimeException(e);
        }
    }

    //从队列中获取comment_id返回需要审查的信息
    public CommentAudit comment_audit_get() {
        int comment_id= 0;
        try {
            comment_id = (int)redis.lGet(comment);
        }catch (Exception e) {
            return null;
        }
        try {
            return queueMapper.getCommentById(comment_id);
        }catch (Exception e) {
            comment_audit_set(comment_id);//重新写入队列
            throw new RuntimeException(e);
        }

    }

    //更新post状态码
    public void post_status(int post_id, int status) {
        queueMapper.updatePostStatus(post_id, status);
    }

    //更新comment状态码
    public void comment_status(int comment_id, int status) {
        queueMapper.updateCommentStatus(comment_id, status);
    }

}
