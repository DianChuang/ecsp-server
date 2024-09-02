package com.dcstd.web.ecspserver.mapper;

import cn.hutool.core.date.DateTime;
import com.dcstd.web.ecspserver.entity.ActiveApplicant;
import com.dcstd.web.ecspserver.entity.ActiveInfo;
import com.dcstd.web.ecspserver.entity.SchoolGroup;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ActiveMapper {

    //根据名称获取活动类别id
    @Select("select id from active_category where name = #{category}")
    Integer getCategoryIdByName(@Param("category") String category);

    //根据名称获取活动所属id
    @Select("select id from active_belong where name = #{belong}")
    Integer getBelongIdByName(@Param("belong") String belong);

    //查询活动组织信息
    @Select("select * from school_group where id = #{id}")
    SchoolGroup selectSchoolGroupById(@Param("id") Integer id);

    //插入活动申请信息
    @Select("insert into active_applicant(uid, name_active, id_belong, id_group, id_category, name_applicant, contact_applicant, content, time) values(#{applicantId}, #{activeName}, #{idBelong}, #{idGroup}, #{idCategory}, #{applicantName}, #{contactApplicant}, #{content}, CURRENT_TIMESTAMP())")
    void insertActiveApplicant(@Param("applicantId") Integer applicantId,
                               @Param("activeName") String activeName,
                               @Param("idBelong") Integer idBelong,
                               @Param("idGroup") Integer idGroup,
                               @Param("idCategory") Integer idCategory,
                               @Param("applicantName") String applicantName,
                               @Param("contactApplicant") String contactApplicant,
                               @Param("content") String content);

    //更新活动信息
    @Update("update active_info set position_detail = #{positionDetail}, time_join = #{timeJoin}, time_join_end = #{timeJoinEnd}, id_category = #{idCategory}, id_belong = #{idBelong}, id_group = #{idGroup}, id_superintendent = #{superintendent}, id_superintendent_phone = #{id_superintendent_phone}, number_group = #{numberGroup}, qrcode = #{qrcode}, code_invite = #{codeInvite} where id_applicant = #{idApplicant}")
    void updateActiveInfoApplicantDetail(@Param("idApplicant") Integer idApplicant,
                                         @Param("positionDetail") String positionDetail,
                                         @Param("timeJoin") DateTime timeJoin,
                                         @Param("timeJoinEnd") DateTime timeJoinEnd,
                                         @Param("idCategory") Integer idCategory,
                                         @Param("idBelong") Integer idBelong,
                                         @Param("idGroup") Integer idGroup,
                                         @Param("superintendent") String superintendent,
                                         @Param("id_superintendent_phone") String id_superintendent_phone,
                                         @Param("numberGroup") String numberGroup,
                                         @Param("qrcode") String qrcode,
                                         @Param("codeInvite") String codeInvite);


    @Select("select * from active_applicant where id = #{idApplicant}")
    ActiveApplicant selectActiveApplicantById(@Param("idApplicant") Integer idApplicant);

    @Update("update active set name = #{nameActive}, intro = #{content}, cover = #{cover}, time_start = #{timeStart}, time_end = #{timeEnd}, position = #{activeType}, limit_num = #{limitNum}, status = 1 where id = #{id_active}")
    void updateActive(@Param("id_active") Integer id_active,
                      @Param("nameActive") String nameActive,
                      @Param("content") String content,
                      @Param("cover") String cover,
                      @Param("timeStart") DateTime timeStart,
                      @Param("timeEnd") DateTime timeEnd,
                      @Param("activeType") String activeType,
                      @Param("limitNum") Integer limitNum);

    @Select("select * from active_applicant where (id = #{uid} and status = 1)")
    Map<Object, Object> selectActiveApplicantByUid(Integer uid);
}
