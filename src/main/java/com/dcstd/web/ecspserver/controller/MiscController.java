package com.dcstd.web.ecspserver.controller;

import com.dcstd.web.ecspserver.common.Result;
import com.dcstd.web.ecspserver.mapper.MiscMapper;
import jakarta.annotation.Nullable;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class MiscController {

    @Resource
    MiscMapper miscMapper;

    @GetMapping("/home/hall/holidays")
    public Result getHolidays(@Nullable @RequestParam Integer type) {
        type = Optional.ofNullable(type).orElse(0);
        if (type == 0) {
            return Result.success(miscMapper.getHoliday());
        } else {
            return Result.success(miscMapper.getFestival());
        }

    }

}
