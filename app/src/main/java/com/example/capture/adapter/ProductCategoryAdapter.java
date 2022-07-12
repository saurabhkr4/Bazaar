package com.example.capture.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capture.R;
import com.example.capture.databinding.ProductCategoryBinding;
import com.example.capture.model.ProductCategory;

import java.util.ArrayList;

public class ProductCategoryAdapter extends RecyclerView.Adapter<ProductCategoryAdapter.ViewHolder>{

    Context context;
    ArrayList<ProductCategory> productCategoryArrayList;

    public ProductCategoryAdapter(Context context, ArrayList<ProductCategory> productCategoryArrayList) {
        this.context = context;
        this.productCategoryArrayList = productCategoryArrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ProductCategoryBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ProductCategoryBinding.bind(itemView);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.product_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductCategory productCategory = productCategoryArrayList.get(position);
        holder.binding.categoryName.setText(productCategory.getName());

        //To load image for Product Category

        Glide.with(context).load(productCategory.getIcon()).into(holder.binding.categoryImage);

        //To change color

        holder.binding.categoryImage.setBackgroundColor(Color.parseColor(productCategory.getColor()));

    }

    @Override
    public int getItemCount() {
        return productCategoryArrayList.size();
    }
}
