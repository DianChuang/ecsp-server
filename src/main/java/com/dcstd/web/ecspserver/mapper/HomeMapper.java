package com.dcstd.web.ecspserver.mapper;

import com.dcstd.web.ecspserver.entity.Active;
import com.dcstd.web.ecspserver.entity.outgoing.ActiveList;
import com.dcstd.web.ecspserver.entity.outgoing.Course;
import com.dcstd.web.ecspserver.entity.outgoing.Course_form;
import com.dcstd.web.ecspserver.entity.outgoing.HomeActive;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * @FileName HomeMapper
 * @Description
 * @Author fazhu
 * @date 2024-10-11
 **/
@Mapper
public interface HomeMapper {

  //查询活动总数
  @Select("SELECT COUNT(*) FROM active where status = 1")
  Integer getActiveCount();

  //查询校园生活的活动
  @Select("SELECT a.id, a.name, a.cover,b.name FROM active as a left join active_info as c ON a.id=c.id LEFT JOIN active_belong as b on  c.id_belong = b.id where status=1  LIMIT ${page}, ${size}")
  ArrayList<ActiveList> getFirstActive(Integer page, Integer size);

  //查询热播课程
  @Select("SELECT a.title, a.cover, b.name as subject,a.view_num " +
          "FROM course_info as a " +
          "LEFT JOIN course_subject as b ON a.id_subject = b.id " +
          "ORDER BY a.view_num DESC " +
          "LIMIT 3")
  ArrayList<Course> getHotCourse();

  //查询课程总数
  @Select("SELECT COUNT(*) FROM active where status = 1")
  Integer getCourseCount();

  //查询热播课程
  @Select("SELECT title,cover, source, view_num " +
          "FROM course_info " +
          "ORDER BY view_num DESC " +
          "LIMIT #{page},#{limit}")
  ArrayList<Course_form> getAllCourse(Integer page, Integer limit);



}
