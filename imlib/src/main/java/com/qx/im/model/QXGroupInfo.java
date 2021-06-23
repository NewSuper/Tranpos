package com.qx.im.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class QXGroupInfo implements Parcelable {

    private String id;
    private String name;
    private int memberCount;
    private Uri avatarUri;
    private int isBan;
    // 昵称图片地址
    private String nameExtraUrl;
    // 头像角标地址
    private String avatarExtraUrl;

    public QXGroupInfo() {

    }

    public boolean getIsBan() {
        return isBan == 0 ? false : true;
    }

    public void setIsBan(int isBan) {
        this.isBan = isBan;
    }

    protected QXGroupInfo(Parcel in) {
        id = in.readString();
        name = in.readString();
        memberCount = in.readInt();
        avatarUri = in.readParcelable(Uri.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeInt(memberCount);
        dest.writeParcelable(avatarUri, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QXGroupInfo> CREATOR = new Creator<QXGroupInfo>() {
        @Override
        public QXGroupInfo createFromParcel(Parcel in) {
            return new QXGroupInfo(in);
        }

        @Override
        public QXGroupInfo[] newArray(int size) {
            return new QXGroupInfo[size];
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

    public Uri getAvatarUri() {
        return avatarUri;
    }

    public void setAvatarUri(Uri avatarUri) {
        this.avatarUri = avatarUri;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
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
