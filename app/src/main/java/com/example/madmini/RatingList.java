package com.example.madmini;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madmini.Model.Rating;

import java.util.List;

import ViewHolder.Rating_RecycleView_Config;

public class RatingList extends AppCompatActivity {

    private RecyclerView sRecyclerView;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_list);
        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


        sRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView_rating);
        new RatingFirebaseDatabaseHelper().readRatings(new RatingFirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Rating> ratings
                    , List<String> keys) {
                findViewById(R.id.loading_rating_progressBar).setVisibility(View.GONE);
                new Rating_RecycleView_Config().setConfig(sRecyclerView, RatingList.this,
                        ratings, keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }
}