package com.newsuper.t.juejinbao.ui.home.entity;
import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;
public class HomeListEntity extends Entity {


    /**
     * code : 0
     * msg : success
     * data : [{"id":5573978,"title":"看掘金宝资讯影视，赚车赚房可能么？","author":"掘金宝资讯","publish_time":1552127425,"img_url":["http://p3.pstatp.com/list/190x124/pgc-image/656e8cf39a564dc3a85ac0d352bf5d81"],"author_logo":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/aboutus_logo.png","mark":"置顶","showtype":1,"read_count":398784,"comment_count":0,"digg_count":0,"type":"article","description":"","other":{}},{"id":5573979,"title":"掘金宝与其它资讯类APP的本质区别","author":"掘金宝资讯","publish_time":1561551017,"img_url":["http://p3.pstatp.com/list/190x124/pgc-image/65f82539921f4c53b532a3f611c618a5"],"author_logo":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/aboutus_logo.png","mark":"置顶","showtype":1,"read_count":97372,"comment_count":0,"digg_count":0,"type":"article","description":"","other":{}},{"id":2450,"title":"爸爸坐牢，妈妈改嫁，5岁女孩顽强求生独自照顾奶奶和92岁曾祖母","author":"乡土山西","publish_time":1562727464,"img_url":["http://p9.pstatp.com/origin/pgc-image/4fabedaaa9b34a068fa6d3bc2c558506","http://p1.pstatp.com/origin/pgc-image/43948332c08c435ea7fe23c28c42750b","http://p3.pstatp.com/origin/pgc-image/51735f1115a74420933e96765b58fb96"],"mark":"热议","author_logo":"//p9.pstatp.com/large/6eec000174c5a7ec62e6","showtype":7,"read_count":583680,"comment_count":11112,"digg_count":0,"type":"picture","description":"","other":{}},{"id":7142,"title":"第60集 提前清理干净烟囱，只为了迎接更加高效炉子的到来","author":"我的自力更生","publish_time":1562768021,"img_url":["http://sf1-ttcdn-tos.pstatp.com/obj/tos-cn-p-0000/23e22b9146734cd9af76e6c5b600d8a3","http://v6-default.bytecdn.cn/77cc478fbfacb1ec85bf7136d5eb35b9/5d26036a/video/m/22015f4427865dd448e8786242b6d0e62e01162d1a9f0000277034bf0719/?rc=Mzt2OnVwdnRlbjMzZTczM0ApQHRAbzw6NTc7MzUzMzc0NTQzNDVvQGg1dilAZzN3KUBmM3UpZHNyZ3lrdXJneXJseHdmNTNAbTBrcGIwMi5mXy0tMi0wc3MtbyNvIzU2NC4yNS0uLTAvLTQ2LTojbyM6YS1vIzpgLXAjOmB2aVxiZitgXmJmK15xbDojLi9e"],"mark":"热文","showtype":8,"author_logo":"http://p9.pstatp.com/large/5acc00095c00ffbb4429","read_count":9424,"comment_count":32,"digg_count":0,"type":"video","description":"我也偏爱旧炉子的蓝色外观，但效率和寿命更重要，Jotul制造了耐用的炉子。比佛蒙特州的铸件更容易操作。等不及下一个视频了","other":{}},{"id":2457,"title":"独臂男被妻抛弃，隐居深山\u201c淘金\u201d，养1稀罕宠物，营收3000万","author":"黄泥巴光影","publish_time":1562770927,"img_url":["http://p1.pstatp.com/origin/pgc-image/f8b40c8cc6cf4c9a86961d0210719d1f","http://p3.pstatp.com/origin/pgc-image/4cbd483e04ee4c1b921732aa2835f9e3","http://p1.pstatp.com/origin/pgc-image/82c5348f093a43b39202ed1d11a662cb"],"mark":"热门","author_logo":"//p5a.pstatp.com/large/6eeb0002d759e2597951","showtype":7,"read_count":363815,"comment_count":88,"digg_count":0,"type":"picture","description":"","other":{}},{"id":7148,"title":"黑科技智能家居大赏！自己会打包的垃圾桶，谁不想要！","author":"网不红萌叔Joey","publish_time":1562768068,"img_url":["http://sf1-ttcdn-tos.pstatp.com/obj/tos-cn-p-0000/2049e5436cec468884db47253fdbd6d9","http://v6-default.bytecdn.cn/c4d08933c6d1af17db7925b88ae41437/5d260219/video/m/220f33b2cbde30741fcb7569c44ea7611481162d2009000045504a1a0b56/?rc=amxxO3B0PHh1bjMzOzczM0ApQHRAbzw2NDQ1MzUzMzU0NTQzNDVvQGg2dilAZzN3KUBmM3UpZHNyZ3lrdXJneXJseHdmNTNAMmlwNDJlMW5mXy0tXi0vc3MtbyNvIy81MC8wLS0uNDEvLTQ2LTojbyM6YS1vIzpgLXAjOmB2aVxiZitgXmJmK15xbDojLi9e"],"mark":"热文","showtype":8,"author_logo":"http://p1.pstatp.com/large/ff3d0000185c92fe5382","read_count":12984,"comment_count":50,"digg_count":0,"type":"video","description":"解决宿舍矛盾的智能开关，自己会打包的垃圾桶，还有\u201c妲己\u201d陪你打游戏，做你的专属军师，哪个男孩子不想要！","other":{}}]
     * time : 1562815507
     * vsn : 1.8.2
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

    public static class DataBean extends Entity {
        public boolean isSelected;
        /**
         * id : 5573978
         * title : 看掘金宝资讯影视，赚车赚房可能么？
         * author : 掘金宝资讯
         * publish_time : 1552127425
         * img_url : ["http://p3.pstatp.com/list/190x124/pgc-image/656e8cf39a564dc3a85ac0d352bf5d81"]
         * author_logo : https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/aboutus_logo.png
         * mark : 置顶
         * showtype : 1
         * read_count : 398784
         * comment_count : 0
         * digg_count : 0
         * type : article
         * description :
         * other : {}
         */

