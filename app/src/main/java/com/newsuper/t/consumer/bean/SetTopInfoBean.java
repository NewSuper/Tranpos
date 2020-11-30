package com.newsuper.t.consumer.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/7/19 0019.
 */

public class SetTopInfoBean extends BaseBean {
    public SetTopInfoData data;
    public static class SetTopInfoData{
        public String type;
        public String top_fee;
        public String weixinzhifu_type;
        public ArrayList<String> pay_type;
    }
}
