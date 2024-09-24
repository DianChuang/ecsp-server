package com.dcstd.web.ecspserver.controller;

import com.dcstd.web.ecspserver.common.Result;
import com.dcstd.web.ecspserver.config.GlobalConfiguration;
import com.dcstd.web.ecspserver.entity.*;
import com.dcstd.web.ecspserver.entityRes.ActiveAll;
import com.dcstd.web.ecspserver.exception.GlobalException;
import com.dcstd.web.ecspserver.mapper.UserMapper;
import com.dcstd.web.ecspserver.service.ActiveService;
import com.dcstd.web.ecspserver.utils.RSAUtils;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/active")
public class ActiveController {

    @Resource
    ActiveService activeService;

    @Resource
    GlobalConfiguration globalConfiguration;
    @Resource
    private UserMapper userMapper;

    /**
     * 获取活动详情信息
     * @param id 活动id
     * @return (Result)
     */
    @GetMapping("/info")
    public Result activeInfo(@Param("id") Integer id) {
        //查询活动
        ActiveAll activeAll = activeService.getActiveAllById(id);
        if(activeAll == null) {
            return Result.error(GlobalException.EMPTY.getCode(), "活动不存在！", GlobalException.EMPTY.getTips());
        }

        List<ActiveImageLib> activeImageLibs = activeService.selectActiveImage(id);
        activeImageLibs.forEach(activeImageLib -> {
            activeImageLib.setUrl(globalConfiguration.getFileUrl() + globalConfiguration.getPathImage() + activeImageLib.getPath());
        });
        //查询活动信息
        ActiveInfo activeInfo = activeService.selectActiveInfoByActiveId(id);
        activeAll.setCover(globalConfiguration.getFileUrl() + globalConfiguration.getPathImageCover() +activeService.getActiveCoverByCoverId(activeAll.getId_cover()));
        activeAll.setShow_image(activeImageLibs);
        activeAll.setId_applicant(activeInfo.getId_applicant());
        activeAll.setTime_join(activeInfo.getTime_join());
        activeAll.setTime_join_end(activeInfo.getTime_join_end());
        activeAll.setNumber_group(activeInfo.getNumber_group());
        activeAll.setId_category(activeInfo.getId_category());
        activeAll.setId_belong(activeInfo.getId_belong());
        activeAll.setId_group(activeInfo.getId_group());
        activeAll.setCode_invite(activeInfo.getCode_invite());
        activeAll.setId_superintendent(activeInfo.getId_superintendent());
        activeAll.setId_superintendent_phone(activeInfo.getId_superintendent_phone());
        String name;
        try{
            name = RSAUtils.decrypt(userMapper.selectUserInfoById(activeAll.getId_superintendent()).getName());
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            name = userMapper.selectUserInfoById(activeAll.getId_superintendent()).getNickname();
        }
        activeAll.setName_applicant(name);
        activeAll.setPosition_detail(activeInfo.getPosition_detail());
        activeAll.setContact_applicant(activeInfo.getId_superintendent_phone());
        activeAll.setQrcode(globalConfiguration.getFileUrl() + globalConfiguration.getPathImage() + activeInfo.getQrcode());
        //查询详细名称
        ActiveCategory activeCategory = activeService.selectCategoryById(activeInfo.getId_category());
        ActiveBelong activeBelong = activeService.selectBelongById(activeInfo.getId_belong());
        SchoolGroup schoolGroup = activeService.getSchoolGroupById(activeInfo.getId_group());
        //设置详情名称
        activeAll.setCategory(activeCategory.getName());
        activeAll.setBelong(activeBelong.getName());
        activeAll.setGroup(schoolGroup.getName());

        return Result.success(activeAll);
    }

}
