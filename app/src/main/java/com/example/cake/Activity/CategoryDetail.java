package com.example.cake.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cake.Adapter.ProductAdapter;
import com.example.cake.Model.Product;
import com.example.cake.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryDetail extends AppCompatActivity {

    private TextView categoryNameTextView;
    private ImageView categoryImageView;
    private RecyclerView recyclerViewProducts;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);

        // Kết nối với các View trong layout
        categoryNameTextView = findViewById(R.id.categoryTitle);
        categoryImageView = findViewById(R.id.categoryImageView);
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);

        // Nhận dữ liệu từ Intent

        // Nhận dữ liệu từ Intent
        String categoryName = getIntent().getStringExtra("categoryName");
        String categoryImageUrl = getIntent().getStringExtra("categoryImage");
        String categoryId = getIntent().getStringExtra("categoryId");

        // Hiển thị tên và ảnh của danh mục
        categoryNameTextView.setText(categoryName);
        Glide.with(this)
                .load(categoryImageUrl)
                .into(categoryImageView);

        // Khởi tạo RecyclerView
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(productList);
        recyclerViewProducts.setAdapter(productAdapter);

        // Lấy ID danh mục và tải sản phẩm

        loadProductsForCategory(categoryId);
    }

    private void loadProductsForCategory(String categoryId) {
        // Log the category ID
        Log.d("CategoryDetail", "Category ID: " + categoryId);
        try {
            FirebaseDatabase.getInstance().getReference("products")
                    .orderByChild("category") // Filter by the "category" field
                    .equalTo(categoryId) // Find products where "category" == categoryId
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // Log the entire data snapshot returned from Firebase
                            Log.d("CategoryDetail", "DataSnapshot: " + dataSnapshot.toString());

                            productList.clear();

                            // Iterate through the returned products
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                // Convert Firebase data to Product object
                                Product product = snapshot.getValue(Product.class);

                                // Check each product to avoid null errors
                                if (product != null) {
                                    Log.d("CategoryDetail", "Loaded Product: " + product.getName());
                                    productList.add(product);
                                } else {
                                    Log.e("CategoryDetail", "Product is null!");
                                }
                            }
                            // Update RecyclerView
                            productAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Log errors if Firebase fails
                            Log.e("CategoryDetail", "DatabaseError: " + databaseError.getMessage());
                        }
                    });
        } catch (Exception e) {
            // Log any exceptions that occur while setting up the listener
            Log.e("CategoryDetail", "Error adding value event listener: " + e.getMessage());
        }
    }



}

