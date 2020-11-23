package com.newsuper.t.juejinbao.utils;

/**是否连点
 */
public class ClickUtil {

    public static final int DELAY = 1000;
    private static long lastClickTime = 0;
    public static boolean isNotFastClick() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > DELAY) {
            lastClickTime = currentTime;
            return true;
        }
        else if(currentTime < lastClickTime){
            lastClickTime = currentTime;
            return true;
        }
        else{
            return false;
        }
    }
    public static boolean isNotFastClick(int time) {

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > time) {
            lastClickTime = currentTime;
            return true;
        } else {
            return false;
        }
    }



    public static final int LONGDELAY = 10000;
    private static long lastClickTime2 = 0;
    //10秒内不再响应点击
    public static boolean isNot10Click() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime2 > LONGDELAY) {
            lastClickTime2 = currentTime;
            return true;
        }
        else if(currentTime < lastClickTime2){
            lastClickTime2 = currentTime;
            return true;
        }
        else{
            return false;
        }
    }

}
