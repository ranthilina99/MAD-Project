package com.example.madmini;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.madmini.Model.AdminOrders;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ViewHolder.AdminOrdersViewHolder;

public class AdminNewOrderActivity extends AppCompatActivity {

    private RecyclerView ordersList;
    private DatabaseReference ordersRef,cartlistadmin;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_order);

        getSupportActionBar().setTitle(" ");
          getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        cartlistadmin=FirebaseDatabase.getInstance().getReference().child("Cart List").child("Cartlist Admin View");


        ordersList= findViewById(R.id.orders_list);
        ordersList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<AdminOrders> options = new FirebaseRecyclerOptions.Builder<AdminOrders>()
                .setQuery(ordersRef,AdminOrders.class).build();

        FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder> adapter= new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AdminOrdersViewHolder adminOrdersViewHolder, final int i, @NonNull AdminOrders adminOrders) {
                adminOrdersViewHolder.userName.setText("Name: "+adminOrders.getName());
                adminOrdersViewHolder.userphone.setText("Phone num : "+adminOrders.getPhone());
                adminOrdersViewHolder.userTotalprice.setText("Total price: "+adminOrders.getTotalAmount());
                adminOrdersViewHolder.userDtaetime.setText("Order at:  "+adminOrders.getDate());
                adminOrdersViewHolder.useraddress.setText("Address: "+adminOrders.getAddress()+","+adminOrders.getProvince()+","+adminOrders.getDistrict()+","+adminOrders.getCity());

                adminOrdersViewHolder.showOrders.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // get the selected orderkey,id
                        String UID=getRef(i).getKey();
                        Intent intent = new Intent(AdminNewOrderActivity.this,AdminUserProductActivity.class);
                        intent.putExtra("uid",UID);
                        startActivity(intent);
                    }
                });


                adminOrdersViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[]= new CharSequence[]
                                {
                                        "Yes",
                                        "No"
                                };

                        AlertDialog.Builder builder= new AlertDialog.Builder(AdminNewOrderActivity.this);
                        builder.setTitle("Have you delivered this order prooducts ?");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                    if(i ==0)
                                    {
                                        String UID=getRef(i).getKey();

                                        RemoveOrder(UID);
                                    }
                                    else
                                    {
                                        finish();
                                    }
                            }
                        });
                       builder.show();
                    }
                });


            }

            @NonNull
            @Override
            public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout,parent,false);
                AdminOrdersViewHolder holder= new AdminOrdersViewHolder(view);
                return holder;
            }
        };

        ordersList.setAdapter(adapter);
        adapter.startListening();
    }

    private void RemoveOrder(String uid) {
        ordersRef.child(uid).removeValue();
        cartlistadmin.child(uid).removeValue();


    }
}