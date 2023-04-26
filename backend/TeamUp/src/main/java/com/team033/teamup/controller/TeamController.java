package com.team033.teamup.controller;

import com.team033.teamup.service.TeamService;
import com.team033.teamup.service.UserService;
import com.team033.teamup.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/team")
@CrossOrigin(origins = "*")
public class TeamController {

    @Autowired
    private UserService userService;

    @Autowired
    private TeamService teamService;

    @RequestMapping(value = "/{teamId}", method = RequestMethod.GET)
    public Result getTeamById(@PathVariable String teamId) {
        return teamService.getTeamById(teamId);
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public Result getTeamListByUserId(@PathVariable String userId) {
        return teamService.getTeamListByUserId(userId);
    }

    @RequestMapping(value = "/{teamId}/join/{userId}", method = RequestMethod.POST)
    public Result userJoinTeam(@PathVariable String teamId, @PathVariable String userId) {
        return teamService.userJoinTeam(teamId, userId);
    }
    @RequestMapping(value = "/{teamId}/courseid/{courseId}/taskid/{taskId}/invite/{userId}/{netId}", method = RequestMethod.POST)
    public Result inviteUserToTeam(@PathVariable String teamId, @PathVariable String courseId, @PathVariable String taskId, @PathVariable String userId, @PathVariable String netId) {
        return teamService.inviteUserToTeam(teamId, courseId, taskId, userId, netId);
    }

    @RequestMapping(value = "/instructor/{userId}", method = RequestMethod.GET)
    public Result getTeamCountByInstructorId(@PathVariable String userId) {
        return teamService.getTeamCountByInstructorId(userId);
    }

    @RequestMapping(value = "/{teamId}/user/{userId}", method = RequestMethod.DELETE)
    public Result deleteUserInTeam(@PathVariable String teamId, @PathVariable String userId) {
        return teamService.deleteUserInTeam(teamId, userId);
    }

    @RequestMapping(value = "/task/{taskId}/type", method = RequestMethod.GET)
    public Result getTaskTypeById(@PathVariable String taskId) {
        return teamService.getTaskTypeById(taskId);
    }

    @RequestMapping(value = "/task/{taskId}/name", method = RequestMethod.GET)
    public Result getTaskNameById(@PathVariable String taskId) {
        return teamService.getTaskNameById(taskId);
    }

}
