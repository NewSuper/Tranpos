package com.newsuper.t.consumer.utils;


public class UrlConst {
    //发送手机登录验证码
    public static final String LOGIN_SEND_CODE_2 = "customer/crm/account/customer/sendsorv";
    public static final String USER_XIEYI ="customer/common/merchant/settings/customerapp/setinfo";
    //是否升级灰度环境
    public static String GRAY_UPGRADE = "common/helper/chechGrayUpgradeStatus";
    //登录
    public static final String LOGIN = "customer/crm/account/customer/phonelogin";
    public static final String QQ_LOGIN = "customer/crm/account/customer/qqthirdlogin";
    public static final String WEINXIN_LOGIN = "customer/crm/account/customer/wxthirdlogin";
    public static final String GET_BACK_PASSWORD = "customer/crm/account/customer/modifypassword";
    public static final String BIND_PHONE ="customer/crm/account/customer/bindphone";
    //注册
    public static final String REGISTER = "customer/crm/account/customer/RegisterByPhone";
    //首页
    public static final String TOP_SHOP_LIST = "customer/common/shop/shop/list?ver=v2";
    public static final String TOP_WEI = "customer/common/divpage/divpage/info";
    public static final String TOP_WEI_AREA = "customer/common/divpage/divpage/area";
    public static final String TOP_COUPON = "customer/crm/marketing/pagecoupon/info";
    public static final String TOP_TAB = "customer/common/divpage/divpage/groupinfo";
    //店铺搜索
    public static final String SHOP_SEARCH = "customer/common/shop/shop/search";
    //店铺评论
    public static final String SHOP_COMMENTS= "customer/crm/communion/comment/list";
    //评论
    public static final String SHOP_COMMENT_INFO = "customer/crm/communion/comment/beforecomment";
    //店铺信息
    public static final String SHOP_INFO= "customer/common/shop/shop/info?ver=v2";
    //购物车下单结算
    public static final String CART_PLACE_ORDER= "customer/trade/process/waimai/ordering";
    //购物车
    public static final String SHOPPING_CART= "customer/common/page/cart/info";
    //商品详情
    public static final String GOODS_DETAIL= "customer/common/food/food/foodDetail";
    //商品搜索
    public static final String GOODS_SEARCH= "customer/common/page/food/searchFood?ver=v2";
    //购物车列表
    public static final String SHOPPING_CART_LIST= "customer/common/page/cart/cartInfoFromConfigs?ver=v2";
    //获取我的地址列表
    public static final String GET_ADDRESS_LIST = "customer/crm/account/address/list";
    //获取优惠劵列表
    public static final String GET_COUPON_LIST = "customer/crm/marketing/coupon/list";
    //添加或者修改顾客地址
    public static final String ADD_CUSTOMER_ADDRESS = "customer/crm/account/address/add";
    //删除顾客地址
    public static final String DEL_CUSTOMER_ADDRESS = "customer/crm/account/address/delete";
    //获取我的收藏列表
    public static final String GET_COLLECTION_LIST = "customer/common/shop/collection/list";
    //获取顾客信息
    public static final String CUSTOMERINFO = "customer/crm/account/customer/customerinfo";
    //编辑顾客信息
    public static final String EDITINFO = "customer/crm/account/customer/editinfo";
    //检查更新
    public static final String CHECKVERSION = "customer/common/version/version/androidversion";
    //顾客订单列表
    public static final String GETORDERLIST = "customer/trade/order/waimai/list";
    //外卖订单详情
    public static final String ORDER_INFO= "customer/trade/order/waimai/info";
    //获取又拍云
    public static final String GETUPYUN = "customer/common/helper/getupyun";
    //上传图片
    public static final String UPDATEIMG = "customer/crm/account/customer/updateheadimg";
    //收藏店铺
    public static final  String COLLECTSHOP="customer/common/shop/collection/add";
    //取消收藏店铺
    public static final  String QUITCOLLECTSHOP="customer/common/shop/collection/cancel";
    //获取配送区域
    public static final String ServiceArea="customer/common/shop/shop/map";
    //取消订单
    public static final String CANCELORDER="/customer/trade/order/waimai/cancelorder";
    //删除订单
    public static final String DELETEORDER="customer/trade/order/waimai/delete";
    //商品列表
    public static final String GETGOODSLIST = "customer/common/page/food/choose?ver=v2";
    //获取商品
    public static final  String GETGOODSBYTYPE="customer/common/page/food/getFoodByPage?ver=v2";
    //店铺评论
    public static final  String SHOPCOMMENT="customer/crm/communion/comment/edit";
    //修改店铺评论
    public static final  String EDITCOMMENT="customer/crm/communion/comment/modify";
    public static final  String SEND_CART_CODE ="customer/trade/order/waimai/sendcaptcha";
    //外卖下单继续支付
    public static final  String CONTINUE_PAY_ORDER ="customer/trade/process/waimai/continuepay";
    //外卖下单继续支付类型
    public static final  String CONTINUE_PAY_TYPE ="customer/common/shop/shop/weixinzhifubaotype?ver=v2";
    //获取后台设置app信息
    public static final String APP_INFO="customer/common/merchant/settings/customerapp/setinfo";
    //会员卡详情
    public static final String VIP_CARD = "customer/crm/account/member/setinfo";
    //会员余额明细
    public static final String VIP_BALANCE_DETAI = "customer/crm/account/member/balancehistory";
    //冻结会员
    public static final String FREEZE_VIP = "customer/crm/account/member/freezemember";
    //付款二维码
    public static final String QRCODE_PAY = "customer/crm/account/member/memberqrcode";
    //检测会员是否付款成功接口
    public static final String QRCODE_PAY_CHECK = "customer/crm/account/member/checkpaystatus";
    public static final String VIP_CHARGE="customer/crm/account/member/rechargebalance";
    //开通会员充值页面的数据
//    public static final String VIP_CHARGE_INFO="customer/crm/account/member/balancepay";
    public static final String VIP_CHARGE_INFO = "customer/crm/account/member/newbalancepay";

