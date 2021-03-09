package com.newsuper.t.consumer.manager;

import android.app.Activity;

import java.util.ArrayList;


public class ActivityManager {
    private static ActivityManager manager;
    private ArrayList<Activity> activities;
    private ActivityManager(){
        activities = new ArrayList<>();
    }
    public static ActivityManager getInstance(){
        if (manager == null){
            manager = new ActivityManager();
        }
        return manager;
    }
    public void addActivity(Activity activity){
        activities.add(activity);
    }
    public void removeActivity(Activity activity){
        activities.remove(activity);
    }
    public void closeAllActivity(){
        for (Activity activity :activities){
            activity.finish();
        }
        activities.clear();
    }
}
