package com.team033.teamup.controller.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AddTaskRequest {
    private String course_id;
    private String type;
    private String task_name;
    private String max_size;


    private String min_size;
    private String ddl;
    private String ddl_time;
    private boolean published;
    private String description;

}
