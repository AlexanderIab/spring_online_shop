package com.iablonski.springboot.shop.spring_online_shop.service;

import com.iablonski.springboot.shop.spring_online_shop.dto.OrderDetailsDTO;
import com.iablonski.springboot.shop.spring_online_shop.dto.OrderIntegrationDTO;
import com.iablonski.springboot.shop.spring_online_shop.entity.Order;
import com.iablonski.springboot.shop.spring_online_shop.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        subject = order.getUser().getName() + ", this is your order № " + order.getId();
        OrderIntegrationDTO dto = new OrderIntegrationDTO();
        dto.setUsername(order.getUser().getName());
        dto.setAddress(order.getAddress());
        dto.setOrderId(order.getId());
        dto.setOrderStatus(order.getStatus());

        List<OrderDetailsDTO> details = order.getDetails().stream()
                .map(OrderDetailsDTO::new).collect(Collectors.toList());
        dto.setDetails(details);

        content = "Your order: " + order;
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