package com.newsuper.t.juejinbao.ui.movie.entity;

import com.juejinchain.android.module.movie.adapter.EasyAdapter;

import java.io.Serializable;
import java.util.List;

public class MovieThirdIframeEntity implements Serializable {


    /**
     * currentIndex : 0
     * sourceList : [{"title":"2017-10-27期","url":"http://v.qq.com/x/cover/gpxi4rt5xnac260.html","source":"qq"},{"title":"2017-10-20期","url":"http://v.qq.com/x/cover/0xflk6yacsyn4dp.html","source":"qq"},{"title":"2017-10-13期","url":"http://v.qq.com/x/cover/61x1lesyztfz5tp.html","source":"qq"},{"title":"2017-10-06期","url":"http://v.qq.com/x/cover/6o3rfmiye6l1qrf.html","source":"qq"},{"title":"2017-09-29期","url":"http://v.qq.com/x/cover/j2ubf6j78zwpj22.html","source":"qq"},{"title":"2017-09-22期","url":"http://v.qq.com/x/cover/6a819g7fcyd3dvl.html","source":"qq"},{"title":"2017-09-15期","url":"http://v.qq.com/x/cover/bqo61k73czphhwa.html","source":"qq"},{"title":"2017-09-08期","url":"http://v.qq.com/x/cover/r7vu7uye2y578sk.html","source":"qq"},{"title":"2017-09-01期","url":"http://v.qq.com/x/cover/hizyhchgx6keu7m.html","source":"qq"},{"title":"2017-08-25期","url":"http://v.qq.com/x/cover/flrza3sqzt9glvx.html","source":"qq"},{"title":"2017-08-18期","url":"http://v.qq.com/x/cover/fh9glnk4euaioei.html","source":"qq"},{"title":"2017-08-11期","url":"http://v.qq.com/x/cover/9e84o47ic63u2g8.html","source":"qq"}]
     * currentUrl : http://v.qq.com/x/cover/gpxi4rt5xnac260.html
     * type : thirdIframe
     * id : 276618
     */

    private String title;
    private String info;
    private int currentIndex;
    private String currentUrl;
    private String type;
    private int id;
    private List<SourceListBean> sourceList;

    private int downIndex;
    private List<DownListBean> downlist;

    public int getDownIndex() {
        return downIndex;
    }

    public void setDownIndex(int downIndex) {
        this.downIndex = downIndex;
    }

    public List<DownListBean> getDownlist() {
        return downlist;
    }

    public void setDownlist(List<DownListBean> downlist) {
        this.downlist = downlist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public String getCurrentUrl() {
        return currentUrl;
    }

    public void setCurrentUrl(String currentUrl) {
        this.currentUrl = currentUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<SourceListBean> getSourceList() {
        return sourceList;
    }

    public void setSourceList(List<SourceListBean> sourceList) {
        this.sourceList = sourceList;
    }

    public static class SourceListBean implements Serializable {
        /**
         * title : 2017-10-27期
         * url : http://v.qq.com/x/cover/gpxi4rt5xnac260.html
         * source : qq
         */

        private String title;
        private String url;
        private String source;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }
    }

    public static class DownListBean extends EasyAdapter.TypeBean {
        private String name;
        private String url;
        private String videoDownloadUrl;



        private int selected = 0;
        private int played = 0;

        public int getSelected() {
            return selected;
        }

        public void setSelected(int selected) {
            this.selected = selected;
        }

        public int getPlayed() {
            return played;
        }

        public void setPlayed(int played) {
            this.played = played;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getVideoDownloadUrl() {
            return videoDownloadUrl;
        }

        public void setVideoDownloadUrl(String videoDownloadUrl) {
            this.videoDownloadUrl = videoDownloadUrl;
        }
    }
}
