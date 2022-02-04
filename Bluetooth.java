package com.example.team22_fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.companion.BluetoothLeDeviceFilter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.bluetooth.BluetoothAdapter;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class Bluetooth extends AppCompatActivity {


    //BluetoothGatt bluetoothGatt = device.connectGatt(this,false,gettCallback)
    BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
    private boolean scanningDevices;
    private Handler handler = new Handler();

    private static final long scanningPeriod = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);


    }

    @SuppressLint("MissingPermission")
    private void scanDevices(){
        if (!scanningDevices) {
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    scanningDevices = false;
                    bluetoothLeScanner.stopScan(scanCallback);
                }
            }, scanningPeriod);
            scanningDevices = true;
           bluetoothLeScanner.stopScan(scanCallback);
        }
                else{
                scanningDevices=false;
                bluetoothLeScanner.stopScan(scanCallback);
            }
            }


          private ScanCallback scanCallback = new ScanCallback() {
                @Override
                public void onScanResult(int callbackType, ScanResult result) {
                    super.onScanResult(callbackType, result);


                }
            };
        }






