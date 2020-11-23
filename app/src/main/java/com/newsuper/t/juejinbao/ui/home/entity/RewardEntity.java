package com.newsuper.t.juejinbao.ui.home.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;
public class RewardEntity extends Entity {
    public RewardEntity() {
    }

    public RewardEntity(String title, double coin, String rewardType, String rewardDescribe, boolean isAutoDismiss) {
        this.title = title;
        this.coin = coin;
        this.rewardType = rewardType;
        this.rewardDescribe = rewardDescribe;
        this.isAutoDismiss = isAutoDismiss;
    }

    private String title;
    private double coin;
    private String rewardType = "";
    private String rewardDescribe;
    private boolean isAutoDismiss;

    public boolean isAutoDismiss() {
        return isAutoDismiss;
    }

    public void setAutoDismiss(boolean autoDismiss) {
        isAutoDismiss = autoDismiss;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getCoin() {
        return coin;
    }

    public void setCoin(double coin) {
        this.coin = coin;
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }

    public String getRewardDescribe() {
        return rewardDescribe;
    }

    public void setRewardDescribe(String rewardDescribe) {
        this.rewardDescribe = rewardDescribe;
    }
}
