package com.newsuper.t.consumer.bean;

import java.util.ArrayList;

public class PersonCenterBean extends BaseBean {
    public  PersonCenterData data;
    public static class PersonCenterData{
        public String ele_num;
        public ArrayList<PersonCenterListData> list;
    }
    public static class PersonCenterListData{
        public String type;
        public String style;
        public String back_color;
        public String text_color;
        public String name;
        public String space;
        public String radioVal;

        public ArrayList<PersonCenterCustom> tubiao_daohangData;
        public PersonCenterDivider fuzhukongbaiData;
        public ArrayList<WTopBean.LunboImageData> lunbo_imageData;
    }

    public static class PersonCenterCustom extends WTopBean.BaseData {
        public String image;
        public String title;
        public String shop_id;
        public String area_id;
    }
    public static class PersonCenterDivider{
        public String height;
    }

}
