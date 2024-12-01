package com.example.cake.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cake.Adapter.CartAdapter;
import com.example.cake.Model.CartItem;
import com.example.cake.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItemList;
    private FirebaseFirestore db;
    private String userId;
    private TextView productNameTextView, productPriceTextView, productQuantityTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        userId = LoginActivity.userId;
        // Kiểm tra xem userId có tồn tại không
        if (userId == null) {
            Toast.makeText(this, "Lỗi: Không có userId", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Khởi tạo RecyclerView và các thành phần khác
        recyclerView = findViewById(R.id.cartRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartItemList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();

        // Lấy dữ liệu từ Intent
        String productId = getIntent().getStringExtra("productId");
        int quantity = getIntent().getIntExtra("quantity", 0);
        String size = getIntent().getStringExtra("size");

        // Kiểm tra nếu quantity hợp lệ
        if (quantity > 0) {
            // Tiến hành thêm sản phẩm vào giỏ hàng
            addProductToCart(productId, quantity, size);
        }

        // Tải danh sách sản phẩm trong giỏ hàng
        loadCartItems();
    }

    // Thêm sản phẩm vào giỏ hàng
    private void addProductToCart(String productId, int quantity, String size) {
        DocumentReference productRef = db.collection("products").document(productId);
        productRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Lấy thông tin sản phẩm từ Firestore
                CartItem cartItem = task.getResult().toObject(CartItem.class);
                if (cartItem != null) {
                    // Cập nhật số lượng
                    cartItem.setQuantity(quantity);
                    cartItem.setSize(size);
                    // Thêm sản phẩm vào giỏ hàng trong Firestore
                    db.collection("users")
                            .document(userId)
                            .collection("cart")
                            .document(productId)
                            .set(cartItem)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(CartActivity.this, "Sản phẩm đã được thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(CartActivity.this, "Lỗi khi thêm sản phẩm vào giỏ hàng.", Toast.LENGTH_SHORT).show();
                            });
                }
            }
        });
    }


    // Tải danh sách sản phẩm trong giỏ hàng
    private void loadCartItems() {
        db.collection("users")
                .document(userId)
                .collection("cart")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        double totalPrice = 0;  // Biến để tính tổng giá

                        for (DocumentSnapshot document : querySnapshot) {
                            CartItem cartItem = document.toObject(CartItem.class);
                            if (cartItem != null) {
                                // Tính giá cho từng sản phẩm
                                double productPrice = cartItem.getPrice();  // Lấy giá sản phẩm
                                int quantity = cartItem.getQuantity();  // Lấy số lượng sản phẩm
                                String size = cartItem.getSize();  // Lấy kích thước sản phẩm

                                // Nếu size là 500g thì nhân với 2, nếu không thì giữ nguyên
                                if (size != null && size.equals("500g")) {
                                    productPrice *= 2;
                                }

                                // Cộng dồn vào tổng giá
                                totalPrice += productPrice * quantity;
                                cartItemList.add(cartItem);
                            }
                        }

                        // Set tổng giá lên TextView
                        TextView totalPriceTextView = findViewById(R.id.totalPrice);
                        totalPriceTextView.setText("Tổng: " + totalPrice + " VND");

                        // Set adapter cho RecyclerView
                        cartAdapter = new CartAdapter(CartActivity.this, cartItemList);
                        recyclerView.setAdapter(cartAdapter);
                    }
                });
    }


}


