package com.example.luerlyu.easyprint;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class BluetoothAction implements View.OnClickListener{
    private Button switchBT =null;
    private Button searchDevices =null;
    private Activity activity =null;
    private ListView unbondDevices = null;
    private ListView bondDevices = null;
    private Context context = null;
    private BluetoothService bluetoothService = null;
    public BluetoothAction(Context context, ListView unbondDevices, ListView bondDevices,
                           Button switchBT, Button searchDevices, Activity activity){
        super();
        this.context = context;
        this.unbondDevices = unbondDevices;
        this.bondDevices = bondDevices;
        this.switchBT = switchBT;
        this.searchDevices = searchDevices;
        this.activity = activity;
        this.bluetoothService = new BluetoothService(this.context,
                this.unbondDevices, this.bondDevices, this.switchBT,
                this.searchDevices);
    }
    public void setSwitchBT(Button switchBT) {
        this.switchBT = switchBT;
    }

    public void setSearchDevices(Button searchDevices) {
        this.searchDevices = searchDevices;
    }

    public void setUnbondDevices(ListView unbondDevices) {
        this.unbondDevices = unbondDevices;
    }
    //initialize UI
    public void initView(){
        if(this.bluetoothService.isOpen()){
            System.out.println("Bluetooth is on");
            switchBT.setText("turn off Bluetooth");
        }if(!this.bluetoothService.isOpen()){
            System.out.println("Bluetooth is off!");
            this.searchDevices.setEnabled(false);
        }
    }
    private void searchDevices(){
        bluetoothService.searchDevices();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.searchDevices){
            this.searchDevices();
        }else if (view.getId()==R.id.return_Bluetooth_btn){
            activity.finish();
        }else if (view.getId()==R.id.openBluetooth_tb){
            if (!this.bluetoothService.isOpen()){
                //when Bluetooth is turned off
                System.out.println("Bluetooth is off");
                this.bluetoothService.openBluetooth(activity);
            }else{
                System.out.println("Bluetooth is on");
                this.bluetoothService.closeBluetooth();
            }
        }
    }
}
