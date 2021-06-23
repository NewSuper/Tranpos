package com.newchao.imlib.message;


import android.os.Parcel;

public class GeoMessage extends MessageContent {

    // 标题
    private String title;
    //地址
    private String address;
    private String previewUrl;
    private String localPath;
    private float lon;// 经度
    private float lat;// 维度
    private String extra = "";

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public GeoMessage(String title, String address, String previewUrl, String localPath, float lon, float lat ) {
        this.title = title;
        this.address = address;
        this.previewUrl = previewUrl;
        this.localPath = localPath;
        this.lon = lon;
        this.lat = lat;
    }
    protected GeoMessage(Parcel in) {
        title = in.readString();
        address = in.readString();
        previewUrl = in.readString();
        localPath = in.readString();
        lon = in.readFloat();
        lat = in.readFloat();
        extra = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(address);
        dest.writeString(previewUrl);
        dest.writeString(localPath);
        dest.writeFloat(lon);
        dest.writeFloat(lat);
        dest.writeString(extra);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GeoMessage> CREATOR = new Creator<GeoMessage>() {
        @Override
        public GeoMessage createFromParcel(Parcel in) {
            return new GeoMessage(in);
        }

        @Override
        public GeoMessage[] newArray(int size) {
            return new GeoMessage[size];
        }
    };

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return "GeoMessage{" +
                "title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", previewUrl='" + previewUrl + '\'' +
                ", localPath='" + localPath + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                ", extra='" + extra + '\'' +
                '}';
    }
}
