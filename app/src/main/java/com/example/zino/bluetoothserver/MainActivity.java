package com.example.zino.bluetoothserver;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/*
* 현재 앱이 설치될 스마트폰을 검색허용기기로 처리해야, 다른 블루투스 기반앱이
* 나를 검색할 수 있다!!
* */
public class MainActivity extends AppCompatActivity {

    BluetoothAdapter bluetoothAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //꺼져있으면 켠다
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(intent, 1);

        //검색을 허용한다!! 발견당하도록..
        Intent intent2 = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        intent2.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(intent2);


        //지금부터 쓰레드 생성하여, 접속을 허용하도록 한다!!
        AcceptThread acceptThread = new AcceptThread(this , bluetoothAdapter);
        acceptThread.start();
    }
}








