package com.newsuper.t.juejinbao.bean;

import com.newsuper.t.juejinbao.base.PagerCons;

import java.util.List;

import io.paperdb.Paper;


public class BaseConfigEntity extends Entity {


    /**
     * code : 0
     * msg : success
     * data : {"share_movie_count":2,"advertiser_type":1,"is_open_app_protect":0,"picture":{"reward_num":4}}
     * time : 1574217991
     * vsn : 1.8.8.2
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


    /**
     * 获取激励视频控制
     * @return
     */
    public static int getStimulateSwitch() {
        BaseConfigEntity.DataBean configBean = Paper.book().read(PagerCons.KEY_JJB_CONFIG);
        if (configBean == null) {
            return 0;
        } else {
            return configBean.getStimulate_switch();
        }
    }


    public static class DataBean  extends Entity{
        /**
         * share_movie_count : 2
         * advertiser_type : 1
         * is_open_app_protect : 0
         * picture : {"reward_num":4}
         */

        private int share_movie_count; // 是否分享之后才可观看电影
        private int advertiser_type;  //1：穿山甲；2：广点通 3搜狗
        private int is_open_app_protect; // 是否开启app防护（暂时没用）
        private PictureBean picture;
        private MusicBean music;
        private int stimulate_switch; // 激励视频控制：0 代表穿山甲   1 广点通   2 穿山甲+广点通 自动切换
        private List<String> ignore_vip_parse;
        private int is_open_p2p;
        private List<MovieParseDelayBean> movie_parse_delay;

        public int getIs_open_p2p() {
            return is_open_p2p;
        }

        public void setIs_open_p2p(int is_open_p2p) {
            this.is_open_p2p = is_open_p2p;
        }

        public MusicBean getMusic() {
            return music;
        }

        public void setMusic(MusicBean music) {
            this.music = music;
        }

        public int getStimulate_switch() {
            return stimulate_switch;
        }

        public void setStimulate_switch(int stimulate_switch) {
            this.stimulate_switch = stimulate_switch;
        }

        public int getShare_movie_count() {
            return share_movie_count;
        }

        public void setShare_movie_count(int share_movie_count) {
            this.share_movie_count = share_movie_count;
        }

        public int getAdvertiser_type() {
            return advertiser_type;
        }

        public void setAdvertiser_type(int advertiser_type) {
            this.advertiser_type = advertiser_type;
        }

        public int getIs_open_app_protect() {
            return is_open_app_protect;
        }

        public void setIs_open_app_protect(int is_open_app_protect) {
            this.is_open_app_protect = is_open_app_protect;
        }

        public PictureBean getPicture() {
            return picture;
        }

        public void setPicture(PictureBean picture) {
            this.picture = picture;
        }

        public List<String> getIgnore_vip_parse() {
            return ignore_vip_parse;
        }

        public void setIgnore_vip_parse(List<String> ignore_vip_parse) {
            this.ignore_vip_parse = ignore_vip_parse;
        }

        public List<MovieParseDelayBean> getMovie_parse_delay() {
            return movie_parse_delay;
        }

        public void setMovie_parse_delay(List<MovieParseDelayBean> movie_parse_delay) {
            this.movie_parse_delay = movie_parse_delay;
        }

        public static class PictureBean  extends Entity{
            /**
             * reward_num : 4
             */

            private int reward_num;

            public int getReward_num() {
                return reward_num;
            }

            public void setReward_num(int reward_num) {
                this.reward_num = reward_num;
            }
        }

        public static class MusicBean extends Entity{
            private int is_open_crawl;

            public int getIs_open_crawl() {
                return is_open_crawl;
            }

            public void setIs_open_crawl(int is_open_crawl) {
                this.is_open_crawl = is_open_crawl;
            }
        }

        public static class MovieParseDelayBean {
            /**
             * host : m.kankanwu.com
             * delay : 5000
             */

            private String host;
            private int delay;

            public String getHost() {
                return host;
            }

            public void setHost(String host) {
                this.host = host;
            }

            public int getDelay() {
                return delay;
            }

            public void setDelay(int delay) {
                this.delay = delay;
            }
        }
    }
}
