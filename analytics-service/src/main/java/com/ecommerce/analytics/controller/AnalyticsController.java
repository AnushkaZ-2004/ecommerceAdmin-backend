package com.ecommerce.analytics.controller;

import com.ecommerce.analytics.dto.DashboardData;
import com.ecommerce.analytics.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/dashboard")
    public DashboardData getDashboardData() {
        return analyticsService.getDashboardData();
    }

    @GetMapping("/sales")
    public Map<String, Object> getSalesAnalytics() {
        return analyticsService.getSalesAnalytics();
    }

    @GetMapping("/products")
    public Map<String, Object> getProductAnalytics() {
        return analyticsService.getProductAnalytics();
    }

    @GetMapping("/customers")
    public Map<String, Object> getCustomerAnalytics() {
        return analyticsService.getCustomerAnalytics();
    }
}