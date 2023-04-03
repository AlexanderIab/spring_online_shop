package com.iablonski.springboot.shop.spring_online_shop.service;

import com.iablonski.springboot.shop.spring_online_shop.configuration.OrderIntegrationConfig;
import com.iablonski.springboot.shop.spring_online_shop.repository.OrderRepository;
import com.iablonski.springboot.shop.spring_online_shop.entity.Order;
import com.iablonski.springboot.shop.spring_online_shop.dto.OrderDTO;
import com.iablonski.springboot.shop.spring_online_shop.dto.OrderDetailsDTO;
import com.iablonski.springboot.shop.spring_online_shop.dto.OrderIntegrationDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderIntegrationConfig integrationConfig;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderIntegrationConfig integrationConfig) {
        this.orderRepository = orderRepository;
        this.integrationConfig = integrationConfig;
    }

    @Override
    public void saveOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        sendIntegrationNotify(savedOrder);
    }

    private void sendIntegrationNotify(Order order) {
        OrderIntegrationDTO dto = new OrderIntegrationDTO();
        dto.setUsername(order.getUser().getName());
        dto.setAddress(order.getAddress());
        dto.setOrderId(order.getId());
        dto.setOrderStatus(order.getStatus());

        List<OrderDetailsDTO> details = order.getDetails().stream()
                .map(OrderDetailsDTO::new).collect(Collectors.toList());
        dto.setDetails(details);

        Message<OrderIntegrationDTO> message = MessageBuilder.withPayload(dto)
                .setHeader("Content-type", "application/json")
                .build();
        integrationConfig.getOrdersChannel().send(message);
    }

    @Override
    public List<OrderDTO> getOrdersByUserId(Long id) {
        List<Order> orders = orderRepository.getOrdersByUserId(id);
        List<OrderDTO> ordersDto = new ArrayList<>();
        for (Order order : orders) {
            OrderDTO orderDTO = OrderDTO.builder()
                    .orderId(order.getId())
                    .username(order.getUser().getName())
                    .address(order.getAddress())
                    .created(order.getCreated().withNano(0))
                    .updated(order.getUpdated().withNano(0))
                    .sum(order.getSum())
                    .status(order.getStatus())
                    .build();
            ordersDto.add(orderDTO);
        }
        return ordersDto;
    }
}