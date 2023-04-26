package com.team033.teamup.serviceImpl;

import cn.hutool.json.JSONObject;
import com.team033.teamup.dao.CourseDao;
import com.team033.teamup.dao.TeamDao;
import com.team033.teamup.dao.UserDao;
import com.team033.teamup.dto.UserIdAndName;
import com.team033.teamup.dto.UserWithNameAndTeamId;
import com.team033.teamup.entity.Team;
import com.team033.teamup.entity.User;
import com.team033.teamup.service.MailService;
import com.team033.teamup.service.TeamService;
import com.team033.teamup.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    TeamDao teamDao;
    @Autowired
    CourseDao courseDao;

    @Autowired
    UserDao userDao;

    @Autowired
    MailService mailService;

    @Override
    public Result getTeamId(String courseId, String taskId, String userId) {
        Result result = new Result();
        result.setSuccess(false);
        result.setMsg(null);
        result.setDetail(null);
        try {
            result.setSuccess(true);
            result.setDetail(teamDao.getTeamId(courseId, taskId, userId));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public Result getTeamListByUserId(String userId) {
        Result result = new Result();
        result.setSuccess(false);
        result.setMsg(null);
        result.setDetail(null);
        try {
            result.setSuccess(true);

            List<UserWithNameAndTeamId> teamAndUserList = teamDao.getTeamListByUserId(userId);

            HashMap<String, HashMap<String, HashMap<String, List<UserIdAndName>>>> map = new HashMap<>();
            for (UserWithNameAndTeamId u : teamAndUserList) {
                map.putIfAbsent(u.getCourseId(), new HashMap<String, HashMap<String, List<UserIdAndName>>>());
                map.get(u.getCourseId()).putIfAbsent(u.getTaskId(), new HashMap<String, List<UserIdAndName>>());
                map.get(u.getCourseId()).get(u.getTaskId()).putIfAbsent(u.getTeamId(), new ArrayList<UserIdAndName>());
                map.get(u.getCourseId()).get(u.getTaskId()).get(u.getTeamId())
                        .add(new UserIdAndName(u.getUserId(), u.getName()));
            }

            result.setDetail(map);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public Result getTeamList(String courseId, String taskId) {
        Result result = new Result();
        result.setSuccess(false);
        result.setMsg(null);
        result.setDetail(null);
        try {
            result.setSuccess(true);
            result.setDetail(teamDao.getTeamList(courseId, taskId));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public Result getTeamCountByInstructorId(String userId) {
        Result result = new Result();
        result.setSuccess(false);
        result.setMsg(null);
        result.setDetail(null);
        try {
            result.setSuccess(true);
            result.setDetail(teamDao.getTeamCountByInstructorId(userId));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public Result getTeamById(String teamId) {
        Result result = new Result();
        result.setSuccess(false);
        result.setMsg(null);
        result.setDetail(null);
        try {
            result.setSuccess(true);
            result.setDetail(teamDao.getTeamById(teamId));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public Result creatTeam(String courseId, String taskId) {
        Result result = new Result();
        result.setSuccess(false);
        result.setMsg(null);
        result.setDetail(null);
        try {
            result.setSuccess(true);
            result.setDetail(teamDao.creatTeam(courseId, taskId));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public Result userJoinTeam(String teamId, String userId) {
        Result result = new Result();
        result.setSuccess(false);
        result.setMsg(null);
        result.setDetail(null);
        try {
            result.setSuccess(true);
            result.setDetail(teamDao.userJoinTeam(teamId, userId));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public Result inviteUserToTeam(String teamId, String courseId, String taskId, String userId, String netId) {
        Result result = new Result();
        result.setSuccess(false);
        result.setMsg(null);
        result.setDetail(null);
        try {
            User invitor = userDao.getUserById(userId);
            String invitorName = invitor.getName();
            String emailAddress = netId + "@illinois.edu";
            User acceptUser = userDao.getUserByEmail(emailAddress);
            if (acceptUser == null) {
                result.setMsg("no such user!!");
                return result;
            }
            String courseName = courseDao.getCourseNameByCourseId(courseId);
            String taskName = teamDao.getTaskNameById(taskId);

            String acceptUserId = acceptUser.getUserId();
//            String acceptWebAddress = "http://localhost:8080/invitation/team/"+teamId+"/"+acceptUserId;
//            // TODO change to correct url, abandon web url solution, turn to plain text version.
            String subject = "TeamUp Invitation from :" + invitorName;
//            String content = "Your classmate "+ invitorName +
//                    " from TeamUp invite you to join a team! \n Go to this page to accept or deny:"
//                    + acceptWebAddress;
            String content = "Your classmate "+ invitorName +
                    " from TeamUp invite you to join a team! \n " +
                    " Team details as follow, please use Team Id to join the in our webpage! \n " +
                    "----------------------------------------\n " +
                    "--Course Name :"+ courseName +" --\n " +
                    "--Course Id :"+ courseId +" --\n " +
                    "--Task Name :"+ taskName +" --\n " +
                    "--Task Id :"+ taskId +" --\n " +
                    "--Team Id :"+ teamId +" --\n " +
                    "----------------------------------------\n " +
                    "";
            mailService.sendMail(emailAddress, subject, content);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public Result generateRandomTeam(String courseId, String taskId) {
        Result result = new Result();
        result.setSuccess(false);
        result.setMsg(null);
        result.setDetail(null);
        try {
            result.setSuccess(true);
            // TODO call tesk get team limit
            // TODO call user get all user in that course
            // TODO random split userIds
            // TODO call team to new team && add user
            //result.setDetail(teamDao.generateRandomTeam(courseId, taskId));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public Result deleteUserInTeam(String teamId, String userId) {
        Result result = new Result();
        result.setSuccess(false);
        result.setMsg(null);
        result.setDetail(null);
        try {
            result.setSuccess(true);
            result.setDetail(teamDao.deleteUserInTeam(teamId, userId));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public Result getTaskTypeById(String taskId) {
        Result result = new Result();
        result.setSuccess(false);
        result.setMsg(null);
        result.setDetail(null);
        try {
            result.setSuccess(true);
            result.setDetail(teamDao.getTaskTypeById(taskId));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public Result getTaskNameById(String taskId) {
        Result result = new Result();
        result.setSuccess(false);
        result.setMsg(null);
        result.setDetail(null);
        try {
            result.setSuccess(true);
            result.setDetail(teamDao.getTaskNameById(taskId));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }
}
