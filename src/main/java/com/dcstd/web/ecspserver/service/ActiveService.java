package com.dcstd.web.ecspserver.service;

import cn.hutool.core.date.DateTime;
import com.dcstd.web.ecspserver.entity.*;
import com.dcstd.web.ecspserver.mapper.ActiveMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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
    public void insertActiveApplicant(Integer applicantId, String activeName, Integer idBelong, Integer idGroup, Integer idCategory, String applicantName, String contactApplicant, String content) {
        activeMapper.insertActiveApplicant(applicantId, activeName, idBelong, idGroup, idCategory, applicantName, contactApplicant, content);
    }

    public void updateActiveInfoApplicantDetail(Integer idApplicant, String positionDetail, DateTime timeJoin, DateTime timeJoinEnd, Integer idCategory, Integer idBelong, Integer idGroup, String superintendent, String id_superintendent_phone, String numberGroup, String qrcode, String codeInvite) {
        activeMapper.updateActiveInfoApplicantDetail(idApplicant, positionDetail, timeJoin, timeJoinEnd, idCategory, idBelong, idGroup, superintendent, id_superintendent_phone, numberGroup, qrcode, codeInvite);
    }

    public ActiveApplicant getActiveApplicantById(Integer idApplicant) {
        return activeMapper.selectActiveApplicantById(idApplicant);
    }

    public void updateActive(Integer id_active, String nameActive, String content, String cover, DateTime timeStart, DateTime timeEnd, String activeType, Integer limitNum) {
        activeMapper.updateActive(id_active, nameActive, content, cover, timeStart, timeEnd, activeType, limitNum);
    }

    public Map<Object, Object> selectActiveApplicantByUid(Integer uid) {
        return activeMapper.selectActiveApplicantByUid(uid);
    }


    //TODO:
}
