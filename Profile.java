package com.example.team22_fitnesstracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends AppCompatActivity {


    TextView FirstName,LastName,Age,Weight,Height,Gender, EPhone,EName, Email;
    Button EditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FirstName=(TextView)findViewById(R.id.text1);
        Intent incomingData=getIntent();
        String userFirstName=incomingData.getStringExtra("firstName");
        FirstName.setText(userFirstName);

        LastName=(TextView)findViewById(R.id.text22);
        Intent incomingData1=getIntent();
        String userLastName=incomingData1.getStringExtra("lastName");
        LastName.setText(userLastName);

        Age=(TextView)findViewById(R.id.text3);
        Intent incomingData2=getIntent();
        String userAge=incomingData2.getStringExtra("age");
        Age.setText(userAge);

        Height=(TextView)findViewById(R.id.text4);
        Intent incomingData3=getIntent();
        String userHeight=incomingData3.getStringExtra("height");
        Height.setText(userHeight);

        Weight=(TextView)findViewById(R.id.text5);
        Intent incomingData4=getIntent();
        String userWeight=incomingData4.getStringExtra("weight");
        Weight.setText(userWeight);

        Gender=(TextView)findViewById(R.id.text6);
        Intent incomingData5=getIntent();
        String userGender=incomingData5.getStringExtra("gender");
        Gender.setText(userGender);

        EName=(TextView)findViewById(R.id.text7);
        Intent incomingData6=getIntent();
        String userEName=incomingData6.getStringExtra("ename");
        EName.setText(userEName);

        EPhone=(TextView)findViewById(R.id.text8);
        Intent incomingData7=getIntent();
        String userEPhone=incomingData7.getStringExtra("ephone");
        EPhone.setText(userEPhone);

        Email=(TextView)findViewById(R.id.text9);
        Intent incomingData8=getIntent();
        String userEmail=incomingData8.getStringExtra("userEmail");
        Email.setText(userEmail);

        EditProfile=(Button) findViewById(R.id.edit_profile);
        EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, EditProfile.class);
                startActivity(intent);

                Intent sendPr0file = new Intent(com.example.team22_fitnesstracker.Profile.this, com.example.team22_fitnesstracker.EditProfile.class);
                sendPr0file.putExtra("firstName",userFirstName);
                sendPr0file.putExtra("lastName", userLastName);
                sendPr0file.putExtra("age", userAge);
                sendPr0file.putExtra("height", userHeight);
                sendPr0file.putExtra("weight", userWeight);
                sendPr0file.putExtra("gender", userGender);
                sendPr0file.putExtra("ename", userEName);
                sendPr0file.putExtra("ephone", userEPhone);
                sendPr0file.putExtra("userEmail", userEmail);
                startActivity(sendPr0file);

            }
        });

    }
}




