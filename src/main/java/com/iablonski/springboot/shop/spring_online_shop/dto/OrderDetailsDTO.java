package com.iablonski.springboot.shop.spring_online_shop.dto;

import com.iablonski.springboot.shop.spring_online_shop.entity.OrderDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDTO {
    private String product;
    private double price;
    private double quantity;
    private double sum;

    public OrderDetailsDTO(OrderDetails orderDetails) {
        this.product = orderDetails.getProduct().getTitle();
        this.price = orderDetails.getPrice();
        this.quantity = orderDetails.getQuantity().doubleValue();
        this.sum = orderDetails.getPrice() * orderDetails.getQuantity().doubleValue();
    }
}
