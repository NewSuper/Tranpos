package com.newsuper.t.juejinbao.ui.ad;

import android.util.Log;

import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.ui.home.entity.ADConfigEntity;

import io.paperdb.Paper;

import static io.paperdb.Paper.book;

public class ADSwitchUtil {
    private static final String TAG = "ADSwitchUtil";

    /**
     * 计算下一个激励视频类型
     *
     * @return
     */
    public static int calculateStimulateType() {
        ADConfigEntity adConfigEntity = book().read(PagerCons.KEY_AD_CONFIG);
        int totalNum = book().read(PagerCons.KEY_SWITCH_STIMULATE_TOTAL_NUMBER, 1);

        int stimulateType = 0;
        int csjNum = 0;
        int gdtNum = 0;
        ADConfigEntity.DataBean.AdType3Bean ad_type3 = new ADConfigEntity.DataBean.AdType3Bean();
        if (adConfigEntity != null) {
            ad_type3 = adConfigEntity.getData().getAd_type3();
        }
        // 使用此值决定切换方式:0客户端算法切换, 1服务端算法切换
        if (ad_type3.getType() == 1) {
            // type=1时使用此值
            stimulateType = ad_type3.getPlatform();
        } else {

            if (ad_type3.getPlatform_list() == null) {
                return 0;
            }
            if (ad_type3.getPlatform_list().size() == 1) {

                stimulateType = ad_type3.getPlatform_list().get(0).getPlatform();

            } else {

                for (int i = 0; i < ad_type3.getPlatform_list().size(); i++) {
                    if (ad_type3.getPlatform_list().get(i).getPlatform() == 0) {
                        csjNum = ad_type3.getPlatform_list().get(i).getRadio_num();
                    }
                    if (ad_type3.getPlatform_list().get(i).getPlatform() == 1) {
                        gdtNum = ad_type3.getPlatform_list().get(i).getRadio_num();
                    }
                }
                if (totalNum <= csjNum && csjNum != 0) {
                    stimulateType = 0;
                } else if (gdtNum != 0) {
                    stimulateType = 1;
                } else {
                    stimulateType = 0;
                }
            }
        }
        if (totalNum >= (csjNum + gdtNum)) {
            Paper.book().write(PagerCons.KEY_SWITCH_STIMULATE_TOTAL_NUMBER, 1);
        } else {
            Paper.book().write(PagerCons.KEY_SWITCH_STIMULATE_TOTAL_NUMBER, totalNum + 1);
        }

        Log.i(TAG, "穿山甲: " + csjNum + "----- gdtNum" + gdtNum + "----- currentNum" + totalNum);
        Log.i(TAG, "播放平台: " + stimulateType);
        return stimulateType;
    }

    /**
     * 计算下一个banner平台类型
     *
     * @return
     */
    public static int calculateBannerType() {
        ADConfigEntity adConfigEntity = book().read(PagerCons.KEY_AD_CONFIG);
        int totalNum = book().read(PagerCons.KEY_SWITCH_BANNER_TOTAL_NUMBER, 1);

        int stimulateType = 0;
        int csjNum = 0;
        int gdtNum = 0;
        ADConfigEntity.DataBean.AdType4Bean ad_type4 = new ADConfigEntity.DataBean.AdType4Bean();
        if (adConfigEntity != null) {
            ad_type4 = adConfigEntity.getData().getAd_type4();
        } else {
            return 0;
        }
        // 使用此值决定切换方式:0客户端算法切换, 1服务端算法切换
        if (ad_type4.getType() == 1) {
            // type=1时使用此值
            stimulateType = ad_type4.getPlatform();
        } else {

            if (ad_type4.getPlatform_list() == null) {
                return 0;
            }
            if (ad_type4.getPlatform_list().size() == 1) {

                stimulateType = ad_type4.getPlatform_list().get(0).getPlatform();

            } else {

                for (int i = 0; i < ad_type4.getPlatform_list().size(); i++) {
                    if (ad_type4.getPlatform_list().get(i).getPlatform() == 0) {
                        csjNum = ad_type4.getPlatform_list().get(i).getRadio_num();
                    }
                    if (ad_type4.getPlatform_list().get(i).getPlatform() == 1) {
                        gdtNum = ad_type4.getPlatform_list().get(i).getRadio_num();
                    }
                }
                if (totalNum <= csjNum && csjNum != 0) {
                    stimulateType = 0;
                } else if (gdtNum != 0) {
                    stimulateType = 1;
                } else {
                    stimulateType = 0;
                }
            }
        }
        if (totalNum >= (csjNum + gdtNum)) {
            Paper.book().write(PagerCons.KEY_SWITCH_BANNER_TOTAL_NUMBER, 1);
        } else {
            Paper.book().write(PagerCons.KEY_SWITCH_BANNER_TOTAL_NUMBER, totalNum + 1);
        }

        Log.i(TAG, "穿山甲: " + csjNum + "----- gdtNum" + gdtNum + "----- currentNum" + totalNum);
        Log.i(TAG, "播放平台: " + stimulateType);
        return stimulateType;
    }
}
