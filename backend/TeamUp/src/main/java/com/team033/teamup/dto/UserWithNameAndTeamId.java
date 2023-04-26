package com.team033.teamup.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWithNameAndTeamId implements Serializable {
    private String courseId;
    private String taskId;
    private String teamId;
    private String userId;
    private String name;
}
