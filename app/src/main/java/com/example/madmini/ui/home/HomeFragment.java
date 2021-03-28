package com.example.madmini.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.madmini.AddAppoinment;
import com.example.madmini.CartActivity;
import com.example.madmini.ChangePassword;
import com.example.madmini.Changeprofile;
import com.example.madmini.Cus1PetCategorySelect;
import com.example.madmini.CustomerView3;
import com.example.madmini.R;
import com.example.madmini.RatingActivity;
import com.example.madmini.ui.Login;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {


    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        final ImageView btn1=root.findViewById(R.id.changepro);


        final ImageView btn2=root.findViewById(R.id.productStore);
        final ImageView btn3=root.findViewById(R.id.appointmentAdd);
        final ImageView btn4 = root.findViewById(R.id.rating1);
       final FloatingActionButton catrBtn = root.findViewById(R.id.cart4);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Changeprofile.class));
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Cus1PetCategorySelect.class));
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddAppoinment.class));
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getActivity(), RatingActivity.class));
            }
        });

        catrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CartActivity.class));
            }
        });



        return root;

    }
}