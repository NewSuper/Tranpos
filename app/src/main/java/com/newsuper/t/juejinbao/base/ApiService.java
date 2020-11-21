package com.newsuper.t.juejinbao.base;

import com.google.gson.JsonObject;
import com.newsuper.t.juejinbao.bean.BaseConfigEntity;
import com.newsuper.t.juejinbao.bean.BaseEntity;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.bean.OwnADEntity;
import com.newsuper.t.juejinbao.ui.ad.HomeAdDialogEntity;
import com.newsuper.t.juejinbao.ui.book.entity.BooKDataEntity;
import com.newsuper.t.juejinbao.ui.book.entity.BooKInfoEntity;
import com.newsuper.t.juejinbao.ui.book.entity.BookCategoryDetailEntity;
import com.newsuper.t.juejinbao.ui.book.entity.BookCategoryEntity;
import com.newsuper.t.juejinbao.ui.book.entity.BookChapterDetailEntity;
import com.newsuper.t.juejinbao.ui.book.entity.BookChapterEntity;
import com.newsuper.t.juejinbao.ui.book.entity.BookCommendEntity;
import com.newsuper.t.juejinbao.ui.book.entity.BookListEntity;
import com.newsuper.t.juejinbao.ui.book.entity.BookSearchEntity;
import com.newsuper.t.juejinbao.ui.book.entity.BookshelfEntity;
import com.newsuper.t.juejinbao.ui.home.NetInfo.ChannelInfo;
import com.newsuper.t.juejinbao.ui.home.entity.ADConfigEntity;
import com.newsuper.t.juejinbao.ui.home.entity.ArticleCollectReplyEntity;
import com.newsuper.t.juejinbao.ui.home.entity.ArticleDetailEntity;
import com.newsuper.t.juejinbao.ui.home.entity.AuditEntity;
import com.newsuper.t.juejinbao.ui.home.entity.BackCardEntity;
import com.newsuper.t.juejinbao.ui.home.entity.BigGiftEntity;
import com.newsuper.t.juejinbao.ui.home.entity.ClearUnreadEntity;
import com.newsuper.t.juejinbao.ui.home.entity.CommentCommitEntity;
import com.newsuper.t.juejinbao.ui.home.entity.DetailRecomentEntity;
import com.newsuper.t.juejinbao.ui.home.entity.ExChangeMiniProgramEntity;
import com.newsuper.t.juejinbao.ui.home.entity.ExChangeWalkMiniProgramEntity;
import com.newsuper.t.juejinbao.ui.home.entity.FinishTaskEntity;
import com.newsuper.t.juejinbao.ui.home.entity.GetCoinCountDownEntity;
import com.newsuper.t.juejinbao.ui.home.entity.GetCoinEntity;
import com.newsuper.t.juejinbao.ui.home.entity.GiftCarEntity;
import com.newsuper.t.juejinbao.ui.home.entity.GiveLikeEnty;
import com.newsuper.t.juejinbao.ui.home.entity.HomeBottomTabEntity;
import com.newsuper.t.juejinbao.ui.home.entity.HomeListEntity;
import com.newsuper.t.juejinbao.ui.home.entity.HomeListSearchEntity;
import com.newsuper.t.juejinbao.ui.home.entity.HomeSearchResultEntity;
import com.newsuper.t.juejinbao.ui.home.entity.HotPointEntity;
import com.newsuper.t.juejinbao.ui.home.entity.HotSearchEntity;
import com.newsuper.t.juejinbao.ui.home.entity.HotWordSearchEntity;
import com.newsuper.t.juejinbao.ui.home.entity.IsCollectEntity;
import com.newsuper.t.juejinbao.ui.home.entity.MessageHintEntity;
import com.newsuper.t.juejinbao.ui.home.entity.NewsEntity;
import com.newsuper.t.juejinbao.ui.home.entity.PictureContentEntity;
import com.newsuper.t.juejinbao.ui.home.entity.PictureViewPageEntity;
import com.newsuper.t.juejinbao.ui.home.entity.Read60Reword;
import com.newsuper.t.juejinbao.ui.home.entity.RewardDoubleEntity;
import com.newsuper.t.juejinbao.ui.home.entity.ShenmaHotWordsEntity;
import com.newsuper.t.juejinbao.ui.home.entity.SmallVideoContentEntity;
import com.newsuper.t.juejinbao.ui.home.entity.TodayHotEntity;
import com.newsuper.t.juejinbao.ui.home.entity.UnReadEntity;
import com.newsuper.t.juejinbao.ui.home.entity.UnreadMaseggeEntity;
import com.newsuper.t.juejinbao.ui.home.entity.UpAppEntity;
import com.newsuper.t.juejinbao.ui.home.entity.VideoDetailEntity;
import com.newsuper.t.juejinbao.ui.home.entity.WelFareRewardEntity;
import com.newsuper.t.juejinbao.ui.home.entity.WelfareEntity;
import com.newsuper.t.juejinbao.ui.login.entity.BindInviterEntity;
import com.newsuper.t.juejinbao.ui.login.entity.DefaultIntviteCodeEntity;
import com.newsuper.t.juejinbao.ui.login.entity.IsShowQQEntity;
import com.newsuper.t.juejinbao.ui.login.entity.PhoneIsRegistEntity;
import com.newsuper.t.juejinbao.ui.login.entity.PostAliasEntity;
import com.newsuper.t.juejinbao.ui.login.entity.SetAndChangePswEntity;
import com.newsuper.t.juejinbao.ui.movie.bean.UpdateEntity;
import com.newsuper.t.juejinbao.ui.movie.bean.V2MovieSearchEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.ALiYunOssEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.BindThirdEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.BoxShareEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.CommenEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.DependentResourcesDataEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.HotSearchDataEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.LeaveEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieAlertEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieCancelEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieCartonHotEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieCinamesEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieDetailEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieIndexRecommendEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieInfoEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieLooklDataEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieMovieFilterDataEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieMovieRecommendDataEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieNewFilterEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieNewFilterTagEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieNewRecommendEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieNewTabRankEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.MoviePostDataEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.MoviePostDataEntity2;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieRadarMovieDetailEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieRadarSearchListEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieReadEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieRotationListEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieSearchthirdWebEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieShowBookEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieShowHotEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieTVHotEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieTabDataEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.PlayerADEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.PlayerADReadEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.RecommendRankingEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.ResponseEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.SearchResultDataEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.TaskADEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.UploadMovieDetailBean;
import com.newsuper.t.juejinbao.ui.movie.entity.V2PlayListEntity;
import com.newsuper.t.juejinbao.ui.my.entity.AdFirstEntity;
import com.newsuper.t.juejinbao.ui.my.entity.AdTwoEntity;
import com.newsuper.t.juejinbao.ui.my.entity.BaseDefferentEntity;
import com.newsuper.t.juejinbao.ui.my.entity.InviteFriendRewardValueEntity;
import com.newsuper.t.juejinbao.ui.my.entity.InviteFriendWheelEntity;
import com.newsuper.t.juejinbao.ui.my.entity.LookOrOnTachEntity;
import com.newsuper.t.juejinbao.ui.my.entity.MyInviteFriendListEntity;
import com.newsuper.t.juejinbao.ui.my.entity.MyUnreadMessageEntity;
import com.newsuper.t.juejinbao.ui.my.entity.UserDataEntity;
import com.newsuper.t.juejinbao.ui.my.entity.UserInfoEntity;
import com.newsuper.t.juejinbao.ui.my.entity.UserProfileEntity;
import com.newsuper.t.juejinbao.ui.share.entity.ShareConfigEntity;
import com.newsuper.t.juejinbao.ui.share.entity.ShareDetailEntity;
import com.newsuper.t.juejinbao.ui.share.entity.ShareDomainEntity;
import com.newsuper.t.juejinbao.ui.share.entity.ShareInfoByBraekLock;
import com.newsuper.t.juejinbao.ui.share.entity.SharePicsEntity;
import com.newsuper.t.juejinbao.ui.share.entity.ShortUrlEntity;
import com.newsuper.t.juejinbao.ui.song.entity.AddSongEntity;
import com.newsuper.t.juejinbao.ui.song.entity.AlbumDetailEntity;
import com.newsuper.t.juejinbao.ui.song.entity.ClassAreaDetailEntity;
import com.newsuper.t.juejinbao.ui.song.entity.ClassAreaEntity;
import com.newsuper.t.juejinbao.ui.song.entity.LatestAlbumListEntity;
import com.newsuper.t.juejinbao.ui.song.entity.LatestAlbumTagEntity;
import com.newsuper.t.juejinbao.ui.song.entity.LatestMusicListEntity;
import com.newsuper.t.juejinbao.ui.song.entity.LatestMusicTagEntity;
import com.newsuper.t.juejinbao.ui.song.entity.MusicCollectEntity;
import com.newsuper.t.juejinbao.ui.song.entity.MusicCollectionEntity;
import com.newsuper.t.juejinbao.ui.song.entity.MusicDataListEntity;
import com.newsuper.t.juejinbao.ui.song.entity.MusicHistoryEntity;
import com.newsuper.t.juejinbao.ui.song.entity.MusicHotListEntity;
import com.newsuper.t.juejinbao.ui.song.entity.MusicListEntity;
import com.newsuper.t.juejinbao.ui.song.entity.MusicSearchFromEntity;
import com.newsuper.t.juejinbao.ui.song.entity.SongBillBoardEntity;
import com.newsuper.t.juejinbao.ui.task.entity.BigTurnTabRewardEntity;
import com.newsuper.t.juejinbao.ui.task.entity.BoxInfoEntity;
import com.newsuper.t.juejinbao.ui.task.entity.BoxTimeEntity;
import com.newsuper.t.juejinbao.ui.task.entity.SignEntity;
import com.newsuper.t.juejinbao.ui.task.entity.SleepEarningEntity;
import com.newsuper.t.juejinbao.ui.task.entity.SleepEarningEntity2;
import com.newsuper.t.juejinbao.ui.task.entity.TaskListEntity;
import com.newsuper.t.juejinbao.ui.task.entity.TaskMsgEntity;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

