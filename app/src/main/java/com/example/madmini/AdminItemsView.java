package com.example.madmini;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.madmini.Model.Item;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import ViewHolder.AdminViewHolder;
import ViewHolder.ItemViewHolder;

public class AdminItemsView extends AppCompatActivity {

    private DatabaseReference itemReff;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private AppBarConfiguration mAppBarConfiguration;
    private String stts ="";

    private FloatingActionButton searchbtnAdm;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_items_view);
        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


        itemReff = FirebaseDatabase.getInstance().getReference().child("Items");

        recyclerView = findViewById(R.id.recycler_menu_adm);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        searchbtnAdm = findViewById(R.id.serch_adm);

        searchbtnAdm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();




        FirebaseRecyclerOptions<Item> options = new FirebaseRecyclerOptions.Builder<Item>().setQuery(itemReff, Item.class).build();

        FirebaseRecyclerAdapter<Item, AdminViewHolder> adapter = new
                FirebaseRecyclerAdapter<Item, AdminViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdminViewHolder adminViewHolder, final int i, @NonNull final Item item) {
                        adminViewHolder.textItemNameAdm.setText(item.getItemName());
                        adminViewHolder.txtitembrandAdm.setText(item.getBrand());
                        adminViewHolder.txtitemPriceAdm.setText("Rs " +item.getPrice());
                        adminViewHolder.txtItemDiscriptionAdm.setText(item.getDescription());
                        adminViewHolder.txtPetTypeAdm.setText(item.getPetCategory());
                        adminViewHolder.txtItetypeAdm.setText(item.getItemType());
                        Picasso.get().load(item.getImage()).into(adminViewHolder.imageViewAdm);
                        stts = item.getStatus();
                        if(stts.equals("Not-Available")){
                            adminViewHolder.txtStatusAdm.setText(stts);}
                        else
                        {
                            adminViewHolder.txtStatusAdm.setText(" ");

                        }


                        adminViewHolder.imageViewAdm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent= new Intent(AdminItemsView.this,AdminMaintainItem.class);
                                intent.putExtra("pid",item.getPid());
                                startActivity(intent);

                            }
                        });

                    }

                    @NonNull
                    @Override
                    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_layout, parent, false);
                        AdminViewHolder viewHolder = new AdminViewHolder(view);
                        return viewHolder;
                    }
                };



        recyclerView.setAdapter(adapter);
        adapter.startListening();



    }
}