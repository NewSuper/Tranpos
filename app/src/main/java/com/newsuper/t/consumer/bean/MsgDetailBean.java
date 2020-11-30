package com.newsuper.t.consumer.bean;

/**
 * Created by Administrator on 2018/7/23 0023.
 */

public class MsgDetailBean extends BaseBean {
    public MsgDetailData data;
    public static class MsgDetailData {
        public String push_title;
        public String push_content;
        public String date;
    }
}
