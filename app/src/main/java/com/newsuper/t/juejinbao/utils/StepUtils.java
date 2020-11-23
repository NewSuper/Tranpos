package com.newsuper.t.juejinbao.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.juejinchain.steplibrary.NimPreferences;
import com.juejinchain.steplibrary.StepService;

import java.text.SimpleDateFormat;

import static android.content.Context.SENSOR_SERVICE;

public class StepUtils {
    private Context context;

    //传感器管理
    private SensorManager mSensorManager;

    /**
     * Sensor.TYPE_STEP_COUNTER
     * 计步传感器计算当天步数，不需要后台Service
     */
    private StepCounter mStepCounter;

    /**
     * 当前步数
     */
    private static long CURRENT_STEP = 0L;


    private static StepUtils stepUtils;
    public static StepUtils getInstance(Context context){
        if(stepUtils == null || stepUtils.mStepCounter == null){
            stepUtils = new StepUtils(context);
        }
        return stepUtils;
    }
    private StepUtils(Context context){
        this.context = context;
        mSensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        CURRENT_STEP = getCurrentStepForSP();

        if(getStepCounter()){
            addStepCounterListener();
        }
    }


    //是否存在计步传感器
    public boolean getStepCounter() {
        Sensor countSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (null == countSensor) {
            return false;
        }
        return true;
    }

    //返回步数
    public long getCurrentStep(){
        Log.e("zy" , "步数：" + CURRENT_STEP);
        return CURRENT_STEP;
    }

    //清空步数
    public void cleanStep(){
        CURRENT_STEP = 0;
        NimPreferences.setLong(context , NimPreferences.STEP_NUM_LAST , 0);
        NimPreferences.setLong(context , NimPreferences.STEP_NUM_NOW , 0);
    }


    private void addStepCounterListener() {

        if (null != mStepCounter) {
            CURRENT_STEP = getCurrentStepForSP();
            return;
        }

        mStepCounter = new StepCounter(context, new StepChangeListener() {
            @Override
            public void stepCreate(long step) {
                CURRENT_STEP += step;
//                Log.e("zy" , "stepCreate_CURRENT_STEP = " + CURRENT_STEP);

                long nowTime = System.currentTimeMillis();
                //APP持续开启时，检查是否过天
                long lastTime = NimPreferences.getLong(context , NimPreferences.STEP_TIME , 0);
                //天数不对，清0
                if(!showDay(nowTime).equals(showDay(lastTime))){
                    CURRENT_STEP = 0;
                }
                Log.e("zy" , "步数累积:" + CURRENT_STEP);


                NimPreferences.setLong(context , NimPreferences.STEP_TIME , nowTime);
                //步数累加并保存
                NimPreferences.setLong(context , NimPreferences.STEP_NUM_NOW , CURRENT_STEP);


            }
        });

        Sensor countSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        boolean registerSuccess = mSensorManager.registerListener(mStepCounter, countSensor, SensorManager.SENSOR_DELAY_FASTEST);

        if(registerSuccess){
            Log.e("zy" , "传感器注册成功");
        }
    }


    //从偏好获取当前步数
    private long getCurrentStepForSP(){
        long nowTime = System.currentTimeMillis();
        String nowDay = showDay(nowTime);
        long lastTime = NimPreferences.getLong(context , NimPreferences.STEP_TIME , 0);
        String lastDay = showDay(lastTime);
        //设置当前时间为历史时间
        NimPreferences.setLong(context , NimPreferences.STEP_TIME , nowTime);

        //当前是同一天，取出步数
        if(nowDay.equals(lastDay)){
            long step = NimPreferences.getLong(context , NimPreferences.STEP_NUM_NOW , 0);
            //小于0  有问题
            if(step < 0){
                NimPreferences.setLong(context , NimPreferences.STEP_NUM_LAST , 0);
                NimPreferences.setLong(context , NimPreferences.STEP_NUM_NOW , 0);
                return 0;
            }else{
                return NimPreferences.getLong(context , NimPreferences.STEP_NUM_NOW , 0);

            }
        }
        //当前是不同天，则步数清0
        else{
            //将上一次步数计为0，onSensorChanged会判断并清0
            NimPreferences.setLong(context , NimPreferences.STEP_NUM_LAST , 0);
            NimPreferences.setLong(context , NimPreferences.STEP_NUM_NOW , 0);
            return 0;
        }
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    //获取日期
    public String showDay(long timeStamp) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(timeStamp);
        return date;
    }


    //每天开APP检测日期，发现日期过天后则重置步数并且更新日期
    public void resetStepForDayStart(){
        long nowTime = System.currentTimeMillis();
        String nowDay = showDay(nowTime);
        long lastTime = NimPreferences.getLong(context , NimPreferences.STEP_TIME , 0);
        String lastDay = showDay(lastTime);
        //设置当前时间为历史时间
        NimPreferences.setLong(context , NimPreferences.STEP_TIME , nowTime);


        //当前是同一天，取出步数
        if(nowDay.equals(lastDay)){

        }
        //当前是不同天，则步数清0
        else{
            Log.e("zy" , "不同天，步数清0");
            //将上一次步数计为0，onSensorChanged会判断并清0
            NimPreferences.setLong(context , NimPreferences.STEP_NUM_LAST , 0);
            NimPreferences.setLong(context , NimPreferences.STEP_NUM_NOW , 0);
        }
    }


    /**
     * Sensor.TYPE_STEP_COUNTER
     * 计步传感器监听
     * Created by jiahongfei on 2017/6/30.
     */
    private static class StepCounter implements SensorEventListener {
        private Context mContext;

        private StepChangeListener stepChangeListener;

        public StepCounter(Context context , StepChangeListener stepChangeListener){
            mContext = context;
            this.stepChangeListener = stepChangeListener;
        }

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
                //当前步数
                long counterStep = (long) sensorEvent.values[0];
                Log.e("zy" , "onSensorChanged = " + counterStep );
                //上一次记录的步数
                long lastStep = NimPreferences.getLong(mContext , NimPreferences.STEP_NUM_LAST , 0);
                //保存当前步数为上一次记录的步数
                NimPreferences.setLong(mContext , NimPreferences.STEP_NUM_LAST , counterStep);

                //上一次步数为0,则从新开始计算
                if(lastStep == 0) {
                    stepChangeListener.stepCreate(0);
                }else if(lastStep > 0){

                    if(counterStep - lastStep < 0){
                        //出bug了，重置
                        NimPreferences.setLong(mContext , NimPreferences.STEP_NUM_LAST , 0);
                    }else{
                        stepChangeListener.stepCreate(counterStep - lastStep);
                    }
                }else{
                    //出bug了，重置
                    NimPreferences.setLong(mContext , NimPreferences.STEP_NUM_LAST , 0);
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }

    }




    public static interface StepChangeListener {
        public void stepCreate(long step);
    }


}
