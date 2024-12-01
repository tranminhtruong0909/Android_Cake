package com.example.cake.Activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cake.Adapter.ProductAdminAdapter;
import com.example.cake.Model.Product;
import com.example.cake.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ProductManagerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdminAdapter adapter;
    private List<Product> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_manager);

        recyclerView = findViewById(R.id.recyclerViewProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo danh sách và adapter
        productList = new ArrayList<>();
        adapter = new ProductAdminAdapter(productList);
        recyclerView.setAdapter(adapter);

        // Lấy dữ liệu từ Firestore
        getProducts();
        ProductAdminAdapter adapter = new ProductAdminAdapter(productList);
        adapter.setOnItemClickListener(product -> {
            // Truyền thông tin sản phẩm vào Intent
            Intent intent = new Intent(ProductManagerActivity.this, ProductManager_Add.class);
            intent.putExtra("productName", product.getName());
            intent.putExtra("productPrice", product.getPrice());
            intent.putExtra("productDescription", product.getDescription());
            intent.putExtra("productImageUrl", product.getImageUrl());
            intent.putExtra("productCategory", product.getCategory());
            intent.putExtra("productStock", product.getStock());
            intent.putExtra("productId", product.getId());
            startActivity(intent);
        });




        // Xử lý sự kiện
        ImageView imageViewAddProduct = findViewById(R.id.imageViewAddProduct);
        imageViewAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent đến màn hình thêm sản phẩm
                Intent intent = new Intent(ProductManagerActivity.this, ProductManager_Add.class);
                startActivity(intent);
            }
        });

    }

    private void getProducts() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference productsCollection = db.collection("products");

        productsCollection.get().addOnSuccessListener(querySnapshot -> {
            if (!querySnapshot.isEmpty()) {
                for (DocumentSnapshot document : querySnapshot) {
                    Product product = document.toObject(Product.class);
                    if (product != null) {
                        productList.add(product);
                    }
                }
                // Cập nhật adapter sau khi tải xong dữ liệu
                adapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(e -> {
            Log.e("FirestoreError", "Error fetching products", e);
        });
    }
}