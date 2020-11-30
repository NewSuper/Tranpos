package com.newsuper.t.consumer.bean;

import java.util.ArrayList;


public class OrderInfoBean extends BaseBean {
    public OrderData data;

    public class OrderData {
        public String cutmoney;    //订单的优惠金额
        public String status;    //订单状态
        public OrderInfo orderinfo; //订单信息
        public CourierInfo courier; //配送员信息
        public ArrayList<OrderItem> orderitem;//商品信息数组
        public CommentContent commentModel; //评论内容
        public String init_time_stamp;   //订单创建的时间戳
        public ShopModel shopmodel;
        public String redEnevlopeOpen;//是否开启红包分享功能
        public RedPackageInfo redEnevlopeInfo;
        public Roulette roulette;
    }

    public class ShopModel {
        public String shopname;
        public String orderphone;
        public String id;
        public String shopaddress;
        public String is_opencomment;
    }

    public class OrderInfo {
        public String id;    //订单id
        public String admin_id;    //乐外卖账号id
        public String shop_id;    //店铺id
        public String lewaimai_customer_id;    //顾客id
        public String order_no;    //全局唯一的订单号，应该不允许重复，这个是内部查询用的
        public String trade_no;    //订单号，这个是给商家看的
        public String nickname;    //顾客名称
        public String phone;    //顾客手机号码
        public String address;    //顾客地址
        public String memo;    //顾客备注
        public String configmemo;    //商家备注
        public String charge_type;    //1：货到付款 2：余额付款 3：在线支付
        public String pay_type;    //支付类型 1：乐外卖自己通道的支付宝 2：乐外卖自己通道的微信支付 3：乐外卖自己通道的财付通 4：钱方通道的微信支付（需要加余额）5：乐刷通道微信支付（需要加余额） 6：乐刷通道支付宝（需要加余额） 7：商户自己的微信支付（非特约商户）8：商户自己的微信支付（特约商户） 9：乐刷T1结算微信支付 10：乐刷T1结算支付宝支付
        public String is_dabao;    //是否收取打包费
        public String dabao_money;    //打包费金额
        public String promotion;    //满减优惠
        public String is_member_delete;    //是否会员优惠,1:是，0：否
        public String member_delete;    //会员优惠金额
        public String is_discount;    //是否打折
        public String discount_value;    //折扣值
        public String is_coupon;    //是否使用优惠券
        public String coupon_value;    //优惠券面值
        public String coupon_id;    //优惠券ID
        public String delivery_fee;    //配送费
        public String total_price; //订单总金额
        public String init_date;    //创建订单的时间
        public String confirme_date;    //确认订单的时间
        public String complete_date;    //完成订单的时间
        public ArrayList<OrderField> order_field;    //预设信息
        public String order_status;//订单状态
        public String courier_id;//配送员id
        public String courier_name;//配送员名称
        public String courier_phone; //配送员手机号码
        public String courier_type;//配送类型：1-商家配送,3-快服务,5-达达
        public String is_comment;//	是否评论店铺
        public String is_selftake;// 0:商家配送，1：到店自取
        public String delivery_date;//	配送日期
        public String delivertime;//配送时间
        public ArrayList<AddService> addservice;//增值服务
        public String courier_time;//设置配送员时间
        public String is_firstcut;//是否首单减免
        public String firstcut_value;//	首单减免金额

        public String from_type;//订单来源:0:外卖,1:后台新建,2:智能机,3:饿了么,4:美团外卖,5:百度外卖,6:小程序
        public String is_pickup;//此外卖订单是否取货，0：否，1：已取
        public String pickup_time;//配送员取货的时间
        public String cancel_detail;
        public String cancel_reason;

        public String is_show_expected_delivery;
        public String expected_delivery_times;

        public String delivery_mode;//配送模式

        //满赠
        public String is_manzeng;
        public String manzeng_name;

        public String  is_shop_first_discount;//（是否享受了新客立减 1是 0否）
        public String shop_first_discount ;//（新客立减实际减的金额）;

    }

    public class RedPackageInfo {
        public String id;
        public String admin_id;
        public String logo;
        public String link;
        public String coupon_data;
        public String share_title;
        public String share_desc;
        public String share_logo;
        public String rule_desc;
        public String lucky_num;
        public String share_id;
    }

    public class OrderField {
        public String content;
        public String name;
        public String price;
        public String type;
    }

    public class AddService {
        public String name;
        public String price;
    }

    public class CourierInfo {
        public String id;    //配送员id
        public String admin_id;    //乐外卖账号id
        public String shop_id;    //店铺id
        public String name;    //配送员姓名
        public String phone;    //手机号码
        public String successed_num;    //配送成功的订单数量
        public String failed_num;    //配送失败的订单数量
        public String total_num;    //抢单总数量
        public String longitude;    //配送员的经度
        public String latitude;    //配送员的纬度
        public String pic;    //头像
        public String errand_successed_num;//成功跑腿订单总数
        public String errand_failed_num;//失败跑腿订单总数
        public String errand_total_num;//跑腿订单总数
        public String comment_score;//评论总分数
        public String comment_num;//评论总次数数
    }

    public class CommentContent {
        public String comment_id;//评论id
        public String type;//评论的类型，1：店铺 2：商品
        public String lewaimai_customer_id;//乐外卖顾客id
        public String admin_id;//乐外卖账号id
        public String shop_id;//店铺id
        public String food_id;//商品id
        public Content content;//评论的内容
        public String grade;//对店铺的打分 1-5分
        public String is_showname;//是否是匿名评价 0、是匿名评论 1、不是匿名评论
        public String agreenum;//赞同的次数
        public String opposenum;//反对的次数
        public String agree_ids;//赞同者列表
        public String oppose_ids;//反对者列表
        public String time;//评论的时间
        public String status;//评论的状态
        public String is_read;//是否阅读，0、未读1、已读
        public String order_id;//乐外卖订单id
        public ArrayList<String> imgurl;//评论图片
        public ArrayList<String> tag;//评论标签
    }

    public class OrderItem {
        public String id;
        public String lewaimai_order_id;
        public String food_id;
        public String type_id;
        public String food_name;
        public String food_type;
        public String price;
        public String point;
        public String quantity;
        public String food_name_old;
        public String food_unit;
        public String food_second_type;
        public String buying_price;
        public String foodpackage_id;
        public String is_foodpackage;
        public String order_item_id;
    }

    public class Content {
        public String content;//顾客评论的内容
//        public boolean creply;
//        public boolean addcomment;
//        public boolean areply;
        public boolean is_creply;
        public Creply creply_content;
    }

    public class Creply {
        public String content;
        public String time;
    }

   // "roulette":{"is_roulette":0,"back_string":""}
    public class Roulette{
       public String is_roulette;
       public String back_string;
       public String back_url;
   }
}
