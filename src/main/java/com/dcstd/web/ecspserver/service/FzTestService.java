package com.dcstd.web.ecspserver.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcstd.web.ecspserver.common.Result;
import com.dcstd.web.ecspserver.entity.outgoing.fzTest;
import com.dcstd.web.ecspserver.mapper.FzMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @FileName UserService
 * @Description
 * @Author fazhu
 * @date 2024-08-13
 **/
@RestController
public class FzTestService {
    @Autowired
    FzMapper fzMapper;


    @GetMapping("/a")
    public Result insert(Integer page,Integer limit){
        Page<fzTest> pages=new Page<>(page,limit);
        String pageResult=JSON.toJSONString(fzMapper.testPage(pages));
       Object a=JSON.parse(pageResult);
        return Result.success("200","查询成功",a);
    }
}
