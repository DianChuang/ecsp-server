package com.dcstd.web.ecspserver.entity.outgoing;

import lombok.Data;

import java.util.ArrayList;

/**
 * @FileName Comment_father
 * @Description
 * @Author fazhu
 * @date 2024-09-14
 **/
@Data
public class Comment_father {
    private Comment father;//父评论
    private ArrayList<Comment> son;//两条子评论
}
