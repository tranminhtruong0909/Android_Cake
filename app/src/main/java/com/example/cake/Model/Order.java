package com.example.cake.Model;

import com.google.firebase.Timestamp;

public class Order {
    private String id; // ID của đơn hàng
    private String customerName; // Tên khách hàng
    private String phone; // Số điện thoại khách hàng
    private Timestamp orderDate; // Ngày đặt hàng
    private String status; // Trạng thái đơn hàng
    private double totalAmount; // Tổng giá trị đơn hàng

    // Constructor
    public Order() {}

    public Order(String id, String customerName, String phone, Timestamp orderDate, String status, double totalAmount) {
        this.id = id;
        this.customerName = customerName;
        this.phone = phone;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhone() {
        return phone;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public String getStatus() {
        return status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}

