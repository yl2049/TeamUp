package com.team033.teamup.dao;

import com.team033.teamup.controller.UserController;
import com.team033.teamup.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import org.springframework.stereotype.Repository;

import java.sql.Statement;
import java.util.List;
import java.security.*;
import java.math.BigInteger;
import java.sql.PreparedStatement;

@Repository
public class UserDao {

//    Logger logger = LoggerFactory.getLogger(UserDao.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User getUserById(String userId) {

        String sql = String.format("SELECT * FROM User WHERE user_id = %s", userId);

        List<User> res = jdbcTemplate.query(sql,
                (rs, rowNum) -> new User(rs.getString("user_id"),
                        rs.getInt("identity"),
                        rs.getString("name"),
                        rs.getInt("gender"),
                        rs.getString("password"),
                        rs.getString("email_address"),
//                        ,
//                        rs.getString("verification_code"),
                        rs.getBoolean("is_enabled")
                        )
        );
        try {
            return res.get(0);
        } catch (Exception e){
            System.err.println(e.toString());
        }
        return null;
    }

    public User getUserByEmail(String emailAddress) {

        String sql = String.format("SELECT * FROM User WHERE email_address = '%s'", emailAddress);

        List<User> res = jdbcTemplate.query(sql,
                (rs, rowNum) -> new User(rs.getString("user_id"),
                        rs.getInt("identity"),
                        rs.getString("name"),
                        rs.getInt("gender"),
                        rs.getString("password"),
                        rs.getString("email_address"),
//                        ,
//                        rs.getString("verification_code"),
                        rs.getBoolean("is_enabled")
                        )
        );

        try {
            return res.get(0);
        } catch (Exception e){
            System.err.println("getUserByEmail " + e.toString());
        }
        return null;
    }

    /*
        @param: email_address. user email address
        @return: -1 when email_address exists in database and is_enabled = true,
                 0 when email_address exists in database and is_enabled = false,
                 1 when email_address does not exist in database
     */
    public int getUserByEmailCheckIsEnabled(String emailAddress) {

        if(getUserByEmail(emailAddress) == null) {
            return 1;
        }

        String sql = String.format("SELECT * FROM User WHERE email_address = '%s' AND is_enabled is TRUE", emailAddress);

        List<User> res = jdbcTemplate.query(sql,
                (rs, rowNum) -> new User(rs.getString("user_id"),
                        rs.getInt("identity"),
                        rs.getString("name"),
                        rs.getInt("gender"),
                        rs.getString("password"),
                        rs.getString("email_address")
//                        ,
//                        rs.getString("verification_code"),
//                        rs.getBoolean("is_enabled")
                )
        );
        if(res.isEmpty()) {
            return 0;
        }
        return -1;
    }

    public User getUserByEmailAndPassword(String emailAddress, String password) {

        String sql = String.format("SELECT * FROM User WHERE email_address = '%s' AND password = '%s'", emailAddress, password);

        List<User> res = jdbcTemplate.query(sql,
                (rs, rowNum) -> new User(rs.getString("user_id"),
                        rs.getInt("identity"),
                        rs.getString("name"),
                        rs.getInt("gender"),
                        rs.getString("password"),
                        rs.getString("email_address"),
//                        ,
//                        rs.getString("verification_code"),
                        rs.getBoolean("is_enabled")
                        )
        );
        try {
            return res.get(0);
        } catch (Exception e){
            System.err.println("getUserByEmailAndPassword " + e.toString());
        }
        return null;
    }

    public User userRegister(User user, int registerOption) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        String sql = "";


        System.err.println("userRegister　" +registerOption);

        // TODO: add verification_code, is_enabled in database
        if(registerOption == 1) {
          sql = "INSERT INTO User (identity, name, gender, password, email_address, verification_code, is_enabled) " +
                  "VALUES (?, ?, ?, ?, ?, ?, ?)";
            final String md5 = user.getMd5();
            final String sqlFinal = sql;
            int ret = jdbcTemplate.update(conn -> {
                PreparedStatement preparedStatement = conn.prepareStatement(sqlFinal, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, user.getIdentity());
                preparedStatement.setString(2, user.getName());
                preparedStatement.setInt(3, user.getGender());
                preparedStatement.setString(4, md5); // store password in database
                preparedStatement.setString(5, user.getEmailAddress());
                preparedStatement.setString(6, user.getVerificationCode());
                preparedStatement.setBoolean(7, user.isEnabled());

                return preparedStatement;
            }, generatedKeyHolder);
            user.setUserId(generatedKeyHolder.getKey().toString());
        } else if(registerOption == 0) {
            sql = "UPDATE User SET identity = ?, name = ?, gender = ?, password = ?, email_address = ?, " +
                    "verification_code = ?, is_enabled = ? WHERE email_address = ?";
            final String md5 = user.getMd5();
            final String sqlFinal = sql;
            try {
                int ret = jdbcTemplate.update(conn -> {
                    PreparedStatement preparedStatement = conn.prepareStatement(sqlFinal, Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setInt(1, user.getIdentity());
                    preparedStatement.setString(2, user.getName());
                    preparedStatement.setInt(3, user.getGender());
                    preparedStatement.setString(4, md5); // store password in database
                    preparedStatement.setString(5, user.getEmailAddress());
                    preparedStatement.setString(6, user.getVerificationCode());
                    preparedStatement.setBoolean(7, user.isEnabled());
                    preparedStatement.setString(8, user.getEmailAddress());

                    return preparedStatement;
                }, generatedKeyHolder);
            } catch (Exception e) {
                System.err.println(e.toString());
                return null;
            }
//            user.setUserId(generatedKeyHolder.getKey().toString());
        } else {
            return null;
        }
        return user;
    }

    /*
        @param: emailAddress, verificationCode
        @return: -1 when verification code does not match; 0 when update fails; 1 on success
     */
    public int userVerify(String emailAddress, String verificationCode) {


        String sql = String.format("SELECT * FROM User WHERE email_address = '%s' AND verification_code = '%s'",
                emailAddress, verificationCode);
        List<User> res = jdbcTemplate.query(sql,
                (rs, rowNum) -> new User(rs.getString("user_id"),
                        rs.getInt("identity"),
                        rs.getString("name"),
                        rs.getInt("gender"),
                        rs.getString("password"),
                        rs.getString("email_address"),
//                        ,
//                        rs.getString("verification_code"),
                        rs.getBoolean("is_enabled")
                )
        );
        if(res.isEmpty()) {
            return -1;
//            return "Verification code does not match. Registration failed!";
        }

        if(res.get(0).isEnabled()) {
            // user email already verified
            return 2;
        }

        int ret = jdbcTemplate.update("UPDATE User SET is_enabled = ? WHERE email_address = ?",
                true, emailAddress);

        return ret == 1 ? 1 : 0;
//        return ret == 1 ? "Registered successfully!" : "Failed to update table 'user'";
    }

}
