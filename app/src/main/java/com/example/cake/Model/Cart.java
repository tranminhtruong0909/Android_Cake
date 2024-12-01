package com.example.cake.Model;

import java.util.List;

public class Cart {
    private String userId; // ID của người dùng sở hữu giỏ hàng
    private List<CartItem> items; // Danh sách các sản phẩm trong giỏ hàng

    // Constructor
    public Cart() {}

    public Cart(String userId, List<CartItem> items) {
        this.userId = userId;
        this.items = items;
    }

    // Getter và Setter
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }
}
