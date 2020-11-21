package com.newsuper.t.juejinbao.base;


import android.os.Environment;


import com.newsuper.t.juejinbao.utils.FileUtils;

import java.io.File;

public class Constant {
    //电影搜索历史
    public final static String MOVIE_SEARCH_HISTORY = "movie_search_history";

    //qq
    public static final String WEIXINAPPKEYQQ;
    public static final String WEIXINAPPPACKAGEQQ;

    //UC浏览器
    public static final String WEIXINAPPKEYUC;
    public static final String WEIXINAPPPACKAGEUC;

    //qq浏览器
    public static final String WEIXINAPPKEYQQBROWSER;
    public static final String WEIXINAPPPACKAGEQQBROWSER;

    //新浪
    public static final String WEIXINAPPKEYSINA;
    public static final String WEIXINAPPPACKAGESINA;

    //今日头条
    public static final String WEIXINAPPKEYNEWSTODAY;
    public static final String WEIXINAPPPACKAGENEWSTODAY;

    //百度
    public static final String WEIXINAPPKEYBAIDU;
    public static final String WEIXINAPPPACKAGEBAIDU;

    //大众点评
    public static final String WEIXINAPPKEYDIANPING;
    public static final String WEIXINAPPPACKAGEDIANPING;

    //大众点评
    public static final String WEIXINAPPKEYDAYANDDAY;
    public static final String WEIXINAPPPACKAGEDAYANDDAY;

    static {
        /**
         * qq的appId ,package
         */
        WEIXINAPPKEYQQ = "wxf0a80d0ac2e82aa7";
        WEIXINAPPPACKAGEQQ = "com.tencent.mobileqq";

        /**
         * UC
         */
        WEIXINAPPKEYUC = "wx020a535dccd46c11";
        WEIXINAPPPACKAGEUC = "com.UCMobile";

        /**
         * QQ浏览器
         */
        WEIXINAPPKEYQQBROWSER = "wx64f9cf5b17af074d";
        WEIXINAPPPACKAGEQQBROWSER = "com.tencent.mtt";

        /**
         * 微博
         */
        WEIXINAPPKEYSINA = "wx299208e619de7026";
        WEIXINAPPPACKAGESINA = "com.sina.weibo";

        /**
         * 今日头条
         */
        WEIXINAPPKEYNEWSTODAY = "wx50d801314d9eb858";
        WEIXINAPPPACKAGENEWSTODAY = "com.ss.android.article.news";

        /**
         * 百度
         */
        WEIXINAPPKEYBAIDU = "wx27a43222a6bf2931";
        WEIXINAPPPACKAGEBAIDU = "com.baidu.searchbox";

        /**
         * 大众点评
         */
        WEIXINAPPKEYDIANPING= "wxbf7b27e4789951c7";
        WEIXINAPPPACKAGEDIANPING = "com.ss.android.article.news";

        /**
         * 天天快报
         */
        WEIXINAPPKEYDAYANDDAY = "wxe90c9765ad00e2cd";
        WEIXINAPPPACKAGEDAYANDDAY = "com.tencent.reading";

    }



    public static final String BASE_APP_PATH = Environment.getExternalStorageDirectory() + "/1jjb";

    public static String PATH_DATA = FileUtils.createRootPath(JJBApplication.getContext()) + "/cache";
    //钱盟盟sdk
    public static final String QMM_APPID = "qmmf70b51169ae73be8";
    public static final String QMM_APPKEY = "444a139566156bd1de242109b2a0e486";

    //钉钉APP_ID
    public static final String DDINGG_APP_ID = "dingoanzsjkp8nm1cip6zv";
    public static final String UM_APP_ID = "5c650ff4b465f5ce66000ca5";
    public static final String WX_APP_ID = "wx8526e046cde9ed1b";
    public static final String WX_MINI_OLD_ID = "gh_61a6dcb4b991";
    public static final String WX_APP_KEY = "270be6c177911661af73e4e000bd0e9c";
    public static final String QQ_APP_ID = "1107908760";
    public static final String QQ_APP_KEY = "Y74N9Gsmb1SKEqEx";
    public static final String SINA_APP_ID = "1748224285";
    public static final String SINAAPP_KEY = "dbf563ecd43c53b802e65c3eab677ef6";
    public static final String SINAAPP_WEB = "http://open.weibo.com/apps/1748224285/privilege/oauth";

