package com.newsuper.t.juejinbao.base;

public class PagerCons {
    //首页频道缓存信息
    public static final String CHANNEL_CHCHE = "channelCache";
    //图集频道缓存信息
    public static final String PICTURE_CHCHE = "pictureCache";
    //视频频道缓存信息
    public static final String VIDEO_CHANNEL_CHCHE = "videoChannelCache";
    //广告类型缓存
    public static final String AD_TYPE_CHCHE = "adTypeCache";

    //是否有领鸡蛋任务
    public static final String HAS_TASK_RECEIVE_EGGS = "has_task_receive_eggs";
    //是否完成领鸡蛋任务1
    public static final String FINISH_TASK1_RECEIVE_EGGS = "finish_task1_receive_eggs";
    //是否完成领鸡蛋任务2
    public static final String FINISH_TASK2_RECEIVE_EGGS = "finish_task2_receive_eggs";

    //用户所有信息
    public static final String USER_DATA = "userData";
    public static final String PERSONAL = "personal";
    public static final String SMALL_VIDEO_DATA = "video_data";
    //推送状态1打开，2关闭
    public static final String PUSH_TYPE = "push_type";
    //是否显示大礼包图片
    public static final String ISSHOWBIGGIFT = "isShowGift";
    //记录大礼包弹出时间
    public static final String BIGGIFTTIME = "giftTime";

    //记录首页弹框广告弹出时间
    public static final String KEY_HOME_DIALOG_TIME = "homeDialogTime";
    //首页弹框广告弹出间隔时间
    public static final String KEY_HOME_DIALOG_INTERVAL_TIME = "homeDialogIntervalTime";

    //获取金币弹框
    public static final String KEY_GET_COIN_POP = "key_get_coin_pop";
    public static final String KEY_REWARD_60_MIN = "key_get_coin_pop";

    //首次进入
    public static final String WELCOME = "welcome";

    //首页30分钟奖励引导
    public static final String NEW_TASK_TRIANGLE = "new_task_triangle";

    //首页引导
    public static final String SHOUYE_MOVIE_GUIDE = "shouye_movie_guide";
    public static final String SHOUYE_ZHUANFANG_GUIDE = "shouye_zhuanfang_guide";
    public static final String SHOUYE_RENWU_GUIDE = "shouye_renwu_guide";

    //分享配置缓存(进入app时下载缓存到本地)
    public static final String SHARE_CONFIG = "shareConfig";


    /**
     * 下面用于控制规定时间内广告显示次数
     */
    // 最后一次显示的时间
    public static final String LAST_SHOW_AD_TINE = "lastShowAdTime";
    // 规定时间内已显示的广告次数
    public static final String SHOW_TIMES_BY_LIMIT = "showTimesByLimit";

    public static final String KEY_IS_FIRST_SHARE = "isFirstShare";

    //是否显示小视频操作指引
    public static final String KEY_IS_FIRST_VIDEO = "isFirstPlay";


    //文章阅读历史
    public static final String KEY_READOBJECT = "readObjecy";

    //首页掘金宝频道数据缓存
    public static final String KET_HOME_CHANNEL_DATA = "homeChannelData";

    //切换到后台的时间缓存
    public static final String KET_BACKGROUND_TIME = "backGroundTime";

    //上次退出app的时间
    public static final String KET_EXIT_APP_TIME = "exitAppTime";

    /**
     * 需要将后台给的间隔时间存入缓存
     */
    //切换到后台需要显示广告的间隔时间
    public static final String KET_BACKGROUND_INTERVAL_TIME = "backGroundIntervalTime";
    //退出之后再进入后显示广告的间隔时间
    public static final String KET_BACK_INTERVAL_TIME = "backIntervalTime";

    //首页视频列表上次刷到的数据
    public static final String KET_HOME_VIDEO_LASTTIME_DATA = "homeVideoListTimeData";
    public static final String KET_PICTURE = "picture";

    //是否开启无感广告
    public static final String KEY_SHOW_SENSELESS_AD = "showSenselessAd";
    //无感广告上次弹出时间
    public static final String KEY_SENSELESS_AD_LAST_OPEN_TIME = "senselessAdLastOpenTime";

