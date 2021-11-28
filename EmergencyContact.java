package com.example.team22_fitnesstracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EmergencyContact extends AppCompatActivity {
    public static String EPhone;
    TextView ECPhone;
    private static final int REQUEST_CALL=1;
   // private
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact);

        ECPhone=(TextView)findViewById(R.id.textView1);
        Intent incomingData=getIntent();
        String userEPhone=incomingData.getStringExtra("ephone");
        ECPhone.setText(userEPhone);


        ImageView imageCall = findViewById(R.id.image_call);
        imageCall.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                makePhoneCall();
            }
        });
    }
    private void makePhoneCall(){
        if(ContextCompat.checkSelfPermission(EmergencyContact.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(EmergencyContact.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
        }else{
            String dial="tel:"+ECPhone;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if(requestCode==REQUEST_CALL){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            }else{
                Toast.makeText(this,"Permission DENIED",Toast.LENGTH_SHORT).show();
            }
        }
    }
}