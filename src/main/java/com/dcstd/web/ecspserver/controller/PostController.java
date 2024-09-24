package com.dcstd.web.ecspserver.controller;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dcstd.web.ecspserver.common.Result;
import com.dcstd.web.ecspserver.entity.incoming.*;
import com.dcstd.web.ecspserver.entity.outgoing.*;
import com.dcstd.web.ecspserver.mapper.PostMapper;
import com.dcstd.web.ecspserver.service.PostService;
import com.dcstd.web.ecspserver.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * @FileName PostControLLer
 * @Description
 * @Author fazhu
 * @date 2024-08-15
 **/
@Slf4j
@RestController
@RequestMapping("/walls")
public class PostController {
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
                postService.post_insert(post_insert);//发帖操作
                return Result.success("发帖成功");
            }
            return Result.error(301, "数据不足", "数据缺少辣~");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(500, "操作失败", "寄，服务器出现问题了喵T^T");
        }
    }

    //发评论
    @PostMapping("/post/comment/insert")
    public Result CommentInsert(@RequestBody Comment_Insert comment_insert) {
        try {
            comment_insert.setUid(TokenUtils.getCurrentUser().getId());
            if (comment_insert.getUid() != null && comment_insert.getContent() != null && comment_insert.getId_post() != null && comment_insert.getId_parent() != null && comment_insert.getId_reply() != null) {
                comment_insert.setTime(new DateTime());
                postService.comment_insert(comment_insert);//发评论操作
                return Result.success("评论成功");
            }
            return Result.error(301, "数据不足", "数据缺少辣~");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(500, "操作失败", "寄，服务器出现问题了喵T^T");
        }
    }

    //帖子点赞
    @PostMapping("/post/tsan")
    public Result PostTsan(@RequestBody Post_tsan post_tsan) {
        try {
            post_tsan.setUid(TokenUtils.getCurrentUser().getId());
            if (postMapper.post_tsan_select(post_tsan.getUid(), post_tsan.getId_post()) == 0) {//判断是否点赞过
                post_tsan.setTime(new DateTime());
                if (post_tsan.getTime() != null && post_tsan.getId_post() != null && post_tsan.getUid() != null) {
                    postService.post_tsan(post_tsan);//帖子点赞操作
                    return Result.success("动态点赞成功");
                }
                return Result.error(301, "数据不足", "数据缺少辣~");
            }
            return Result.error(302, "重复点赞", "已点赞过了");
        } catch (Exception e) {
            return Result.error(500, "操作失败", "寄，服务器出现问题了喵T^T");
        }
    }

    // 评 论 点 赞
    @PostMapping("/comment/tsan")
    public Result CommentTsan(@RequestBody Comment_tsan comment_tsan) {
        try {
            comment_tsan.setUid(TokenUtils.getCurrentUser().getId());
            if (postMapper.comment_tsan_select(comment_tsan.getUid(), comment_tsan.getId_comment()) == 0) {//判断是否点赞过
                 comment_tsan.setTime(new DateTime());
                if (comment_tsan.getUid() != null && comment_tsan.getId_comment() != null && comment_tsan.getTime() != null) {
                    postService.comment_tsan(comment_tsan);//评论点赞操作
                    return Result.success("动态点赞成功");
                }
                return Result.error(301, "数据不足", "数据缺少辣~");
            }
            return Result.error(302, "重复点赞", "已点赞过了");
        } catch (Exception e) {
            return Result.error(500, "操作失败", "寄，服务器出现问题了喵T^T");
        }
    }

    //删帖子
    @PostMapping("/post/delete")
    public Result PostDelete(@RequestParam Integer post_id) {
        try {
            if (post_id != null) {
                if (postMapper.post_uid(post_id) == TokenUtils.getCurrentUser().getId()) {
                    postService.post_delete(post_id);//删帖子操作
                    return Result.success("帖子删除成功");
                }
                return Result.error(402, "token有误", "你的token和你的账户不匹配诶!");
            }
            return Result.error(301, "数据不足", "数据缺少辣~");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(500, "操作失败", "寄，服务器出现问题了喵T^T");
        }
    }

    //删评论
    @PostMapping("/comment/delete")
    public Result CommentDelete(@RequestParam Integer comment_id) {
        try {
            if (comment_id != null) {
                if (postMapper.comment_uid(comment_id) == TokenUtils.getCurrentUser().getId()) {
                    postService.comment_delete(comment_id);//删评论操作
                    return Result.success("评论删除成功");
                }
                return Result.error(402, "token有误", "你的token和你的账户不匹配诶!");
            }
            return Result.error(301, "数据不足", "数据缺少辣~");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(500, "操作失败", "寄，服务器出现问题了喵T^T");
        }
    }

    //举报
    @PostMapping("/report")
    public Result Report(@RequestBody Report report) {
        try {
            report.setUid_p(TokenUtils.getCurrentUser().getId());
            report.setTime(new DateTime());
            if (report.getId_comment() != null) {
                report.setUid_b(postMapper.comment_user_id(report.getId_comment()));//通过传入的comment_id获取被举报人
            } else {
                report.setUid_b(postMapper.post_user_id(report.getId_post()));//通过传入的post_id获取被举报人
            }
            if (report.getUid_p() != null && report.getUid_b() != null && (report.getId_post() != null || report.getId_comment() != null) && report.getTime() != null && report.getReason() != null) {
                postService.report(report);//举报操作
                return Result.success("举报成功，感谢您为社区的贡献");
            }
            return Result.error(301, "数据不足", "数据缺少辣~");
        } catch (Exception e) {
            return Result.error(500, "操作失败", "寄，服务器出现问题了喵T^T");
        }
    }

    //获取举报原因
     @GetMapping("/report/cause")
    public Result cause() {
        try {
            return Result.success(postService.report_cause());
        } catch (Exception e) {
            return Result.error(500, "操作失败", "寄，服务器出现问题了喵T^T");
        }
    }

    //获取分类类别
    @GetMapping("/post/category")
    public Result category() {
        try {
            return Result.success(postService.course_category());
        } catch (Exception e) {
            return Result.error(500, "操作失败", "寄，服务器出现问题了喵T^T");
        }
    }

    //获取联系方式
    @GetMapping("/post/contact")
    public Result contact(@RequestParam Integer post_id) {
        try {
            if (post_id != null) {
                Post_contact contact = postService.contact(post_id);
                if (contact == null) {
                    return Result.success("无联系方式");
                }
                return Result.success(contact);
            }
            return Result.error(301, "数据不足", "数据缺少辣~");
        } catch (Exception e) {
            return Result.error(500, "操作失败", "寄，服务器出现问题了喵T^T");
        }
    }

    //首页今日热榜
    @GetMapping("/list/first")
    public Result first() {
        try {
            return Result.success("", postMapper.first());
        } catch (Exception e) {
            return Result.error(500, "操作失败", "寄，服务器出现问题了喵T^T");
        }
    }

    //今日热榜
    @GetMapping("/list")
    public Result list(@RequestParam Integer page, @RequestParam Integer limit) {
        try {
            if (page!=null && limit!=null) {
                return Result.success(postService.host_list(page, limit));
            }
            return Result.error(301, "数据不足", "数据缺少辣~");
        } catch (Exception e) {
            return Result.error(500, "操作失败", "寄，服务器出现问题了喵T^T");
        }
    }