    //开通会员添加信息
    public static final String VIP_COMMIT_INFO="customer/crm/account/member/addinfo";
    //修改会员信息
    public static final String VIP_MODIFY_INFO="customer/crm/account/member/modifyinfo";
    //会员首页信息
    public static final String VIP_TOP_INFO="customer/crm/account/member/index";
    //会员信息
    public static final String VIP_INFO="customer/crm/account/member/memberinfo";
    //会员等级列表信息
    public static final String VIP_LEVEL_INFO="customer/crm/account/member/gradelist";
    //修改会员手机号
    public static final String VIP_MODIFY_PHONE="customer/crm/account/member/modifyphone";
    //充值状态查询
    public static final String VIP_CHECK_CHARG="customer/trade/order/pay/queryorder";

    //跑腿首页
    public static final String PAO_TUI_TOP = "customer/trade/order/errand/homepage";
    //跑腿分类
    public static final String PAO_CATEGORY = "customer/trade/order/errand/getcategory";
    //跑腿优惠券
    public static final String PAO_COUPON = "customer/crm/marketing/coupon/errandlist";
    //跑腿下单
    public static final String PAO_ORDER = "customer/trade/process/errand/order";

    //跑腿订单详情
    public static final String PAO_ORDER_DETAIL = "customer/trade/order/errand/details";
    //跑腿支付
    public static final String PAO_PAY = "customer/trade/process/errand/pay";
    //跑腿支付
    public static final String PAO_ADDRESS = "customer/trade/order/errand/getaddress";
    //跑腿支付
    public static final String PAO_SAMLL_FEE = "customer/trade/process/errand/paytip";
    //获取活动商品
    public static final String GOODS_COUPON= "customer/common/food/food/advertising";
    //获取活动商品
    public static final String GET_COUPON= "customer/crm/marketing/coupon/exclusivereceive";

    //检测必选商品
    public static final String CHECK_ORDER= "customer/common/shop/shop/checkorder?ver=v2";

