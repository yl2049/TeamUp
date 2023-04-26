package com.team033.teamup.service;

import com.team033.teamup.util.Result;

public interface PostService {
    public Result getPostById(String postId);

    public Result getTaskIdByCourseId(String courseId);

    public Result getPostsByCourseTaskId(String courseId, String taskId);

    public Result addPost( String taskId, String courseId, String userId,
                           String weekendAvailability, String previousExperience,
                           String teamWorkStyle, String additionalComments);

    public Result deletePostByCourseTaskUserId(String courseId, String taskId, String userId);
}
