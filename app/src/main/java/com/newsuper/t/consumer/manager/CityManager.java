package com.newsuper.t.consumer.manager;

import android.content.Context;

import com.newsuper.t.consumer.function.db.City;
import com.newsuper.t.consumer.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CityManager {
    private String []key = new String[]{"A","B","C","D","E","F","G","H","J","K","L","M","N","P","Q","R","S","T","W","X","Y","Z"};
    private static CityManager manager;
    private String json = "";
    private CityManager(Context context){
        try {
            InputStreamReader inputReader = new InputStreamReader( context.getResources().getAssets().open("china_city.txt") );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line ="";
            while((line = bufReader.readLine()) != null)
                json += line.trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  static CityManager getInstance(Context context){
        if (manager == null){
            manager = new CityManager(context);
        }
        return  manager;
    }
    public  ArrayList<City> getCityList(){
        LogUtil.log("getCityList","json == "+json.trim());
        ArrayList<City> cities = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(json.trim());
            for (String k : key){
                LogUtil.log("getCityList","key == "+k);
                JSONArray array = object.getJSONArray(k);
                for (int i =  0; i < array.length();i++){
                    String city = array.getString(i);
                    cities.add(new City(city,k));
                    LogUtil.log("getCityList","city == "+city);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cities;
    }

}
