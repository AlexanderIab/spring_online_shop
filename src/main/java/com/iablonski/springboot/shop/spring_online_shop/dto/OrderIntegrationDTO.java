package com.iablonski.springboot.shop.spring_online_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderIntegrationDTO {
    private Long orderId;
    private String username;
    private String address;
    private List<OrderDetailsDTO> details;
}
