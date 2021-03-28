package com.example.madmini;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class AdminUserList extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter adapter;
    FirebaseAuth FAuth;
    FirebaseUser user;

    DatabaseReference itemRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_list);
        FAuth = FirebaseAuth.getInstance();
        user = FAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.firestore_list);
        user = FAuth.getCurrentUser();



        Query query = firebaseFirestore.collection("Users");

        FirestoreRecyclerOptions<UserModels> options = new FirestoreRecyclerOptions.Builder<UserModels>()
                .setQuery(query, UserModels.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<UserModels, UserViewModel>(options) {
            @NonNull
            @Override
            public UserViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_single_list, parent, false);
                return new UserViewModel(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull UserViewModel holder, final int position, @NonNull UserModels model) {
                holder.F1.setText(model.getFirstName());
                holder.L1.setText(model.getLastName());
                holder.M1.setText(model.getMobile());
                holder.E1.setText(model.getEmail());

            }
        };
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

   /* private void deleteSelectRow() {
        itemRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(AdminUserList.this,"The Product Deleted successfully",Toast.LENGTH_LONG).show();

                finish();
            }
        });
    }*/


    private class UserViewModel extends RecyclerView.ViewHolder {
        TextView F1, L1, M1, E1;


        public UserViewModel(@NonNull View itemView) {
            super(itemView);
            F1 = itemView.findViewById(R.id.txtFName);
            L1 = itemView.findViewById(R.id.txtLName);
            M1 = itemView.findViewById(R.id.txtMobile);
            E1 = itemView.findViewById(R.id.txtEmail);


        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}