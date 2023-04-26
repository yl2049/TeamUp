package com.team033.teamup.dto;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskPieChart_result_mapper implements RowMapper<TaskPieChart_result> {
    public TaskPieChart_result mapRow(ResultSet rs, int rowNum) throws SQLException {
        TaskPieChart_result result = new TaskPieChart_result();
        String temp = "Team Size of ";
        temp = temp + rs.getString("teamSize");
        result.setName(temp);
        result.setValue(rs.getString("numOfTeams"));
        return result;
    }
}
