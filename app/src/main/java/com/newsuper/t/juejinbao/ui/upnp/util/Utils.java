package com.newsuper.t.juejinbao.ui.upnp.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

/**
 * 说明：
 * 作者：zhouzhan
 * 日期：17/6/29 10:47
 */

public class Utils {

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    /**
     * 把时间戳转换成 00:00:00 格式
     * @param timeMs    时间戳
     * @return 00:00:00 时间格式
     */
    public static String getStringTime(int timeMs) {
        StringBuilder formatBuilder = new StringBuilder();
        Formatter formatter = new Formatter(formatBuilder, Locale.getDefault());

        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        formatBuilder.setLength(0);
        return formatter.format("%02d:%02d:%02d", hours, minutes, seconds).toString();
    }

    /**
     * 把 00:00:00 格式转成时间戳
     * @param formatTime    00:00:00 时间格式
     * @return 时间戳(毫秒)
     */
    public static int getIntTime(String formatTime) {
        if (isNull(formatTime)) {
            return 0;
        }

        String[] tmp = formatTime.split(":");
        if (tmp.length < 3) {
            return 0;
        }
        int second = Integer.valueOf(tmp[0]) * 3600 + Integer.valueOf(tmp[1]) * 60 + Integer.valueOf(tmp[2]);

        return second * 1000;
    }

    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress().toUpperCase();
                        boolean isIPv4 = addr instanceof Inet4Address;
                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 port suffix
                                return delim < 0 ? sAddr : sAddr.substring(0, delim);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";
    }

    /**
     * Parse path url, obtain database id from file name.
     *
     * @param path
     * @return
     */
    public static String parseResourceId(String path) {
        String result = null;
        if (path != null && path.length() > 0) {
            int index = path.lastIndexOf("/");
            //File name like "id".mp3
            String fileName = path.substring(index + 1);
            result = fileName.substring(0, fileName.lastIndexOf("."));
        }

        return result;
    }

}

