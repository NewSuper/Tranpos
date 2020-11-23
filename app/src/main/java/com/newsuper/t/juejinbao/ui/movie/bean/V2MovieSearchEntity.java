package com.newsuper.t.juejinbao.ui.movie.bean;

import java.util.List;

public class V2MovieSearchEntity {


    /**
     * code : 0
     * msg : success
     * data : ["猫狗大战","猫狗大战2：珍珠猫复仇","猫狗 第一季","哆啦A梦：大雄的猫狗时空传","猫狗宠物街","猫狗 第二季","猫狗 第三季","猫狗互换","猫狗宠物街OVA","猫狗宠物街 第二季","猫狗宠物街3"]
     * time : 1582268666
     * vsn : 1.8.8
     */

    private int code;
    private String msg;
    private int time;
    private String vsn;
    private List<String> data;

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

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
