package com.newsuper.t.markert.utils.usbPrint;


import android.app.Activity;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.Display;


import java.util.HashMap;
import java.util.Map;

import static android.os.Build.MANUFACTURER;
import static android.os.Build.MODEL;

/**
 * 判断设备
 */

public class HardwareUtils {
    /**
     * 是否可以使用内部打印机
     *
     * @return 是否可以
     */
    public static boolean CanUseIndexPrint() {
        boolean isCan = false;
        try {
            if (MODEL.equalsIgnoreCase("hdx039E")) {//L100
                isCan = true;
            } else if (MANUFACTURER.toLowerCase().contains("Ydj".toLowerCase())) {//云东家
                isCan = true;
            } else if (MANUFACTURER.toLowerCase().contains("SUNMI".toLowerCase()) && (!(MODEL.toLowerCase().contains("D1".toLowerCase())||MODEL.toLowerCase().contains("D2_d".toLowerCase())))) {//商米
                isCan = true;
            } else if (MODEL.toLowerCase().contains("QBOSSI".toLowerCase())) {
                isCan = true;
            }
        } catch (Exception e) {
        }
        return isCan;
    }

    /**
     * 判断是否是哪种设备 我们设备默认为0
     */
    public static int WhichDevice() {
        int type = 10;
        try {
            if (MODEL.toLowerCase().contains("hdx".toLowerCase())) {
                type = 0;
            } else if (MANUFACTURER.toLowerCase().contains("Ydj".toLowerCase())) {
                type = 1;
            } else if (MANUFACTURER.toLowerCase().contains("SUNMI".toLowerCase())) {
                type = 2;
            } else if (MODEL.toLowerCase().contains("QBOSS".toLowerCase())) {
                type = 3;
            } else if (MODEL.toLowerCase().contains("HIBOSS".toLowerCase())) {
                type = 4;
            } else if (MODEL.toLowerCase().contains("eagle")) {
                type = 5;
            } else if (MODEL.toLowerCase().contains("ps1562a")) {
                type = 6;
            } else if (MODEL.toLowerCase().contains("tps615".toLowerCase())) {
                type = 7;
            } else if (MANUFACTURER.toLowerCase().contains("posin".toLowerCase())) {
                type = 8;
            } else if (MODEL.toLowerCase().contains("rk3188".toLowerCase())) {
                type = 9;
            } else if (MODEL.toLowerCase().contains("rk3288".toLowerCase()) && MANUFACTURER.toLowerCase()
                    .contains("rockchip".toLowerCase())) {
                type = 11;
            } else {
                type = 10;
            }
        } catch (Exception e) {
        }
        return type;
    }

    /**
     * 是否可以硬件打开钱箱
     *
     * @return 是否可以
     */
    public static boolean CanOpenQianXian() {
        boolean isCan = false;
        try {
            if (MODEL.equalsIgnoreCase("hdx039E")) {
                isCan = true;
            } else if (MODEL.equalsIgnoreCase("HDX073")) {//L200
                isCan = true;
            } else if (MODEL.equalsIgnoreCase("HDX075H")) {//L300
                isCan = true;
            } else if (MANUFACTURER.toLowerCase().contains("Ydj".toLowerCase())) {
                isCan = true;
            } else if (MANUFACTURER.toLowerCase().contains("SUNMI".toLowerCase())) {
                isCan = true;
            } else if (MODEL.toLowerCase().contains("QBOSS".toLowerCase())) {
                isCan = true;
            } else if (MODEL.toLowerCase().contains("eagle")) {
                isCan = true;
            } else if (MODEL.toLowerCase().contains("ps1562a".toLowerCase())) {
                isCan = true;
            } else if (MODEL.toLowerCase().contains("tps615".toLowerCase())) {
                isCan = true;
            } else if (MANUFACTURER.toLowerCase().contains("posin".toLowerCase())) {
                isCan = true;
            } else if (MODEL.toLowerCase().contains("rk3188".toLowerCase())) {
                isCan = true;
            } else if (MODEL.toLowerCase().contains("rk3288".toLowerCase()) && MANUFACTURER.toLowerCase()
                    .contains("rockchip".toLowerCase())) {
                isCan = true;
            }
        } catch (Exception e) {
        }
        return isCan;
    }

    /**
     * 是否可显示数显
     *
     * @return 是否可以
     */
    public static boolean CanNumShow() {
        boolean isCan = false;
        try {
            if (MODEL.equalsIgnoreCase("HDX073")) {
                isCan = true;
            } else if (MANUFACTURER.toLowerCase().contains("posin".toLowerCase())) {
                isCan = true;
            } else if (MANUFACTURER.toLowerCase().contains("SUNMI".toLowerCase()) && (MODEL.toLowerCase().contains("T1mini".toLowerCase()))) {
                isCan = true;
            }
        } catch (Exception e) {
        }
        return isCan;
    }

