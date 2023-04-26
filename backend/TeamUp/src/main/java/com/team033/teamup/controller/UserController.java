package com.team033.teamup.controller;

import com.team033.teamup.entity.User;
import com.team033.teamup.entity.Course;
import com.team033.teamup.controller.request.FakeLoginRequest;
import com.team033.teamup.service.UserService;
import com.team033.teamup.util.JwtUtil;
import com.team033.teamup.util.Result;
//import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletRequest;


import java.util.List;
//@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {

//    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public Result getUser(@PathVariable String userId, HttpServletRequest request) {
        return userService.getUserById(userId);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public Result userLogin(@RequestBody User userInput) {
        return userService.userLogin(userInput.getEmailAddress(), userInput.getPassword());
    }

    /*
        Verify the verification code from user input.
        TODO: prevent editing information after the user clicks "send verification code" button in the frontend
              or check input information again?
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
    public Result userVerify(@RequestBody User userInput) {
        return userService.userVerify(userInput.getEmailAddress(), userInput.getVerificationCode()); // user-entered verification code from frontend
    }

    /*
        Create a new User object containing the information retrieved from the frontend.

        Map this path to "send verification code" button.
     */
    @RequestMapping(value = "/sendVerification", method = RequestMethod.POST, produces = "application/json")
//    public Result<User> userRegister(@RequestParam String emailAddress, String password, String name, int gender) {
//        User newUser = new User(name, gender, password, emailAddress);
    public Result<User> userRegister(@RequestBody User userInput) {
        User newUser = new User(userInput.getName(), userInput.getGender(), userInput.getPassword(), userInput.getEmailAddress());
        return userService.userRegister(newUser);
    }

    @GetMapping("/list")
    public Result listCourses() {
        Result courses = userService.getList();
        return courses;
    }

    /*
    WX TODO: UNCOMMENT THIS!!!!!!!!!!!!!!!!!! COMMENT THE FAKE REQUEST!!!!!!!!
     */
//    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
////    public Result<User> userLogin(@RequestBody User userInput) {
//    public String userLogin(@RequestBody User userInput) {
//        User user = userService.userLogin(userInput.getEmailAddress(), userInput.getPassword());
//        if(user == null) {
////            return Result.error(false, "Invalid account or password!");
//            return "Invalid email address or password!";
//        } else {
////            return Result.success(user, "Successfully logged in!");
//            return JwtUtil.generate(user.getEmailAddress());
//        }
//    }

    /*
    WX TODO: COMMENT THIS OUT !!!!!!!!!!!!!!!!!! FAKE REQUEST FOR WX TO TEST HER CODE
    返回值：User
     */
//    @PostMapping("/login")
//    public Result login(@RequestBody FakeLoginRequest fakeLoginRequest){
//        Result result = new Result();
//        User user = null;
//        try {
//            user = userService.userLogin(fakeLoginRequest.getEmail_address(), fakeLoginRequest.getPassword());
//            if (user == null){
//                result.setSuccess(false);
//                result.setDetail(null);
//                result.setMsg("NO USER FOUND");
//                return result;
//            }
//            result.setSuccess(true);
//            result.setDetail(user); // return a string: user_id
//        } catch (Exception e){
//            log.error("Exception in UserController -> login()",e);
//            result.setSuccess(false);
//            result.setDetail(null);
//        }
//        return result;
//    }

}