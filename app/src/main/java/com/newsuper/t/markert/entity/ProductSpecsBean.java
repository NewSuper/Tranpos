package com.newsuper.t.markert.entity;

public class ProductSpecsBean {

    private boolean isCheck;
    private String specsName;
    private String specsPrice;
    private MultipleQueryProduct multipleQueryProduct;

    public MultipleQueryProduct getMultipleQueryProduct() {
        return multipleQueryProduct;
    }

    public ProductSpecsBean setMultipleQueryProduct(MultipleQueryProduct multipleQueryProduct) {
        this.multipleQueryProduct = multipleQueryProduct;
        return this;
    }

    public String getSpecsName() {
        return specsName;
    }

    public ProductSpecsBean setSpecsName(String specsName) {
        this.specsName = specsName;
        return this;
    }

    public String getSpecsPrice() {
        return specsPrice;
    }

    public ProductSpecsBean setSpecsPrice(String specsPrice) {
        this.specsPrice = specsPrice;
        return this;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public ProductSpecsBean setCheck(boolean check) {
        isCheck = check;
        return this;
    }
}
