package com.newsuper.t.markert.entity;

import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;

import java.util.Objects;

public class MultipleQueryProduct extends Product implements Cloneable{
    private String specName;
    private String specId;
    private String categoryName;
    private String categoryNo;
    private String unitName;
    private String brandName;
    private String batchPrice;
    private String batchPrice2;
    private String batchPrice3;
    private String batchPrice4;
    private String batchPrice5;
    private String batchPrice6;
    private String batchPrice7;
    private String batchPrice8;
    private String minPrice;
    private String otherPrice;
    private String postPrice;
    private String purPrice;
    private String salePrice;
    private String vipPrice;
    private String vipPrice2;
    private String vipPrice3;
    private String vipPrice4;
    private String vipPrice5;
    private String isDefault;
    private String purchaseSpec;
    private String supplierName;

    private int mAmount = 1;
    private String finalPrice = "0";
    public int getmAmount() {
        return mAmount;
    }

    public void setmAmount(int mAmount) {
        this.mAmount = mAmount;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public MultipleQueryProduct setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
        return this;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hashCode(getId())+3 * super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean isEqual = false;
        if(obj instanceof MultipleQueryProduct){
            MultipleQueryProduct m = (MultipleQueryProduct) obj;
            if(this.getId().equals(m.getId())){
                isEqual = true;
                if(!TextUtils.isEmpty(specName) && !TextUtils.isEmpty(m.getSpecName())){
                    if(!specName.equals(m.getSpecName())){
                        isEqual = false;
                    }
                }

            }
        }
        return isEqual;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        MultipleQueryProduct copy = null;
        copy = (MultipleQueryProduct) super.clone();
        return copy;
    }

    //    private String askFlag;

    @Override
    public String toString() {
        return "MultipleQueryProduct{" +
                "specName='" + specName + '\'' +
                ", specId='" + specId + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", categoryNo='" + categoryNo + '\'' +
                ", unitName='" + unitName + '\'' +
                ", brandName='" + brandName + '\'' +
                ", batchPrice='" + batchPrice + '\'' +
                ", batchPrice2='" + batchPrice2 + '\'' +
                ", batchPrice3='" + batchPrice3 + '\'' +
                ", batchPrice4='" + batchPrice4 + '\'' +
                ", batchPrice5='" + batchPrice5 + '\'' +
                ", batchPrice6='" + batchPrice6 + '\'' +
                ", batchPrice7='" + batchPrice7 + '\'' +
                ", batchPrice8='" + batchPrice8 + '\'' +
                ", minPrice='" + minPrice + '\'' +
                ", otherPrice='" + otherPrice + '\'' +
                ", postPrice='" + postPrice + '\'' +
                ", purPrice='" + purPrice + '\'' +
                ", salePrice='" + salePrice + '\'' +
                ", vipPrice='" + vipPrice + '\'' +
                ", vipPrice2='" + vipPrice2 + '\'' +
                ", vipPrice3='" + vipPrice3 + '\'' +
                ", vipPrice4='" + vipPrice4 + '\'' +
                ", vipPrice5='" + vipPrice5 + '\'' +
                ", isDefault='" + isDefault + '\'' +
                ", purchaseSpec='" + purchaseSpec + '\'' +
                ", supplierName='" + supplierName + '\'' +
                '}';
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getSpecId() {
        return specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryNo() {
        return categoryNo;
    }

    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBatchPrice() {
        return batchPrice;
    }

    public void setBatchPrice(String batchPrice) {
        this.batchPrice = batchPrice;
    }

    public String getBatchPrice2() {
        return batchPrice2;
    }

    public void setBatchPrice2(String batchPrice2) {
        this.batchPrice2 = batchPrice2;
    }

    public String getBatchPrice3() {
        return batchPrice3;
    }

    public void setBatchPrice3(String batchPrice3) {
        this.batchPrice3 = batchPrice3;
    }

    public String getBatchPrice4() {
        return batchPrice4;
    }

    public void setBatchPrice4(String batchPrice4) {
        this.batchPrice4 = batchPrice4;
    }

    public String getBatchPrice5() {
        return batchPrice5;
    }

    public void setBatchPrice5(String batchPrice5) {
        this.batchPrice5 = batchPrice5;
    }

    public String getBatchPrice6() {
        return batchPrice6;
    }

    public void setBatchPrice6(String batchPrice6) {
        this.batchPrice6 = batchPrice6;
    }

    public String getBatchPrice7() {
        return batchPrice7;
    }

    public void setBatchPrice7(String batchPrice7) {
        this.batchPrice7 = batchPrice7;
    }

    public String getBatchPrice8() {
        return batchPrice8;
    }

    public void setBatchPrice8(String batchPrice8) {
        this.batchPrice8 = batchPrice8;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getOtherPrice() {
        return otherPrice;
    }

    public void setOtherPrice(String otherPrice) {
        this.otherPrice = otherPrice;
    }

    public String getPostPrice() {
        return postPrice;
    }

    public void setPostPrice(String postPrice) {
        this.postPrice = postPrice;
    }

    public String getPurPrice() {
        return purPrice;
    }

    public void setPurPrice(String purPrice) {
        this.purPrice = purPrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(String vipPrice) {
        this.vipPrice = vipPrice;
    }

    public String getVipPrice2() {
        return vipPrice2;
    }

    public void setVipPrice2(String vipPrice2) {
        this.vipPrice2 = vipPrice2;
    }

    public String getVipPrice3() {
        return vipPrice3;
    }

    public void setVipPrice3(String vipPrice3) {
        this.vipPrice3 = vipPrice3;
    }

    public String getVipPrice4() {
        return vipPrice4;
    }

    public void setVipPrice4(String vipPrice4) {
        this.vipPrice4 = vipPrice4;
    }

    public String getVipPrice5() {
        return vipPrice5;
    }

    public void setVipPrice5(String vipPrice5) {
        this.vipPrice5 = vipPrice5;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getPurchaseSpec() {
        return purchaseSpec;
    }

    public void setPurchaseSpec(String purchaseSpec) {
        this.purchaseSpec = purchaseSpec;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }


//    public String getAskFlag() {
//        return askFlag;
//    }
//
//    public void setAskFlag(String askFlag) {
//        this.askFlag = askFlag;
//    }
}
