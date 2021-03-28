package com.example.madmini;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.madmini.Model.Cart;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import ViewHolder.CartViewHolder;

public class AdminUserProductActivity extends AppCompatActivity {
    private RecyclerView productslist;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference productsRef;
    private String UserId="";
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_product);

        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


        UserId= getIntent().getStringExtra("uid");

        productslist = findViewById(R.id.producust_order_list);
        productslist.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        productslist.setLayoutManager(layoutManager);

        productsRef = FirebaseDatabase.getInstance().getReference().child("Cart List").child("Cartlist Admin View").child(UserId).child("products");
    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(productsRef,Cart.class).build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter= new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull Cart cart) {

                cartViewHolder.txtProductQuentity.setText("Quantity ="+cart.getQuantity());
                cartViewHolder.txtProductPrice.setText("Price ="+cart.getPrice()+"Rs");
                cartViewHolder.txtProductName.setText(cart.getPname());
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };

        productslist .setAdapter(adapter);
        adapter.startListening();
    }
}