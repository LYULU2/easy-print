package com.example.luerlyu.easyprint;

import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class PrintDataService {
    private Context context = null;
    private String deviceAddress = null;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter
            .getDefaultAdapter();
    private BluetoothDevice device = null;
    private static BluetoothSocket bluetoothSocket = null;
    private static OutputStream outputStream = null;
    private static final UUID uuid = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private boolean isConnection = false;
    // can be removed???
    final String[] items = { "reset printer", "standard ASCII font", "compressASCIIfont", "字体不放大",
            "double height and width", "cancel bold", "bold", "convert printing", "cancel convert printing",
            "cancel convert black and white", "convert black and white", "cancel clockwise 90°", "clockwise 90°" };
    final byte[][] byteCommands = { { 0x1b, 0x40 },// reset printer
            { 0x1b, 0x4d, 0x00 },// standard ASCII font
            { 0x1b, 0x4d, 0x01 },// compress ASCII font
            { 0x1d, 0x21, 0x00 },// not enlarge font
            { 0x1d, 0x21, 0x11 },// double height and width
            { 0x1b, 0x45, 0x00 },// cancel bold
            { 0x1b, 0x45, 0x01 },// bold
            { 0x1b, 0x7b, 0x00 },// cancel convert printing
            { 0x1b, 0x7b, 0x01 },// convert printing
            { 0x1d, 0x42, 0x00 },// cancel convert black and white
            { 0x1d, 0x42, 0x01 },// convert black and white
            { 0x1b, 0x56, 0x00 },// cancel clockwise 90°
            { 0x1b, 0x56, 0x01 },// clockwise 90°
    };
    public PrintDataService(Context context, String deviceAddress) {
        super();
        this.context = context;
        this.deviceAddress = deviceAddress;
        this.device = this.bluetoothAdapter.getRemoteDevice(this.deviceAddress);
    }
    public String getDeviceName() {
        return this.device.getName();
    }
    public boolean connect() {
        if (!this.isConnection) {
            try {
                bluetoothSocket = this.device
                        .createRfcommSocketToServiceRecord(uuid);
                bluetoothSocket.connect();
                outputStream = bluetoothSocket.getOutputStream();
                this.isConnection = true;
                if (this.bluetoothAdapter.isDiscovering()) {
                    System.out.println("turn off adapter！");
                    this.bluetoothAdapter.isDiscovering();
                }
            } catch (Exception e) {
                Toast.makeText(this.context, "connection failed！",Toast.LENGTH_LONG).show();
                return false;
            }
            Toast.makeText(this.context, this.device.getName() + "connected！",
                    Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return true;
        }
    }
    public static void disconnect() {
        System.out.println("disconnected with device");
        try {
            bluetoothSocket.close();
            outputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public void selectCommand() {
        new AlertDialog.Builder(context).setTitle("please select command")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            outputStream.write(byteCommands[which]);
                        } catch (IOException e) {
                            Toast.makeText(context, "failed setting command！",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).create().show();
    }
    public void send(String sendData) {
        if (this.isConnection) {
            System.out.println("start printing！");
            try {
                byte[] data = sendData.getBytes("gbk");
                outputStream.write(data, 0, data.length);
                outputStream.flush();
            } catch (IOException e) {
                Toast.makeText(this.context, "failed sending！", Toast.LENGTH_SHORT)
                        .show();
            }
        } else {
            Toast.makeText(this.context, "device is not connected, please try again！",
                    Toast.LENGTH_SHORT).show();

        }
    }
}
