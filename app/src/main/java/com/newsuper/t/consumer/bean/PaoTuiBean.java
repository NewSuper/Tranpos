package com.newsuper.t.consumer.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/21 0021.
 */

public class PaoTuiBean extends BaseBean {

    /**
     * error_code : 0
     * data : {"delivery_num":"3","delivery_man":[{"longitude":"100.439396","latitude":"38.91846"},{"longitude":"105.220876","latitude":"37.915133"},{"longitude":"103.413782","latitude":"38.186771"}],"category":[{"id":"13","title":"帮买","icon":"http://img.lewaimai.com/upload_files/image/20180210/dcqM1dbqOPIDhsYYmS328VNVfzq5Oz8o.jpg"},{"id":"14","title":"帮买","icon":"http://img.lewaimai.com/upload_files/image/20180210/AwXbdEV27QCTnJfFOrlX2oNO9QEg4Dkw.jpg"},{"id":"15","title":"帮排队","icon":"http://img.lewaimai.com/upload_files/image/20180210/AwXbdEV27QCTnJfFOrlX2oNO9QEg4Dkw.jpg"}]}
     */

    public PaoTuiData data;

    public static class PaoTuiData {
        public String delivery_num;
        public ArrayList<DeliveryManBean> delivery_man;
        public ArrayList<CategoryBean> category;

    }
    public static class DeliveryManBean {
        /**
         * longitude : 100.439396
         * latitude : 38.91846
         */

        public String longitude;
        public String latitude;

    }

    public static class CategoryBean {
        /**
         * id : 13
         * title : 帮买
         * icon : http://img.lewaimai.com/upload_files/image/20180210/dcqM1dbqOPIDhsYYmS328VNVfzq5Oz8o.jpg
         */

        public String id;
        public String title;
        public String icon;
        public String onbusiness;
        public String notice;
        public String type;

    }
}
