package com.iablonski.springboot.shop.spring_online_shop.dto;

import com.iablonski.springboot.shop.spring_online_shop.entity.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private Long orderId;
    private String username;
    private String address;
    private LocalDateTime created;
    private LocalDateTime updated;
    private double sum;
    private OrderStatus status;
}
