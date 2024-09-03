package com.dcstd.web.ecspserver.controller;

import cn.hutool.core.date.DateTime;
import com.dcstd.web.ecspserver.common.AuthAccess;
import com.dcstd.web.ecspserver.common.Result;
import com.dcstd.web.ecspserver.config.GlobalConfiguration;
import com.dcstd.web.ecspserver.entity.*;
import com.dcstd.web.ecspserver.exception.CustomException;
import com.dcstd.web.ecspserver.exception.GlobalException;
import com.dcstd.web.ecspserver.mapper.UserMapper;
import com.dcstd.web.ecspserver.service.ActiveService;
import com.dcstd.web.ecspserver.utils.RandomUtils;
import com.dcstd.web.ecspserver.utils.TokenUtils;
import jakarta.annotation.Nullable;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/activeApplicant")
public class ActiveApplicantController {

    @Resource
    GlobalConfiguration globalConfiguration;

    @Resource
    ActiveService activeService;

    @Resource
    private UserMapper userMapper;

    /**
     * 发布活动申请
     * @param p_data json参数
     * @return 成功
     */
    @PostMapping("/application")
    public Result activeApplicantApplication(@RequestBody Map<String, Object> p_data) {
        //获取基础信息
        Integer applicant_id;//申请人id
        String applicant_name;//申请人姓名
        try {
            UserInfo dbUserInfo = userMapper.selectUserInfoById(TokenUtils.getCurrentUser().getId());
            applicant_id = dbUserInfo.getUid();//申请人id
            applicant_name = dbUserInfo.getName();//申请人姓名
            //根据uid判断当前用户正在申请多少活动
            if (activeService.selectActiveApplicantByUid(applicant_id).size() >= globalConfiguration.getMaxApplicationNumber()) {
                return Result.error(GlobalException.ERROR_ACTIVE_APPLICANT_NUM.getCode(), GlobalException.ERROR_ACTIVE_APPLICANT_NUM.getMsg(), GlobalException.ERROR_ACTIVE_APPLICANT_NUM.getTips());
            }
        } catch (Exception e) {
            throw new CustomException(GlobalException.ERROR_TOKEN);
        }
        //参数初始化
        String active_name = p_data.get("name_active").toString();
        String contact_applicant = p_data.get("contact_applicant").toString();//联系方式
        Integer id_group = Integer.parseInt(p_data.get("id_group").toString());//申请部落id
        Integer id_category = Integer.parseInt(p_data.get("id_category").toString());//申请类别
        String content = p_data.get("content").toString();//申请内容


        //获取组织信息
        SchoolGroup dbSchoolGroup = activeService.getSchoolGroupById(id_group);
        Integer id_belong = dbSchoolGroup.getId_belong();//获取组织归属

        activeService.insertActiveApplicant(applicant_id, active_name, id_belong, id_group, id_category, applicant_name, contact_applicant, content);

        return Result.success();
    }


    /**
     * 更新活动申请信息
     * @param p_data json参数
     * @param id_belong 归属组织id
     * @param id_group 部落id
     * @param id_applicant 活动申请id
     * @param id_active 活动id
     * @return 成功
     */
    @PostMapping("/info/insert")
    public Result activeApplicantDetail(@RequestBody Map<String, Object> p_data, @Param("id_belong") Integer id_belong, @Param("id_group") Integer id_group, @Param("id_applicant") Integer id_applicant, @Param("id_active") Integer id_active) {
        //更新活动申请详情信息
        String position_detail = p_data.get("position_detail").toString();//活动地点
        DateTime time_join = DateTime.of(Long.parseLong(p_data.get("time_join").toString()));//报名开始时间
        DateTime time_join_end = DateTime.of(Long.parseLong(p_data.get("time_end").toString()));//报名结束时间
        String superintendent = p_data.get("superintendent").toString();//活动负责人
        String qrcode = p_data.get("qrcode").toString();//二维码文件名称
        String id_superintendent_phone = p_data.get("id_superintendent_phone").toString();//活动负责人联系方式

        String number_group = p_data.get("number_group").toString();//活动群号

        String code_invite = RandomUtils.getRandomString(16);
        //获取申请时填写的信息
        ActiveApplicant dbActiveApplicant = activeService.getActiveApplicantById(id_applicant);

        //更新信息
        activeService.updateActiveInfoApplicantDetail(id_applicant, position_detail, time_join, time_join_end, dbActiveApplicant.getId_category(), id_belong, id_group, superintendent, id_superintendent_phone, number_group, qrcode, code_invite);
        //更新活动表
        Integer limit_num = Integer.parseInt(p_data.get("limit_num").toString());//可参与人数
        DateTime time_start = DateTime.of(Long.parseLong(p_data.get("time_start").toString()));//活动开始时间
        DateTime time_end = DateTime.of(Long.parseLong(p_data.get("time_end").toString()));//活动结束时间

        String cover = Optional.ofNullable(p_data.get("cover")).orElse(1).toString();//活动封面

        String active_type = Optional.ofNullable(p_data.get("active_type")).orElse(Objects.equals(position_detail, "线上") ? "线上" : "线下").toString();//活动类型

        //更新信息
        try{
            activeService.updateActive(id_active, dbActiveApplicant.getName_active(), dbActiveApplicant.getContent(), cover, time_start, time_end, active_type, limit_num);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }


        return Result.success();
    }

}
