package com.newsuper.t.markert.utils.usbPrint;


import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;


import com.newsuper.t.markert.utils.UiUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * USB电话盒
 */
public class UsbPhoneService extends Service {
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    private static final String ACTION_USB_PERMISSION1 = "com.android.example.USB_PERMISSION_BIAOQIAN";
    private static final String ACTION_USB_PERMISSION2 = "com.android.example.USB_PERMISSION_GP";
    private static final String ACTION_USB_PERMISSION3 = "com.android.example.USB_PERMISSION_REMIN";
    public static final String ACTION_USB_ADD_GP_DATA = "com.transpos.market.add_gp_data";
    public static final String ACTION_USB_ADD_USBREMIN_DATA = "com.transpos.market.add_usbremin_data";
    public static final String ACTION_USB_ADD_BIAOQIAN_DATA = "com.transpos.market.add_biaoqian_data";
    public static final String ACTION_USB_INITMETHOD_DATA = "com.transpos.market.init_method_data";
    private MyReciver receiver;
    private PendingIntent pendingIntent;
    private PendingIntent pendingIntent1;
    private PendingIntent pendingIntent2;
    private PendingIntent pendingIntent3;
    private UsbManager manager;
    private ReadThread mReadThread;
    private UsbDevice usbDevice;
    private UsbDeviceConnection connection;
    private UsbEndpoint outEndpoint;
    private UsbEndpoint inEndpoint;
    private UsbDeviceConnection connection_biaoqian;
    private UsbEndpoint outEndpoint_biaoqian;
    private UsbEndpoint inEndpoint_biaoqian;
    private UsbDeviceConnection connection_gp;
    private UsbEndpoint outEndpoint_gp;
    private UsbEndpoint inEndpoint_gp;
    private UsbDeviceConnection connection_remin;
    private UsbEndpoint outEndpoint_remin;
    private UsbEndpoint inEndpoint_remin;
    private ArrayList<byte[]> bytes = new ArrayList<>();
    private PowerManager.WakeLock wakeLock = null;
    private UsbDevice biaoqianDivice;
    private UsbDevice usbReminDevice;
    private UsbDevice gpdevice;
    private WriteGpThread writeGpThread;
    private volatile ArrayList<byte[]> gpbytes = new ArrayList<>();
    private volatile ArrayList<byte[]> usbbytes = new ArrayList<>();
    private volatile ArrayList<byte[]> biaoqianbytes = new ArrayList<>();
    private WriteUsbReminThread writeUsbReminThread;
    private WriteUsbBiaoQianThread writeUsbBiaoQianThread;

    public UsbPhoneService() {
    }


