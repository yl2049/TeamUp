package com.team033.teamup.service;

import com.team033.teamup.util.Result;

public interface TeamService {

    public Result getTeamId(String courseId, String taskId, String userId);

    public Result getTeamListByUserId(String userId);

    public Result getTeamList(String courseId, String taskId);

    public Result getTeamCountByInstructorId(String userId);

    public Result getTeamById(String teamId);

    public Result creatTeam(String courseId, String taskId);

    public Result userJoinTeam(String teamId, String userId);

    public Result inviteUserToTeam(String teamId, String courseId, String taskId, String userId, String netId);

    public Result generateRandomTeam(String courseId, String taskId);

    public Result deleteUserInTeam(String teamId, String userId);

    public Result getTaskTypeById(String taskId);

    public Result getTaskNameById(String taskId);
}
