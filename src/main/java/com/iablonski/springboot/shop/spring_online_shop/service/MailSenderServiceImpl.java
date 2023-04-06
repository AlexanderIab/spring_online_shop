package com.iablonski.springboot.shop.spring_online_shop.service;

import com.iablonski.springboot.shop.spring_online_shop.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderServiceImpl implements MailSenderService {
    private final JavaMailSender mailSender;
    @Value("${server.hostname}")
    private String hostname;
    @Value("${mail.server.username}")
    private String sender;

    @Autowired
    public MailSenderServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendActivateCode(User user) {
        String subject = "Account activation";
        String content = "Please activate your account by the link: \n"
                + "https://" + hostname + "/users/activate/" + user.getActivationCode();
        sendMail(user.getEmail(), subject, content);
    }

    private void sendMail(String email, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(email);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }
}