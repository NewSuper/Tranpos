package com.newsuper.t.juejinbao.ui.movie.entity;

import java.util.List;

public class MovieNewFilterTagEntity {


    /**
     * code : 0
     * msg : success
     * data : [{"en":"area","cn":"地区","subType":["中国大陆","美国","中国香港","中国台湾","日本","韩国","英国","法国","德国","意大利","西班牙","印度","泰国","俄罗斯","伊朗","加拿大","澳大利亚","爱尔兰","瑞典","巴西","丹麦"]},{"en":"year","cn":"年份","subType":["2020","2019","2010年代","2000年代","90年代","80年代","70年代","60年代","更早"]},{"en":"form","cn":"形式","subType":["电影","电视剧","综艺","动漫","纪录片","短片"]},{"en":"category","cn":"类型","subType":["剧情","喜剧","动作","爱情","科幻","动画","悬疑","惊悚","恐怖","犯罪","同性","音乐"," 歌舞","传记","历史","战争","西部","奇幻","冒险","灾难","武侠","情色"]}]
     * time : 1578628932
     * vsn : 1.8.8.3
     */

    private int code;
    private String msg;
    private int time;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * en : area
         * cn : 地区
         * subType : ["中国大陆","美国","中国香港","中国台湾","日本","韩国","英国","法国","德国","意大利","西班牙","印度","泰国","俄罗斯","伊朗","加拿大","澳大利亚","爱尔兰","瑞典","巴西","丹麦"]
         */

        private String en;
        private String cn;
        private List<String> subType;

        public String getEn() {
            return en;
        }

        public void setEn(String en) {
            this.en = en;
        }

        public String getCn() {
            return cn;
        }

        public void setCn(String cn) {
            this.cn = cn;
        }

        public List<String> getSubType() {
            return subType;
        }

        public void setSubType(List<String> subType) {
            this.subType = subType;
        }
    }
}
