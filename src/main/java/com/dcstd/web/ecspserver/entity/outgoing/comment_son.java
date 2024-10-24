package com.dcstd.web.ecspserver.entity.outgoing;

import lombok.Data;

import java.util.ArrayList;

/**
 * @FileName comment_son
 * @Description
 * @Author fazhu
 * @date 2024-09-22
 **/
@Data
public class comment_son {
    private Integer pages;//总页数
    private ArrayList<Comment> comments;//评论列表
}
