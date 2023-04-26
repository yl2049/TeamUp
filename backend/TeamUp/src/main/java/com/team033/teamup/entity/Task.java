package com.team033.teamup.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Task {
    private String task_id;
    private String course_id;
    private String type;
    private String task_name;
    private String max_size;
    private String min_size;
    private String description;
    private String ddl;
}
