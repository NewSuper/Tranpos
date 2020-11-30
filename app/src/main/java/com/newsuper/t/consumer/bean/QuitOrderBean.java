package com.newsuper.t.consumer.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/8 0008.
 */

public class QuitOrderBean extends BaseBean{
    public ArrayList<QuitOrderData> data;
    public class QuitOrderData{
        public String order_id;
    }

}
