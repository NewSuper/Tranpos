package com.newsuper.t.juejinbao.ui.home.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;

public class TodayHotEntity extends Entity{

    /**
     * code : 0
     * msg : success
     * data : [{"title":"三河警方打掉卖淫窝点","hot_word":"三河打掉卖淫窝点","encode_hot_word":"4d2bdd5911d0b8478430965dd667cc4e","hot_value":9616682,"label_image":""},{"title":"巴铁别怕 中国物资来了","hot_word":"巴基斯坦 中国物资","encode_hot_word":"e4e20bfbc2ceb348c87423225294a0b5","hot_value":3201115,"label_image":""},{"title":"桑德斯提议新冠疫苗免费","hot_word":"桑德斯提议新冠疫苗免费","encode_hot_word":"aad2a807f80bacf3a1ebaa19b94f9a2d","hot_value":2896489,"label_image":""}]
     * time : 1585106490
     * vsn : 1.8.8.2
     */

    private int code;
    private String msg;
    private String time;
    private String vsn;
    private List<DataBean> data;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVsn() {
        return vsn;
    }

    public void setVsn(String vsn) {
        this.vsn = vsn;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean extends Entity {
        private String title;//标题
        private String hot_word;//热词
        private String encode_hot_word;//热词md5值
        private String hot_value;//热度值
        private String label_image;//标签图
        private String hot_time;//

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getHot_word() {
            return hot_word;
        }

        public void setHot_word(String hot_word) {
            this.hot_word = hot_word;
        }

        public String getEncode_hot_word() {
            return encode_hot_word;
        }

        public void setEncode_hot_word(String encode_hot_word) {
            this.encode_hot_word = encode_hot_word;
        }

        public String getHot_value() {
            return hot_value;
        }

        public void setHot_value(String hot_value) {
            this.hot_value = hot_value;
        }

        public String getLabel_image() {
            return label_image;
        }

        public void setLabel_image(String label_image) {
            this.label_image = label_image;
        }

        public String getHot_time() {
            return hot_time;
        }

        public void setHot_time(String hot_time) {
            this.hot_time = hot_time;
        }
    }
}
