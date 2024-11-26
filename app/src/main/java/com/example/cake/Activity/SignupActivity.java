package com.example.cake.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cake.Controller.UserController;
import com.example.cake.Model.User;
import com.example.cake.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    private TextInputEditText name, address, phoneNumber, age, email, password, confirmPassword;
    private Button createAccountButton;

    private UserController userController;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Ánh xạ các thành phần giao diện
        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        phoneNumber = findViewById(R.id.phone_number);
        age = findViewById(R.id.age);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm_password);
        createAccountButton = findViewById(R.id.create_account);

        userController = new UserController();
        mAuth = FirebaseAuth.getInstance();

        // Xử lý nút đăng ký
        createAccountButton.setOnClickListener(v -> {
            String nameText = name.getText().toString().trim();
            String addressText = address.getText().toString().trim();
            String phoneNumberText = phoneNumber.getText().toString().trim();
            String ageText = age.getText().toString().trim();
            String emailText = email.getText().toString().trim();
            String passwordText = password.getText().toString().trim();
            String confirmPasswordText = confirmPassword.getText().toString().trim();

            // Kiểm tra mật khẩu trùng khớp
            if (!passwordText.equals(confirmPasswordText)) {
                Toast.makeText(SignupActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Chuyển đổi age từ chuỗi sang số nguyên
            int ageNumber;
            try {
                ageNumber = Integer.parseInt(ageText);
            } catch (NumberFormatException e) {
                Toast.makeText(SignupActivity.this, "Invalid age!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Đăng ký người dùng với Firebase Authentication
            mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            if (firebaseUser != null) {
                                // Lấy UID của người dùng từ Firebase Authentication
                                String uid = firebaseUser.getUid();

                                // Tạo đối tượng User với role là "user"
                                User user = new User(
                                        uid, // Lấy UID từ Firebase Authentication
                                        nameText,
                                        addressText,
                                        phoneNumberText,
                                        ageNumber,
                                        emailText,
                                        "user" // Mặc định là user
                                );

                                // Gọi UserController để lưu User vào Firestore
                                userController.addUser(user, new UserController.OnUserCallback() {
                                    @Override
                                    public void onSuccess(String message) {
                                        Toast.makeText(SignupActivity.this, message, Toast.LENGTH_SHORT).show();
                                        finish(); // Đóng màn hình đăng ký
                                    }

                                    @Override
                                    public void onFailure(String error) {
                                        Toast.makeText(SignupActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } else {
                            Toast.makeText(SignupActivity.this, "Authentication failed: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
