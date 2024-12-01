package com.example.cake.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;

import com.example.cake.Model.Product;
import com.example.cake.R;

import java.util.List;

public class SuggestionListAdapter extends ArrayAdapter<Product> {
    private final Context context;

    public SuggestionListAdapter(Context context, List<Product> suggestions) {
        super(context, R.layout.suggestion_item, suggestions);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.suggestion_item, parent, false);
        }

        Product product = getItem(position);
        TextView suggestionTextView = convertView.findViewById(R.id.suggestionTextView);
        if (product != null) {
            suggestionTextView.setText(product.getName());
        }

        return convertView;
    }
    public interface OnSuggestionClickListener {
        void onSuggestionClick(Product product);
    }
}