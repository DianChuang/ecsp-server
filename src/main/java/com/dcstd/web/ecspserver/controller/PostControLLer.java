package com.dcstd.web.ecspserver.controller;

import cn.hutool.core.date.DateTime;
import com.dcstd.web.ecspserver.common.Result;
import com.dcstd.web.ecspserver.entity.incoming.*;
import com.dcstd.web.ecspserver.mapper.PostMapper;
import com.dcstd.web.ecspserver.service.PostService;
import com.dcstd.web.ecspserver.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @FileName PostControLLer
 * @Description
 * @Author fazhu
 * @date 2024-08-15
 **/
@Slf4j
@RestController
@RequestMapping("/walls")
public class PostControLLer {
    @Autowired
    private PostService postService;
    @Autowired
    private PostMapper postMapper;

    //增加帖子
    @PostMapping("/post/insert")
    public Result PostInsert(@RequestBody Post_insert post_insert) {
        try {
            post_insert.setUid(TokenUtils.getCurrentUser().getId());
            if (post_insert.getUid() != null && post_insert.getContent() != null && post_insert.getId_category() != null && post_insert.getIs_anonymity() != null) {
                post_insert.setTime(new DateTime());
                postService.post_insert(post_insert);
                return Result.success("发帖成功");
            }
            return Result.error(301, "数据不足", "数据缺少辣~");
        } catch (Exception e) {
            return Result.error(500, "发帖失败", "寄，服务器出现问题了喵T^T");
        }
    }

    //发评论
    @PostMapping("/walls/post/comment/insert")
    public Result CommentInsert(@RequestBody Comment_Insert comment_insert) {
        try {
            comment_insert.setUid(TokenUtils.getCurrentUser().getId());
            if (comment_insert.getUid() != null && comment_insert.getContent() != null && comment_insert.getId_post() != null && comment_insert.getId_parent() != null && comment_insert.getId_reply() != null) {
                comment_insert.setTime(new DateTime());
                postService.comment_insert(comment_insert);
                return Result.success("评论成功");
            }
            return Result.error(301, "数据不足", "数据缺少辣~");
        } catch (Exception e) {
            return Result.error(500, "发帖失败", "寄，服务器出现问题了喵T^T");
        }
    }

    //帖子点赞
    @PostMapping("/walls/post/tsan")
    public Result PostTsan(@RequestBody Post_tsan post_tsan) {
        try {
            post_tsan.setUid(TokenUtils.getCurrentUser().getId());
            post_tsan.setTime(new DateTime());
            if (post_tsan.getTime() != null && post_tsan.getId_post() != null && post_tsan.getUid() != null) {
                postService.post_tsan(post_tsan);
                return Result.success("动态点赞成功");
            }
            return Result.error(301, "数据不足", "数据缺少辣~");
        } catch (Exception e) {
            return Result.error(500, "发帖失败", "寄，服务器出现问题了喵T^T");
        }
    }

    //评论点赞
    @PostMapping("/walls/comment/tsan")
    public Result CommentTsan(@RequestBody Comment_tsan comment_tsan) {
        try {
            comment_tsan.setUid(TokenUtils.getCurrentUser().getId());
            comment_tsan.setTime(new DateTime());
            if (comment_tsan.getUid() != null && comment_tsan.getId_comment() != null && comment_tsan.getTime() != null) {
                postService.comment_tsan(comment_tsan);
                return Result.success("动态点赞成功");
            }
            return Result.error(301, "数据不足", "数据缺少辣~");
        } catch (Exception e) {
            return Result.error(500, "发帖失败", "寄，服务器出现问题了喵T^T");
        }
    }

    //删帖子
    @PostMapping("/walls/post/delete")
    public Result PostDelete(@RequestParam Integer post_id) {
        try {
            if (post_id != null) {
                if (postMapper.post_uid(post_id) == TokenUtils.getCurrentUser().getId()) {
                    postService.post_delete(post_id);
                    return Result.success("帖子删除成功");
                }
                return Result.error(402, "token有误", "你的token和你的账户不匹配诶!");
            }
            return Result.error(301, "数据不足", "数据缺少辣~");
        } catch (Exception e) {
            return Result.error(500, "发帖失败", "寄，服务器出现问题了喵T^T");
        }
    }


    //删评论
    @PostMapping("/walls/comment/delete")
    public Result CommentDelete(@RequestParam Integer comment_id) {
        try {
            if (comment_id != null) {
                if (postMapper.comment_uid(comment_id) == TokenUtils.getCurrentUser().getId()) {
                    postService.comment_delete(comment_id);
                    return Result.success("评论删除成功");
                }
                return Result.error(402, "token有误", "你的token和你的账户不匹配诶!");
            }
            return Result.error(301, "数据不足", "数据缺少辣~");
        } catch (Exception e) {
            return Result.error(500, "发帖失败", "寄，服务器出现问题了喵T^T");
        }
    }

    //举报
    @PostMapping("/walls/report")
    public Result Report(@RequestBody Report report) {
        try {
            report.setTime(new DateTime());
            if (report.getUid_p() != null && report.getUid_b() != null && (report.getId_post() != null || report.getId_comment() != null) && report.getTime() != null && report.getReason() != null) {
                postService.report(report);
                return Result.success("举报成功，感谢您为社区的贡献");
            }
            return Result.error(301, "数据不足", "数据缺少辣~");
        } catch (Exception e) {
            return Result.error(500, "举报失败", "寄，服务器出现问题了喵T^T");
        }
    }


}