    //离线随机参数缓存
    public static final String KE_UA = "key_ua";

    //搜索历史
    public static final String HOME_HISTORY = "search_history";
    //精彩小视频缓存
    public static final String SMALL_VIDEO = "small_video";

    //首页时间戳
    public static final String HOME_TIME = "homeTime";
    //开始播放时间
    public static final String PLAYSTART = "play_start";
    //视频时长
    public static final String PLAYCURRENT = "play_current";

    //影视视频进度保存
    public static final String PLAYMOVIEPROGRES = "play_movieprogres";

    //阅读文章奖励弹框样式 三个样式
    public static final String ACTICLE_REWARD_STYLE = "acticleRewardStyle";

    //本次启动阅读图集次数
    public static final String ACTICLE_REWARD_SCANPICTURE_NUMBER = "acticleRewardScanPictureNumber";

    //阅读获得奖励的次数设定
    public static final String ACTICLE_REWARD_SCANPICTURE_REWARD_NUMBER = "acticleRewardScanPictureRewardNumber";

    //是否展示遮罩
    public static final String KEY_ARTICLE_IS_SHOW_GUIDE = "articleIsShowGuide";

    //是否展示设置文字
    public static final String KEY_TEXTSET_IS_SHOW_GUIDE = "textSetIsShowGuide";

    //字体设置的大小
    public static final String KEY_TEXTSET_SIZE = "textSetSize";

    //看小视频的数量
    public static final String KEY_SMALLVIDEO_COUNT = "smallVideoCount";

    //帮web存数据
    public static final String KEY_SAVEDATABYWEB = "savedatabyweb";
    //首页列表缓存上一次的数据
    public static final String KEY_HOME_CACHE_LAST_TIME = "homeCacheLastTime";
    //掘金宝Banner数据缓存
    public static final String KEY_HOME_CACHE_BANNER = "homeCacheBanner";

    //影视搜索js抓取数据缓存
    public static final String KEY_MOVIESEARCH_JS_DATA = "moviesearch_js_data";

    //影视搜索跑马灯每天
//    public static final String KEY_MOVIESEARCH_PMD = "moviesearch_pmd";
    //影视搜索跑马灯每天进入
    public static final String KEY_MOVIESEARCH_PMDIN = "moviesearch_pmdin";

    //首页信息流广告间隔
    public static final String KEY_INTERVAL_HOME_PAGE_AD = "intervalHomePageAd";
    //小视频广告间隔
    public static final String KEY_INTERVAL_SMALLVIDEO_AD = "intervalSmallvideoAd";

    //小视频最后的广告数据保存
    public static final String KEY_SMALLVIDEO_AD_CACHE = "smallvideo_ad_cache";

    //非首次进入播放器
    public static final String KEY_MOVIEPLAY_NOFIRST = "movieplay_nofirst";

    // 分享图片保存文件(模板)
    public static final String KEY_SHARE_PICS = "sharePics";

    //是否开启app防护
    public static final String KEY_IS_OPEN_APP_PROTECT = "isOpenAppProtect";

    //是否开启app防护
    public static final String KEY_CLOSE_APP_PROTECT = "closeAppProtect";

    //信息流广告商
    public static final String KEY_AD_TYPE = "advertise_type";

    //广告配置
    public static final String KEY_AD_CONFIG = "ad_config";

    // 首頁頭部背景圖
    public static final String KEY_HOME_TITLE_BG = "homeTitleBg";

    // 影视頭部背景圖
    public static final String KEY_VIP_TITLE_BG = "vipTitleBg";

    // 影视搜索引导
    public static final String KEY_MOVIESEARCH_GUIDE = "key_moviesearch_guide";

    //影视播放次数（每天第二次观看需要分享才可使用）
    public static final String PLAY_MOVIE_TIMES = "playMovieTimes";

    //影视播放的时间
    public static final String PLAY_MOVIE_FIRST_TIME = "playMovieFirstTime";

    //影视播放保存分享信息
    public static final String PLAY_MOVIE_SHARE_INFO = "playMovieShareInfo";

    // 记录阅读文章倒计时
    public static final String KEY_COUNT_DOWN_TIME = "key_count_down_time";

