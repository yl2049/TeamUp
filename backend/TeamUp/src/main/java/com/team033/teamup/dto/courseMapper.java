package com.team033.teamup.dto;

import com.team033.teamup.entity.Course;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class courseMapper implements RowMapper<Course> {
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        Course course = new Course();
        course.setCourse_id(rs.getString("course_id"));
        course.setCourse_num(rs.getString("course_num"));
        course.setCourse_name(rs.getString("course_name"));
        course.setSemester(rs.getString("semester"));
        course.setRegister_code(rs.getString("register_code"));
        return course;
    }
}
