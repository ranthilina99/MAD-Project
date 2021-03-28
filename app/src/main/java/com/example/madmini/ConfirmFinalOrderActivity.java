package com.example.madmini;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.basgeekball.awesomevalidation.AwesomeValidation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    private EditText nameEditText, phoneEditText, addressEditText, provinceEditText, DistrictEditText, CityEditText;
    private Button confermOrderBtn;
    private String totalAmount = "";

    // to get current online user
    private FirebaseAuth FAuth;
    private FirebaseFirestore FStore;
    private String userId;
    boolean valid = true;
    AwesomeValidation awesomeValidation;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);
        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        // awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        confermOrderBtn = (Button) findViewById(R.id.conferm_final_btn);

        nameEditText = (EditText) findViewById(R.id.delivery_name);
        phoneEditText = (EditText) findViewById(R.id.delivery_phone_num);
        addressEditText = (EditText) findViewById(R.id.delivery_Address);
        provinceEditText = (EditText) findViewById(R.id.delivery_province);
        DistrictEditText = (EditText) findViewById(R.id.delivery_District);
        CityEditText = (EditText) findViewById(R.id.delivery_City);

        //Firebase needs------------------------------------------------------------------------------------------------------------
        FAuth = FirebaseAuth.getInstance();
        FStore = FirebaseFirestore.getInstance();

        //get current user ids-----------------------------------------------------------------------------------------------------
        userId = FAuth.getCurrentUser().getUid();

        totalAmount = getIntent().getStringExtra("Total Price");

        Toast.makeText(this, "Total price= Rs" + totalAmount, Toast.LENGTH_SHORT).show();


        confermOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(nameEditText);
                checkField(phoneEditText);
                checkField(addressEditText);
                checkField(provinceEditText);
                checkField(DistrictEditText);
                checkField(CityEditText);

                ConfirmOrder();

            }
        });
    }

    public boolean checkField(EditText textFiled) {

        if (textFiled == nameEditText) {
            String val = nameEditText.getText().toString();
            if (val.isEmpty()) {
                nameEditText.setError("Field cannot be empty");
                valid = false;
            } else {
                nameEditText.setError(null);
                valid = true;
            }
            return valid;


        }

        if (textFiled == phoneEditText) {
            String val = phoneEditText.getText().toString();
            String MobilePattern = "[0-9]{10}";

            if (val.isEmpty()) {
                phoneEditText.setError("Field cannot be empty");
                valid = false;
            } else {
                phoneEditText.setError(null);
                valid = true;
            }
            return valid;

        }
        if (textFiled == addressEditText) {
            String val = addressEditText.getText().toString();

            if (val.isEmpty()) {
                addressEditText.setError("Field cannot be empty");
                valid = false;
            } else {
                addressEditText.setError(null);
                valid = true;
            }
            return valid;

        }

        if (textFiled == provinceEditText) {
            String val = provinceEditText.getText().toString();

            if (val.isEmpty()) {
                provinceEditText.setError("Field cannot be empty");
                valid = false;
            } else {
                provinceEditText.setError(null);
                valid = true;
            }
            return valid;

        }

        if (textFiled == DistrictEditText) {
            String val = DistrictEditText.getText().toString();

            if (val.isEmpty()) {
                DistrictEditText.setError("Field cannot be empty");
                valid = false;
            } else {
                DistrictEditText.setError(null);
                valid = true;
            }
            return valid;

        }

        if (textFiled == CityEditText) {
            String val = CityEditText.getText().toString();

            if (val.isEmpty()) {
                CityEditText.setError("Field cannot be empty");
                valid = false;
            } else {
                CityEditText.setError(null);
                valid = true;
            }
            return valid;

        }

        return valid;
    }


    private void ConfirmOrder() {


            // create varibale to get current date and time
            final String saveCurrentTime, saveCurrentDate;

            // get  current time when adding to cart list the selected item
            Calendar calForDate = Calendar.getInstance();
            // Get current data
            SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
            // store current date in variable
            saveCurrentDate = currentDate.format(calForDate.getTime());


            // Get current time
            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            // store current time in variable
            saveCurrentTime = currentDate.format(calForDate.getTime());

        if (valid) {
            final DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(userId);


            HashMap<String, Object> ordersMap = new HashMap<>();
            //column na to the firebase
            ordersMap.put("totalAmount", totalAmount);
            ordersMap.put("name", nameEditText.getText().toString());
            ordersMap.put("phone", phoneEditText.getText().toString());
            ordersMap.put("date", saveCurrentDate);
            ordersMap.put("time", saveCurrentTime);
            ordersMap.put("address", addressEditText.getText().toString());
            ordersMap.put("province", provinceEditText.getText().toString());
            ordersMap.put("District", DistrictEditText.getText().toString());
            ordersMap.put("City", CityEditText.getText().toString());
            ordersMap.put("state", "pending");

            ordersRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override


                public void onComplete(@NonNull Task<Void> task) {


                    if (task.isSuccessful()) {
                        // remove the cart list after order confirm
                        FirebaseDatabase.getInstance().getReference().child("Cart List").child("Cartlist User View").child(userId).child("products").removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(ConfirmFinalOrderActivity.this, "order is placed successfully", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(ConfirmFinalOrderActivity.this, CartActivity.class);
                                            // user can not go to back this activity
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);

                                            finish();

                                        }
                                    }
                                });
                    }


                }
            });
        }
    }
}



