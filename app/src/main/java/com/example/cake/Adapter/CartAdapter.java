package com.example.cake.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.cake.Activity.CartActivity;
import com.example.cake.Model.CartItem;
import com.example.cake.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    private List<CartItem> cartItems;

    // Constructor
    public CartAdapter(Context context, List<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        holder.productName.setText(cartItem.getName());
        holder.productPrice.setText(String.format("%s VND", cartItem.getPrice()));
        holder.productQuantity.setText(String.valueOf(cartItem.getQuantity()));  // Hiển thị số lượng
        holder.productSize.setText(cartItem.getSize());  // Hiển thị size

        // Sử dụng Glide để tải hình ảnh sản phẩm từ URL
        Glide.with(context)
                .load(cartItem.getImageUrl())
                .into(holder.productImage);

        // Thiết lập sự kiện xóa cho deleteIcon
        holder.deleteIcon.setOnClickListener(v -> {
            // Xóa sản phẩm khỏi giỏ hàng
            deleteProductFromCart(cartItem);
        });
    }



    @Override
    public int getItemCount() {
        return cartItems.size();
    }


    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage, deleteIcon ;
        TextView productName, productPrice, productQuantity, productSize;  // Thêm productSize

        public CartViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productQuantity = itemView.findViewById(R.id.productQuantity);  // Khai báo productQuantity
            productSize = itemView.findViewById(R.id.productSize);  // Khai báo productSize
            deleteIcon = itemView.findViewById(R.id.deleteIcon);
        }
    }
    // Xóa sản phẩm khỏi giỏ hàng
    private void deleteProductFromCart(CartItem cartItem) {
        // Xóa sản phẩm khỏi Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("users")
                .document(userId)
                .collection("cart")
                .document(cartItem.getProductId())  // Giả sử `getProductId()` trả về id sản phẩm
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Sản phẩm đã được xóa khỏi giỏ hàng!", Toast.LENGTH_SHORT).show();
                    // Cập nhật lại danh sách giỏ hàng
                    cartItems.remove(cartItem);
                    notifyDataSetChanged();  // Hoặc sử dụng notifyItemRemoved(position)
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Lỗi khi xóa sản phẩm.", Toast.LENGTH_SHORT).show();
                });
    }

}
