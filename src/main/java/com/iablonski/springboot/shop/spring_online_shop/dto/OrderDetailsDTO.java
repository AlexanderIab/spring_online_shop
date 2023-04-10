package com.iablonski.springboot.shop.spring_online_shop.dto;

import com.iablonski.springboot.shop.spring_online_shop.entity.OrderDetails;

import lombok.*;

@Getter
@Setter
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

    @Override
    public String toString() {
        return "\n" + "Product: " + product + "\n\n" +
                "quantity: " + quantity + "\n" +
                "price: " + price + "\n" +
                "sum: " + sum;
    }
}
