package com.team033.teamup.controller.request;

import lombok.Data;

@Data
public class AddCourseRequest {
    private String semester;
    private String course_name;
    private String course_num;
    private String userId;
}
