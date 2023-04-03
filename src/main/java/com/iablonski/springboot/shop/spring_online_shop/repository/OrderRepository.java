package com.iablonski.springboot.shop.spring_online_shop.repository;

import com.iablonski.springboot.shop.spring_online_shop.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> getOrdersByUserId(Long userId);
}
