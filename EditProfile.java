package com.example.team22_fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditProfile extends AppCompatActivity {

    EditText FirstName,LastName,Age,Weight,Height,Gender, EPhone,EName, Email;
    Button updateProfile;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        databaseHelper = new DatabaseHelper(this);


        updateProfile=(Button)findViewById(R.id.save_profile);

        FirstName=(EditText)findViewById(R.id.text1);
        Intent incomingData=getIntent();
        String userFirstName=incomingData.getStringExtra("firstName");
        FirstName.setText(userFirstName);

        LastName=(EditText)findViewById(R.id.text22);
        Intent incomingData1=getIntent();
        String userLastName=incomingData1.getStringExtra("lastName");
        LastName.setText(userLastName);

        Age=(EditText)findViewById(R.id.text3);
        Intent incomingData2=getIntent();
        String userAge=incomingData2.getStringExtra("age");
        Age.setText(userAge);

        Height=(EditText)findViewById(R.id.text4);
        Intent incomingData3=getIntent();
        String userHeight=incomingData3.getStringExtra("height");
        Height.setText(userHeight);

        Weight=(EditText)findViewById(R.id.text5);
        Intent incomingData4=getIntent();
        String userWeight=incomingData4.getStringExtra("weight");
        Weight.setText(userWeight);

        Gender=(EditText)findViewById(R.id.text6);
        Intent incomingData5=getIntent();
        String userGender=incomingData5.getStringExtra("gender");
        Gender.setText(userGender);

        EName=(EditText)findViewById(R.id.text7);
        Intent incomingData6=getIntent();
        String userEName=incomingData6.getStringExtra("ename");
        EName.setText(userEName);

        EPhone=(EditText)findViewById(R.id.text8);
        Intent incomingData7=getIntent();
        String userEPhone=incomingData7.getStringExtra("ephone");
        EPhone.setText(userEPhone);

        Email=(EditText)findViewById(R.id.text9);
        Intent incomingData8=getIntent();
        String userEmail=incomingData8.getStringExtra("userEmail");
        Email.setText(userEmail);

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = FirstName.getText().toString();
                String lname= LastName.getText().toString();
                String age = Age.getText().toString();
                String height = Height.getText().toString();
                String weight = Weight.getText().toString();;
                String gender = Gender.getText().toString();;
                String ename= EName.getText().toString();;
                String ephone= EPhone.getText().toString();
                String email= Email.getText().toString();;

                Log.d("ADebugTag", "Value: " + fname);
                Log.d("ADebugTag", "Value: " + lname);
                Log.d("ADebugTag", "Value: " + height);
                Log.d("ADebugTag", "Value: " + ephone);
                Log.d("ADebugTag", "Value: " + email);





                    Boolean profileUpdate = databaseHelper.updateProfile(fname, lname,age,height,weight,gender,ename, ephone, email);
                    if (profileUpdate == true) {

                        Toast.makeText(EditProfile.this, "Profile was updated", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(EditProfile.this, "Profile was not updated", Toast.LENGTH_LONG).show();
                    }


            }
        });
    }
}