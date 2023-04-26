package com.team033.teamup.dao;

import com.team033.teamup.controller.request.*;
import com.team033.teamup.dto.*;
import com.team033.teamup.entity.Course;
import com.team033.teamup.entity.Task;
import com.team033.teamup.entity.Team;
import com.team033.teamup.entity.User;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

@Repository
@Slf4j
public class CourseDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public List<Course> getList() {
        String sql = "select * from Course limit 10;";
        List<Course> rs = jdbcTemplate.query(sql, new courseMapper());
        return rs;
    }

    public List<Course> page(CoursePageRequest coursePageRequest) {
        String sql = "select * ";
        if (coursePageRequest.getIdentity() == 0 ){
            sql = sql + "from Course natural join Enrollment";
        }
        else{
            sql = sql + "from Course natural join Instruction";
        }

        sql = sql + " where user_id = " + coursePageRequest.getUserId();

        if (coursePageRequest.getCourse_num()!="" && coursePageRequest.getCourse_num()!=null ){
            sql = sql + " and course_num like '%" + coursePageRequest.getCourse_num() + "%'";
            if (coursePageRequest.getCourse_name()!="" && coursePageRequest.getCourse_name()!=null){
                sql = sql + " and course_name like '%" + coursePageRequest.getCourse_name() + "%'";
            }
        }
        else{
            if (coursePageRequest.getCourse_name()!="" && coursePageRequest.getCourse_name()!=null){
                sql = sql + " and course_name like '%" + coursePageRequest.getCourse_name() + "%'";
            }
        }
        Integer temp = (coursePageRequest.getPageNum() -1) * coursePageRequest.getPageSize();
        sql = sql + " order by semester DESC, course_num, course_id DESC LIMIT " + coursePageRequest.getPageSize() + " OFFSET " + temp + ";";

//        select * from course-->
//<!--        <where>-->
//<!--            <if test="course_num!= null and course_num !=''"> course_num like concat('%',#{course_num}, '%')</if>-->
//<!--            <if test="course_name!= null and course_name !=''"> course_name like concat('%',#{course_name}, '%')</if>-->
//<!--        </where>-->
//<!--        order by course_id, semester DESC-->
        List<Course> rs = jdbcTemplate.query(sql, new courseMapper());
        return rs;
    }

    public Integer getNumofCourse(CoursePageRequest coursePageRequest){
//        String sql = "select count(*) from course";
        String sql = "select count(*) ";
        if (coursePageRequest.getIdentity() == 0 ){
            sql = sql + "from Course natural join Enrollment";
        }
        else{
            sql = sql + "from Course natural join Instruction";
        }
        sql = sql + " where user_id = " + coursePageRequest.getUserId();
        if (coursePageRequest.getCourse_num()!="" && coursePageRequest.getCourse_num()!=null ){
            sql = sql + " and course_num like '%" + coursePageRequest.getCourse_num() + "%'";
            if (coursePageRequest.getCourse_name()!="" && coursePageRequest.getCourse_name()!=null){
                sql = sql + " and course_name like '%" + coursePageRequest.getCourse_name() + "%'";
            }
        }
        else{
            if (coursePageRequest.getCourse_name()!="" && coursePageRequest.getCourse_name()!=null){
                sql = sql + " and course_name like '%" + coursePageRequest.getCourse_name() + "%'";
            }
        }
        Integer temp = (coursePageRequest.getPageNum() -1) * coursePageRequest.getPageSize();
        sql = sql + " order by semester DESC, course_num, course_id DESC;";

        int count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count;
    }

    public List<Course> getById(String course_id) {
//        <!--    <select id="getById" resultType="com.team033.teamup.entity.Course">-->
//<!--        select * from course where course_id = #{course_id}-->
//<!--    </select>-->
        String sql = "select * from Course where course_id = " + course_id + " order by course_id;";
        List<Course> rs = jdbcTemplate.query(sql, new courseMapper());
        return rs;
    }

    public List<Task> getTasksByCourseId(String course_id) {
//        <!--    <select id="getTasksByCourseId" resultType="com.team033.teamup.entity.Task">-->
//<!--        select * from task where course_id = #{course_id}-->
//<!--    </select>-->
        String sql = "select * from Task where course_id = " + course_id + " order by task_id;";
        List<Task> rs = jdbcTemplate.query(sql, new taskMapper());
        return rs;
    }

    public Map<String, String> AddaCourse(AddCourseRequest theNEWcourse) {
        List<Course> rs = new ArrayList<>();
        String temp ="";
        String temp2 = theNEWcourse.getSemester().replaceAll(" ", "");
        theNEWcourse.setSemester(temp2);
        /*
        TODO: CAN REPLACE WITH the SNOWFLAKE
         */
        while(true){
            temp ="";
            String random_num = createRandomStr1(5);
            temp = theNEWcourse.getCourse_num() + "-" + random_num;
            String sql1 = String.format("select * from Course where register_code = '%s';", temp);
            rs = jdbcTemplate.query(sql1, new courseMapper());
            if (rs.size()==0){break;}
        }
//        theNEWcourse.setRegister_code(temp);

        String sql = "insert into Course values(null, :semester, :register_code, :course_name, :course_num);";
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        Map<String, Object> params = new HashMap<>();
        params.put("semester", theNEWcourse.getSemester());
        params.put("register_code", temp);
        params.put("course_name", theNEWcourse.getCourse_name());
        params.put("course_num", theNEWcourse.getCourse_num());

        int rowsAffected = namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params), generatedKeyHolder);
        Integer id = generatedKeyHolder.getKey().intValue();

        // Insert into the instruction Table:

        String sql2 = "insert into Instruction values(:user_id, :course_id);";
        Map<String, Object> params2 = new HashMap<>();
        params2.put("user_id", theNEWcourse.getUserId());
        params2.put("course_id", id.toString());

        int rowsAffected2 = namedParameterJdbcTemplate.update(sql2, new MapSqlParameterSource(params2));


        Map<String, String> the_course_code_AND_id = new HashMap<String, String>();
        the_course_code_AND_id.put("new_course_id",id.toString());
        the_course_code_AND_id.put("register_code",temp);

        return the_course_code_AND_id;
    }

    public String createRandomStr1(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            stringBuffer.append(str.charAt(number));
        }
        return stringBuffer.toString();
    }

    public String deleteCourse(DeleteCourseRequest deleteCourseRequest) {

        String sql = "SELECT course_id from Course where register_code = '" + deleteCourseRequest.getRegister_code() + "';";

        int cIId = jdbcTemplate.queryForObject(sql, Integer.class);

//        String sql2 = "insert into instruction values(:user_id, :course_id);";
        String sql2 = "DELETE FROM Instruction WHERE course_id = :course_id and user_id = :user_id;";
        Map<String, Object> params2 = new HashMap<>();
        params2.put("user_id", deleteCourseRequest.getUserId());
        params2.put("course_id", cIId);

        int rowsAffected2 = namedParameterJdbcTemplate.update(sql2, new MapSqlParameterSource(params2));

        String sql1 = "DELETE FROM Course WHERE register_code = :register_code;";
        Map<String, Object> params1 = new HashMap<>();
        params1.put("register_code", deleteCourseRequest.getRegister_code());

        int rowsAffected1 = namedParameterJdbcTemplate.update(sql1, new MapSqlParameterSource(params1));

        String msg_to_return = "";
        if (rowsAffected1 ==0){
            msg_to_return = "!!!!! NO Course FOUND !!!!!";
        }
        else if (rowsAffected1 ==1){
            msg_to_return = "===== DELETED SUCCESSFULLY =====";
        }
        else{
            msg_to_return = "WRONG!!!!!";
        }
        return msg_to_return;
    }

    /*
    * Copied from TeamDao.getTeamCountByInstructorId()
    * returns the result with the same attribute but query with one more param: course_id
    * */
    public List<tempQ2getTeamCountByInstructorId_result> tempQ2getTeamCountByInstructorId(String course_id, String userId) {

//        String sql = "SELECT * FROM (select course_id, task_id, count(team_id) " +
//                "as count_team from Team natural join (select course_id, task_id from Instruction natural join Task " +
//                "where user_id = " + userId +
//                " AND course_id = " + course_id + ") as temp group by course_id, task_id) AS temp3 " +
//                "NATURAL JOIN task order by course_id, task_id;";
        String sql = "SELECT * FROM (SELECT DISTINCT Task.course_id, Task.task_id, IFNULL(temp3.count_team, 0) count_team " +
                "FROM Task LEFT JOIN (select course_id, task_id, count(team_id) as count_team from Team natural join " +
                "(select course_id, task_id from Instruction natural join Task where user_id = " + userId +" AND course_id= " + course_id +
                " ) AS temp group by course_id, task_id) AS temp3 ON Task.task_id = temp3.task_id WHERE Task.course_id=" + course_id + " ) AS temp4 " +
                "NATURAL JOIN Task order by Task.task_id;";

        List<tempQ2getTeamCountByInstructorId_result> rs = jdbcTemplate.query(sql, new tempQ2getTeamCountByInstructorId_result_mapper());
        return rs;
    }

    public Object AddaTask(AddTaskRequest addTaskRequest) {
        String sql = "insert into Task values(null, :course_id, :type, :task_name, :max_size, :min_size, :description, :ddl);";
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        Map<String, Object> params = new HashMap<>();
        params.put("course_id", addTaskRequest.getCourse_id());
        params.put("type", addTaskRequest.getType());
        params.put("task_name", addTaskRequest.getTask_name());
        params.put("max_size", addTaskRequest.getMax_size());
        params.put("min_size", addTaskRequest.getMin_size());
        params.put("description", addTaskRequest.getDescription());
        params.put("ddl", addTaskRequest.getDdl());

        int rowsAffected = namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params), generatedKeyHolder);
        Integer id = generatedKeyHolder.getKey().intValue();

        // return object:
        Map<String, String> the_course_code_AND_id = new HashMap<String, String>();
        the_course_code_AND_id.put("new_task_id",id.toString());
        the_course_code_AND_id.put("task_name",addTaskRequest.getTask_name());

        return the_course_code_AND_id;
    }

    public Object GenerateRandomGroups(GenerateRandomGroupsRequest generateRandomGroupsRequest) {
        this.jdbcTemplate.execute("call randomAssignTeam1 (" + generateRandomGroupsRequest.getCourse_id() + "," + generateRandomGroupsRequest.getTask_id() + ")");
        this.jdbcTemplate.execute("call randomAssignTeam2 (" + generateRandomGroupsRequest.getCourse_id() + "," + generateRandomGroupsRequest.getTask_id() + ")");

        return 1;
    }

    public List<getTeamPage_result> teamPage(TeamPageRequest teamPageRequest) {
        String sql = "SELECT team_id, COUNT(distinct Participation.user_id) AS team_size, " +
                "JSON_ARRAYAGG(JSON_OBJECT('name', User.name, 'user_id', User.user_id)) AS usersInfo " +
                "FROM (SELECT * FROM Team WHERE " +
                "task_id = :task_id AND course_id = :course_id) AS temp1 NATURAL JOIN Participation " +
                "JOIN User ON User.user_id = Participation.user_id GROUP BY team_id ORDER BY team_id LIMIT :limit  OFFSET :offset;";


        Integer temp = (teamPageRequest.getPageNum() -1) * teamPageRequest.getPageSize();

        Map<String, Object> params = new HashMap<>();
//        log.info("the taskId is: {}", teamPageRequest.getTaskId());
//        log.info("the courseId is: {}", teamPageRequest.getCourseId());

        params.put("task_id", teamPageRequest.getTask_id());
        params.put("course_id", teamPageRequest.getCourse_id());
        params.put("limit", teamPageRequest.getPageSize());
        params.put("offset", temp);
        List<getTeamPage_result> res = namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(params),
                (rs, rowNum) -> new getTeamPage_result(
                        rs.getString("team_id"),
                        rs.getString("team_size"),
                        rs.getString("usersInfo")
                )
        );
        return res;
    }

    public Integer getNumofTeams(TeamPageRequest teamPageRequest) {
        String sql = "SELECT COUNT(team_id) FROM Team WHERE task_id = " + teamPageRequest.getTask_id() + " AND course_id = " + teamPageRequest.getCourse_id() +";";
        int count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count;
    }

    public String getCourseNameByCourseId(String courseId) {

        String sql = "select course_name from Course where course_id = :courseId;";

        Map<String, Object> params = new HashMap<>();
        params.put("courseId", courseId);

        String res = namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource(params),
                (rs, rowNum) -> rs.getString("course_name")
        );

        return res;
    }

    public String studentAddaCourse(StudentDeleteCourseRequest addCourseRequest) {

        List<Course> rs = new ArrayList<>();
        String sql1 = String.format("select * from Course where register_code = '%s';", addCourseRequest.getRegister_code());
        rs = jdbcTemplate.query(sql1, new courseMapper());
        if (rs.size()==0){
            return "===!!! Register Code does not exist !!!===";
        }
        else{
            String sql2 = "insert into Enrollment values( :course_id, :user_id);";
            Map<String, Object> params2 = new HashMap<>();
            params2.put("course_id", rs.get(0).getCourse_id());
            params2.put("user_id", addCourseRequest.getUserId());

            int rowsAffected2 = namedParameterJdbcTemplate.update(sql2, new MapSqlParameterSource(params2));
        }
        return "Success!";
    }

    public String studentDeleteCourse(StudentDeleteCourseRequest deleteCourseRequest) {
        List<Course> rs = new ArrayList<>();
        String sql1 = String.format("select * from Course where register_code = '%s';", deleteCourseRequest.getRegister_code());
        rs = jdbcTemplate.query(sql1, new courseMapper());
        if (rs.size()==0){
            return "===!!! Register Code does not exist !!!===";
        }
        else{
            String sql2 = "DELETE FROM Enrollment WHERE course_id = :course_id and user_id = :user_id;";
            Map<String, Object> params2 = new HashMap<>();
            params2.put("course_id", rs.get(0).getCourse_id());
            params2.put("user_id", deleteCourseRequest.getUserId());

            int rowsAffected2 = namedParameterJdbcTemplate.update(sql2, new MapSqlParameterSource(params2));
        }
        return "=== Deleted Successfully! ===";
    }

    public String deleteATask(DeleteATaskRequest deleteCourseRequest) {
        String sql2 = "DELETE FROM Task WHERE course_id = :course_id and task_id = :task_id;";
        Map<String, Object> params2 = new HashMap<>();
        params2.put("course_id", deleteCourseRequest.getCourse_id());
        params2.put("task_id", deleteCourseRequest.getTask_id());

        int rowsAffected2 = namedParameterJdbcTemplate.update(sql2, new MapSqlParameterSource(params2));
        return "=== Deleted The Task Successfully! ===";
    }


    public List<TaskPieChart_result> taskPieChart(String course_id, String task_id) {
        String sql = "select teamSize, COUNT(teamSize) AS numOfTeams FROM (select COUNT(Distinct user_id) AS teamSize " +
                "FROM (SELECT team_id FROM Team WHERE course_id = " + course_id + " AND task_id = " + task_id + ") AS temp1 " +
                "NATURAL JOIN Participation GROUP BY team_id) AS temp2 GROUP BY teamSize";

        List<TaskPieChart_result> rs = jdbcTemplate.query(sql, new TaskPieChart_result_mapper());
        return rs;

    }
}
