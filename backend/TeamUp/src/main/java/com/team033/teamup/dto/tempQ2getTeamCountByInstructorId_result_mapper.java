package com.team033.teamup.dto;

import com.team033.teamup.entity.Course;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class tempQ2getTeamCountByInstructorId_result_mapper implements RowMapper<tempQ2getTeamCountByInstructorId_result> {
    public tempQ2getTeamCountByInstructorId_result mapRow(ResultSet rs, int rowNum) throws SQLException {
        tempQ2getTeamCountByInstructorId_result result = new tempQ2getTeamCountByInstructorId_result();
        result.setIndex(rowNum);
        result.setCount_team(rs.getString("count_team"));
        result.setCourse_id(rs.getString("course_id"));
        result.setMax_size(rs.getString("max_size"));
        result.setType(rs.getString("type"));
        result.setTask_name(rs.getString("task_name"));
        result.setTask_id(rs.getString("task_id"));
        result.setMin_size(rs.getString("min_size"));
        result.setDescription(rs.getString("description"));
        result.setDdl(rs.getString("ddl"));
        return result;
    }
}
