// src/main/java/com/ecommerce/gateway/controller/GatewayController.java
package com.ecommerce.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class GatewayController {

    @Autowired
    private RestTemplate restTemplate;

    // Authentication Routes
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody Object loginRequest) {
        return restTemplate.postForEntity("http://localhost:8081/auth/login", loginRequest, Object.class);
    }

    @PostMapping("/auth/customer-login")
    public ResponseEntity<?> customerLogin(@RequestBody Object loginRequest) {
        // Route to the same login endpoint but for customers
        return restTemplate.postForEntity("http://localhost:8081/auth/login", loginRequest, Object.class);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<?> registerCustomer(@RequestBody Object registerRequest) {
        return restTemplate.postForEntity("http://localhost:8081/auth/register", registerRequest, Object.class);
    }

    // User Service Routes
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return restTemplate.getForEntity("http://localhost:8081/users/" + id, Object.class);
    }

    @GetMapping("/users/customers")
    public ResponseEntity<?> getCustomers() {
        return restTemplate.getForEntity("http://localhost:8081/users/customers", Object.class);
    }

    @GetMapping("/users/customers/count")
    public ResponseEntity<?> getCustomerCount() {
        return restTemplate.getForEntity("http://localhost:8081/users/customers/count", Object.class);
    }

    // Product Service Routes
    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts() {
        return restTemplate.getForEntity("http://localhost:8082/products", Object.class);
    }

    @GetMapping("/products/active")
    public ResponseEntity<?> getActiveProducts() {
        return restTemplate.getForEntity("http://localhost:8082/products/active", Object.class);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        return restTemplate.getForEntity("http://localhost:8082/products/" + id, Object.class);
    }

    @PostMapping("/products")
    public ResponseEntity<?> createProduct(@RequestBody Object product) {
        return restTemplate.postForEntity("http://localhost:8082/products", product, Object.class);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Object product) {
        restTemplate.put("http://localhost:8082/products/" + id, product);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        restTemplate.delete("http://localhost:8082/products/" + id);
        return ResponseEntity.ok().build();
    }

    // Order Service Routes
    @GetMapping("/orders")
    public ResponseEntity<?> getAllOrders() {
        return restTemplate.getForEntity("http://localhost:8083/orders", Object.class);
    }

    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@RequestBody Object orderRequest) {
        return restTemplate.postForEntity("http://localhost:8083/orders", orderRequest, Object.class);
    }

    @GetMapping("/orders/customer/{customerId}")
    public ResponseEntity<?> getCustomerOrders(@PathVariable Long customerId) {
        // This endpoint would need to be added to your Order Service
        return restTemplate.getForEntity("http://localhost:8083/orders/customer/" + customerId, Object.class);
    }

    @PutMapping("/orders/{id}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long id, @RequestBody Object statusUpdate) {
        restTemplate.put("http://localhost:8083/orders/" + id + "/status", statusUpdate);
        return ResponseEntity.ok().build();
    }

    // Analytics Service Routes
    @GetMapping("/analytics/dashboard")
    public ResponseEntity<?> getDashboardAnalytics() {
        return restTemplate.getForEntity("http://localhost:8084/analytics/dashboard", Object.class);
    }

    @GetMapping("/analytics/sales")
    public ResponseEntity<?> getSalesAnalytics() {
        return restTemplate.getForEntity("http://localhost:8084/analytics/sales", Object.class);
    }

    @GetMapping("/analytics/products")
    public ResponseEntity<?> getProductAnalytics() {
        return restTemplate.getForEntity("http://localhost:8084/analytics/products", Object.class);
    }

    @GetMapping("/analytics/customers")
    public ResponseEntity<?> getCustomerAnalytics() {
        return restTemplate.getForEntity("http://localhost:8084/analytics/customers", Object.class);
    }
}