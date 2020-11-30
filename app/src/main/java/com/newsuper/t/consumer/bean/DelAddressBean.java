package com.newsuper.t.consumer.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 删除我的地址数据返回
 */

public class DelAddressBean extends BaseBean {
    public DelAddressData data;

    public class DelAddressData {
        public String address_id;
    }


}
