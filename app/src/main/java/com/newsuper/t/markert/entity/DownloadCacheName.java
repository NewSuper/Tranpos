package com.newsuper.t.markert.entity;

public enum DownloadCacheName {
    NONE(0),
    //服务端数据版本
    SERVER_DATA_VERSION(99),
    //员工
    WORKER(1),
    WORKER_ERROR(10),
    WORKER_EXCEPTION(100),
    //商品品牌
    PRODUCT_BRAND(2),
    PRODUCT_BRAND_ERROR(20),
    PRODUCT_BRAND_EXCEPTION(200),

    //商品品类
    PRODUCT_CATEGORY(3),
    PRODUCT_CATEGORY_ERROR(30),
    PRODUCT_CATEGORY_EXCEPTION(300),

    //商品单位
    PRODUCT_UNIT(4),
    PRODUCT_UNIT_ERROR(40),
    PRODUCT_UNIT_EXCEPTION(400),

    //商品资料
    PRODUCT(5),
    PRODUCT_ERROR(50),
    PRODUCT_EXCEPTION(500),

    //商品附加码
    PRODUCT_CODE(6),
    PRODUCT_CODE_ERROR(60),
    PRODUCT_CODE_EXCEPTION(600),

    //商品关联信息表
    PRODUCT_CONTACT(7),
    PRODUCT_CONTACT_ERROR(70),
    PRODUCT_CONTACT_EXCEPTION(700),

    //商品图片表
    PRODUCT_IMAGE(8),
    PRODUCT_IMAGE_ERROR(80),
    PRODUCT_IMAGE_EXCEPTION(800),

    //商品规格
    PRODUCT_SPEC(9),
    PRODUCT_SPEC_ERROR(90),
    PRODUCT_SPEC_EXCEPTION(900),

    //门店商品关联
    STORE_PRODUCT(10),
    STORE_PRODUCT_ERROR(100),
    STORE_PRODUCT_EXCEPTION(1000),


    //pos角色
    POS_ROLE(11),
    POS_ROLE_ERROR(110),
    POS_ROLE_EXCEPTION(1100),
    //员工POS模块权限
    WORKER_POSMODULE(12),
    WORKER_POSMODULE_ERROR(120),
    WORKER_POSMODULE_EXCEPTION(1200),

    //pos员工角色关联
    WORKER_ROLE(13),
    WORKER_ROLE_ERROR(130),
    WORKER_ROLE_EXCEPTION(1300),

    //供应商
    SUPPLIER(14),
    SUPPLIER_ERROR(140),
    SUPPLIER_EXCEPTION(1400),

    //付款方式
    PAYMODE(15),
    PAYMODE_ERROR(150),
    PAYMODE_EXCEPTION(1500),

    //做法信息
    MAKE_INFO(16),
    MAKE_INFO_ERROR(160),
    MAKE_INFO_EXCEPTION(1600),

    //做法分类
    MAKE_CATEGORY(17),
    MAKE_CATEGORY_ERROR(170),
    MAKE_CATEGORY_EXCEPTION(1700),

    //门店信息
    STORE_INFO(18),
    STORE_INFO_ERROR(180),
    STORE_INFO_EXCEPTION(1800),

    //会员设置
    MEMBER_SETTING(19),
    MEMBER_SETTING_ERROR(190),
    MEMBER_SETTING_EXCEPTION(1900),

    //会员等级
    MEMBER_LEVEL(21),
    MEMBER_LEVEL_ERROR(210),
    MEMBER_LEVEL_EXCEPTION(2100),

    //会员积分规则
    MEMBER_POINT_RULE(24),
    MEMBER_POINT_RULE_ERROR(240),
    MEMBER_POINT_RULE_EXCEPTION(2400),

    //类别积分规则
    MEMBER_POINT_RULE_CATEGORY(25),
    MEMBER_POINT_RULE_CATEGORY_ERROR(250),
    MEMBER_POINT_RULE_CATEGORY_EXCEPTION(2500),

    //品牌积分规则
    MEMBER_POINT_RULE_BRAND(26),
    MEMBER_POINT_RULE_BRAND_ERROR(260),
    MEMBER_POINT_RULE_BRAND_EXCEPTION(2600),

    //会员等级分类折扣
    MEMBER_LEVEL_CATEGORY_DISCOUNT(27),
    MEMBER_LEVEL_CATEGORY_DISCOUNT_ERROR(270),
    MEMBER_LEVEL_CATEGORY_DISCOUNT_EXCEPTION(2700),

    //移动支付信息
    PAY_SETTING(38),
    PAY_SETTING_ERROR(380),
    PAY_SETTING_EXCEPTION(3800),

    //后台销售设置
    LINE_SALES_SETTING(40),
    LINE_SALES_SETTING_ERROR(400),
    LINE_SALES_SETTING_EXCEPTION(4000),

    //平台信息
    PLATEFORM(43),
    PLATEFORM_ERROR(430),
    PLATEFORM_EXCEPTION(4300),
    //快捷键
    SHORTCUTS(42),
    SHORTCUTS_ERROR(420),
    SHORTCUTS_EXCEPTION(4200),

    //PLUS商品
    PLUS_PRODUCT(47),
    PLUS_PRODUCT_ERROR(470),
    PLUS_PRODUCT_EXCEPTION(4700),
    //员工
    WORKER_DATA(48),
    WORKER_DATA_ERROR(480),
    WORKER__DATA_EXCEPTION(4800);

    private int sign;

    DownloadCacheName(int sign) {
        this.sign = sign;
    }

    public int getSign() {
        return this.sign;
    }

}