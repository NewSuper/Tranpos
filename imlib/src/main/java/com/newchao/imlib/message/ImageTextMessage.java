package com.newchao.imlib.message;


import android.os.Parcel;

public class ImageTextMessage extends MessageContent {


    private String title;// 标题
    private String content;// 内容
    private String imageUrl;// 图片地址
    private String redirectUrl;// 链接跳转地址
    private String tag;// 来源
    private String extra = "";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public ImageTextMessage(String title, String content, String imageUrl  , String redirectUrl, String tag) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.redirectUrl = redirectUrl;
        this.tag = tag;

    }
    protected ImageTextMessage(Parcel in) {
        this.title = in.readString();
        this.content = in.readString();
        this.imageUrl = in.readString();
        this.redirectUrl = in.readString();
        this.tag = in.readString();
        this.extra = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(imageUrl);
        dest.writeString(redirectUrl);
        dest.writeString(tag);
        dest.writeString(extra);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImageTextMessage> CREATOR = new Creator<ImageTextMessage>() {
        @Override
        public ImageTextMessage createFromParcel(Parcel in) {
            return new ImageTextMessage(in);
        }

        @Override
        public ImageTextMessage[] newArray(int size) {
            return new ImageTextMessage[size];
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
        return "ImageTextMessage{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", redirectUrl='" + redirectUrl + '\'' +
                ", tag='" + tag + '\'' +
                ", extra='" + extra + '\'' +
                '}';
    }
}
