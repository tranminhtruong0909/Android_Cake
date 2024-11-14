package com.example.cake.Model;

public class Category {
    private String id; // ID của danh mục
    private String name; // Tên danh mục
    private String description; // Mô tả danh mục

    // Constructor
    public Category() {}

    public Category(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
