package com.example.cake.Model;

import com.google.firebase.Timestamp;

public class Review {
    private String id; // ID của đánh giá
    private String productId; // ID của sản phẩm được đánh giá
    private String customerId; // ID của khách hàng đã đánh giá
    private int rating; // Đánh giá (1-5)
    private String comment; // Bình luận
    private Timestamp reviewDate; // Ngày đánh giá

    // Constructor
    public Review() {}

    public Review(String id, String productId, String customerId, int rating, String comment, Timestamp reviewDate) {
        this.id = id;
        this.productId = productId;
        this.customerId = customerId;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public Timestamp getReviewDate() {
        return reviewDate;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setReviewDate(Timestamp reviewDate) {
        this.reviewDate = reviewDate;
    }
}
