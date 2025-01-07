package com.example.modernecommerce.modernecommerce.service;

import com.example.modernecommerce.modernecommerce.exception.OrderException;
import com.example.modernecommerce.modernecommerce.model.Address;
import com.example.modernecommerce.modernecommerce.model.Order;
import com.example.modernecommerce.modernecommerce.model.User;

import java.util.List;

public interface OrderService {
    public Order createOrder(User user, Address shippingAddress);

    public Order findOrderById(Long orderId) throws OrderException;

    public List<Order> usersOrderHistory(Long userId);

    public Order placedOrder(Long orderId) throws OrderException;

    public Order confirmedOrder(Long orderId) throws OrderException;

    public Order shippedOrder(Long orderId) throws OrderException;

    public Order deliveredOrder(Long orderId) throws OrderException;

    public Order canceledOrder(Long orderId) throws OrderException;
    public List<Order> getAllOrders();
    public void deleteOrder(Long orderId) throws OrderException;

}
