package com.dcstd.web.ecspserver.controller;

import com.dcstd.web.ecspserver.common.AuthAccess;
import com.dcstd.web.ecspserver.common.Result;
import com.dcstd.web.ecspserver.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @FileName HomeController
 * @Description
 * @Author fazhu
 * @date 2024-10-21
 **/
@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
    HomeService homeService;

    //获得首页“活动 ”
    @AuthAccess
    @RequestMapping("/active")
    public Result getActive(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit) {
        try {
            if (page != null && limit != null) {
                return Result.success(homeService.getActive(page, limit));
            }else {
                return Result.error(301, "数据不足", "页码和每页条数不能为空");
            }
        } catch (Exception e) {
            return Result.error(500, "操作失败："+e.getMessage(), "寄，服务器出现问题了喵T^T");
        }
    }

    //获得三条首页“热播课程 ”
    @AuthAccess
    @RequestMapping("/course")
    public Result getHotCourse() {
        try {
            return Result.success(homeService.getHotCourse());
        }catch (Exception e) {
            return Result.error(500, "操作失败", "寄，服务器出现问题了喵T^T");
        }
    }

    //获得所有“热播课程 ”
    @AuthAccess
    @RequestMapping("/course/list")
    public Result getAllCourse(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit) {
        try {
            return Result.success(homeService.getAllCourse(page, limit));
        }catch (Exception e) {
            return Result.error(500, "操作失败", "寄，服务器出现问题了喵T^T");
        }
    }
}
