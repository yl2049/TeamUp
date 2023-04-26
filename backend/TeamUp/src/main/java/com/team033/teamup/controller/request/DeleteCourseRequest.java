package com.team033.teamup.controller.request;

import lombok.Data;

@Data
public class DeleteCourseRequest {
    private String userId;
    private String register_code;
}
