package com.dcstd.web.ecspserver.service;

import cn.hutool.core.date.DateTime;
import com.dcstd.web.ecspserver.entity.*;
import com.dcstd.web.ecspserver.entityRes.ActiveAll;
import com.dcstd.web.ecspserver.entityRes.ActiveApplicantVote;
import com.dcstd.web.ecspserver.mapper.ActiveMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ActiveService {
    @Resource
    ActiveMapper activeMapper;

    public SchoolGroup getSchoolGroupById(Integer id) {
        return activeMapper.selectSchoolGroupById(id);
    }

    /**
     * 插入活动申请
     * @param applicantId 申请人id
     * @param activeName 申请人姓名
     * @param idBelong 归属id
     * @param idGroup 组织id
     * @param idCategory 分类id
     * @param applicantName 活动名称
     * @param contactApplicant 联系人
     * @param content 活动内容
     */
    public void insertActiveApplicant(Integer applicantId, String activeName, Integer idBelong, Integer idGroup, Integer idCategory, String applicantName, String contactApplicant, String content, Integer idCover) {
        activeMapper.insertActiveApplicant(applicantId, activeName, idBelong, idGroup, idCategory, applicantName, contactApplicant, content, idCover);
    }

    public void updateActiveInfoApplicantDetail(Integer idApplicant, String positionDetail, DateTime timeJoin, DateTime timeJoinEnd, Integer idCategory, Integer idBelong, Integer idGroup, String superintendent, String id_superintendent_phone, String numberGroup, String qrcode, String codeInvite) {
        activeMapper.updateActiveInfoApplicantDetail(idApplicant, positionDetail, timeJoin, timeJoinEnd, idCategory, idBelong, idGroup, superintendent, id_superintendent_phone, numberGroup, qrcode, codeInvite);
    }

    public ActiveApplicant getActiveApplicantById(Integer idApplicant) {
        return activeMapper.selectActiveApplicantById(idApplicant);
    }

    public void updateActive(Integer id_active, String nameActive, String content, Integer id_cover, DateTime timeStart, DateTime timeEnd, String activeType, Integer limitNum) {
        activeMapper.updateActive(id_active, nameActive, content, id_cover, timeStart, timeEnd, activeType, limitNum);
    }

    public Map<Object, Object> selectActiveApplicantByUid(Integer uid) {
        return activeMapper.selectActiveApplicantByUid(uid);
    }

    public Map<Object, Object> selectDayVoteNumByDate(Date thisDayDate, Integer uid) {
        return activeMapper.selectDayVoteNumByDate(thisDayDate, uid);
    }

    public void insertActiveVote(Integer idActiveApplicant, Integer uid) {
        activeMapper.insertActiveVote(idActiveApplicant, uid);
    }

    public void updateActiveApplicantVoteNum(Integer idActiveApplicant) {
        activeMapper.updateActiveApplicantVoteNum(idActiveApplicant);
    }

    public List<ActiveApplicantVote> selectActiveApplicants(Integer page, Integer limit) {
        Integer index = (page - 1) * limit;
        return activeMapper.selectActiveApplicantsLimit(index, limit);
    }

    public List<ActiveApplicantVote> selectActiveApplicants() {
        return activeMapper.selectActiveApplicants();
    }

    public String getActiveApplicantCoverByCoverId(Integer idCover) {
        return activeMapper.getActiveApplicantCoverByCoverId(idCover);
    }

    public List<ActiveApplicantImageLib> selectActiveApplicantImage() {
        return activeMapper.selectActiveApplicantImage();
    }

    public Active getActiveById(Integer id) {
        return activeMapper.selectActiveById(id);
    }

    public List<ActiveImageLib> selectActiveImage(Integer id) {
        return activeMapper.selectActiveImage(id);
    }

    public ActiveAll getActiveAllById(Integer id) {
        return activeMapper.selectActiveAllById(id);
    }

    public ActiveInfo selectActiveInfoByActiveId(Integer id) {
        return activeMapper.selectActiveInfoByActiveId(id);
    }

    public ActiveCategory selectCategoryById(Integer idCategory) {
        return activeMapper.selectCategoryById(idCategory);
    }

    public ActiveBelong selectBelongById(Integer idBelong) {
        return activeMapper.selectBelongById(idBelong);
    }

    public String getActiveCoverByCoverId(Integer idCover) {
        return activeMapper.getActiveCoverByCoverId(idCover);
    }

    public List<ActiveCategory> selectActiveCategory() {
        return activeMapper.selectActiveCategory();
    }


    //TODO:
}
