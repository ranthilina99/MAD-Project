package com.example.madmini;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.madmini.Model.Item;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import ViewHolder.ItemViewHolder;

public class SearchActivity extends AppCompatActivity {

  private   Button search_btn;
   private EditText inputProductName;
   private RecyclerView searchList;
   private String searchInput;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        inputProductName = (EditText)findViewById(R.id.search_product_name);
        search_btn = (Button)findViewById(R.id.search_btn);
        searchList = (RecyclerView)findViewById(R.id.search_list);
        searchList.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 searchInput = inputProductName.getText().toString();
                 onStart();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Items");

        FirebaseRecyclerOptions<Item> options = new FirebaseRecyclerOptions.Builder<Item>()
                .setQuery(reference.orderByChild("ItemName").startAt(searchInput),Item.class).build();

        FirebaseRecyclerAdapter<Item, ItemViewHolder> adapter = new FirebaseRecyclerAdapter<Item, ItemViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i, @NonNull Item item) {

                itemViewHolder.textItemName.setText(item.getItemName());
                itemViewHolder.txtitembrand.setText(item.getBrand());
                itemViewHolder.txtitemPrice.setText("Rs " +item.getPrice());
                itemViewHolder.txtItemDiscription.setText(item.getDescription());
                Picasso.get().load(item.getImage()).into(itemViewHolder.imageView);

            }

            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_view_layout, parent, false);
                ItemViewHolder viewHolder = new ItemViewHolder(view);
                return viewHolder;
            }
        };

        searchList.setAdapter(adapter);
        adapter.startListening();

    }
}