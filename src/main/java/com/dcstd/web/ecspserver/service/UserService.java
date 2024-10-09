package com.dcstd.web.ecspserver.service;

import com.dcstd.web.ecspserver.mapper.UserInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @FileName UserService
 * @Description
 * @Author fazhu
 * @date 2024-09-22
 **/
@Slf4j
@Service
public class UserService {
    @Autowired
   private UserInfoMapper userInfoMapper;

    // 获取用户的总点赞
    public Integer zan(Integer user_id) {
        Integer post_tsan=0,comment_tsan=0;
        ArrayList<Integer> ArrPostTsan = userInfoMapper.getPostId(user_id);
        for (int i=0;i<ArrPostTsan.size();i++){
            post_tsan+=ArrPostTsan.get(i);
        }
        comment_tsan=userInfoMapper.getCommentNum(user_id);
        return post_tsan+comment_tsan;
    }
}
