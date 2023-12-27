package com.relatablecode.ecommerce.repository;

import com.relatablecode.ecommerce.auth.user.User;
import com.relatablecode.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
