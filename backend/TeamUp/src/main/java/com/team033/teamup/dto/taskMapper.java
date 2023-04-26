package com.team033.teamup.dto;

import com.team033.teamup.entity.Task;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class taskMapper implements RowMapper<Task> {
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        Task task = new Task();
        task.setTask_id(rs.getString("task_id"));
        task.setCourse_id(rs.getString("course_id"));
        task.setType(rs.getString("type"));
        task.setTask_name(rs.getString("task_name"));
        task.setMax_size(rs.getString("max_size"));
        task.setMin_size(rs.getString("min_size"));
        task.setDescription(rs.getString("description"));
        task.setDdl(rs.getString("ddl"));
        return task;
    }
}
