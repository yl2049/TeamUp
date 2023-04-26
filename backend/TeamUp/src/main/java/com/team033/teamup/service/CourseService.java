package com.team033.teamup.service;

import com.team033.teamup.controller.request.*;
import com.team033.teamup.entity.Course;
import com.team033.teamup.util.Result;

import java.util.List;
import java.util.Map;

public interface CourseService {

    Result getList();

    Map<String, Result> page(CoursePageRequest coursePageRequest);

    Map<String,Result> getById(String course_id);

    Result AddaCourse(AddCourseRequest addCourseRequest);

    Result deleteCourse(DeleteCourseRequest deleteCourseRequest);

    Map<String, Result> tempQ2getTeamCountByInstructorId(String course_id, String user_id);

    Result AddaTask(AddTaskRequest addTaskRequest);

    Result GenerateRandomGroups(GenerateRandomGroupsRequest generateRandomGroupsRequest);

    Map<String, Result> teamPage(TeamPageRequest teamPageRequest);

    Result studentAddaCourse(StudentDeleteCourseRequest addCourseRequest);

    Result studentDeleteCourse(StudentDeleteCourseRequest deleteCourseRequest);

    Result deleteATask(DeleteATaskRequest deleteCourseRequest);

    Map<String, Result> taskPieChart(String course_id, String task_id);
}
