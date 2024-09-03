package com.dcstd.web.ecspserver.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BaseInfoMapper {

    //查询活动投票规则
    @Select("select rule_active from base_info limit 1")
    String selectActiveApplicantVoteRule();

    @Select("select version from base_info limit 1")
    String selectVersion();
}
