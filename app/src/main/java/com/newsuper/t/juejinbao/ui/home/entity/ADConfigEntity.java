package com.newsuper.t.juejinbao.ui.home.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;

public class ADConfigEntity extends Entity {

    /**
     * code : 0
     * msg : success
     * data : {"ad_type1":{"platform":0},"ad_type2":{"platform":0},"ad_type3":{"type":0,"platform":0,"platform_list":[{"platform":0,"radio_num":1},{"platform":1,"radio_num":2}]},"ad_type4":{"type":0,"platform":0,"platform_list":[{"platform":0,"radio_num":1},{"platform":1,"radio_num":1}]}}
     * time : 1585909152
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

    public static class DataBean extends Entity{
        /**
         * ad_type1 : {"platform":0}
         * ad_type2 : {"platform":0}
         * ad_type3 : {"type":0,"platform":0,"platform_list":[{"platform":0,"radio_num":1},{"platform":1,"radio_num":2}]}
         * ad_type4 : {"type":0,"platform":0,"platform_list":[{"platform":0,"radio_num":1},{"platform":1,"radio_num":1}]}
         */

        private AdType1Bean ad_type1;
        private AdType2Bean ad_type2;
        private AdType3Bean ad_type3;
        private AdType4Bean ad_type4;

        public AdType1Bean getAd_type1() {
            return ad_type1;
        }

        public void setAd_type1(AdType1Bean ad_type1) {
            this.ad_type1 = ad_type1;
        }

        public AdType2Bean getAd_type2() {
            return ad_type2;
        }

        public void setAd_type2(AdType2Bean ad_type2) {
            this.ad_type2 = ad_type2;
        }

        public AdType3Bean getAd_type3() {
            return ad_type3;
        }

        public void setAd_type3(AdType3Bean ad_type3) {
            this.ad_type3 = ad_type3;
        }

        public AdType4Bean getAd_type4() {
            return ad_type4;
        }

        public void setAd_type4(AdType4Bean ad_type4) {
            this.ad_type4 = ad_type4;
        }

        public static class AdType1Bean extends Entity{
            /**
             * platform : 0
             */

            private int platform;

            public int getPlatform() {
                return platform;
            }

            public void setPlatform(int platform) {
                this.platform = platform;
            }
        }

        public static class AdType2Bean extends Entity{
            /**
             * platform : 0
             */

            private int platform;

            public int getPlatform() {
                return platform;
            }

            public void setPlatform(int platform) {
                this.platform = platform;
            }
        }

        public static class AdType3Bean extends Entity{
            /**
             * type : 0
             * platform : 0
             * platform_list : [{"platform":0,"radio_num":1},{"platform":1,"radio_num":2}]
             */

            private int type;
            private int platform;
            private List<PlatformListBean> platform_list;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getPlatform() {
                return platform;
            }

            public void setPlatform(int platform) {
                this.platform = platform;
            }

            public List<PlatformListBean> getPlatform_list() {
                return platform_list;
            }

            public void setPlatform_list(List<PlatformListBean> platform_list) {
                this.platform_list = platform_list;
            }

            public static class PlatformListBean extends Entity{
                /**
                 * platform : 0
                 * radio_num : 1
                 */

                private int platform;
                private int radio_num;

                public int getPlatform() {
                    return platform;
                }

                public void setPlatform(int platform) {
                    this.platform = platform;
                }

                public int getRadio_num() {
                    return radio_num;
                }

                public void setRadio_num(int radio_num) {
                    this.radio_num = radio_num;
                }
            }
        }

        public static class AdType4Bean extends Entity{
            /**
             * type : 0
             * platform : 0
             * platform_list : [{"platform":0,"radio_num":1},{"platform":1,"radio_num":1}]
             */

            private int type;
            private int platform;
            private List<PlatformListBeanX> platform_list;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getPlatform() {
                return platform;
            }

            public void setPlatform(int platform) {
                this.platform = platform;
            }

            public List<PlatformListBeanX> getPlatform_list() {
                return platform_list;
            }

            public void setPlatform_list(List<PlatformListBeanX> platform_list) {
                this.platform_list = platform_list;
            }

            public static class PlatformListBeanX extends Entity{
                /**
                 * platform : 0
                 * radio_num : 1
                 */

                private int platform;
                private int radio_num;

                public int getPlatform() {
                    return platform;
                }

                public void setPlatform(int platform) {
                    this.platform = platform;
                }

                public int getRadio_num() {
                    return radio_num;
                }

                public void setRadio_num(int radio_num) {
                    this.radio_num = radio_num;
                }
            }
        }
    }
}
