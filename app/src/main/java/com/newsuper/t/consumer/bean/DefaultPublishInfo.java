package com.newsuper.t.consumer.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/6/28 0028.
 */

public class DefaultPublishInfo {

    public DefaultPublishData data;
    public class DefaultPublishData{
        public TypeContent first_category;//一级分类信息
        public TypeContent second_category;//	二级分类信息
        public String hint;// 	内容提示语
        public ArrayList<TypeContent> business;//	行业分类信息
        public ArrayList<TypeContent> labs;//	标签
        public PublishInfo publish;//	发布信息设置参数
        public String area_checked;//	当前所在分区id（'0'为当前位置不在商户所设置的分区内），发布信息的时候要把这个值传递过来
        public ArrayList<String> pay_type;// 	支付类型
        public String weixinzhifu_type;//	string 	微信支付类型
    }

    public class TypeContent{
        public String id;
        public String name;
    }

    public class PublishInfo{
        public int is_charge_to_publish;//发布信息是否收费 1否 2是
        public float publish_fee;//发布费用（每次）
        public float top_fee;// 	置顶费用（每天）
    }

}
