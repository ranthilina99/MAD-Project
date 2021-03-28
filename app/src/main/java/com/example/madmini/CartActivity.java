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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madmini.Model.Cart;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import ViewHolder.CartViewHolder;

public class CartActivity extends AppCompatActivity {


    private RecyclerView recyclerView; //
    private RecyclerView.LayoutManager layoutManager;
    private Button NextProcessBtn;
    private TextView txtTotalAmount,txtmsg1;
    private int TotalPrice=0; // varibale for total price

    // to get current online user
    private FirebaseAuth FAuth;
    private FirebaseFirestore FStore;
    private String userId;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        recyclerView= findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        NextProcessBtn= (Button) findViewById(R.id.next_process_btn);
        txtTotalAmount =(TextView) findViewById(R.id.total_price);


        txtmsg1 =(TextView) findViewById(R.id.msg1);

        //Firebase needs------------------------------------------------------------------------------------------------------------
        FAuth = FirebaseAuth.getInstance();
        FStore = FirebaseFirestore.getInstance();

        //get current user ids-----------------------------------------------------------------------------------------------------
        userId = FAuth.getCurrentUser().getUid();

        NextProcessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, ConfirmFinalOrderActivity.class);
                intent.putExtra("Total Price",String.valueOf(TotalPrice));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        CheckOrderState();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        // get parent and child firebase name and assigning to the recylerviwe
        FirebaseRecyclerOptions<Cart> options= new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartListRef.child("Cartlist User View").child(userId).child("products"),Cart.class).build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull final Cart cart) {
                // get product details from the cart object and assigning  to the text view
                cartViewHolder.txtProductQuentity.setText("Quantity ="+cart.getQuantity());
                cartViewHolder.txtProductPrice.setText("Price ="+cart.getPrice()+"Rs");
                cartViewHolder.txtProductName.setText(cart.getPname());
                // convert to string data type price to integer data type
                // calculate total price for one item
                int oneItemTotalPrice=((Integer.valueOf(cart.getPrice())))*Integer.valueOf(cart.getQuantity());

                TotalPrice=TotalPrice+oneItemTotalPrice;

                /////////////////////////////////
                txtTotalAmount.setText("Total price ="+String.valueOf(TotalPrice));

                /////////////////////////////
                cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        CharSequence options [] = new CharSequence[]
                                {
                                        "Edit",
                                        "Delete"

                                };
                        AlertDialog.Builder  builder= new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Cart Options:");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if(i==0)
                                {
                                    Intent intent= new Intent(CartActivity.this,ProductDeatilsActivity.class);
                                    intent.putExtra("pid",cart.getPid());
                                    startActivity(intent);
                                }
                                if(i==1)
                                {

                                    cartListRef.child("Cartlist User View").child(userId).child("products").child(cart.getPid())
                                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(CartActivity.this,"Item Deleted successfully.",Toast.LENGTH_SHORT).show();
                                                Intent intent= new Intent(CartActivity.this,Cus1PetCategorySelect.class);
                                                startActivity(intent);

                                            }
                                        }
                                    });
                                }
                            }

                        });

                        builder.show();

                    }
                });

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    // print order state in the chart activity
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
                    String  uname= snapshot.child("name").getValue().toString();

                    if(state.equals("delivered"))
                    {
                        txtTotalAmount.setText("Hello"+uname+"\n  Order is delivered successfully");
                        recyclerView.setVisibility(View.GONE);//invisible recyclerView
                        txtmsg1.setVisibility(View.VISIBLE);
                        txtmsg1.setText("thanks , your order has been placed successfully, soon we will received your order");
                        // visible txtmessage
                        NextProcessBtn.setVisibility(View.GONE);/// disable the next button
                    }
                    else if (state.equals("pending"))
                    {
                        txtTotalAmount.setText("Not deliver yet");
                        recyclerView.setVisibility(View.GONE);//invisible recyclerView
                        txtmsg1.setVisibility(View.VISIBLE);  // visible txtmessage
                        NextProcessBtn.setVisibility(View.GONE);/// disable the next button
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}