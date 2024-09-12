package com.dcstd.web.ecspserver.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcstd.web.ecspserver.common.Result;
import com.dcstd.web.ecspserver.entity.incoming.*;
import com.dcstd.web.ecspserver.entity.outgoing.*;
import com.dcstd.web.ecspserver.mapper.PostMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName PostService
 * @Description
 * @Author fazhu
 * @date 2024-08-15
 **/
@Slf4j
@Service
public class PostService {
    @Autowired
    private PostMapper postMapper;

    //发帖
    public void post_insert(Post_insert post_insert) {
        postMapper.post_insert(post_insert.getUid(), post_insert.getContent(), post_insert.getId_category(), post_insert.getTime(), post_insert.getIs_anonymity(), post_insert.getContact_name(), post_insert.getContact_type(), post_insert.getContact_number());
    }

    //发评论
    public void comment_insert(Comment_Insert comment_insert) {
        //发评论
        postMapper.comment_insert(comment_insert.getUid(), comment_insert.getContent(), comment_insert.getId_post(), comment_insert.getId_parent(), comment_insert.getId_reply(), comment_insert.getTime());
        postMapper.comment_insert_num(comment_insert.getId_post());
    }

    //评论点赞
    public void comment_tsan(Comment_tsan comment_tsan) {
        postMapper.comment_tsan(comment_tsan.getUid(), comment_tsan.getId_comment(), comment_tsan.getTime());
        postMapper.comment_tsan_num(comment_tsan.getId_comment());
    }

    //帖子点赞
    public void post_tsan(Post_tsan post_tsan) {
        postMapper.post_tsan(post_tsan.getUid(), post_tsan.getId_post(), post_tsan.getTime());
    }

    //删除帖子
    public void post_delete(Integer post_id) {
        postMapper.post_delete(post_id);
        postMapper.post_delete_tsan(post_id); //删除帖子的点赞
        postMapper.post_delete_comment(post_id);
        postMapper.comment_delete_tsan(post_id);
    }

    //删除评论
    public void comment_delete(Integer comment_id) {
        postMapper.comment_delete(comment_id);
        postMapper.comment_tsan_delete(comment_id);
    }

    //举报
    public void report(Report report) {
        postMapper.report(report.getUid_p(), report.getUid_b(), report.getId_post(), report.getId_comment(), report.getTime(), report.getReason().toString(), report.getDescription());
    }

    //获取举报原因
    public List<Report_cause> report_cause() {
        return postMapper.report_cause();
    }

    //获取分类类别
    public List<Course_category> course_category() {
        return postMapper.course_category();
    }

    //获取联系方式
    public Post_contact contact(Integer post_id) {
        return postMapper.contact(post_id);
    }

    //校园墙列表
    public IPage post_list(Integer type, Integer page, Integer limit) {
        Page<Post> pages = new Page<Post>(page, limit);
        IPage<Post> data = null;
        switch (type) {
            case 1:
                data = postMapper.post_list_1(pages);
                break;
            case 2:
                data = postMapper.post_list_2(pages);
                break;
            case 3:
                data = postMapper.post_list_3(pages);
                break;
            case 4:
                data = postMapper.post_list_4(pages);
                break;
            case 5:
                data = postMapper.post_list_5(pages);
                break;
        }
        return data;
    }

    //首页今日热榜(唯一)
    public String first() {
        return postMapper.first();
    }

    //今日热榜
    public Pages host_list(Integer page, Integer limit) {
        Pages pages = new Pages();
        ArrayList<Integer> list = postMapper.hot(limit * (page - 1), limit);
        String id = "" + list.get(0);
        for (int i = 1; i < list.size(); i++) {//组装筛选条件
            id = id + "," + list.get(i).toString();
        }
        ArrayList<Post> data = postMapper.hot_list(id);
        pages.setPages((int) Math.ceil((double) postMapper.row() / limit));
        pages.setDate(data);
        return pages;
    }

    //帖子图片上传
    public void post_insert_image(Image_in images) {
        for (int i = 0; i < images.getImages().size(); i++) {
            postMapper.insert_post_image(images.getId(), images.getImages().get(i).getIntro(), images.getImages().get(i).getPath());
        }
    }
    //评论图片上传
    public void comment_insert_image(Image_in images) {
        for (int i = 0; i < images.getImages().size(); i++) {
            postMapper.insert_comment_image(images.getId(), images.getImages().get(i).getIntro(), images.getImages().get(i).getPath(),images.getImages().get(i).getSort());
        }
    }

    ;

}
