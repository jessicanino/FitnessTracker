package com.example.team22_fitnesstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

public class Bluetooth extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public  static final UUID MY_UUID = UUID.fromString("6277d103-33ef-4652-8bec-005b99c8b113") ;
    private static final int SUCCESS_CONNECT =0 ;
    public static final int MESSAGE_READ = 0;
    public static final int MESSAGE_WRITE = 1;
    public static final int MESSAGE_TOAST = 2;



    ArrayAdapter<String> listAdaptor;
    Button newConnection;
    ListView listConnections;
    BluetoothAdapter blAdapter;
    Set<BluetoothDevice> devicesArray;
    ArrayList<String>pairedDevices;

    IntentFilter filter;
    BroadcastReceiver broadcastReceiver;
    /*Handler mHandler= new Handler() {


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS_CONNECT:
                    MyBluetoothService.ConnectedThread connectedThread = new MyBluetoothService.ConnectedThread((BluetoothSocket)msg.obj);
                    Toast.makeText(getApplicationContext(), "Connect", Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_READ:
                    break;
            }
        }
    };*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        init();
        if (blAdapter == null) {
            Toast.makeText(getApplicationContext(), "No bluetooth detected",Toast.LENGTH_SHORT).show();
            finish();
        } else {
            if (!blAdapter.isEnabled()) {
                turnOnBlueTooth();

            }
            getPairedDevices();
            startDiscovery();


        }
        //mHandler.set(new Handler());
    }

    private void turnOnBlueTooth() {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(intent, 1);
    }

    private void startDiscovery() {
        blAdapter.cancelDiscovery();
        blAdapter.startDiscovery();
    }

    private void getPairedDevices() {
        devicesArray = blAdapter.getBondedDevices();
        if (devicesArray.size() > 0) {
            for(BluetoothDevice device:devicesArray){
                listAdaptor.add(device.getName());
            }
        }
    }

    private void init () {
      //newConnection = (Button) findViewById(R.id.bl_button);
        listConnections = (ListView) findViewById(R.id.listView);
        listConnections.setOnItemClickListener(this);
        listAdaptor = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listConnections.setAdapter(listAdaptor);
        blAdapter = BluetoothAdapter.getDefaultAdapter();
        pairedDevices=new ArrayList<String>();
        filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        broadcastReceiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(BluetoothDevice.ACTION_FOUND.equals(action)){
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    String str=" ";
                    if(listAdaptor.getCount()>0){
                        for(int i=0; i<listAdaptor.getCount();i++){
                            for(int a=0; a<pairedDevices.size();a++){
                                if(device.getName().equals(pairedDevices.get(a))){

                                    str= "(Paired)";

                                    break;
                                }
                            }
                        }
                    }
                    listAdaptor.add(device.getName()+" "+str+" "+"\n"+device.getAddress());
                }
                else if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)){

                }
                else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){

                }
                else if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)){
                    if(blAdapter.getState()==blAdapter.STATE_OFF){
                        turnOnBlueTooth();
                    }
                }
            }
        };
        registerReceiver(broadcastReceiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        registerReceiver(broadcastReceiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(broadcastReceiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);

    }

    @Override
    protected void onPause(){
        super.onPause();
        unregisterReceiver(broadcastReceiver);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_CANCELED){
            Toast.makeText(getApplicationContext(), "Bluetooth is not enabled. Please enable bluetooth to continue", Toast.LENGTH_SHORT).show();
            finish();

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(blAdapter.isDiscovering()){
            blAdapter.cancelDiscovery();
        }
        if(listAdaptor.getItem(position).contains("Paired")){
            Object[] object=devicesArray.toArray();
            BluetoothDevice selectedDevice=(BluetoothDevice)object[position] ;
            ConnectThread connect =new ConnectThread(selectedDevice);
            connect.start();
            Toast.makeText(getApplicationContext(),"Device is paired",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Device is not paired",Toast.LENGTH_SHORT).show();
        }
    }
    private class ConnectThread extends Thread {


        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket
            // because mmSocket is final.
            BluetoothSocket tmp = null;
            mmDevice = device;

            try {
                // Get a BluetoothSocket to connect with the given BluetoothDevice.
                // MY_UUID is the app's UUID string, also used in the server code.
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {

            }
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it otherwise slows down the connection.
            blAdapter.cancelDiscovery();

            try {
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and return.
                try {
                    mmSocket.close();
                } catch (IOException closeException) {

                }
                return;
            }

            // The connection attempt succeeded. Perform work associated with
            // the connection in a separate thread.
            manageMyConnectedSocket(mmSocket);
            // mHandler.get().obtainMessage(SUCCESS_CONNECT);
        }
        private void manageMyConnectedSocket(BluetoothSocket mmSocket2){

        }
        // Closes the client socket and causes the thread to finish.
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {

            }
        }
    }
    public class MyBluetoothService {
        private static final String TAG = "MY_APP_DEBUG_TAG";
        private Handler handler; // handler that gets info from Bluetooth service

        // Defines several constants used when transmitting messages between the
        // service and the UI.

        private class ConnectedThread extends Thread {
            private final BluetoothSocket mmSocket;
            private final InputStream mmInStream;
            private final OutputStream mmOutStream;
            private byte[] mmBuffer; // mmBuffer store for the stream

            public ConnectedThread(BluetoothSocket socket) {
                mmSocket = socket;
                InputStream tmpIn = null;
                OutputStream tmpOut = null;

                // Get the input and output streams; using temp objects because
                // member streams are final.
                try {
                    tmpIn = socket.getInputStream();
                } catch (IOException e) {
                    Log.e(TAG, "Error occurred when creating input stream", e);
                }
                try {
                    tmpOut = socket.getOutputStream();
                } catch (IOException e) {
                    Log.e(TAG, "Error occurred when creating output stream", e);
                }

                mmInStream = tmpIn;
                mmOutStream = tmpOut;
            }

            public void run() {
                mmBuffer = new byte[1024];
                int numBytes; // bytes returned from read()

                // Keep listening to the InputStream until an exception occurs.
                while (true) {
                    try {
                        // Read from the InputStream.
                        numBytes = mmInStream.read(mmBuffer);
                        // Send the obtained bytes to the UI activity.
                        Message readMsg = handler.obtainMessage(
                                MESSAGE_READ, numBytes, -1,
                                mmBuffer);
                        readMsg.sendToTarget();
                    } catch (IOException e) {
                        Log.d(TAG, "Input stream was disconnected", e);
                        break;
                    }
                }
            }

            // Call this from the main activity to send data to the remote device.
            public void write(byte[] bytes) {
                try {
                    mmOutStream.write(bytes);

                    // Share the sent message with the UI activity.
                    Message writtenMsg = handler.obtainMessage(
                            MESSAGE_WRITE, -1, -1, mmBuffer);
                    writtenMsg.sendToTarget();
                } catch (IOException e) {
                    Log.e(TAG, "Error occurred when sending data", e);

                    // Send a failure message back to the activity.
                    Message writeErrorMsg =
                            handler.obtainMessage(MESSAGE_TOAST);
                    Bundle bundle = new Bundle();
                    bundle.putString("toast",
                            "Couldn't send data to the other device");
                    writeErrorMsg.setData(bundle);
                    handler.sendMessage(writeErrorMsg);
                }
            }

            // Call this method from the main activity to shut down the connection.
            public void cancel() {
                try {
                    mmSocket.close();
                } catch (IOException e) {
                    Log.e(TAG, "Could not close the connect socket", e);
                }
            }
        }
    }
}