public interface ApiService {

    //    http://api.pre.juejinchain.cn/v1/article/pull_up?per_page=10&columnid=0&page=2&channel_id=0&source_style=7&user_token=&uid=0&channel=normal&version=1.8.3.8
    //验证码登录
    @POST(RetrofitManager.API_Prefix + "passport/login")
    Observable<LoginEntity> login(@QueryMap Map<String, String> requestBodyMap);

    //微信绑定手机号
    @POST(RetrofitManager.API_Prefix + "user/bind_mobile_new")
    Observable<LoginEntity> bindMoble(@QueryMap Map<String, String> requestBodyMap);

    //QQ绑定手机号
    @GET(RetrofitManager.API_Prefix + "third_party_api/bind_mobile")
    Observable<LoginEntity> bindQQMoble(@QueryMap Map<String, String> requestBodyMap);

    //密码登录
    @POST(RetrofitManager.API_Prefix + "user/login_by_password")
    Observable<LoginEntity> loginPsw(@QueryMap Map<String, String> requestBodyMap);

    //一键登录
    @POST(RetrofitManager.API_Prefix + "passport/login")
    Observable<LoginEntity> loginOnePsw(@QueryMap Map<String, String> requestBodyMap);

    //微信登录
    @POST(RetrofitManager.API_Prefix + "wechat/oath")
    Observable<LoginEntity> loginWX(@QueryMap Map<String, String> requestBodyMap);

    //qq登录
    @POST(RetrofitManager.API_Prefix + "third_party_api/qq_login")
    Observable<LoginEntity> loginQQ(@QueryMap Map<String, String> requestBodyMap);

    //绑定微信号
    @POST(RetrofitManager.API_Prefix + "wechat/bind_wechat_in_app_new")
    Observable<BindThirdEntity> bindWechat(@QueryMap Map<String, String> requestBodyMap);

    //绑定邀请码
    @POST(RetrofitManager.API_Prefix + "user/set_inviter")
    Observable<BindInviterEntity> bindCode(@QueryMap Map<String, String> requestBodyMap);

    //是否显示微信或者QQ
    @POST(RetrofitManager.API_Prefix + "/system/loginicon")
    Observable<IsShowQQEntity> isShowWechat(@QueryMap Map<String, String> requestBodyMap);

    //检测当前手机号是否注册
    @POST(RetrofitManager.API_Prefix + "user/is_register_mobile")
    Observable<PhoneIsRegistEntity> phoneIsRegistCode(@QueryMap Map<String, String> requestBodyMap);

    //获取短信验证码
    @GET(RetrofitManager.API_Prefix + "passport/smscode")
    Observable<SetAndChangePswEntity> getPhoneCode(@QueryMap Map<String, String> requestBodyMap);


    //获取图形验证码
    @GET(RetrofitManager.API_Prefix + "passport/captcha")
    Observable<SetAndChangePswEntity> getPhoneImage(@QueryMap Map<String, String> requestBodyMap);

    //修改密码，设置密码
    @GET(RetrofitManager.API_Prefix + "user/set_password")
    Observable<LoginEntity> commitNewPsw(@QueryMap Map<String, String> requestBodyMap);

    //设置密码
    @GET(RetrofitManager.API_Prefix + "user/set_password")
    Observable<LoginEntity> commitLoginNewPsw(@QueryMap Map<String, String> requestBodyMap);

    //获取广告数据及开屏广告类型
    @GET(RetrofitManager.API_Prefix + "user/ad_start_alert?ad_position=1")
    Observable<OwnADEntity> getOpenADtypeAndData();

