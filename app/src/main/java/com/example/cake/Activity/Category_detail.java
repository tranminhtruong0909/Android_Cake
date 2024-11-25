package com.example.cake.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cake.Adapter.ProductAdapter;
import com.example.cake.Controller.ProductController;
import com.example.cake.Model.Product;
import com.example.cake.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Category_detail extends AppCompatActivity {

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
        String categoryName = getIntent().getStringExtra("categoryName");
        String categoryImageUrl = getIntent().getStringExtra("categoryImage");

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
        String categoryId = getIntent().getStringExtra("categoryId");
        loadProductsForCategory(categoryId);
    }

    private void loadProductsForCategory(String categoryId) {
        // Giả sử bạn lấy danh sách sản phẩm từ Firebase hoặc cơ sở dữ liệu theo ID danh mục
        // Ví dụ:
        FirebaseDatabase.getInstance().getReference("products")
                .orderByChild("categoryId")
                .equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        productList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Product product = snapshot.getValue(Product.class);
                            productList.add(product);
                        }
                        productAdapter.notifyDataSetChanged(); // Cập nhật RecyclerView
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Xử lý lỗi nếu cần
                    }
                });
    }
}

