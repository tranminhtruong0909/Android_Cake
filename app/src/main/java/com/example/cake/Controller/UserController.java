package com.example.cake.Controller;


import com.google.firebase.firestore.FirebaseFirestore;
import com.example.cake.Model.User;
import java.util.List;

public class UserController {
    private FirebaseFirestore db;

    public UserController() {
        db = FirebaseFirestore.getInstance();
    }

    public void addUser(User user, OnUserCallback callback) {
        db.collection("users").document(user.getUidd())
                .set(user)
                .addOnSuccessListener(aVoid -> callback.onSuccess("User added successfully"))
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

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

    public interface OnUserCallback {
        void onSuccess(String message);
        void onFailure(String error);
    }

    public interface OnUsersCallback {
        void onSuccess(List<User> users);
        void onFailure(String error);
    }
}