    //开屏广告点击
    @GET(RetrofitManager.API_Prefix + "user/ad_count")
    Observable<BaseEntity> getClickADCount(@QueryMap Map<String, String> requestBodyMap);

    /**
     * 首页返回奖励Dialog接口
     */
    @GET(RetrofitManager.API_Prefix + "share/top_car")
    Observable<BackCardEntity> getBackCardInfo(@QueryMap Map<String, String> requestBodyMap);
    //获取睡眠赚信息
    @GET(RetrofitManager.API_Prefix + "sleep_money/index")
    Observable<SleepEarningEntity> getSleepEarningInfo();
    //开启睡眠
    @GET(RetrofitManager.API_Prefix + "sleep_money/start_sleep")
    Observable<SleepEarningEntity2> startSleep();
    //领取金币
    @GET(RetrofitManager.API_Prefix + "sleep_money/get_reward")
    Observable<SleepEarningEntity> getReward();
    @GET(RetrofitManager.API_Prefix + "sleep_money/video_reward")
    Observable<SleepEarningEntity> videoReward(@QueryMap Map<String, String> requestBodyMap);
    /*
     * 首页menu控制
     */
    @GET(RetrofitManager.API_Prefix + "system/menu")
    Observable<HomeBottomTabEntity> getHomeTab(@QueryMap Map<String, String> requestBodyMap);

    //退出登录
    @GET(RetrofitManager.API_Prefix + "/user/logout")
    Observable<LoginEntity> loginOut(@QueryMap Map<String, String> requestBodyMap);

    /**
     * 未读信息，登录后每20~30秒调用一次（全局的
     * 如果有top弹下滚提示3s后消失，点击跳到消息页面
     */
    @GET(RetrofitManager.API_Prefix + "message/unread")
    Observable<UnReadEntity> getUnReadMessage(@QueryMap Map<String, String> requestBodyMap);

    /**
     * 60分钟奖励
     */
    @GET(RetrofitManager.API_Prefix + "user/read_60minute")
    Observable<Read60Reword> getRead60Reword(@QueryMap Map<String, String> requestBodyMap);

    //获取分享图片列表
    @GET(RetrofitManager.API_Prefix + "share/getsharepics")
    Observable<SharePicsEntity> getSharePics(@QueryMap Map<String, String> requestBodyMap);

    //分享配置
    @GET(RetrofitManager.API_Prefix + "share/btn_config")
    Observable<ShareConfigEntity> getShareConfig(@QueryMap Map<String, String> requestBodyMap);

    //广告配置
    @GET(RetrofitManager.API_Prefix + "system/getAdConfig")
    Observable<ADConfigEntity> getADConfig(@QueryMap Map<String, String> requestBodyMap);

    //获取可用分享域名-app升级
    @GET(RetrofitManager.API_Prefix + "system/check_domain_api")
    Observable<ShareDomainEntity> getDomainApi(@QueryMap Map<String, String> requestBodyMap);

    //获取缩略图及描述等信息
    @GET(RetrofitManager.API_Prefix + "share/copywriting")
    Observable<ShareDetailEntity> getShareInfo(@QueryMap Map<String, String> requestBodyMap);

    //防封神器获取缩略图及描述等信息
    @GET(RetrofitManager.API_Prefix + "share/poster_break_lock")
    Observable<ShareInfoByBraekLock> getInfoByBeakLock(@QueryMap Map<String, String> requestBodyMap);

    //获取分享短地址
    @GET(RetrofitManager.API_Prefix + "index/shorturlguard")
    Observable<ShortUrlEntity> getShortUrl(@QueryMap Map<String, String> requestBodyMap);

    //分享埋点
    @GET(RetrofitManager.API_Prefix + "misc/share_click_count")
    Observable<BaseEntity> shareCountCommit(@QueryMap Map<String, String> requestBodyMap);

    //走路赚钱分享埋点
//    http://dev.wechat.juejinchain.cn/api/v1/system/share_count&btn_name=webapp_news_download_btn1
    @GET(RetrofitManager.API_Prefix + "system/share_count")
    Observable<BaseEntity> shareCountCommitByWalk(@QueryMap Map<String, String> requestBodyMap);


    //试玩游戏获取金币
    @GET(RetrofitManager.API_Prefix + "/game/reward")
    Observable<BaseEntity> playGameGetCoin(@QueryMap Map<String, String> requestBodyMap);

    //首页进入广告弹框
    @GET(RetrofitManager.API_Prefix + "/user/ad_index_alert")
    Observable<HomeAdDialogEntity> getAdDialogData(@QueryMap Map<String, String> requestBodyMap);

    //第三方分享贡献埋点
    @GET(RetrofitManager.API_Prefix + "misc/add_ad_contribute")
    Observable<BaseEntity> adDataCommitByThird(@QueryMap Map<String, String> requestBodyMap);

    /*****************************************   首页   ******************************************************/

    //获取频道，默认调用此接口，没登录返回8个，登录后重新获取
    @GET(RetrofitManager.API_Prefix + "channel/default_list")
    Observable<ChannelInfo> getChennelList(@QueryMap Map<String, String> requestBodyMap);


    //设置频道 type:[article|picture|video]
    @GET(RetrofitManager.API_Prefix + "channel/channel_set")
    Observable<BaseEntity> setChennelList(@QueryMap Map<String, String> requestBodyMap);

    ////频道管理的，推荐频道
    @GET(RetrofitManager.API_Prefix + "channel/all_list")
    Observable<ChannelInfo> getRecommendChannel(@QueryMap Map<String, String> requestBodyMap);

    /**
     * 推荐和热门
     * 推荐频道id=0 ,热门1
     */

    //
    @GET(RetrofitManager.API_Prefix + "article/pull_up")
    Observable<NewsEntity> getRecommendAndHotNews(@QueryMap Map<String, String> requestBodyMap);

    @GET(RetrofitManager.API_Prefix + "article/pagelist")
    Observable<NewsEntity> getOtherNews(@QueryMap Map<String, String> requestBodyMap);


    /**
     * 提示签到接口，非新用户登录后回到首页时调用
     * 如未签到底部弹签到提示popup
     * "data": {
     * "unsigned": 1,
     */
    @GET(RetrofitManager.API_Prefix + "message/hint")
    Observable<MessageHintEntity> getMessageHint(@QueryMap Map<String, String> requestBodyMap);


    //首页半小时 领取金币接口
    @GET(RetrofitManager.API_Prefix + "task/minute30_save")
    Observable<GetCoinEntity> getCoinOf30Min(@QueryMap Map<String, String> requestBodyMap);

    //首页半小时 计时
    @GET(RetrofitManager.API_Prefix + "task/minute30")
    Observable<GetCoinCountDownEntity> countDownOf30Min(@QueryMap Map<String, String> requestBodyMap);


    //小视频
    @GET(RetrofitManager.API_Prefix + "article/get_recommend_list")
    Observable<HomeListEntity> getSmallVideoList(@QueryMap Map<String, String> requestBodyMap);

