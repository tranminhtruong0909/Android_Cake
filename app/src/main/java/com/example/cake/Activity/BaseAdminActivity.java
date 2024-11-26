package com.example.cake.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.graphics.Color;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.cake.R;

public class BaseAdminActivity extends AppCompatActivity {

    // Declare the CardViews
    private CardView productCardView;
    private CardView categoryCardView;
    private CardView userCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_admin);

        // Initialize the CardViews using findViewById()
        productCardView = findViewById(R.id.product);
        categoryCardView = findViewById(R.id.category);
        userCardView = findViewById(R.id.user);


        // Thay đổi màu thanh trạng thái thành trong suốt
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            // Đặt màu của thanh trạng thái
            window.setStatusBarColor(Color.TRANSPARENT); // Nếu bạn muốn thanh trạng thái trong suốt
        }

        // Set click listeners for the CardViews
        productCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle product management click
                Toast.makeText(BaseAdminActivity.this, "Quản Lý Sản Phẩm", Toast.LENGTH_SHORT).show();
            }
        });

        categoryCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle category management click
                Toast.makeText(BaseAdminActivity.this, "Quản Lý Loại Sản Phẩm", Toast.LENGTH_SHORT).show();
            }
        });

        userCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle user management click
                Toast.makeText(BaseAdminActivity.this, "Quản Lý Tài Khoản", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
