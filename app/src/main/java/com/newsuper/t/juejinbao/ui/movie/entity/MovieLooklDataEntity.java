package com.newsuper.t.juejinbao.ui.movie.entity;

import java.io.Serializable;
import java.util.List;

public class MovieLooklDataEntity implements Serializable {


    /**
     * code : 0
     * msg : success
     * data : {"count":74901,"user_list":[{"avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/avatar/v2-321a45fa38cf7d0af3bb7c0d42f2c350_m.jpg","nickname":"limingbo"},{"avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/avatar/47c12d52de485f0c4cf2cc11a44f3b71_m.jpg","nickname":"pcccc"},{"avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/avatar/90260e6baf7c2cfa9c8b722e31a734aa_m.jpg","nickname":"三旬"},{"avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/avatar/a307a6611a3fd145e0b318705843b1c9_m.jpg","nickname":"姜楠"},{"avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/avatar/59aba485f_m.jpg","nickname":"懒墨"},{"avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/avatar/33770cb36b5c6ddf0cf798f6d1c7711a_m.jpg","nickname":"曉夜"},{"avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/avatar/v2-1a2767be3883782bff8b9e1526fdb566_m.jpg","nickname":"灼灼"},{"avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/avatar/v2-98830a6004ee893eae4c0fb1a0893129_m.jpg","nickname":"荒原"},{"avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/avatar/v2-31bb0c4bc93f9ab841384246f61825c7_m.jpg","nickname":"酥久"},{"avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/avatar/3db8a5a0925d03c5073e30fe6e342d8b_m.jpg","nickname":"长安"}]}
     * time : 1562901927
     * vsn : 1.8.5
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
         * count : 74901
         * user_list : [{"avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/avatar/v2-321a45fa38cf7d0af3bb7c0d42f2c350_m.jpg","nickname":"limingbo"},{"avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/avatar/47c12d52de485f0c4cf2cc11a44f3b71_m.jpg","nickname":"pcccc"},{"avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/avatar/90260e6baf7c2cfa9c8b722e31a734aa_m.jpg","nickname":"三旬"},{"avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/avatar/a307a6611a3fd145e0b318705843b1c9_m.jpg","nickname":"姜楠"},{"avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/avatar/59aba485f_m.jpg","nickname":"懒墨"},{"avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/avatar/33770cb36b5c6ddf0cf798f6d1c7711a_m.jpg","nickname":"曉夜"},{"avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/avatar/v2-1a2767be3883782bff8b9e1526fdb566_m.jpg","nickname":"灼灼"},{"avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/avatar/v2-98830a6004ee893eae4c0fb1a0893129_m.jpg","nickname":"荒原"},{"avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/avatar/v2-31bb0c4bc93f9ab841384246f61825c7_m.jpg","nickname":"酥久"},{"avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/avatar/3db8a5a0925d03c5073e30fe6e342d8b_m.jpg","nickname":"长安"}]
         */

        private int count;
        private List<UserListBean> user_list;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<UserListBean> getUser_list() {
            return user_list;
        }

        public void setUser_list(List<UserListBean> user_list) {
            this.user_list = user_list;
        }

        public static class UserListBean {
            /**
             * avatar : https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/avatar/v2-321a45fa38cf7d0af3bb7c0d42f2c350_m.jpg
             * nickname : limingbo
             */

            private String avatar;
            private String nickname;
            private String j_url;

            public String getJ_url() {
                return j_url;
            }

            public void setJ_url(String j_url) {
                this.j_url = j_url;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }
        }
    }
}
