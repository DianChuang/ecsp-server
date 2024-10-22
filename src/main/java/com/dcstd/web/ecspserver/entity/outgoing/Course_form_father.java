package com.dcstd.web.ecspserver.entity.outgoing;

import lombok.Data;

import java.util.ArrayList;

/**
 * @FileName Course_form_father
 * @Description
 * @Author fazhu
 * @date 2024-10-22
 **/
@Data
public class Course_form_father {
    Integer pages;
    ArrayList<Course_form> list;
}
