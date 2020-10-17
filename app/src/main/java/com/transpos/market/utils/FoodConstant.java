package com.transpos.market.utils;

public class FoodConstant {

	// 零售地址
	public static final String newServerUrlExtend = "http://api.jwsaas.com/api/extend";
	public static final String newServerUrl = "http://api.jwsaas.com/api";

	// 餐饮
	public static final String serverUrl = "http://fv2.jwsaas.com/openApi/api";
	public static final String serverUrlExtend = "http://fv2.jwsaas.com/openApi/open/api/extend";

	public static final String appSign = "estore";
	public static final String terminalType = "SELFPOS-ANDROID";

	public static final String DEFAULT_SYNC_USER = "sync";
	public static final String DEFAULT_TENANTID = "373001";

	//默认下载页大小
	public static final int DEFAULT_PAGESIZE = 2000;

	//openapi请求超时时间
	public static final int OPENAPI_TIMEOUT = 20;

	// 支付参数
	public static final String sbpay = "sbpay";
	public static final String fypay = "fypay";
	public static final String leshuapay = "leshuapay";
	public static final String alipay = "subalipay";
	public static final String wxpay = "subwxpay";
	public static String memberPay = "memberPay";

	/**
	 * 微信支付宝固定参数名称配置，由于自助没有其他，所以没有去下载
	 */
	public static final String WEIXINPAYNO = "05";
	public static final String WEIXINPAYNAME = "微信";

	public static final String ALIPAYNO = "04";
	public static final String ALIPAYNAME = "支付宝";


	//每页大小
	public static int DEFAULT_PAGE_SIZE = 2000;

	/**
	 * 订单来源
	 */

	public static final String scanType = "e1001";

	public static final String TempPathImg = "sdcard/" + "camera.jpg";

	public static String UPLOAD_STATUS_ACTION_RECEIVER = "upload.status.action.receiver";// 本地数据上传广播
	public static String SELECT_TIME_ACTION_RECEIVER = "select.time.action.receiver"; // 时间选择器广播
	public static String DELETE_PAY_ACTION_RECEIVER = "delete.pay.action.receiver";// 删除支付方式广播
	public static String BILL_PAY_ACTION_RECEIVER = "bill.pay.action.receiver";// 删除支付方式广播
	public static String BILL_DISCOUNT_ACTION_RECEIVER = "bill.discount.action.receiver";// 删除支付方式广播
	public static String HAND_OVER_ACTION_RECEIVER = "hand.over.action.receiver";// 删除支付方式广播
	public static String HAND_MEMBER_SWIPING_CARD = "hand_member_swiping_card"; // 会员刷卡
	public static String HAND_MEMBER_RECHARGE_CARD = "hand_member_RECHARGE_card"; // 会员充值
	public static String DISCOUNT_TYPE_MARK = "discount_type_mark"; // 优惠标示
	public static String UPDATE_MAIN_UI = "update_main_ui"; // 刷新主页面Ui
	public static String PRODUCT_SPEC = "product_spec"; // 选择商品的规格
	public static String TICKET_RECEIVER = "ticket_receiver"; // 订单相关的广播
	public static String TICKET_PAY = "ticket_pay"; // 订单支付方式选择广播
	public static String TICKET_RECEIVER_SHULIANG = "ticket_receiver_shuliang"; // 订单数量的广播
	public static String OTHER_COST = "other_cost"; // 其它收入
	public static String HAS_USE_MONEY = "has_use_money"; // 备用金
	public static String BLUETOOTH_MAC = "bluetooth_mac"; // MAC地址选择
	public static String UPDATE_TYPE = "update_type"; // 刷新分类

	public static String POSREGISTER = "pos.register";

	public static String STORE_BUSINESSPLAN = "store.businessplan";

	public static String STORE_BUSINESSPLAN_DETAIL = "store.businessplan.detail";

	public static String STORE_PAYTYPE = "store.paytype";

