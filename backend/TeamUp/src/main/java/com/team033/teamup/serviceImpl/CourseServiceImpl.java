package com.team033.teamup.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.team033.teamup.controller.request.*;
import com.team033.teamup.dao.CourseDao;
import com.team033.teamup.dto.InstructionOverall;
import com.team033.teamup.dto.getTeamPage_result;
import com.team033.teamup.dto.tempQ2getTeamCountByInstructorId_result;
import com.team033.teamup.entity.Course;
import com.team033.teamup.entity.Task;
import com.team033.teamup.service.CourseService;
import com.team033.teamup.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseDao courseDao;

    @Override
    public Result getList(){
        Result result=new Result();
        result.setSuccess(false);
        result.setMsg(null);
        result.setDetail(null);
        try{
            result.setSuccess(true);
            result.setDetail(courseDao.getList());
        }
        catch (Exception e)
        {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public Map<String, Result> page(CoursePageRequest coursePageRequest) {
        Result result=new Result();
        result.setSuccess(false);
        result.setMsg(null);
        result.setDetail(null);
        try{
            result.setSuccess(true);
//            PageHelper.startPage(coursePageRequest.getPageNum(), coursePageRequest.getPageSize());
            List<Course> courses = courseDao.page(coursePageRequest);
//            PageInfo<Course> coursePageInfo = new PageInfo<>(courses);
            result.setDetail(courses);
        }
        catch (Exception e)
        {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }

        Result result2=new Result();
        result2.setSuccess(false);
        result2.setMsg(null);
        result2.setDetail(null);
        try{
            result2.setSuccess(true);
//            PageHelper.startPage(coursePageRequest.getPageNum(), coursePageRequest.getPageSize());
            Integer num_of_courses = courseDao.getNumofCourse(coursePageRequest);
//            PageInfo<Course> coursePageInfo = new PageInfo<>(courses);
            result2.setDetail(num_of_courses);
        }
        catch (Exception e)
        {
            result2.setSuccess(false);
            result2.setMsg(e.getMessage());
        }
        Map<String,Result> list1 = new HashMap<String,Result>(){{
            put("num_of_courses",result2);
            put("courses_result", result);
        }};
        return list1;
    }

    @Override
    public Map<String, Result> teamPage(TeamPageRequest teamPageRequest){
        Result result=new Result();
        result.setSuccess(false);
        result.setMsg(null);
        result.setDetail(null);
        try{
            result.setSuccess(true);
//            PageHelper.startPage(coursePageRequest.getPageNum(), coursePageRequest.getPageSize());
            List<getTeamPage_result> courses = courseDao.teamPage(teamPageRequest);
//            PageInfo<Course> coursePageInfo = new PageInfo<>(courses);
            result.setDetail(courses);
        }
        catch (Exception e)
        {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }

        Result result2=new Result();
        result2.setSuccess(false);
        result2.setMsg(null);
        result2.setDetail(null);
        try{
            result2.setSuccess(true);
//            PageHelper.startPage(coursePageRequest.getPageNum(), coursePageRequest.getPageSize());
            Integer num_of_courses = courseDao.getNumofTeams(teamPageRequest);
//            PageInfo<Course> coursePageInfo = new PageInfo<>(courses);
            result2.setDetail(num_of_courses);
        }
        catch (Exception e)
        {
            result2.setSuccess(false);
            result2.setMsg(e.getMessage());
        }
        Map<String,Result> list1 = new HashMap<String,Result>(){{
            put("num_of_teams",result2);
            put("team_result", result);
        }};
        return list1;
    }

    @Override
    public Map<String,Result> getById(String course_id){
        Result courses_result=new Result();
        courses_result.setSuccess(false);
        courses_result.setMsg(null);
        courses_result.setDetail(null);
        try{
            courses_result.setSuccess(true);
//            List<Course> courses =
//            List<Task> tasks = courseDao.getTasksByCourseId(course_id);
//            HashMap<String, List<>> Sites = new HashMap<Integer, String>();
            courses_result.setDetail(courseDao.getById(course_id));
        }
        catch (Exception e)
        {
            courses_result.setSuccess(false);
            courses_result.setMsg(e.getMessage());
        }

        Result tasks_result=new Result();
        tasks_result.setSuccess(false);
        tasks_result.setMsg(null);
        tasks_result.setDetail(null);
        try{
            tasks_result.setSuccess(true);
            tasks_result.setDetail(courseDao.getTasksByCourseId(course_id));
        }
        catch (Exception e)
        {
            tasks_result.setSuccess(false);
            tasks_result.setMsg(e.getMessage());
        }
        Map<String,Result> list1 = new HashMap<String,Result>(){{
            put("courses_result",courses_result);
            put("tasks_result", tasks_result);
        }};
        return list1;
    }

    @Override
    public Result AddaCourse(AddCourseRequest addCourseRequest) {
        Result result=new Result();
        result.setSuccess(false);
        result.setMsg(null);
        result.setDetail(null);
        try{
            result.setSuccess(true);
            result.setMsg("inserted");
            result.setDetail(courseDao.AddaCourse(addCourseRequest));
        }
        catch (Exception e)
        {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;

    }

    @Override
    public Result studentAddaCourse(StudentDeleteCourseRequest addCourseRequest) {
        Result result=new Result();
        result.setSuccess(false);
        result.setMsg(null);
        result.setDetail(null);
        try{
            result.setSuccess(true);
            result.setMsg("inserted");
            result.setDetail(courseDao.studentAddaCourse(addCourseRequest));
        }
        catch (Exception e)
        {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;

    }


    @Override
    public Result deleteCourse(DeleteCourseRequest deleteCourseRequest){
        Result result=new Result();
        result.setSuccess(false);
        result.setMsg(null);
        result.setDetail(null);
        try{
            result.setSuccess(true);
            result.setMsg("deleted");
            result.setDetail(courseDao.deleteCourse(deleteCourseRequest));
        }
        catch (Exception e)
        {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public Result deleteATask(DeleteATaskRequest deleteCourseRequest){
        Result result=new Result();
        result.setSuccess(false);
        result.setMsg(null);
        result.setDetail(null);
        try{
            result.setSuccess(true);
            result.setMsg("deleted");
            result.setDetail(courseDao.deleteATask(deleteCourseRequest));
        }
        catch (Exception e)
        {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public Result studentDeleteCourse(StudentDeleteCourseRequest deleteCourseRequest){
        Result result=new Result();
        result.setSuccess(false);
        result.setMsg(null);
        result.setDetail(null);
        try{
            result.setSuccess(true);
            result.setMsg("deleted");
            result.setDetail(courseDao.studentDeleteCourse(deleteCourseRequest));
        }
        catch (Exception e)
        {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public Result AddaTask(AddTaskRequest addTaskRequest){
        Result result=new Result();
        result.setSuccess(false);
        result.setMsg(null);
        result.setDetail(null);
        try{
            result.setSuccess(true);
            result.setMsg("Created Successfully!");
            result.setDetail(courseDao.AddaTask(addTaskRequest));
        }
        catch (Exception e)
        {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public Map<String, Result> tempQ2getTeamCountByInstructorId(String course_id, String user_id){
        Result result1=new Result();
        result1.setSuccess(false);
        result1.setMsg(null);
        result1.setDetail(null);
        try{
            result1.setSuccess(true);
            result1.setDetail(courseDao.getById(course_id));
        }
        catch (Exception e)
        {
            result1.setSuccess(false);
            result1.setMsg(e.getMessage());
        }

        Result result2=new Result();
        result2.setSuccess(false);
        result2.setMsg(null);
        result2.setDetail(null);
        try{
            result2.setSuccess(true);
            result2.setDetail(courseDao.tempQ2getTeamCountByInstructorId(course_id, user_id));
        }
        catch (Exception e)
        {
            result2.setSuccess(false);
            result2.setMsg(e.getMessage());
        }

        Map<String,Result> list1 = new HashMap<String,Result>(){{
            put("courses_result",result1);
            put("tasks_result", result2);
        }};
        return list1;
    }

    @Override
    public Result GenerateRandomGroups(GenerateRandomGroupsRequest generateRandomGroupsRequest){
        Result result1=new Result();
        result1.setSuccess(false);
        result1.setMsg(null);
        result1.setDetail(null);
        result1.setCode(2);
        try{
            result1.setSuccess(true);
            result1.setDetail(courseDao.GenerateRandomGroups(generateRandomGroupsRequest));
            result1.setCode(200);
        }
        catch (Exception e)
        {
            result1.setSuccess(false);
            result1.setMsg(e.getMessage());
            result1.setCode(2);
        }

        return result1;
    }

    @Override
    public Map<String, Result> taskPieChart(String course_id, String task_id){
        Result result2=new Result();
        result2.setSuccess(false);
        result2.setMsg(null);
        result2.setDetail(null);
        try{
            result2.setSuccess(true);
            result2.setDetail(courseDao.taskPieChart(course_id, task_id));
        }
        catch (Exception e)
        {
            result2.setSuccess(false);
            result2.setMsg(e.getMessage());
        }

        Map<String,Result> list1 = new HashMap<String,Result>(){{
            put("taskPieChart_result", result2);
        }};
        return list1;
    }


}
