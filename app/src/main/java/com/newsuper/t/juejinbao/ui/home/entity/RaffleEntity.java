package com.newsuper.t.juejinbao.ui.home.entity;


public class RaffleEntity {
    private int type; // 1金币 2 掘金宝

    private int coin;// 金币数量

    private int vcoin;// 掘金宝数量

    private int is_open_ad;// 1为看视频领取广告，不是1就显示立即领取

    private int alertType;//弹框类型 1 领取前的，2 领取奖励完成

    public RaffleEntity() {
    }

    public RaffleEntity(int type, int coin, int vcoin, int is_open_ad, int alertType) {
        this.type = type;
        this.coin = coin;
        this.vcoin = vcoin;
        this.is_open_ad = is_open_ad;
        this.alertType = alertType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getVcoin() {
        return vcoin;
    }

    public void setVcoin(int vcoin) {
        this.vcoin = vcoin;
    }

    public int getIs_open_ad() {
        return is_open_ad;
    }

    public void setIs_open_ad(int is_open_ad) {
        this.is_open_ad = is_open_ad;
    }

    public int getAlertType() {
        return alertType;
    }

    public void setAlertType(int alertType) {
        this.alertType = alertType;
    }
}