    //同城信息
    //二级分类
    public static final String PUBLISH_SECOND_CATEGORY = "customer/common/tongcheng/tongcheng/getsecondcategory";
    //分区
    public static final String PUBLISH_AREA = "customer/common/tongcheng/tongcheng/getareainfo";
    //发布信息列表
    public static final String PUBLISH_LIST = "customer/common/tongcheng/tongcheng/publishlist";
    //发布类别
    public static final String PUBLISH_CATEGORY_ITEM = "customer/common/tongcheng/tongcheng/getCategoryImg";
    //发布搜索
    public static final String PUBLISH_SEARCH = "customer/common/tongcheng/tongcheng/Publishsearch";
    //发布举报
    public static final String PUBLISH_COLLECT = "customer/common/tongcheng/tongcheng/collect";
    //发布收藏
    public static final String PUBLISH_REPORT= "customer/common/tongcheng/tongcheng/report";
    //我的发布
    public static final String PUBLISH_MY = "customer/common/tongcheng/tongcheng/mypublish";
    //我的 收藏
    public static final String PUBLISH_MY_COLLECT = "customer/common/tongcheng/tongcheng/mycollect";
    //发详情布
    public static final String PUBLISH_DETAIL = "customer/common/tongcheng/tongcheng/getinfodetail";
    //我的发布 删除
    public static final String PUBLISH_MY_DEL = "customer/common/tongcheng/tongcheng/myPublishDel";
    //发布前信息
    public static final String PUBLISH_AHEAD = "customer/trade/process/tongcheng/getpreinfo";
    //发布信息 重发消息
    public static final String PUBLISH_INFO = "customer/trade/process/tongcheng/publish";
    //追加置顶
    public static final String PUBLISH_SET_TOP = "customer/trade/process/tongcheng/addtotop";
    //追加置顶前信息
    public static final String PUBLISH_SET_TOP_INFO = "customer/trade/process/tongcheng/newpreaddtotop";
    //重新编辑
    public static final String PUBLISH_EDIT = "customer/trade/process/tongcheng/reedit";

    //消息中心
    //消息列表
    public static final String MSG_LIST = "customer/crm/communion/message/getpushmsglist";
    //未读消息
    public static final String MSG_READ = "customer/crm/communion/message/noreadnum";
    //消息详情
    public static final String MSG_DETAIL = "customer/crm/communion/message/pushmsgdetail";
    //个人中心风格
    public static final String PERSON_STYLE = "customer/common/divpage/divpage/personalinfo";
    //邀请好友
    public static final String INVITE_FRIEND = "customer/crm/marketing/inviteprize/shareinfo";

    //店铺信息页面领取优惠券
    public static final String GET_COUPON_SHOP = "customer/common/shop/shop/info?ver=v2";

    //我的足迹列表
    public static final String GET_TRACE_LIST = "customer/crm/account/footprint/list";

    //删除足迹
    public static final String DELETE_TRACE = "customer/crm/account/footprint/delete";

    //我的评价
    public static final String GET_EVALUATE_DETAIL = "customer/crm/communion/comment/CustomerCommentList";

    //删除评价
    public static final String DELETE_EVALUATE = "customer/crm/communion/comment/deleteComment";

    //已开通区域列表
    public static final String GET_DREDGE_AREA_LIST = "customer/common/divpage/divpage/arealist";

    //提交加盟申请
    public static final String JOIN_APPLY = "customer/common/divpage/divpage/joinapply";

    //已开通区域地图信息
    public static final String GET_DREDGE_AREA_MAP = "customer/common/divpage/divpage/openinfo";

    //我的押金列表
    public static final String GET_MY_DEPOSIT_LIST="customer/crm/account/deposit/mydeposit";
    //押金明细列表
    public static final String GET_DEPOSIT_HISTORY_LIST="customer/crm/account/deposit/deposithistorylist";
    //押金明细详情
    public static final String GET_DEPOSIT_DETAIL="customer/crm/account/deposit/detail";

    //再来一单
    public static final String ORDER_AGAIN = "customer/common/page/food/anotherorder?ver=v2";

}