package com.newsuper.t.juejinbao.ui.my.entity;

import com.newsuper.t.juejinbao.ui.movie.adapter.EasyAdapter;

import java.util.List;

public class MyInviteFriendListEntity {

    /**
     * code : 0
     * msg : success
     * data : {"total":41,"per_page":20,"current_page":1,"last_page":3,"data":[{"id":1487,"create_time":1573890492,"vcoin":"12.5000","status":1,"invitee_nickname":"沉鱼落雁","invite_code":152962,"trade_status":0,"j_url":"","invitee_avatar":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLGWtJrGPnibxmsIHMvvnBYnwKhicOpRWvcgw1c55p4Se3ZWfJyvqWwQ8ARDeyzUrDdXFiaofnb7hgFA/132"},{"id":1486,"create_time":1573889488,"vcoin":"12.5000","status":1,"invitee_nickname":"JasonMsj","invite_code":1806809,"trade_status":0,"j_url":"","invitee_avatar":"https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eobu9WmNqeCLXQGSocxUWZbsLYwm2u7JBjZD6rpM4H1MG90ibqpyDTbdfhibfMdViaSddDWd0lZVdpzA/132"},{"id":1485,"create_time":1573889461,"vcoin":"12.5000","status":1,"invitee_nickname":"微信粉丝:11161531","invite_code":7775386,"invitee_avatar":"http://jjlmobile.juejinchain.com/vsn1_1/share_icon/wechat.png"},{"id":1484,"create_time":1573888392,"vcoin":"12.5000","status":1,"invitee_nickname":"微信粉丝:11161513","invite_code":8818443,"invitee_avatar":"http://jjlmobile.juejinchain.com/vsn1_1/share_icon/wechat.png"},{"id":1483,"create_time":1573888250,"vcoin":"12.5000","status":1,"invitee_nickname":"微信粉丝:11161510","invite_code":0,"invitee_avatar":"http://jjlmobile.juejinchain.com/vsn1_1/share_icon/wechat.png"},{"id":1481,"create_time":1573887632,"vcoin":"12.5000","status":1,"invitee_nickname":"微信粉丝:11161500","invite_code":0,"invitee_avatar":"http://jjlmobile.juejinchain.com/vsn1_1/share_icon/wechat.png"},{"id":1480,"create_time":1573882841,"vcoin":"12.5000","status":1,"invitee_nickname":"微信粉丝:11161340","invite_code":0,"invitee_avatar":"http://jjlmobile.juejinchain.com/vsn1_1/share_icon/wechat.png"},{"id":1478,"create_time":1573872171,"vcoin":"6.2500","status":1,"invitee_nickname":"微信粉丝:11161042","invite_code":0,"invitee_avatar":"http://jjlmobile.juejinchain.com/vsn1_1/share_icon/wechat.png"},{"id":1477,"create_time":1573819191,"vcoin":"25.0000","status":1,"invitee_nickname":"掘金宝4976","invite_code":5697353,"trade_status":0,"j_url":"","invitee_avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/avatar.png"},{"id":1475,"create_time":1573809644,"vcoin":"25.0000","status":1,"invitee_nickname":"掘金宝6797","invite_code":2132755,"trade_status":0,"j_url":"","invitee_avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/avatar.png"},{"id":1474,"create_time":1573809521,"vcoin":"25.0000","status":1,"invitee_nickname":"掘金宝9797","invite_code":6312430,"trade_status":0,"j_url":"","invitee_avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/avatar.png"},{"id":1472,"create_time":1573808929,"vcoin":"25.0000","status":1,"invitee_nickname":"掘金宝3123","invite_code":4068250,"trade_status":0,"j_url":"","invitee_avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/avatar.png"},{"id":1424,"create_time":1573364637,"vcoin":"25.0000","status":1,"invitee_nickname":"掘金宝4423","invite_code":3211354,"trade_status":0,"j_url":"","invitee_avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/avatar.png"},{"id":1423,"create_time":1573364570,"vcoin":"25.0000","status":1,"invitee_nickname":"掘金宝9999","invite_code":2797998,"trade_status":0,"j_url":"","invitee_avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/avatar.png"},{"id":1422,"create_time":1573364491,"vcoin":"25.0000","status":1,"invitee_nickname":"掘金宝0888","invite_code":6792158,"trade_status":0,"j_url":"","invitee_avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/avatar.png"},{"id":1421,"create_time":1573364291,"vcoin":"25.0000","status":1,"invitee_nickname":"掘金宝5555","invite_code":2737418,"trade_status":0,"j_url":"","invitee_avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/avatar.png"},{"id":1420,"create_time":1573364246,"vcoin":"25.0000","status":1,"invitee_nickname":"掘金宝0055","invite_code":2410647,"trade_status":0,"j_url":"","invitee_avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/avatar.png"},{"id":1419,"create_time":1573364134,"vcoin":"25.0000","status":1,"invitee_nickname":"掘金宝0456","invite_code":192329,"trade_status":0,"j_url":"","invitee_avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/avatar.png"},{"id":1408,"create_time":1573356469,"vcoin":"25.0000","status":1,"invitee_nickname":"掘金宝0022","invite_code":1496033,"trade_status":0,"j_url":"","invitee_avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/avatar.png"},{"id":1406,"create_time":1573356085,"vcoin":"25.0000","status":1,"invitee_nickname":"掘金宝0100","invite_code":7594684,"trade_status":0,"j_url":"","invitee_avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/avatar.png"}]}
     * time : 1574071839
     * vsn : 1.8.8.2
     */

