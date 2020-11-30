package com.newsuper.t.consumer.bean;

import com.google.gson.Gson;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GoodsListBean extends BaseBean implements Serializable{
    public GoodsListData data;

    public class GoodsListData implements Serializable{
        public String outtime_info;//预订单
        public int worktime;    // int 	店铺状态，-1暂停营业，0休息中,1营业中
        public int ordervalid;  //int 	店铺是否开启微信下单，1开启，0关闭，这个只针对微信端（注：关闭可以进店铺，不能微信下单）
        public int showtype;    // int 	店铺展示类型，1:纯文字，2:小图显示，3:中图显示，4:大图显示
        public int pageTotal;   // int 	该一级或二级分类下商品总页数
        public int is_first_order; //int 	是不是首单,0:否，1：是
        public int is_first_discount; // int 	是否开启首单减免,0:否，1：是
        public String first_discount; //string 	首单减免金额
        public String open_promotion; //string 	是否开启满减活动，1开启，0不开启
        public String promotion;// 	string 	满减活动列表
        public List<ManJian> promotion_rule;
        public String is_coupon;//	string 	是否启用优惠券，1启用，0不启用
        public String is_discount;//string 	店铺是否开启折扣值，1启用，0不启用
        public String discount_value;//string 	店铺开启打折的折扣值
        public String is_collect;//	string 	店铺是否被收藏，1收藏，0没收藏
        public String shopname;// 	string 	店铺名称
        public String is_only_promotion;// 	string 	满减活动是否只支持在线支付和余额付款,1是，0不是
        public String discountlimitmember;// 	string 	是否只有会员才能打折,1是，0不是
        public String image;// 	string 	店铺图片
        public String is_guodu;// 	string 	是否需要分类过渡，0表示不需要分类过渡滑动，1表示需要分类过渡滑动
        public String is_total;//string 	商品是一次传给客户端还是分批传给客户端，0分批，1不分批
        public ArrayList<GoodsInfo> foodlist;//	json_array_string 	商品列表，value值是该分类所有的商品
        public ArrayList<GoodsPackage> food_package;//array 	商品列表，value值是该分类所有的商品
        public ArrayList<SecondType> foodsecondtypelist;//json_array_string 	商品二级分类列表
        public ArrayList<Type> foodtype;//	json_array_string 	商品一级分类列表
        public String IsShopMember;//string 	是不是店铺会员，1是，0不是
        public String memberFreeze;//	string 	会员是否被冻结，1被冻结，0没有被冻结
        public ArrayList<GoodsPackage> foodPackageSelectedArr;//	json_array_string 	选中商品套餐的信息，字段参照food_package里面值
        public ArrayList<GoodsInfo> foodSelectedArr;//json_array_string 	选中商品的信息，字段参照foodlist里面值
        public String basicprice;//起送价
        public String switch_advertising;//是否开启广告图片 0否 1是
        public String image_advertising;//广告图片
        public Exclusivecoupon exclusivecoupon;
        public ArrayList<GoodsInfo> foodDiscountlist;//折扣商品
        public ArrayList<GoodsPackage> foodPackageDiscountlist;//折扣套餐



    }

    public class Exclusivecoupon{
        public String amount;
        public ArrayList<Coupon> list;
    }

    public class Coupon implements Serializable{
        public String id;
        public String name;// 	优惠券名称
        public String des;// 	优惠券描述
        public String value;//优惠券金额
        public String basic_price;//优惠券使用最低金额限制
        public String deadline;// 	优惠券有效期
        public String got;//是否领取 0否 1是
        public String num;//优惠券库存量，-1表示无限库存
    }


    public class GoodsInfo implements Serializable{
        public String outtime_info;//预订单
        public String shop_id;//判断商品属于哪个店铺
        public String shopname;//所属店铺名称
        public String type_id;// 	商品一级分类ID
        public String ordernum;// 	string 	该商品下单数，默认为0
        public String second_type_id;//	string 	商品二级分类ID，默认0即商品不存在二级分类
        public String id;// 	string 	商品ID
        public String name;// 	string 	商品名称
        public String tag;//	string 	决定展示顺序，编号越小越靠前
        public String unit;// 	string 	商品单位，默认为空
        public String buying_price;// 	string 	商品进货价
        public String label;// 	string 	商品标签
        public String price;// 	string 	商品价格
        public String has_formerprice;//	string 	商品是否有原价，默认0没有，1有
        public String formerprice;// 	string 	商品原价
        public String img;// 	string 	商品图片路径
        public String point;// 	string 	商品可获积分，默认为0
        public String stockvalid;//	string 	商品是否开启库存功能，默认0不开启，1开启
        public long stock;//	string 	商品当前库存量
        public String ordered_count;// 	string 	商品总销量
        public String memberlimit;// 	string 	商品是否会员专享，默认0不开启，1开启
        public String member_price_used;// 	string 	是否开启会员价，默认0不开启，1开启
        public String member_price;// 	string 	商品会员价
        public String is_dabao;// 	string 	是否收取打包费,默认为0不开启，1开启
        public String dabao_money;// 	string 	商品打包费
        public String is_nature;// 	string 	是否开启自定义属性，默认0不开启，1开启
        public ArrayList<GoodsNature> nature;// 	json_array_string 	商品属性
        public String open_autostock;// 	string 	是否开启每天自动库存功能,1开启，0关闭
        public String autostocknum;// 	string 	每天自动库存量
        public String is_limitfood;//	string 	是否开启限购活动,1开启，0不开启
        public String start_time;// 	string 	限购活动生效开始时间
        public String stop_time;// 	string 	限购活动生效结束时间
        public String limit_tags;//	string 	限购活动标签
        public String is_all_limit;// 	string 	是否开启整个活动期间每人限购，1开启，0不开启
        public String is_all_limit_num;// 	string 	整个活动期间每人限购数量

        public String is_order_limit;//每单限购
        public String order_limit_num;

        public String is_customerday_limit;// 	string 	是否开启每人每天限购，1开启，0不开启
        public String day_foodnum;// 	string 	每人每天限购数量
        public String datetage;//	string 	是否在商品限购生效时间段内，1是，0不是
        public String timetage;// 	string 	判断是否在限制时间段内，1是，0不是
        public String hasBuyNum;// 	string 	客户限购生效时间内已经购买该商品数量
        public String hasBuyNumByDay;// 	string 	客户限购生效时间内当天已经购买该商品数量
        public ArrayList<LimitTime> limit_time;//array 	限购商品限制的时间段
        public String start;// 	string 	限购商品限制的时间段里面的开始时间
        public String stop;// 	string 	限购商品限制的时间段里面的结束时间
        public int count;
        public ArrayList<String> image;
        public String min_price;//最低属性价
        public ArrayList<PackageNature> packageNature;
        public String descript;
        public String description;
        public String minBasicprice;
        public int foodLimitType;
        public String timeRelativeHD;
        public String status;
        public int worktime;
        public String showsales;
        public String IsShopMember;
        public String memberFreeze;
        public String index;//套餐或有属性商品需要用到索引记录选择的顺序
        public String open_promotion;
        public List<ManJian> promotion_rule;
        public String is_only_promotion;// 	string 	满减活动是否只支持在线支付和余额付款,1是，0不是

        public String switch_discount;//是否为折扣商品 0否 1是
        public String num_discount;//每笔订单折扣商品的最大购买量，0为不限
        public String rate_discount;//折扣率
        public String discount_type;//"0" 表示套餐 "1"表示商品   该字段是自添加的，用来区分折扣里的套餐 和商品
        public boolean isUseDiscount;//是否使用折扣 该字段是在添加商品时，前端自行判断

        //由于优惠券、广告图片和商品处于同一个列表中，所以给商品增加以下属性
        public String switch_advertising;//是否开启广告图片
        public String image_advertising;//广告图片
        public ArrayList<Coupon> exclusivecoupon;
        public String min_buy_count;//最小购买数量

        public ArrayList<MemberGradePrice> member_grade_price; //会员等级价格
        public String discount_show_type;//1同时显示  2只显示在折扣分类 3只显示在普通分类
        public String original_type_id;

        @Override
        public boolean equals(Object obj) {
            if (obj.getClass() == GoodsInfo.class) {
                GoodsInfo goodsInfo = (GoodsInfo)obj;
                //套餐
                if (null != goodsInfo.packageNature && goodsInfo.packageNature.size() > 0){
                    boolean isEqual=goodsInfo.id.equals(id)&&(new Gson().toJson(goodsInfo.packageNature).equals(new Gson().toJson(packageNature)));
                    return isEqual;
                }else{
                    if(null!=goodsInfo.nature&&goodsInfo.nature.size()>0){
                        return goodsInfo.id.equals(id)&&(new Gson().toJson(goodsInfo.nature).equals(new Gson().toJson(nature)));
                    }else{
                        return goodsInfo.id.equals(id);
                    }
                }
            }
            return false;
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }

    }

    public class LimitTime implements Serializable{
        public String start;
        public String stop;
    }

    public class GoodsNature implements Serializable{
        public String naturename;
        public String maxchoose;
        public ArrayList<GoodsNatureData> data;

        //重写equals()方法
        @Override
        public boolean equals(Object obj) {
            if (obj.getClass() == GoodsNature.class) {
                GoodsNature nature = (GoodsNature)obj;
                return nature.naturename.equals(naturename);
            }
            return false;
        }
    }

    public class GoodsNatureData implements Serializable{
        public String naturevalue;
        public String price;
        public boolean is_selected;
        //重写equals()方法
        @Override
        public boolean equals(Object obj) {
            if (obj.getClass() == GoodsNatureData.class) {
                GoodsNatureData natureData = (GoodsNatureData)obj;
                return natureData.naturevalue.equals(naturevalue);
            }
            return false;
        }
    }

    public static class GoodsPackage implements Serializable{
        public String outtime_info;//预订单
        public String shop_id;
        public String name;//	string 	商品名
        public String price;//	string 	价钱
        public String img;// 	string 	商品图片路径
        public ArrayList<PackageNature> nature;
        public int count;
        public String id;
        public String unit;
        public String label;
        public String is_dabao;
        public String dabao_money;
        public String ordered_count;
        public String ordernum;
        public String descript;
        public String description;
        public String minBasicprice;
        public ArrayList<String> image;
        public String showsales;
        public int worktime;
        public String status;
        public String formerprice; // string 	商品原价
        public String has_formerprice;//	string 	商品是否有原价，默认0没有，1有

        public String open_promotion;
        public List<ManJian> promotion_rule;
        public String is_only_promotion;// 	string 	满减活动是否只支持在线支付和余额付款,1是，0不是

        public String switch_discount;//是否为折扣商品 0否 1是
        public String num_discount;//每笔订单折扣商品的最大购买量，0为不限
        public String rate_discount;//折扣率
        public String discount_type;
        public String min_buy_count;//最小购买数量
        public String discount_show_type;//1同时显示  2只显示在折扣分类 3只显示在普通分类
        public String original_type_id;
    }

    public class PackageNature implements Serializable{
        public String name;
        public ArrayList<PackageNatureValue> value;
        //重写equals()方法
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj.getClass() == PackageNature.class) {
                PackageNature packageNature = (PackageNature)obj;
                return packageNature.name.equals(name);
            }
            return false;
        }
    }

    public class PackageNatureValue implements Serializable{
        public String id;
        public String name;
        public String stockvalid;
        public int stock;
        public String status;
        public boolean is_selected;//判断是否选中
        public boolean isCanSelect=true;//判断商品是否能选,默认可选
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj.getClass() == PackageNatureValue.class) {
                PackageNatureValue value = (PackageNatureValue)obj;
                return value.id.equals(id);
            }
            return false;
        }
    }

    public class Type implements Serializable{
        public String id;//	string 	商品分类id
        public String name;//	string 	名称
        public String is_weekshow; //string 	是否开启星期几显示
        public String week; //	string 	1、星期一 2、星期二 3、星期三 4、星期四 5、星期五 6、星期六 7、星期日
        public String is_show;//	string 	是否显示商品分类,1:显示,0:不显示
        public String shows_tart_time;//	string 	商品分类显示开始时间
        public String shows_stop_time;//	string 	商品分类显示结束时间段
    }

    public class SecondType implements Serializable{
        public String typeid;    //string 	一级分类ID
        public String second_type_id;    //string 	二级分类ID
        public String name;    //string 	二级分类名称
    }

    public class MemberGradePrice implements Serializable{
        public String grade_id;    //string 	会员等级
        public String price;    //string 	会员价格
    }
}
