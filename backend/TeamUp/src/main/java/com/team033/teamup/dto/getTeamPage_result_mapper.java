//package com.team033.teamup.dto;
//
//import lombok.Data;
//import org.springframework.jdbc.core.RowMapper;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//@Data
//public class getTeamPage_result_mapper implements RowMapper<getTeamPage_result> {
//    public getTeamPage_result mapRow(ResultSet rs, int rowNum) throws SQLException {
//        getTeamPage_result result = new getTeamPage_result();
//        result.setteam_id(rs.getString("count_team");
//        result.setCount_team(rs.getString("count_team");
//        result.setCourse_id(rs.getString("course_id"));
//        result.setMax_size(rs.getString("max_size"));
//        result.setType(rs.getString("type"));
//        result.setTask_name(rs.getString("task_name"));
//        result.setTask_id(rs.getString("task_id"));
//        return result;
//    }
//}
