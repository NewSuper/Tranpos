package com.newsuper.t.juejinbao.ui.song.entity;

import java.util.List;

public class SongBillBoardEntity {


    /**
     * code : 0
     * msg : success
     * data : [{"icon":"","title":"网易排行榜","lists":[{"id":16,"title":"云音乐飙升榜","thumbnail":""}]},{"icon":"","title":"酷狗榜单","lists":[]},{"icon":"","title":"QQ音乐榜单","lists":[]}]
     */

    private int code;
    private String msg;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * icon :
         * title : 网易排行榜
         * lists : [{"id":16,"title":"云音乐飙升榜","thumbnail":""}]
         */

        private String icon;
        private String title;
        private List<ListsBean> lists;

        public boolean isClick;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ListsBean> getLists() {
            return lists;
        }

        public void setLists(List<ListsBean> lists) {
            this.lists = lists;
        }

        public static class ListsBean {
            /**
             * id : 16
             * title : 云音乐飙升榜
             * thumbnail :
             */

            private int id;
            private String title;
            private String thumbnail;

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

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }
        }
    }
}
