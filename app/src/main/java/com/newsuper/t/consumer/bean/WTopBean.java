package com.newsuper.t.consumer.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/31 0031.
 */

public class WTopBean extends BaseBean {
    public WTopData data;
    public static class WTopData{
        public String memberFreeze;
        public DivPageData divpage;
        public String pinpai_logo;
        public String area_due;
        public String no_area;
        public String is_advert_show;
        public String is_show_expected_delivery;
        public int is_area;
        public int is_area_button;
        public int is_join_button;
    }
    public static class DivPageData{
        public String id;
        public String admin_id;
        public String name;
        public ArrayList<PageData> data;
        public String area_id;

    }

    //副文本
    public static class RichTextData{
        public String html;

    }

    //跳转数据
    public static class BaseData{
        public String h5_url;
        public String url;
        public String url_from;
        public String url_from_id;
        public String lat;
        public String lng;
        public String desc;

    }
    // 轮播图
    public static class LunboImageData extends BaseData{
        public String image;
        public String title;
    }
    //图标导航
    public static class IconGuideData extends BaseData{
        public String image;
        public String title;
    }
    //活动导航
    public static class ActivityGuideData{
        public String show_type;
        public ArrayList<ActivityData> list;
    }
    public static class ActivityData extends BaseData{
        public String image;
        public String title;
        public String secondtitle;
    }
    //标题
    public static class TitleData extends BaseData{
        public String secondtitle;
        public String title;
        public String align;
        public String backgroundcolor;
        public String style;
        public String linkTitle;
        public ArrayList<BaseData> list;
    }
    //图片导航
    public static class PictureGuideData{
        public ArrayList<PictureGuide> list;
        public String bg_color;
        public String pageSpace;
        public String imageSpace;
        public String radioVal;
        public String scrollWay;
        public String text_color;
        public String back_color;

    }
    public static class PictureGuide extends BaseData{
        public String image;
        public String title;
        public String rowStyle;
        public String text_color;
        public String width;
        public String height;
    }
    //文本导航
    public static class TextGuideData extends BaseData{
        public String title;

    }
    //图片广告
    public static class PictureAdvertismentData{
        public String show_type;
        public ArrayList<PictureAdvertisment> list;
        public String space;
        public String pageSpace;
        public String radioVal;

    }
    public static class PictureAdvertisment extends BaseData{
        public String image;
        public String title;
        public String text_color;
        public String width;
        public String height;
    }
    public static class AdvertismentPoster extends BaseData{
        public String image;
        public String title;
        public String secondtitle;
        public String width;
        public String height;
    }
    //活动优选
    public static class ActivitySelectData{
        public ArrayList<ActivityData> list;
    }
    //弹框广告
    public static class AdvertDataList{
        public ArrayList<AdvertData> list;
    }
    public static class AdvertData extends BaseData{
        public String image;
        public String title;
    }





    //店铺优选
    public static class ShopSelectData{
        public ArrayList<ShopSelect> list;
    }

    public static class ShopSelect implements Serializable{
        public String desc;
        public String shop_name;
        public String shop_id;
        public String image;
        public String shopname;
        public String worktime;
    }

    //推荐店铺
    public static class ShopListData{
        public String title;
        public String is_show_sale;
        public ArrayList<TopBean.ShopList> list;
    }

    //横排店铺
    public static class HShopList{
        public String show_shop_status;
        public ArrayList<ShopSelect> list;
    }


    //商品优选
    public static class GoodsSelectData{
        public ArrayList<GoodsListBean.GoodsInfo> list;
    }
    //商品优选
    public static class PhoneData{
        public String phone;
        public String title;
        public String prompt;
    }

    //辅助线
    public static class Fuzhuxian{

    }
    //辅助空白
    public static class FuzhuKongbai{
        public String height;
        public String colors;
    }
    //进入店铺
    public static class ShopComeIn implements Serializable{
        public String shopname;
        public String shop_id;
    }
    //公告
    public static class GongGao{
        public String content;
    }
    //商品搜索
    public static class FoodSearchData{

    }

    //广告海报
    public static class AdvertismentPosterData{
        public ArrayList<AdvertismentPoster> list;
        public String show_type;
        public String radioVal;
        public String space;
        public String pageSpace;
    }
    //猜你喜欢
    public static class GuessLikeData{
        public String shop_id;
        public String shopname;
        public String shopimage;
    }
    public static class GoodsGroupData{

        public String show_all_group;
        public String menu_type;
        public String list_type;
        public String radioVal;
        public String text_align;
        public String imageSpace;
        public boolean show_goods_sales;
        public boolean show_goods_price;
        public boolean show_goods_des;
        public boolean show_goods_name;
        public boolean show_buy_btn;
        public String pageSpace;
        public ArrayList<GoodsGroupListData> group_list;

    }

    public static class AgainOrderData{
        public String text;
    }

    public static class GoodsGroupListData{

        public String id;
        public String name;
        public String admin_id;
        public String tag;
        public ArrayList<GoodsListBean.GoodsInfo> foodlist;
    }

    public static class PageData{
        public String type;
        public String tag;
        public String show_type;
        public String num;
        public String radioVal;
        public String space;
        public String pageSpace;
        public String is_show_sale;
        public RichTextData rich_textData; // 副文布
        public ArrayList<LunboImageData> lunbo_imageData; // 轮播图
        public ArrayList<IconGuideData> tubiao_daohangData; //图标导航
        public ActivityGuideData huodong_daohangData; //活动导航
        public TitleData biaotiData; //标题
        public PictureGuideData tupian_daohangData; // 图片导航
        public ArrayList<TextGuideData> wenben_daohangData; //文本导航
        public PictureAdvertismentData tupian_guanggaoData; //图片广告
        public ArrayList<Fuzhuxian> fuzhuxiData; //辅助线
        public FuzhuKongbai fuzhukongbaiData; //辅助空白
        public ShopComeIn jinrudianpuData; //进入店铺
        public GongGao gonggaoData; //公告
        public ArrayList<GoodsListBean.GoodsInfo> shangpin_liebiaoData;//商品列表
        public ShopListData dianpu_liebiaoData;//推荐店铺
        public ArrayList<FoodSearchData> food_searchData; //商品搜索
        public AdvertismentPosterData guanggao_haibaoData; //广告海报
        public ActivitySelectData huodong_youxuanData; //活动优选
        public ShopSelectData dianpu_youxuanData; //店铺优选
        public HShopList hengpai_dianpuData;  //横排店铺
        public GoodsSelectData shangpin_youxuanData;//商品优选
        public ArrayList<ShopSelect> push_shopData;//竖排店铺
        public PhoneData phoneData;//拨打电话
        public AdvertDataList tankuang_guanggaoData;
        public ArrayList<GuessLikeData> guess_likeData;
        public GoodsGroupData shangpin_fenzuData;
        public AgainOrderData again_orderData;
    }


}
