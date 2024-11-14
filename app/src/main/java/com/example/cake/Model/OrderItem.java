package com.example.cake.Model;

public class OrderItem {
    private String id; // ID của sản phẩm trong đơn hàng
    private String orderId; // ID của đơn hàng mà sản phẩm thuộc về
    private String productId; // ID của sản phẩm
    private int quantity; // Số lượng sản phẩm
    private double price; // Giá sản phẩm tại thời điểm đặt hàng

    // Constructor
    public OrderItem() {}

    public OrderItem(String id, String orderId, String productId, int quantity, double price) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

