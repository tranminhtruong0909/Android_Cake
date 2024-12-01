package com.example.cake.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cake.Model.Product;
import com.example.cake.R;

import java.util.List;

public class ProductAdminAdapter extends RecyclerView.Adapter<ProductAdminAdapter.ProductViewHolder> {

    private List<Product> productList;
    private OnItemClickListener listener;

    // Interface để lắng nghe sự kiện click
    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    // Phương thức set listener cho click item
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // Constructor để truyền vào danh sách sản phẩm
    public ProductAdminAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Tạo View từ XML layout item_product_admin
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_admin, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        // Lấy sản phẩm từ danh sách
        Product product = productList.get(position);

        // Gán dữ liệu vào các View
        holder.productTitle.setText(product.getName());
        holder.productPrice.setText(String.format("$%.2f", product.getPrice()));

        // Tải hình ảnh sản phẩm sử dụng Glide
        Glide.with(holder.productImage.getContext())
                .load(product.getImageUrl()) // URL hình ảnh
                .placeholder(R.drawable.placeholder_product) // Hình mặc định khi đang tải
                .into(holder.productImage);

        holder.itemView.setOnClickListener(view -> {
            if (listener != null && position != RecyclerView.NO_POSITION) {
                // Truyền thông tin sản phẩm qua Intent
                listener.onItemClick(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size(); // Trả về số lượng sản phẩm trong danh sách
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productTitle, productPrice;
        ImageView productImage;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ các View từ XML
            productTitle = itemView.findViewById(R.id.productTitle);
            productPrice = itemView.findViewById(R.id.productPrice);
            productImage = itemView.findViewById(R.id.productImage);
        }
    }
}
