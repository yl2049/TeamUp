package com.team033.teamup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructionOverall {
    private String courseId;
    private String taskId;
    private Integer countOfTeam;
}
