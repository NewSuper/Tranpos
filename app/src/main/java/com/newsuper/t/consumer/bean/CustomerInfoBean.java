package com.newsuper.t.consumer.bean;


public class CustomerInfoBean extends BaseBean {
    public InfoData data;

    public class InfoData {
        public String customer_id;// id
        public String headimgurl;//	string	头像
        public String nickname;//string	昵称
        public String sex;//string	性别
        public String address;//	string	地址
        public String couponNum;//	int	优惠券数量
        public String point;//	int	积分
        public String balance;//	int	余额
        public String phone;
        public String is_member;
        public String is_freeze;

        public String grade_id;//	该顾客的会员等级id
        public String grade_status;//该顾客的会员等级状态 1可用 2禁用
    }
}
