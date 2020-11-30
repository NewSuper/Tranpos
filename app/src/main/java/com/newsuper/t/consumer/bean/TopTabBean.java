package com.newsuper.t.consumer.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/10 0010.
 */

public class TopTabBean extends BaseBean {
    public TopTabData data;

    /**
     * list : [{"name":"首页","bg_img":"https://assets-dev2.lewaimai.com/www/img/divpage/unchecked_home.png","hh_img":"https://assets-dev2.lewaimai.com/www/img/divpage/home.png","text_color":"#ccc","url":"https://wap-dev2.lewaimai.com/h5/lwm/fenqu/index?admin_id=1","url_from":"areaindex","url_from_id":"0","rowStyle":"left:0%; width:33.333333333333336%","align":2},{"name":"订单","bg_img":"https://assets-dev2.lewaimai.com/www/img/divpage/unchecked_order.png","hh_img":"https://assets-dev2.lewaimai.com/www/img/divpage/order.png","url":"","align":2,"text_color":"#ccc","rowStyle":"left:33.333333333333336%; width:33.333333333333336%","url_from":"","url_from_id":"0"},{"name":"我的","bg_img":"https://assets-dev2.lewaimai.com/www/img/divpage/unchecked_personal.png","hh_img":"https://assets-dev2.lewaimai.com/www/img/divpage/personal.png","url":"https://wap-dev2.lewaimai.com/h5/lwm/waimai/mine_index?admin_id=1","text_color":"#ccc","align":2,"rowStyle":"left:66.66666666666667%; width:33.333333333333336%","url_from":"usercenter","url_from_id":"0"}]
     * bg_color : #ffffff
     * show_type : 2
     * platform : 2
     */
    public static class TopTabData {
        public String bg_color;
        public String show_type;
        public String platform;
        public ArrayList<TabData> list;
    }
    public static class TabData {
        /**
         * name : 首页
         * bg_img : https://assets-dev2.lewaimai.com/www/img/divpage/unchecked_home.png
         * hh_img : https://assets-dev2.lewaimai.com/www/img/divpage/home.png
         * text_color : #ccc
         * url : https://wap-dev2.lewaimai.com/h5/lwm/fenqu/index?admin_id=1
         * url_from : areaindex
         * url_from_id : 0
         * rowStyle : left:0%; width:33.333333333333336%
         * align : 2
         */

        public String name;
        public String bg_img;
        public String hh_img;
        public String text_color;
        public String url;
        public String url_from;
        public String url_from_id;
        public String rowStyle;
        public int align;
    }
}