    public static int NumberShowType() {
        int type = 1;
        try {
            if (MANUFACTURER.toLowerCase().contains("posin".toLowerCase())) {
                type = 2;
            } else if (MANUFACTURER.toLowerCase().contains("SUNMI".toLowerCase()) && (MODEL.toLowerCase().contains("T1mini".toLowerCase()))) {
                type = 3;
            }
        } catch (Exception e) {
        }
        return type;
    }

    public static boolean CanCallPhone() {
        boolean isCan = false;
        try {
            if (MODEL.equalsIgnoreCase("hdx039E")) {
                isCan = true;
            }
        } catch (Exception e) {
        }
        return isCan;
    }

    /**
     * 是否可双屏显示
     *
     * @return 是否可以
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean CanDoubleDisplay(Context context) {
        boolean isCan = false;
        try {
            if (MODEL.toLowerCase().equalsIgnoreCase("HDX075H".toLowerCase())) {
                isCan = true;
            } else if (MODEL.toLowerCase().contains("HIBOSS".toLowerCase())) {
                isCan = true;
            } else if (MODEL.toLowerCase().contains("ps1562a".toLowerCase())) {
                isCan = true;
            } else if (MODEL.toLowerCase().contains("tps615".toLowerCase())) {
                isCan = true;
            } else if (MODEL.toLowerCase().contains("rk3188".toLowerCase())) {
                isCan = true;
            } else if (MANUFACTURER.toLowerCase().contains("SUNMI".toLowerCase()) && (MODEL.toLowerCase().contains("T2".toLowerCase()))) {
                isCan = true;
            } else if (MANUFACTURER.toLowerCase().contains("SUNMI".toLowerCase()) && (MODEL.toLowerCase().contains("S2".toLowerCase()))) {
                isCan = true;
            } else {
                int length = 1;
                try {
                    DisplayManager mDisplayManager = (DisplayManager) context.getApplicationContext().getSystemService(
                            Context.DISPLAY_SERVICE);
                    Display[] displays = mDisplayManager.getDisplays();
                    length = displays.length;
                } catch (Exception e) {

                } catch (Throwable a) {

                }
                if (length > 1) {
                    isCan = true;
                }
            }
        } catch (Exception e) {
        }
        return isCan;
    }

    /**
     * 是否使用小布局NewOrder
     *
     * @return
     */
    public static boolean IsShowSmallNewOrder() {
        boolean isCan = false;
        try {
            if (MODEL.equalsIgnoreCase("hdx039E")) {
                isCan = true;
            }
        } catch (Exception e) {
        }
        return isCan;
    }

    /**
     * 是否可以使用内部摄像头扫码
     *
     * @return
     */
    public static boolean IsCanUseIndexCamera() {
        boolean isCan = false;
        try {
            if (MODEL.equalsIgnoreCase("hdx039E")) {
                isCan = true;
            } else if (MODEL.toLowerCase().contains("MZ63".toLowerCase())) {
                isCan = true;
            } else if (MANUFACTURER.toLowerCase().contains("samsung".toLowerCase())) {
                isCan = true;
            } else if (MANUFACTURER.toLowerCase().contains("SUNMI".toLowerCase()) && (MODEL.toLowerCase().contains("T1mini".toLowerCase()))) {
                isCan = true;
            }
        } catch (Exception e) {
        }
        return isCan;
    }

    /**
     * 是否可以使用串口称重
     */
    public static boolean CanPortWeigh() {
//        boolean isCan = true;
//        if (MODEL.equalsIgnoreCase("hdx039E")) {
//            isCan = true;
//        } else if (MODEL.equalsIgnoreCase("HDX073")) {//L200
//            isCan = true;
//        } else if (MODEL.equalsIgnoreCase("HDX075H")) {//L300
//            isCan = true;
//        } else if (MANUFACTURER.toLowerCase().contains("Ydj".toLowerCase())) {
//            isCan = true;
//        } else if (MANUFACTURER.toLowerCase().contains("SUNMI".toLowerCase())) {
//            isCan = true;
//        }else if(MODEL.toLowerCase().contains("eagle")){
//            isCan = true;
//        }else if(MODEL.toLowerCase().contains("ps1562a".toLowerCase())){
//            isCan = true;
//        }else if(MODEL.toLowerCase().contains("tps615".toLowerCase())){
//            isCan = true;
//        }else if(MANUFACTURER.toLowerCase().contains("posin".toLowerCase())){
//            isCan = true;
//        }else if(MODEL.toLowerCase().equalsIgnoreCase("gp".toLowerCase())){
//            isCan = true;
//        }
        return true;
    }

    /**
     * 是否使用TTYS0串口，否选择TTYS1
     *
     * @return
     */
    public static boolean IsWeighUseSerialPortTTYS0() {
        boolean isCan = false;
        try {
            if (MODEL.equalsIgnoreCase("hdx039E")) {
                isCan = true;
            }
        } catch (Exception e) {
        }
        return isCan;
    }

