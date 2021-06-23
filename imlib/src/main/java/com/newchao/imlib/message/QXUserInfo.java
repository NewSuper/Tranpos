package com.newchao.imlib.message;


import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class QXUserInfo implements Parcelable {
    private String id;
    private String name;
    private String noteName;
    private Uri avatarUri;
    private String extra;
    // 昵称图片地址
    private String nameExtraUrl;
    // 头像角标地址
    private String avatarExtraUrl;

    public QXUserInfo() {

    }

    protected QXUserInfo(Parcel in) {
        id = in.readString();
        name = in.readString();
        noteName = in.readString();
        avatarUri = in.readParcelable(Uri.class.getClassLoader());
        extra = in.readString();
        nameExtraUrl = in.readString();
        avatarExtraUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(noteName);
        dest.writeParcelable(avatarUri, flags);
        dest.writeString(extra);
        dest.writeString(nameExtraUrl);
        dest.writeString(avatarExtraUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QXUserInfo> CREATOR = new Creator<QXUserInfo>() {
        @Override
        public QXUserInfo createFromParcel(Parcel in) {
            return new QXUserInfo(in);
        }

        @Override
        public QXUserInfo[] newArray(int size) {
            return new QXUserInfo[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public String getDisplayName() {
        if(TextUtils.isEmpty(noteName)) {
            return name;
        }
        return noteName;
    }

    public Uri getAvatarUri() {
        return avatarUri;
    }

    public void setAvatarUri(Uri avatarUri) {
        this.avatarUri = avatarUri;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getNameExtraUrl() {
        return nameExtraUrl;
    }

    public void setNameExtraUrl(String nameExtraUrl) {
        this.nameExtraUrl = nameExtraUrl;
    }

    public String getAvatarExtraUrl() {
        return avatarExtraUrl;
    }

    public void setAvatarExtraUrl(String avatarExtraUrl) {
        this.avatarExtraUrl = avatarExtraUrl;
    }
}