        private int id;
        private String title;
        private String author;
        private int publish_time;
        private String author_logo;
        private String mark;
        private int showtype;
        private int read_count;
        private int comment_count;
        private int digg_count;
        private int isGiveLike; //是否点赞
        private String type;
        private String description;
        private OtherBean other;
        private List<String> img_url;
        private String videoUrl;
        private int read_num;
        private List<HotPointEntity.HotPoint> hot_point_lists;
        private int pre_load_count;
        private int delay_pre_load_time;
        private int view_reward_count;
        private int official_notice;// 为1时表示公告，为0表示不是公告
        private String analysissUrl; //视频解析地址  头条视频时需要解析播放地址后才能播放
        private String source; //来源 ： "好看视频" "头条视频"
        private int share_count;

        public int getShare_count() {
            return share_count;
        }

        public void setShare_count(int share_count) {
            this.share_count = share_count;
        }

        public int getIsGiveLike() {
            return isGiveLike;
        }

        public void setIsGiveLike(int isGiveLike) {
            this.isGiveLike = isGiveLike;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getAnalysissUrl() {
            return analysissUrl;
        }

        public void setAnalysissUrl(String analysissUrl) {
            this.analysissUrl = analysissUrl;
        }

        public int getOfficial_notice() {
            return official_notice;
        }

        public void setOfficial_notice(int official_notice) {
            this.official_notice = official_notice;
        }

        public int getPre_load_count() {
            return pre_load_count;
        }

        public void setPre_load_count(int pre_load_count) {
            this.pre_load_count = pre_load_count;
        }

        public int getDelay_pre_load_time() {
            return delay_pre_load_time;
        }

        public void setDelay_pre_load_time(int delay_pre_load_time) {
            this.delay_pre_load_time = delay_pre_load_time;
        }

        public int getView_reward_count() {
            return view_reward_count;
        }

        public void setView_reward_count(int view_reward_count) {
            this.view_reward_count = view_reward_count;
        }

        public List<HotPointEntity.HotPoint> getHot_point_lists() {
            return hot_point_lists;
        }

        public void setHot_point_lists(List<HotPointEntity.HotPoint> hot_point_lists) {
            this.hot_point_lists = hot_point_lists;
        }

        public int getRead_num() {
            return read_num;
        }

        public void setRead_num(int read_num) {
            this.read_num = read_num;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(int publish_time) {
            this.publish_time = publish_time;
        }

        public String getAuthor_logo() {
            return author_logo;
        }

        public void setAuthor_logo(String author_logo) {
            this.author_logo = author_logo;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        public int getShowtype() {
            return showtype;
        }

        public void setShowtype(int showtype) {
            this.showtype = showtype;
        }

        public int getRead_count() {
            return read_count;
        }

        public void setRead_count(int read_count) {
            this.read_count = read_count;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public int getDigg_count() {
            return digg_count;
        }

        public void setDigg_count(int digg_count) {
            this.digg_count = digg_count;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public OtherBean getOther() {
            return other;
        }

        public void setOther(OtherBean other) {
            this.other = other;
        }

        public List<String> getImg_url() {
            return img_url;
        }

        public void setImg_url(List<String> img_url) {
            this.img_url = img_url;
        }

        public static class OtherBean extends Entity {

            private List<SmallvideoListBean> smallvideo_list;

            private long video_duration;

            private String video_id;

            private String redirect_url;

            public String getRedirect_url() {
                return redirect_url;
            }

            public void setRedirect_url(String redirect_url) {
                this.redirect_url = redirect_url;
            }

            public String getVideo_id() {
                return video_id;
            }

            public void setVideo_id(String video_id) {
                this.video_id = video_id;
            }

            public List<SmallvideoListBean> getSmallvideo_list() {
                return smallvideo_list;
            }

            public void setSmallvideo_list(List<SmallvideoListBean> smallvideo_list) {
                this.smallvideo_list = smallvideo_list;
            }


            public long getVideo_duration() {
                return video_duration;
            }

            public void setVideo_duration(long video_duration) {
                this.video_duration = video_duration;
            }

            public static class SmallvideoListBean extends Entity {
                /**
                 * id : 10580
                 * author : 高唐县美食家餐饮
                 * publish_time : 1562748883
                 * img_url : ["http://p1-hs.byteimg.com/img/tos-cn-p-0000/df7db70539e5494da829ceb5eb740ec5~tplv-hs-large.webp","http://v3-hs.bytecdn.cn/e34d3dd8efb94e3c42669b57ca62bd71/5d25b5dc/video/m/22049d838e02bab4e489e5613e3db340cee1162a1e5e00004131f8d3287f/?rc=amdqc2wzc2w0bjMzN2YzM0ApQHRAbzo5MzM0NTszNDc3OTM6PDNAKXUpQGc2dSlAZjN2KUBmbmQxaGxobEApNDRkc2FxaHBwaGEzXy0tXi0vc3MtbyNvIz81MzMzLS4tLS8yLi4tLi9pOmIwcCM6YS1xIzpgLW8jYG1qcStzaWAvLjo%3D"]
                 * mark :
                 * showtype : 6
                 * read_count : 10525082
                 * comment_count : 2183
                 * digg_count : 89984
                 * type : smallvideo
                 */

                private int id;
                private String author;
                private int publish_time;
                private String mark;
                private int showtype;
                private double read_count;
                private double comment_count;
                private double digg_count;
                private String type;
                private String description;

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getAuthor_logo() {
                    return author_logo;
                }


                public void setAuthor_logo(String author_logo) {
                    this.author_logo = author_logo;
                }

                private String author_logo;
                private List<String> img_url;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getAuthor() {
                    return author;
                }

                public void setAuthor(String author) {
                    this.author = author;
                }

                public int getPublish_time() {
                    return publish_time;
                }

                public void setPublish_time(int publish_time) {
                    this.publish_time = publish_time;
                }

                public String getMark() {
                    return mark;
                }

                public void setMark(String mark) {
                    this.mark = mark;
                }

                public int getShowtype() {
                    return showtype;
                }

                public void setShowtype(int showtype) {
                    this.showtype = showtype;
                }

                public double getRead_count() {
                    return read_count;
                }

                public void setRead_count(int read_count) {
                    this.read_count = read_count;
                }

                public double getComment_count() {
                    return comment_count;
                }

                public void setComment_count(int comment_count) {
                    this.comment_count = comment_count;
                }

                public double getDigg_count() {
                    return digg_count;
                }

                public void setDigg_count(int digg_count) {
                    this.digg_count = digg_count;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public List<String> getImg_url() {
                    return img_url;
                }

                public void setImg_url(List<String> img_url) {
                    this.img_url = img_url;
                }


            }
        }
    }
}