    /**
     * 是否支持NFC
     */
    public static boolean ISSurrportNFC(Context context) {
        boolean isCan = true;
//        if (MODEL.equalsIgnoreCase("hdx039E")) {
//            isCan = true;
//        } else if (MODEL.equalsIgnoreCase("HDX073")) {
//            isCan = true;
//        } else if (MODEL.equalsIgnoreCase("HDX075H")) {
//            isCan = true;
//        }if(MANUFACTURER.toLowerCase().contains("SUNMI".toLowerCase())&&(MODEL.toLowerCase().contains("T1mini".toLowerCase()))){
//            isCan = true;
//        }
        return isCan;
    }

    /**
     * 是否需要开程序WIFI
     */
    public static boolean NeedOpenWifi() {
        boolean isCan = true;
//        if (MODEL.equalsIgnoreCase("hdx039E")) {
//            isCan = true;
//        } else if (MODEL.equalsIgnoreCase("HDX073")) {
//            isCan = true;
//        } else if (MODEL.equalsIgnoreCase("HDX075H")) {
//            isCan = true;
//        } else if(MODEL.toLowerCase().contains("eagle")){
//            isCan = true;
//        }
        return isCan;
    }

    /**
     * 是否可以返回键移到后台
     */
    public static boolean CanMoveToHome() {
        boolean isCan = true;
        try {
            if (MODEL.equalsIgnoreCase("hdx039E")) {
                isCan = false;
            } else if (MODEL.equalsIgnoreCase("HDX073")) {
                isCan = false;
            } else if (MODEL.equalsIgnoreCase("HDX075H")) {
                isCan = false;
            }
        } catch (Exception e) {
        }
        return isCan;
    }

    /**
     * 更新方式
     */
    public static String UpdateType() {
        String type = "1";
        try {
            if (MODEL.equalsIgnoreCase("hdx039E")) {
                type = "0";
            } else if (MODEL.equalsIgnoreCase("HDX073")) {
                type = "0";
            } else if (MODEL.equalsIgnoreCase("HDX075H")) {
                type = "0";
            }
        } catch (Exception e) {
        }
        return type;
    }

    /**
     * 是否需要判断权限
     */
    public static boolean NeedPermission() {
        boolean isCan = false;
        try {
            if (MANUFACTURER.toLowerCase().contains("SUNMI".toLowerCase())) {
                isCan = true;
            } else if (MANUFACTURER.toLowerCase().contains("gp".toLowerCase())) {
                isCan = true;
            } else if (MANUFACTURER.toLowerCase().contains("xiaomi".toLowerCase())) {
                isCan = true;
            }
        } catch (Exception e) {
        }
        return isCan;
    }

    /**
     * 返回具体智能机型号，用于收银等布局适配
     *
     * @return
     */
    public static int DeviceType() {
        int type = 1;
        try {
            if (MODEL.equalsIgnoreCase("hdx039E")) {
                type = 0;
            } else if (MODEL.equalsIgnoreCase("HDX073")) {//L200
                type = 1;
            } else if (MODEL.equalsIgnoreCase("HDX075H")) {//L300
                type = 2;
            } else if (MANUFACTURER.toLowerCase().contains("Ydj".toLowerCase())) {
                type = 3;
            } else if (MANUFACTURER.toLowerCase().contains("SUNMI".toLowerCase())) {
                type = 4;
            } else if (MANUFACTURER.toLowerCase().contains("gp".toLowerCase())) {
                type = 5;
            }
            if (MODEL.toLowerCase().contains("eagle")) {
                type = 6;
            } else if (MODEL.toLowerCase().contains("ps1562a".toLowerCase())) {
                type = 7;
            } else if (MODEL.toLowerCase().contains("tps615".toLowerCase())) {
                type = 8;
            }
        } catch (Exception e) {
        }
        return type;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static boolean IsCanShowFace(Context context) {
        boolean iscan = false;
        if (MANUFACTURER.toLowerCase().contains("SUNMI".toLowerCase())) {
            HashMap<String, UsbDevice> deviceHashMap = ((UsbManager) context.getSystemService(Activity.USB_SERVICE)).getDeviceList();
            for (Map.Entry entry : deviceHashMap.entrySet()) {
                UsbDevice usbDevice = (UsbDevice) entry.getValue();
                try {
                    if (!TextUtils.isEmpty(usbDevice.getInterface(0).getName()) && usbDevice.getInterface(0).getName().contains("Orb")) {
                        iscan = true;
                    }
                    if (!TextUtils.isEmpty(usbDevice.getInterface(0).getName()) && usbDevice.getInterface(0).getName().contains("Astra")) {
                        iscan = true;
                    }
                }catch (Exception e){

                }
            }
        }
        return iscan;
    }
}
