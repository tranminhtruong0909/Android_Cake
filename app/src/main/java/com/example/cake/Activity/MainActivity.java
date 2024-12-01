package com.example.cake.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cake.Model.Product;
import com.example.cake.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.CollectionReference;

public class MainActivity extends AppCompatActivity {

    private EditText edtName, edtPrice, edtDescription, edtImageUrl, edtCategory, edtStock;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo Firestore
        db = FirebaseFirestore.getInstance();

        // Ánh xạ các EditText
        edtName = findViewById(R.id.edtName);
        edtPrice = findViewById(R.id.edtPrice);
        edtDescription = findViewById(R.id.edtDescription);
        edtImageUrl = findViewById(R.id.edtImageUrl);
        edtCategory = findViewById(R.id.edtCategory);
        edtStock = findViewById(R.id.edtStock);
    }

    public void addProduct(View view) {
        // Lấy dữ liệu từ các EditText
        String name = edtName.getText().toString().trim();
        double price = Double.parseDouble(edtPrice.getText().toString().trim());
        String description = edtDescription.getText().toString().trim();
        String imageUrl = edtImageUrl.getText().toString().trim();
        String category = edtCategory.getText().toString().trim();
        int stock = Integer.parseInt(edtStock.getText().toString().trim());

        // Tạo đối tượng Product, chưa có id
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setImageUrl(imageUrl);
        product.setCategory(category);
        product.setStock(stock);

        // Lấy reference đến collection "products"
        CollectionReference productsRef = db.collection("products");

        // Thêm sản phẩm vào Firestore
        productsRef.add(product)
                .addOnSuccessListener(documentReference -> {
                    // Lấy Document ID tự động
                    String documentId = documentReference.getId();

                    // Cập nhật lại trường id trong Firestore
                    documentReference.update("id", documentId)
                            .addOnSuccessListener(aVoid -> {
                                // Hiển thị Toast khi cập nhật thành công
                                Toast.makeText(MainActivity.this, "Sản phẩm đã được thêm với ID: " + documentId, Toast.LENGTH_SHORT).show();
                                // Xóa thông tin sau khi thêm thành công
                                clearFields();
                            })
                            .addOnFailureListener(e -> {
                                // Hiển thị lỗi nếu không cập nhật được
                                Toast.makeText(MainActivity.this, "Lỗi khi cập nhật ID: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                })
                .addOnFailureListener(e -> {
                    // Hiển thị lỗi nếu không thêm được
                    Toast.makeText(MainActivity.this, "Lỗi khi thêm sản phẩm: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // Hàm để xóa dữ liệu trong các trường nhập
    private void clearFields() {
        edtName.setText("");
        edtPrice.setText("");
        edtDescription.setText("");
        edtImageUrl.setText("");
        edtCategory.setText("");
        edtStock.setText("");
    }
}
