package com.team033.teamup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
    Used to return a single result that includes information needed on the instructor's EnterCourseView
 */
@Data
public class tempQ2getTeamCountByInstructorId_result {
    private Integer index;
    private String task_id;
    private String course_id;
    private String type;
    private String task_name;
    private String max_size;
    private String count_team;

    private String min_size;
    private String description;
    private String ddl;
}
