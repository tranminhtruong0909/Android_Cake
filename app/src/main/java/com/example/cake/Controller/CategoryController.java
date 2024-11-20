package com.example.cake.Controller;

import com.example.cake.Model.Category; // Đổi thành model Category
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.CollectionReference;


import java.util.List;

public class CategoryController {
    private FirebaseFirestore db;
    private CollectionReference categoryRef;

    public CategoryController() {
        db = FirebaseFirestore.getInstance();
        categoryRef = db.collection("category");
    }

    public void getCategories(OnCategoriesLoadedListener listener) {
        categoryRef.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Category> category = queryDocumentSnapshots.toObjects(Category.class);
                    listener.onCategoriesLoaded(category);
                })
                .addOnFailureListener(e -> {
                    listener.onCategoriesLoaded(null); // Trả về null nếu có lỗi
                });
    }

    public interface OnCategoriesLoadedListener {
        void onCategoriesLoaded(List<Category> categories);
    }
}
