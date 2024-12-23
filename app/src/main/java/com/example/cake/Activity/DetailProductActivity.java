package com.example.cake.Activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import com.example.cake.Controller.ProductController;
import com.example.cake.Model.Product;
import com.example.cake.R;

public class DetailProductActivity extends AppCompatActivity {

    private TextView txtTitle, txtPrice, txtDescription;
    private ImageView imgProduct, btnCart;

    private ProductController productController;
    private TextView btn250g, btn500g;
    public String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        // Ánh xạ các View
        txtTitle = findViewById(R.id.Txttitle);
        txtPrice = findViewById(R.id.Txtprice);
        txtDescription = findViewById(R.id.Txtdescriptions);
        imgProduct = findViewById(R.id.imageView5);
        btn250g = findViewById(R.id.btn_250g);
        btn500g = findViewById(R.id.btn_500g);
        ImageView btnBack = findViewById(R.id.btnBack);
        TextView textReduce = findViewById(R.id.text_reduce);
        TextView textincrease = findViewById(R.id.text_increase);
        EditText etQuantity = findViewById(R.id.et_quantity);
        btnCart = findViewById(R.id.Btncart);
        // Nhận productId từ Intent
        String productId = getIntent().getStringExtra("productId");

        // Khởi tạo ProductController
        productController = new ProductController();

        userId = LoginActivity.userId;

        // Sự kiện nhấn nút giỏ hàng
        btnCart.setOnClickListener(v -> {

            Intent intent = new Intent(DetailProductActivity.this, CartActivity.class);

            // Lấy thông tin sản phẩm
            String productName = txtTitle.getText().toString();
            String productPrice = txtPrice.getText().toString();
            String productDescription = txtDescription.getText().toString();

            // Lấy số lượng từ EditText
            String quantityText = etQuantity.getText().toString();
            int quantity = 0;

            // Kiểm tra nếu số lượng là hợp lệ
            if (!quantityText.isEmpty()) {
                quantity = Integer.parseInt(quantityText);
            }

            // Xác định kích thước đã chọn hoặc mặc định là 250g
            String size = btn250g.isSelected() ? "250g" : (btn500g.isSelected() ? "500g" : "250g");

            // Chuyển thông tin sản phẩm, số lượng và kích thước sang CartActivity
            intent.putExtra("productId", productId);
            intent.putExtra("quantity", quantity);
            intent.putExtra("size", size);  // Thêm kích thước

            startActivity(intent);

            Log.d("CartActivity", "Quantity: " + quantity + ", Size: " + size);
        });

        // Sự kiện nhấn nút quay lại
        btnBack.setOnClickListener(v -> onBackPressed());

        // Tải dữ liệu sản phẩm từ Firestore
        if (productId != null) {
            loadProductDetails(productId);
        }

        // Xử lý sự kiện click cho các nút chọn kích thước
        btn250g.setOnClickListener(v -> {
            btn250g.setSelected(true);
            btn500g.setSelected(false);
            btn250g.setTextColor(Color.WHITE);
            btn500g.setTextColor(getResources().getColor(R.color.black));
        });

        btn500g.setOnClickListener(v -> {
            btn500g.setSelected(true);
            btn250g.setSelected(false);
            btn500g.setTextColor(Color.WHITE);
            btn250g.setTextColor(getResources().getColor(R.color.black));
        });

        // Thiết lập sự kiện nhấn nút giảm số lượng
        textReduce.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(etQuantity.getText().toString());
            if (currentQuantity > 0) {
                currentQuantity--;
                etQuantity.setText(String.valueOf(currentQuantity));
            }
        });

        // Thiết lập sự kiện nhấn nút tăng số lượng
        textincrease.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(etQuantity.getText().toString());
            currentQuantity++;
            etQuantity.setText(String.valueOf(currentQuantity));
        });

        // Thiết lập sự kiện click cho etQuantity (chỉ nhập số)
        etQuantity.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                // Nếu nhập từ bàn phím, chỉ cho phép nhập số
                etQuantity.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
            } else {
                // Nếu không focus, giữ lại giá trị đã nhập
                String quantityText = etQuantity.getText().toString();
                if (quantityText.isEmpty()) {
                    etQuantity.setText("0");
                }
            }
        });
    }

    private void loadProductDetails(String productId) {
        productController.getProducts(products -> {
            if (products != null) {
                for (Product product : products) {
                    if (product.getId().equals(productId)) {
                        // Gắn dữ liệu sản phẩm vào giao diện
                        txtTitle.setText(product.getName());
                        txtPrice.setText("$" + product.getPrice());
                        txtDescription.setText(product.getDescription());

                        // Hiển thị hình ảnh sản phẩm bằng Glide
                        Glide.with(this)
                                .load(product.getImageUrl())
                                .into(imgProduct);
                        break;
                    }
                }
            }
        });
    }
}
