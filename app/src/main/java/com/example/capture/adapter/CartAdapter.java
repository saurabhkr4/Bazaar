package com.example.capture.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capture.R;
import com.example.capture.databinding.CartItemBinding;
import com.example.capture.model.ProductDetail;
import com.example.capture.utility.Quantity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    Context context;
    ArrayList<ProductDetail> products;
    FirebaseDatabase database;
    FirebaseAuth auth;
    ProgressDialog dialog;


    public CartAdapter(Context context, ArrayList<ProductDetail> products) {
        this.context = context;
        this.products = products;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        CartItemBinding binding;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CartItemBinding.bind(itemView);

        }
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_item ,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        ProductDetail product = products.get(position);
        holder.binding.productName.setText(product.getName());

        Glide.with(context)
                .load(product.getImage())
                .into(holder.binding.productImage);

//        holder.binding.quantity.setText(product.quantity+"");

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        database.getReference().child("carts").child(auth.getUid()).child(product.getId()+"").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //String quantity1 =  snapshot.child("quantity").getValue(String.class);
//                if(quantity1!=null) {
//                    holder.binding.quantity.setText(quantity1);
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




//        holder.binding.quantity.setText(database.getReference().child("carts").child(product.getId()+"").child(q);
        holder.binding.price.setText(""+product.getPrice());


        holder.binding.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.valueOf(holder.binding.quantity.getText().toString());
                quantity = quantity +1;
                //product.quantity = quantity;
                holder.binding.quantity.setText(quantity+"");
                //  products.clear();
                updateQuantityinFirebase(product.getId(), quantity);

            }
        });

        holder.binding.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.valueOf(holder.binding.quantity.getText().toString());
                if(quantity!=1){
                    quantity = quantity-1;
                }
                //product.quantity = quantity;
                holder.binding.quantity.setText(quantity+"");
                //  products.clear();
                updateQuantityinFirebase(product.getId(), quantity);
            }
        });











//        holder.binding.plusButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int quantity = Integer.valueOf(holder.binding.quantity.getText().toString());
//
//                double unitprice = product.getPrice();
//                quantity  = quantity+1;
//                double cost = unitprice * (quantity);
//                holder.binding.quantity.setText(quantity+"");
//                holder.binding.price.setText(""+cost);
//                updateCostinFirebase(product.getId(),quantity);
//                //updateSubtotal(holder.itemView.getId());
//
//
//
//            }
//        });
//
//        holder.binding.minusButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int quantity = Integer.valueOf(holder.binding.quantity.getText().toString());
//
//                double unitprice = product.getPrice();
//                if(quantity!=1){
//                    quantity  = quantity-1;
//                }
//
//                double cost = unitprice * (quantity);
//                holder.binding.quantity.setText(quantity+"");
//                holder.binding.price.setText(cost+"");
//                updateCostinFirebase(product.getId(),quantity);
//
//            }
//        });




    }


    @Override
    public int getItemCount() {
        return products.size();
    }


    private void updateCostinFirebase(int id, int quantity) {
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
//        Quantity quantity1 = new Quantity(quantity);
        database.getReference().child("carts").child(auth.getUid()).child(id+"").child("stock").setValue(quantity);


    }

    private void updateQuantityinFirebase(int id, int quantity) {
        dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait");
        dialog.setCancelable(false);
        dialog.show();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
//        Quantity quantity1 = new Quantity(quantity);
        database.getReference().child("carts").child(auth.getUid()).child(id+"").child("quantity").setValue(quantity);
        dialog.dismiss();

    }


}
