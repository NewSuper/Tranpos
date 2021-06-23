package com.newchao.imlib.message;


import android.os.Parcel;

public class VideoMessage extends MessageContent {

    //本地路径
    private String localPath;
    private long size;
    // 音频时长
    private int duration;
    // 视频首图地址
    private String headUrl;
    // 视频资源地址
    private String originUrl;
    // 视频宽度
    private int width;
    // 视频高度
    private int height;

    private String extra = "";

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
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }



    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
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

    public VideoMessage(String localPath, int duration, long size ,String headUrl,String originUrl,int width,int height) {
        this.localPath = localPath;
        this.duration = duration;
        this.size = size;
        this.headUrl = headUrl;
        this.originUrl = originUrl;
        this.width = width;
        this.height = height;

    }
    protected VideoMessage(Parcel in) {
        this.localPath = in.readString();
        this.duration = in.readInt();
        this.size = in.readLong();
        this.headUrl = in.readString();
        this.originUrl = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.extra = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(localPath);
        dest.writeInt(duration);
        dest.writeLong(size);
        dest.writeString(headUrl);
        dest.writeString(originUrl);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeString(extra);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VideoMessage> CREATOR = new Creator<VideoMessage>() {
        @Override
        public VideoMessage createFromParcel(Parcel in) {
            return new VideoMessage(in);
        }

        @Override
        public VideoMessage[] newArray(int size) {
            return new VideoMessage[size];
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
        return "VideoMessage{" +
                "localPath='" + localPath + '\'' +
                ", size=" + size +
                ", duration=" + duration +
                ", headUrl='" + headUrl + '\'' +
                ", originUrl='" + originUrl + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", extra='" + extra + '\'' +
                '}';
    }
}
