package com.example.cake.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cake.R;

public class AboutShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_shop);

        Button btnVisitShop = findViewById(R.id.btnVisitShop);

        btnVisitShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutShopActivity.this, BaseActivity.class);
                startActivity(intent);
                finish(); // Kết thúc `AboutShopActivity` nếu không cần quay lại
            }
        });
    }
}
