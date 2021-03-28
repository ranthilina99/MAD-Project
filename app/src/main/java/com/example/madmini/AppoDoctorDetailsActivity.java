package com.example.madmini;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.List;

public class AppoDoctorDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {

    private EditText ownerName,petName,Type,dateText;

    private Spinner spinner;
  //  private Spinner spinnerS;
    String choice;
    String choice2;

    private Button mConfirm_btn;
    private Button mDelete_btn;
    private Button mBack_btn;


    private String key;

    private  String Dateq;

    private String pet;
    private String owner;
    private String breed;
     //  private String diseases;
    //private String vaccine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appo_doctor_detais);

        key = getIntent().getStringExtra("key");
        owner=getIntent().getStringExtra("ownerName"); //000000000000000000000000000000000000000
        pet=getIntent().getStringExtra("petName");
        breed=getIntent().getStringExtra("Breed");
        //
        Dateq=getIntent().getStringExtra("Date");
      //  diseases=getIntent().getStringExtra("Diseases");
       // vaccine=getIntent().getStringExtra("Vaccine");


        //btn
        mConfirm_btn = (Button) findViewById(R.id.buttonConfirm);
        mDelete_btn = (Button) findViewById(R.id.btnDeleteDoctor);
        //mBack_btn = (Button) findViewById(R.id.btnDoctorCancelUpPage);

       /* mConfirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appoinment appoinment = new Appoinment();
                appoinment.setownerName(ownerName.getText().toString().trim());
                appoinment.setpetName(petName.getText().toString().trim());
                appoinment.setType(choice.trim());
                appoinment.setBreed(Breed.getText().toString().trim());
                appoinment.setDate(dateText.getText().toString().trim());
                appoinment.setDiseases(Diseases.getText().toString().trim());
                appoinment.setVaccine(Vaccine.getText().toString().trim());
                appoinment.setSurgery(choice2.trim());

                new FirebaseDatabaseHelper().updateAppointment(key, appoinment, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Appoinment> appoinments, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(AppoDoctorDetailsActivity.this,"Appointment record has been" + "updated successfully",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
        });    */

        //confirm and go notification

        mConfirm_btn = findViewById(R.id.buttonConfirm);
        mConfirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),NptificaSendingActivity.class);
                startActivity(intent);
            }
        });


        mDelete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DoctorFirebaseDatabaseHelper().deleteAppointment(key, new DoctorFirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Appoinment> appoinments, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {
                        Toast.makeText(AppoDoctorDetailsActivity.this, "Appointment record has been" + "deleted successfully!", Toast.LENGTH_LONG).show();
                        finish(); return;
                    }
                });
            }
        });

       /* mBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();return;
            }
        });*/

        //spinner type
        spinner = findViewById(R.id.spinnerType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.locations22, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        //spinner Surgery
     /*   spinnerS=findViewById(R.id.spinnerSurgery);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.locationsSurgery,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerS.setAdapter(adapter1);   */

       // spinnerS.setOnItemSelectedListener(this);

        ownerName = findViewById(R.id.editOwnerName);
        ownerName.setText(owner); //0000000000000000000000

        petName = findViewById(R.id.editPetName);
        petName.setText(pet);

        //  Type = findViewById(R.id.spinnerType);
     //   Breed = findViewById(R.id.editBreed);
      //  Breed.setText(breed);

       // Diseases = findViewById(R.id.editDiseases);
        //Diseases.setText(diseases);
        //Vaccine = findViewById(R.id.editVaccine);
        //Vaccine.setText(vaccine);
        // Surgery = findViewById(R.id.spinnerSurgery);
        dateText = findViewById(R.id.editTextDateMain);
        //
        dateText.setText(Dateq);
        findViewById(R.id.btnDatefix).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
    }

    //date fix
    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

        );
        datePickerDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        choice = adapterView.getItemAtPosition(i).toString();
        choice2 = adapterView.getItemAtPosition(i).toString();
    }
    //spinner pet type
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date =  + month + "/" + dayOfMonth + "/" + year;
        dateText.setText(date);

    }
}