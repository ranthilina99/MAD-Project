package com.example.madmini;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DoctorFirebaseDatabaseHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceAppointments;
    private List<Appoinment> appoinments = new ArrayList<>();


    public interface DataStatus{
        void DataIsLoaded(List<Appoinment> appoinments, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public DoctorFirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceAppointments = mDatabase.getReference("Appoinment");
    }

    //read appointments---appoitments

    public void readAppoitments(final DataStatus dataStatus){
        mReferenceAppointments.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appoinments.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Appoinment appoinment = keyNode.getValue(Appoinment.class);
                    appoinments.add(appoinment);
                }
                dataStatus.DataIsLoaded(appoinments,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

  /*  public void updateAppointment(String key, Appoinment appoinment, final FirebaseDatabaseHelper.DataStatus dataStatus){
        mReferenceAppointments.child(key).setValue(appoinment)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsUpdated();
                    }
                });
    } */
    public void deleteAppointment(String key, final DoctorFirebaseDatabaseHelper.DataStatus dataStatus){
        mReferenceAppointments.child(key).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsDeleted();
                    }
                });
    }
}

