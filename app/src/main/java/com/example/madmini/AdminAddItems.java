package com.example.madmini;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddItems extends AppCompatActivity implements  AdapterView.OnItemSelectedListener {


    private String PetCategoryName, Description, price, Pname, saveCurrentDate, saveCurrentTime,ItemType,Brand,status;
    private Button AddNewButton;

    RadioButton available,notAvailable;
    private EditText InputProductName, InputProductDisctription, InputProductPrice,InputBrand;
    private Spinner ItemTypeSpinner;
    private String ItemCategoryName;
    ImageView InputProductImage;
    boolean valid = true;
    // private static final int GalleryPick=1;

    public Uri ImageUri;

    private String productRandomKey, downloadImageUrl;
    private StorageReference productImagesRef;
    private DatabaseReference productsRef;
    private ProgressDialog loadingBar;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_items);

        PetCategoryName = getIntent().getExtras().get("petCategory").toString();

        getSupportActionBar().setTitle("For "+PetCategoryName);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);



        productImagesRef = FirebaseStorage.getInstance().getReference().child("Item Images");
        productsRef = FirebaseDatabase.getInstance().getReference().child("Items");

        //Toast.makeText(this, "CategoryName",Toast.LENGTH_SHORT).show();
        AddNewButton = (Button) findViewById(R.id.add_new_product);
        InputProductImage = (ImageView) findViewById(R.id.select1_product_image);
        InputProductName = (EditText) findViewById(R.id.product1_name);
        InputProductDisctription = (EditText) findViewById(R.id.product1_Discription);
        InputProductPrice = (EditText) findViewById(R.id.product1_Price);
        ItemTypeSpinner = (Spinner)findViewById(R.id.spinnerType);
        InputBrand =(EditText)findViewById(R.id.product1_brand);
        available=(RadioButton)findViewById(R.id.rdBtnAvailable);
        notAvailable=(RadioButton)findViewById(R.id.rdBtnNotAvailable);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.locationsitem1,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ItemTypeSpinner.setAdapter(adapter);

        ItemTypeSpinner.setOnItemSelectedListener(this);

        loadingBar = new ProgressDialog(this);


        InputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenPhoneGallery();
            }
        });

        AddNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProductData();
                checkField(InputProductName);
                checkField(InputProductDisctription);
                checkField(InputProductPrice);
                checkField(InputBrand);


            }
        });



    }


    private void OpenPhoneGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(galleryIntent.createChooser(galleryIntent,"select picture"), 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            ImageUri = data.getData();
            InputProductImage.setImageURI(ImageUri);

        }

    }


    private void ValidateProductData() {
        Description = InputProductDisctription.getText().toString();
        price = InputProductPrice.getText().toString();
        Pname = InputProductName.getText().toString();
        ItemType = ItemCategoryName;
        Brand = InputBrand.getText().toString();

        String R1 = available.getText().toString();
        String R2 = notAvailable.getText().toString();

        if(available.isChecked()){
            status = R1;
        }
        else
        {
            status = R2;
        }



        if (ImageUri == null) {

            Toast.makeText(this, "Select Product Image", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Description)) {
            Toast.makeText(this, "Please Write product discription", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(price)) {
            Toast.makeText(this, "Please Write product price", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(Pname)) {
            Toast.makeText(this, "Please Write product name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(ItemType)||ItemType.equals("Choose")) {
            Toast.makeText(this, "Please Select product type. Choose not a type", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(status)) {
            Toast.makeText(this, "Please Select availble or not", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Brand)) {
            Toast.makeText(this, "Please Write brand", Toast.LENGTH_SHORT).show();

        }

        else {
            StoreProductInformation();
        }

    }


    private void StoreProductInformation() {
        loadingBar.setTitle("Add New Product");
        loadingBar.setMessage("Please wait");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = productImagesRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AdminAddItems .this, "Error:" + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminAddItems.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadImageUrl = task.getResult().toString();
                            Toast.makeText(AdminAddItems.this, "getting product image Url successfully", Toast.LENGTH_SHORT).show();

                            SaveProductInfortoDatabase();
                        }
                    }
                });
            }
        });
    }


    private void SaveProductInfortoDatabase() {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid", productRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("description", Description);
        productMap.put("image", downloadImageUrl);
        productMap.put("petCategory", PetCategoryName);
        productMap.put("price", price);
        productMap.put("ItemName", Pname);
        productMap.put("ItemType",ItemType);
        productMap.put("Brand" , Brand);
        productMap.put("status" , status);

        productsRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(AdminAddItems.this, AdminPetCategory.class);
                            startActivity(intent);
                            loadingBar.dismiss();
                            Toast.makeText(AdminAddItems.this, "Product is added successfully", Toast.LENGTH_SHORT).show();

                        } else {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AdminAddItems.this, "Error:" + message, Toast.LENGTH_SHORT).show();


                        }

                    }
                });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ItemCategoryName = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public boolean checkField(EditText textFiled) {

        if (textFiled == InputProductName) {
            String val = InputProductName.getText().toString();
            if (val.isEmpty()) {
                InputProductName.setError("Field cannot be empty");
                valid = false;
            } else {
                InputProductName.setError(null);
                valid = true;
            }
            return valid;
        } else if (textFiled == InputProductPrice) {
            String val = InputProductPrice.getText().toString();
            if (val.isEmpty()) {
                InputProductPrice.setError("Field cannot be empty");
                valid = false;
            } else {
                InputProductPrice.setError(null);
                valid = true;
            }
            return valid;
        }else if (textFiled == InputProductDisctription) {
            String val = InputProductDisctription.getText().toString();
            if (val.isEmpty()) {
                InputProductDisctription.setError("Field cannot be empty");
                valid = false;
            } else {
                InputProductDisctription.setError(null);
                valid = true;
            }
            return valid;
        }else if (textFiled == InputBrand) {
            String val = InputBrand.getText().toString();
            if (val.isEmpty()) {
                InputBrand.setError("Field cannot be empty");
                valid = false;
            } else {
                InputBrand.setError(null);
                valid = true;
            }
            return valid;
        }
        return valid;
    }
}