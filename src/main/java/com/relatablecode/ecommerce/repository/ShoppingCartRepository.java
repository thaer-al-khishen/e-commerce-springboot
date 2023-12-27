package com.relatablecode.ecommerce.repository;

import com.relatablecode.ecommerce.auth.user.User;
import com.relatablecode.ecommerce.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUser(User user);
}