    @Override
    public void onCreate() {
        manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        pendingIntent1 = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION1), 0);
        pendingIntent2 = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION2), 0);
        pendingIntent3 = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION3), 0);
        IntentFilter filter2 = new IntentFilter();
        filter2.addAction(ACTION_USB_PERMISSION);
        filter2.addAction(ACTION_USB_PERMISSION1);
        filter2.addAction(ACTION_USB_PERMISSION2);
        filter2.addAction(ACTION_USB_PERMISSION3);
        registerReceiver(mUsbReceiver, filter2);
        receiver = new MyReciver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
        filter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
        filter.addAction(ACTION_USB_ADD_GP_DATA);
        filter.addAction(ACTION_USB_ADD_USBREMIN_DATA);
        filter.addAction(ACTION_USB_ADD_BIAOQIAN_DATA);
        filter.addAction(ACTION_USB_INITMETHOD_DATA);
        registerReceiver(receiver, filter);
        method_start_inservice();
        // 获取电源锁，使其在进入休眠后任能高速定位;
        acquireWakeLock();
        super.onCreate();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 开机时处理已经插上的设备
     */
    private void method_start_inservice() {
        UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        //取连接到设备上的USB设备集合
        try {
            HashMap<String, UsbDevice> map = usbManager.getDeviceList();
            if (map != null) {
                for (UsbDevice device : map.values()) {
                    int vid = device.getVendorId();
                    int pid = device.getProductId();
                    if ((vid == 1155 && pid == 22352) || vid == 1659) {
                        usbDevice = device;
                        method_inservice();
                    } else if ((26728 == vid && pid == 1280)) {
                       // UIUtils.showToast("打印机来了");
                        if (HardwareUtils.DeviceType() == 5) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                if (!TextUtils.isEmpty(device.getSerialNumber())) {
                                    biaoqianDivice = device;
                                //    UIUtils.showToast("来了标签");
                                    Biaoqian_start();
                                } else {
                                  //  UIUtils.showToast("来了打印机");
                                    gpdevice = device;
                                    Gp_start();
                                }
                            } else {
                                biaoqianDivice = device;
                                Biaoqian_start();
                            }
                        } else {
                            biaoqianDivice = device;
                            Biaoqian_start();
                        }
                    } else if ((8137 == vid && pid == 8214)) {
                        biaoqianDivice = device;
                        Biaoqian_start();
                    } else if (26728 == vid || 34918 == vid || 1137 == vid || 1046 == vid ||
                            4070 == vid || 1155 == vid || 10473 == vid) {
                        usbReminDevice = device;
                        UsbRemin_start();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("没有USB：" + e.toString());
        }
    }

    private void Query_Remin() {
        UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        //取连接到设备上的USB设备集合
        try {
            HashMap<String, UsbDevice> map = usbManager.getDeviceList();
            if (map != null) {
                for (UsbDevice device : map.values()) {
                    int vid = device.getVendorId();
                    int pid = device.getProductId();
                    if ((vid == 1155 && pid == 22352) || vid == 1659) {
                    } else if ((26728 == vid && pid == 1280)) {
                    } else if ((8137 == vid && pid == 8214)) {
                    } else if (26728 == vid || 34918 == vid || 1137 == vid || 1046 == vid || 4070 == vid || 1155 == vid || 10473 == vid) {
                        usbReminDevice = device;
                        UsbRemin_start();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("没有USB：" + e.toString());
        }
    }

    private void Query_BiaoQian() {
        UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        //取连接到设备上的USB设备集合
        try {
            HashMap<String, UsbDevice> map = usbManager.getDeviceList();
            if (map != null) {
                for (UsbDevice device : map.values()) {
                    int vid = device.getVendorId();
                    int pid = device.getProductId();
                    if ((vid == 1155 && pid == 22352) || vid == 1659) {
                    } else if ((26728 == vid && pid == 1280)) {
                        if (HardwareUtils.DeviceType() == 5) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                if (!TextUtils.isEmpty(device.getSerialNumber())) {
                                    biaoqianDivice = device;
                                    Biaoqian_start();
                                }
                            } else {
                                biaoqianDivice = device;
                                Biaoqian_start();
                            }
                        } else {
                            biaoqianDivice = device;
                            Biaoqian_start();
                        }
                    } else if ((8137 == vid && pid == 8214)) {
                        biaoqianDivice = device;
                        Biaoqian_start();
                    } else if (26728 == vid || 34918 == vid || 1137 == vid || 1046 == vid || 4070 == vid || 1155 == vid || 10473 == vid) {
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("没有USB：" + e.toString());
        }
    }

    private void Query_Gp() {
        UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        //取连接到设备上的USB设备集合
        try {
            HashMap<String, UsbDevice> map = usbManager.getDeviceList();
            if (map != null) {
                for (UsbDevice device : map.values()) {
                    int vid = device.getVendorId();
                    int pid = device.getProductId();
                    if ((vid == 1155 && pid == 22352) || vid == 1659) {
                    } else if ((26728 == vid && pid == 1280)) {
                        if (HardwareUtils.DeviceType() == 5) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                if (!TextUtils.isEmpty(device.getSerialNumber())) {
                                } else {
                                    gpdevice = device;
                                    Gp_start();
                                }
                            }
                        }
                    } else if ((8137 == vid && pid == 8214)) {
                    } else if (26728 == vid || 34918 == vid || 1137 == vid || 1046 == vid || 4070 == vid || 1155 == vid || 10473 == vid) {
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("没有USB：" + e.toString());
        }
    }

    /**
     * 标签打印机获取权限
     */
    private void Biaoqian_start() {
        try {
            if (biaoqianDivice == null) {
                return;
            }
            if (manager.hasPermission(biaoqianDivice)) {
                getUsbBiaoQianConn();
            } else {
                manager.requestPermission(biaoqianDivice, pendingIntent1);
            }
        } catch (Exception e) {
            System.out.println("没有USB：" + e.toString());
        }
    }

    /**
     * 标签打印机打印数据添加
     *
     * @param data
     */
    public synchronized void AddUsbBiaoQianData(ArrayList<byte[]> data) {
        if (biaoqianbytes == null) {
            biaoqianbytes = new ArrayList<>();
        }
        if (null != data) {
            if (biaoqianbytes.size() == 0) {
                if (biaoqianDivice != null) {
                    biaoqianbytes.addAll(data);
                    if (manager.hasPermission(biaoqianDivice)) {
                        if (outEndpoint_biaoqian != null) {
                            if (writeUsbBiaoQianThread == null) {
                                writeUsbBiaoQianThread = new WriteUsbBiaoQianThread();
                                writeUsbBiaoQianThread.start();
                            }
                        } else {
                            getUsbBiaoQianConn();
                        }
                    } else {
                        manager.requestPermission(biaoqianDivice, pendingIntent1);
                    }
                } else {
                    biaoqianbytes.addAll(data);
                    Query_BiaoQian();
                }
            } else {
                if (biaoqianDivice != null) {
                    biaoqianbytes.addAll(data);
                    if (manager.hasPermission(biaoqianDivice)) {
                        if (outEndpoint_biaoqian != null) {
                            if (writeUsbBiaoQianThread == null) {
                                writeUsbBiaoQianThread = new WriteUsbBiaoQianThread();
                                writeUsbBiaoQianThread.start();
                            }
                        } else {
                            getUsbBiaoQianConn();
                        }
                    } else {
                        manager.requestPermission(biaoqianDivice, pendingIntent1);
                    }
                } else {
                    if (biaoqianbytes.size() > 100) {
                        biaoqianbytes.clear();
                    } else {
                        biaoqianbytes.addAll(data);
                        Query_BiaoQian();
                    }
                }
            }
        }
    }

    /**
     * 连接标签打印机
     */
    private void getUsbBiaoQianConn() {
        try {
            UsbInterface intf = null;
            UsbEndpoint ep = null;
            inEndpoint_biaoqian = null;
            outEndpoint_biaoqian = null;
            int InterfaceCount = biaoqianDivice.getInterfaceCount();
            int j;
            for (j = 0; j < InterfaceCount; j++) {
                int i;
                intf = biaoqianDivice.getInterface(j);
                if (intf.getInterfaceClass() == 7) {
                    int UsbEndpointCount = intf.getEndpointCount();
                    for (i = 0; i < UsbEndpointCount; i++) {
                        ep = intf.getEndpoint(i);
                        if (ep.getDirection() == 0 && ep.getType() == UsbConstants.USB_ENDPOINT_XFER_BULK) {
                            break;
                        }
                    }
                    if (i != UsbEndpointCount) {
                        break;
                    }
                }
            }
            outEndpoint_biaoqian = ep;
            connection_biaoqian = manager.openDevice(biaoqianDivice);
            if (connection_biaoqian == null) {
                return;
            }
            connection_biaoqian.claimInterface(intf, true);
            if (writeUsbBiaoQianThread == null) {
                writeUsbBiaoQianThread = new WriteUsbBiaoQianThread();
                writeUsbBiaoQianThread.start();
            }
        } catch (Exception e) {
            System.out.println("测试出错：" + e.toString());
        }
    }

    /**
     * 标签打印机打印线程
     */
    private class WriteUsbBiaoQianThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                try {
                    if (biaoqianbytes.size() > 0) {
                        if (outEndpoint_biaoqian != null) {
                            int out = connection_biaoqian.bulkTransfer(outEndpoint_biaoqian, biaoqianbytes.get(0), biaoqianbytes.get(0).length, 0);
                            if (out < 0) {
                                try {
                                    sleep(1000L);
                                } catch (Exception e1) {
                                    System.out.println("测试BIAOQIAN" + e1.toString());
                                }
                            } else {
                                synchronized (this) {
                                    biaoqianbytes.remove(0);
                                }
                            }
                        } else {
                            interrupt();
                            writeUsbBiaoQianThread = null;
                        }
                    } else {
                        interrupt();
                        writeUsbBiaoQianThread = null;
                    }
                }catch (Exception e){
                    interrupt();
                    writeUsbBiaoQianThread = null;
                }
            }

        }
    }

    /**
     * 关闭标签打印
     */
    private void BiaoQian_stop() {
        biaoqianDivice = null;
        outEndpoint_biaoqian = null;
        if (writeUsbBiaoQianThread != null) {
            writeUsbBiaoQianThread.interrupt();
        }
    }

    /**
     * 热敏打印机获取权限
     */
    private void UsbRemin_start() {
        try {
            if (usbReminDevice == null) {
                return;
            }
            if (manager.hasPermission(usbReminDevice)) {
                getUsbReminConn();
            } else {
                manager.requestPermission(usbReminDevice, pendingIntent3);
            }
        } catch (Exception e) {

        }
    }

    /**
     * 热敏打印机添加打印数据
     *
     * @param data
     */
    public synchronized void AddUsbReminData(ArrayList<byte[]> data) {
        if (usbbytes == null) {
            usbbytes = new ArrayList<>();
        }
        if (null != data) {
            if (usbbytes.size() == 0) {
                if (usbReminDevice != null) {
                    usbbytes.addAll(data);
                    if (manager.hasPermission(usbReminDevice)) {
                        if (outEndpoint_remin != null) {
                            if (writeUsbReminThread == null) {
                                writeUsbReminThread = new WriteUsbReminThread();
                                writeUsbReminThread.start();
                            }
                        } else {
                            getUsbReminConn();
                        }

                    } else {
                        manager.requestPermission(usbReminDevice, pendingIntent3);
                    }

                } else {
                    usbbytes.addAll(data);
                    Query_Remin();
                }
            } else {
                if (usbReminDevice != null) {
                    usbbytes.addAll(data);
                    if (manager.hasPermission(usbReminDevice)) {
                        if (outEndpoint_remin != null) {
                            if (writeUsbReminThread == null) {
                                writeUsbReminThread = new WriteUsbReminThread();
                                writeUsbReminThread.start();
                            }
                        } else {
                            getUsbReminConn();
                        }

                    } else {
                        manager.requestPermission(usbReminDevice, pendingIntent3);
                    }
                } else {
                    if (usbbytes.size() > 100) {
                        usbbytes.clear();
                    } else {
                        usbbytes.addAll(data);
                        Query_Remin();
                    }
                }
            }
        }
    }

    /**
     * 连接热敏打印机
     */
    private void getUsbReminConn() {
        try {
            UsbInterface intf = null;
            UsbEndpoint ep = null;
            inEndpoint_remin = null;
            outEndpoint_remin = null;
            int InterfaceCount = usbReminDevice.getInterfaceCount();
            int j;
            for (j = 0; j < InterfaceCount; j++) {
                int i;
                intf = usbReminDevice.getInterface(j);
                if (intf.getInterfaceClass() == 7) {
                    int UsbEndpointCount = intf.getEndpointCount();
                    for (i = 0; i < UsbEndpointCount; i++) {
                        ep = intf.getEndpoint(i);
                        if (ep.getDirection() == 0 && ep.getType() == UsbConstants.USB_ENDPOINT_XFER_BULK) {
                            break;
                        }
                    }
                    if (i != UsbEndpointCount) {
                        break;
                    }
                }
            }
            outEndpoint_remin = ep;
            connection_remin = manager.openDevice(usbReminDevice);
            if (connection_remin == null) {
                return;
            }
            connection_remin.claimInterface(intf, true);
            if (writeUsbReminThread == null) {
                writeUsbReminThread = new WriteUsbReminThread();
                writeUsbReminThread.start();
            }
        } catch (Exception e) {
            System.out.println("测试出错：" + e.toString());
        }
    }

    /**
     * 热敏打印机打印线程
     */
    private class WriteUsbReminThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                try {
                    if (usbbytes.size() > 0) {
                        if (outEndpoint_remin != null) {
                            int out = connection_remin.bulkTransfer(outEndpoint_remin, usbbytes.get(0), usbbytes.get(0).length, 0);
                            if (out < 0) {
                                try {
                                    sleep(1000L);
                                } catch (Exception e1) {
                                    System.out.println("测试热敏" + e1.toString());
                                }
                            } else {
                                synchronized (this) {
                                    usbbytes.remove(0);
                                }
                            }
                        } else {
                            interrupt();
                            writeUsbReminThread = null;
                        }
                    } else {
                        interrupt();
                        writeUsbReminThread = null;
                    }
                }catch (Exception e){
                    interrupt();
                    writeUsbReminThread = null;
                }
            }

        }
    }

    /**
     * 关闭热敏打印机
     */
    private void UsbRemin_stop() {
        usbReminDevice = null;
        outEndpoint_remin = null;
        if (writeUsbReminThread != null) {
            writeUsbReminThread.interrupt();
        }
    }

    /**
     * 佳博内置打印机权限请求
     */
    private void Gp_start() {
        try {
            if (gpdevice == null) {
                return;
            }
            if (manager.hasPermission(gpdevice)) {
                getGpConn();
            } else {
                manager.requestPermission(gpdevice, pendingIntent2);
            }
        } catch (Exception e) {

        }

    }

    /**
     * 佳博内置打印机打印数据添加
     *
     * @param data
     */
    public synchronized void AddGpData(ArrayList<byte[]> data) {
        if (gpbytes == null) {
            gpbytes = new ArrayList<>();
        }
        if (null != data) {
            if (gpbytes.size() == 0) {
                if (gpdevice != null) {
                    gpbytes.addAll(data);
                    if (manager.hasPermission(gpdevice)) {
                        if (outEndpoint_gp != null) {
                            if (writeGpThread == null) {
                                writeGpThread = new WriteGpThread();
                                writeGpThread.start();
                            }
                        } else {
                            getGpConn();
                        }
                    } else {
                        manager.requestPermission(gpdevice, pendingIntent2);
                    }

                } else {
                    gpbytes.addAll(data);
                    Query_Gp();
                }
            } else {
                if (gpdevice != null) {
                    gpbytes.addAll(data);
                    if (manager.hasPermission(gpdevice)) {
                        if (outEndpoint_gp != null) {
                            if (writeGpThread == null) {
                                writeGpThread = new WriteGpThread();
                                writeGpThread.start();
                            }
                        } else {
                            getGpConn();
                        }
                    } else {
                        manager.requestPermission(gpdevice, pendingIntent2);
                    }
                } else {
                    if (gpbytes.size() > 100) {
                        gpbytes.clear();
                    } else {
                        gpbytes.addAll(data);
                        Query_Gp();
                    }
                }
            }
        }
    }

    /**
     * 连接佳博内置打印机
     */
    private void getGpConn() {
        try {
            UsbInterface intf = null;
            UsbEndpoint ep = null;
            inEndpoint_gp = null;
            outEndpoint_gp = null;
            int InterfaceCount = gpdevice.getInterfaceCount();
            int j;
            for (j = 0; j < InterfaceCount; j++) {
                int i;
                intf = gpdevice.getInterface(j);
                if (intf.getInterfaceClass() == 7) {
                    int UsbEndpointCount = intf.getEndpointCount();
                    for (i = 0; i < UsbEndpointCount; i++) {
                        ep = intf.getEndpoint(i);
                        if (ep.getDirection() == 0 && ep.getType() == UsbConstants.USB_ENDPOINT_XFER_BULK) {
                            break;
                        }
                    }
                    if (i != UsbEndpointCount) {
                        break;
                    }
                }
            }
            outEndpoint_gp = ep;
            connection_gp = manager.openDevice(gpdevice);
            if (connection_gp == null) {
                return;
            }
            connection_gp.claimInterface(intf, true);
            if (writeGpThread == null) {
                writeGpThread = new WriteGpThread();
                writeGpThread.start();
            }
        } catch (Exception e) {
            System.out.println("测试出错：" + e.toString());
        }
    }

    /**
     * 佳博内置打印机打印线程
     */
    private class WriteGpThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                try {
                    if (gpbytes.size() > 0) {
                        if (outEndpoint_gp != null) {
                            int out = connection_gp.bulkTransfer(outEndpoint_gp, gpbytes.get(0), gpbytes.get(0).length, 0);
                            if (out < 0) {
                                try {
                                    sleep(1000L);
                                } catch (Exception e1) {
                                    System.out.println("测试GP" + e1.toString());
                                }
                            } else {
                                synchronized (this) {
                                    gpbytes.remove(0);
                                }
                            }
                        } else {
                            interrupt();
                            writeGpThread = null;
                        }
                    } else {
                        interrupt();
                        writeGpThread = null;
                    }
                }catch (Exception e){
                    interrupt();
                    writeGpThread = null;
                }
            }

        }
    }

    /**
     * 关闭佳博打印机
     */
    private void UsbGp_stop() {
        gpdevice = null;
        outEndpoint_gp = null;
        if (writeGpThread != null) {
            writeGpThread.interrupt();
        }
    }

    /**
     * 获取USB电话盒权限
     */
    private void method_inservice() {
        try {
            if (usbDevice == null) {
                return;
            }
            if (manager.hasPermission(usbDevice)) {
                getSearilDate();
            } else {
                manager.requestPermission(usbDevice, pendingIntent);
            }
        } catch (Exception e) {
            System.out.println("测试出错：" + e.toString());
        }

    }

    /**
     * 连接USB来电盒
     */
    private void getSearilDate() {
        try {
            UsbInterface intf = null;
            UsbEndpoint ep = null;
            //写数据节点
            outEndpoint = null;
            //读数据节点
            inEndpoint = null;
            int InterfaceCount = usbDevice.getInterfaceCount();
            int j;
            for (j = 0; j < InterfaceCount; j++) {
                int i;
                intf = usbDevice.getInterface(j);
                if ((intf.getInterfaceClass() == UsbConstants.USB_CLASS_HID)
                ) {
                    int UsbEndpointCount = intf.getEndpointCount();
                    for (i = 0; i < UsbEndpointCount; i++) {
                        ep = intf.getEndpoint(i);
                        if (ep.getType() == UsbConstants.USB_ENDPOINT_XFER_INT) {
                            if (ep.getDirection() == UsbConstants.USB_DIR_OUT) {
                                outEndpoint = ep;
                            } else if (ep.getDirection() == UsbConstants.USB_DIR_IN) {
                                inEndpoint = ep;
                            }
                        }
                    }
                    if (i != UsbEndpointCount) {
                        break;
                    }
                }
            }
            if (j == InterfaceCount + 1) {
                return;
            }
            if (outEndpoint == null) {
//            UIUtils.showToastSafe("没有写节点");
            }
            if (inEndpoint == null) {
//            UIUtils.showToastSafe("没有读节点");
            }
            if (outEndpoint == null && inEndpoint == null) {
//            UIUtils.showToastSafe("错误1");
                return;
            }
            connection = manager.openDevice(usbDevice);
            if (connection == null) {
//            UIUtils.showToastSafe("错误");
                return;
            }
            connection.claimInterface(intf, true);
            mReadThread = new ReadThread();
            mReadThread.start();
        } catch (Exception e) {
            System.out.println("测试出错：" + e.toString());
        }
    }
    private int inMax = 8192;
    private byte[] buffer3 = new byte[inMax];
    private int readix = 0;
    private int readlen = 0;
    private boolean readSuccess = false;
    private long receivecount = 0;
    private ArrayList<Byte> recs = new ArrayList<Byte>();
    /**
     * 来电号码线程
     */
    private class ReadThread extends Thread {
        @Override
        public void run() {
            super.run();
            long pretime = System.currentTimeMillis();
            while (!isInterrupted()) {
                int size;
                try {
                    int inMax = inEndpoint.getMaxPacketSize();
                    byte buffer[] = new byte[inMax];
                    size = connection.bulkTransfer(inEndpoint, buffer, (int) buffer.length, 1000);
                    if (size > 0) {
                        OnDateRecevie(buffer, size);
                    }
                    Thread.sleep(1000);

//                    byte dat = 0;
//                    if (readix >= readlen) {
//                        readlen = connection.bulkTransfer(inEndpoint, buffer3, (int) buffer3.length, 100);
//                        readix = 0;
//                    }
//                    if (readix < readlen) {
//                        dat = buffer3[readix];
//                        readSuccess = true;
//                        ++receivecount;
//                        ++readix;
//                    } else {
//                        readSuccess = false;
//                    }
//                    if (readSuccess) {
//                        recs.add(dat);
//                    } else {
//                        // 暂时没有收到数据
//                        pretime = 0;
//                        try {
//                            Thread.sleep(100L);
//                        } catch (InterruptedException e) {
////                                    e.printStackTrace();
//                            System.out.println("测试："+e.toString());
//                        }
//                    }
//                    // 至少没200ms显示一下接收到的数据
//                    if ((System.currentTimeMillis() - pretime) > 1500 && recs.size() > 0) {
//                        byte[] temp = new byte[recs.size()];
//
//                        OnDateRecevie(temp, temp.length);
////                            System.out.println("测试收到2：进来"+temp.length );
//
//                        recs.clear();
//                        pretime = System.currentTimeMillis();
//                    }
//                    if (recs.size() == 0)
//                        pretime = System.currentTimeMillis();
                } catch (Exception e) {
                    System.out.println("测送");
                }
            }

        }
    }

    /**
     * 处理来电号码
     *
     * @param buffer
     * @param size
     */
    private void OnDateRecevie(byte[] buffer, int size) {
        byte[] result = new byte[size];
        byte[] temp = null;
        for (int i = 0; i < size; i++) {
            result[i] = buffer[i];
        }
        bytes.add(result);
        if (bytes.size() == 1) {
            temp = bytes.get(0);
        } else {
            byte[] temp1 = bytes.get(0);
            for (int i = 1; i < bytes.size(); i++) {
                temp1 = ByteUtils.addBytes(temp1, bytes.get(i));
            }
            temp = temp1;
        }
        int startPosistion = -1;
        int endPosition = -1;
        for (int i = 0; i < temp.length; i++) {
            if (HexDump.toHexString(temp[i]).equalsIgnoreCase("A0")) {
                startPosistion = i;
                break;
            }
        }
        if (startPosistion != -1) {
            for (int i = startPosistion; i < temp.length; i++) {
                if (HexDump.toHexString(temp[i]).equalsIgnoreCase("0D")) {
                    endPosition = i;
                    break;
                }
            }
        } else {
            bytes.clear();
            return;
        }
        if (endPosition == -1) {
            return;
        }
        //电话盒子获取来电号码逻辑
//        if ((endPosition > startPosistion) && (endPosition != -1) && (startPosistion != -1)) {
//            if ((HexDump.toHexString(temp[startPosistion + 1]).equalsIgnoreCase("40"))
//                    && ((temp[startPosistion + 2] == 0x31) || (temp[startPosistion + 2] == 0x32)
//                    || (temp[startPosistion + 2] == 0x33) || (temp[startPosistion + 2] == 0x34))) {
//                int usePosition = startPosistion + 3;
//                String num = "";
//                for (int i = usePosition; i < endPosition; i++) {
//                    num += (temp[i] - 0x30) + "";
//                }
//                Intent tmpI = new Intent(getApplicationContext(), NewPhoneActivity.class);
//                tmpI.putExtra("incoming_number", num);
//                tmpI.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(tmpI);
//                bytes.clear();
//                SpeechUtil.getInstance(getApplicationContext()).speak("有电话来了，电话号码是：" + num);
//            }
//        }

    }

    /**
     * USB电话盒移除处理
     */
    private void method_stopRead() {
        usbDevice = null;
        if (mReadThread != null) {
            mReadThread.interrupt();
        }
    }

    @Override
    public void onDestroy() {
     //   Intent i = new Intent("com.xunjoy.lewaimai.pos.usb_phone_service_destory");
       // Intent i = new Intent("com.cg.pos.utils.usbPrint.usb_phone_service_destory");
        Intent i = new Intent("com.cg.pos.usb_phone_service_destory");
        sendBroadcast(i);
        unregisterReceiver(receiver);
        unregisterReceiver(mUsbReceiver);
        receiver = null;
        method_stopRead();
        UsbGp_stop();
        UsbRemin_stop();
        BiaoQian_stop();
        releaseWakeLock();
        super.onDestroy();
    }

    // 获取电源锁，保持该服务在屏幕熄灭时仍然获取CPU时，保持运行
    private void acquireWakeLock() {
        try {
            if (null == wakeLock) {
                PowerManager pm = (PowerManager) this
                        .getSystemService(Context.POWER_SERVICE);
                wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK
                        | PowerManager.ON_AFTER_RELEASE, getPackageName()+":USBPhone");
                if (null != wakeLock) {
                    wakeLock.acquire();
                }
            }
        } catch (Exception e) {

        }
    }

    // 释放设备电源锁
    private void releaseWakeLock() {
        if (null != wakeLock) {
            wakeLock.release();
            wakeLock = null;
        }
    }

    /**
     * 获取USB插拔和数据接收
     */
    private class MyReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context arg0, Intent intent) {
            if (intent != null) {
                if (intent.getAction().equalsIgnoreCase(UsbManager.ACTION_USB_DEVICE_ATTACHED)) {
                    UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    int vid = ((UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)).getVendorId();
                    int pid = ((UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)).getProductId();

                    if ((vid == 1155 && pid == 22352) || vid == 1659) {
                        usbDevice = device;
                        method_inservice();
                    } else if ((26728 == vid && pid == 1280)) {
                        if (HardwareUtils.DeviceType() == 5) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                if (!TextUtils.isEmpty(device.getSerialNumber())) {
                                    biaoqianDivice = device;
                                    Biaoqian_start();
                                } else {
                                    gpdevice = device;
                                    Gp_start();
                                }
                            } else {
                                biaoqianDivice = device;
                                Biaoqian_start();
                            }
                        } else {
                            biaoqianDivice = device;
                            Biaoqian_start();
                        }
                    } else if ((8137 == vid && pid == 8214)) {
                        biaoqianDivice = device;
                        Biaoqian_start();
                    } else if (26728 == vid || 34918 == vid || 1137 == vid || 1046 == vid || 4070 == vid || 1155 == vid || 10473 == vid) {
                        usbReminDevice = device;
                        UsbRemin_start();
                    }

                } else if (intent.getAction().equalsIgnoreCase(UsbManager.ACTION_USB_DEVICE_DETACHED)) {
                    UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    int vid = ((UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)).getVendorId();
                    int pid = ((UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)).getProductId();
                    if ((vid == 1155 && pid == 22352) || vid == 1659) {
                        method_stopRead();
                    } else if ((26728 == vid && pid == 1280)) {
                        if (HardwareUtils.DeviceType() == 5) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                if (!TextUtils.isEmpty(device.getSerialNumber())) {
                                    BiaoQian_stop();
                                } else {
                                    UsbGp_stop();
                                }
                            } else {
                                BiaoQian_stop();
                            }
                        } else {
                            BiaoQian_stop();
                        }
                    } else if ((8137 == vid && pid == 8214)) {
                        BiaoQian_stop();
                    } else if (26728 == vid || 34918 == vid || 1137 == vid || 1046 == vid || 4070 == vid || 1155 == vid || 10473 == vid) {
                        UsbRemin_stop();
                    }
                } else if (intent.getAction().equalsIgnoreCase(ACTION_USB_ADD_GP_DATA)) {
                    ArrayList<byte[]> data = (ArrayList<byte[]>) intent.getSerializableExtra("data");
                    AddGpData(data);
                } else if (intent.getAction().equalsIgnoreCase(ACTION_USB_ADD_BIAOQIAN_DATA)) {
                    ArrayList<byte[]> data = (ArrayList<byte[]>) intent.getSerializableExtra("data");
                    AddUsbBiaoQianData(data);
                } else if (intent.getAction().equalsIgnoreCase(ACTION_USB_ADD_USBREMIN_DATA)) {
                    ArrayList<byte[]> data = (ArrayList<byte[]>) intent.getSerializableExtra("data");
                    AddUsbReminData(data);
                } else if (intent.getAction().equalsIgnoreCase(ACTION_USB_INITMETHOD_DATA)) {
                    method_start_inservice();
                }
            }
        }
    }

    /**
     * 在需要USB权限的机器上接收USB权限广播
     */
    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                if (ACTION_USB_PERMISSION.equalsIgnoreCase(action)) {
                    synchronized (this) {
                        if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                            usbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                            getSearilDate();
                        } else {
                            UiUtils.showToastShort("权限：电话盒权限被拒绝，不能读取来电号码，请重新插拔设备");
                        }
                    }
                } else if (ACTION_USB_PERMISSION1.equalsIgnoreCase(action)) {
                    synchronized (this) {
                        if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                            biaoqianDivice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                            getUsbBiaoQianConn();
                        } else {
                            UiUtils.showToastShort("权限：标签打印机权限被拒绝，不能打印标签，请重新插拔设备");
                        }
                    }
                } else if (ACTION_USB_PERMISSION2.equalsIgnoreCase(action)) {
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        gpdevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                        getGpConn();
                    } else {
                        UiUtils.showToastShort("权限：内置打印机权限被拒绝，不能打印小票，请重新插拔设备");
                    }
                } else if (ACTION_USB_PERMISSION3.equalsIgnoreCase(action)) {
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        usbReminDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                        getUsbReminConn();
                    } else {
                        UiUtils.showToastShort("权限：外接USB打印机权限被拒绝，不能打印小票，请重新插拔设备");
                    }
                }
            }
        }
    };
}

