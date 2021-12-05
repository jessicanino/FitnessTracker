package com.example.team22_fitnesstracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.team22_fitnesstracker.DatabaseHelper;
import com.example.team22_fitnesstracker.R;

public class MainActivity extends AppCompatActivity {


    Button LogInButton, RegisterButton;
    EditText Email, Password;
    String EmailHolder, PasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    DatabaseHelper sqLiteHelper;
    Cursor cursor;
    String TempPassword = "NOT_FOUND";
    public static String UserName;
    public static String FirstName;
    public static String LastName;
    public static String Age;
    public static String Height;
    public static String Weight;
    public static String Gender;
    public static String EName;
    public static String EPhone;
    public static String UserEmail;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogInButton = (Button) findViewById(R.id.button_login);

        RegisterButton = (Button) findViewById(R.id.button_register);

        Email = (EditText) findViewById(R.id.edit_email);
        Password = (EditText) findViewById(R.id.edit_password);



        sqLiteHelper = new DatabaseHelper(this);

        //Adding click listener to log in button.
        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Calling EditText is empty or no method.
                CheckEditTextStatus();

                // Calling login method.
                LoginFunction();


            }
        });

        // Adding click listener to register button.
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Opening new user registration activity using intent on button click.
                Intent intent = new Intent(com.example.team22_fitnesstracker.MainActivity.this, Register.class);
                startActivity(intent);

            }
        });

    }
    public void EmptyEditTextAfterDataInsert(){


        Email.getText().clear();
        Password.getText().clear();

    }

    // Login function starts from here.
    @SuppressLint("Range")
    public void LoginFunction() {

        if (EditTextEmptyHolder) {

            // Opening SQLite database write permission.
            sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();


            // Adding search email query to cursor.
            cursor = sqLiteDatabaseObj.query(sqLiteHelper.TABLE_NAME, null, " " + DatabaseHelper.Table_Column_9_Email + "=?", new String[]{EmailHolder}, null, null, null);

            while (cursor.moveToNext()) {

                if (cursor.isFirst()) {

                    cursor.moveToFirst();

                    // Storing Password associated with entered email.
                    TempPassword = cursor.getString(cursor.getColumnIndex(DatabaseHelper.Table_Column_10_Password));
                    UserName=cursor.getString(1);
                    FirstName=cursor.getString(1);
                    LastName=cursor.getString(2);
                    Age=cursor.getString(3);
                    Height=cursor.getString(4);
                    Weight=cursor.getString(5);
                    Gender=cursor.getString(6);
                    EName=cursor.getString(7);
                    EPhone=cursor.getString(8);
                    UserEmail=cursor.getString(9);

                    // Closing cursor.
                    cursor.close();
                }
            }

            // Calling method to check final result ..
            CheckFinalResult();

        } else {

            //If any of login EditText empty then this block will be executed.
            Toast.makeText(com.example.team22_fitnesstracker.MainActivity.this, "Please Enter UserName or Password.", Toast.LENGTH_LONG).show();

        }
        EmptyEditTextAfterDataInsert();

    }

    // Checking EditText is empty or not.
    public void CheckEditTextStatus() {

        // Getting value from All EditText and storing into String Variables.
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();



        // Checking EditText is empty or no using TextUtils.
        if (TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)) {

            EditTextEmptyHolder = false;

        } else {

            EditTextEmptyHolder = true;
        }
    }

    // Checking entered password from SQLite database email associated password.
    public void CheckFinalResult() {

        if (TempPassword.equalsIgnoreCase(PasswordHolder)) {

            Toast.makeText(com.example.team22_fitnesstracker.MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();

            // Going to Dashboard activity after login success message.
            Intent intent = new Intent(com.example.team22_fitnesstracker.MainActivity.this, com.example.team22_fitnesstracker.Dashboard.class);

            // Sending Email to Dashboard Activity using intent.
            intent.putExtra("fname",UserName);
            intent.putExtra("ephone", EPhone);


            startActivity(intent);
           /* Intent intent2 = new Intent(com.example.team22_fitnesstracker.MainActivity.this, com.example.team22_fitnesstracker.Profile.class);
            intent2.putExtra("firstName",FirstName);
            intent2.putExtra("lastName",LastName);
            intent2.putExtra("age",Age);
            intent2.putExtra("height",Height);
            intent2.putExtra("weight",Weight);
            intent2.putExtra("gender",Gender);
            intent2.putExtra("ename",EName);
            intent2.putExtra("ephone",EPhone);
            intent2.putExtra("userEmail",UserEmail);
            startActivity(intent2);*/


        } else {

            Toast.makeText(com.example.team22_fitnesstracker.MainActivity.this, "UserName or Password is incorrect. Please Try Again.", Toast.LENGTH_LONG).show();

        }
        TempPassword = "NOT_FOUND";

    }

}
