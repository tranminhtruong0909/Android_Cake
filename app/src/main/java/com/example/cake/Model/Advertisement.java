package com.example.cake.Model;

public class Advertisement {
    private String title;
    private String description;  // Mô tả quảng cáo
    private String imageUrl;     // Đường dẫn đến hình ảnh quảng cáo
    private String link;         // Liên kết đến sản phẩm hoặc trang web (tuỳ chọn)

    // Constructor không tham số
    public Advertisement() {
        // Constructor mặc định cho Firestore
    }

    // Constructor có tham số
    public Advertisement(String title, String description, String imageUrl, String link) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.link = link;
    }

    // Constructor với 3 tham số (nếu không cần link)
    public Advertisement(String title, String description, String imageUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.link = ""; // Mặc định link trống
    }

    // Getter và Setter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    // Phương thức mới getContent() trả về description
    public String getContent() {
        return description; // Trả về mô tả quảng cáo
    }

    public void setContent(String content) {
        this.description = content; // Cập nhật mô tả quảng cáo
    }
}
