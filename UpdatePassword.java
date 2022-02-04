package com.example.team22_fitnesstracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class UpdatePassword extends AppCompatActivity {

    EditText userEmail;
    Button findUserBtn;
    DatabaseHelper databaseHelper;

    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder ;
    DatabaseHelper sqLiteHelper;
    Cursor cursor;
    String  EmailHolder;
    String F_Result = "Not_Found";
    String Email;
    //String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        userEmail=(EditText) findViewById(R.id.userEmail_reset);
        findUserBtn=(Button)findViewById(R.id.button_find_user);

        sqLiteHelper = new DatabaseHelper(this);
        databaseHelper= new DatabaseHelper(this);

        findUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //email = userEmail.getText().toString();
                 Email= userEmail.getText().toString();


                sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

                // Adding search email query to cursor.
                cursor = sqLiteDatabaseObj.query(DatabaseHelper.TABLE_NAME, null, " " + DatabaseHelper.Table_Column_9_Email + "=?", new String[]{Email}, null, null, null);

                while (cursor.moveToNext()) {

                    if (cursor.isFirst()) {

                        cursor.moveToFirst();

                        // If Email is already exists then Result variable value set as Email Found.
                        F_Result = "Email Found";

                        // Closing cursor.
                        cursor.close();
                    }
                }

                if(F_Result.equalsIgnoreCase("Email Found"))
                {

                    // If email is exists then toast msg will display.
                    Toast.makeText(UpdatePassword.this,"User Found",Toast.LENGTH_LONG).show();

                    Log.d("ADebugTag", "Value: " + Email);
                    Intent intent = new Intent(UpdatePassword.this, ChangePassword.class);
                    intent.putExtra("email",Email);
                    startActivity(intent);

                }
                else {

                    Toast.makeText(UpdatePassword.this,"User Not Found",Toast.LENGTH_LONG).show();

                }



            }
        });

    }


}