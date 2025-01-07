package com.example.modernecommerce.modernecommerce.repository;

import com.example.modernecommerce.modernecommerce.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
