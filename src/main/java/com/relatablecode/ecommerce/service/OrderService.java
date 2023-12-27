package com.relatablecode.ecommerce.service;

import com.relatablecode.ecommerce.auth.role.ERole;
import com.relatablecode.ecommerce.auth.role.Role;
import com.relatablecode.ecommerce.auth.user.User;
import com.relatablecode.ecommerce.dto.OrderDTO;
import com.relatablecode.ecommerce.enums.Status;
import com.relatablecode.ecommerce.model.CartItem;
import com.relatablecode.ecommerce.model.Order;
import com.relatablecode.ecommerce.model.OrderItem;
import com.relatablecode.ecommerce.model.ShoppingCart;
import com.relatablecode.ecommerce.repository.CartItemRepository;
import com.relatablecode.ecommerce.repository.OrderRepository;
import com.relatablecode.ecommerce.repository.ShoppingCartRepository;
import com.relatablecode.ecommerce.utils.GeneralUtils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final ModelMapper modelMapper;

    public OrderService(OrderRepository orderRepository, ShoppingCartRepository shoppingCartRepository, CartItemRepository cartItemRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemRepository = cartItemRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public Order placeOrder(User user) {
        ShoppingCart cart = shoppingCartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cannot place an order with an empty cart");
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderTime(LocalDateTime.now());
        order.setStatus(Status.PLACED);

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            order.getItems().add(orderItem);
        }

        orderRepository.save(order);

        // Clear the shopping cart
        cart.getItems().clear();
        shoppingCartRepository.save(cart);

        return order;
    }

    // Other methods like getOrderHistory, updateOrderStatus, etc.
    public List<OrderDTO> getOrderHistory(User user) {

        //Return all orders for the admin, else return only the user specific orders
        if (GeneralUtils.isAdminUser(user)) {
            return orderRepository.findAll().stream()
                    .map(order -> modelMapper.map(order, OrderDTO.class))
                    .collect(Collectors.toList());
        } else {
            return orderRepository.findByUser(user).stream()
                    .map(order -> modelMapper.map(order, OrderDTO.class))
                    .collect(Collectors.toList());
        }

    }

    public Order updateOrderStatus(Long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(Status.valueOf(newStatus));
        return orderRepository.save(order);
    }

}
