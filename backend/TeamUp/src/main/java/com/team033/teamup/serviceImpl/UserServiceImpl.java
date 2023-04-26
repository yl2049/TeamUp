package com.team033.teamup.serviceImpl;

import com.team033.teamup.controller.UserController;
import com.team033.teamup.dao.UserDao;
import com.team033.teamup.entity.User;
import com.team033.teamup.service.MailService;
import com.team033.teamup.service.UserService;
import com.team033.teamup.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.team033.teamup.util.JwtUtil;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.MessageDigest;

@Service
public class UserServiceImpl implements UserService {

//    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserDao userDao;

    @Autowired
    private MailService mailService;

    @Override
    public Result getUserById(String userId) {

        Result result = new Result();
        try {
            result.setDetail(userDao.getUserById(userId));
            result.setSuccess(true);
            result.setCode(200);
        }
        catch (Exception e) {
            result.setSuccess(false);
            result.setMsg(e.getMessage());
            result.setCode(400);
        }
        return result;
    }

    @Override
    public Result userLogin(String emailAddress, String password) {
        Result result = new Result();
        try {
            String md5 = convertPassword(password);
            User user = userDao.getUserByEmailAndPassword(emailAddress, md5);
            System.err.println(user.isEnabled());
            if(user.isEnabled() == false) {
                result.setSuccess(false);
                result.setMsg("Email not verified, please register again");
                result.setCode(400);
                return result;
            }
            user.setPassword("");
            user.setJWT(JwtUtil.generate(user.getEmailAddress()));
            result.setDetail(user);
            result.setSuccess(true);
            result.setCode(200);
            result.setMsg("Successfully logged in");
        }
        catch (Exception e) {
            result.setSuccess(false);
//            result.setMsg(e.getMessage());
            result.setMsg("Invalid account or password!");
            result.setCode(400);
        }
        return result;
    }

    /*
        @param: User
        @return: Result
        A user can register when
            (1) email_address is not in database
            or
            (2) email_address exists in database and enabled = false (not verified)
     */
    @Override
    public Result userRegister(User user) {
        Result result = new Result();
        // user email address already exists and is verified
        int registerOption = userDao.getUserByEmailCheckIsEnabled(user.getEmailAddress());
        // user email address already exists and is verified
        if(registerOption == -1) {
            System.err.println("user email address already exists and is verified");
            result.setCode(400);
            result.setSuccess(false);
            result.setMsg("user email address already exists and is verified");
            return result;
        }

        try {
            User newUser = userDao.userRegister(user, registerOption);
            if(newUser != null) {
                // clear password
                newUser.setPassword("");
                result.setCode(201);
                result.setDetail(newUser);
                result.setSuccess(true);
                result.setMsg("Successfully registered, please verify your email");
                String subject = "TeamUp Email Verification";
                String content = "Please use the following code to verify your email: " + user.getVerificationCode();
                mailService.sendMail(user.getEmailAddress(), subject, content);
            } else {
                result.setCode(500);
                result.setSuccess(false);
                result.setMsg("Registration failed");
            }
        } catch (Exception e) {
            result.setCode(500);
            result.setSuccess(false);
            result.setMsg("Registration failed " + e.getMessage());
        }

        return result;
    }

    @Override
    public Result userVerify(String emailAddress, String verificationCode) {

        Result result = new Result();


        int ret = userDao.userVerify(emailAddress, verificationCode);

        if(ret == -1) {
            result.setSuccess(false);
            result.setCode(400);
            result.setMsg("Verification code does not match. Registration failed!");
        } else if (ret == 0) {
            result.setSuccess(false);
            result.setCode(400);
            result.setMsg("Failed to update table 'user'");
        } else if (ret == 2) {
            result.setSuccess(false);
            result.setCode(400);
            result.setMsg("User email already verified");
        } else {
            result.setSuccess(true);
            result.setCode(201);
            result.setMsg("Email verified! Now you can log in.");
        }
        return result;
    }

    /*
        Convert password to md5.
        Return: md5 string.
     */
    private String convertPassword(String password) {
        // generate md5 for password
        String md5 = password;
        byte[] bytesOfMessage;
        MessageDigest md;
        try{
            bytesOfMessage = md5.getBytes("UTF-8");
            md = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.err.println("user service exception" + e.toString());
            return null;
        }
        byte[] MD5digest = md.digest(bytesOfMessage);
        BigInteger no = new BigInteger(1, MD5digest);
        md5 = no.toString(16);
        while (md5.length() < 32) {
            md5 = "0" + md5;
        }
        return md5;
    }

    @Override
    public Result getList(){
        Result result=new Result();
        result.setSuccess(true);
        result.setDetail(userDao.getUserById("1"));
        return result;
    }
}
