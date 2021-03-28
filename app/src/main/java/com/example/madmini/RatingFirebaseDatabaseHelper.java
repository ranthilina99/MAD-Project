package com.example.madmini;

import androidx.annotation.NonNull;

import com.example.madmini.Model.Rating;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RatingFirebaseDatabaseHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceRatings;
    private List<Rating> ratings = new ArrayList<>();


    public interface DataStatus{
        void DataIsLoaded(List<Rating> ratings, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();


    }

    public RatingFirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceRatings = mDatabase.getReference("Ratings");

    }

    public void readRatings(final DataStatus dataStatus){
        mReferenceRatings.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ratings.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Rating rating = keyNode.getValue(Rating.class);
                    ratings.add(rating);
                }

                dataStatus.DataIsLoaded(ratings,keys);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
