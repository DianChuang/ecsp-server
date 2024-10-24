package com.dcstd.web.ecspserver.service;

import com.dcstd.web.ecspserver.entity.outgoing.Course;
import com.dcstd.web.ecspserver.entity.outgoing.Course_form;
import com.dcstd.web.ecspserver.entity.outgoing.Course_form_father;
import com.dcstd.web.ecspserver.entity.outgoing.HomeActive;
import com.dcstd.web.ecspserver.mapper.HomeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @FileName HomeService
 * @Description
 * @Author fazhu
 * @date 2024-10-21
 **/
@Service
public class HomeService {
    @Autowired
    HomeMapper homeMapper;
//获取首页活动
    public HomeActive getActive(Integer page, Integer size) {
        HomeActive homeActive = new HomeActive();
        Integer pages = homeMapper.getActiveCount();
        if (pages % size != 0) {
            pages=pages/size+1;
        }else {
            pages = pages / size;
        }
        homeActive.setPages(pages);
        homeActive.setActiveLists(homeMapper.getFirstActive(page,size));

        return homeActive;
    }

    //获取热播课程(3条)
    public ArrayList<Course> getHotCourse() {
       return homeMapper.getHotCourse();
    }

    //获取课程列表
    public Course_form_father getAllCourse(Integer page, Integer limit) {
        Course_form_father course = new Course_form_father();
        Integer pages = homeMapper.getActiveCount();
        course.setPages(pages/limit+(pages%limit==0?0:1));
        course.setList(homeMapper.getAllCourse(limit * (page-1), limit));
        return course;
    }
}
