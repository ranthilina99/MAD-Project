package com.example.madmini;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Cus1PetCategorySelect extends AppCompatActivity {

    private Button dog, cat, bird;
    private Button fish, other;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus1_pet_category_select);

        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        dog = (Button)findViewById(R.id.btnDog);
        cat =(Button)findViewById(R.id.btnCat);
        bird = (Button)findViewById(R.id.btnBird);
        fish =(Button)findViewById(R.id.btnFish);
        other = (Button)findViewById(R.id.btnOther);


        dog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), CustomerView3.class);
                intent.putExtra("petCategory", "Dog");
                startActivity(intent);
            }
        });

        cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), CustomerView3.class);
                intent.putExtra("petCategory", "Cat");
                startActivity(intent);
            }
        });

        bird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), CustomerView3.class);
                intent.putExtra("petCategory", "Bird");
                startActivity(intent);
            }
        });

        fish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), CustomerView3.class);
                intent.putExtra("petCategory", "Fish");
                startActivity(intent);
            }
        });

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), CustomerView3.class);
                intent.putExtra("petCategory", "Other");
                startActivity(intent);
            }
        });


    }
}