    //首页列表//图集列表
    @GET(RetrofitManager.API_Prefix + "article/get_recommend_list")
    Observable<HomeListEntity> getHomePageList(@QueryMap Map<String, String> requestBodyMap);

    ///图集列表
    @GET(RetrofitManager.API_Prefix + "article/get_recommend_list")
    Observable<PictureContentEntity> getPictureContent(@QueryMap Map<String, String> requestBodyMap);

//    //视频二级频道列表
//    @GET(RetrofitManager.API_Prefix + "video/category_lists")
//    Observable<VideoChannelInfo> getVideoChannelList();

    //图集二级tab
    @GET(RetrofitManager.API_Prefix + "channel/default_list?")
    Observable<ChannelInfo> getPictureList(@QueryMap Map<String, String> requestBodyMap);

    //搜索接口
    @GET(RetrofitManager.API_Prefix + "article/search_v2")
    Observable<HomeListEntity> searchByHome(@QueryMap Map<String, String> requestBodyMap);

    //全网搜索接口
    @GET(RetrofitManager.API_Prefix + "article/search")
    Observable<HomeListSearchEntity> searchNews(@QueryMap Map<String, String> requestBodyMap);

    //热门搜索接口
    @GET(RetrofitManager.API_Prefix + "")
    Observable<HotSearchEntity> hotSearch(@QueryMap Map<String, String> requestBodyMap);

    /**
     * 视频点赞和评论点赞
     * vid: 4168444
     * 评论的加一个参，用post
     * cid: 1688984
     */
    @GET(RetrofitManager.API_Prefix + "video/fabulous_v2")
    Observable<BaseEntity> giveLike(@QueryMap Map<String, String> requestBodyMap);

    //小视频点赞
    @GET(RetrofitManager.API_Prefix + "small_video/fabulous?")
    Observable<GiveLikeEnty> getGiveLike(@QueryMap Map<String, String> map);

    //文章点赞
    @POST(RetrofitManager.API_Prefix + "article/fabulous?")
    Observable<BaseEntity> getArticleGiveLike(@QueryMap Map<String, String> map);

    //小视频是否已点赞
    @GET(RetrofitManager.API_Prefix + "small_video/check_fabulous?")
    Observable<GiveLikeEnty> getIsGiveLike(@QueryMap Map<String, String> map);

    //评论点赞或者回复评论点赞
    @GET(RetrofitManager.API_Prefix + "picture/fabulous?")
    Observable<GiveLikeEnty> getPictureCommentGiveLike(@QueryMap Map<String, String> map);

    //神马搜索
    @POST
    public Observable<ShenmaHotWordsEntity> shenmaHotWords(@Url String url , @QueryMap Map<String, String> map);

    //小视频评论
    @GET(RetrofitManager.API_Prefix + "small_video/comment?")
    Observable<BaseEntity> postCotent(@QueryMap Map<String, String> map);

    //图集评论
    @GET(RetrofitManager.API_Prefix + "picture/comment?")
    Observable<BaseEntity> postPictureCotent(@QueryMap Map<String, String> map);

    //图集评论回复
    @GET(RetrofitManager.API_Prefix + "picture/reply?")
    Observable<BaseEntity> postPictureCotentReply(@QueryMap Map<String, String> map);

    //文章评论回复
    @GET(RetrofitManager.API_Prefix + "article/reply?")
    Observable<CommentCommitEntity> postArticleCotentReply(@QueryMap Map<String, String> map);

    //小视频评论回复
    @GET(RetrofitManager.API_Prefix + "small_video/reply?")
    Observable<BaseEntity> postSmallVideoCotentReply(@QueryMap Map<String, String> map);

    //小视频兴趣
    @GET(RetrofitManager.API_Prefix + "video/user_interesting_v2?")
    Observable<GiveLikeEnty> postSmallVideoLike(@QueryMap Map<String, String> map);


    //小视频评论列表
    @GET(RetrofitManager.API_Prefix + "small_video/comment_list?")
    Observable<SmallVideoContentEntity> getSmallVideoContentList(@QueryMap Map<String, String> map);

    //评论列表
    @GET(RetrofitManager.API_Prefix + "picture/comment_list?")
    Observable<SmallVideoContentEntity> getPictureContentList(@QueryMap Map<String, String> map);

    //评论某个人的列表
    @GET(RetrofitManager.API_Prefix + "picture/reply_list?")
    Observable<SmallVideoContentEntity> getPioplePictureContentList(@QueryMap Map<String, String> map);

    //文章评论某个人的列表
    @GET(RetrofitManager.API_Prefix + "article/reply_list?")
    Observable<ArticleCollectReplyEntity> getArticleContentList(@QueryMap Map<String, String> map);

    //评论小视频某个人的列表
    @GET(RetrofitManager.API_Prefix + "small_video/reply_list?")
    Observable<SmallVideoContentEntity> getPiopleVideoContentList(@QueryMap Map<String, String> map);

    //图集详情http://dev.api.juejinchain.cn/v1/picture/detail&aid=4470
    @GET(RetrofitManager.API_Prefix + "picture/detail?")
    Observable<PictureViewPageEntity> getPictureBigImageList(@QueryMap Map<String, String> map);

    //图集兴趣
    @GET(RetrofitManager.API_Prefix + "picture/user_interesting?")
    Observable<IsCollectEntity> getPictureLike(@QueryMap Map<String, String> map);

    //收藏
    @GET(RetrofitManager.API_Prefix + "user/collection?")
    Observable<IsCollectEntity> getPictureIsCollect(@QueryMap Map<String, String> map);

    //获取大礼包随机车型数据
    @GET(RetrofitManager.API_Prefix + "gift_image?")
    Observable<GiftCarEntity> getCarType(@QueryMap Map<String, String> map);

    //获取大礼包
    @GET(RetrofitManager.API_Prefix + "task/gift_bag?")
    Observable<BigGiftEntity> getBigGif(@QueryMap Map<String, String> map);

    //文章详情文章富文本
    @GET(RetrofitManager.API_Prefix + "article/detail?")
    Observable<ArticleDetailEntity> getArticleDetail(@QueryMap Map<String, String> map);

    //文章详情推荐列表
    @GET(RetrofitManager.API_Prefix + "article/recommend?")
    Observable<HomeListEntity>getArticleListDetail(@QueryMap Map<String, String> map);

    //文章详情关注作者
    @POST(RetrofitManager.API_Prefix + "user/follow?")
    Observable<HomeListEntity> getArticleAuthorFcous(@QueryMap Map<String, String> map);

    //文章收藏
    @POST(RetrofitManager.API_Prefix + "user/collection?")
    Observable<BaseDefferentEntity> getArticleAuthorCollect(@QueryMap Map<String, String> map);

