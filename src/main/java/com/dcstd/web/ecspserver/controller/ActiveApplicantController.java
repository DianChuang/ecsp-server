package com.dcstd.web.ecspserver.controller;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.dcstd.web.ecspserver.common.Result;
import com.dcstd.web.ecspserver.config.GlobalConfiguration;
import com.dcstd.web.ecspserver.entity.*;
import com.dcstd.web.ecspserver.entityRes.ActiveApplicantVote;
import com.dcstd.web.ecspserver.exception.CustomException;
import com.dcstd.web.ecspserver.exception.GlobalException;
import com.dcstd.web.ecspserver.mapper.UserMapper;
import com.dcstd.web.ecspserver.service.ActiveService;
import com.dcstd.web.ecspserver.service.BaseInfoService;
import com.dcstd.web.ecspserver.utils.DateUtil;
import com.dcstd.web.ecspserver.utils.RandomUtils;
import com.dcstd.web.ecspserver.utils.TokenUtils;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/activeApplicant")
public class ActiveApplicantController {

    @Resource
    GlobalConfiguration globalConfiguration;

    @Resource
    ActiveService activeService;

    @Resource
    private UserMapper userMapper;
    @Resource
    private BaseInfoService baseInfoService;

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
            Integer count;
            try {
                count = activeService.selectActiveApplicantByUid(applicant_id).size();
            } catch (Exception e) {
                count = 0;
            }
            if (count >= globalConfiguration.getMaxApplicationNumber()) {
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
        Integer id_cover = Integer.parseInt(Optional.ofNullable(p_data.get("cover")).orElse(1).toString());


        //获取组织信息
        SchoolGroup dbSchoolGroup = activeService.getSchoolGroupById(id_group);
        Integer id_belong = dbSchoolGroup.getId_belong();//获取组织归属

        activeService.insertActiveApplicant(applicant_id, active_name, id_belong, id_group, id_category, applicant_name, contact_applicant, content, id_cover);

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

        Integer id_cover = Integer.parseInt(Optional.ofNullable(p_data.get("cover")).orElse(Optional.ofNullable(activeService.getActiveApplicantById(id_applicant).getId_cover()).orElse(1)).toString());//活动封面

        String active_type = Optional.ofNullable(p_data.get("active_type")).orElse(Objects.equals(position_detail, "线上") ? "线上" : "线下").toString();//活动类型

        //更新信息
        try{
            activeService.updateActive(id_active, dbActiveApplicant.getName_active(), dbActiveApplicant.getContent(), id_cover, time_start, time_end, active_type, limit_num);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }


        return Result.success();
    }

    /**
     * 活动投票
     * @param id_active_applicant 活动申请id
     * @return Result
     */
    @PostMapping("/vote")
    public Result activeApplicantVote(@Param("id_active_applicant") Integer id_active_applicant) {
        //获取活动信息
        ActiveApplicant dbActive = activeService.getActiveApplicantById(id_active_applicant);

        if(dbActive == null) {
            return Result.error(GlobalException.EMPTY.getCode(), "活动不存在！", GlobalException.EMPTY.getTips());
        }

        //是否是自己的活动
        if (Objects.equals(dbActive.getUid(), TokenUtils.getCurrentUser().getId())) {
            return Result.error("不能给自己的活动投票！");
        }

        //获取当前用户id
        Integer uid = TokenUtils.getCurrentUser().getId();

        //今天是否还有投票次数
        Date thisDayDate = DateUtil.getThisDayDate();
        if(Integer.parseInt(activeService.selectDayVoteNumByDate(thisDayDate, uid).get("num").toString()) >= globalConfiguration.getMaxVoteNumberPerDay()){
            return Result.error("今天已经达到投票次数上限！");
        }

        //投票操作
        activeService.insertActiveVote(id_active_applicant, uid);
        activeService.updateActiveApplicantVoteNum(id_active_applicant);

        return Result.success();
    }


    /**
     * 获取投票列表
     * @param page 页
     * @param limit 每页条数
     * @return (Result)
     */
    @GetMapping("/voteList")
    public Result activeApplicantVoteList(@Param("page") Integer page, @Param("limit") Integer limit) {
        List<ActiveApplicantVote> activeApplicants;

        if(page == null || limit == null){
            activeApplicants = activeService.selectActiveApplicants();
        } else {
            activeApplicants = activeService.selectActiveApplicants(page, limit);
        }
        activeApplicants.forEach(activeApplicant -> {
            activeApplicant.setCover(globalConfiguration.getFileUrl() + globalConfiguration.getPathImageCover() + activeService.getActiveApplicantCoverByCoverId(activeApplicant.getId_cover()));
        });

        return Result.success(activeApplicants);
    }

    /**
     * 获取投票规则
     * @return (Result)
     */
    @GetMapping("/voteList/rules")
    public Result activeApplicantVoteRule() {
        if(StringUtils.isEmpty(baseInfoService.getActiveApplicantVoteRule())){
            return Result.error("活动投票规则未设置！");
        }
        return Result.success(baseInfoService.getActiveApplicantVoteRule());
    }

    /**
     * 获取活动详情
     * @param id 活动id
     * @return
     */
    @GetMapping("/info/{id}")
    public Result activeApplicantInfo(@PathVariable("id") Integer id) {
        ActiveApplicant activeApplicant = activeService.getActiveApplicantById(id);
        if(activeApplicant == null) {
            return Result.error(GlobalException.EMPTY.getCode(), "活动申请不存在！", GlobalException.EMPTY.getTips());
        }
        List<ActiveApplicantImageLib> activeApplicantImageLibs = activeService.selectActiveApplicantImage();
        activeApplicantImageLibs.forEach(activeApplicantImageLib -> {
            activeApplicantImageLib.setUrl(globalConfiguration.getFileUrl() + globalConfiguration.getPathImage() + activeApplicantImageLib.getPath());
        });
        activeApplicant.setShow_image(activeApplicantImageLibs);
        return Result.success(activeApplicant);
    }


    /**
     * 获取活动申请类别
     * @return (Result)
     */
    @GetMapping("/application/cause")
    public Result activeApplicantCause() {
        return Result.success(activeService.selectActiveCategory());
    }



}
