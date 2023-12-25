package com.relatablecode.ecommerce.repository;

import com.relatablecode.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Custom query methods can be added here
}
