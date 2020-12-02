package com.newsuper.t.consumer.bean;

import java.util.ArrayList;


public class CategoryBean extends BaseBean{
    public CategoryData data;
    public static class CategoryData{
        public String first_category_name;
        public String sign;
        public ArrayList<CategoryList> data;
    }
    public static class CategoryList{
        public String id;
        public String name;
        public String image;
    }
}