    //文章详情评论列表
    @POST(RetrofitManager.API_Prefix + "article/comment_list?")
    Observable<DetailRecomentEntity> getArticleComment(@QueryMap Map<String, String> map);

    //文章详情评论
    @POST(RetrofitManager.API_Prefix + "article/comment?")
    Observable<CommentCommitEntity> getArticleSubmit(@QueryMap Map<String, String> map);

    //首页--搜索
    @GET(RetrofitManager.API_Prefix + "article/search_tip")
    Observable<HomeSearchResultEntity> getHomeSearch(@QueryMap Map<String, String> map);


    /*****************************************   影视   ******************************************************/

    //影视模块改版推荐数据
    @GET(RetrofitManager.API_Prefix + "movie_v2/recommLists")
    Observable<MovieNewRecommendEntity> movieNewRecommendData();

    //影视模块tab数据获取
    @GET(RetrofitManager.API_Prefix + "movie/get_nav_config")
    Observable<MovieTabDataEntity> movieTabData();

    //影视模块推荐数据获取
    @GET(RetrofitManager.API_Prefix + "movie/index_recommend")
    Observable<MoviePostDataEntity> moviePostData();

    //影视模块海报数据获取
    @GET(RetrofitManager.API_Prefix + "movie/carousel")
    Observable<MoviePostDataEntity2> moviePostData2(@QueryMap Map<String, Object> map);

    //影视观影用户数据获取
    @GET(RetrofitManager.API_Prefix + "system/online_user")
    Observable<MovieLooklDataEntity> getLookData();

    //影视--电影--推荐
    @GET(RetrofitManager.API_Prefix + "movie/recommend")
    Observable<MovieMovieRecommendDataEntity> getMovieMovieRecommend(@Query("page") Integer page, @Query("type") String type, @Query("category") String category);

    //影视--电影--筛选
    @GET(RetrofitManager.API_Prefix + "movie/search")
    Observable<MovieMovieFilterDataEntity> getMovieMovieFilter(@Query("page") Integer page, @Query("pre_page") Integer pre_page, @Query("type") String type, @Query("type_name") String type_name, @Query("area") String area, @Query("year") String year);


    //影视--电影--豆瓣改版搜索分类
    @GET(RetrofitManager.API_Prefix + "movie_v2/searchTags")
    Observable<MovieNewFilterTagEntity> getMovieNewFilterTag();

    //影视--电影--豆瓣改版筛选
    @GET(RetrofitManager.API_Prefix + "movie_v2/search")
    Observable<MovieNewFilterEntity> getMovieNewFilter(@QueryMap Map<String, String> map);

    //影视--电影--豆瓣改版一周节目表
    @GET(RetrofitManager.API_Prefix + "movie_v2/playLists")
    Observable<V2PlayListEntity> getMovieNewPlaylist();


    //影视--相关资源
    @GET(RetrofitManager.API_Prefix + "movie/search")
    Observable<DependentResourcesDataEntity> getDependentResources(@Query("page") Integer page, @Query("kw") String kw, @Query("type") String type);

    //影视--影院列表
    @GET(RetrofitManager.API_Prefix + "movie/cinemas")
    Observable<MovieCinamesEntity> getCinemas();

    //影视--热搜榜
    @GET(RetrofitManager.API_Prefix + "movie/hot_search")
    Observable<HotSearchDataEntity> getHotSearch();

    //影视--搜索
    @GET(RetrofitManager.API_Prefix + "movie/search_tip")
    Observable<SearchResultDataEntity> getMovieSearch(@Query("kw") String kw);

    //影视--提醒
    @POST(RetrofitManager.API_Prefix + "subscription/movie")
    Observable<MovieAlertEntity> setMovieAlert(@QueryMap Map<String, String> map);

    //影视--取消提醒
    @POST(RetrofitManager.API_Prefix + "subscription/cancle_movie")
    Observable<MovieCancelEntity> setMovieCancelAlert(@QueryMap Map<String, String> map);

    //影视--VIP解析
    @POST(RetrofitManager.API_Prefix + "movie/vipparse")
    Observable<JsonObject> vipparse(@QueryMap Map<String, String> map);

    //影视--错误上报
    @POST(RetrofitManager.API_Prefix + "movie/add_shield_source")
    Observable<JsonObject> shieldSource(@QueryMap Map<String, String> map);

    //影视--小说tab动态显示
    @POST(RetrofitManager.API_Prefix + "movie/layouttag")
    Observable<MovieShowBookEntity> readbookShow(@QueryMap Map<String, String> map);

    //影视搜索--第三方网页列表
    @POST(RetrofitManager.API_Prefix + "search/lists")
    Observable<MovieSearchthirdWebEntity> MovieSearchthirdWeb();

    //影视播放超时报错
    @POST(RetrofitManager.API_Prefix + "movie/playFailureCount")
    Observable<CommenEntity> movieSearchOutTime(@QueryMap Map<String, String> map);

    //影视播放时调用计数
    @POST(RetrofitManager.API_Prefix + "movie/viewcount")
    Observable<MovieReadEntity> movieRead(@QueryMap Map<String, String> map);

    //播放器广告
    @POST(RetrofitManager.API_Prefix + "ad/moviead")
    Observable<PlayerADEntity> playerAD(@QueryMap Map<String, String> map);

    //播放器广告计量
    @POST(RetrofitManager.API_Prefix + "user/ad_count")
    Observable<PlayerADReadEntity> playerADCount(@QueryMap Map<String, String> map);

    //请求影视详情
    @POST(RetrofitManager.API_Prefix + "movie/detail")
    Observable<MovieDetailEntity> movieDetail(@QueryMap Map<String, String> map);


    /*****************************************   任务   ******************************************************/
    //关联第三方
    @POST(RetrofitManager.API_Prefix + "third_party_api/bind_third_party")
    Observable<BindThirdEntity> bindThird(@QueryMap Map<String, String> map);

    //关联微信
    @POST(RetrofitManager.API_Prefix + "wechat/bind_wechat_in_app_new")
    Observable<BindThirdEntity> bindWX(@QueryMap Map<String, String> map);

    //宝箱分享回调接口
    @POST(RetrofitManager.API_Prefix + "share/sharing_callback")
    Observable<BoxShareEntity> BoxShareSuccess(@QueryMap Map<String, String> map);

    //获取任务广告接口
    @POST(RetrofitManager.API_Prefix + "task/ad_iframe_model")
    Observable<TaskADEntity> getTaskAD(@QueryMap Map<String, String> map);

    //任务签到接口
    @POST(RetrofitManager.API_Prefix + "task/sign_in")
    Observable<SignEntity> signIn(@QueryMap Map<String, String> map);

    //任务左上角滚动轮播图
    @POST(RetrofitManager.API_Prefix + "message/scroll_user_get")
    Observable<TaskMsgEntity> getTaskMessage(@QueryMap Map<String, String> map);

    //任务列表
    @GET(RetrofitManager.API_Prefix + "task/list")
    Observable<TaskListEntity> getTaskList();

