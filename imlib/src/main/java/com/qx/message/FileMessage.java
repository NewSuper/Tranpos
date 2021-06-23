package com.qx.message;


import android.os.Parcel;

public class FileMessage extends MessageContent {


    //本地路径
    private String localPath;
    private long size;
    // 文件资源地址
    private String originUrl;
    private String fileName;
    private String type;
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


    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FileMessage(String localPath , long size , String fileName, String originUrl, String type ) {
        this.localPath = localPath;
        this.size = size;
        this.fileName = fileName;
        this.originUrl = originUrl;
        this.type = type;
    }
    protected FileMessage(Parcel in) {
        this.localPath = in.readString();
        this.size = in.readLong();
        this.fileName = in.readString();
        this.originUrl = in.readString();
        this.type = in.readString();
        this.extra = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(localPath);
        dest.writeLong(size);
        dest.writeString(fileName);
        dest.writeString(originUrl);
        dest.writeString(type);
        dest.writeString(extra);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FileMessage> CREATOR = new Creator<FileMessage>() {
        @Override
        public FileMessage createFromParcel(Parcel in) {
            return new FileMessage(in);
        }

        @Override
        public FileMessage[] newArray(int size) {
            return new FileMessage[size];
        }
    };

    @Override
    public String toString() {
        return "FileMessage{" +
                "localPath='" + localPath + '\'' +
                ", size=" + size +
                ", originUrl='" + originUrl + '\'' +
                ", fileName='" + fileName + '\'' +
                ", type='" + type + '\'' +
                ", extra='" + extra + '\'' +
                '}';
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
