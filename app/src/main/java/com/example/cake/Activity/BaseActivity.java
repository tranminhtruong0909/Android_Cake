package com.example.cake.Activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.cake.Adapter.AdvertisementAdapter;
import com.example.cake.Model.Advertisement;
import com.example.cake.R;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.example.cake.Controller.AdvertisementController;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private DotsIndicator dotsIndicator;
    private AdvertisementAdapter advertisementAdapter;
    private List<Advertisement> advertisementList = new ArrayList<>();
    private AdvertisementController advertisementController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base); // Thay bằng layout của bạn

        viewPager2 = findViewById(R.id.viewPager2);
        dotsIndicator = findViewById(R.id.dotIndicator);
        advertisementController = new AdvertisementController();

        // Lấy dữ liệu quảng cáo từ Firebase
        loadAdvertisements();
    }

    private void loadAdvertisements() {
        advertisementController.getAdvertisements(new AdvertisementController.OnAdvertisementsLoadedListener() {
            @Override
            public void onAdvertisementsLoaded(List<Advertisement> advertisements) {
                if (advertisements != null && !advertisements.isEmpty()) {
                    advertisementList.clear();
                    advertisementList.addAll(advertisements);
                    advertisementAdapter = new AdvertisementAdapter(advertisementList);
                    viewPager2.setAdapter(advertisementAdapter);
                    dotsIndicator.setViewPager2(viewPager2);  // Đặt DotsIndicator với ViewPager2
                } else {
                    Toast.makeText(BaseActivity.this, "Không có quảng cáo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
