package com.dcstd.web.ecspserver.mapper;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dcstd.web.ecspserver.entity.incoming.Post_insert;
import com.dcstd.web.ecspserver.entity.outgoing.Post;
import com.dcstd.web.ecspserver.entity.outgoing.Post_content;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.Date;

/**
 * @FileName PostMapper
 * @Description
 * @Author fazhu
 * @date 2024-08-15
 **/
@Mapper
public interface PostMapper extends BaseMapper {

    //查询帖子所需要的参数
    @Select("select a.id,a.content,a.tsan_num,a.tsan_num,a.comment_num" +
            ",a.tag,a.time,b.avatar,b.nickname" +
            "from post as a" +
            "join userinfo as b" +
            "on a.uid=b.uid")
    Post post();

    //发帖
    @Insert("insert into `post` value (null,#{uid},#{content},0,0,#{id_category},null,0,#{time},#{is_anonymity},#{contact_name},#{contact_type},#{contact_number})")
    void post_insert(@Param("uid") Integer uid,//用户id
                            @Param("content") String content,//正文
                            @Param("id_category") Integer id_category,//分类
                            @Param("time") DateTime time,//发布时间
                            @Param("is_anonymity")Integer is_anonymity,//是否匿名
                            @Param("contact_name")String contact_name,//联系方式——姓名
                            @Param("contact_type")String contact_type,//号码类型
                            @Param("contact_number")Integer contact_number//号码
                     );
    //发帖增加图片
    @Insert("insert into `post_image_lib` value(null,(select last_insert_id()),null,#{path})")
    void post_insert_image(@Param("path") String path);
}
