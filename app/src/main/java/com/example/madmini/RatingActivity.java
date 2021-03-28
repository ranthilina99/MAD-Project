package com.example.madmini;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.madmini.Model.Rating;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class RatingActivity extends AppCompatActivity {

    DatabaseReference reff;
    private RatingBar rating1,rating2;
    int totalRating;
    Button btnSubmit,btnView;
    float RatedValue;
    long maxid = 0;
    float totalPercentage;
    Rating rating;
    TextView eTxtName;
    String UserName,userId;
    FirebaseAuth FAuth;
    FirebaseFirestore FStore;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        rating1 = (RatingBar)findViewById(R.id.ratingBar);

        eTxtName = (TextView) findViewById(R.id.edUserName) ;
        rating1.setMax(5);
        rating1.setRating((float) 3.5);

        FAuth = FirebaseAuth.getInstance();
        FStore = FirebaseFirestore.getInstance();

        btnSubmit=(Button)findViewById(R.id.btn_submit);


        userId = FAuth.getCurrentUser().getUid();


        final DocumentReference documentReference =FStore.collection("Users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                eTxtName.setText(value.getString("Email"));
            }
        });
        rating = new Rating();

        reff = FirebaseDatabase.getInstance().getReference().child("Ratings");

        //set ID
        reff.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    maxid=(snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                totalRating = rating1.getNumStars();


                RatedValue = rating1.getRating();

                totalPercentage = (RatedValue/totalRating)*100;


                Toast.makeText(RatingActivity.this, "Your rating is"+RatedValue+"/"+totalRating,Toast.LENGTH_LONG).show();



                        rating.setPrecentage(totalPercentage);
                        rating.setUserName(eTxtName.getText().toString().trim());
                        rating.setRatedvalue(RatedValue);



                        reff.child(String.valueOf(maxid+1)).setValue(rating);


                        Toast.makeText(RatingActivity.this,"Insert successfuly!!",Toast.LENGTH_LONG).show();


                        rating1.setIsIndicator(true);

                    }
                });




    }
}