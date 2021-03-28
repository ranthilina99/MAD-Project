package com.example.madmini;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.List;

public class AppListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView_Appo);

        new FirebaseDatabaseHelper().readAppointments(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Appoinment> appoinments, List<String> keys) {
                findViewById(R.id.loading_appo_progressBar).setVisibility(View.GONE);
                new RecyclerView_Config().setConfig(mRecyclerView, AppListActivity.this,
                        appoinments, keys);

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