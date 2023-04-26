package com.team033.teamup.service;

public interface MailService {
    void sendMail(String sendTo, String subject, String content);
}
