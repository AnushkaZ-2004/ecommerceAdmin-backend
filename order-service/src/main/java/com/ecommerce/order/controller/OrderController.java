package com.ecommerce.order.controller;

import com.ecommerce.order.dto.CreateOrderRequest;
import com.ecommerce.order.dto.OrderStatusUpdate;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.entity.OrderStatus;
import com.ecommerce.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest orderRequest) {
        try {
            Order newOrder = orderService.createOrder(orderRequest);
            return ResponseEntity.ok(newOrder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public List<Order> getOrdersByStatus(@PathVariable OrderStatus status) {
        return orderService.getOrdersByStatus(status);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestBody OrderStatusUpdate statusUpdate) {
        Order updatedOrder = orderService.updateOrderStatus(id, statusUpdate.getStatus());
        if (updatedOrder != null) {
            return ResponseEntity.ok(updatedOrder);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/count")
    public Long getOrderCount() {
        return orderService.getOrderCount();
    }

    @GetMapping("/revenue")
    public BigDecimal getTotalRevenue() {
        return orderService.getTotalRevenue();
    }

    @GetMapping("/date-range")
    public List<Order> getOrdersByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return orderService.getOrdersByDateRange(startDate, endDate);
    }

    @GetMapping("/status-stats")
    public List<Object[]> getOrderCountByStatus() {
        return orderService.getOrderCountByStatus();
    }

    @GetMapping("/daily-sales")
    public List<Object[]> getDailySales() {
        return orderService.getDailySales();
    }
}