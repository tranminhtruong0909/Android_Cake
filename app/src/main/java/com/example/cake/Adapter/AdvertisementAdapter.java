package com.example.cake.Adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cake.Model.Advertisement;
import com.example.cake.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class AdvertisementAdapter extends RecyclerView.Adapter<AdvertisementAdapter.ViewHolder> {
    private List<Advertisement> advertisementList;

    public AdvertisementAdapter(List<Advertisement> advertisementList) {
        this.advertisementList = advertisementList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_advertisement, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Advertisement advertisement = advertisementList.get(position);


        // Lấy đường dẫn ảnh từ Firebase Storage
        String imageUrl = advertisement.getImageUrl();
        // Sử dụng Glide để tải ảnh từ URL vào ImageView
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .into(holder.adImageView); // Đảm bảo `imageView` là tên `ImageView` trong `ViewHolder`
    }


    @Override
    public int getItemCount() {
        return advertisementList.size();
    }

    // ViewHolder để lưu các view của mỗi item trong RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView adImageView; // ImageView để hiển thị ảnh quảng cáo

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Khởi tạo các view trong layout item

            adImageView = itemView.findViewById(R.id.adImageView);  // Khởi tạo ImageView
        }
    }
}