    public static final String FLIP_STYLE = "flipStyle";
    public static final String ISNIGHT = "isNight";
    public static String PATH_TXT = PATH_DATA + "/book/";
    public static String PATH_EPUB = PATH_DATA + "/epub";
    public static String PATH_CHM = PATH_DATA + "/chm";
    //BookCachePath (因为getCachePath引用了Context，所以必须是静态变量，不能够是静态常量)
    public static String BOOK_CACHE_PATH = FileUtils.getCachePath()+ File.separator
            + "book_cache"+ File.separator ;
    public static int FRAGMENT_TAB = 4;
    //1弹出，0不弹
    public static int IS_SHOW = 0;

    public static final int ONE = 1;
    public static final int TWO = 2;

    public static final String FROM_NEW_TASK_INTENT = "from_new_task_intent"; // 来自新手任务跳转

    public static final String MUSIC_COLLECTION = "collection"; // 收藏歌单
    public static final String MUSIC_HISTORY = "history"; // 历史歌单
    public static final String MUSIC_POPULAR = "popular";// 热门歌单
    public static final String MUSIC_SONGS= "songs";// 歌单
    public static final String MUSIC_ALBUM = "album";// 专辑
    public static final String MUSIC_RANK = "rank";// 榜单

    //过审数据Key
    public static final String  KEY_BOTTOM_CONTROL =  "keyBottomControl";
    public static final String  KEY_AD_CONTROL =  "keyAdControl";
    public static final String  KEY_MINE_MOVIE_REVIEW_CONTROL =  "keyMineMovieReviewControl";


    public static final String  YUYUETUI_APP_ID =  "329";
    public static final String  YUYUETUI_APP_KEY =  "787d1bd1c5f3e52";


    /**************************  H5单页面地址  *********************************************************/
    //关于我们
    public static final String WEB_ONLINE_ABOUT_US = "/juejinchain/html/personal_center/about.html";
    //我的钱包
    public static final String WEB_ONLINE_MY_WALLET = "/juejinchain/html/personal_center/myprofit.html";
    //生成邀请链接
    public static final String WEB_ONLINE_GET_SHORT_URL = "/juejinchain/html/task/shortUrl.html";
    //VIP解析页面
    public static final String WEB_ONLINE_VIP = "/juejinchain/html/movie/vip.html";
    //富豪榜
    public static final String WEB_ONLINE_RICH_LIST = "/juejinchain/html/rich_play/richList.html";
    //邀请好友
    public static final String WEB_ONLINE_INVITE_FRIEND = "/juejinchain/html/task/inviteFriend.html";
    //邀请人列表（原生无入口）
    public static final String WEB_ONLINE_INVITE_LIST = "/juejinchain/html/task/allInvite.html";
    //我的消息
    public static final String WEB_ONLINE_MY_MESSAGE = "/juejinchain/html/personal_center/message.html";
    //奖品列表
    public static final String WEB_ONLINE_REWARD_LIST = "/juejinchain/html/award_pag/index.html";
    //任务页面
    public static final String WEB_ONLINE_TASK = "/juejinchain/html/task/index.html";
    //手把手教你赚掘金宝
    public static final String WEB_ONLINE_TECH_EARN_MONEY = "/juejinchain/html/task/earnMoney.html";
    //赚钱秘籍
    public static final String WEB_ONLINE_TECH_SECRET_BOOK = "/juejinchain/html/task/secretBook.html";
    //赚车赚房
    public static final String WEB_ONLINE_TECH_RICH_PLAY = "/juejinchain/html/rich_play/richIndex.html";
    //我的-生成海报页面
    public static final String WEB_ONLINE_CREATE_POSTER = "/juejinchain/html/personal_center/poster.html";
    //交易入口（目前后台返回）
    public static final String WEB_ONLINE_RICH_PLAY_TRANSACTION = "/juejinchain/html/rich_play/transaction.html";
    //用户信息
    public static final String WEB_ONLINE_USER_INFO = "/juejinchain/html/personal_center/personalinfo.html";
    //用户等级（原生无入口）
    public static final String WEB_ONLINE_USER_GRADE = "/juejinchain/html/personal_center/mygrade-info.html";
    //阅读历史
    public static final String WEB_ONLINE_READ_HISTORY = "/juejinchain/html/personal_center/history.html";
    //我的收藏
    public static final String WEB_ONLINE_MY_COLLECTION = "/juejinchain/html/personal_center/collection.html";
    //用户反馈
    public static final String WEB_ONLINE_USER_FEED_BACK = "/juejinchain/html/personal_center/feedBackQuestion.html";
    //我的反馈
    public static final String WEB_ONLINE_MY_FEED_BACK = "/juejinchain/html/personal_center/feedback.html";
    //设置邀请码
    public static final String WEB_ONLINE_SET_INVATE_CODE = "/juejinchain/html/task/setInvite.html";
    //我的好友
    public static final String WEB_ONLINE_MY_FRIEND = "/juejinchain/html/personal_center/friend.html";
    //浏览金币
    public static final String WEB_ONLINE_TODAT_COIN = "/juejinchain/html/home/todayVcoin.html";
    //置顶文章页
    public static final String WEB_ONLINE_ACTICLE_TOP = "/juejinchain/html/home/browser.html?id=";
    //我的关注
    public static final String WEB_ONLINE_MY_CORCER = "/juejinchain/html/personal_center/focusCenter.html";
    //我的影评
    public static final String WEB_ONLINE_MY_MOVIECOMMENT = "/juejinchain/html/personal_center/movieComment.html";
    //媒体号详情
    public static final String WEB_ONLINE_MEDIACODEDETAIL = "/juejinchain/html/personal_center/mediaNumber.html?mid=";
    //影视提醒
    public static final String WEB_ONLINE_MOVIEALERT = "/juejinchain/html/personal_center/movieSubscribe.html";

