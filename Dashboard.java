package com.example.team22_fitnesstracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.BreakIterator;


public class Dashboard extends AppCompatActivity {

    EditText Email;
    String EmailHolder;

    SQLiteDatabase sqLiteDatabaseObj;
    DatabaseHelper sqLiteHelper;
    Cursor cursor;


    public static String FirstName;
    public static String LastName;
    public static String Age;
    public static String Height;
    public static String Weight;
    public static String Gender;
    public static String EName;
    public static String EPhone;
    public static String UserEmail;

    String FNameHolder;
    TextView FName, FNameR;



    TextView ECPhone;
    private static final int REQUEST_CALL=1;


    CardView heartRate;
    CardView stepCount;
    CardView caloriesBurned;
    CardView faqs;
    CardView profile;
    CardView logout;
    CardView econtact;
    CardView blueetooth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        FName=(TextView)findViewById(R.id.textView1);
        Intent incomingData=getIntent();
        String userFirstName=incomingData.getStringExtra("fname");
        FName.setText(userFirstName);

      /*  FNameR=(TextView)findViewById(R.id.textView1);
        Intent intent=getIntent();
        FNameHolder= intent.getStringExtra(Register.UserName);
        FNameR.setText(FNameR.getText().toString()+FNameHolder);*/


        sqLiteHelper = new DatabaseHelper(this);
       /* FName=(TextView)findViewById(R.id.textView1);
        Intent intent=getIntent();
        FNameHolder=intent.getStringExtra(MainActivity.UserName);
        // Setting up received email to TextView.
        FName.setText(FName.getText().toString()+ FNameHolder);*/

        heartRate= findViewById(R.id.heart_rate);
        stepCount= findViewById(R.id.step_count);
        caloriesBurned= findViewById(R.id.calories_burned);
        faqs= findViewById(R.id.faqs);
        profile= findViewById(R.id.profile);
        logout= findViewById(R.id.logout);
        econtact=findViewById(R.id.emergency_contact);
        blueetooth=findViewById(R.id.bluetooth);

        Email = (EditText) findViewById(R.id.edit_email);
        String fname = FName.getText().toString();
        heartRate.setOnClickListener(new View.OnClickListener (){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, HeartRate.class);
                startActivity(intent);

            }
        });

        stepCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, StepCount.class);
                startActivity(intent);

            }
        });
        caloriesBurned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, CaloriesBurned.class);
                startActivity(intent);

            }
        });
        faqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, FAQs.class);
                startActivity(intent);

            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();
                cursor = sqLiteDatabaseObj.query(sqLiteHelper.TABLE_NAME, null, " " + DatabaseHelper.Table_Column_1_FName + "=?", new String[]{fname}, null, null, null);



                // Adding search email query to cursor.
               //cursor = sqLiteDatabaseObj.query(sqLiteHelper.TABLE_NAME, null, " " + DatabaseHelper.Table_Column_9_Email + "=?", new String[]{EmailHolder}, null, null, null);

                while (cursor.moveToNext()) {

                    if (cursor.isFirst()) {

                        cursor.moveToFirst();

                        // Storing Password associated with entered email.


                        FirstName = cursor.getString(1);
                        LastName = cursor.getString(2);
                        Age = cursor.getString(3);
                        Height = cursor.getString(4);
                        Weight = cursor.getString(5);
                        Gender = cursor.getString(6);
                        EName = cursor.getString(7);
                        EPhone = cursor.getString(8);
                        UserEmail = cursor.getString(9);

                        // Closing cursor.
                        cursor.close();
                    }
                }



                Intent intent2 = new Intent(com.example.team22_fitnesstracker.Dashboard.this, com.example.team22_fitnesstracker.Profile.class);
                intent2.putExtra("firstName",FirstName);
                intent2.putExtra("lastName",LastName);
                intent2.putExtra("age",Age);
                intent2.putExtra("height",Height);
                intent2.putExtra("weight",Weight);
                intent2.putExtra("gender",Gender);
                intent2.putExtra("ename",EName);
                intent2.putExtra("ephone",EPhone);
                intent2.putExtra("userEmail",UserEmail);
                startActivity(intent2);


               // Intent intent = new Intent(Dashboard.this, Profile.class);
               // startActivity(intent);


            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

                Toast.makeText(Dashboard.this,"Log Out Successful", Toast.LENGTH_LONG).show();

            }
        });

        econtact.setOnClickListener(new View.OnClickListener () {
                                        @Override


                                        public void onClick (View v){
                                            sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();
                                            cursor = sqLiteDatabaseObj.query(sqLiteHelper.TABLE_NAME, null, " " + DatabaseHelper.Table_Column_1_FName + "=?", new String[]{fname}, null, null, null);


                                            // Adding search email query to cursor.
                                            //cursor = sqLiteDatabaseObj.query(sqLiteHelper.TABLE_NAME, null, " " + DatabaseHelper.Table_Column_9_Email + "=?", new String[]{EmailHolder}, null, null, null);

                                            while (cursor.moveToNext()) {

                                                if (cursor.isFirst()) {

                                                    cursor.moveToFirst();


                                                    EPhone = cursor.getString(11);


                                                    // Closing cursor.
                                                    cursor.close();
                                                }
                                            }

                                            Intent incomingData = getIntent();
                                            String userEPhone = incomingData.getStringExtra("ephone");

                                            String dial="tel:"+userEPhone;
                                            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                                           // makePhoneCall();
        }
       /* private void makePhoneCall(){
            if(ContextCompat.checkSelfPermission(Dashboard.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(Dashboard.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
            }else{

                String dial="tel:"+userEPhone;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }*/
      /*  @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
            super.onRequestPermissionsResult(requestCode,permissions,grantResults);
            if(requestCode==REQUEST_CALL){
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    makePhoneCall();
                }else{
                    Toast.makeText(this,"Permission DENIED",Toast.LENGTH_SHORT).show();
                }
            }

*/

                                        });


        blueetooth.setOnClickListener(new View.OnClickListener (){
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(Dashboard.this, Bluetooth.class);
               startActivity(intent);

            }
        });



    }


}