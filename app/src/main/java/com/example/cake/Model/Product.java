package com.example.cake.Model;

public class Product {
    private String id; // ID của sản phẩm
    private String name; // Tên sản phẩm
    private double price; // Giá sản phẩm
    private String description; // Mô tả sản phẩm
    private String imageUrl; // Đường dẫn đến hình ảnh sản phẩm
    private String category; // Danh mục sản phẩm
    private int stock; // Số lượng còn lại

    // Constructor
    public Product() {}

    public Product(String id, String name, double price, String description, String imageUrl, String category, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
        this.stock = stock;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public int getStock() {
        return stock;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
