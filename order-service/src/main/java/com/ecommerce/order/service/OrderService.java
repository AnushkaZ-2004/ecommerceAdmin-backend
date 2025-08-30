package com.ecommerce.order.service;

import com.ecommerce.order.dto.CreateOrderRequest;
import com.ecommerce.order.dto.OrderItemRequest;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.entity.OrderItem;
import com.ecommerce.order.entity.OrderStatus;
import com.ecommerce.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    // In your OrderService.java, make sure this method is correct:
    public List<Order> getAllOrders() {
        try {
            List<Order> orders = orderRepository.findAll();
            System.out.println("Found " + orders.size() + " orders"); // Debug log
            return orders;
        } catch (Exception e) {
            System.err.println("Error fetching orders: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Add this method to your OrderService.java

    public Order createOrder(CreateOrderRequest orderRequest) {
        Order order = new Order();
        order.setCustomerId(orderRequest.getCustomerId());
        order.setCustomerName(orderRequest.getCustomerName());
        order.setCustomerEmail(orderRequest.getCustomerEmail());
        order.setShippingAddress(orderRequest.getShippingAddress());
        order.setPaymentMethod(orderRequest.getPaymentMethod());
        order.setTotalAmount(orderRequest.getTotalAmount());
        order.setStatus(OrderStatus.PENDING);

        // Save the order first to get the ID
        Order savedOrder = orderRepository.save(order);

        // Create order items if provided
        if (orderRequest.getOrderItems() != null && !orderRequest.getOrderItems().isEmpty()) {
            List<OrderItem> orderItems = new ArrayList<>();

            for (OrderItemRequest itemRequest : orderRequest.getOrderItems()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(savedOrder);
                orderItem.setProductId(itemRequest.getProductId());
                orderItem.setProductName(itemRequest.getProductName());
                orderItem.setQuantity(itemRequest.getQuantity());
                orderItem.setPrice(itemRequest.getPrice());
                orderItem.setSubtotal(itemRequest.getSubtotal());

                orderItems.add(orderItem);
            }

            savedOrder.setOrderItems(orderItems);
        }

        return orderRepository.save(savedOrder);
    }

    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setStatus(status);
            order.setUpdatedAt(LocalDateTime.now());
            return orderRepository.save(order);
        }
        return null;
    }

    public Long getOrderCount() {
        return orderRepository.countAllOrders();
    }

    public BigDecimal getTotalRevenue() {
        BigDecimal revenue = orderRepository.getTotalRevenue();
        return revenue != null ? revenue : BigDecimal.ZERO;
    }

    public List<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findOrdersByDateRange(startDate, endDate);
    }

    public List<Object[]> getOrderCountByStatus() {
        return orderRepository.getOrderCountByStatus();
    }

    public List<Object[]> getDailySales() {
        return orderRepository.getDailySales();
    }
}