    private int code;
    private String msg;
    private DataBeanX data;
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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
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

    public static class DataBeanX {
        /**
         * total : 41
         * per_page : 20
         * current_page : 1
         * last_page : 3
         * data : [{"id":1487,"create_time":1573890492,"vcoin":"12.5000","status":1,"invitee_nickname":"沉鱼落雁","invite_code":152962,"trade_status":0,"j_url":"","invitee_avatar":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLGWtJrGPnibxmsIHMvvnBYnwKhicOpRWvcgw1c55p4Se3ZWfJyvqWwQ8ARDeyzUrDdXFiaofnb7hgFA/132"},{"id":1486,"create_time":1573889488,"vcoin":"12.5000","status":1,"invitee_nickname":"JasonMsj","invite_code":1806809,"trade_status":0,"j_url":"","invitee_avatar":"https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eobu9WmNqeCLXQGSocxUWZbsLYwm2u7JBjZD6rpM4H1MG90ibqpyDTbdfhibfMdViaSddDWd0lZVdpzA/132"},{"id":1485,"create_time":1573889461,"vcoin":"12.5000","status":1,"invitee_nickname":"微信粉丝:11161531","invite_code":7775386,"invitee_avatar":"http://jjlmobile.juejinchain.com/vsn1_1/share_icon/wechat.png"},{"id":1484,"create_time":1573888392,"vcoin":"12.5000","status":1,"invitee_nickname":"微信粉丝:11161513","invite_code":8818443,"invitee_avatar":"http://jjlmobile.juejinchain.com/vsn1_1/share_icon/wechat.png"},{"id":1483,"create_time":1573888250,"vcoin":"12.5000","status":1,"invitee_nickname":"微信粉丝:11161510","invite_code":0,"invitee_avatar":"http://jjlmobile.juejinchain.com/vsn1_1/share_icon/wechat.png"},{"id":1481,"create_time":1573887632,"vcoin":"12.5000","status":1,"invitee_nickname":"微信粉丝:11161500","invite_code":0,"invitee_avatar":"http://jjlmobile.juejinchain.com/vsn1_1/share_icon/wechat.png"},{"id":1480,"create_time":1573882841,"vcoin":"12.5000","status":1,"invitee_nickname":"微信粉丝:11161340","invite_code":0,"invitee_avatar":"http://jjlmobile.juejinchain.com/vsn1_1/share_icon/wechat.png"},{"id":1478,"create_time":1573872171,"vcoin":"6.2500","status":1,"invitee_nickname":"微信粉丝:11161042","invite_code":0,"invitee_avatar":"http://jjlmobile.juejinchain.com/vsn1_1/share_icon/wechat.png"},{"id":1477,"create_time":1573819191,"vcoin":"25.0000","status":1,"invitee_nickname":"掘金宝4976","invite_code":5697353,"trade_status":0,"j_url":"","invitee_avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/avatar.png"},{"id":1475,"create_time":1573809644,"vcoin":"25.0000","status":1,"invitee_nickname":"掘金宝6797","invite_code":2132755,"trade_status":0,"j_url":"","invitee_avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/avatar.png"},{"id":1474,"create_time":1573809521,"vcoin":"25.0000","status":1,"invitee_nickname":"掘金宝9797","invite_code":6312430,"trade_status":0,"j_url":"","invitee_avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/avatar.png"},{"id":1472,"create_time":1573808929,"vcoin":"25.0000","status":1,"invitee_nickname":"掘金宝3123","invite_code":4068250,"trade_status":0,"j_url":"","invitee_avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/avatar.png"},{"id":1424,"create_time":1573364637,"vcoin":"25.0000","status":1,"invitee_nickname":"掘金宝4423","invite_code":3211354,"trade_status":0,"j_url":"","invitee_avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/avatar.png"},{"id":1423,"create_time":1573364570,"vcoin":"25.0000","status":1,"invitee_nickname":"掘金宝9999","invite_code":2797998,"trade_status":0,"j_url":"","invitee_avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/avatar.png"},{"id":1422,"create_time":1573364491,"vcoin":"25.0000","status":1,"invitee_nickname":"掘金宝0888","invite_code":6792158,"trade_status":0,"j_url":"","invitee_avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/avatar.png"},{"id":1421,"create_time":1573364291,"vcoin":"25.0000","status":1,"invitee_nickname":"掘金宝5555","invite_code":2737418,"trade_status":0,"j_url":"","invitee_avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/avatar.png"},{"id":1420,"create_time":1573364246,"vcoin":"25.0000","status":1,"invitee_nickname":"掘金宝0055","invite_code":2410647,"trade_status":0,"j_url":"","invitee_avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/avatar.png"},{"id":1419,"create_time":1573364134,"vcoin":"25.0000","status":1,"invitee_nickname":"掘金宝0456","invite_code":192329,"trade_status":0,"j_url":"","invitee_avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/avatar.png"},{"id":1408,"create_time":1573356469,"vcoin":"25.0000","status":1,"invitee_nickname":"掘金宝0022","invite_code":1496033,"trade_status":0,"j_url":"","invitee_avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/avatar.png"},{"id":1406,"create_time":1573356085,"vcoin":"25.0000","status":1,"invitee_nickname":"掘金宝0100","invite_code":7594684,"trade_status":0,"j_url":"","invitee_avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/avatar.png"}]
         */

