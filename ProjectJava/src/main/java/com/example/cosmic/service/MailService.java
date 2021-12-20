package com.example.cosmic.service;

import com.example.cosmic.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {
    @Autowired
    JavaMailSender emailSender;


    @Async("asyncExecutor")
    public void sendMail(User user) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        boolean multipart = true;
        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
        String htmlMsg = "<h2>Thank you for registration!</h2>";
        htmlMsg += "<h5>Your creds:</h5>";
        htmlMsg += "<p>Login: "+ user.getLogin()+"</p>";
        htmlMsg += "<p>Password: "+user.getPassword()+"</p>";
        htmlMsg += "</br>";
        htmlMsg += "<img src='https://upload.wikimedia.org/wikipedia/commons/thumb/2/26/Beats_Music_logo.svg/1200px-Beats_Music_logo.svg.png' height=200/>";
        message.setContent(htmlMsg, "text/html");
        helper.setTo(user.getEmail());
        helper.setSubject("Cosmic - listen music for free!");
        this.emailSender.send(message);
    }
}