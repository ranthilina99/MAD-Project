package com.example.madmini;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class UserFeedBackTstActivity extends AppCompatActivity {
    EditText namedata, emaildata, messagedata;
    Button send, details;

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed_back_tst);

        namedata=findViewById(R.id.namedata);
        emaildata=findViewById(R.id.emaildata);
        messagedata=findViewById(R.id.messagedata);

        send=findViewById(R.id.btn_send);
        details=findViewById(R.id.btn_details);


        //Initialize Validation Style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //Add Validation For Name
        awesomeValidation.addValidation(this, R.id.namedata,
                RegexTemplate.NOT_EMPTY, R.string.invalid_name);

        //For Email
        awesomeValidation.addValidation(this, R.id.emaildata,
                Patterns.EMAIL_ADDRESS, R.string.invalid_email);



        //Add Validation For Name
        awesomeValidation.addValidation(this, R.id.messagedata,
                RegexTemplate.NOT_EMPTY, R.string.invalid_message);




        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                //check Validation
                if (awesomeValidation.validate()){
                    //on success
                    Toast.makeText(getApplicationContext()
                            ,"Form Validate Succefully!!!!!!",Toast.LENGTH_SHORT).show();



                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/html");
                    i.putExtra(Intent.EXTRA_EMAIL, new String("xyz@gmail.com"));
                    i.putExtra(Intent.EXTRA_SUBJECT, "FeedBack From App");
                    i.putExtra(Intent.EXTRA_TEXT, "Name:" + namedata.getText() + "\n Email:" + emaildata.getText() + "\n Message:" + messagedata.getText());

                    try {
                        startActivity(Intent.createChooser(i, "Please select Email"));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(UserFeedBackTstActivity.this, "There are no Email Clients", Toast.LENGTH_SHORT).show();
                    }



                }else {
                    Toast.makeText(getApplicationContext()
                            ,"Form Validate Failed!!!!!!",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}