package com.dcstd.web.ecspserver.entity.outgoing;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @FileName Post_content
 * @Description 只输出内容
 * @Author fazhu
 * @date 2024-08-15
 **/
@Data
public class Post_content {
   private String content;//动态内容
}
