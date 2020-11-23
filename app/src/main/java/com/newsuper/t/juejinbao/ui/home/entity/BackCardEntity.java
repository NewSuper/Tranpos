package com.newsuper.t.juejinbao.ui.home.entity;


import com.newsuper.t.juejinbao.bean.Entity;

public class BackCardEntity extends Entity {

    /**
     * code : 0
     * msg : success
     * data : {"id":1003492,"level":21,"price":"276800.00","brand_name":"东风本田","shortname":"本田CR-V","fullname":"2019款锐混动2.0L两驱净致版","brand_logo":null,"thumb":"http://car2.autoimg.cn/cardfs/product/g1/M06/5D/E9/640x480_autohomecar__ChsEmVwt2xSAOKiCAAuFCSpnV1s998.jpg?x-oss-process=image/resize,h_50","total_count":229483,"start_time":"2019/04/15 00:00:00"}
     * time : 1563268537
     * vsn : 1.8.2
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
         * id : 1003492
         * level : 21
         * price : 276800.00
         * brand_name : 东风本田
         * shortname : 本田CR-V
         * fullname : 2019款锐混动2.0L两驱净致版
         * brand_logo : null
         * thumb : http://car2.autoimg.cn/cardfs/product/g1/M06/5D/E9/640x480_autohomecar__ChsEmVwt2xSAOKiCAAuFCSpnV1s998.jpg?x-oss-process=image/resize,h_50
         * total_count : 229483
         * start_time : 2019/04/15 00:00:00
         */

        private int id;
        private int level;
        private String price;
        private String brand_name;
        private String shortname;
        private String fullname;
        private Object brand_logo;
        private String thumb;
        private int total_count;
        private String start_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
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

        public Object getBrand_logo() {
            return brand_logo;
        }

        public void setBrand_logo(Object brand_logo) {
            this.brand_logo = brand_logo;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public int getTotal_count() {
            return total_count;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }
    }
}
