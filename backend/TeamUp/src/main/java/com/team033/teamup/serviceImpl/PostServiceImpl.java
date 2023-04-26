package com.team033.teamup.serviceImpl;

import com.team033.teamup.util.Result;

import com.team033.teamup.dao.PostDao;
import com.team033.teamup.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostDao postDao;

    @Override
    public Result getPostById(String postId) {

        Result result = new Result();
        result.setSuccess(false);
        result.setMsg(null);
        result.setDetail(null);
        try{
            result.setSuccess(true);
            result.setDetail(postDao.getPostById(postId));
        }
        catch (Exception e)
        {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public Result getTaskIdByCourseId(String courseId) {
        Result result = new Result();
        result.setSuccess(false);
        result.setMsg(null);
        result.setDetail(null);
        try{
            result.setSuccess(true);
            result.setDetail(postDao.getTaskIdByCourseId(courseId));
        }
        catch (Exception e)
        {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public Result getPostsByCourseTaskId(String courseId, String taskId) {
        Result result = new Result();
        result.setSuccess(false);
        result.setMsg(null);
        result.setDetail(null);
        try{
            result.setSuccess(true);
            result.setDetail(postDao.getPostsByCourseTaskId(courseId, taskId));
        }
        catch (Exception e)
        {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public Result addPost(String taskId, String courseId, String userId,
                          String weekendAvailability, String previousExperience,
                          String teamWorkStyle, String additionalComments) {
        Result result = new Result();
        result.setSuccess(false);
        result.setMsg(null);
        result.setDetail(null);
        try{
            result.setSuccess(true);
            result.setDetail(postDao.addPost(taskId, courseId, userId, weekendAvailability, previousExperience,
                    teamWorkStyle, additionalComments));
        }
        catch (Exception e)
        {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }

        return result;
    }

    @Override
    public Result deletePostByCourseTaskUserId(String courseId, String taskId, String userId){
        Result result = new Result();
        result.setSuccess(false);
        result.setMsg(null);
        result.setDetail(null);
        try{
            result.setSuccess(true);
            result.setDetail(postDao.deletePostByCourseTaskUserId(courseId, taskId, userId));
        }
        catch (Exception e)
        {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }

        return result;
    }
}
