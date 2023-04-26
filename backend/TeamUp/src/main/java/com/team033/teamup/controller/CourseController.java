package com.team033.teamup.controller;

import com.team033.teamup.controller.request.*;
import com.team033.teamup.entity.Course;
import com.team033.teamup.service.PostService;
import com.team033.teamup.service.TeamService;
import com.team033.teamup.service.UserService;
import com.team033.teamup.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.team033.teamup.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/course")
public class CourseController {


    @Autowired
    private UserService userService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private CourseService courseService;


    @GetMapping("/list")
    public Result listCourses() {
        return courseService.getList();
    }

    @GetMapping("/page")
    public Map<String, Result> page(CoursePageRequest coursePageRequest){
    return courseService.page(coursePageRequest);
}

    @GetMapping("/team/page")
    public Map<String, Result> teamPage(TeamPageRequest teamPageRequest){
//        log.info("HERE the taskId is: {}", teamPageRequest.getTaskId());
        return courseService.teamPage(teamPageRequest);
    }

    @GetMapping("/pieCharts/{course_id}/{task_id}")
    public Map<String, Result> taskPieChart(@PathVariable String course_id, @PathVariable String task_id){
//        log.info("HERE the taskId is: {}", teamPageRequest.getTaskId());
        return courseService.taskPieChart(course_id, task_id);
    }

    @GetMapping("/{course_id}")
    public Map<String,Result> getById(@PathVariable String course_id){
        return courseService.getById(course_id);
    }

//    @GetMapping("tempQ2/{course_id}/{user_id}")
//    public Map<String,Result> getById(@PathVariable String course_id, @PathVariable String user_id){
//        Map<String,Result> quey2Map = courseService.getById(course_id);
//        quey2Map.put("tasks_queied_from_TEamService",teamService.getTeamCountByInstructorId(user_id));
//        return quey2Map;
//}
    @GetMapping("/tempQ2/{course_id}/{user_id}")
    public Map<String,Result> tempQ2getTeamCountByInstructorId(@PathVariable String course_id, @PathVariable String user_id){
        return courseService.tempQ2getTeamCountByInstructorId(course_id, user_id);
    }

    @PostMapping("/addcourse")
    public Result AddaCourse(@RequestBody AddCourseRequest addCourseRequest){
        return courseService.AddaCourse(addCourseRequest);
    }

    @PostMapping("/student/addcourse")
    public Result studentAddaCourse(@RequestBody StudentDeleteCourseRequest addCourseRequest){
        return courseService.studentAddaCourse(addCourseRequest);
    }

    @PostMapping("/deletecourse")
    public Result deleteCourse(@RequestBody DeleteCourseRequest deleteCourseRequest){
        return courseService.deleteCourse(deleteCourseRequest);
    }

    @PostMapping("/teacher/deleteatask")
    public Result deleteATask(@RequestBody DeleteATaskRequest deleteCourseRequest){
        return courseService.deleteATask(deleteCourseRequest);
    }

    @PostMapping("/student/deletecourse")
    public Result studentdeleteCourse(@RequestBody StudentDeleteCourseRequest deleteCourseRequest){
        return courseService.studentDeleteCourse(deleteCourseRequest);
    }

    @PostMapping("/addtask")
    public Result AddaCourse(@RequestBody AddTaskRequest addTaskRequest){
        return courseService.AddaTask(addTaskRequest);
    }

    @PostMapping("/generateRandomGroups")
    public Result AddaCourse(@RequestBody GenerateRandomGroupsRequest generateRandomGroupsRequest){
        return courseService.GenerateRandomGroups(generateRandomGroupsRequest);
    }



    @RequestMapping(value = "/{courseId}/task/{taskId}/team/user/{userId}", method = RequestMethod.GET)
    public Result getTeamIdByUserId(@PathVariable String courseId,
                                    @PathVariable String taskId, @PathVariable String userId) {
        return teamService.getTeamId(courseId, taskId, userId);
    }

    @RequestMapping(value = "/{courseId}/task/{taskId}/team/user/{userId}", method = RequestMethod.POST)
    public Result createTeamByUserId(@PathVariable String courseId,
                                    @PathVariable String taskId, @PathVariable String userId) {

        Result s = teamService.creatTeam(courseId, taskId);

        if (s != null && s.isSuccess() && s.getDetail() != null) {
            return teamService.userJoinTeam(String.valueOf(s.getDetail()), userId);
        }

        Result badResult = new Result();
        badResult.setSuccess(false);
        badResult.setMsg("Fail creating team in [CourseController]-[createTeamByUserId]-[teamService.creatTeam]");

        return badResult;
    }

    @RequestMapping(value = "/{courseId}/task/{taskId}/team", method = RequestMethod.GET)
    public Result getTeamList(@PathVariable String courseId, @PathVariable String taskId) {
        return teamService.getTeamList(courseId, taskId);
    }



}
