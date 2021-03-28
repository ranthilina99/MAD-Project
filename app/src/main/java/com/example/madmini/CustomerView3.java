package com.example.madmini;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import com.example.madmini.Model.Item;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ViewHolder.ItemViewHolder;

public class CustomerView3 extends AppCompatActivity {

    private DatabaseReference itemReff;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private AppBarConfiguration mAppBarConfiguration;
    private String stts ="";
    private String PetCategoryName;
    
    private  FloatingActionButton catrBtn,search;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view3);
        PetCategoryName = getIntent().getExtras().get("petCategory").toString();

        getSupportActionBar().setTitle("For your "+PetCategoryName);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


        itemReff = FirebaseDatabase.getInstance().getReference().child("Items");

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        catrBtn = findViewById(R.id.fab11);
        search = findViewById(R.id.search_cus);
        
        catrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotocart();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerView3.this,SearchActivity.class);
                startActivity(intent);

            }
        });


    }

    private void gotocart() {

        Intent intent = new Intent(CustomerView3.this,CartActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Item> options = new FirebaseRecyclerOptions.Builder<Item>()
                .setQuery(itemReff.orderByChild("petCategory").equalTo(PetCategoryName),Item.class).build();


        // FirebaseRecyclerOptions<Item> options =
                //new FirebaseRecyclerOptions.Builder<Item>().setQuery(itemReff,Item.class).build();
        FirebaseRecyclerAdapter<Item, ItemViewHolder> adapter =
                new FirebaseRecyclerAdapter<Item, ItemViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i, @NonNull final Item item) {

                        itemViewHolder.textItemName.setText(item.getItemName());
                        itemViewHolder.txtitembrand.setText(item.getBrand());
                        itemViewHolder.txtitemPrice.setText("Rs " +item.getPrice());
                        itemViewHolder.txtItemDiscription.setText(item.getDescription());
                        Picasso.get().load(item.getImage()).into(itemViewHolder.imageView);
                        stts = item.getStatus();
                        if(stts.equals("Not-Available")){
                        itemViewHolder.txtStatus.setText(stts);
                            itemViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(CustomerView3.this, "Sorry...Product is not available!!!!", Toast.LENGTH_SHORT).show();


                                }
                            });}

                        else {
                            itemViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(CustomerView3.this, ProductDeatilsActivity.class);
                                    // get prodcut id using product model object
                                    intent.putExtra("pid", item.getPid());
                                    startActivity(intent);

                                }
                            });
                        }

                    }

                    @NonNull
                    @Override
                    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_view_layout, parent, false);
                        ItemViewHolder viewHolder = new ItemViewHolder(view);
                        return viewHolder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}