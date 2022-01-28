package com.example.team22_fitnesstracker;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText Email, Password, FName, LName, Age, Height, Weight, Gender, Ename, Ephone, CPassword ;
    Button Register, Login;
    String  EmailHolder, PasswordHolder, FNameHolder, LNameHolder, AgeHolder, HeightHolder, WeightHolder, GenderHolder, EnameHolder, EphoneHolder, CPasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder ;
    DatabaseHelper sqLiteHelper;
    Cursor cursor;
    String F_Result = "Not_Found";
    public static final String UserName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Register = (Button)findViewById(R.id.button_register);
        Login =(Button)findViewById(R.id.button_login);

        Email = (EditText)findViewById(R.id.edit_email);
        Password = (EditText)findViewById(R.id.edit_password);
        FName = (EditText)findViewById(R.id.edit_fname);
        LName = (EditText)findViewById(R.id.edit_lname);
        Age = (EditText)findViewById(R.id.edit_age);
        Height = (EditText)findViewById(R.id.edit_height);
        Weight = (EditText)findViewById(R.id.edit_weight);
        Gender = (EditText)findViewById(R.id.edit_gender);
        Ename = (EditText)findViewById(R.id.edit_ec_name);
        Ephone = (EditText)findViewById(R.id.edit_ec_number);
        CPassword =(EditText)findViewById(R.id.edit_confirm_password);

        sqLiteHelper = new DatabaseHelper(this);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Opening new user registration activity using intent on button click.
                Intent intent = new Intent(com.example.team22_fitnesstracker.Register.this, MainActivity.class);
                startActivity(intent);

            }
        });
 
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

         
                SQLiteDataBaseBuild();

              
                SQLiteTableBuild();

       
                CheckEditTextStatus();

              
                CheckingEmailAlreadyExistsOrNot();

               
                EmptyEditTextAfterDataInsert();


            }
        });

    }



    public void SQLiteDataBaseBuild(){

        sqLiteDatabaseObj = openOrCreateDatabase(DatabaseHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    public void SQLiteTableBuild() {

        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + DatabaseHelper.TABLE_NAME + "(" + DatabaseHelper.Table_Column_ID + " PRIMARY KEY AUTOINCREMENT NOT NULL, " +DatabaseHelper.Table_Column_1_FName+" VARCHAR, "+DatabaseHelper.Table_Column_2_LName+" VARCHAR, "+DatabaseHelper.Table_Column_3_Age+" VARCHAR, "+DatabaseHelper.Table_Column_4_Height+" VARCHAR, "+DatabaseHelper.Table_Column_5_Weight+" VARCHAR,  "+DatabaseHelper.Table_Column_6_Gender+" VARCHAR, "+DatabaseHelper.Table_Column_7_Ename+" VARCHAR,"+DatabaseHelper.Table_Column_8_Ephone+" VARCHAR,"+DatabaseHelper.Table_Column_9_Email+" VARCHAR, "+DatabaseHelper.Table_Column_10_Password+" VARCHAR,"+DatabaseHelper.Table_Column_11_CPassword+" VARCHAR);");

    }

    public void InsertDataIntoSQLiteDatabase(){

       
        if(EditTextEmptyHolder == true)
        {

            SQLiteDataBaseQueryHolder = "INSERT INTO "+DatabaseHelper.TABLE_NAME+"  (fname,lname,age, height, weight, gender,ename,ephone,email,password,cpassword ) VALUES('"+FNameHolder+"','"+LNameHolder+"','"+AgeHolder+"','"+HeightHolder+"','"+WeightHolder+"', '"+GenderHolder+"', '"+EnameHolder+"','"+EphoneHolder+"','"+EmailHolder+"', '"+PasswordHolder+"','"+CPasswordHolder+"');";

      
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);

            sqLiteDatabaseObj.close();

       
            Toast.makeText(Register.this,"You Have Registered Successfully", Toast.LENGTH_LONG).show();
            //Intent intent = new Intent(Register.this, Dashboard.class);

            // Sending Email to Dashboard Activity using intent.
            //intent.putExtra(UserName, FNameHolder);

            // startActivity(intent);
        }
        // This block will execute if any of the registration EditText is empty.
        else {

            // Printing toast message if any of EditText is empty.
            Toast.makeText(Register.this,"Missing Required Fields.", Toast.LENGTH_LONG).show();

        }

    }

    // Empty edittext after done inserting process method.
    public void EmptyEditTextAfterDataInsert(){

        FName.getText().clear();
        LName.getText().clear();
        Age.getText().clear();
        Height.getText().clear();
        Weight.getText().clear();
        Gender.getText().clear();
        Ename.getText().clear();
        Ephone.getText().clear();
        Email.getText().clear();
        Password.getText().clear();
        CPassword.getText().clear();

    }

    // Method to check EditText is empty or Not.
    public void CheckEditTextStatus(){

        // Getting value from All EditText and storing into String Variables.
        FNameHolder = FName.getText().toString() ;
        LNameHolder = LName.getText().toString() ;
        AgeHolder = Age.getText().toString() ;
        HeightHolder = Height.getText().toString() ;
        WeightHolder= Weight.getText().toString();
        GenderHolder=Gender.getText().toString();
        EnameHolder=Ename.getText().toString();
        EphoneHolder=Ephone.getText().toString();
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();
        CPasswordHolder=CPassword.getText().toString();

        if(TextUtils.isEmpty(FNameHolder) || TextUtils.isEmpty(LNameHolder) ||TextUtils.isEmpty(AgeHolder) ||TextUtils.isEmpty(HeightHolder) ||TextUtils.isEmpty(WeightHolder) ||TextUtils.isEmpty(GenderHolder) ||TextUtils.isEmpty(EmailHolder) ||TextUtils.isEmpty(EnameHolder) ||TextUtils.isEmpty(EphoneHolder) ||  TextUtils.isEmpty(PasswordHolder) || TextUtils.isEmpty(CPasswordHolder)){

            EditTextEmptyHolder = false ;

        }
        else {

            EditTextEmptyHolder = true ;
        }
    }


    public void CheckingEmailAlreadyExistsOrNot(){

        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

    
        cursor = sqLiteDatabaseObj.query(DatabaseHelper.TABLE_NAME, null, " " + DatabaseHelper.Table_Column_9_Email + "=?", new String[]{EmailHolder}, null, null, null);

        while (cursor.moveToNext()) {

            if (cursor.isFirst()) {

                cursor.moveToFirst();

   
                F_Result = "Email Found";

                cursor.close();
            }
        }

      
        CheckFinalResult();

    }



    public void CheckFinalResult(){

        if(F_Result.equalsIgnoreCase("Email Found"))
        {

            
            Toast.makeText(Register.this,"Email Already Exists",Toast.LENGTH_LONG).show();

        }
        else {

   
            InsertDataIntoSQLiteDatabase();

        }

        F_Result = "Not_Found" ;

    }



}
