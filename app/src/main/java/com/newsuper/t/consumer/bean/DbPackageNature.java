package com.newsuper.t.consumer.bean;

import java.util.ArrayList;

public class DbPackageNature {
    public String name;
    public ArrayList<DbPackageNatureValue> value;


    public class DbPackageNatureValue{
        public String id;
        public String name;
        public boolean is_selected;//判断是否选中
    }
}
