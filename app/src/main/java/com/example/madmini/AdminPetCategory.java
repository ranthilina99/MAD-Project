package com.example.madmini;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AdminPetCategory extends AppCompatActivity {



    private ImageView dog, cat, bird;
    private ImageView fish, other;
    private Button adminViewItwms;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pet_category);

        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        dog = (ImageView)findViewById(R.id.img_dog);
        cat =(ImageView)findViewById(R.id.img_cat);
        bird = (ImageView)findViewById(R.id.img_bird);
        fish =(ImageView)findViewById(R.id.img_fish);
        other = (ImageView)findViewById(R.id.img_other);
        adminViewItwms =(Button)findViewById(R.id.btnAdminViewItemss);



        dog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),AdminAddItems.class);
                intent.putExtra("petCategory", "Dog");
                startActivity(intent);
            }
        });

        cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),AdminAddItems.class);
                intent.putExtra("petCategory", "Cat");
                startActivity(intent);
            }
        });

        bird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),AdminAddItems.class);
                intent.putExtra("petCategory", "Bird");
                startActivity(intent);
            }
        });

        fish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),AdminAddItems.class);
                intent.putExtra("petCategory", "Fish");
                startActivity(intent);
            }
        });

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),AdminAddItems.class);
                intent.putExtra("petCategory", "Other");
                startActivity(intent);
            }
        });

        adminViewItwms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AdminItemsView.class);
                startActivity(intent);
            }
        });
    }
}