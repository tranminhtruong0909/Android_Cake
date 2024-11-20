package com.example.cake.Controller;


import com.example.cake.Model.Product;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.CollectionReference;


import java.util.List;

public class ProductController {
    private FirebaseFirestore db;
    private CollectionReference productRef;

    public ProductController() {
        db = FirebaseFirestore.getInstance();
        productRef = db.collection("products");
    }


    public void getProducts(OnProductsLoadedListener listener) {
        productRef.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Product> products = queryDocumentSnapshots.toObjects(Product.class);
                    listener.onProductsLoaded(products);
                })
                .addOnFailureListener(e -> {
                    listener.onProductsLoaded(null); // Trả về null nếu có lỗi
                });
    }


    // Interface cho việc tải dữ liệu sản phẩm
    public interface OnProductsLoadedListener {
        void onProductsLoaded(List<Product> products);
    }

}
