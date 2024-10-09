package com.dcstd.web.ecspserver.entity.outgoing;

import lombok.Data;

import java.util.ArrayList;

/**
 * @FileName Commrnt_parent
 * @Description
 * @Author fazhu
 * @date 2024-09-22
 **/
@Data
public class Comment_parent {
    private Integer pages;//总页数
    private ArrayList<Comment_father> comment_fathers;
}
