package com.newsuper.t.juejinbao.ui.home.NetInfo;

import com.newsuper.t.juejinbao.bean.Entity;
import com.newsuper.t.juejinbao.ui.home.entity.VideoChannelEntity;

import java.util.List;

import java.util.List;


public class VideoChannelInfo extends Entity {

    /**
     * code : 0
     * msg : success
     * data : [{"ch":"热门","en":"video"},{"ch":"影视","en":"subv_movie"},{"ch":"游戏","en":"subv_game"},{"ch":"社会","en":"subv_society"},{"ch":"音乐","en":"subv_voice"},{"ch":"综艺","en":"subv_tt_video_variety"},{"ch":"农人","en":"subv_tt_video_agriculture"},{"ch":"美食","en":"subv_tt_video_food"},{"ch":"宠物","en":"subv_tt_video_pet"},{"ch":"儿童","en":"subv_tt_video_child"},{"ch":"懂车帝","en":"subv_tt_video_car"},{"ch":"生活","en":"subv_life"},{"ch":"体育","en":"subv_tt_video_sports"},{"ch":"文化","en":"subv_tt_video_culture"},{"ch":"时尚","en":"subv_tt_video_fashion"},{"ch":"手工","en":"subv_tt_hand_made"},{"ch":"金秒奖","en":"subv_jmj"},{"ch":"科技","en":"subv_tt_video_tech"},{"ch":"广场舞","en":"subv_tt_video_squaredance"},{"ch":"亲子","en":"subv_tt_video_motherbaby"},{"ch":"搞笑","en":"subv_funny"},{"ch":"娱乐","en":"subv_entertainment"},{"ch":"NBA","en":"tt_subv_nba"}]
     * time : 1562771660
     * vsn : 1.8.2
     */

    private int code;
    private String msg;
    private int time;
    private String vsn;
    private List<VideoChannelEntity> data;

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

    public List<VideoChannelEntity> getData() {
        return data;
    }

    public void setData(List<VideoChannelEntity> data) {
        this.data = data;
    }

}
