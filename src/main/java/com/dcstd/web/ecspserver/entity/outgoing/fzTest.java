package com.dcstd.web.ecspserver.entity.outgoing;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * @FileName User
 * @Description
 * @Author fazhu
 * @date 2024-08-13
 **/
@Data
public class fzTest {
    private Integer id;
    private String name;
    private Integer age;
    private String email;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
