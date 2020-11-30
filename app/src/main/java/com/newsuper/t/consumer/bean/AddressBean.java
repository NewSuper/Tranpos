package com.newsuper.t.consumer.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 获取我的地址数据返回
 */

public class AddressBean extends BaseBean {
    public AddressData data;

    public static class AddressData implements Serializable{
        public ArrayList<AddressList> addresslist;
    }
    public static class AddressList implements Serializable{
        public String id;//	string	地址id
        public String name;//	string	姓名
        public String phone;//string	电话
        public String address;//	string	地址
        public String lng;//string	定位经度
        public String lat;//string	定位纬度
        public String address_name;//	string	定位位置
        public boolean isactive;//	boolean	是否是默认地址，true:是，false:不是
        public String isinarea;//是否在店铺服务范围，1:是，0:不是
    }
}
