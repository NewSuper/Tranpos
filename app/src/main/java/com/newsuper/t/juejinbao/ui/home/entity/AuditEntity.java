package com.newsuper.t.juejinbao.ui.home.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;
public class AuditEntity extends Entity {


    /**
     * code : 1
     * msg : {我有错误信息}
     * data : {}
     * time : 1574147370
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

    public static class DataBean {
        // ANDROID功能表示：fun1=信息流、开屏广告, fun2=免费看整体模块、赚车赚房页面、赚钱整体模块,fun3=我的模块-影评模块，
        //IOS功能表示：fun1=整体绕审     1打开，0关闭
        private int fun1;
        private int fun2;
        private int fun3;

        public int getFun1() {
            return fun1;
        }

        public void setFun1(int fun1) {
            this.fun1 = fun1;
        }

        public int getFun2() {
            return fun2;
        }

        public void setFun2(int fun2) {
            this.fun2 = fun2;
        }

        public int getFun3() {
            return fun3;
        }

        public void setFun3(int fun3) {
            this.fun3 = fun3;
        }
    }
}
