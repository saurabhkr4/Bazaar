package com.example.capture.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.capture.R;
import com.example.capture.databinding.ActivityProductDetailsBinding;
import com.example.capture.model.ProductDetail;
import com.example.capture.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ProductDetails extends AppCompatActivity {
    int k = 1;  //For onclickListener of heartwishList image
    ActivityProductDetailsBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ArrayList<Integer> productArrayforID;

    String name;
    double price;
    int id;
    String image;
    int quantity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        name = getIntent().getStringExtra("name");
        price = getIntent().getDoubleExtra("price",0.0);
        id = getIntent().getIntExtra("id",0);
        image = getIntent().getStringExtra("image");

        Glide.with(this).load(image).into(binding.productImage);
        binding.productName.setText(name);
        binding.aboutProduct.setText("₹"+ price);

        binding.wishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(k == 1) {

                    binding.wishList.setImageResource(R.drawable.heart_red);
                    k =2;
                }else{
                    binding.wishList.setImageResource(R.drawable.heart);
                    k = 1;

                }
            }
        });

        //For got to home button in the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        binding.AddtoCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductDetail product = new ProductDetail(id,name, price);
                // product.setQuantity(Integer.valueOf(binding.quantity.getText().toString()));
                binding.AddtoCartButton.setText("Added to Cart");
                binding.AddtoCartButton.setEnabled(false);
//                productArrayforID.add(id);
//                User.cartList.add(id);

//                List productIdList = new ArrayList<>(Arrays.asList(productArrayforID));
//                HashMap<Integer, Integer> map = new HashMap<>();
//                map.put(id,id);

//                Collections.addAll(productIdList, productArrayforID);


                database.getReference().child("carts")
                        .child(auth.getUid())
                        .child(id+"")
                        .setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){






                                    Intent intent = new Intent(ProductDetails.this, cartActivity.class);
//                                    intent.putExtra("quantity", quantity);
//This                                    //Toast.makeText(ProductDetails.this, "Id is"+ id , Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                }
//
                            }
                        });



            }
        });


    }

    //For got to home button in the action bar
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.cartMenu){
            Intent intent = new Intent(this, cartActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }




}