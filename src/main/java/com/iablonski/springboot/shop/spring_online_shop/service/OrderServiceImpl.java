package com.iablonski.springboot.shop.spring_online_shop.service;

import com.iablonski.springboot.shop.spring_online_shop.repository.OrderRepository;
import com.iablonski.springboot.shop.spring_online_shop.entity.Order;
import com.iablonski.springboot.shop.spring_online_shop.dto.OrderDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final MailSenderService senderService;

//    private final OrderIntegrationConfig integrationConfig;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, MailSenderService senderService) {
        this.orderRepository = orderRepository;
        this.senderService = senderService;
    }

    @Override
    public void saveOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        senderService.sendAndSaveOrder(savedOrder);
//        sendIntegrationNotify(savedOrder);
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

//  private void sendIntegrationNotify(Order order) {
//        OrderIntegrationDTO dto = new OrderIntegrationDTO();
//        dto.setUsername(order.getUser().getName());
//        dto.setAddress(order.getAddress());
//        dto.setOrderId(order.getId());
//        dto.setOrderStatus(order.getStatus());
//
//        List<OrderDetailsDTO> details = order.getDetails().stream()
//                .map(OrderDetailsDTO::new).collect(Collectors.toList());
//        dto.setDetails(details);
//
//        Message<OrderIntegrationDTO> message = MessageBuilder.withPayload(dto)
//                .setHeader("Content-type", "application/json")
//                .build();
//        integrationConfig.getOrdersChannel().send(message);
//    }

}