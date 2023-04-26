package com.team033.teamup.controller.request;

import lombok.Data;

@Data
public class TeamPageRequest {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private String course_id;
    private String task_id;
//    private String userId = "";
//    private Integer identity = 0;
}
