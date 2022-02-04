package com.example.team22_fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@SuppressLint("MissingPermission")
public class BLE extends AppCompatActivity {
    private Handler handler = new Handler();
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner LeScanner;
    private ScanSettings scanSettings;
    private BluetoothGatt bluetoothGatt;
    private List<ScanFilter> scanFilters;
    private final UUID my_uuid = UUID.fromString("ed825785-893a-4451-a543-ee59314d81ed");


    private static final long scanPeriod = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble);

        //Handler handler = new Handler();
        //check if BLE is supported on device
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE is not supported on this device", Toast.LENGTH_LONG).show();
        }

        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();


    }


    @Override
    protected void onResume() {
        //enable bluetooth if not on
        super.onResume();
        //enable bluetooth

        if (!bluetoothAdapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, 1);
        } else {
            if (Build.VERSION.SDK_INT >= 21) {
                LeScanner = bluetoothAdapter.getBluetoothLeScanner();
                scanSettings = new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).build();
                scanFilters = new ArrayList<ScanFilter>();
            }
            scanDevices(true);
        }
    }

    @Override
    protected void onPause() {

        super.onPause();
        if (!bluetoothAdapter.isEnabled()) {
            scanDevices(false);
        }

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (bluetoothGatt == null) {
            return;
        }
        bluetoothGatt.close();
       // bluetoothGatt = null;

    }

    @Override
    protected void onActivityResult(int request, int result, Intent data) {

        super.onActivityResult(request, result, data);
        if (request == 1) {
            if (result == Activity.RESULT_CANCELED) {
                finish();
                return;

            }
        }
    }


    private void scanDevices(final boolean on) {
        if (on) {
            handler.postDelayed(new Runnable() {


                @Override
                public void run() {
                    if (Build.VERSION.SDK_INT < 21) {

                        bluetoothAdapter.stopLeScan(scanLeCallback);
                    } else {
                        LeScanner.stopScan(scanCallback);
                    }
                }

            }, scanPeriod);

            if (Build.VERSION.SDK_INT < 21) {
                bluetoothAdapter.startLeScan(scanLeCallback);

            } else {
                LeScanner.startScan(scanFilters, scanSettings, scanCallback);
            }
            if (Build.VERSION.SDK_INT < 21) {
                bluetoothAdapter.stopLeScan(scanLeCallback);

            } else {
                LeScanner.stopScan(scanCallback);
            }
        }


    }

    private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            BluetoothDevice BLEdevice = result.getDevice();
            connectToDevice(BLEdevice);
        }

        @Override
        public void onBatchScanResults(List<ScanResult> availableDevices) {
            for (ScanResult scanResult : availableDevices) {
            }
            //list devices

        }

        @Override
        public void onScanFailed(int error) {
            //message scan failed
        }
    };

    private BluetoothAdapter.LeScanCallback scanLeCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    connectToDevice(device);
                }
            });
        }
    };

    public void connectToDevice(BluetoothDevice device) {
        if (bluetoothGatt == null) {

            bluetoothGatt = device.connectGatt(this, false, bluetoothGattCallback);
            scanDevices(false);
        }
    }

    private final BluetoothGattCallback bluetoothGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newStatus) {
            super.onConnectionStateChange(gatt, status, newStatus);
            switch (newStatus) {
                case BluetoothProfile.STATE_CONNECTED:

                    gatt.discoverServices();
                    break;
                case BluetoothProfile.STATE_DISCONNECTING:
                    break;
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            List<BluetoothGattService> bluetoothServiceList = gatt.getServices();

            //BluetoothGattCharacteristic heartRate=bluetoothServiceList.get(2).getCharacteristic;
            gatt.readCharacteristic(bluetoothServiceList.get(1).getCharacteristic(my_uuid));

        }

        public void onCharateristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {

            gatt.disconnect();
        }
    };

}