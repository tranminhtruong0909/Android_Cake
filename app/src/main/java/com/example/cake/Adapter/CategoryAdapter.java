package com.example.cake.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cake.Activity.CategoryDetail;
import com.example.cake.Model.Category;
import com.example.cake.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private Context context;
    private List<Category> categoryList;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    // Phương thức này sẽ tạo ViewHolder mới cho mỗi item trong RecyclerView
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout item_category
        View categoryView = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(categoryView);
    }

    // Phương thức này sẽ gắn dữ liệu vào ViewHolder cho từng item
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Lấy dữ liệu loại sản phẩm từ danh sách
        Category category = categoryList.get(position);

        // Gán tên loại sản phẩm vào TextView
        holder.nameTextView.setText(category.getName());

        // Hiển thị ảnh của loại sản phẩm sử dụng Glide
        Glide.with(holder.itemView.getContext())
                .load(category.getImageUrl()) // URL hình ảnh từ trường 'url' của Category
                .into(holder.imageView);

        // Thêm sự kiện nhấn vào item
        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(holder.itemView.getContext(), "Bạn đã ấn vào loại sản phẩm", Toast.LENGTH_SHORT).show();

            // Chuyển đến Activity hiển thị chi tiết loại sản phẩm
            Intent intent = new Intent(holder.itemView.getContext(), CategoryDetail.class);
            intent.putExtra("categoryName", category.getName());  // Truyền tên danh mục
            intent.putExtra("categoryImage", category.getImageUrl());  // Truyền URL hình ảnh
            intent.putExtra("categoryId", category.getId());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    // Phương thức này trả về số lượng phần tử trong danh sách
    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    // ViewHolder để nắm giữ các View của mỗi item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.categoryImageView);
            nameTextView = itemView.findViewById(R.id.categoryTitle);
        }
    }
    public void addCategoriesToLayout(LinearLayout linearLayout) {
        linearLayout.removeAllViews(); // Xóa các View cũ nếu có

        for (Category category : categoryList) {
            // Inflate layout item_category
            View categoryView = LayoutInflater.from(context).inflate(R.layout.item_category, linearLayout, false);

            // Set name and image
            TextView categoryNameTextView = categoryView.findViewById(R.id.categoryNameTextView);
            categoryNameTextView.setText(category.getName());

            ImageView categoryImageView = categoryView.findViewById(R.id.categoryImageView);
            Glide.with(context)
                    .load(category.getImageUrl())
                    .into(categoryImageView);

            // Thêm sự kiện click vào mỗi item
            categoryView.setOnClickListener(v -> {
                Toast.makeText(context, "Bạn đã ấn vào loại sản phẩm", Toast.LENGTH_SHORT).show();

                // Chuyển đến Activity chi tiết loại sản phẩm
                Intent intent = new Intent(context, CategoryDetail.class);
                intent.putExtra("categoryName", category.getName());
                intent.putExtra("categoryImage", category.getImageUrl());
                context.startActivity(intent);
            });

            // Thêm view vào LinearLayout
            linearLayout.addView(categoryView);
        }
    }

}