    //影视详情
    public static final String WEB_ONLINE_MOVIEDETAIL = "/juejinchain/html/movie/movieDetail.html";
    //影视海报
    public static final String WEB_ONLINE_MOVIEPOSTER= "/juejinchain/html/movie/movieImage.html";
    //鲸看看详情
    public static final String WEB_ONLINE_JKKDETAIL= "/juejinchain/html/movie/jkkDetails.html";
    //商学院
    public static final String WEB_ONLINE_COLLAGE= "/juejinchain/html/collage/collage.html";
    //商品详情
    public static final String WEB_ONLINE_GOODS_DETAIL= "/juejinchain/html/award_pag/goodsDetail.html?id=";
    //汽车详情
    public static final String WEB_ONLINE_CAR_DETAIL= "/juejinchain/html/award_pag/carDetail.html?id=";
    //房源详情
    public static final String WEB_ONLINE_HOUSE_DETAIL= "/juejinchain/html/award_pag/houseDetail.html?id=";

    //问卷调查
    public static final String WEB_ONLINE_QUESTIONAIRE= "/juejinchain/html/task/questionaire.html";
    //走路赚钱
    public static final String WEB_ONLINE_WALK_MONEY= "/juejinchain/html/task/walkMoney.html";

    //邀请好友-查看全部邀请好友名单
    public static final String WEB_ONLINE_INVITEFIRNEDS_ALL = "/juejinchain/html/task/allInvite.html";

    //用户权益
    public static final String WEB_ONLINE_USER_IDENTITY = "/juejinchain/html/personal_center/identityRight.html";

    //用户申请页面
    public static final String WEB_ONLINE_USER_APPLY = "/juejinchain/html/personal_center/applyIdentity.html";

    //用户协议
    public static final String WEB_ONLINE_USE_AGREEMENT = "/phone/user_agreement.html";
    //隐私条款
    public static final String WEB_ONLINE_SMS_AGREEMENT = "/phone/serect.html";

    //暴富秘籍（推送）
    public static final String WEB_ONLINE_BURST_RICH = "/juejinchain/html/burst_rich/index.html";

    //大转盘奖励页面
    public static final String WEB_ONLINE_TURN_RECORD = "/juejinchain/html/personal_center/activity/turnRecord.html";

    //游戏赚
    public static final String WEB_ONLINE_GAME_EARNED = "/juejinchain/html/task/gameEarned.html";
    //分享赚
    public static final String WEB_ONLINE_SHARE_EARNED = "/juejinchain/html/task/shareEarned.html";

}
