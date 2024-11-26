package com.example.cake.Controller;

import com.google.firebase.firestore.FirebaseFirestore;
import com.example.cake.Model.User;
import java.util.List;

public class UserController {
    private FirebaseFirestore db;

    public UserController() {
        db = FirebaseFirestore.getInstance();
    }

    // Thêm người dùng
    public void addUser(User user, OnUserCallback callback) {
        db.collection("users").document(user.getUidd())
                .set(user)
                .addOnSuccessListener(aVoid -> callback.onSuccess("User added successfully"))
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    // Lấy danh sách người dùng
    public void getUsers(OnUsersCallback callback) {
        db.collection("users").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<User> userList = task.getResult().toObjects(User.class);
                        callback.onSuccess(userList);
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }

    // Cập nhật thông tin người dùng
    public void updateUser(User user, OnUserCallback callback) {
        db.collection("users").document(user.getUidd())
                .update(
                        "name", user.getName(),
                        "address", user.getAddress(),
                        "phoneNumber", user.getPhoneNumber(),
                        "age", user.getAge(),
                        "email", user.getEmail(),
                        "role", user.getRole()
                )
                .addOnSuccessListener(aVoid -> callback.onSuccess("User updated successfully"))
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    // Callback cho kết quả thao tác với người dùng
    public interface OnUserCallback {
        void onSuccess(String message);
        void onFailure(String error);
    }

    // Callback cho lấy danh sách người dùng
    public interface OnUsersCallback {
        void onSuccess(List<User> users);
        void onFailure(String error);
    }
}