        private int total;
        private int per_page;
        private int current_page;
        private int last_page;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean extends EasyAdapter.TypeBean{
            /**
             * id : 1487
             * create_time : 1573890492
             * vcoin : 12.5000
             * status : 1
             * invitee_nickname : 沉鱼落雁
             * invite_code : 152962
             * trade_status : 0
             * j_url :
             * invitee_avatar : https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLGWtJrGPnibxmsIHMvvnBYnwKhicOpRWvcgw1c55p4Se3ZWfJyvqWwQ8ARDeyzUrDdXFiaofnb7hgFA/132
             */

            private int id;
            private long create_time;
            private String vcoin;
            private int status;
            private String invitee_nickname;
            private int invite_code;
            private int trade_status;
            private String j_url;
            private String invitee_avatar;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public long getCreate_time() {
                return create_time;
            }

            public void setCreate_time(long create_time) {
                this.create_time = create_time;
            }

            public String getVcoin() {
                return vcoin;
            }

            public void setVcoin(String vcoin) {
                this.vcoin = vcoin;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getInvitee_nickname() {
                return invitee_nickname;
            }

            public void setInvitee_nickname(String invitee_nickname) {
                this.invitee_nickname = invitee_nickname;
            }

            public int getInvite_code() {
                return invite_code;
            }

            public void setInvite_code(int invite_code) {
                this.invite_code = invite_code;
            }

            public int getTrade_status() {
                return trade_status;
            }

            public void setTrade_status(int trade_status) {
                this.trade_status = trade_status;
            }

            public String getJ_url() {
                return j_url;
            }

            public void setJ_url(String j_url) {
                this.j_url = j_url;
            }

            public String getInvitee_avatar() {
                return invitee_avatar;
            }

            public void setInvitee_avatar(String invitee_avatar) {
                this.invitee_avatar = invitee_avatar;
            }
        }
    }
}
