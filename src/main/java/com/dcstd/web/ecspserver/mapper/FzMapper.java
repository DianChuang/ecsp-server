package com.dcstd.web.ecspserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcstd.web.ecspserver.entity.outgoing.fzTest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @FileName UserMapper
 * @Description
 * @Author fazhu
 * @date 2024-08-13
 **/
@Mapper
public interface FzMapper extends BaseMapper<fzTest> {
    @Select("select * from user")
    Page<fzTest> testPage(Page<fzTest> page);
}
