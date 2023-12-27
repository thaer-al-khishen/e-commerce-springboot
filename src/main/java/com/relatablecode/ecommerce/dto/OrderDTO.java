package com.relatablecode.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.relatablecode.ecommerce.auth.user.User;
import com.relatablecode.ecommerce.enums.Status;
import com.relatablecode.ecommerce.model.OrderItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO {
    private Long id;

    @JsonIgnore
    private User user;

    private List<OrderItemDTO> items = new ArrayList<>();

    private LocalDateTime orderTime;

    private Status status; // e.g., "PLACED", "SHIPPED", "DELIVERED"

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
