package com.iablonski.springboot.shop.spring_online_shop.dto;

import com.iablonski.springboot.shop.spring_online_shop.entity.OrderStatus;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderIntegrationDTO {
    private Long orderId;
    private String username;
    private String address;
    private double totalSum;
    private List<OrderDetailsDTO> details;
    private OrderStatus orderStatus;

    @Override
    public String toString() {
        return "Total sum: " + totalSum + "\n\n" + "Your order: \n" + details;
    }
}
