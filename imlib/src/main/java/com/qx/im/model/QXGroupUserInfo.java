package com.qx.im.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class QXGroupUserInfo implements Parcelable {

    private String userId;
    private String displayName;
    private String nickName;//昵称
    private String groupNickName;//群昵称
    private String noteName;//备注名称
    private String groupId;
    private Uri avatarUri;
    // 昵称图片地址
    private String nameExtraUrl;
    // 头像角标地址
    private String avatarExtraUrl;

    private int isAdmin;

    public boolean getIsAdmin() {
        return isAdmin == 0 ? false : true;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public QXGroupUserInfo() {

    }


    protected QXGroupUserInfo(Parcel in) {
        userId = in.readString();
        nickName = in.readString();
        groupId = in.readString();
        avatarUri = in.readParcelable(Uri.class.getClassLoader());
        nameExtraUrl = in.readString();
        avatarExtraUrl = in.readString();
    }

    public static final Creator<QXGroupUserInfo> CREATOR = new Creator<QXGroupUserInfo>() {
        @Override
        public QXGroupUserInfo createFromParcel(Parcel in) {
            return new QXGroupUserInfo(in);
        }

        @Override
        public QXGroupUserInfo[] newArray(int size) {
            return new QXGroupUserInfo[size];
        }
    };


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGroupNickName() {
        return groupNickName;
    }

    public void setGroupNickName(String groupNickName) {
        this.groupNickName = groupNickName;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Uri getAvatarUri() {
        return avatarUri;
    }

    public void setAvatarUri(Uri avatarUri) {
        this.avatarUri = avatarUri;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        displayName = getNoteName();
        if(TextUtils.isEmpty(displayName)) {
            displayName = getGroupNickName();
        }
        if(TextUtils.isEmpty(displayName)) {
            displayName = getNickName();
        }
        return displayName;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(nickName);
        dest.writeString(groupId);
        dest.writeParcelable(avatarUri, flags);
        dest.writeString(nameExtraUrl);
        dest.writeString(avatarExtraUrl);
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
