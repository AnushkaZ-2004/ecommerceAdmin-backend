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

    // User Service Routes
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody Object loginRequest) {
        return restTemplate.postForEntity("http://localhost:8081/auth/login", loginRequest, Object.class);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return restTemplate.getForEntity("http://localhost:8081/users/" + id, Object.class);
    }

    // Product Service Routes
    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts() {
        return restTemplate.getForEntity("http://localhost:8082/products", Object.class);
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