package com.example.modernecommerce.modernecommerce.service.impl;

import com.example.modernecommerce.modernecommerce.model.OrderItem;
import com.example.modernecommerce.modernecommerce.repository.OrderItemRepository;
import com.example.modernecommerce.modernecommerce.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {

        return orderItemRepository.save(orderItem);
    }
}
