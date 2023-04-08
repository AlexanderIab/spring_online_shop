package com.iablonski.springboot.shop.spring_online_shop.service;

import com.iablonski.springboot.shop.spring_online_shop.entity.Order;
import com.iablonski.springboot.shop.spring_online_shop.entity.User;

public interface MailSenderService {
    void sendActivateCode(User user);

    void sendAndSaveOrder(Order order);
}