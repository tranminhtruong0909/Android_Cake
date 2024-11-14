package com.example.cake.Controller;

import com.example.cake.Model.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProductController {
    private DatabaseReference productRef;

    public ProductController() {
        // Initialize Realtime Database and reference to "products" node
        productRef = FirebaseDatabase.getInstance().getReference("products");
    }

    // Thêm sản phẩm
    public void addProduct(Product product) {
        String productId = productRef.push().getKey(); // Generate a unique key for the product
        if (productId != null) {
            productRef.child(productId).setValue(product);
        }
    }

    // Sửa sản phẩm
    public void updateProduct(String productId, Product product) {
        productRef.child(productId).setValue(product);
    }

    // Xóa sản phẩm
    public void deleteProduct(String productId) {
        productRef.child(productId).removeValue();
    }

    // Lấy tất cả sản phẩm
    public void getAllProducts() {
        productRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Xử lý dữ liệu sản phẩm
            }
        });
    }
}
