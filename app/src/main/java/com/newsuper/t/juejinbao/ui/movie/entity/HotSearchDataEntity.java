package com.newsuper.t.juejinbao.ui.movie.entity;

import com.newsuper.t.juejinbao.ui.movie.adapter.EasyAdapter;

import java.io.Serializable;
import java.util.List;

public class HotSearchDataEntity implements Serializable {


    /**
     * code : 0
     * msg : success
     * data : [{"img_url":"http://mu1.sinaimg.cn/frame.180x240/weiyinyue.music.sina.com.cn/movie_poster/181726_vertical.jpg","chinese_name":"地球最后的夜晚","directors":"毕赣","actors":"汤唯 / 黄觉 / 陈永忠 / 李鸿其 / 张艾嘉","score":"98.0"},{"img_url":"https://mu1.sinaimg.cn/frame.180x240/weiyinyue.music.sina.com.cn/movie_poster/184690_vertical.jpg","chinese_name":"爱宠大机密2","directors":"克里斯·雷纳德","actors":"凯文·哈特 / 哈里森·福特 / Tiffany Haddish","score":"96.7"},{"img_url":"http://mu1.sinaimg.cn/frame.180x240/weiyinyue.music.sina.com.cn/movie_poster/185476_vertical.jpg","chinese_name":"蜘蛛侠：平行宇宙","directors":"Robert Persichetti Jr. / 彼得·拉姆齐 / Rodney Rothman","actors":"杰克·约翰逊 / 尼古拉斯·凯奇 / 列维·施瑞博尔","score":"96.1"},{"img_url":"https://mu1.sinaimg.cn/frame.180x240/weiyinyue.music.sina.com.cn/movie_poster/99933_vertical.jpg","chinese_name":"玩具总动员4","directors":"乔什·库雷 ","actors":"汤姆·汉克斯 / 蒂姆·艾伦 / 琼·库萨克 / 唐·里克斯 / 迈克尔·基顿 / 邦尼·亨特","score":"92.1"},{"img_url":"http://mu1.sinaimg.cn/frame.180x240/weiyinyue.music.sina.com.cn/movie_poster/179340_vertical.jpg","chinese_name":"海王","directors":"温子仁","actors":"杰森·莫玛 / 艾梅柏·希尔德 / 威廉·达福 / 妮可·基德曼 / 帕特里克·威尔森 / 杜夫·龙格尔 / 特穆拉·莫里森","score":"95.7"},{"img_url":"https://mu1.sinaimg.cn/frame.180x240/weiyinyue.music.sina.com.cn/movie_poster/42168_vertical.jpg","chinese_name":"千与千寻","directors":"宫崎骏","actors":"柊瑠美 / 入野自由 / 夏木真理 / 菅原文太 / 内藤刚志","score":"91.4"},{"img_url":"http://mu1.sinaimg.cn/frame.180x240/weiyinyue.music.sina.com.cn/movie_poster/185537_vertical.jpg","chinese_name":"来电狂响","directors":"于淼","actors":"佟大为 / 马丽 / 霍思燕 / 乔杉 / 田雨 / 代乐乐 / 奚梦瑶","score":"94.3"},{"img_url":"https://mu1.sinaimg.cn/frame.180x240/weiyinyue.music.sina.com.cn/movie_poster/184272_vertical.jpg","chinese_name":"扫毒2：天地对决","directors":"邱礼涛","actors":"古天乐 / 刘德华 / 应采儿 / 苗侨伟 / 林嘉欣","score":"88.2"},{"img_url":"http://mu1.sinaimg.cn/frame.180x240/weiyinyue.music.sina.com.cn/movie_poster/185455_vertical.jpg","chinese_name":"印度暴徒","directors":"Vijay Krishna Acharya","actors":"阿米尔·汗 / 卡特莉娜·卡芙 / 阿米特巴·巴赫卡安","score":"92.8"},{"img_url":"https://mu1.sinaimg.cn/frame.180x240/weiyinyue.music.sina.com.cn/movie_poster/186049_vertical.jpg","chinese_name":"绝杀慕尼黑","directors":"安东·梅格迪契夫","actors":"弗拉基米尔·马什科夫","score":"87.6"}]
     * time : 1563161813
     * vsn : 1.8.8
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

    public static class DataBean extends EasyAdapter.TypeBean {
        /**
         * img_url : http://mu1.sinaimg.cn/frame.180x240/weiyinyue.music.sina.com.cn/movie_poster/181726_vertical.jpg
         * chinese_name : 地球最后的夜晚
         * directors : 毕赣
         * actors : 汤唯 / 黄觉 / 陈永忠 / 李鸿其 / 张艾嘉
         * score : 98.0
         */

        private String img_url;
        private String chinese_name;
        private String directors;
        private String actors;
        private String score;

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getChinese_name() {
            return chinese_name;
        }

        public void setChinese_name(String chinese_name) {
            this.chinese_name = chinese_name;
        }

        public String getDirectors() {
            return directors;
        }

        public void setDirectors(String directors) {
            this.directors = directors;
        }

        public String getActors() {
            return actors;
        }

        public void setActors(String actors) {
            this.actors = actors;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }
    }

}
