package com.team033.teamup.service;

import com.team033.teamup.controller.request.FakeLoginRequest;
import com.team033.teamup.entity.User;
import com.team033.teamup.util.Result;

public interface UserService {

    public Result getUserById(String userId);

    public Result userLogin(String emailAddress, String password);

    public Result userRegister(User user);

    public Result userVerify(String emailAddress, String verificationCode);

    public Result getList();

}
