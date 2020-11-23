package com.juejinchain.steplibrary;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

/**
 * Sensor.TYPE_STEP_COUNTER
 * 计步传感器计算当天步数，不需要后台Service
 * Created by jiahongfei on 2017/6/30.
 */
public class StepCounter implements SensorEventListener {
    private Context mContext;

    private StepChangeListener stepChangeListener;

    public StepCounter(Context context , StepChangeListener stepChangeListener){
        mContext = context;
        this.stepChangeListener = stepChangeListener;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            long counterStep = (long) sensorEvent.values[0];
            Log.e("zy" , "onSensorChanged = " + counterStep );
            long lastStep = NimPreferences.getLong(mContext , NimPreferences.STEP_NUM_LAST , 0);



            NimPreferences.setLong(mContext , NimPreferences.STEP_NUM_LAST , counterStep);
            if(lastStep == 0) {
                stepChangeListener.stepCreate(0);
            }else if(lastStep > 0){
                stepChangeListener.stepCreate(counterStep - lastStep);
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