//
//    //首页新消息
//    @GetMapping("/home/notice")
//    public Result notice() {
//
//    }

    //校园墙列表
    @GetMapping("/post")
    public Result post_list(@RequestParam Integer type, @RequestParam Integer page, @RequestParam Integer limit) {
        try {
            if (type!=null && page!=null && limit != null ) {
                IPage data = postService.post_list(type, page, limit);
                if (data.getPages() >= limit) {
                    return Result.success(data);
                }
                return Result.error(300, "返回结果为空", "查不到数据捏 >.<");
            }
            return Result.error(301, "数据不足", "数据缺少或出错辣~");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(500, "操作失败", "寄，服务器出现问题了喵T^T");
        }
    }

//    //校园墙消息通知
//    @GetMapping("/post/notice")
//    public Result post_notice() {
//
//    }


    //帖子照片传输
    @PostMapping("/post/images")
    public Result post_image(@RequestBody Image_in images){
        try {
            postService.post_insert_image(images);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }
    //评论照片传输
    @PostMapping("/comment/images")
    public Result comment_image(@RequestBody Image_in images){
        try {
            if (images != null) {
            postService.comment_insert_image(images);
            return Result.success();
            }
            return Result.error(301, "数据不足", "数据缺少或出错辣~");
        } catch (Exception e) {
            return Result.error();
        }
    }

    //评论区父评论查询
    @GetMapping("/post/comment_father")
    public Result comment_parent(@RequestParam("post_id")Integer post_id, @RequestParam("page") Integer page, @RequestParam("limit") Integer limit){
        try {
            Integer pages;
            if(postMapper.parent_num()%limit==0){
                 pages = postMapper.parent_num()/limit;
            }else{
                 pages = postMapper.parent_num()/limit+1;
            }

            Comment_parent comment = new Comment_parent();
            ArrayList<Comment_father> comment_fathers = new ArrayList<>();
            for (int i = 0; i < limit; i++) {
                comment_fathers.add(i, postService.comment_parent(post_id,limit * (page - 1)+1+i));
            }
            comment.setComment_fathers(comment_fathers);

            comment.setPages(pages);
            return Result.success(comment) ;
        } catch (Exception e) {
            return Result.error();
        }
    }
    //评论区子评论查询
    @GetMapping("/post/comment_son")
    public Result comment_son( @RequestParam("comment_id") Integer comment_id, @RequestParam("page") Integer page,@RequestParam("limit") Integer limit){
        try {
            Integer pages;
            if(postMapper.son_num(comment_id)%limit==0){
                pages = postMapper.son_num(comment_id)/limit;
            }else{
                pages = postMapper.son_num(comment_id)/limit+1;
            }
            comment_son commentSon = new comment_son();
            commentSon.setPages(pages);
            ArrayList<Comment> comments = postMapper.comment_son(comment_id,limit * (page - 1), limit);
            commentSon.setComments(comments);
            return Result.success(commentSon);
        } catch (Exception e) {
            return Result.error();
        }
    }
}