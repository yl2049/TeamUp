package com.team033.teamup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post implements Serializable {
    private String postId;
    private String taskId;
    private String courseId;
    private String userId;
    private String timeSlot;
    private String additionalComments;
    private String weekendAvailability;
    private String previousExperience;
    private String workStyle;

}
