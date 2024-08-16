package com.dcstd.web.ecspserver.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @FileName MybatisObjectHandler
 * @Description 自动填充创建，更新时间
 * @Author fazhu
 * @date 2024-08-13
 **/
@Slf4j
@Component
public class MybatisObjectHandler implements MetaObjectHandler {
//    @TableField(fill = FieldFill.INSERT)
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime",new Date(),metaObject);

    }
// @TableField(fill = FieldFill.INSERT_UPDATE)
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime",new Date(),metaObject);
    }
}
