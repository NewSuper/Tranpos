package com.newchao.imlib.message;


import android.os.Parcel;

public class AudioMessage extends MessageContent {
    //本地路径
    private String localPath;
    private String originUrl;
    private long size;
    // 音频时长
    private int duration;
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

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public AudioMessage(String localPath, int duration, long size, String originUrl ) {
        this.localPath = localPath;
        this.originUrl = originUrl;
        this.duration = duration;
        this.size = size;
    }
    protected AudioMessage(Parcel in) {
        localPath = in.readString();
        originUrl = in.readString();
        duration = in.readInt();
        size = in.readLong();
        extra = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(localPath);
        dest.writeString(originUrl);
        dest.writeInt(duration);
        dest.writeLong(size);
        dest.writeString(extra);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AudioMessage> CREATOR = new Creator<AudioMessage>() {
        @Override
        public AudioMessage createFromParcel(Parcel in) {
            return new AudioMessage(in);
        }

        @Override
        public AudioMessage[] newArray(int size) {
            return new AudioMessage[size];
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
        return "AudioMessage{" +
                "localPath='" + localPath + '\'' +
                ", originUrl='" + originUrl + '\'' +
                ", size=" + size +
                ", duration=" + duration +
                ", extras='" + extra + '\'' +
                '}';
    }
}
