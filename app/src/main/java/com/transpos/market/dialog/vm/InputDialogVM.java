package com.transpos.market.dialog.vm;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class InputDialogVM extends ViewModel {

    private MutableLiveData<String> vipVerifyData = new MutableLiveData<>();
    private MutableLiveData<String> vipCancelData = new MutableLiveData<>();
    private MutableLiveData<String> specsData = new MutableLiveData<>();
    private MutableLiveData<String[]> editNumData = new MutableLiveData<>();
    private MutableLiveData<String[]> continueMemberPay = new MutableLiveData<>();
    private MutableLiveData<String> passwordData = new MutableLiveData<>();
    private MutableLiveData<String[]> viphoneData = new MutableLiveData<>();
    private MutableLiveData<String> bargaiData = new MutableLiveData<>();
    private MutableLiveData<String> discountData = new MutableLiveData<>();
    private MutableLiveData<String[]> cashPayData = new MutableLiveData<>();
    private MutableLiveData<String> orderCancelData = new MutableLiveData<>();
    private MutableLiveData<String[]> thirdCodeData = new MutableLiveData<>();
    private MutableLiveData<String[]> payLoadingData = new MutableLiveData<>();
    private MutableLiveData<String[]> payFailData = new MutableLiveData<>();

    //输入会员密码继续支付
    public MutableLiveData<String[]> getContinueMemberPay() {
        return continueMemberPay;
    }

    //输入会员手机号
    public MutableLiveData<String[]> getPhoneOrBarcodeData() {
        return viphoneData;
    }

    //修改商品数量
    public MutableLiveData<String[]> getEditNumData() {
        return editNumData;
    }

    //搜索手机号查会员
    public MutableLiveData<String> getVipVerifyData() {
        return vipVerifyData;
    }

    //单纯微信，会员，支付宝支付回调密码进行支付
    public MutableLiveData<String> getPasswordData() {
        return passwordData;
    }

    //取消会员
    public MutableLiveData<String> getVipCancelData() {
        return vipCancelData;
    }

    //多规格商品
    public MutableLiveData<String> getSpecsData() {
        return specsData;
    }

    //整单议价
    public MutableLiveData<String> getBargaiData() {
        return bargaiData;
    }

    //整单折扣
    public MutableLiveData<String> getDiscountData() {
        return discountData;
    }

    //现金支付并打印
    public MutableLiveData<String[]> getCashPayData() {
        return cashPayData;
    }

    //整单取消
    public MutableLiveData<String> getOrderCancelData() {
        return orderCancelData;
    }

    //付款码无效
    public MutableLiveData<String[]> getPayLoadingData() {
        return payLoadingData;
    }

    public MutableLiveData<String[]> getPayFailData() {
        return payFailData;
    }

    //微信+支付宝+会员码
    public MutableLiveData<String[]> getThirdCodeData() {
        return thirdCodeData;
    }
}
