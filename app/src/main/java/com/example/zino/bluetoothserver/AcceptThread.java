package com.example.zino.bluetoothserver;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

/*
 * Created by zino on 2016-07-04.
 */
public class AcceptThread extends Thread {
    String TAG=this.getClass().getName();
    Context context;
    private final BluetoothServerSocket mmServerSocket;
    BluetoothAdapter bluetoothAdapter;

    public AcceptThread(Context context , BluetoothAdapter bluetoothAdapter) {
        this.context=context;
        this.bluetoothAdapter=bluetoothAdapter;

        // Use a temporary object that is later assigned to mmServerSocket,
        // because mmServerSocket is final
        BluetoothServerSocket tmp = null;
        String NAME="my device";
        UUID MY_UUID;

        try {
            // MY_UUID is the app's UUID string, also used by the client code
            //MY_UUID=UUID.randomUUID();
            MY_UUID = UUID.fromString("e90926d6-5a20-40eb-b788-f22b23aadbcd");

            Log.d(TAG, MY_UUID.toString()); //이 정보를 클라이언트가 알아야 서로 통신이 가능하다!!

            tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
        } catch (IOException e) { }
        mmServerSocket = tmp;
    }

    public void run() {
        BluetoothSocket socket = null;
        // Keep listening until exception occurs or a socket is returned
        while (true) {
            try {
                socket = mmServerSocket.accept(); //클라이언트의 접속을 처리하는 메서드!!
                Toast.makeText(context, "클라이언트 접속 감지함!!", Toast.LENGTH_LONG).show();

            } catch (IOException e) {
                break;
            }
            // If a connection was accepted
            if (socket != null) {
                // Do work to manage the connection (in a separate thread)
                //manageConnectedSocket(socket);
                try {
                    mmServerSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    /** Will cancel the listening socket, and cause the thread to finish */
    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) { }
    }
}