package com.relatablecode.ecommerce.controller;

import com.relatablecode.ecommerce.auth.user.User;
import com.relatablecode.ecommerce.auth.user.UserRepository;
import com.relatablecode.ecommerce.dto.OrderDTO;
import com.relatablecode.ecommerce.model.Order;
import com.relatablecode.ecommerce.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;

    public OrderController(OrderService orderService, UserRepository userRepository) {
        this.orderService = orderService;
        this.userRepository = userRepository;
    }

    @PostMapping("/place-order")
    public ResponseEntity<?> placeOrder(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Order order = orderService.placeOrder(user);
        return ResponseEntity.ok("Order placed successfully with ID: " + order.getId());
    }

    @GetMapping("order-history")
    public ResponseEntity<?> getOrderHistory(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<OrderDTO> orders = orderService.getOrderHistory(user);
        return ResponseEntity.ok(orders);
    }

}
