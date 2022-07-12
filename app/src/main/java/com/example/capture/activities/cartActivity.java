package com.example.capture.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.capture.R;
import com.example.capture.adapter.CartAdapter;
import com.example.capture.databinding.ActivityCartBinding;
import com.example.capture.model.ProductDetail;
import com.example.capture.model.User;
import com.example.capture.utility.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class cartActivity extends AppCompatActivity {


    CartAdapter cartAdapter;
    ArrayList<ProductDetail> products;
    ActivityCartBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    ProgressDialog dialog;
//    int subtotal = 0;
    int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("My Cart");

        auth = FirebaseAuth.getInstance();
//        database = FirebaseDatabase.getInstance();
//        int id;
//        id = getIntent().getIntExtra("id", 0);
        database = FirebaseDatabase.getInstance();
        products = new ArrayList<>();
        cartAdapter = new CartAdapter(this, products);
        dialog = new ProgressDialog(cartActivity.this);
        dialog.setTitle("Please Wait");
        dialog.setMessage("Preparing your bill");
        dialog.setCancelable(false);

        total = 0;


        binding.continueButton.setOnClickListener(new View.OnClickListener() {





            @Override
            public void onClick(View v) {

                dialog.show();
                database.getReference().child("carts").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        total = 0;
                        for(DataSnapshot snapshot1 : snapshot.getChildren()){
//                            Toast.makeText(cartActivity.this, "Here", Toast.LENGTH_SHORT).show();
                            int a =  snapshot1.child("price").getValue(Integer.class)* snapshot1.child("quantity").getValue(Integer.class);
                            total = total + a;
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                database.getReference().child("total").child(auth.getUid()).child("total").setValue(total).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                                        dialog.dismiss();
                                        Intent intent = new Intent(cartActivity.this,ProceedtoPay.class);
                                        startActivity(intent);
                        }
                    }
                });

            }

        });

//        products.add()

//        products.add(new ProductDetail("Apple iPhone 16 Pro (128 GB) - Alpine Green", "https://www.reliancedigital.in/medias/Apple-MLPF3HN-A-Smart-Phones-491997699-i-3-1200Wx1200H?context=bWFzdGVyfGltYWdlc3wxNTM3NTF8aW1hZ2UvanBlZ3xpbWFnZXMvaGJjL2gyYS85Nzc2MDM5MTAwNDQ2LmpwZ3wxMTQ1ODYwOWU4OTk3ZmM4Y2ZjYzQ4MWUwNTIyMTU2NzVkYTdlOWE4ZWMxN2RiYzc1MTJmNzZkNzRhYzhlZDgx",
//                "In Stock", 100000, 5, 2000,1110));
//        products.add(new ProductDetail("Apple iPhone 16 Pro (128 GB) - Alpine Green", "https://www.reliancedigital.in/medias/Apple-MLPF3HN-A-Smart-Phones-491997699-i-3-1200Wx1200H?context=bWFzdGVyfGltYWdlc3wxNTM3NTF8aW1hZ2UvanBlZ3xpbWFnZXMvaGJjL2gyYS85Nzc2MDM5MTAwNDQ2LmpwZ3wxMTQ1ODYwOWU4OTk3ZmM4Y2ZjYzQ4MWUwNTIyMTU2NzVkYTdlOWE4ZWMxN2RiYzc1MTJmNzZkNzRhYzhlZDgx",
//                "In Stock", 100000, 5, 2000,1110));
//        products.add(new ProductDetail("Apple iPhone 16 Pro (128 GB) - Alpine Green", "https://www.reliancedigital.in/medias/Apple-MLPF3HN-A-Smart-Phones-491997699-i-3-1200Wx1200H?context=bWFzdGVyfGltYWdlc3wxNTM3NTF8aW1hZ2UvanBlZ3xpbWFnZXMvaGJjL2gyYS85Nzc2MDM5MTAwNDQ2LmpwZ3wxMTQ1ODYwOWU4OTk3ZmM4Y2ZjYzQ4MWUwNTIyMTU2NzVkYTdlOWE4ZWMxN2RiYzc1MTJmNzZkNzRhYzhlZDgx",
//                "In Stock", 100000, 5, 2000,1110));
//        products.add(new ProductDetail("Apple iPhone 16 Pro (128 GB) - Alpine Green", "https://www.reliancedigital.in/medias/Apple-MLPF3HN-A-Smart-Phones-491997699-i-3-1200Wx1200H?context=bWFzdGVyfGltYWdlc3wxNTM3NTF8aW1hZ2UvanBlZ3xpbWFnZXMvaGJjL2gyYS85Nzc2MDM5MTAwNDQ2LmpwZ3wxMTQ1ODYwOWU4OTk3ZmM4Y2ZjYzQ4MWUwNTIyMTU2NzVkYTdlOWE4ZWMxN2RiYzc1MTJmNzZkNzRhYzhlZDgx",
//                "In Stock", 100000, 5, 2000,1110));
//        for (int i : User.cartList){
//            getProducts(216);
//            getProducts(213);
//            getProducts(210);
//        }
//        Toast.makeText(this, User.cartList.toString(), Toast.LENGTH_SHORT).show();
        database.getReference().child("carts").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                getProducts(snapshot.getValue(Integer.class));
                products.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
 //This                 //  Toast.makeText(cartActivity.this, "Here", Toast.LENGTH_SHORT).show();
                    int k =  snapshot1.child("id").getValue(Integer.class);

                    getProducts(k);
//                    subtotal = snapshot1.child("price").getValue(Integer.class)*snapshot1.child("quantity").getValue(Integer.class) ;
//                    binding.price.setText("₹"+ subtotal);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        binding.subtotalButtton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                database.getReference().child("carts").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
////                getProducts(snapshot.getValue(Integer.class));
//                        products.clear();
//                        for(DataSnapshot snapshot1 : snapshot.getChildren()){
////                            Toast.makeText(cartActivity.this, "Here", Toast.LENGTH_SHORT).show();
//                            int k =  snapshot1.child("id").getValue(Integer.class);
//
//
////                    subtotal = snapshot1.child("price").getValue(Integer.class)*snapshot1.child("quantity").getValue(Integer.class) ;
////                    binding.price.setText("₹"+ subtotal);
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//        });



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.cartRecylerView.setLayoutManager(linearLayoutManager);
        binding.cartRecylerView.setAdapter(cartAdapter);



    }






    void getProducts(int id){

//        database.getReference().child("carts").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });




        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constants.GET_PRODUCT_BY_ID + id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mainObject = new JSONObject(response);

                    if(mainObject.getString("status").equals("success")) {
  //This                  //    Toast.makeText(cartActivity.this, "Value Fetched", Toast.LENGTH_SHORT).show();
                        JSONObject obj = mainObject.getJSONObject("product");

//                        for(int i = 0; i<productArrayfromInternet.length()-1; i++){
//                            Log.e("eee", response);
//                            JSONObject obj = productArrayfromInternet.getJSONObject(i);
                            ProductDetail productDetail = new ProductDetail(obj.getString("name"), Constants.PRODUCTS_IMAGE_URL+ obj.getString("image"),
                                    obj.getString("status"), obj.getDouble("price"),obj.getDouble("price_discount"),
                                    obj.getInt("stock"), obj.getInt("id"));

                            products.add(productDetail);
                            cartAdapter.notifyDataSetChanged();
//                        }



                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);
    }





}