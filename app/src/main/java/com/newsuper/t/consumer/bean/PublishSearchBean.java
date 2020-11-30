package com.newsuper.t.consumer.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/20 0020.
 */

public class PublishSearchBean extends BaseBean{
    public ArrayList<PublishSearchData> data;
    public static class PublishSearchData{
        /**
         * id : 20
         * category : 招聘求职-全职招聘
         * publish_date : 刚刚
         * application_business : 互联网/IT
         * labs : ["好品牌","值得信赖"]
         * content : test20
         */

        public String id;
        public String category;
        public String publish_date;
        public String application_business;
        public String content;
        public ArrayList<String> labs;
    }

   
    
}
