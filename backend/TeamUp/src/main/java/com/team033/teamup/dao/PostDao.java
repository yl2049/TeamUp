package com.team033.teamup.dao;

import com.team033.teamup.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PostDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Post getPostById(String postId) {

//        String sql = String.format( "select * from User where post_id = %s", postId);
        String sql = "select * from Post where post_id = :postId;";

        Map<String, Object> params = new HashMap<>();
        params.put("postId", postId);

        Post res = namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource(params),
                (rs, rowNum) -> new Post(
                        rs.getString("post_id"),
                        rs.getString("course_id"),
                        rs.getString("task_id"),
                        rs.getString("user_id"),
                        rs.getString("time_slot"),
                        rs.getString("additional_comments"),
                        rs.getString("weekend_availability"),
                        rs.getString("previous_experience"),
                        rs.getString("team_work_style")
                )
        );

        return res;
    }

    public List<String> getTaskIdByCourseId(String courseId) {

        String sql = "SELECT DISTINCT task_id FROM Task WHERE course_id = :courseId;";

        Map<String, Object> params = new HashMap<>();
        params.put("courseId", courseId);

        List<String> res = namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(params),
                (rs, rowNum) -> rs.getString("task_id")
        );

        return res;
    }

    public List<Post> getPostsByCourseTaskId(String courseId, String taskId) {

        String sql = "SELECT * FROM Post WHERE course_id = :courseId AND task_id = :taskId ;";

        Map<String, Object> params = new HashMap<>();
        params.put("courseId", courseId);
        params.put("taskId", taskId);

        List<Post> res = namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(params),
                (rs, rowNum) -> new Post(
                        rs.getString("post_id"),
                        rs.getString("course_id"),
                        rs.getString("task_id"),
                        rs.getString("user_id"),
                        rs.getString("time_slot"),
                        rs.getString("additional_comments"),
                        rs.getString("weekend_availability"),
                        rs.getString("previous_experience"),
                        rs.getString("team_work_style")
                    )
                );

        return res;
    }

    public int addPost(String taskId, String courseId, String userId, String weekendAvailability,
                       String previousExperience, String teamWorkStyle, String additionalComments) {
        String sql = "INSERT INTO Post(task_id, course_id, user_id, time_slot, weekend_availability, previous_experience, team_work_style, additional_comments)" +
                " VALUES (:taskId, :courseId, :userId, null, :weekendAvailability, :previousExperience, :teamWorkStyle, :additionalComments);";

        Map<String, Object> params = new HashMap<>();
        params.put("taskId", taskId);
        params.put("courseId", courseId);
        params.put("userId", userId);
        params.put("weekendAvailability", weekendAvailability);
        params.put("previousExperience", previousExperience);
        params.put("teamWorkStyle", teamWorkStyle);
        params.put("additionalComments", additionalComments);

        int rowsAffected = namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params));

        return rowsAffected;
    }

    public int deletePostByCourseTaskUserId(String courseId, String taskId, String userId){
        String sql = "DELETE FROM Post WHERE course_id = :courseId AND task_id = :taskId AND user_id = :userId";

        Map<String, Object> params = new HashMap<>();
        params.put("courseId", courseId);
        params.put("taskId", taskId);
        params.put("userId", userId);

        int rowsAffected = namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params));

        return rowsAffected;

    }

}
