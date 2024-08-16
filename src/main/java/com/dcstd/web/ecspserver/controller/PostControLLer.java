package com.dcstd.web.ecspserver.controller;

import com.dcstd.web.ecspserver.common.Result;
import com.dcstd.web.ecspserver.entity.incoming.Post_insert;
import com.dcstd.web.ecspserver.mapper.PostMapper;
import com.dcstd.web.ecspserver.service.PostService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/post/insert")
    public Result PostInsert(@RequestBody Post_insert post_insert) {
        try {
            if (post_insert.getContent()!=null && post_insert.getId_category()!=null && post_insert.getIs_anonymity()!=null) {
//        此处通过took获取uid存入post_insert中
                postService.post_insert(post_insert);
                return Result.success("发帖成功");
            }else {
                return Result.error("数据不足");//要改
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("发帖失败");//要改
        }
    }


}
