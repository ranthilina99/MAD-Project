package com.example.madmini;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdminAPPOListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_a_p_p_o_list);


        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView_AppoAdmin);
        new DoctorFirebaseDatabaseHelper().readAppoitments(new DoctorFirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Appoinment> appoinments, List<String> keys) {
                findViewById(R.id.loading_appo_progressBarAdmin).setVisibility(View.GONE);
                new RecyclerVeiwConfig_Doctor().setConfig(mRecyclerView, AdminAPPOListActivity.this,
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