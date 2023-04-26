package com.team033.teamup.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import com.team033.teamup.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class MailServiceImpl implements MailService{
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String sendFrom;

    public void sendMail(String sendTo, String subject, String content) {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(sendFrom);
        message.setTo(sendTo);
//        message.setSubject("TeamUp Email Verification");
//        message.setText("Please use the following code to verify your email:" +　);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
        log.info("Email sent successfully");
    }


}
