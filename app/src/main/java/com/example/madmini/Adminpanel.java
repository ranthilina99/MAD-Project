package com.example.madmini;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.madmini.ui.Login;
import com.google.firebase.auth.FirebaseAuth;

public class Adminpanel extends AppCompatActivity {
    private Button logout;

    private Button category,AllUsersList;

    private Button checkOrdersbtn,appoinments,btnView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpanel);

        category= findViewById(R.id.btn_category);
        checkOrdersbtn = findViewById(R.id.btnShowOrders);
        appoinments = findViewById(R.id.appointmentsShow);
        AllUsersList = findViewById(R.id.AllUserlist);
        btnView=(Button)findViewById(R.id.btn_view);

        logout=findViewById(R.id.btnadminpanel);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Adminpanel.this,AdminPetCategory.class);
                startActivity(intent);

            }
        });

        checkOrdersbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Adminpanel.this, maintainOrders.class);
                startActivity(intent);
            }
        });

      appoinments.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(Adminpanel.this,AdminAPPOListActivity.class);
              startActivity(intent);
          }
      });
        AllUsersList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Adminpanel.this,AdminUserList.class);
                startActivity(intent);
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RatingList.class);
                startActivity(intent);
            }
        });
    }
}