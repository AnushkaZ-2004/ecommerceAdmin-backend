package com.ecommerce.product.repository;

import com.ecommerce.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByActiveTrue();
    List<Product> findByCategory(String category);
    List<Product> findByStockQuantityLessThan(Integer quantity);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.active = true")
    Long countActiveProducts();

    @Query("SELECT p.category, COUNT(p) FROM Product p WHERE p.active = true GROUP BY p.category")
    List<Object[]> getProductCountByCategory();
}