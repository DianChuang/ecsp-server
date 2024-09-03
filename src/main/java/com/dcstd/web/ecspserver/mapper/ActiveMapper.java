package com.dcstd.web.ecspserver.mapper;

import cn.hutool.core.date.DateTime;
import com.dcstd.web.ecspserver.entity.*;
import com.dcstd.web.ecspserver.entityRes.ActiveApplicantVote;
import org.apache.ibatis.annotations.*;

import java.util.Date;
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
    @Select("insert into active_applicant(uid, name_active, id_belong, id_group, id_category, name_applicant, contact_applicant, content, num_vote, time) values(#{applicantId}, #{activeName}, #{idBelong}, #{idGroup}, #{idCategory}, #{applicantName}, #{contactApplicant}, #{content}, 1, CURRENT_TIMESTAMP())")
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


    //根据id查询活动申请信息
    @Select("select * from active_applicant where id = #{idApplicant}")
    ActiveApplicant selectActiveApplicantById(@Param("idApplicant") Integer idApplicant);

    //更新活动信息
    @Update("update active set name = #{nameActive}, intro = #{content}, id_cover = #{id_cover}, time_start = #{timeStart}, time_end = #{timeEnd}, position = #{activeType}, limit_num = #{limitNum}, status = 1 where id = #{id_active}")
    void updateActive(@Param("id_active") Integer id_active,
                      @Param("nameActive") String nameActive,
                      @Param("content") String content,
                      @Param("id_cover") Integer id_cover,
                      @Param("timeStart") DateTime timeStart,
                      @Param("timeEnd") DateTime timeEnd,
                      @Param("activeType") String activeType,
                      @Param("limitNum") Integer limitNum);

    //根据uid查询活动申请信息
    @Select("select * from active_applicant where (id = #{uid} and status = 1)")
    Map<Object, Object> selectActiveApplicantByUid(Integer uid);

    //查询今日投票次数
    @Select("select count(*) as num from active_vote where time >= #{thisDayDate} and uid = #{uid}")
    Map<Object, Object> selectDayVoteNumByDate(@Param("thisDayDate") Date thisDayDate,
                                               @Param("uid") Integer uid);

    //投票
    @Insert("insert into active_vote(id_active, uid, time) values(#{idActiveApplicant}, #{uid}, CURRENT_TIMESTAMP())")
    void insertActiveVote(Integer idActiveApplicant,
                          Integer uid);

    //更新活动申请表投票次数
    @Update("update active_applicant set num_vote = num_vote + 1 where id = #{idActiveApplicant}")
    void updateActiveApplicantVoteNum(Integer idActiveApplicant);

    //分页查询活动申请信息
    @Select("select id, uid, name_active, id_cover, num_vote, time from active_applicant where status = 1 order by id desc limit #{index}, #{limit}")
    List<ActiveApplicantVote> selectActiveApplicantsLimit(Integer index, Integer limit);

    //查询所有活动申请信息
    @Select("select id, uid, name_active, id_cover, num_vote, time from active_applicant where status = 1 order by id desc")
    List<ActiveApplicantVote> selectActiveApplicants();

    //根据封面id查询封面
    @Select("select path from active_applicant_image_lib where id = #{idCover}")
    String getActiveApplicantCoverByCoverId(Integer idCover);

    //查询该活动的所有图片
    @Select("select * from active_applicant_image_lib where status = 1")
    List<ActiveApplicantImageLib> selectActiveImage();
}
