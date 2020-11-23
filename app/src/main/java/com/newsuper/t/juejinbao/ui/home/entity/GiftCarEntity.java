package com.newsuper.t.juejinbao.ui.home.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;
public class GiftCarEntity extends Entity {

    /**
     * code : 0
     * msg : success
     * data : {"id":28393,"brand_logo":"http://jjlmobile.oss-cn-shenzhen.aliyuncs.com/goods_car/gid_28393/brand_logo/c6bd8d030256bd1ac6494f2009d7e4c7.png","shortname":"保时捷Panamera","fullname":"2017款 Panamera 3.0T","images":"http://jjlmobile.oss-cn-shenzhen.aliyuncs.com/goods_car/gid_28393/pic_list_h/b6f08f25dfb6b4f2593b0575923f8850.jpg"}
     * time : 1563269903
     * vsn : 1.8.4
     */

    private int code;
    private String msg;
    private DataBean data;
    private int time;
    private String vsn;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getVsn() {
        return vsn;
    }

    public void setVsn(String vsn) {
        this.vsn = vsn;
    }

    public static class DataBean {
        /**
         * id : 28393
         * brand_logo : http://jjlmobile.oss-cn-shenzhen.aliyuncs.com/goods_car/gid_28393/brand_logo/c6bd8d030256bd1ac6494f2009d7e4c7.png
         * shortname : 保时捷Panamera
         * fullname : 2017款 Panamera 3.0T
         * images : http://jjlmobile.oss-cn-shenzhen.aliyuncs.com/goods_car/gid_28393/pic_list_h/b6f08f25dfb6b4f2593b0575923f8850.jpg
         */

        private int id;
        private String brand_logo;
        private String shortname;
        private String fullname;
        private String images;
        /**
         * vcoin : 5
         * vcoin_to_rmb : 10
         * user_count : 229484
         */

        private double vcoin;
        private double vcoin_to_rmb;
        private int user_count;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBrand_logo() {
            return brand_logo;
        }

        public void setBrand_logo(String brand_logo) {
            this.brand_logo = brand_logo;
        }

        public String getShortname() {
            return shortname;
        }

        public void setShortname(String shortname) {
            this.shortname = shortname;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public double getVcoin() {
            return vcoin;
        }

        public void setVcoin(double vcoin) {
            this.vcoin = vcoin;
        }

        public double getVcoin_to_rmb() {
            return vcoin_to_rmb;
        }

        public void setVcoin_to_rmb(double vcoin_to_rmb) {
            this.vcoin_to_rmb = vcoin_to_rmb;
        }

        public int getUser_count() {
            return user_count;
        }

        public void setUser_count(int user_count) {
            this.user_count = user_count;
        }
    }
}