	// 门店资料
	public static String store_message = "store.message";
	// 接口名称
	public static String store_table_area = "store.table.area";
	// 餐桌类型
	public static String store_table_type = "store.table.type";
	// 餐桌资料
	public static String store_table = "store.table";
	// 门店品牌
	public static String store_brand = "store.brand";
	// 门店双屏图片
	public static String store_ad_picture = "store.advert.picture";
	// 门店双屏字幕
	public static String store_ad_caption = "store.advert.caption";
	// 门店小票图片
	public static String store_print_image = "store.print.image";
	// 商品单位信息
	public static String product_unit = "product.unit";
	// 商品类别
	public static String product_type = "mall.category";

	// 商品规格
	public static String product_spec = "product.spec.lists";
	// 商品图片
	public static String product_image = "product.image";
	// 做法分类
	public static String product_maketype = "product.maketype";
	// 商品做法信息
	public static String product_makedetail = "product.makedetail";
	// 套菜资料
	public static String product_suit = "product.suit";
	// 道菜明细
	public static String product_suit_detail = "product.suit.detail";
	// 员工登录
	public static String worker_login = "worker.login";
	// 门店可登陆员工
	public static String worker = "worker.list";
	// 员工角色列表
	public static String worker_posrole = "worker.posrole";
	// 员工模块权限
	public static String worker_posmodule = "worker.posmodule";
	// 读卡器信息
	public static String peripheral_card_reader_info = "peripheral.card.reader.info";
	// 打印机信息
	public static String peripheral_printer_info = "peripheral.printer.info";
	// 厨打方案信息
	public static String kitchen_plan_info = "kit.plan";
	// 商品厨打方案
	public static String kitchen_product_info = "kit.product";
	// 订单上传
	public static String upload_business_order = "upload.business.order";
	// 交班数据上传
	public static String upload_business_handover = "upload.business.handover";
	// 费用项目列表
	public static String store_feeitem_list = "store.feeitem.list";

	// 会员卡权限信息地址
	public static String serverCardUrlExtend = "http://mv2.jwsaas.com/openApi/open/api/extend";
	public static String serverCardUrl = "http://mv2.jwsaas.com/openApi/api";

	// 会员信息查询
	public static String member_info_query = "member.info.query";
	// 会员卡信息效验
	public static String card_valid = "card.valid";
	// 统一收单-订单创建
	public static String card_trade_create_voucherNo = "jw.trade.create.voucherNo";
	// 统一收单-卡支付明细
	public static String card_trade_create_card = "jw.trade.create.card";
	// 统一收单-支付
	public static String card_trade_pay = "jw.trade.pay";

	// 会员类型查询
	public static String member_type_list = "member.type.list";
	// 证件类型查询
	public static String cre_type_list = "cre.type.list";
	// 会员卡开户
	public static String card_open = "card.open";
	// 电子会员卡开户
	public static String card_elec_open = "card.elec.open";
	// 充值方案列表
	public static String card_recharge_scheme_list = "card.recharge.scheme.list";
	// 充值赠送明细
	public static String card_recharge_give_detail = "card.recharge.give.detail";
	// 卡充值
	public static String card_recharge = "card.recharge";

	/**
	 * 零售
	 */
	// 门店信息
	public static final String store_list = "store.list";
	// 会员设置
	public static final String member_setting = "member.setting";
	// 商品资料
	public static String product_list = "product.lists";
	// 商品资料
	public static String product_spec_lists = "product.spec.lists";
	// 商品品牌
	public static String product_brand_lists = "product.brand.lists";
	// 支付方式
	public static String pay_setting_list = "pay.setting.list";
	// 会员查询
	public static String member_query = "member.query";

	/**
	 * 商品条码标示
	 */
	public static int orderSource = 1; // 自助收银机收银台 = 0,	自助收银机 = 1,	扫码购 = 2,	网店 = 20,	拼团 = 21,	//美团外卖 = 30,	饿了么外卖 = 31,	云秤 = 40
	public static final String sourceSign = "Android";
	public static final String STEELYARD = "STEELYARD"; // 称重组名
	public static final String electronic_code_identification = "electronic_code_identification"; // 称重条码标示
	public static final String electronic_code_type = "electronic_code_type"; // 条码类型12金额码07称重码
	public static final String electronic_code_amount = "electronic_code_amount"; // 金额精度
	public static final String electronic_code_weight = "electronic_code_weight"; // 重量精度
	public static final String count_product_weight = "count_product_weight"; // 重量精度
}
