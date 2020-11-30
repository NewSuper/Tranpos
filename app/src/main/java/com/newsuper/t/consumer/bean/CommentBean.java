package com.newsuper.t.consumer.bean;

import java.util.ArrayList;


public class CommentBean extends BaseBean{
    public CommentData data;
    public class CommentData{
        public String page;
        public ArrayList<Comment> comments;
    }
    public class Comment{
        public String lewaimai_customer_id;
        public String grade;
        public String content;
        public String time;
        public String creplycontent;
        public ArrayList<String> tag;
        public ArrayList<String> imgurl;
    }
}
