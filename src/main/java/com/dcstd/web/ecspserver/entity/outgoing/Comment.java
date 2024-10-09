package com.dcstd.web.ecspserver.entity.outgoing;

import cn.hutool.core.date.DateTime;
import com.dcstd.web.ecspserver.entity.incoming.Image;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

/**
 * @FileName Comment
 * @Description
 * @Author fazhu
 * @date 2024-09-13
 **/
@Data
public class Comment {
    private Integer id;//评论id
    private String avatar;//头像
    private String nickname;//昵称
    private String content;//内容
    private String time;//评论时间
    private Integer tsan_num;//点赞数
    private Integer comment_num;//评论数
    private ArrayList<Image> comment_image;//图片
}
