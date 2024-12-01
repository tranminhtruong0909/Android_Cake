package com.example.cake.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cake.Activity.DetailProductActivity;
import com.example.cake.Model.Product;
import com.example.cake.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);



        // Gán dữ liệu sản phẩm vào ViewHolder
        holder.nameTextView.setText(product.getName());
        holder.priceTextView.setText(String.format("$%.2f", product.getPrice()));

        // Sử dụng Glide để hiển thị ảnh sản phẩm
        Glide.with(holder.itemView.getContext())
                .load(product.getImageUrl())
                .into(holder.imageView);

        // Thêm sự kiện nhấn vào item
        holder.itemView.setOnClickListener(v -> {
            // Khi nhấn vào item, chuyển đến Activity khác và truyền ID sản phẩm
            Intent intent = new Intent(holder.itemView.getContext(), DetailProductActivity.class);
            intent.putExtra("productId", product.getId()); // Truyền ID sản phẩm
            holder.itemView.getContext().startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, priceTextView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.productNameTextView);
            priceTextView = itemView.findViewById(R.id.productPriceTextView);
            imageView = itemView.findViewById(R.id.productImageView);
        }
    }

    public void updateProductList(List<Product> newList) {
        if (newList != null) { // Kiểm tra newList không null
            productList.clear(); // Xóa danh sách cũ
            productList.addAll(newList); // Thêm sản phẩm mới vào danh sách
            notifyDataSetChanged(); // Cập nhật giao diện
        } else {
            Log.e("ProductAdapter", "Cannot update to a null list");
        }
    }
}