package com.ecommerce.analytics.service;

import com.ecommerce.analytics.dto.DashboardData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RestTemplate restTemplate;

    public DashboardData getDashboardData() {
        try {
            // Get data from other services
            Long totalOrders = getTotalOrdersFromOrderService();
            BigDecimal totalRevenue = getTotalRevenueFromOrderService();
            Long totalCustomers = getTotalCustomersFromUserService();
            Long totalProducts = getTotalProductsFromProductService();

            BigDecimal averageOrderValue = BigDecimal.ZERO;
            if (totalOrders > 0 && totalRevenue != null) {
                averageOrderValue = totalRevenue.divide(BigDecimal.valueOf(totalOrders), 2, BigDecimal.ROUND_HALF_UP);
            }

            Double conversionRate = calculateConversionRate();

            return new DashboardData(totalOrders, totalRevenue, totalCustomers, totalProducts, averageOrderValue, conversionRate);
        } catch (Exception e) {
            // Return default values if services are unavailable
            return new DashboardData(0L, BigDecimal.ZERO, 0L, 0L, BigDecimal.ZERO, 0.0);
        }
    }

    public Map<String, Object> getSalesAnalytics() {
        Map<String, Object> analytics = new HashMap<>();
        try {
            // Get daily sales data from order service
            Object[] dailySales = restTemplate.getForObject("http://localhost:8083/orders/daily-sales", Object[].class);
            analytics.put("dailySales", dailySales);

            // Get order status distribution
            Object[] orderStatus = restTemplate.getForObject("http://localhost:8083/orders/status-stats", Object[].class);
            analytics.put("orderStatusDistribution", orderStatus);

            analytics.put("totalRevenue", getTotalRevenueFromOrderService());
            analytics.put("totalOrders", getTotalOrdersFromOrderService());
        } catch (Exception e) {
            analytics.put("error", "Unable to fetch sales analytics");
        }
        return analytics;
    }

    public Map<String, Object> getProductAnalytics() {
        Map<String, Object> analytics = new HashMap<>();
        try {
            // Get product category distribution
            Object[] categoryStats = restTemplate.getForObject("http://localhost:8082/products/category-stats", Object[].class);
            analytics.put("categoryDistribution", categoryStats);

            // Get low stock products
            Object[] lowStockProducts = restTemplate.getForObject("http://localhost:8082/products/low-stock?threshold=10", Object[].class);
            analytics.put("lowStockProducts", lowStockProducts);

            analytics.put("totalProducts", getTotalProductsFromProductService());
        } catch (Exception e) {
            analytics.put("error", "Unable to fetch product analytics");
        }
        return analytics;
    }

    public Map<String, Object> getCustomerAnalytics() {
        Map<String, Object> analytics = new HashMap<>();
        try {
            analytics.put("totalCustomers", getTotalCustomersFromUserService());

            // Get customer list
            Object[] customers = restTemplate.getForObject("http://localhost:8081/users/customers", Object[].class);
            analytics.put("customers", customers);

        } catch (Exception e) {
            analytics.put("error", "Unable to fetch customer analytics");
        }
        return analytics;
    }

    private Long getTotalOrdersFromOrderService() {
        try {
            return restTemplate.getForObject("http://localhost:8083/orders/count", Long.class);
        } catch (Exception e) {
            return 0L;
        }
    }

    private BigDecimal getTotalRevenueFromOrderService() {
        try {
            return restTemplate.getForObject("http://localhost:8083/orders/revenue", BigDecimal.class);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    private Long getTotalCustomersFromUserService() {
        try {
            return restTemplate.getForObject("http://localhost:8081/users/customers/count", Long.class);
        } catch (Exception e) {
            return 0L;
        }
    }

    private Long getTotalProductsFromProductService() {
        try {
            return restTemplate.getForObject("http://localhost:8082/products/count", Long.class);
        } catch (Exception e) {
            return 0L;
        }
    }

    private Double calculateConversionRate() {
        // Mock calculation - in real scenario, you'd track visitors vs buyers
        return 2.5; // 2.5% conversion rate
    }
}