package com.dcstd.web.ecspserver.service;

import cn.hutool.core.date.DateTime;
import com.dcstd.web.ecspserver.entity.incoming.Post_insert;
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

    public void post_insert(Post_insert post_insert){
       post_insert.setUid(1);
       post_insert.setTime(new DateTime());
       postMapper.post_insert(post_insert.getUid(), post_insert.getContent(),post_insert.getId_category(),post_insert.getTime(),post_insert.getIs_anonymity(),post_insert.getContact_name(),post_insert.getContact_type(),post_insert.getContact_number());
       if(post_insert.getPath()!=null){postMapper.post_insert_image(post_insert.getPath());}
    }



}
