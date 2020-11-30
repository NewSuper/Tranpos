package com.newsuper.t.consumer.bean;

import java.util.ArrayList;


public class LocationSearchBean {
    public ArrayList<SearchBean> search;
    public static class SearchBean{
        public String lat;
        public String lng;
        public String address;
    }
}
