package com.qx.im.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class RTCServerConfig implements Parcelable {

    private List<String> iceServers;
    private String whiteDeveice;

    public RTCServerConfig() {

    }

    protected RTCServerConfig(Parcel in) {
        iceServers = in.createStringArrayList();
        whiteDeveice = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(iceServers);
        dest.writeString(whiteDeveice);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RTCServerConfig> CREATOR = new Creator<RTCServerConfig>() {
        @Override
        public RTCServerConfig createFromParcel(Parcel in) {
            return new RTCServerConfig(in);
        }

        @Override
        public RTCServerConfig[] newArray(int size) {
            return new RTCServerConfig[size];
        }
    };

    public List<String> getIceServers() {
        return iceServers;
    }

    public void setIceServers(List<String> iceServers) {
        this.iceServers = iceServers;
    }

    public String getWhiteDeveice() {
        return whiteDeveice;
    }

    public void setWhiteDeveice(String whiteDeveice) {
        this.whiteDeveice = whiteDeveice;
    }
}
