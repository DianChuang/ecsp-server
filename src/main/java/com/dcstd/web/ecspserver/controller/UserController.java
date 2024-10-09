package com.dcstd.web.ecspserver.controller;

import com.dcstd.web.ecspserver.common.AuthAccess;
import com.dcstd.web.ecspserver.common.Result;
import com.dcstd.web.ecspserver.entity.User;
import com.dcstd.web.ecspserver.entity.outgoing.*;
import com.dcstd.web.ecspserver.mapper.UserInfoMapper;
import com.dcstd.web.ecspserver.service.UserService;
import com.dcstd.web.ecspserver.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * @FileName UserController
 * @Description
 * @Author fazhu
 * @date 2024-09-22
 **/
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    UserService userService;

    @GetMapping("/individual")
    public Result individual() {//获取点赞数，粉丝数，关注数
        try {
            Integer user_id = TokenUtils.getCurrentUser().getId();
            Integer tsan = userService.zan(user_id);
            UserInfo_num userInfoNum = new UserInfo_num();
            userInfoNum.setFan(userInfoMapper.getFanNum(user_id));
            userInfoNum.setFollow(userInfoMapper.getFollowNum(user_id));
            userInfoNum.setTsan(tsan);
            return Result.success(userInfoNum);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/information/select")
    public Result getUserSelect() {//获取用户信息
        try {
            Integer user_id = TokenUtils.getCurrentUser().getId();
            return Result.success(userInfoMapper.getUserInfo(user_id));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    //更新用户信息
    @PostMapping("/information/update")
    public Result updateUserInfo(@RequestBody User_info user_info) {
        try {
            Integer user_id = TokenUtils.getCurrentUser().getId();
            userInfoMapper.updateUserInfo(user_id, user_info.getId_student(), user_info.getProfile_intro(), user_info.getNickname(), user_info.getGender());
            return Result.success();
        } catch (Exception e) {
            return Result.error();
        }
    }

    //更新用户头像
    @PostMapping("/information/update/avatar")
    public Result updateUserAvatar(@RequestParam String avatar) {
        try {

            Integer user_id = TokenUtils.getCurrentUser().getId();
            userInfoMapper.updateAvatar(avatar, user_id);
            return Result.success();
        } catch (Exception e) {
            return Result.error();
        }
    }

    //获取“我的动态”
    @GetMapping("/dynamic")
    public Result Dynamic(@RequestParam Integer page, @RequestParam Integer limit) {
        try {
            Integer id=TokenUtils.getCurrentUser().getId();
            My_post_page myPost = new My_post_page();
            Integer pages = userInfoMapper.getPostNum(id);
            if (pages% limit != 0) {
                myPost.setPages((pages / limit) + 1);
            }else {
                myPost.setPages((pages / limit));
            }
            myPost.setPosts(userInfoMapper.getPost(id, limit * (page - 1), limit));
            return Result.success(myPost);
        } catch (Exception e) {
            return Result.error();
        }
    }

    //获取"我的部落"
    @GetMapping("/tribe")
    public Result tribe(@RequestParam Integer page, @RequestParam Integer limit) {
        try {
            Integer id=TokenUtils.getCurrentUser().getId();
            My_group myGroup = new My_group();
            Integer pages = userInfoMapper.getGroupNum(id);
            if (pages % limit != 0) {
                myGroup.setPages((pages / limit) + 1);
            }else{
                myGroup.setPages(pages/limit);
            }

            myGroup.setGroups(userInfoMapper.getGroup(id, limit * (page - 1), limit));
            return Result.success(myGroup);
        } catch (Exception e) {
            return Result.error();
        }
    }

    @AuthAccess
    @GetMapping("/active")
    public Result active(@RequestParam Integer page, @RequestParam Integer limit) {
        try {
            Integer id = 1;
            My_active_page myActivePage = new My_active_page();
            Integer pages = userInfoMapper.getActiveNum(id);
            if (pages%limit!=0){
                myActivePage.setPages(pages/limit+1);
            }else{
                myActivePage.setPages(pages/limit);
            }
            myActivePage.setActives(userInfoMapper.getActive(id, limit * (page - 1), limit));
            return Result.success(myActivePage);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }
}
