package com.newsuper.t.consumer.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/26 0026.
 */

public class PublishDetailBean extends BaseBean {
    public PublishDetailData data;
    public static class PublishDetailData{

        /**
         * content : 周边规划人名公民共和国万岁周边规划人名公民共和国万岁周边规划人名公民共和国万岁周
         * publish_date : 05-22
         * first_category_name : 招聘求职
         * first_category : 1
         * second_category_name : 全职招聘
         * second_category : 2
         * info_id : 1
         * application_business : 1
         * area_id : 1
         * contact_name : hfhhfhfhfhfhfhf
         * contact_tel : 13017695257
         * toped_num : 2
         * status : 2
         * nickname : 李四
         * headimgurl :
         * is_collect : false
         * collect_id :
         * labs : [{"name":"包吃住","choose":false},{"name":"双休","choose":false},{"name":"工资高","choose":false},{"name":"福利好","choose":false},{"name":"五险一金","choose":false},{"name":"两班倒","choose":false},{"name":"更快，更高，更强","choose":true}]
         * images : ["/upload_files/image/20180528/uspYmhX1BUs6cVfj4hgD4VQGKArOFhDY.png","/upload_files/image/20180528/UR2SMBv8tK45jnaL3BCjkdbu7zGQr9G8.png","/upload_files/image/20180528/mae6miiDE54onrmfHWEzeUbBGGIAs4RN.png","/upload_files/image/20180528/eOYhV6eE52fCGn2f0bBqxPFN4mNK1r4D.png"]
         * publish : {"is_charge_to_publish":"2","top_fee":"6.00","publish_fee":"9.00"}
         * area_checked : 1
         * business : [{"id":"1","name":"餐饮"},{"id":"2","name":"互联网/IT"},{"id":"3","name":"金融"},{"id":"4","name":"旅游"},{"id":"5","name":"房地产"},{"id":"6","name":"建筑业"},{"id":"7","name":"服装"},{"id":"8","name":"农业"},{"id":"9","name":"制造业"},{"id":"10","name":"教育"},{"id":"11","name":"交通/运输"},{"id":"12","name":"体育"},{"id":"13","name":"物流/快递"},{"id":"14","name":"家具"},{"id":"15","name":"食品"},{"id":"16","name":"医疗"}]
         */

        public String content;
        public String publish_date;
        public String first_category_name;
        public String first_category;
        public String second_category_name;
        public String second_category;
        public String info_id;
        public String application_business;
        public String area_id;
        public String contact_name;
        public String contact_tel;
        public String toped_num;
        public String status;
        public String nickname;
        public String headimgurl;
        public boolean is_collect;
        public String collect_id;
        public PublishBean publish;
        public String area_checked;
        public ArrayList<LabsBean> labs;
        public ArrayList<String> images;
        public ArrayList<BusinessBean> business;
        public ArrayList<String> pay_type;
        public String weixinzhifu_type;
    }
    public static class PublishBean {
        /**
         * is_charge_to_publish : 2
         * top_fee : 6.00
         * publish_fee : 9.00
         */

        public String is_charge_to_publish;
        public String top_fee;
        public String publish_fee;
    }

    public static class LabsBean {
        /**
         * name : 包吃住
         * choose : false
         */

        public String name;
        public boolean choose;

    }

    public static class BusinessBean {
        /**
         * id : 1
         * name : 餐饮
         */

        public String id;
        public String name;

    }

}
