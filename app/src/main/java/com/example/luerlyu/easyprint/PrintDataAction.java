package com.example.luerlyu.easyprint;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PrintDataAction implements View.OnClickListener {
    private Context context = null;
    private TextView deviceName = null;
    private TextView connectState = null;
    private EditText printData = null;
    private String deviceAddress = null;
    private PrintDataService printDataService = null;
    public PrintDataAction(Context context, String deviceAddress,
                           TextView deviceName, TextView connectState) {
        super();
        this.context = context;
        this.deviceAddress = deviceAddress;
        this.deviceName = deviceName;
        this.connectState = connectState;
        this.printDataService = new PrintDataService(this.context,
                this.deviceAddress);
        this.initView();

    }
    private void initView() {
        // set name for current device
        this.deviceName.setText(this.printDataService.getDeviceName());
        // first connect bluetooth device
        boolean flag = this.printDataService.connect();
        if (flag == false) {
            // connection failed
            this.connectState.setText("connection failed！");
        } else {
            // connected
            this.connectState.setText("connected！");

        }
    }
    public void setPrintData(EditText printData) {
        this.printData = printData;
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.send) {
            String sendData = this.printData.getText().toString();
            this.printDataService.send(sendData + "\n");
        } else if (view.getId() == R.id.command) {
            this.printDataService.selectCommand();

        }
    }
}
