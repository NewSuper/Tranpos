package com.newsuper.t.consumer.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class GoodsDbBean {
    @Id(autoincrement = true)
    private Long id;
    private String shopId;
    private String goodsId;
    private String nature;//主要是用来判断商品是否相同
    private String goodsInfo;
    private String type;//"0" 表示商品  "1"表示套餐
    private String index;
    @Generated(hash = 2057618486)
    public GoodsDbBean(Long id, String shopId, String goodsId, String nature,
            String goodsInfo, String type, String index) {
        this.id = id;
        this.shopId = shopId;
        this.goodsId = goodsId;
        this.nature = nature;
        this.goodsInfo = goodsInfo;
        this.type = type;
        this.index = index;
    }

    @Generated(hash = 473331760)
    public GoodsDbBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getGoodsId() {
        return this.goodsId;
    }
    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
    public String getGoodsInfo() {
        return this.goodsInfo;
    }
    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getShopId() {
        return this.shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getNature() {
        return this.nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getIndex() {
        return this.index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

}
