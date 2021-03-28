package com.example.madmini;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class maintainOrders extends AppCompatActivity {


    private Button checkOrders,approveorders;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain_orders);
        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        checkOrders= findViewById(R.id.btn_Check_new_orde);



        checkOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(maintainOrders.this,AdminNewOrderActivity.class);
                startActivity(intent);
            }
        });
    }
}