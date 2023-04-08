package com.iablonski.springboot.shop.spring_online_shop.service;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.iablonski.springboot.shop.spring_online_shop.entity.Order;
import com.iablonski.springboot.shop.spring_online_shop.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class MailSenderServiceImpl implements MailSenderService {
    private final JavaMailSender mailSender;
    @Value("${server.hostname}")
    private String hostname;
    @Value("${mail.server.username}")
    private String sender;
    private String subject;
    private String content;

    @Autowired
    public MailSenderServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendActivateCode(User user) {
        subject = "Account activation";
        content = "Please activate your account by the link: \n"
                + "https://" + hostname + "/users/activate/" + user.getActivationCode();
        sendMail(user.getEmail(), subject, content);
    }

    @Override
    public void sendAndSaveOrder(Order order) {
        File orderFolder = new File("C:/Users/Default/Downloads");
        File orderFile = new File(orderFolder, order.getId() + ".json");
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        try {
            writer.writeValue(orderFile, order);
        } catch (IOException e) {
            e.printStackTrace();
        }
        subject = order.getUser().getName() + ", this id your order № " + order.getId();
        content = "Your order: " + order.getDetails().toString();
        sendMail(order.getUser().getEmail(), subject, content);
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