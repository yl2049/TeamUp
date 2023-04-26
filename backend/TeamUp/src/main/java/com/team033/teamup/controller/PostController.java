package com.team033.teamup.controller;

import com.team033.teamup.service.PostService;
import com.team033.teamup.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/post")
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    private PostService postService;

    @RequestMapping(value = "/{postId}", method = RequestMethod.GET)
    public Result getPostById(@PathVariable String postId) {
        return postService.getPostById(postId);
    }

    @RequestMapping(value = "/getTask/{courseId}", method = RequestMethod.GET)
    public Result getTaskIdByCourseId(@PathVariable String courseId) {
        return postService.getTaskIdByCourseId(courseId);
    }

    @RequestMapping(value = "/{courseId}/getPost/{taskId}", method = RequestMethod.GET)
    public Result getPostsByCourseTaskId(@PathVariable String courseId, @PathVariable String taskId) {
        return postService.getPostsByCourseTaskId(courseId, taskId);
    }

    @RequestMapping(value = "/addPost/{taskId}/{courseId}/{userId}/{weekendAvailability}/{previousExperience}/{teamWorkStyle}/{additionalComments}",
                    method = RequestMethod.POST)
    public Result getPostsByCourseTaskId(@PathVariable String taskId, @PathVariable String courseId,
                                         @PathVariable String userId, @PathVariable String weekendAvailability,
                                         @PathVariable String previousExperience, @PathVariable String teamWorkStyle,
                                         @PathVariable String additionalComments) {
        return postService.addPost(taskId, courseId, userId, weekendAvailability, previousExperience, teamWorkStyle, additionalComments);
    }

    @RequestMapping(value = "/deletePost/{courseId}/{taskId}/{userId}", method = RequestMethod.POST)
    public Result deletePostByCourseTaskUserId(@PathVariable String courseId, @PathVariable String taskId,
                                               @PathVariable String userId) {
        return postService.deletePostByCourseTaskUserId(courseId, taskId, userId);
    }


}
