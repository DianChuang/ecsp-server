package com.dcstd.web.ecspserver.controller;

import com.dcstd.web.ecspserver.common.AuthAccess;
import com.dcstd.web.ecspserver.common.Result;
import com.dcstd.web.ecspserver.entity.outgoing.Comment;
import com.dcstd.web.ecspserver.entity.outgoing.CommentAudit;
import com.dcstd.web.ecspserver.entity.outgoing.PostAudit;
import com.dcstd.web.ecspserver.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @FileName AuditController
 * @Description
 * @Author fazhu
 * @date 2024-11-11
 **/
@RestController
@RequestMapping("/backend")
public class AuditController {
    @Autowired
    private AuditService auditService;

    /**
     * @Description 获取需要审核的帖子
     * @return
     */
    @AuthAccess
    @GetMapping("/post/audit")
    public Result post() {
        try {
            PostAudit post=auditService.post_audit_get();
            if(post==null){
                return Result.error(400, "操作失败", "没有需要审核的内容哦~");
            }
            return Result.success(post);
        } catch (Exception e) {
            return Result.error(500, "操作失败", "寄，服务器出现问题了喵T^T");
        }
    }

    /**
     * @Description 提交审核的帖子
     * @param id
     * @param status
     * @return
     */
    @AuthAccess
    @PostMapping("/post/submit")
    public Result post_audit(@RequestParam int id, @RequestParam int status) {
        try {
            auditService.post_status(id, status);
            return Result.success("操作成功");
        } catch (Exception e) {
            return Result.error(500, "操作失败", "寄，服务器出现问题了喵T^T");
        }
    }

    /**
     * @Description 获取需要审核的评论
     * @return
     */
    @AuthAccess
    @GetMapping("/comment/audit")
    public Result comment() {
        try {
            CommentAudit comment=auditService.comment_audit_get();
            if(comment==null){
                return Result.error(400, "操作失败", "没有需要审核的内容哦~");
            }
            return Result.success(comment);
        } catch (Exception e) {
            return Result.error(500, "操作失败", "寄，服务器出现问题了喵T^T");
        }
    }

    /**
     * @Description 提交审核的评论
     * @param id
     * @param status
     * @return
     */
    @AuthAccess
    @PostMapping("/comment/submit")
    public Result comment_audit(@RequestParam int id, @RequestParam int status) {
        try {
            auditService.comment_status(id, status);
            return Result.success("操作成功");
        } catch (Exception e) {
            return Result.error(500, "操作失败", "寄，服务器出现问题了喵T^T");
        }
    }

    /**
     * @Description 帖子管理
     * @return
     */
    @AuthAccess
    @PostMapping("/post/management")
    public Result post_management() {
        return null;
    }

    /**
     * @Description 评论管理
     * @return
     */
    @AuthAccess
    @PostMapping("/comment/management")
    public Result comment_management() {
        return null;
    }
}
