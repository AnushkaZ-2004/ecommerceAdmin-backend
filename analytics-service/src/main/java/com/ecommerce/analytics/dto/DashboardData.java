package com.ecommerce.analytics.dto;

import java.math.BigDecimal;

public class DashboardData {
    private Long totalOrders;
    private BigDecimal totalRevenue;
    private Long totalCustomers;
    private Long totalProducts;
    private BigDecimal averageOrderValue;
    private Double conversionRate;

    public DashboardData() {}

    public DashboardData(Long totalOrders, BigDecimal totalRevenue, Long totalCustomers,
                         Long totalProducts, BigDecimal averageOrderValue, Double conversionRate) {
        this.totalOrders = totalOrders;
        this.totalRevenue = totalRevenue;
        this.totalCustomers = totalCustomers;
        this.totalProducts = totalProducts;
        this.averageOrderValue = averageOrderValue;
        this.conversionRate = conversionRate;
    }

    // Getters and Setters
    public Long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Long getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(Long totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public Long getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(Long totalProducts) {
        this.totalProducts = totalProducts;
    }

    public BigDecimal getAverageOrderValue() {
        return averageOrderValue;
    }

    public void setAverageOrderValue(BigDecimal averageOrderValue) {
        this.averageOrderValue = averageOrderValue;
    }

    public Double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(Double conversionRate) {
        this.conversionRate = conversionRate;
    }
}