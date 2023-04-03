package com.iablonski.springboot.shop.spring_online_shop.service;

import com.iablonski.springboot.shop.spring_online_shop.entity.Order;
import com.iablonski.springboot.shop.spring_online_shop.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    void saveOrder(Order order);
    List<OrderDTO> getOrdersByUserId(Long id);
}
