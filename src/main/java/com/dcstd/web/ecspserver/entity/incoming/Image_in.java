package com.dcstd.web.ecspserver.entity.incoming;

import lombok.Data;

import java.util.ArrayList;

/**
 * @FileName Image_in
 * @Description
 * @Author fazhu
 * @date 2024-09-12
 **/
@Data
public class Image_in {
    private Integer id;//传入的id
    private ArrayList<Image> images;//照片组
}
