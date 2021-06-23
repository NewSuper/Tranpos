package com.newchao.imlib.message;


import android.os.Parcel;

public class ImageMessage extends MessageContent {


    //本地缩略图路径
    private String localPath;
    private String breviary_url;
    private long size;
    // 视频资源地址
    private String originUrl;
    //图片宽度
    private int width;
    // 图片高度
    private int height;
    private String extra = "";

    public String getBreviary_url() {
        return breviary_url;
    }

    public void setBreviary_url(String breviary_url) {
        this.breviary_url = breviary_url;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public ImageMessage(String localPath, String breviary_url, long size  , String originUrl, int width, int height) {
        this.localPath = localPath;
        this.breviary_url = breviary_url;
        this.size = size;
        this.originUrl = originUrl;
        this.width = width;
        this.height = height;

    }
    protected ImageMessage(Parcel in) {
        this.localPath = in.readString();
        this.breviary_url = in.readString();
        this.size = in.readLong();
        this.originUrl = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.extra = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(localPath);
        dest.writeString(breviary_url);
        dest.writeLong(size);
        dest.writeString(originUrl);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeString(extra);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImageMessage> CREATOR = new Creator<ImageMessage>() {
        @Override
        public ImageMessage createFromParcel(Parcel in) {
            return new ImageMessage(in);
        }

        @Override
        public ImageMessage[] newArray(int size) {
            return new ImageMessage[size];
        }
    };

    @Override
    public String toString() {
        return "ImageMessage{" +
                "localPath='" + localPath + '\'' +
                ", breviary_url='" + breviary_url + '\'' +
                ", size=" + size +
                ", originUrl='" + originUrl + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", extra='" + extra + '\'' +
                '}';
    }
}

