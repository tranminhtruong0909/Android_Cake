package com.example.cake.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cake.Model.Category;
import com.example.cake.R;

import java.util.List;

public class CategoryAdapter {
    private Context context;
    private List<Category> categoryList;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    public void addCategoriesToLayout(LinearLayout linearLayout) {
        linearLayout.removeAllViews(); // Xóa các View cũ nếu có

        for (Category category : categoryList) {
            // Inflate layout item
            View categoryView = LayoutInflater.from(context).inflate(R.layout.item_category, linearLayout, false);

            // Set name and image
            TextView categoryNameTextView = categoryView.findViewById(R.id.categoryNameTextView);
            categoryNameTextView.setText(category.getName());

            ImageView categoryImageView = categoryView.findViewById(R.id.categoryImageView);
            Glide.with(context)
                    .load(category.getImageUrl())
                    .into(categoryImageView);

            // Add view to LinearLayout
            linearLayout.addView(categoryView);
        }
    }
}
