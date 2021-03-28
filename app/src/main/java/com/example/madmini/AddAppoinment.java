package com.example.madmini;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AddAppoinment extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener{



    private Spinner spinner;
    private Spinner spinnerS;
    EditText ownerName,petName,Type,Breed,dateText,Diseases,Vaccine,Surgery;
    Button appoSave;
    Button Go;
    String choice;
    String choice2;
    long maxid = 0;

    Button Doctor;
    Button Confirm;

    DatabaseReference reff;
    Appoinment appoinment;

    AwesomeValidation awesomeValidation;


    @SuppressLint("RestrictedApi")


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appoinment);


        getSupportActionBar().setTitle("");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);



        //Toast.makeText(MainActivity.this,"Firbase connection is success!!!",Toast.LENGTH_LONG).show();

        //spinner type
        spinner=findViewById(R.id.spinnerType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.locations,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        //spinner Surgery
        spinnerS=findViewById(R.id.spinnerSurgery);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.locationsSurgery,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerS.setAdapter(adapter1);

        spinnerS.setOnItemSelectedListener(this);

        ownerName = findViewById(R.id.editOwnerName);
        petName = findViewById(R.id.editPetName);
        //Type = findViewById(R.id.spinnerType);
        Breed = findViewById(R.id.editBreed);

        Diseases = findViewById(R.id.editDiseases);
        Vaccine = findViewById(R.id.editVaccine);
        // Surgery = findViewById(R.id.spinnerSurgery);
        dateText = findViewById(R.id.editTextDateMain);

        //validation
        //Initialize Validation Style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //Add Validation For Owner Name
        awesomeValidation.addValidation(this,R.id.editOwnerName,
                RegexTemplate.NOT_EMPTY,R.string.invalid_ownerName);

        //Add Validation For pet Name
        awesomeValidation.addValidation(this,R.id.editPetName,
                RegexTemplate.NOT_EMPTY,R.string.invalid_petName);


        //Add Validation Date
        awesomeValidation.addValidation(this,R.id.editTextDateMain,
                RegexTemplate.NOT_EMPTY,R.string.invalid_date);


        findViewById(R.id.btnDatefix).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        //go to view button for card  list veiw button

        Go = findViewById(R.id.buttonGoVeiwCust);
        Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent = new Intent(getApplicationContext(),AppListActivity.class);
                startActivity(intent);
            }
        });
        //go to view of doctor


        appoSave = findViewById(R.id.btnSaveAppp);


        appoinment = new Appoinment();


        reff = FirebaseDatabase.getInstance().getReference().child("Appoinment");


        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                if (dataSnapshot.exists())
                    maxid=(dataSnapshot.getChildrenCount());
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



           appoSave.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            //check Validation
            if (awesomeValidation.validate()){
                //on success




                appoinment.setownerName(ownerName.getText().toString().trim());
                appoinment.setpetName(petName.getText().toString().trim());
                appoinment.setType(choice.trim());
                appoinment.setBreed(Breed.getText().toString().trim());
                appoinment.setDate(dateText.getText().toString().trim());
                appoinment.setDiseases(Diseases.getText().toString().trim());
                appoinment.setVaccine(Vaccine.getText().toString().trim());
                appoinment.setSurgery(choice2.trim());


                //set ID
                reff.child(String.valueOf(maxid+1)).setValue(appoinment);

                //reff.push().setValue(appoinment);
                Toast.makeText(AddAppoinment.this,"Form Validate Succefully!!!!!!  Insert successfuly!!",Toast.LENGTH_LONG).show();






            }else {
                Toast.makeText(getApplicationContext()
                        ,"Form Validate Failed!!!!!!",Toast.LENGTH_LONG).show();
            }




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
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date =  + month + "/" + dayOfMonth + "/" + year;
        dateText.setText(date);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        choice = adapterView.getItemAtPosition(i).toString();
        choice2 = adapterView.getItemAtPosition(i).toString();
    }
    //spinner for pet TYPE
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}