    //获取开宝箱剩余时间 0可开启宝箱，返回秒数，时间还有就显示倒计时
    @POST(RetrofitManager.API_Prefix + "task/get_treasure_box_time")
    Observable<BoxTimeEntity> getTreasureBoxTime(@QueryMap Map<String, String> map);

    //开宝箱接口
    @POST(RetrofitManager.API_Prefix + "task/treasure_box_save")
    Observable<BoxInfoEntity> treasureBoxSave(@QueryMap Map<String, String> map);

    //离开web接口
    @POST(RetrofitManager.API_Prefix + "article/leave")
    Observable<LeaveEntity> leaveWeb(@QueryMap Map<String, String> map);

    //APP检查更新
    @GET(RetrofitManager.API_Prefix + "index/movie_check_upgrade")
    Observable<UpdateEntity> updateAPP(@QueryMap Map<String, String> requestBodyMap);

    //大转盘奖励
    @POST(RetrofitManager.API_Prefix + "task/watch_ad_video")
    Observable<BigTurnTabRewardEntity> bigTurnTab(@QueryMap Map<String, String> map);

    //领鸡蛋 app奖励接口
    @GET(RetrofitManager.API_Egg + "user_task/welfare")
    Observable<WelfareEntity> welfare(@QueryMap Map<String, String> requestBodyMap);

    //完成 app领鸡蛋任务
    @GET(RetrofitManager.API_Egg + "user_task/finish_task")
    Observable<FinishTaskEntity> finishTask(@QueryMap Map<String, String> requestBodyMap);

    /*****************************************   我的   ******************************************************/
    //用户信息
    @GET(RetrofitManager.API_Prefix + "user/personal")
    Observable<UserDataEntity> getUserData(@QueryMap Map<String, String> map);

    //获取轮播的广告
    @GET(RetrofitManager.API_Prefix + "message/scroll_user_prize")
    Observable<AdFirstEntity> getAdFirst(@QueryMap Map<String, String> map);

    //获取带图轮播的广告
    @GET(RetrofitManager.API_Prefix + "message/upgrade_broadcast")
    Observable<AdTwoEntity> getAdTwo(@QueryMap Map<String, String> map);

    //获取浏览或者点击统计
    @GET(RetrofitManager.API_Prefix + "user/ad_count")
    Observable<LookOrOnTachEntity> getLookOrOnTach(@QueryMap Map<String, String> map);

    //是否显示msg以及邀请码邀请获得掘金宝数量
    @GET(RetrofitManager.API_Prefix + "message/hint")
    Observable<MyUnreadMessageEntity> getMyMessage(@QueryMap Map<String, String> map);

    //等级弹窗或者商品弹窗已弹出
    @GET(RetrofitManager.API_Prefix + "user/upgradecallback")
    Observable<LookOrOnTachEntity> getGiftAndGoods(@QueryMap Map<String, String> map);

    //个人主页
    @GET(RetrofitManager.API_Prefix + "user/profile")
    Observable<UserProfileEntity> getUserProfile(@QueryMap Map<String, String> map);

    //个人信息
    @GET(RetrofitManager.API_Prefix + "user/info")
    Observable<UserInfoEntity> getUserInfo();

    //隐私设置
    @POST(RetrofitManager.API_Prefix + "user/updateInfo")
    Observable<BaseEntity> updateInfo(@QueryMap Map<String, String> map);

    //隐私设置开启所有隐私
    @POST(RetrofitManager.API_Prefix + "user/updateallswitch")
    Observable<GetCoinEntity> updateAllSwitch();

    //邀请好友轮播文本
    @POST(RetrofitManager.API_Prefix + "message/scroll_user_get")
    Observable<InviteFriendWheelEntity> inviteFriendWheel(@QueryMap Map<String, String> map);

    //我的邀请好友列表
    @POST(RetrofitManager.API_Prefix + "user/invitee_list")
    Observable<MyInviteFriendListEntity> myInviteFriendList(@QueryMap Map<String, String> map);

    //邀请好友金币奖励规则
    @POST(RetrofitManager.API_Prefix + "reward/inviting_friend_reward_info")
    Observable<InviteFriendRewardValueEntity> inviteFriendRewardValue(@QueryMap Map<String, String> map);

    /*****************************************   全局APP更新  ******************************************************/
    //获取更新信息
    @GET(RetrofitManager.API_Prefix + "vsn")
    Observable<UpAppEntity> getUpApp(@QueryMap Map<String, String> map);

    //下载APP
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

    //推送
    @POST(RetrofitManager.API_Prefix + "user/set_push_alias")
    Observable<PostAliasEntity> postAlias(@QueryMap Map<String, String> map);

    //文章详情 获取30s阅读奖励
    @POST(RetrofitManager.API_Prefix + "article/reading_article_reward")
    Observable<GetCoinEntity> getRewardOf30Second(@QueryMap Map<String, String> map);

    //文章详情 双倍阅读奖励
    @POST(RetrofitManager.API_Prefix + "article/reading_article_double_reward")
    Observable<RewardDoubleEntity> getRewardDouble(@QueryMap Map<String, String> map);

    //新手奖励
    @POST(RetrofitManager.API_Prefix + "reward/newbie_task_reward")
    Observable<BaseEntity> getNewTaskReward(@QueryMap Map<String, String> map);

    //文章详情 关闭文章时调用
    @POST(RetrofitManager.API_Prefix + "article/leave")
    Observable<BaseEntity> leaveActicleDetail(@QueryMap Map<String, String> map);

    // 分享奖励
    @POST(RetrofitManager.API_Prefix + "share/sharing_callback")
    Observable<GetCoinEntity> shareGetReward(@QueryMap Map<String, String> map);

    // 初始化oss对象
    @POST(RetrofitManager.API_Prefix + "oss/getStsToken")
    Observable<ResponseEntity<ALiYunOssEntity>> initOss(@QueryMap Map<String, String> map);

    // 设置 钱盟盟平台 openId
    @POST(RetrofitManager.API_Prefix + "debt/saveOpenid")
    Observable<ResponseEntity<Object>> setQmmOpenId(@QueryMap Map<String, String> map);

    // 任务看视频赚金币
    @POST(RetrofitManager.API_Prefix + "game/videoreward")
    Observable<BaseEntity> watchAdVideoInTask(@QueryMap Map<String, String> map);

    //根据渠道获取官方邀请码
    @POST(RetrofitManager.API_Prefix + "user/getRecomendInviteCode")
    Observable<DefaultIntviteCodeEntity> getInvitecodeByChannel(@QueryMap Map<String, String> map);

    //根据渠道获取官方邀请码
    @GET(RetrofitManager.API_Prefix + "article/hotpoint")
    Observable<ResponseEntity<HotPointEntity>> getHotpoint(@QueryMap Map<String, String> map);

