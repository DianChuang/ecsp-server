package com.dcstd.web.ecspserver.service;

import com.dcstd.web.ecspserver.mapper.BaseInfoMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class BaseInfoService {

    @Resource
    BaseInfoMapper baseInfoMapper;

    public String getActiveApplicantVoteRule() {
        return baseInfoMapper.selectActiveApplicantVoteRule();
    }

    public String getVersion() {
        return baseInfoMapper.selectVersion();
    }
}
