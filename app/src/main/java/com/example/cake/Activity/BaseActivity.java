package com.example.cake.Activity;


import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.example.cake.Controller.AdvertisementController;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class BaseActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private DotsIndicator dotsIndicator;
    private AdvertisementAdapter advertisementAdapter;
    private List<Advertisement> advertisementList = new ArrayList<>();
    private AdvertisementController advertisementController;
    private List<Category> categoryList = new ArrayList<>();
    private CategoryController categoryController;
    private ProductAdapter productAdapter;
    private List<Product> productList = new ArrayList<>();
    private ProductController productController;
    private TextView textViewUserName;
    private HorizontalScrollView horizontalscrollviewCategoryHome;
    private RecyclerView recyclerViewProductsHome;

    private LinearLayout imageContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        // Layout cho activity của bạn

        // Khởi tạo các view
        viewPager2 = findViewById(R.id.viewPager2);
        dotsIndicator = findViewById(R.id.dotIndicator);
        recyclerViewProductsHome = findViewById(R.id.recyclerViewProductsHome);
        horizontalscrollviewCategoryHome = findViewById(R.id.viewPagerCategories);

        textViewUserName = findViewById(R.id.textView5);

        // Khởi tạo controllers
        advertisementController = new AdvertisementController();
        categoryController = new CategoryController();

        // Lấy dữ liệu quảng cáo từ Firebase
        loadAdvertisements();

        // Lấy dữ liệu loại sản phẩm từ Firebase
        loadCategories();

        // Lấy dữ liệu sản phẩm từ Firebase
        loadProducts();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Lấy UID của người dùng hiện tại
            String userId = user.getUid();

            // Truy vấn Firestore để lấy tên người dùng
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(userId)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                // Lấy tên từ Firestore
                                String name = documentSnapshot.getString("name");
                                if (name != null && !name.isEmpty()) {
                                    // Hiển thị tên lên TextView
                                    textViewUserName.setText(name);
                                } else {
                                    // Trường name không có trong tài liệu
                                    textViewUserName.setText("Tên không có sẵn");
                                }
                            } else {
                                // Tài liệu không tồn tại
                                textViewUserName.setText("Không tìm thấy thông tin người dùng");
                            }
                        }
                    })
                    .addOnFailureListener(e -> {
                        // Xử lý lỗi nếu không lấy được dữ liệu
                        textViewUserName.setText("Lỗi: " + e.getMessage());
                    });
        } else {
            // Người dùng chưa đăng nhập
            textViewUserName.setText("User not logged in");
        }
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

                    // Sử dụng GridLayoutManager với 2 cột
                    productAdapter = new ProductAdapter(productList);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(BaseActivity.this, 2); // 2 cột
                    recyclerViewProductsHome.setLayoutManager(gridLayoutManager);
                    recyclerViewProductsHome.setAdapter(productAdapter);
                } else {
                    Toast.makeText(BaseActivity.this, "Không có sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}