package com.transpos.market.utils.summiPrint;


import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.IBinder;


import com.transpos.market.utils.UiUtils;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;


public class SummiPrinterQueneService extends Service {
    private boolean isCanPrint = true;
    public static volatile ArrayList<byte[]> printerbytes = new ArrayList<>();
    private static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public SummiPrinterQueneService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        PrintThead printThead = new PrintThead();
        printThead.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class PrintThead extends Thread {
        @Override
        public void run() {
            super.run();
            while (isCanPrint) {
                if (printerbytes.size() > 0) {
                    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (mBluetoothAdapter == null && !mBluetoothAdapter.isEnabled()) {
                        UiUtils.showToastShort("请打开蓝牙！");
                    } else {
                        try{
                            String innerprinter_address = "00:11:22:33:44:55";
                            BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(innerprinter_address);
                            BluetoothSocket btSocket = device.createRfcommSocketToServiceRecord(SPP_UUID);
                            btSocket.connect();
                            OutputStream mOutputStream = btSocket.getOutputStream();
                            try {
                                mOutputStream.write(printerbytes.get(0), 0, printerbytes.get(0).length);
                                long length = printerbytes.get(0).length;
                                printerbytes.remove(0);
                                mOutputStream.flush();
                                sleep(200);
                            }catch (Exception e){
                                System.out.println("写错了1"+e.toString());
                            }finally {
                                mOutputStream.close();
                                btSocket.close();
                            }
                        }catch (Exception e){
                            System.out.println("写错了2"+e.toString());
                            try {
                                sleep(3000);
                            }catch (Exception e1){
                                System.out.println("写错了3"+e1.toString());
                            }
                        }
                    }
                }
            }
        }
    }

    private long NeedToWait(long length) {
        long waitMills = 0;
        long times = length / 320;
        long remainder = length % 320;
        if (remainder > 0) {
            waitMills = (times + 1) * 700;
        } else {
            waitMills = (times) * 700;
        }
        return waitMills;
    }
}
