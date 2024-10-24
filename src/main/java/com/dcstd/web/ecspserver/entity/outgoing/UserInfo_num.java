package com.dcstd.web.ecspserver.entity.outgoing;

import lombok.Data;

/**
 * @FileName UserInfo_num
 * @Description
 * @Author fazhu
 * @date 2024-09-22
 **/
@Data
public class UserInfo_num {
    private Integer tsan;// 点赞数
    private Integer fan;// 粉丝
    private Integer follow;// 关注数
}