    // 记录观看短视频倒计时
    public static final String KEY_VIDEO_COUNT_DOWN_TIME = "key_video_count_down_time";


    //定时关闭时间戳
    public static final String KEY_PLAYAPP_DSGB = "key_playapp_dsgb";

    // 影视搜索引导 - 天（几号）
    public static final String KEY_MOVIESEARCH_GUIDE_Day = "key_moviesearch_guide_day";

    //后台配置 当天观看影视第n 次需要分享
    public static final String KEY_MOVIE_PLAY_COUNR_NEED_SHARE = "moviePlayCountNeedShare";

    //进入影视模块时间
    public static final String INTO_MOVIE_TIME = "into_movie_time";

    //是否首次安装 并跳转至赚车赚房页面
    public static final String KEY_IS_FIRST_OPEN_APP_JUMP_ZCZF = "isFirstOpenAppJumpZczf";

    //是否关闭开屏广告
    public static final String KEY_IS_CLOSE_OPEN_AD = "isCloseOpenAd";

    //记录沉浸式视频点赞数
    public static final String KEY_GIVE_LIKE_LIST = "give_like_list";

    //openInstall中获取到的邀请码   为空时代表无锁粉邀请码  为used代表已锁粉
    public static final String KEY_INVATECODE_FROM_OPENINSTALL = "inveteCodeFromOpenInstall";

    //登录UI参数
    public static final String KEY_GUIDELOGIN_UI = "key_guidelogin_ui";

    //文章详情分享引导弹框出现时间
    public static final String KEY_LEAD_ARTICLE_SHARE_TIME = "keyArticleShareTime";
    //观影历史
//    public static final String KEY_MOVIEHISTORY_SAVE = "key_moviehistory_save";
    //观影历史2
    public static final String KEY_MOVIEHISTORY_SAVE2 = "key_moviehistory_save2";

    //夜间模式
    public static final String KEY_NIGHT_MODE = "keyNightMode";

    //播放视频页面数据跳转
    public static final String KEY_PLAYMOVIE_DATA = "keyPlayMovieData";

    //当前展开任务
    public static final String SELECT_TAG = "selectTag";

    //是否第一次进入阅读页面
    public static final String KEY_IS_READ_FIRST = "key_is_read_first";

    //绕审数据保存
    public static final String KEY_AUDIT_MAP = "keyAuditMAP";

    //下次广告展示控制（目前实现激励视频方案为：穿山甲-广点通 等比例切换）
    public static final String KEY_SWITCH_STIMULATE_NEXT = "keySwitchStimulatenNext";

    //切换比例
    public static final String KEY_SWITCH_STIMULATE_RADIO = "keySwitchStimulateRadio";

    //当前激励视频总数
    public static final String KEY_SWITCH_STIMULATE_TOTAL_NUMBER = "keySwitchStimulateNumber";

    //当前banner广告总数
    public static final String KEY_SWITCH_BANNER_TOTAL_NUMBER = "keySwitchBannerNumber";

    //激励视频广告切换控制（0 穿山甲 1 广点通 2 动态切换）
    public static final String KEY_SWITCH_STIMULATE = "keySwitchStimulaten";

    //基础配置数据
    public static final String KEY_JJB_CONFIG = "keyJJBConfig";

    //P2P引导
    public static final String KEY_MOVIE_P2P_GUIDE = "key_movie_p2p_guide";

    //首页搜索历史
    public static final String KEY_HOMESEARCH_HISTORY = "key_homesearch_history";

    public static final String KEY_HOME_SEARCH_HISTORY = "key_home_search_history";


    /******************************** 音乐 ***********************************/
    //播放音乐下标数据
    public static final String KEY_CURRENT_SONG_INDEX = "key_current_song_index";
    //喜欢列表
    public static final String KEY_MUSIC_LIKE_LIST = "keyMusicLikeList";
    //喜欢列表
    public static final String KEY_MUSIC_SEARCH_LIST = "SearchMusicHistoryList";

    //歌单收藏列表
    public static final String KEY_GEDAN_LIKE_LIST = "key_gedan_like_list";

    //首次进入任务 赚钱模式引导
    public static final String KEY_TASK_WELCOME_EARN_FIRST = "KEY_TASK_WELCOME_EARN_FIRST";
}


