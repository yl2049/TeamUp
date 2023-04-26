package com.team033.teamup.controller;

import com.team033.teamup.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailController {
    @Autowired
    private MailService mailService;

//  example verification url: http://localhost:8080/mail/sendVerification?emailAddress=yl128@illinois.edu
    @RequestMapping(value = "/sendVerification",method = RequestMethod.POST)
    public String sendVerification(@RequestParam(value = "emailAddress")String emailAddress) {
        try {
            String subject = "TeamUp Email Verification";
            String content = "Please use the following code to verify your email: " + generateVerificationCode();

            mailService.sendMail(emailAddress, subject, content);
        } catch (Exception e) {
            return e.toString();
        }
        return "success";
    }

    private String generateVerificationCode() {
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        return code;
    }
}
