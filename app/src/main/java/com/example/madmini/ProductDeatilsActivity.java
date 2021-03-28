package com.example.madmini;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.madmini.Model.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDeatilsActivity extends AppCompatActivity {

    private Button addtocartButton;
    private ImageView productimage;
    private ElegantNumberButton numberButton1;
    private TextView prodcutprice,productDescription,productName;

    private String userId;


    FirebaseFirestore FStore;
    FirebaseAuth FAuth;

    // create varibale for get specific product id
    private String productID= "";


    private String statedel="Normal";

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_deatils);
        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


        //Firebase needs------------------------------------------------------------------------------------------------------------
        FAuth = FirebaseAuth.getInstance();
        FStore = FirebaseFirestore.getInstance();

        // get pid from intent extras

        productID= getIntent().getStringExtra("pid");
        userId = FAuth.getCurrentUser().getUid();

        numberButton1=(ElegantNumberButton)findViewById(R.id.number_btn);
        productimage=(ImageView) findViewById(R.id.product_image_deatils);
        productName=(TextView) findViewById(R.id.product_name_details);
        productDescription=(TextView) findViewById(R.id.product_discriptin_details);
        prodcutprice=(TextView) findViewById(R.id.product_price_details);
        addtocartButton=(Button) findViewById(R.id.pd_add_to_cart_button);

        getProductDetails(productID);

        addtocartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addinToCartList();

                if(statedel.equals("order placed")|| statedel.equals("order delivered"))
                {
                    Toast.makeText(ProductDeatilsActivity.this,"you can add purchase more sproducts ones your order  is Delivered or confirmed ",Toast.LENGTH_LONG).show();
                }
                else
                {
                    addinToCartList();
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        CheckOrderState();
    }


    private void addinToCartList() {
        // create varibale to get current date and time
        String saveCurrentTime,saveCurrentDate;

        // get  current time when adding to cart list the selected item
        Calendar calForDate=Calendar.getInstance();
        // Get current data
        SimpleDateFormat currentDate= new SimpleDateFormat("MMM dd, yyyy");
        // store current date in variable
        saveCurrentDate=currentDate.format(calForDate.getTime());


        // Get current time
        SimpleDateFormat currentTime= new SimpleDateFormat("HH:mm:ss a");
        // store current time in variable
        saveCurrentTime=currentDate.format(calForDate.getTime());

        //store in to firebase batabase
        // Cart list is a saving child name in firebase (cart list= table name)
        final DatabaseReference cartListRef= FirebaseDatabase.getInstance().getReference().child("Cart List");

        // store data using HashMap'
        final HashMap<String,Object> cartMap= new HashMap<>();
        //column na to the firebase
        cartMap.put("Pid",productID);
        cartMap.put("pname",productName.getText().toString());
        cartMap.put("price",prodcutprice.getText().toString());
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrentTime);
        cartMap.put("quantity",numberButton1.getNumber());
        cartMap.put("discount","");

        //  cartListRef.child("User View").child("product").//
        cartListRef.child("Cartlist User View").child(userId).child("products").child(productID).updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                        if (task.isSuccessful()) {

                            cartListRef.child("Cartlist Admin View").child(userId).child("products").child(productID).updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {

                                                Toast.makeText(ProductDeatilsActivity.this, "Product is updated ", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(ProductDeatilsActivity.this,Cus1PetCategorySelect.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                          //  Intent intent = new Intent(ProductDeatilsActivity.this,Cus1PetCategorySelect.class);
                           /// startActivity(intent);

                            //Toast.makeText(ProductDeatilsActivity.this, "Product is added to cart", Toast.LENGTH_SHORT).show();

                        } else {

                            String message = task.getException().toString();
                            Toast.makeText(ProductDeatilsActivity.this, "Error:" + message, Toast.LENGTH_SHORT).show();


                        }

                    }
                });

    }

    private void getProductDetails(String productID) {
        // create database reference go get product details
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Items");

        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                   // product product=  snapshot.getValue(com.example.madmini.Model.product.class);

                    Item item  =snapshot.getValue(Item.class);

                    productName.setText(item.getItemName());
                    prodcutprice.setText(item.getPrice());
                    productDescription.setText(item.getDescription());

                    //get picasso library to get image from database
                    Picasso.get().load(item.getImage()).into(productimage);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void CheckOrderState()
    {
        DatabaseReference ordersRef;
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(userId);
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String state = snapshot.child("state").getValue().toString();


                    if(state.equals("delivered"))
                    {
                        statedel="order delivered";
                    }
                    else if (state.equals("pending"))
                    {
                        statedel="order placed";
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}