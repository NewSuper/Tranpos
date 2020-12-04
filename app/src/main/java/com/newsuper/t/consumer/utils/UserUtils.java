package com.newsuper.t.consumer.utils;


import android.text.TextUtils;

import com.newsuper.t.consumer.bean.UserEntity;

public class UserUtils {
    public static void saveUserInfo(UserEntity user){
        if(user == null){
            return;
        }
     String strUser = JsonUtils.toJson(user);
        SharedPreferencesUtil.saveRongYunUser(strUser);
    }

    public static UserEntity getUserInfo(){
       String strUser = SharedPreferencesUtil.getRongYunUser();
        if(TextUtils.isEmpty(strUser)){
            return null;
        }
        UserEntity userInfo = JsonUtils.parseObj(strUser, UserEntity.class);
        return userInfo;
    }
}
