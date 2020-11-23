package com.newsuper.t.juejinbao.ui.share.entity;

import android.graphics.Bitmap;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;
public class ShareInfo extends Entity {

    public static final String TYPE_POSTER = "poster";
    public static final String TYPE_DOWNLOAD = "download";

    public static final String TYPE_ARTICLE = "article"; //文章
    public static final String TYPE_MOVIE = "movie"; //影视
    public static final String TYPE_MUSIC = "music"; //影视
    public static final String TYPE_MOVIE_LIST = "movieList"; //影视首页
    public static final String TYPE_VIDEO = "video"; //视频
    public static final String TYPE_SMALL_VIDEO = "smallvideo"; //小视频
    public static final String TYPE_PICTURES = "picture"; //图集

    public static final String TYPE_HOME = "index";
    public static final String TYPE_TASK = "task";
    public static final String TYPE_ME = "me";
    public static final String TYPE_SLEEP_MONEY = "sleepMoney";


    public static final String PATH_SHOUYE = "/";
    public static final String PATH_TASK = "/task";
    public static final String PATH_PERSONAL_CENTER = "/personal_center";
    public static final String PATH_MAKE_MONEY = "/make_money";
    public static final String PATH_ARTICLE = "/ArticleDetails";
    public static final String PATH_MOVIE = "/movie";
    public static final String PATH_MUSIC = "/music";
    public static final String PATH_VIDEO = "/VideoDetail/";

    public ShareInfo() {
    }

    public ShareInfo(String url, String title, String description, String thumb, String type, String path, String id) {
        this.url = url;
        this.title = title;
        this.description = description;
        this.thumb = thumb;
        this.type = type;
        this.id = id;
    }

    private String url;
    private String title;
    private String description;
    private String thumb;

    private String type = "";
    private String id = ""; // 获取大图海报时需要
    private String aid = "";  // 图集 视频埋点时需要 对应的id
    private String uploadId = ""; //影视海报信息上传到后端后返回的id，用于落地页获取影视信息

    private String url_type = "";
    private String url_path = "";
    private String bigPic = "";
    private int isPictrueBybase64; //分享大图时是否是base64格式
    private String platform_type;

    private String shareContent;  //分享文案

    private String sharePicUrl; //图片用于生成分享大图
    private String fullPicUrl; //用于全屏大图分享
    private Bitmap bitmap; //分享的图

    private String text_id; //分享文案id

    private int column_id; //频道id

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public int getColumn_id() {
        return column_id;
    }

    public void setColumn_id(int column_id) {
        this.column_id = column_id;
    }

    private String mini_program_id = "";
    private String mini_program_path = "";

    public String getMini_program_id() {
        return mini_program_id;
    }

    public void setMini_program_id(String mini_program_id) {
        this.mini_program_id = mini_program_id;
    }

    public String getMini_program_path() {
        return mini_program_path;
    }

    public void setMini_program_path(String mini_program_path) {
        this.mini_program_path = mini_program_path;
    }

    public String getText_id() {
        return text_id;
    }

    public void setText_id(String text_id) {
        this.text_id = text_id;
    }

    public static String getTypePoster() {
        return TYPE_POSTER;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getFullPicUrl() {
        return fullPicUrl;
    }

    public void setFullPicUrl(String fullPicUrl) {
        this.fullPicUrl = fullPicUrl;
    }

    public String getSharePicUrl() {
        return sharePicUrl;
    }

    public void setSharePicUrl(String sharePicUrl) {
        this.sharePicUrl = sharePicUrl;
    }

    public int getIsPictrueBybase64() {
        return isPictrueBybase64;
    }

    public void setIsPictrueBybase64(int isPictrueBybase64) {
        this.isPictrueBybase64 = isPictrueBybase64;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    //是否是走路赚钱的分享
    private int isWalk = 0;

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    //开宝箱分享
    private boolean isRewardBox = false;

    public String getPlatform_type() {
        return platform_type;
    }

    public void setPlatform_type(String platform_type) {
        this.platform_type = platform_type;
    }

    public String getBigPic() {
        return bigPic;
    }

    public void setBigPic(String bigPic) {
        this.bigPic = bigPic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getUrl_type() {
        return url_type;
    }

    public void setUrl_type(String url_type) {
        this.url_type = url_type;
    }

    public String getUrl_path() {
        return url_path;
    }

    public void setUrl_path(String url_path) {
        this.url_path = url_path;
    }

    public boolean isRewardBox() {
        return isRewardBox;
    }

    public void setRewardBox(boolean rewardBox) {
        isRewardBox = rewardBox;
    }

    public int getIsWalk() {
        return isWalk;
    }

    public void setIsWalk(int isWalk) {
        this.isWalk = isWalk;
    }

    @Override
    public String toString() {
        return "ShareInfo{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", thumb='" + thumb + '\'' +
                ", type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", aid='" + aid + '\'' +
                ", url_type='" + url_type + '\'' +
                ", url_path='" + url_path + '\'' +
                ", bigPic='" + bigPic + '\'' +
                ", isPictrueBybase64=" + isPictrueBybase64 +
                ", platform_type='" + platform_type + '\'' +
                ", shareContent='" + shareContent + '\'' +
                ", sharePicUrl='" + sharePicUrl + '\'' +
                ", fullPicUrl='" + fullPicUrl + '\'' +
                ", bitmap=" + bitmap +
                ", isWalk=" + isWalk +
                ", isRewardBox=" + isRewardBox +
                '}';
    }
}
