package com.newsuper.t.consumer.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/29 0029.
 */

public class PublishCollectBean extends BaseBean {

    public ArrayList<PublishCollectData> data;
    public static class PublishCollectData{
        /**
         * info_id : 2
         * first_category_name : 招聘求职
         * second_category_name : 兼职招聘
         * content : test2
         * images : ["upload_files/image/20180517/catDp5GgFZuD8QvqFh7Mf2EKh81TLwih.png","upload_files/image/20180517/catDp5GgFZuD8QvqFh7Mf2EKh81TLwih.png","upload_files/image/20180517/catDp5GgFZuD8QvqFh7Mf2EKh81TLwih.png"]
         * nickname : 李四
         * collect_time : 39分钟前
         */

        public String info_id;
        public String first_category_name;
        public String second_category_name;
        public String content;
        public String nickname;
        public String collect_time;
        public ArrayList<String> images;
        public String headimgurl;
        public boolean isShowAll;
    }
}
