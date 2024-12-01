package com.example.cake.Activity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cake.Adapter.AdvertisementAdapter;
import com.example.cake.Adapter.CategoryAdapter;
import com.example.cake.Adapter.ProductAdapter;

import com.example.cake.Adapter.SuggestionListAdapter;
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


public class BaseActivity extends AppCompatActivity implements SuggestionListAdapter.OnSuggestionClickListener{

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
    private LinearLayout profile;
    private LinearLayout explorer;
    private LinearLayout cartTitle;
    private EditText editSearch;
    private LinearLayout imageContainer;
    private List<Product> suggestionList;
    private String userId;
    private ListView listViewSuggestions; // Thay thế RecyclerView bằng ListView
    private ArrayAdapter<String> suggestionAdapter; // Adapter cho ListView

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        // Layout cho activity của bạn

        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(productList);
        productList = new ArrayList<>();
        // Khởi tạo các view
        viewPager2 = findViewById(R.id.viewPager2);
        dotsIndicator = findViewById(R.id.dotIndicator);
        recyclerViewProductsHome = findViewById(R.id.recyclerViewProductsHome);
        horizontalscrollviewCategoryHome = findViewById(R.id.viewPagerCategories);

        explorer = findViewById(R.id.explorer);
        textViewUserName = findViewById(R.id.textView5);
        editSearch = findViewById(R.id.editSearch);
        profile = findViewById(R.id.profile);
        listViewSuggestions = findViewById(R.id.listViewSuggestions);
        cartTitle = findViewById(R.id.cartTitle);

        suggestionList = new ArrayList<>();
        suggestionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>()); // Khởi tạo với ArrayList rỗng
        listViewSuggestions.setAdapter(suggestionAdapter);

        advertisementController = new AdvertisementController();
        categoryController = new CategoryController();

        productList = new ArrayList<>();

        // Khởi tạo controllers
        advertisementController = new AdvertisementController();
        categoryController = new CategoryController();



        // Lấy dữ liệu từ Firebase
        loadUserData();
        loadAdvertisements();
        loadCategories();
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

        if (userId != null) {
            // Khi có userId, bạn có thể sử dụng nó để mở DetailProductActivity
            Intent intent = new Intent(BaseActivity.this, DetailProductActivity.class);
            intent.putExtra("userId", userId); // Truyền userId sang DetailProductActivity
            startActivity(intent); // Khởi động DetailProductActivity
        } else {
            // Xử lý khi không có userId (ví dụ: thông báo lỗi hoặc thực hiện hành động khác)
            Log.e("BaseActivity", "User ID không tồn tại");
        }

        // Thêm OnClickListener cho textViewUserName
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở UserInfoActivity khi người dùng nhấn vào TextView
                Intent intent = new Intent(BaseActivity.this, UserInfoActivity.class);
                startActivity(intent);
            }
        });

        // Thêm OnClickListener cho textViewUserName
        cartTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở UserInfoActivity khi người dùng nhấn vào TextView
                Intent intent = new Intent(BaseActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        // Thêm OnClickListener cho textViewUserName
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở UserInfoActivity khi người dùng nhấn vào TextView
                Intent intent = new Intent(BaseActivity.this, UserInfoActivity.class);
                startActivity(intent);
            }
        });

        // Thêm OnClickListener cho textViewUserName
        explorer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở UserInfoActivity khi người dùng nhấn vào TextView
                Intent intent = new Intent(BaseActivity.this, AboutShopActivity.class);
                startActivity(intent);
            }
        });


        // Thêm TextWatcher cho ô tìm kiếm
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProducts(s.toString());
                updateSuggestions(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
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
    private void filterProducts(String query) {
        List<Product> filteredList = new ArrayList<>();
        for (Product product : productList) {
            if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(product);
            }
        }
        productAdapter.updateProductList(filteredList); // Assuming you have a method to update your product list
    }

    private void loadUserData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(userId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getString("name");
                            textViewUserName.setText(name != null && !name.isEmpty() ? name : "Tên không có sẵn");
                        } else {
                            textViewUserName.setText("Không tìm thấy thông tin người dùng");
                        }
                    })
                    .addOnFailureListener(e -> textViewUserName.setText("Lỗi: " + e.getMessage()));
        } else {
            textViewUserName.setText("User not logged in");
        }
    }
    private void updateSuggestions(String query) {
        if (query.isEmpty()) {
            suggestionList.clear();
            suggestionAdapter.clear(); // Xóa gợi ý trong adapter
            suggestionAdapter.notifyDataSetChanged(); // Cập nhật ListView
            listViewSuggestions.setVisibility(View.GONE);
            return;
        }

        // Danh sách sản phẩm gợi ý dựa trên truy vấn
        suggestionList.clear();
        for (Product product : productList) {
            if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                suggestionList.add(product); // Thêm sản phẩm vào gợi ý
            }
        }

        // Chuyển đổi danh sách sản phẩm thành danh sách tên
        List<String> suggestionNames = new ArrayList<>();
        for (Product suggestion : suggestionList) {
            suggestionNames.add(suggestion.getName()); // Chỉ thêm tên
        }

        // Cập nhật adapter với list tên sản phẩm
        suggestionAdapter.clear();
        suggestionAdapter.addAll(suggestionNames); // Cập nhật danh sách gợi ý
        suggestionAdapter.notifyDataSetChanged(); // Cập nhật ListView

        // Kiểm tra xem có gợi ý nào không
        listViewSuggestions.setVisibility(!suggestionList.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onSuggestionClick(Product product) {
        // Xử lý khi người dùng nhấn vào gợi ý sản phẩm
        Intent intent = new Intent(this, DetailProductActivity.class);
        intent.putExtra("productId", product.getId());
        intent.putExtra("userId", userId);// Giả sử có phương thức getId() trong lớp Product
        startActivity(intent);
    }
}