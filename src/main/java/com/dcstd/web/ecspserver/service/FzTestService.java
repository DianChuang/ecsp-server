package com.dcstd.web.ecspserver.service;

import com.dcstd.web.ecspserver.common.MyTask;
import com.dcstd.web.ecspserver.entity.outgoing.fzTest;
import com.dcstd.web.ecspserver.mapper.FzMapper;
import com.dcstd.web.ecspserver.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

/**
 * @FileName UserService
 * @Description
 * @Author fazhu
 * @date 2024-08-13
 **/
@Controller
public class FzTestService {
    @Autowired
    FzMapper fzMapper;

    @GetMapping("/ecsp/re")
    void insert(){
        MyTask myTask = new MyTask();
    }
}
