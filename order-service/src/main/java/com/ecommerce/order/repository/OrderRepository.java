package com.ecommerce.order.repository;

import com.ecommerce.order.entity.Order;
import com.ecommerce.order.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByCustomerId(Long customerId);

    @Query("SELECT COUNT(o) FROM Order o")
    Long countAllOrders();

    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.status != 'CANCELLED'")
    BigDecimal getTotalRevenue();

    // Add this method to debug
    @Query("SELECT o FROM Order o ORDER BY o.createdAt DESC")
    List<Order> findAllOrdersOrdered();
}