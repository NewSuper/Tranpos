package com.newsuper.t.sale.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;


import com.newsuper.t.sale.base.BaseApp;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.UUID;

/*
        MemTotal: 所有可用RAM大小。
        MemFree: LowFree与HighFree的总和，被系统留着未使用的内存。
        Buffers: 用来给文件做缓冲大小。
        Cached: 被高速缓冲存储器（cache memory）用的内存的大小（等于diskcache minus SwapCache）。
        SwapCached:被高速缓冲存储器（cache memory）用的交换空间的大小。已经被交换出来的内存，仍然被存放在swapfile中，用来在需要的时候很快的被替换而不需要再次打开I/O端口。
        Active: 在活跃使用中的缓冲或高速缓冲存储器页面文件的大小，除非非常必要，否则不会被移作他用。
        Inactive: 在不经常使用中的缓冲或高速缓冲存储器页面文件的大小，可能被用于其他途径。
        SwapTotal: 交换空间的总大小。
        SwapFree: 未被使用交换空间的大小。
        Dirty: 等待被写回到磁盘的内存大小。
        Writeback: 正在被写回到磁盘的内存大小。
        AnonPages：未映射页的内存大小。
        Mapped: 设备和文件等映射的大小。
        Slab: 内核数据结构缓存的大小，可以减少申请和释放内存带来的消耗。
        SReclaimable:可收回Slab的大小。
        SUnreclaim：不可收回Slab的大小（SUnreclaim+SReclaimable＝Slab）。
        PageTables：管理内存分页页面的索引表的大小。
        NFS_Unstable:不稳定页表的大小。
        要获取android手机总内存大小，只需读取”/proc/meminfo”文件的第1行，并进行简单的字符串处理即可。*/
public class DeviceUtils {

    private static final String TAG = "DeviceUtils";

    private static DeviceUtils _instance;

    private String computerName;

    private String ipAddress;

    private String motherboardNumber;

    private String cpuID;

    private String diskID;

    private String macAddress;

    private String deviceBrand;

    private String systemModel;

    private String systemVersion;

    private String appVersion;

    private static final String MEM_INFO_PATH = "/proc/meminfo";
    private static final String MEMTOTAL = "MemTotal";
    private static final String MEMFREE = "MemFree";

    public static DeviceUtils getInstance(){
        if(_instance == null){
            _instance = new DeviceUtils();
            _instance.setup();
        }
        return _instance;
    }

    /**
     * 组装基本信息
     */
    private void setup(){
        try {
            computerName = installLoacalSerialNumber();
            ipAddress = installIPAddress(BaseApp.getApplication());
            macAddress = installMACAddress(BaseApp.getApplication());
            motherboardNumber = installUniquePsuedoID();
            cpuID = getCPUSerial();
            systemVersion = installSystemVersion();
            systemModel = installSystemModel();
            deviceBrand = installDeviceBrand();
            appVersion = installAPPVersion(BaseApp.getApplication());
        }catch (Exception ex){
            LogUtil.e(this, "获取硬件信息异常", ex);
        }
    }

