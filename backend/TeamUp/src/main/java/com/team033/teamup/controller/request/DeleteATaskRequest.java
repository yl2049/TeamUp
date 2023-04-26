package com.team033.teamup.controller.request;

import lombok.Data;

@Data
public class DeleteATaskRequest {
    private String task_id;
    private String course_id;
}
