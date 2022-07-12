package com.example.capture.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capture.R;
import com.example.capture.activities.ProductDetails;
import com.example.capture.databinding.ProductCardBinding;
import com.example.capture.model.ProductDetail;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    Context context;
    ArrayList <ProductDetail> productArrayList;

    public ProductAdapter(Context context, ArrayList<ProductDetail> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        ProductCardBinding binding;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ProductCardBinding.bind(itemView);
        }
    }



    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(context).inflate(R.layout.product_card,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductDetail productDetail = productArrayList.get(position);
        holder.binding.productDetailonCard.setText(productDetail.getName());

        Glide.with(context).load(productDetail.getImage()).into(holder.binding.productImageonCard);

        holder.binding.productPriceonCard.setText("₹"+productDetail.getPrice());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetails.class);
                intent.putExtra("name",productDetail.getName());
                intent.putExtra("price",productDetail.getPrice());
                intent.putExtra("id",productDetail.getId());
                intent.putExtra("image",productDetail.getImage());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }






}
