package com.newsuper.t.consumer.bean;

/**
 * Created by Administrator on 2018/4/10 0010.
 */

public class CustomAddressBean extends BaseBean {
    /**
     * data : {"name":"刘先生","phone":"18688712411","address":"哈哈哈","address_name":"","lng":"0.000000","lat":"0.000000"}
     */

    public CustomAddressData data;
    
    public static class CustomAddressData {
        public CustomAddress address;
    }
    public static class CustomAddress {
        /**
         * name : 刘先生
         * phone : 18688712411
         * address : 哈哈哈
         * address_name :
         * lng : 0.000000
         * lat : 0.000000
         */

        public String name;
        public String phone;
        public String address;
        public String address_name;
        public String lng;
        public String lat;
    }
}
