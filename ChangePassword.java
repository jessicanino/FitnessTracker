package com.example.team22_fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChangePassword extends AppCompatActivity {

    TextView userEmail;
    EditText newPassword, confirmNewPassword;
    Button resetButton;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        //userEmail= (TextView) findViewById(R.id.userEmail_reset);
        newPassword= (EditText) findViewById(R.id.change_password);
        confirmNewPassword= (EditText) findViewById(R.id.confirm_changed_password);
        resetButton= (Button) findViewById(R.id.button_change_password);
        databaseHelper = new DatabaseHelper(this);



        Intent intent = getIntent();
        String userEmailFrom= intent.getStringExtra("email");
        Log.d("ADebugTag", "Value: " + userEmailFrom);
        //userEmail.setText(userEmailFrom);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmailFrom;
                String password = newPassword.getText().toString();
                String checkPassword = confirmNewPassword.getText().toString();
                if(password.equals(checkPassword)) {


                    Boolean passwordUpdate = databaseHelper.updatePassword(email,password);
                    if (passwordUpdate == true) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(ChangePassword.this, "Password was updated", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ChangePassword.this, "Password was not updated", Toast.LENGTH_LONG).show();
                    }
                }
                    else {
                        Toast.makeText(ChangePassword.this, "Passwords do not match", Toast.LENGTH_LONG).show();
                    }

            }
        });

    }
}