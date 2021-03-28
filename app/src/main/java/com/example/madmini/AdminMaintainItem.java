package com.example.madmini;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AdminMaintainItem extends AppCompatActivity {

    private Button updateButton,deleteButton;
    private EditText name,brand,price,description;
    private ImageView imge1;
    private String productID = "";
    private DatabaseReference itemRef;
    private RadioButton availbl,notAvail;
    boolean valid = true;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintain_item);
        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        productID = getIntent().getStringExtra("pid");

        itemRef = FirebaseDatabase.getInstance().getReference().child("Items").child(productID);

        updateButton = (Button)findViewById(R.id.update_button_I);
        name = (EditText) findViewById(R.id.item_name_m);
        brand = (EditText)findViewById(R.id.item_brand_m);
        price = (EditText)findViewById(R.id.item_price_m);
        description = (EditText)findViewById(R.id.item_description_m);
        imge1 = (ImageView)findViewById(R.id.item_image_m);
        deleteButton = (Button)findViewById(R.id.delete_button_I);
        availbl = (RadioButton)findViewById(R.id.rdBtnAvailable_m);
        notAvail = (RadioButton)findViewById(R.id.rdBtnNotAvailable_m);


        displayItemInfo();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyChanges();
                checkField(name);
                checkField(brand);
                checkField(price);
                checkField(description);

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct();
            }
        });



    }

    private void deleteProduct() {
        itemRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(AdminMaintainItem.this,"The Product Deleted successfully",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AdminMaintainItem.this, AdminItemsView.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void applyChanges() {

        String Iname =name.getText().toString();
        String Ibrand =brand.getText().toString();
        String Iprice =price.getText().toString();
        String Idescription =description.getText().toString();
        String R1 = availbl.getText().toString();
        String R2 = notAvail.getText().toString();
        String status;

        if(availbl.isChecked()){
            status = R1;
        }
        else
        {
            status = R2;
        }


        if(Iname.equals(""))
        {
            Toast.makeText(this, "Please Write product name", Toast.LENGTH_SHORT).show();

        }
        else if(Ibrand.equals(""))
        {
            Toast.makeText(this, "Please Write product brand", Toast.LENGTH_SHORT).show();

        }
        else if(Iprice.equals(""))
        {
            Toast.makeText(this, "Please Write product price", Toast.LENGTH_SHORT).show();

        }
        else if(Idescription.equals(""))
        {
            Toast.makeText(this, "Please Write product description", Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty(status)) {
            Toast.makeText(this, "Please Select availble or not", Toast.LENGTH_SHORT).show();
        }
        else{

            HashMap<String, Object> itemMap = new HashMap<>();
            itemMap.put("pid", productID);
            itemMap.put("description", Idescription);
            itemMap.put("price", Iprice);
            itemMap.put("ItemName", Iname);
            itemMap.put("Brand" , Ibrand);
            itemMap.put("status" , status);
            itemRef.updateChildren(itemMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(AdminMaintainItem.this,"Updated successfully",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AdminMaintainItem.this, AdminItemsView.class);

                        startActivity(intent);
                        finish();

                    }

                }
            });
        }

    }


    private void displayItemInfo() {
        itemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    String Iname = dataSnapshot.child("ItemName").getValue().toString();
                    String Ibrand = dataSnapshot.child("Brand").getValue().toString();
                    String Iprice = dataSnapshot.child("price").getValue().toString();
                    String Idescription = dataSnapshot.child("description").getValue().toString();
                    String Iimage = dataSnapshot.child("image").getValue().toString();
                    String ssts = dataSnapshot.child("status").getValue().toString();

                    if(ssts.equals("Available"))
                    {
                        availbl.toggle();
                    }
                    else
                    {
                        notAvail.toggle();
                    }
                    name.setText(Iname);
                    brand.setText(Ibrand);
                    price.setText(Iprice);
                    description.setText(Idescription);

                    Picasso.get().load(Iimage).into(imge1);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public boolean checkField(EditText textFiled) {

        if (textFiled == name) {
            String val = name.getText().toString();
            if (val.isEmpty()) {
                name.setError("Field cannot be empty");
                valid = false;
            } else {
                name.setError(null);
                valid = true;
            }
            return valid;
        } else if (textFiled == brand) {
            String val = brand.getText().toString();
            if (val.isEmpty()) {
                brand.setError("Field cannot be empty");
                valid = false;
            } else {
                brand.setError(null);
                valid = true;
            }
            return valid;
        }else if (textFiled == price) {
            String val = price.getText().toString();
            if (val.isEmpty()) {
                price.setError("Field cannot be empty");
                valid = false;
            } else {
                price.setError(null);
                valid = true;
            }
            return valid;
        }else if (textFiled == description) {
            String val = description.getText().toString();
            if (val.isEmpty()) {
                description.setError("Field cannot be empty");
                valid = false;
            } else {
                description.setError(null);
                valid = true;
            }
            return valid;
        }
        return valid;
    }
}