    public String getComputerName() {
        return computerName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getMotherboardNumber() {
        return motherboardNumber;
    }

    public String getCpuID() {
        return cpuID;
    }

    public String getDiskID() {
        return diskID;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public String getSystemModel() {
        return systemModel;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    /**
     * 得到中内存大小
     *
     * @return
     */
    public static long getTotalMemory() {
        return getMemInfoIype(MEMTOTAL);
    }

    /**
     * 得到可用内存大小
     *
     * @return
     */
    public static long getFreeMemory() {
        ActivityManager am = (ActivityManager)BaseApp.getApplication().getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem / 1024;
    }

    /**
     * 得到type info
     *
     * @param type
     * @return
     */
    public static long getMemInfoIype(String type) {
        try {
            FileReader fileReader = new FileReader(MEM_INFO_PATH);
            BufferedReader bufferedReader = new BufferedReader(fileReader, 4 * 1024);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                if (str.contains(type)) {
                    break;
                }
            }
            bufferedReader.close();
            /* \\s表示   空格,回车,换行等空白符,
            +号表示一个或多个的意思     */
            String[] array = str.split("\\s+");
            // 获得系统总内存，单位是KB，乘以1024转换为Byte
            return Long.valueOf(array[1]);
        } catch (FileNotFoundException e) {
            LogUtil.e(TAG, "获取内存信息异常", e);
        } catch (IOException e) {
            LogUtil.e(TAG, "获取内存信息异常", e);
        }
        return 0L;
    }

    /**
     * 得到内置存储空间的总容量
     * @return
     */
    public static long getInternalToatalSpace(){
        String path = Environment.getDataDirectory().getPath();
        LogUtil.d(TAG, "root path is " + path);
        StatFs statFs = new StatFs(path);
        long blockSize = statFs.getBlockSize();
        long totalBlocks = statFs.getBlockCount();
        long availableBlocks = statFs.getAvailableBlocks();
        long useBlocks  = totalBlocks - availableBlocks;

        long rom_length = totalBlocks*blockSize;

        return rom_length / 1024;
    }

    /**
     * 获取外置存储卡总空间
     * @return
     */
    public static long getExternalTotalSpace(){
        //得到外部储存sdcard的状态
        String sdcard= Environment.getExternalStorageState();
        //外部储存sdcard存在的情况
        String state= Environment.MEDIA_MOUNTED;
        if(sdcard.equals(state)){
            File file= Environment.getExternalStorageDirectory();
            StatFs statFs=new StatFs(file.getPath());
            long blockSize = statFs.getBlockSize();
            long totalBlocks = statFs.getBlockCount();
            long availableBlocks = statFs.getAvailableBlocks();
            long useBlocks  = totalBlocks - availableBlocks;

            long rom_length = totalBlocks*blockSize;

            return rom_length / 1024;
        }else{
            return 0L;
        }
    }

    /**
     * 获取外置存储卡总空间
     * @return
     */
    public static long getExternalFreeSpace(){
        //得到外部储存sdcard的状态
        String sdcard= Environment.getExternalStorageState();
        //外部储存sdcard存在的情况
        String state= Environment.MEDIA_MOUNTED;
        if(sdcard.equals(state)){
            File file= Environment.getExternalStorageDirectory();
            StatFs statFs=new StatFs(file.getPath());
            long blockSize = statFs.getBlockSize();
            long totalBlocks = statFs.getBlockCount();
            long availableBlocks = statFs.getAvailableBlocks();
            long useBlocks  = totalBlocks - availableBlocks;

            long rom_length = availableBlocks*blockSize;

            return rom_length / 1024;
        }else{
            return 0L;
        }
    }


    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String installAPPVersion(Context context) {
        String versionCode = "";
        try {
            PackageInfo packageInfo = context.getApplicationContext().getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取MAC地址
     *
     * @param context
     * @return
     */
    private static String installMACAddress(Context context) {
        StringBuffer deviceId = new StringBuffer();
        // wifi mac地址
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        // 判断wifi是否开启
        if (!wifi.isWifiEnabled()) {
            wifi.setWifiEnabled(true);
        }
        WifiInfo info = wifi.getConnectionInfo();
        String wifiMac = info.getMacAddress();
        if (StringUtils.isNotBlank(wifiMac)) {
            deviceId.append(wifiMac);
            return deviceId.toString();
        }
        return "";
    }

    /**
     * 获取ip地址
     *
     * @param context
     * @return
     */
    private static String installIPAddress(Context context) {
        // 获取wifi服务
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        // 判断wifi是否开启
        if (!wifi.isWifiEnabled()) {
            wifi.setWifiEnabled(true);
        }
        WifiInfo info = wifi.getConnectionInfo();
        int ipAddress = info.getIpAddress();
        String ip = intToIp(ipAddress);
        return ip;
    }

    private static String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF);
    }

    // 获得独一无二的设备 ID
    private static String installUniquePsuedoID() {
        String serial = null;
        String m_szDevIDShort = "35" + Build.BOARD.length() % 10 + Build.BRAND.length() % 10
                + Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10
                + Build.HOST.length() % 10 + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10
                + Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10
                + Build.TYPE.length() % 10 + Build.USER.length() % 10; // 13
        // 位
        try {
            serial = Build.class.getField("SERIAL").get(null).toString();
            // API>=9 使用serial号
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            // serial需要一个初始化
            serial = "serial"; // 随便一个初始化
        }
        // 使用硬件信息拼凑出来的15位号码
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    /**
     * CPU序列号
     *
     * @return
     */
    private static String getCPUSerial() {
        String str = "", strCPU = "", cpuAddress = "0000000000000000";
        try {
            // 读取CPU信息
            Process pp = Runtime.getRuntime().exec("cat /proc/cpuinfo");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            // 查找CPU序列号
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null) {
                    // 查找到序列号所在行
                    if (str.indexOf("Serial") > -1) {
                        // 提取序列号
                        strCPU = str.substring(str.indexOf(":") + 1, str.length());
                        // 去空格
                        cpuAddress = strCPU.trim();
                        break;
                    }
                } else {
                    // 文件结尾
                    break;
                }
            }
        } catch (Exception ex) {
            // 赋予默认值
            ex.printStackTrace();
        }
        return cpuAddress;
    }

    /**
     * 获取本机设备的序列号
     *
     * @return String
     */
    private static String installLoacalSerialNumber() {
        String SerialNumber = Build.SERIAL;
        if (SerialNumber == null) {
            SerialNumber = "";
        }
        return SerialNumber;
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String installSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String installSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    public static String installDeviceBrand() {
        return Build.BRAND;
    }


}
