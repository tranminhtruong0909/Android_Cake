package com.example.cake.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.cake.Adapter.AdvertisementAdapter;
import com.example.cake.Adapter.CategoryAdapter;
import com.example.cake.Adapter.ProductAdapter;

import com.example.cake.Controller.CategoryController;
import com.example.cake.Controller.ProductController;

import com.example.cake.Model.Advertisement;
import com.example.cake.Model.Category;
import com.example.cake.Model.Product;
import com.example.cake.R;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.example.cake.Controller.AdvertisementController;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;


public class BaseActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private DotsIndicator dotsIndicator;
    private AdvertisementAdapter advertisementAdapter;
    private List<Advertisement> advertisementList = new ArrayList<>();
    private AdvertisementController advertisementController;
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private List<Category> categoryList = new ArrayList<>();
    private CategoryController categoryController;
    private ProductAdapter productAdapter;
    private List<Product> productList = new ArrayList<>();
    private ProductController productController;

    private HorizontalScrollView horizontalscrollviewCategoryHome;
    private RecyclerView recyclerViewCategoryHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base); // Layout cho activity của bạn

        // Khởi tạo các view
        viewPager2 = findViewById(R.id.viewPager2);
        dotsIndicator = findViewById(R.id.dotIndicator);
        recyclerView = findViewById(R.id.recyclerView2);
        horizontalscrollviewCategoryHome = findViewById(R.id.viewPagerCategories);

        // Khởi tạo controllers
        advertisementController = new AdvertisementController();
        categoryController = new CategoryController();

        // Lấy dữ liệu quảng cáo từ Firebase
        loadAdvertisements();

        // Lấy dữ liệu loại sản phẩm từ Firebase
        loadCategories();

        // Lấy dữ liệu sản phẩm từ Firebase
        loadProducts();
    }

    private void loadAdvertisements() {
        advertisementController.getAdvertisements(new AdvertisementController.OnAdvertisementsLoadedListener() {
            @Override
            public void onAdvertisementsLoaded(List<Advertisement> advertisements) {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
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

    private void loadCategories() {
        findViewById(R.id.progressBarCategories).setVisibility(View.VISIBLE);

        categoryController.getCategories(new CategoryController.OnCategoriesLoadedListener() {
            @Override
            public void onCategoriesLoaded(List<Category> categories) {
                findViewById(R.id.progressBarCategories).setVisibility(View.GONE);

                if (categories != null && !categories.isEmpty()) {
                    LinearLayout linearLayoutCategories = findViewById(R.id.linearLayoutCategories);
                    CategoryAdapter categoryAdapter = new CategoryAdapter(BaseActivity.this, categories);
                    categoryAdapter.addCategoriesToLayout(linearLayoutCategories);
                } else {
                    Toast.makeText(BaseActivity.this, "Không có loại sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadProducts() {
        ProductController productController = new ProductController();
        productController.getProducts(new ProductController.OnProductsLoadedListener() {
            @Override
            public void onProductsLoaded(List<Product> products) {
                findViewById(R.id.progressBarBestSeller).setVisibility(View.GONE);
                if (products != null && !products.isEmpty()) {
                    productList.clear();
                    productList.addAll(products);
                    productAdapter = new ProductAdapter(productList);
                    recyclerView.setAdapter(productAdapter);
                } else {
                    Toast.makeText(BaseActivity.this, "Không có sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
