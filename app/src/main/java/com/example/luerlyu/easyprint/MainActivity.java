package com.example.luerlyu.easyprint;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private Context context = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;
        setTitle("Bluetooth print");
        this.intListener();
    }
    private void intListener(){
        ListView unbondDevices = (ListView) this.findViewById(R.id.unbondDevices);
        ListView bondDevices = (ListView) this.findViewById(R.id.bondDevices);
        Button switchBT = (Button) this.findViewById(R.id.openBluetooth_tb);
        Button searchDevices = (Button) this.findViewById(R.id.searchDevices);
        BluetoothAction bluetoothAction =new BluetoothAction(this.context, unbondDevices,bondDevices,
                switchBT, searchDevices, MainActivity.this);
        Button returnButton = (Button)this.findViewById(R.id.return_Bluetooth_btn);
        bluetoothAction.setSearchDevices(searchDevices);
        bluetoothAction.initView();
        switchBT.setOnClickListener(bluetoothAction);
        searchDevices.setOnClickListener(bluetoothAction);
        returnButton.setOnClickListener(bluetoothAction);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event){
        switch(keyCode){
            case KeyEvent.KEYCODE_BACK: return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
