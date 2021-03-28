package com.example.madmini;

import android.app.Notification;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import static com.example.madmini.App.CHANNEL_1_ID;
import static com.example.madmini.App.CHANNEL_2_ID;


public class NptificaSendingActivity extends AppCompatActivity {


    private NotificationManagerCompat notificationManager;
    private EditText editTextTitle;
    private EditText editTextMessage;


    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nptifica_sending);

        notificationManager = NotificationManagerCompat.from(this);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextMessage = findViewById(R.id.edit_text_message);



        //validation
        //Initialize Validation Style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //Add Validation For Owner Name
        awesomeValidation.addValidation(this,R.id.edit_text_title,
                RegexTemplate.NOT_EMPTY,R.string.invalid_NotificationTitle);

        //Add Validation For pet Name
        awesomeValidation.addValidation(this,R.id.edit_text_message,
                RegexTemplate.NOT_EMPTY,R.string.invalid_message);




    }

    public void sendOnChannel1(View v) {



        //check Validation
        if (awesomeValidation.validate()){
            //on success



            String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();

        //Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_one)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();


            Toast.makeText(NptificaSendingActivity.this,"Form Validate Succefully!!!!!!    Notification successfuly!!",Toast.LENGTH_LONG).show();



            notificationManager.notify(1, notification);


        }else {
            Toast.makeText(getApplicationContext()
                    ,"Form Validate Failed!!!!!!", Toast.LENGTH_LONG).show();
        }



    }

    public void sendOnChannel2(View v) {
        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_two)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationManager.notify(2, notification);
    }

}