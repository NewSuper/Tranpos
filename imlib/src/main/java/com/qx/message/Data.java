package com.qx.message;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author : zhangjingming
 * e-mail : jimstin@126.com
 * date   : 2020/11/20 14:40
 * desc   :
 * version: 1.0
 */
public class Data implements Parcelable {
    protected Data(Parcel in) {
    }

    public Data() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public void readFromParcel(Parcel in) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}
