package com.transpos.market.ui.food.mvp;

import com.trans.network.callback.FileCallback;
import com.trans.network.callback.JsonObjectCallback;
import com.trans.network.callback.StringCallback;
import com.transpos.market.base.mvp.IBaseModel;
import com.transpos.market.base.mvp.IBaseView;
import com.transpos.market.entity.DownloadNotify;


public interface FoodContract {

    interface Model extends IBaseModel {
        void getWXID();//获取微信appid 商户id
        void obtainServerDataVersion(StringCallback callback);
        void getApkVersion(JsonObjectCallback callback);
        void startApkDownload(String url, FileCallback callback);
        DownloadNotify fetchProductBrand(int pageNum, int pageSize);
        DownloadNotify fetchProductCategroy(int pageNum, int pageSize);
        DownloadNotify fetchProductUnit(int pageNum, int pageSize);
        DownloadNotify fetchProduct(int pageNum, int pageSize);
        DownloadNotify fetchProductCode(int pageNum, int pageSize);
        DownloadNotify fetchProductContact(int pageNum, int pageSize);
        DownloadNotify fetchProductSpec(int pageNum, int pageSize);
        DownloadNotify fetchStoreProduct(int pageNum, int pageSize);
        DownloadNotify fetchWorker(int pageNum, int pageSize);
//        DownloadNotify fetchWorkerData(int pageNum, int pageSize);
//        DownloadNotify fetchWorkerModule(int pageNum, int pageSize);
//        DownloadNotify fetchWorkerRole(int pageNum, int pageSize);
//        DownloadNotify fetchShortCuts(int pageNum, int pageSize);
        DownloadNotify fetchSupplier(int pageNum, int pageSize);
        DownloadNotify fetchPayMode(int pageNum, int pageSize);
        DownloadNotify fetchStoreInfo(int pageNum, int pageSize);
        DownloadNotify fetchMemberSetting();
        DownloadNotify fetchMemberLevel();
        DownloadNotify fetchMemberLevelCategoryDiscount();
        DownloadNotify fetchMemberPointRule();
        DownloadNotify fetchMemberPointRuleCategory();
        DownloadNotify fetchMemberPointRuleBrand();
        DownloadNotify fetchPaymentParameter();
        DownloadNotify fetchSalesSetting();
    }

    interface View extends IBaseView {

    }

    interface Prestenter{
        void startDownload();
        void refreshDb();
    }
}
