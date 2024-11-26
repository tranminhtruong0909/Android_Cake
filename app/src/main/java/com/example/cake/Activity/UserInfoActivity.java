package com.example.cake.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cake.Controller.UserController;
import com.example.cake.Model.User;
import com.example.cake.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserInfoActivity extends AppCompatActivity {

    private EditText etUid, etName, etAddress, etPhoneNumber, etAge, etEmail, etRole;
    private Button btnEditSave;
    private boolean isEditing = false;
    private User currentUser;
    private ImageView btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        // Ánh xạ các View
        etUid = findViewById(R.id.etUid);
        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etAge = findViewById(R.id.etAge);
        etEmail = findViewById(R.id.etEmail);
        etRole = findViewById(R.id.etRole);
        btnEditSave = findViewById(R.id.btnEditSave);
        btnBack = findViewById(R.id.btnBack);
        // Lấy thông tin người dùng và hiển thị
        fetchUserInfo();

        // Xử lý sự kiện cho nút chỉnh sửa/lưu
        btnEditSave.setOnClickListener(v -> {
            if (isEditing) {
                saveUserInfo(); // Lưu thông tin người dùng khi ở chế độ chỉnh sửa
            } else {
                enableEditing(); // Bật chế độ chỉnh sửa
            }
        });

        // Thiết lập sự kiện click
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang BaseActivity khi nhấn nút
                Intent intent = new Intent(UserInfoActivity.this, BaseActivity.class);
                startActivity(intent);
                finish();  // Để đóng UserInfoActivity nếu không cần quay lại
            }
        });
    }

    private void fetchUserInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String userId = user.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            db.collection("users").document(userId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            currentUser = documentSnapshot.toObject(User.class);

                            // Hiển thị thông tin người dùng lên các EditText
                            etUid.setText(currentUser != null ? currentUser.getUidd() : "");
                            etName.setText(currentUser != null ? currentUser.getName() : "");
                            etAddress.setText(currentUser != null ? currentUser.getAddress() : "");
                            etPhoneNumber.setText(currentUser != null ? currentUser.getPhoneNumber() : "");
                            etAge.setText(currentUser != null ? String.valueOf(currentUser.getAge()) : "");
                            etEmail.setText(currentUser != null ? currentUser.getEmail() : "");
                            etRole.setText(currentUser != null ? currentUser.getRole() : "");

                            // Tắt chỉnh sửa khi vừa tải dữ liệu
                            disableEditing();
                        } else {
                            etUid.setText("Không tìm thấy thông tin người dùng");
                        }
                    })
                    .addOnFailureListener(e -> {
                        etUid.setText("Lỗi: " + e.getMessage());
                    });
        } else {
            etUid.setText("User not logged in");
        }
    }

    private void enableEditing() {
        // Bật chế độ chỉnh sửa
        etName.setEnabled(true);
        etAddress.setEnabled(true);
        etPhoneNumber.setEnabled(true);
        etAge.setEnabled(true);
        etEmail.setEnabled(true);
        etRole.setEnabled(true);

        // Đổi nút "Chỉnh sửa" thành "Lưu"
        btnEditSave.setText("Lưu");

        // Đặt trạng thái chỉnh sửa thành true
        isEditing = true;
    }

    private void disableEditing() {
        // Tắt chế độ chỉnh sửa
        etName.setEnabled(false);
        etAddress.setEnabled(false);
        etPhoneNumber.setEnabled(false);
        etAge.setEnabled(false);
        etEmail.setEnabled(false);
        etRole.setEnabled(false);

        // Đổi nút "Lưu" thành "Chỉnh sửa"
        btnEditSave.setText("Chỉnh sửa");

        // Đặt trạng thái chỉnh sửa thành false
        isEditing = false;
    }

    private void saveUserInfo() {
        String name = etName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        String ageStr = etAge.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String role = etRole.getText().toString().trim();

        // Kiểm tra dữ liệu đầu vào
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(address) || TextUtils.isEmpty(phoneNumber)
                || TextUtils.isEmpty(ageStr) || TextUtils.isEmpty(email) || TextUtils.isEmpty(role)) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = Integer.parseInt(ageStr);

        // Cập nhật thông tin người dùng
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            User updatedUser = new User(userId, name, address, phoneNumber, age, email, role);

            UserController userController = new UserController();
            userController.updateUser(updatedUser, new UserController.OnUserCallback() {
                @Override
                public void onSuccess(String message) {
                    disableEditing();
                    Toast.makeText(UserInfoActivity.this, message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(String error) {
                    Toast.makeText(UserInfoActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
