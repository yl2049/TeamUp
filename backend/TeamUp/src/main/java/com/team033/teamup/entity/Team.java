package com.team033.teamup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team implements Serializable {
    private String teamId;
    private String taskId;
    private String courseId;
}
