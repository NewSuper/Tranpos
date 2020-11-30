package com.newsuper.t.consumer.function.cityinfo.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/5/18 0018.
 */

public abstract class BaseCityInfoFragment extends Fragment{
    public static final int ACCESS_FINE_LOCATION_CODE = 1111;
    View footerView;
    TextView tvFooter;
    boolean isBottom,isLoadingMore = true;
    //判断是否定位权限
    public boolean isGetLocationPermission(){
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION )
                != PackageManager.PERMISSION_GRANTED) {
            //申请ACCESS_FINE_LOCATION权限
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION },
                    ACCESS_FINE_LOCATION_CODE);
            return false;
        }
        return true;
    }

    public void requestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){

    }

}
