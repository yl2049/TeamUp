package com.team033.teamup.entity;

import com.team033.teamup.controller.UserController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {


    private String userId;
    private Integer identity;
    private String name;
    private Integer gender;
    private String password;
    private String emailAddress;

    private String md5;

    // user email verification
    private String verificationCode;
    private boolean enabled;

    private String JWT;

    /*
        Constructor for user registration.
        Generate and set verification code.
        Set enabled = false.
     */
    public User(String name, int gender, String password, String emailAddress) {
        this.name = name;
        this.gender = gender;
        this.password = password;
        this.emailAddress = emailAddress;

        this.identity = 0;
        this.userId = "";
        this.enabled = false;
        convertPassword();
        setVerificationCode();
    }

    // for testing use
    public User(String userId, Integer identity, String name, Integer gender, String password, String emailAddress) {
        this.userId = userId;
        this.identity = identity;
        this.name = name;
        this.gender = gender;
        this.password = password;
        this.emailAddress = emailAddress;
    }

    public User(String userId, Integer identity, String name, Integer gender, String password, String emailAddress
            , boolean enabled) {
        this.userId = userId;
        this.identity = identity;
        this.name = name;
        this.gender = gender;
        this.password = password;
        this.emailAddress = emailAddress;
        this.enabled = enabled;
    }

    public User(String userId, Integer identity, String name, Integer gender, String password, String emailAddress,
                String verificationCode, boolean enabled) {
        this.userId = userId;
        this.identity = identity;
        this.name = name;
        this.gender = gender;
        this.password = password;
        this.emailAddress = emailAddress;
        this.verificationCode = verificationCode;
        this.enabled = enabled;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getIdentity() {
        return identity;
    }

    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode() {
        this.verificationCode = generateVerificationCode();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getMd5() {
        return this.md5;
    }

    public String getJWT() {
        return JWT;
    }

    public void setJWT(String JWT) {
        this.JWT = JWT;
    }

    /*
        This method generates a 6-digit verification code for the current user account.
     */
    private String generateVerificationCode() {
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        return code;
    }

    /*
        This method converts the user password to md5.
     */

    private boolean convertPassword() {
        // generate md5 for password
        String md5 = this.getPassword();
        byte[] bytesOfMessage;
        MessageDigest md;
        try{
            bytesOfMessage = md5.getBytes("UTF-8");
            md = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            return false;
        }
        byte[] MD5digest = md.digest(bytesOfMessage);
        BigInteger no = new BigInteger(1, MD5digest);
        md5 = no.toString(16);
        while (md5.length() < 32) {
            md5 = "0" + md5;
        }
        this.md5 = md5;
        return true;
    }
}
