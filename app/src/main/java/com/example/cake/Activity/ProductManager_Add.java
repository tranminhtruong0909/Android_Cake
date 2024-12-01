package com.example.cake.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cake.Model.Product;
import com.example.cake.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class ProductManager_Add extends AppCompatActivity {

    private EditText productName, productPrice, productDes, productImgUrl, productType, productStock;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_infor);

        // Khởi tạo Firestore
        db = FirebaseFirestore.getInstance();

        // Ánh xạ các EditText với id trong tệp XML
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        productDes = findViewById(R.id.productDes);
        productImgUrl = findViewById(R.id.productImgUrl);
        productType = findViewById(R.id.productType);
        productStock = findViewById(R.id.productStock);
        Button btnDeleteProduct = findViewById(R.id.btnDeleteProduct);
        Button btnEditSave = findViewById(R.id.btnEditSave);

        // Lấy thông tin sản phẩm từ Intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("productName");
        double price = intent.getDoubleExtra("productPrice", 0.0);
        String description = intent.getStringExtra("productDescription");
        String imageUrl = intent.getStringExtra("productImageUrl");
        String category = intent.getStringExtra("productCategory");
        int stock = intent.getIntExtra("productStock", 0);
        String productId = intent.getStringExtra("productId");  // Lấy ID sản phẩm từ Intent

        if (productId != null) {
            // Hiển thị thông tin sản phẩm khi sửa
            productName.setText(name);
            productPrice.setText(String.valueOf(price));
            productDes.setText(description);
            productImgUrl.setText(imageUrl);
            productType.setText(category);
            productStock.setText(String.valueOf(stock));

            // Hiển thị nút xóa và chỉnh sửa
            btnDeleteProduct.setVisibility(View.VISIBLE);
            btnEditSave.setText("Chỉnh sửa");

            // Xóa sản phẩm
            btnDeleteProduct.setOnClickListener(view -> deleteProduct(productId));

            // Chỉnh sửa sản phẩm
            btnEditSave.setOnClickListener(view -> editProduct(productId));
        } else {
            // Ẩn nút xóa sản phẩm và xử lý thêm sản phẩm mới
            btnDeleteProduct.setVisibility(View.GONE);
            btnEditSave.setOnClickListener(this::addProduct);
        }
    }


    // Hàm thêm sản phẩm
    public void addProduct(View view) {
        String name = productName.getText().toString().trim();
        String priceText = productPrice.getText().toString().trim();
        String description = productDes.getText().toString().trim();
        String imageUrl = productImgUrl.getText().toString().trim();
        String category = productType.getText().toString().trim();
        String stockText = productStock.getText().toString().trim();

        if (name.isEmpty() || priceText.isEmpty() || description.isEmpty() ||
                imageUrl.isEmpty() || category.isEmpty() || stockText.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceText);
        int stock = Integer.parseInt(stockText);

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setImageUrl(imageUrl);
        product.setCategory(category);
        product.setStock(stock);

        CollectionReference productsRef = db.collection("products");
        productsRef.add(product)
                .addOnSuccessListener(documentReference -> {
                    String documentId = documentReference.getId();
                    documentReference.update("id", documentId)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(ProductManager_Add.this, "Thêm sản phẩm thành công với ID: " + documentId, Toast.LENGTH_SHORT).show();
                                clearFields();
                            })
                            .addOnFailureListener(e -> Toast.makeText(ProductManager_Add.this, "Lỗi cập nhật ID: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                })
                .addOnFailureListener(e -> Toast.makeText(ProductManager_Add.this, "Lỗi thêm sản phẩm: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
    // Hàm xóa sản phẩm
    private void deleteProduct(String productId) {
        db.collection("products").document(productId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Lỗi xóa sản phẩm: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
    // Hàm chỉnh sửa sản phẩm
    private void editProduct(String productId) {
        String name = productName.getText().toString().trim();
        String priceText = productPrice.getText().toString().trim();
        String description = productDes.getText().toString().trim();
        String imageUrl = productImgUrl.getText().toString().trim();
        String category = productType.getText().toString().trim();
        String stockText = productStock.getText().toString().trim();

        if (name.isEmpty() || priceText.isEmpty() || description.isEmpty() ||
                imageUrl.isEmpty() || category.isEmpty() || stockText.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceText);
        int stock = Integer.parseInt(stockText);

        Product product = new Product(productId, name, price, description, imageUrl, category, stock);

        db.collection("products").document(productId)
                .set(product)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Chỉnh sửa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Lỗi chỉnh sửa sản phẩm: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
    // Hàm để xóa thông tin nhập liệu
    private void clearFields() {
        productName.setText("");
        productPrice.setText("");
        productDes.setText("");
        productImgUrl.setText("");
        productType.setText("");
        productStock.setText("");
    }
}
