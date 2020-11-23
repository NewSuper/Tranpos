package com.newsuper.t.juejinbao.utils;

import android.util.Log;

//import com.bytedance.sdk.openadsdk.TTFeedAd;

import java.util.ArrayList;
import java.util.List;

public class ADUtils {


    /**
     * 去除穿山甲重复的信息流广告
     * @param ads  接口返回的
     * @param mTTFeedAds 已加载的
     * @return
     */
    public static List<TTFeedAd> removeDuplicate(List<TTFeedAd> ads , List<TTFeedAd> mTTFeedAds) {
        try {
            //先去除接口本身返回的
            for (int i= 0; i < ads.size(); i++) {
                TTFeedAd index = ads.get(i);
//                L.w("ADUtils", "removeDuplicate: 广告接口返回"+ (i+1) +" = "+index.getTitle());
                for (int n = i+1; n < ads.size(); n++){
                    TTFeedAd sort = ads.get(n);
                    if (index.getTitle().trim().equals(sort.getTitle().trim())){
                        Log.w("ADUtils", "removeDuplicate: 广告接口返回重复了1= "+index.getTitle());
                        ads.remove(n);
                        n--;
                    }
                }
            }

            if (mTTFeedAds.size() == 0){
//                for (TTFeedAd ad: ads) L.w("ADUtils", "removeDuplicate: 广告接口过滤后有 = "+ ad.getTitle());
                return ads;
            }

            //再去除和已加载重复的
            List<TTFeedAd> mutableAD = new ArrayList<>();
            for (int i = 0; i < ads.size(); i++){
                TTFeedAd ad = ads.get(i);
                for (TTFeedAd feedAd :mTTFeedAds){
                    if (feedAd.getTitle().equals(ad.getTitle())){
                        Log.w("ADUtils", "removeDuplicate: 重复了= "+ad.getTitle());
    //                    if (temps.size() - mutableAD.size() > 1) //至少保留一条时
                            mutableAD.add(ad);
                        break;
                    }
                }
            }
            ads.removeAll(mutableAD); //移除重复的

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ads;
    }
}
