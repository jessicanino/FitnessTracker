package com.example.team22_fitnesstracker;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.bluetooth.BluetoothAdapter;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Bluetooth1 extends AppCompatActivity {

    BluetoothAdapter bluetoothAdapter;
    BluetoothDevice Device;
    // BluetoothSocket Socket;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_bluetooth);

        //check if device supports bluetooth
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(Bluetooth1.this, "This device does not support bluetooth", Toast.LENGTH_LONG).show();
        }

        //enable bluetooth
        if (!bluetoothAdapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, 1);
        }

      /*  Intent discover = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discover.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 360);
        startActivity(discover);*/

        //get bluetooth module from microcontroller
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        //BluetoothDevice microDevice;
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {

                Device = device;
            }
            ConnectThread connectThread = new ConnectThread(Device);
            connectThread.start();

        }



    }



    //connected thread
    private class ConnectThread extends Thread {


        private final BluetoothSocket Socket;
        private final BluetoothDevice Device;
        private final UUID MY_UUID = UUID.fromString("ed825785-893a-4451-a543-ee59314d81ed");

       @SuppressLint("MissingPermission")
        public ConnectThread(BluetoothDevice device) {
            String NAME= null;

            BluetoothSocket temp = null;

            Device = device;
            try {

               temp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
            }
            Socket = temp;


        }


        @SuppressLint("MissingPermission")
        public void run() {
            bluetoothAdapter.cancelDiscovery();
            try {
                Socket.connect();
            } catch (IOException connectException) {
                try {
                    Socket.close();
                } catch (IOException closeException) {
                }
                return;
            }
            ConnectedThread connectedThread;
            connectedThread = new ConnectedThread(Socket);
            connectedThread.start();



            /*sharedpreference
            SharedPreferences stepCountData = getApplication().getSharedPreferences("Step Count", MODE_PRIVATE);
            Set<String>set = stepCountData.getStringSet("Step Count", );
            ArrayList<String> stepCountList = null;
            stepCountList.addAll(set);*/


        }


        public void cancel() {
            try {
                Socket.close();
            } catch (IOException e) {
            }

        }

    }







        private class ConnectedThread extends Thread {
            private final BluetoothSocket Socket;
            private final InputStream inputStream;
            private final OutputStream outputStream;


            public ConnectedThread(BluetoothSocket socket) {
                Socket = socket;
                InputStream tempInput = null;
                OutputStream tempOutput = null;


                try {
                    tempInput = socket.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    tempOutput = socket.getOutputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                inputStream = tempInput;
                outputStream = tempOutput;
            }

            public void run() {
              byte[]  buffer = new byte[1024];
                int bytes=0;

                while (true) {
                    try {
                       //bytes += inputStream.read(buffer,bytes,buffer.length-bytes);
                        bytes = inputStream.read(buffer);
                        Message readM = handler.obtainMessage(1, bytes, -1, buffer);
                        readM.sendToTarget();
                    } catch (IOException e) {
                        Toast.makeText(Bluetooth1.this, "Device was disconnected", Toast.LENGTH_LONG).show();
                        break;
                    }
                }
            }

            /*  public void write(byte[] bytes){
                  try{
                      outputStream.write(bytes);
                  }catch (IOException e) {
                      e.printStackTrace();
                  }
              }*/
        public void cancel() {
            try {
                Socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }




   // @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
                    byte[] writeToBuffer = (byte[]) msg.obj;
                    int beginMsg;
                    int endMsg;
                    beginMsg = (int) msg.arg1;
                    endMsg = (int) msg.arg2;

                    switch (msg.what) {
                        case 1:
                            String writeMsg = new String(writeToBuffer);
                    writeMsg = writeMsg.substring(beginMsg, endMsg);

                    Intent sentToStepCount = new Intent(Bluetooth1.this,StepCount.class);
                    sentToStepCount.putExtra("StepCount",writeMsg);
                    startActivity(sentToStepCount);
                    break;

            }

        }


    };


}
