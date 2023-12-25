package com.relatablecode.ecommerce.repository;

import com.relatablecode.ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Custom query methods can be added here
}
