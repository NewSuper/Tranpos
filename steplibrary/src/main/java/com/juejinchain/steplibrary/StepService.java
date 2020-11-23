package com.juejinchain.steplibrary;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class StepService extends Service {

    /**
     * 步数通知ID
     */
    private static final int NOTIFY_ID = 1000;

    //频道名
    private static final String STEP_CHANNEL_ID = "stepChannelId";

    //传感器管理
    private SensorManager mSensorManager;
    //系统通知管理
    private NotificationManager nm;
    //兼容版本
    private NotificationApiCompat mNotificationApiCompat;


    /**
     * 当前步数
     */
    private static long CURRENT_STEP = 0L;

    /**
     * Sensor.TYPE_ACCELEROMETER
     * 加速度传感器计算当天步数，需要保持后台Service
     */
    private StepDetector mStepDetector;
    /**
     * Sensor.TYPE_STEP_COUNTER
     * 计步传感器计算当天步数，不需要后台Service
     */
    private StepCounter mStepCounter;



    @Override
    public void onCreate() {
        Log.e("zy" , "onCreate");
        super.onCreate();
        mSensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);


//        initNotification();

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {



        CURRENT_STEP = getCurrentStepForSP();
        Log.e("zy" , "onStartCommand_CURRENT_STEP = " + CURRENT_STEP);
        //注册传感器，android4.4以后如果有stepcounter可以使用计步传感器
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && getStepCounter()) {
            addStepCounterListener();
        } else {
            addBasePedoListener();
        }


        return START_STICKY;
    }


    private void addStepCounterListener() {

        if (null != mStepCounter) {
            CURRENT_STEP = getCurrentStepForSP();
            return;
        }

        mStepCounter = new StepCounter(this, new StepChangeListener() {
            @Override
            public void stepCreate(long step) {
                CURRENT_STEP += step;
                Log.e("zy" , "stepCreate_CURRENT_STEP = " + CURRENT_STEP);
                NimPreferences.setLong(StepService.this , NimPreferences.STEP_TIME , System.currentTimeMillis());
                //步数累加并保存
                NimPreferences.setLong(StepService.this , NimPreferences.STEP_NUM_NOW , CURRENT_STEP);
                updateNotification(CURRENT_STEP);

            }
        });

        Sensor countSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        boolean registerSuccess = mSensorManager.registerListener(mStepCounter, countSensor, SensorManager.SENSOR_DELAY_FASTEST);

        if(registerSuccess){
            Log.e("zy" , "传感器注册成功");
        }
    }

    private void addBasePedoListener() {
        if (null != mStepDetector) {
            CURRENT_STEP = getCurrentStepForSP();
            return;
        }
//        //没有计步器的时候开启定时器保存数据
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (null == sensor) {
            return;
        }
        mStepDetector = new StepDetector(this, new StepChangeListener() {
            @Override
            public void stepCreate(long step) {
                CURRENT_STEP += step;
                NimPreferences.setLong(StepService.this , NimPreferences.STEP_TIME , System.currentTimeMillis());
                NimPreferences.setLong(StepService.this , NimPreferences.STEP_NUM_NOW , CURRENT_STEP);
                updateNotification(CURRENT_STEP);
            }
        });

        boolean registerSuccess = mSensorManager.registerListener(mStepDetector, sensor, SensorManager.SENSOR_DELAY_FASTEST);

    }

    //是否存在计步传感器
    private boolean getStepCounter() {
        Sensor countSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (null == countSensor) {
            return false;
        }
        return true;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder.asBinder();
    }


    private synchronized void initNotification() {

        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        int smallIcon = getResources().getIdentifier("icon_step_small", "mipmap", getPackageName());
        if (0 == smallIcon) {
            smallIcon = R.mipmap.ic_launcher;
        }
        String receiverName = getReceiver(getApplicationContext());
        PendingIntent contentIntent = PendingIntent.getBroadcast(this, 100, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
        if (!TextUtils.isEmpty(receiverName)) {
            try {
                contentIntent = PendingIntent.getBroadcast(this, 100, new Intent(this, Class.forName(receiverName)), PendingIntent.FLAG_UPDATE_CURRENT);
            } catch (Exception e) {
                e.printStackTrace();
                contentIntent = PendingIntent.getBroadcast(this, 100, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
            }
        }
//        String km = "999";
//        String calorie = getCalorieByStep(currentStep);
//        String contentText = calorie + " 千卡  " + km + " 公里";
        int largeIcon = getResources().getIdentifier("ic_launcher", "mipmap", getPackageName());
        Bitmap largeIconBitmap = null;
        if (0 != largeIcon) {
            largeIconBitmap = BitmapFactory.decodeResource(getResources(), largeIcon);
        } else {
            largeIconBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        }
        mNotificationApiCompat = new NotificationApiCompat.Builder(this,
                nm,
                STEP_CHANNEL_ID,
                getString(R.string.app_name),
                smallIcon)
                .setContentIntent(contentIntent)
                .setContentText("文本")
                .setContentTitle("测试")
                .setTicker(getString(R.string.app_name))
                .setOngoing(true)
                .setPriority(Notification.PRIORITY_MIN)
                .setLargeIcon(largeIconBitmap)
                .setOnlyAlertOnce(true)
                .builder();
        mNotificationApiCompat.startForeground(this, NOTIFY_ID);
        mNotificationApiCompat.notify(NOTIFY_ID);

        updateNotification(CURRENT_STEP);
    }



    private final ISportStepInterface.Stub mIBinder = new ISportStepInterface.Stub() {


        @Override
        public long getCurrentTimeSportStep() throws RemoteException {
            return CURRENT_STEP;
        }

        @Override
        public void clean() throws RemoteException {
            CURRENT_STEP = 0;
            NimPreferences.setLong(StepService.this , NimPreferences.STEP_NUM_NOW , 0);
        }

        @Override
        public boolean canWalk() throws RemoteException {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && getStepCounter()) {
                return true;
            } else {
                return false;
            }
        }


    };


    public static String getReceiver(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_RECEIVERS);
            ActivityInfo[] activityInfos = packageInfo.receivers;
            if (null != activityInfos && activityInfos.length > 0) {
                for (int i = 0; i < activityInfos.length; i++) {
                    String receiverName = activityInfos[i].name;
                    Class superClazz = Class.forName(receiverName).getSuperclass();
                    int count = 1;
                    while (null != superClazz) {
                        if (superClazz.getName().equals("java.lang.Object")) {
                            break;
                        }
                        if (superClazz.getName().equals(BaseClickBroadcast.class.getName())) {
                            return receiverName;
                        }
                        if (count > 20) {
                            //用来做容错，如果20个基类还不到Object直接跳出防止while死循环
                            break;
                        }
                        count++;
                        superClazz = superClazz.getSuperclass();

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 更新系统通知
     */
    private synchronized void updateNotification(long stepCount) {
//        if (null != mNotificationApiCompat) {
//            mNotificationApiCompat.updateNotification(NOTIFY_ID, "测试", stepCount + "步");
//        }
    }

    //从偏好获取当前步数
    private long getCurrentStepForSP(){
        long nowTime = System.currentTimeMillis();
        String nowDay = showDay(nowTime);
        long lastTime = NimPreferences.getLong(StepService.this , NimPreferences.STEP_TIME , 0);
        String lastDay = showDay(lastTime);
        //设置当前时间为历史时间
        NimPreferences.setLong(StepService.this , NimPreferences.STEP_TIME , nowTime);

        //当前是同一天，取出步数
        if(nowDay.equals(lastDay)){
            return NimPreferences.getLong(this , NimPreferences.STEP_NUM_NOW , 0);
        }
        //当前是不同天，则步数清0
        else{
            NimPreferences.setLong(this , NimPreferences.STEP_NUM_NOW , 0);
            return 0;
        }


//        if(lastTime != 0){
//            //处于当天
//            if(nowDay.equals(lastDay)){
//                return NimPreferences.getLong(this , NimPreferences.STEP_NUM_NOW , 0);
//            }
//            //处于第N天
//            else{
//                int totalHour = (int)((nowTime - lastTime) / 1000 / 60 / 24);
//                int todayHour = 0;
//                try {
//                    todayHour = Integer.parseInt(showHH(nowTime));
//                }catch (Exception e){}
//                long totalStep = NimPreferences.getLong(this , NimPreferences.STEP_NUM_NOW , 0);
//                long nowStep = totalStep / totalHour * todayHour;
//
//                //保存当前步数
//                NimPreferences.setLong(this , NimPreferences.STEP_NUM_NOW , nowStep);
//                return nowStep;
//            }
//
//        }




    }


    //获取日期
    public static String showDay(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(timeStamp);
        return date;
    }

    //获取日期
    public static String showHH(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String date = sdf.format(timeStamp);
        return date;
    }
}
