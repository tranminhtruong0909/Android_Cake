package com.example.cake.Model;

public class CartItem {
    private String productId; // ID của sản phẩm
    private String name; // Tên sản phẩm
    private double price; // Giá sản phẩm
    private String imageUrl; // Đường dẫn đến hình ảnh sản phẩm
    private int quantity;
    private String size; // Kích cỡ sản phẩm

    // Constructor
    public CartItem() {}

    public CartItem(String productId, String name, double price, String imageUrl, int quantity, String size) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.size = size;
    }

    // Getter và Setter
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
