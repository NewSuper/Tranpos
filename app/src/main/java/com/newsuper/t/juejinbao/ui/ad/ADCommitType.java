package com.newsuper.t.juejinbao.ui.ad;


public class ADCommitType {

    //广告埋点Key
    /**
     * 广告贡献类型：ad_contribute_type
     * {"show_partner":"\u7a7f\u5c71\u7532\u5c55\u793a","click_partner":"\u7a7f\u5c71\u7532\u70b9\u51fb","show_eqq":"\u5e7f\u70b9\u901a\u5c55\u793a","click_eqq":"\u5e7f\u70b9\u901a\u70b9\u51fb","show_jjb":"activaty_show_jjb_count","click_jjb":"activaty_click_jjb_count"}
     *
     * secretKey:赚TR掘22a6&金e76_PO306宝Android#c5d7@车房
     * 签名算法：md5(md5(token+expire)+secretKey+version+ad_contribute_type)
     * version:1.8.7
     * user_token:5c2dafb101e0bb6d0da35669eb8341f2
     * nowTime:1565406392
     * expire:1565406692
     * ad_contribute_type:show_partner
     * signature:e9d6b2004aaef4756a89a8b77105f7af
     * url:http://dev.api.juejinchain.cn/v1/misc/add_ad_contribute&signature=e9d6b2004aaef4756a89a8b77105f7af&version=1.8.7&expire=1565406692&ad_contribute_type=show_partner&source_style=7&uid=5997&user_token=5c2dafb101e0bb6d0da35669eb8341f2&device_type=&channel=other
     */

    public static final String AD_SECREKEY = "赚TR掘22a6&金e76_PO306宝Android#c5d7@车房";

    /**
     * 穿山甲展示
     */
    public static final String AD_TYPE_CSJ_SHOW = "show_partner";
    /**
     * 穿山甲点击
     */
    public static final String AD_TYPE_CSJ_CLICK = "click_partner";
    /**
     * 广点通展示
     */
    public static final String AD_TYPE_GDT_SHOW = "show_eqq";
    /**
     * 广点通点击
     */
    public static final String AD_TYPE_GDT_CLICK = "click_eqq";

    /**
     * 游戏点击
     */
    public static final String AD_TYPE_GAME_CLICK = "click_game";


    /**
     * 自有
     */
    public static final String AD_TYPE_JJB_SHOW= "show_jjb";
    public static final String AD_TYPE_JJB_CLICK = "click_jjb";

}
