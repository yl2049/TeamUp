package com.team033.teamup.dao;

import com.team033.teamup.dto.InstructionOverall;
import com.team033.teamup.dto.UserWithNameAndTeamId;
import com.team033.teamup.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TeamDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public String getTeamId(String courseId, String taskId, String userId) {

        String sql = "select team_id from Participation natural join Team " +
                "where user_id = :userId and course_id = :courseId and task_id = :taskId;";

        Map<String, Object> params = new HashMap<>();
        params.put("courseId", courseId);
        params.put("taskId", taskId);
        params.put("userId", userId);

        String res = namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource(params),
                (rs, rowNum) -> rs.getString("team_id")
        );

        return res;
    }

    public List<UserWithNameAndTeamId> getTeamListByUserId(String userId) {

        String sql = "select course_name, task_name, team_id, user_id, name " +
                "from Course natural join Task natural join Team natural join Participation natural join User " +
                "where team_id in (select team_id from Participation where user_id = :userId);";

        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        List<UserWithNameAndTeamId> res = namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(params),
                (rs, rowNum) -> new UserWithNameAndTeamId(rs.getString("course_name"),
                        rs.getString("task_name"),
                        rs.getString("team_id"),
                        rs.getString("user_id"),
                        rs.getString("name"))
        );
        return res;
    }

    public List<Team> getTeamList(String courseId, String taskId) {

        String sql = "select * from Team where course_id = :courseId and task_id = :taskId;";

        Map<String, Object> params = new HashMap<>();
        params.put("courseId", courseId);
        params.put("taskId", taskId);

        List<Team> res = namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(params),
                (rs, rowNum) -> new Team(rs.getString("team_id"),
                        rs.getString("task_id"),
                        rs.getString("course_id"))
        );

        return res;
    }

    public List<InstructionOverall> getTeamCountByInstructorId(String userId) {

        String sql = "select course_id, task_id, count(team_id) as count_team " +
                "from Team natural join (select course_id, task_id " +
                "from Instruction natural join Task where user_id = :userId order by course_id asc ) as temp " +
                "group by course_id, task_id;";

        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);

        List<InstructionOverall> res = namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(params),
                (rs, rowNum) -> new InstructionOverall(rs.getString("course_id"),
                        rs.getString("task_id"),
                        rs.getInt("count_team"))
        );

        return res;
    }

    public List<UserWithNameAndTeamId> getTeamById(String teamId) {

        String sql = "select course_id, task_id, team_id, user_id, name " +
                "from Team natural join User natural join Participation " +
                "where team_id = :teamId;";

        Map<String, Object> params = new HashMap<>();
        params.put("teamId", teamId);

        List<UserWithNameAndTeamId> res = namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(params),
                (rs, rowNum) -> new UserWithNameAndTeamId(rs.getString("course_id"),
                        rs.getString("task_id"),
                        rs.getString("team_id"),
                        rs.getString("user_id"),
                        rs.getString("name"))
        );

        return res;
    }

    public Integer creatTeam(String courseId, String taskId) {

        String sql = "insert into Team values(null, :taskId, :courseId);";
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        Map<String, Object> params = new HashMap<>();
        params.put("taskId", taskId);
        params.put("courseId", courseId);

        int rowsAffected = namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params), generatedKeyHolder);
        Integer id = generatedKeyHolder.getKey().intValue();

        return id;
    }

    public Integer userJoinTeam(String teamId, String userId) {

        String sql = "insert into Participation values(:userId, :teamId);";

        Map<String, Object> params = new HashMap<>();
        params.put("teamId", teamId);
        params.put("userId", userId);

        int rowsAffected = namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params));

        return rowsAffected;
    }

    public Integer deleteUserInTeam(String teamId, String userId) {

        String sql = "delete from Participation where user_id = :userId and team_id = :teamId;";

        Map<String, Object> params = new HashMap<>();
        params.put("teamId", teamId);
        params.put("userId", userId);

        int rowsAffected = namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params));

        return rowsAffected;
    }


    public String getTaskTypeById(String taskId) {

        String sql = "select type from Task where task_id = :taskId;";

        Map<String, Object> params = new HashMap<>();
        params.put("taskId", taskId);

        String res = namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource(params),
                (rs, rowNum) -> rs.getString("type")
        );

        return res;
    }

    public String getTaskNameById(String taskId) {

        String sql = "select task_name from Task where task_id = :taskId;";

        Map<String, Object> params = new HashMap<>();
        params.put("taskId", taskId);

        String res = namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource(params),
                (rs, rowNum) -> rs.getString("task_name")
        );

        return res;
    }
}