    //头条热榜列表
    @GET(RetrofitManager.API_Prefix + "article/hotword_rank")
    Observable<TodayHotEntity> getHotWordRank(@QueryMap Map<String, String> map);

    //头条热榜搜索
    @GET(RetrofitManager.API_Prefix + "article/hotword_search")
    Observable<HotWordSearchEntity> getHotWordSearch(@QueryMap Map<String, String> map);

    /**********************************视频详情相关接口*********************************************/

    //获取视频详情
    @POST(RetrofitManager.API_Prefix + "video/detail_v2")
    Observable<VideoDetailEntity> getVideoDetail(@QueryMap Map<String, String> map);

    //评论列表
    @POST(RetrofitManager.API_Prefix + "video/comment_list_v2")
    Observable<DetailRecomentEntity> getVideoCommentList(@QueryMap Map<String, String> map);

    //发布评论
    @POST(RetrofitManager.API_Prefix + "video/comment_v2")
    Observable<BaseEntity> postVideoComment(@QueryMap Map<String, String> map);

    //视频评论回复列表
    @POST(RetrofitManager.API_Prefix + "video/reply_list_v2")
    Observable<ArticleCollectReplyEntity> getVideoCommentReplyList(@QueryMap Map<String, String> map);

    //视频评论回复
    @GET(RetrofitManager.API_Prefix + "video/reply_v2?")
    Observable<CommentCommitEntity> postVideoCotentReply(@QueryMap Map<String, String> map);

    //影视首页电影专题轮播列表和电视剧专题轮播列表接口
    @GET(RetrofitManager.API_Prefix + "movie/rotation_list")
    Observable<MovieRotationListEntity> getMovieRotationList();

    //电影专题或电视剧专题列表接口
    @GET(RetrofitManager.API_Prefix + "movie/ranking_list")
    Observable<RecommendRankingEntity> getMovieRankingList(@QueryMap Map<String, String> map);

    //影视豆瓣改版榜单详情
    @GET(RetrofitManager.API_Prefix + "movie_v2/rankList")
    Observable<MovieNewTabRankEntity> getMovieNewTabRankList(@QueryMap Map<String, String> map);

    //影视豆瓣改版模糊搜索
    @GET(RetrofitManager.API_Prefix + "movie_v2/searchKeyword")
    Observable<V2MovieSearchEntity> getDoubanSearch(@Query("keyword") String keyword);

    //影视豆瓣热播新剧
    @GET(RetrofitManager.API_Prefix + "movie_v2/hotNewDrama")
    Observable<MovieTVHotEntity> getMovieTVHot(@QueryMap Map<String, String> map);

    //影视豆瓣热播综艺
    @GET(RetrofitManager.API_Prefix + "movie_v2/hotNewShow")
    Observable<MovieShowHotEntity> getMovieShowHot(@QueryMap Map<String, String> map);

    //影视豆瓣热播动漫
    @GET(RetrofitManager.API_Prefix + "movie_v2/hotNewCartoon")
    Observable<MovieCartonHotEntity> getMovieCartonHot(@QueryMap Map<String, String> map);

    //电影专题或电视剧专题列表接口
    @GET(RetrofitManager.API_Prefix + "movie/movie_info")
    Observable<BaseEntity> getMovieShareInfo(@QueryMap Map<String, String> map);

    //电影专题或电视剧专题列表接口
    @GET(RetrofitManager.API_Prefix + "movie/movie_info")
    Observable<MovieInfoEntity> getMovieInfo(@QueryMap Map<String, String> map);

    //app基本配置接口
    @GET(RetrofitManager.API_Prefix + "system/jjbconfig")
    Observable<BaseConfigEntity> getBaseConfig(@QueryMap Map<String, String> map);

    //获取首页tab未读消息
    @GET(RetrofitManager.API_Prefix + "article/getChannelUnreadInfo")
    Observable<UnreadMaseggeEntity> getHomeTabUnreadMsg();

    //清除首页tab小红点
    @GET(RetrofitManager.API_Prefix + "/article/saveChannelUnreadInfo")
    Observable<ClearUnreadEntity> clearHomeTabUnreadMsg(@QueryMap Map<String, String> map);

    //分享次数，浏览次数，下载次数统计
    @GET(RetrofitManager.API_Prefix + "/lock_fans/share_counter\n")
    Observable<BaseEntity> lockFansShareCounter(@QueryMap Map<String, String> map);


    //电影雷达影院规则
    @GET(RetrofitManager.API_Prefix + "/movie_radar/listRule")
    Observable<MovieRadarSearchListEntity> movieRadarCrawListValue();

    //电影雷达影院详情规则
    @GET(RetrofitManager.API_Prefix + "/movie_radar/detailRule")
    Observable<MovieRadarMovieDetailEntity> movieRadarCrawDetailValue(@QueryMap Map<String, String> map);

    //爬取错误上报
    @GET(RetrofitManager.API_Prefix + "movie_radar/hostFailureCount")
    Observable<BaseEntity> movieCrawError(@QueryMap Map<String, String> map);

    //各影视板块首页
    @GET(RetrofitManager.API_Prefix + "movie_v2/indexRecommend")
    Observable<MovieIndexRecommendEntity> movieIndexRecommend(@QueryMap Map<String, String> map);

    //影视分享信息上传后台
    @POST(RetrofitManager.API_Prefix + "movie/uploadmoviedetail")
    Observable<UploadMovieDetailBean> getUploadMovieDetail(@QueryMap Map<String, String> map);

    //兑换阅读赚的提现余额为掘金宝
    @POST(RetrofitManager.API_Prefix + "user/exchangeWechatAppletMoney")
    Observable<ExChangeMiniProgramEntity> exChangeMiniProgramToJJB();

    //兑换阅读赚的提现余额为掘金宝
    @POST(RetrofitManager.API_Prefix + "user/wxappletWalkNotity")
    Observable<ExChangeWalkMiniProgramEntity> exChangeWalkMiniProgramToJJB();

    /**********************************小说相关接口*********************************************/

    //获取小说首页数据
    @GET(RetrofitManager.API_Novel + "/novelsearch/api/getIndexInfo")
    Observable<BooKDataEntity> getBookData();

    //获取小说的详情
    @GET(RetrofitManager.API_Novel + "/novelsearch/api/getNovelInfo")
    Observable<BooKInfoEntity> getBookInfo(@QueryMap Map<String, String> map);

    //获取小说的章节列表
    @GET(RetrofitManager.API_Novel + "/novelsearch/api/getNovelChapterList")
    Observable<BookChapterEntity> getChapterList(@QueryMap Map<String, String> map);

    //获取小说详情页推荐列表
    @GET(RetrofitManager.API_Novel + "/novelsearch/api/getNovelOtherList")
    Observable<BookCommendEntity> getCommendList(@QueryMap Map<String, String> map);

    //加入书架
    @GET(RetrofitManager.API_Novel + "/novelsearch/api/addToMyNovel")
    Observable<BaseEntity> addToMyNovel(@QueryMap Map<String, String> map);

