package com.newsuper.t.consumer.bean;


public class APPInfoBean extends BaseBean{

    public APPData data;
    public class APPData{
        public String app_type;
        public String divpage_id;
        public String customer_app_id;
        public String due_date;
        public String consumer_hotline;
        public String app_index_style;//1:默认2：微页面组合
        public String divpagegroup_id;
        public String brand_icon;
        public String is_show_jszc;//是否显示乐外卖技术支持 0：否 1：是
    }

}
