package com.example.cake.Controller;

import com.example.cake.Model.Advertisement;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class AdvertisementController {

    private FirebaseFirestore db;
    private CollectionReference advertisementRef;

    public AdvertisementController() {
        // Khởi tạo Firestore
        db = FirebaseFirestore.getInstance();
        advertisementRef = db.collection("advertisements"); // Tên collection
    }

    // Lấy danh sách quảng cáo
    public void getAdvertisements(OnAdvertisementsLoadedListener listener) {
        advertisementRef.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Advertisement> advertisements = queryDocumentSnapshots.toObjects(Advertisement.class);
                    listener.onAdvertisementsLoaded(advertisements);
                })
                .addOnFailureListener(e -> {
                    listener.onAdvertisementsLoaded(null); // Trả về null nếu có lỗi
                });
    }

    // Interface để xử lý callback khi lấy dữ liệu
    public interface OnAdvertisementsLoadedListener {
        void onAdvertisementsLoaded(List<Advertisement> advertisements);
    }
}
