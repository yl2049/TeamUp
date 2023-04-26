package com.team033.teamup.controller.request;

import lombok.Data;

@Data
public class CoursePageRequest {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private String course_name = "";
    private String course_num = "";
    private String userId = "";
    private Integer identity = 0;
}