    //获取我的书架小说列表
    @GET(RetrofitManager.API_Novel + "/novelsearch/api/getMyNovelList")
    Observable<BookshelfEntity> getMyNovelList();

    //获取最近阅读小说列表
    @GET(RetrofitManager.API_Novel + "/novelsearch/api/getMyNovelRecordList")
    Observable<BookshelfEntity> getMyNovelRecordList();

    //移除我的书架小说
    @GET(RetrofitManager.API_Novel + "/novelsearch/api/delFromMyNovel")
    Observable<BaseEntity> delFromMyNovel(@QueryMap Map<String, String> map);

    //移除最近阅读记录
    @GET(RetrofitManager.API_Novel + "/novelsearch/api/delFromMyNovelRecord")
    Observable<BaseEntity> delFromMyNovelRecord(@QueryMap Map<String, String> map);

    //获取小说的分类列表
    @GET(RetrofitManager.API_Novel + "/novelsearch/api/getCategoryList")
    Observable<BookCategoryEntity> getCategoryList();

    //获取小说的分类详情
    @GET(RetrofitManager.API_Novel + "/novelsearch/api/getCategoryInfo")
    Observable<BookCategoryDetailEntity> getCategoryInfo(@QueryMap Map<String, String> map);

    //获取小说列表数据，包括数据：推荐数、总点击、收藏数、入库时间、最近更新、全本、分类、搜索
    @GET(RetrofitManager.API_Novel + "/novelsearch/api/getNovelList")
    Observable<BookListEntity> getNovelList(@QueryMap Map<String, String> map);

    //获取小说的章节详情
    @GET(RetrofitManager.API_Novel + "/novelsearch/chapter/read")
    Observable<BookChapterDetailEntity> getChapterRead(@QueryMap Map<String, String> map);

    //获取热门搜索记录
    @GET(RetrofitManager.API_Novel + "/novelsearch/api/getSearchRecordList")
    Observable<BookSearchEntity> getSearchRecordList();

    //检查应用商店版本绕审功能状态
    @GET(RetrofitManager.API_Prefix + "/System/check_appstore")
    Observable<AuditEntity> getAuditData();

    //用户福利奖励(首页开屏弹窗广告)
    @GET(RetrofitManager.API_Prefix + "/reward/user_welfare_reward")
    Observable<WelFareRewardEntity> getWelFareReward();



    /**********************************音乐相关接口*********************************************/
    //音乐首页
    @GET(RetrofitManager.API_Prefix + "/music_config/index")
    Observable<MusicSearchFromEntity> getSearchFrom();
    //音乐首页
    @GET(RetrofitManager.API_Music + "/music")
    Observable<MusicDataListEntity> getMusicData();
    //收藏列表
    @GET(RetrofitManager.API_Music + "/music/collectLists")
    Observable<MusicCollectEntity> getCollectList();
    //历史列表
    @GET(RetrofitManager.API_Music + "/music/history")
    Observable<MusicHistoryEntity> getHistoryList();
    //歌单列表
    @GET(RetrofitManager.API_Music + "/music/lists")
    Observable<MusicListEntity> getMusicList(@QueryMap Map<String, String> map);
    //热搜列表
    @GET(RetrofitManager.API_Music + "/music/hotSearch")
    Observable<MusicHotListEntity> getHotList();
    //删除历史记录
    @POST(RetrofitManager.API_Music + "/music/delHistory")
    Observable<BaseEntity> delHistory(@QueryMap Map<String, Object> map);
    //取消收藏
    @POST(RetrofitManager.API_Music + "/music/delCollect")
    Observable<BaseEntity> delCollect(@QueryMap Map<String, Object> map);
    //收藏歌曲
    @POST(RetrofitManager.API_Music + "/music/addCollect")
    Observable<BaseEntity> addCollect(@QueryMap Map<String, Object> map);
    //收藏搜索出来的歌曲
    @POST(RetrofitManager.API_Music + "/music/addSongs")
    Observable<AddSongEntity> addSongs(@QueryMap Map<String, Object> map);
    //添加歌曲到历史列表
    @POST(RetrofitManager.API_Music + "/music/addHistory")
    Observable<BaseEntity> addHistory(@QueryMap Map<String, String> map);
    //累加歌曲播放次数
    @POST(RetrofitManager.API_Music + "/music/songIncr")
    Observable<BaseEntity> songIn(@QueryMap Map<String, String> map);

    //新歌速递标签
    @POST(RetrofitManager.API_Music + "/music/latestSongTag")
    Observable<LatestMusicTagEntity> latestSongTag(@QueryMap Map<String, String> map);
    //新歌速递列表
    @POST(RetrofitManager.API_Music + "/music/latestSongList")
    Observable<LatestMusicListEntity> latestSongList(@QueryMap Map<String, String> map);
    //所有榜单列表
    @POST(RetrofitManager.API_Music + "/music/allRankList")
    Observable<SongBillBoardEntity> allRankList(@QueryMap Map<String, String> map);
    //最新专辑标签
    @POST(RetrofitManager.API_Music + "/music/albumTag")
    Observable<LatestAlbumTagEntity> albumTag(@QueryMap Map<String, String> map);
    //最新专辑列表
    @POST(RetrofitManager.API_Music + "/music/albumList")
    Observable<LatestAlbumListEntity> albumList(@QueryMap Map<String, String> map);
    //最新专辑详情
    @POST(RetrofitManager.API_Music + "/music/albumDetail")
    Observable<AlbumDetailEntity> albumDetail(@QueryMap Map<String, String> map);
    //收藏歌单
    @POST(RetrofitManager.API_Music + "/user/addCollectSheet")
    Observable<BaseEntity> addCollectSheet(@QueryMap Map<String, String> map);
    //取消收藏歌单
    @POST(RetrofitManager.API_Music + "/user/delCollectSheet")
    Observable<BaseEntity> delCollectSheet(@QueryMap Map<String, String> map);
    //我的歌单收藏
    @POST(RetrofitManager.API_Music + "/user/myCollect")
    Observable<MusicCollectionEntity> gedanCollect(@QueryMap Map<String, String> map);
    //歌单分类
    @POST(RetrofitManager.API_Music + "/music/classArea")
    Observable<ClassAreaEntity> classArea(@QueryMap Map<String, String> map);
    //歌单分类详情
    @POST(RetrofitManager.API_Music + "/music/classAreaDetail")
    Observable<ClassAreaDetailEntity> classAreaDetail(@QueryMap Map<String, String> map);
    //音乐播放统计
    @POST(RetrofitManager.API_Music + "/music/songIncr")
    Observable<BaseEntity> songIncr(@QueryMap Map<String, String> map);
    //榜单详情
    @POST(RetrofitManager.API_Music + "/music/rankList")
    Observable<MusicListEntity> rankList(@QueryMap Map<String, String> map);
}