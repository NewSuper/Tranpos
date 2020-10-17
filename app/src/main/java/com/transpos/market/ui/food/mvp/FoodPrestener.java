package com.transpos.market.ui.food.mvp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import com.google.gson.reflect.TypeToken;
import com.trans.network.callback.FileCallback;
import com.trans.network.callback.JsonObjectCallback;
import com.trans.network.callback.StringCallback;
import com.trans.network.model.Response;
import com.trans.network.utils.GsonHelper;
import com.transpos.market.R;
import com.transpos.market.base.BaseApp;
import com.transpos.market.base.mvp.BasePresenter;
import com.transpos.market.db.manger.OrderItemDbManger;
import com.transpos.market.entity.BaseListResponse;
import com.transpos.market.entity.BaseResponse;
import com.transpos.market.entity.DownloadCacheName;
import com.transpos.market.entity.DownloadNotify;
import com.transpos.market.entity.EntityResponse;
import com.transpos.market.entity.PosVersion;
import com.transpos.market.entity.Tuple2;
import com.transpos.market.ui.cash.manger.OrderItemManger;
import com.transpos.market.ui.cash.manger.OrderManger;
import com.transpos.market.utils.DateUtil;
import com.transpos.market.utils.DeviceUtils;
import com.transpos.market.utils.FileProvider;
import com.transpos.market.utils.FoodConstant;
import com.transpos.market.utils.Global;
import com.transpos.market.utils.KeyConstrant;
import com.transpos.market.utils.LogUtil;
import com.transpos.sale.db.manger.OrderObjectDbManger;
import com.transpos.sale.thread.ThreadDispatcher;
import com.transpos.tools.tputils.TPUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FoodPrestener extends BasePresenter<FoodContract.Model, FoodContract.View> implements FoodContract.Prestenter {
    @Override
    protected FoodContract.Model createModule() {
        return new FoodModel();
    }

    private static final String TAG = FoodPrestener.class.getSimpleName();
    private Object mLock = new Object();
    private volatile int threadCount = 0;

    @Override
    public void startDownload() {
        ThreadDispatcher.getDispatcher().post(()->{
            getModule().getWXID();
        });
        getView().showLoading();
        getModule().getApkVersion(new JsonObjectCallback<EntityResponse<PosVersion>>(){
            @Override
            public void onSuccess(Response<EntityResponse<PosVersion>> response) {
                super.onSuccess(response);
                EntityResponse<PosVersion> body = response.body();
                if(body.getCode() == BaseResponse.SUCCESS){
                    if(body.getData().getHasNew() == 1){
                        new AlertDialog.Builder(getContext())
                                .setMessage(getContext().getString(R.string.version_update))
                                .setTitle(getContext().getString(R.string.discover_version))
                                .setPositiveButton(getContext().getString(R.string.txt_confirm), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        apkDownload(body.getData());
                                        dialog.dismiss();
                                    }
                                })
                                .setCancelable(false)
                                .create()
                                .show();
                    }
                }
            }
        });
        getModule().obtainServerDataVersion(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                BaseListResponse<Map<String, String>> resp = GsonHelper.fromJson(response.body(), new TypeToken<BaseListResponse<Map<String, String>>>() {
                }.getType());
                if(resp.getCode() == BaseResponse.SUCCESS){
                    if (resp.getData() == null || resp.getData().size() == 0) {
                        LogUtil.e(LogUtil.TAG, String.format("未发现服务端数据版本信息"));
                        return;
                    }
                    compareVersion(resp.getData());
                } else {
                    getView().dismissLoading();
                    LogUtil.e(LogUtil.TAG, String.format("发掘新数据失败:%s", response.message()));
                }
            }

            @Override
            public void onError(Response<String> response) {
                getView().dismissLoading();
                LogUtil.e(LogUtil.TAG, String.format("发掘新数据error失败:%s", response.message()));
            }

        });
    }
    private void apkDownload(PosVersion data) {
        getModule().startApkDownload(data.getUploadFile(), new FileCallback() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(Response<File> response) {
                super.onSuccess(response);
                // install
                Log.e(TAG, "onSuccess: " +Thread.currentThread().getName());
                String apkFile = response.body().getPath();
                Intent installAPKIntent = FileProvider.getInstallApkIntent(getContext(),apkFile);
                getContext().startActivity(installAPKIntent);
            }

            @Override
            public void onProgress(long currLength, long totalLength) {
                super.onProgress(currLength, totalLength);
                Log.e(TAG, "onProgress: "+currLength );
            }

            @Override
            public void onError(Response<File> response) {
                super.onError(response);
                Log.e(TAG, "onError: " );
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public String getDestFileDir() {
                return BaseApp.getApplication().getExternalFilesDir(null).getPath()+"/apk/";
            }

            @Override
            public String getDestFileName() {
                return DeviceUtils.getInstance().getAppVersion()+".apk";
            }
        });
    }

    @Override
    public void refreshDb() {
        //更新订单表
        ThreadDispatcher.getDispatcher().postDelayed(()->{
            if(OrderManger.INSTANCE.getOrderBean() != null){
                OrderObjectDbManger.INSTANCE.update(OrderManger.INSTANCE.getOrderBean());
                OrderItemDbManger.INSTANCE.update(OrderItemManger.INSTANCE.getList());
            }
        },2000);

    }


    private void compareVersion(final List<Map<String, String>> datas) {
        Log.e(TAG, "run: compareVersion");
        ThreadDispatcher.getDispatcher().post(new Runnable() {
            @Override
            public void run() {
                List<Map<String,String>> localList = TPUtils.getObject(getContext(), KeyConstrant.KEY_DATA_VERSION,List.class);
                List<String> needDownload = Arrays.asList(Global.downloadDataType);
                final List<Tuple2<String, String>> vLists = new ArrayList<>();
                if(localList != null && localList.size() > 0){
                    String localJson = GsonHelper.toJson(localList);
                    String serverJson = GsonHelper.toJson(datas);
                    Log.e(TAG, "run: "+localJson );
                    Log.e(TAG, "run: "+serverJson);
                } else {
                    Log.e(TAG, "run: TPUtils.getObject==null");
                }
                for (Map<String, String> map : datas) {
                    String stype = map.get("dataType");
                    String sversion = map.get("dataVersion");
                    boolean isNeedUpdate = false;
                    if (needDownload.contains(stype)) {
                        if(localList != null){
                            for (Map<String, String> localMap : localList) {
                                if(stype.equals(localMap.get("dataType"))){
                                    if(!sversion.equals(localMap.get("dataVersion"))){
                                        isNeedUpdate = true;
                                    }
                                    break;
                                }
                            }
                        } else {
                            isNeedUpdate = true;
                        }
                    }
                    if (isNeedUpdate)
                        vLists.add(new Tuple2<String, String>(stype, sversion));
                }
                if (vLists.size() == 0) {
                    completeDownload();
                    Log.e(TAG, "run: "+"本地为最新数据");
                    return;
                }
                Log.e(TAG, "run: "+ DateUtil.getNowMillSecondDateStr());
                threadCount = 0;
                for (Tuple2<String, String> list : vLists) {
                    String dataType = list.first;
                    final DownloadCacheName downloadCacheName = DownloadCacheName.valueOf(dataType);
                    ThreadDispatcher.getDispatcher().post(new Runnable() {
                        @Override
                        public void run() {
                            realDownload(downloadCacheName,vLists.size());
                        }
                    });

                }
                TPUtils.putObject(getContext(), KeyConstrant.KEY_DATA_VERSION,datas);
            }
        });

    }

    /**
     * 下载完成
     */
    private void completeDownload() {
        Log.e(TAG, "run: "+DateUtil.getNowMillSecondDateStr());
        ThreadDispatcher.getDispatcher().postOnMain(new Runnable() {
            @Override
            public void run() {
                getView().dismissLoading();
            }
        });
    }

    private void realDownload(DownloadCacheName type, int size) {
        switch (type) {
            case PRODUCT_BRAND: {
                downloadProductBrand(1,FoodConstant.DEFAULT_PAGESIZE);
            }
            break;
            case PRODUCT_CATEGORY: {
                downloadProductCategory(1,FoodConstant.DEFAULT_PAGESIZE);
            }
            break;
            case PRODUCT_UNIT: {
                downloadProductUnit(1, FoodConstant.DEFAULT_PAGESIZE);
            }
            break;
            case PRODUCT: {
                downloadProduct(1,FoodConstant.DEFAULT_PAGESIZE);
                downloadProductCode(1,FoodConstant.DEFAULT_PAGESIZE);
                downloadProductContact(1,FoodConstant.DEFAULT_PAGESIZE);
                downloadProductSpec(1,FoodConstant.DEFAULT_PAGESIZE);
                downloadStoreProduct(1,FoodConstant.DEFAULT_PAGESIZE);
            }
            break;
            case WORKER: {
                downloadWorker(1,FoodConstant.DEFAULT_PAGESIZE);
//                downloadWorkerData(1,FoodConstant.DEFAULT_PAGESIZE);
//                downloadWorkerRole(1,FoodConstant.DEFAULT_PAGESIZE);
//                downloadWorkerModule(1,FoodConstant.DEFAULT_PAGESIZE);
            }
            break;
            case SUPPLIER: {
                downloadSupplier(1,FoodConstant.DEFAULT_PAGESIZE);
            }
            break;
            case PAYMODE: {
                downloadPayMode(1,FoodConstant.DEFAULT_PAGESIZE);
            }
            break;
            case STORE_INFO: {
                downloadStoreInfo(1,FoodConstant.DEFAULT_PAGESIZE);
            }
            break;
            case MEMBER_SETTING: {
                //会员设置
                downloadMemberSetting();
            }
            break;
            case MEMBER_LEVEL: {
                //会员等级
                downloadMemberLevel();
                downloadMemberLevelCategoryDiscount();
            }
            break;
            case MEMBER_POINT_RULE: {
                downloadMemberPointRule();
                downloadMemberPointRuleCategory();
                downloadMemberPointRuleBrand();
            }
            break;
            case PAY_SETTING: {
                downloadPaymentParameter();
            }
            break;
            case LINE_SALES_SETTING: {
                downloadSalesSetting();
            }
            break;
            default: {

            }
            break;
        }
        Log.e(TAG, "realDownload: value" + threadCount );
        synchronized (mLock){
            threadCount ++;
            Log.e(TAG, "realDownload: " + threadCount+"-----size"+size );
            if(threadCount >= size){
                completeDownload();
            }
        }
    }

    private void downloadSalesSetting() {
        DownloadNotify notify = getModule().fetchSalesSetting();
        LogUtil.e(TAG,notify.getMessage());
    }

    private void downloadPaymentParameter() {
        DownloadNotify notify = getModule().fetchPaymentParameter();
        LogUtil.e(TAG,notify.getMessage());
    }

    private void downloadMemberPointRuleBrand() {
        DownloadNotify notify = getModule().fetchMemberPointRuleBrand();
        LogUtil.e(TAG,notify.getMessage());
    }

    private void downloadMemberPointRuleCategory() {
        DownloadNotify notify = getModule().fetchMemberPointRuleCategory();
        LogUtil.e(TAG,notify.getMessage());
    }

    private void downloadMemberPointRule() {
        DownloadNotify notify = getModule().fetchMemberPointRule();
        LogUtil.e(TAG,notify.getMessage());
    }

    private void downloadMemberLevelCategoryDiscount() {
        DownloadNotify notify = getModule().fetchMemberLevelCategoryDiscount();
        LogUtil.e(TAG,notify.getMessage());
    }

    private void downloadMemberLevel() {
        DownloadNotify notify = getModule().fetchMemberLevel();
        LogUtil.e(TAG,notify.getMessage());
    }

    private void downloadMemberSetting() {
        DownloadNotify notify = getModule().fetchMemberSetting();
        LogUtil.e(TAG,notify.getMessage());
    }

    private void downloadStoreInfo(int pageIndex, int defaultPagesize) {
        DownloadNotify notify = getModule().fetchStoreInfo(pageIndex, defaultPagesize);
        if(notify.isSuccess()){
            if(notify.isPager()){
                int pageNum = notify.getPageNumber();
                int pageSize = notify.getPageSize();
                int pageCount = notify.getPageCount();
                for (int page = pageNum + 1; page < pageCount + 1; page++){
                    getModule().fetchStoreInfo(page,pageSize);
                }
            }
        }
        LogUtil.e(TAG,notify.getMessage());
    }

    private void downloadPayMode(int pageIndex, int defaultPagesize) {
        DownloadNotify notify = getModule().fetchPayMode(pageIndex, defaultPagesize);
        if(notify.isSuccess()){
            if(notify.isPager()){
                int pageNum = notify.getPageNumber();
                int pageSize = notify.getPageSize();
                int pageCount = notify.getPageCount();
                for (int page = pageNum + 1; page < pageCount + 1; page++){
                    getModule().fetchPayMode(page,pageSize);
                }
            }
        }
        LogUtil.e(TAG,notify.getMessage());
    }

    private void downloadSupplier(int pageIndex, int defaultPagesize) {
        DownloadNotify notify = getModule().fetchSupplier(pageIndex, defaultPagesize);
        if(notify.isSuccess()){
            if(notify.isPager()){
                int pageNum = notify.getPageNumber();
                int pageSize = notify.getPageSize();
                int pageCount = notify.getPageCount();
                for (int page = pageNum + 1; page < pageCount + 1; page++){
                    getModule().fetchSupplier(page,pageSize);
                }
            }
        }
        LogUtil.e(TAG,notify.getMessage());
    }

    private void downloadWorker(int pageIndex, int defaultPagesize) {
        DownloadNotify notify = getModule().fetchWorker(pageIndex, defaultPagesize);
        if(notify.isSuccess()){
            if(notify.isPager()){
                int pageNum = notify.getPageNumber();
                int pageSize = notify.getPageSize();
                int pageCount = notify.getPageCount();
                for (int page = pageNum + 1; page < pageCount + 1; page++){
                    getModule().fetchWorker(page,pageSize);
                }
            }
        }
        LogUtil.e(TAG,notify.getMessage());
    }
//    private void downloadWorkerData(int pageIndex, int defaultPagesize) {
//        DownloadNotify notify = getModule().fetchWorkerData(pageIndex, defaultPagesize);
//        if(notify.isSuccess()){
//            if(notify.isPager()){
//                int pageNum = notify.getPageNumber();
//                int pageSize = notify.getPageSize();
//                int pageCount = notify.getPageCount();
//                for (int page = pageNum + 1; page < pageCount + 1; page++){
//                    getModule().fetchWorkerData(page,pageSize);
//                }
//            }
//        }
//        LogUtil.e(TAG,notify.getMessage());
//    }
//    private void downloadWorkerRole(int pageIndex, int defaultPagesize) {
//        DownloadNotify notify = getModule().fetchWorkerRole(pageIndex, defaultPagesize);
//        if(notify.isSuccess()){
//            if(notify.isPager()){
//                int pageNum = notify.getPageNumber();
//                int pageSize = notify.getPageSize();
//                int pageCount = notify.getPageCount();
//                for (int page = pageNum + 1; page < pageCount + 1; page++){
//                    getModule().fetchWorkerRole(page,pageSize);
//                }
//            }
//        }
//        LogUtil.e(TAG,notify.getMessage());
//    }
//
//    private void downloadWorkerModule(int pageIndex, int defaultPagesize) {
//        DownloadNotify notify = getModule().fetchWorkerModule(pageIndex, defaultPagesize);
//        if(notify.isSuccess()){
//            if(notify.isPager()){
//                int pageNum = notify.getPageNumber();
//                int pageSize = notify.getPageSize();
//                int pageCount = notify.getPageCount();
//                for (int page = pageNum + 1; page < pageCount + 1; page++){
//                    getModule().fetchWorkerModule(page,pageSize);
//                }
//            }
//        }
//        LogUtil.e(TAG,notify.getMessage());
//    }
//    private void downloadShortCuts(int pageIndex, int defaultPagesize) {
//        DownloadNotify notify = getModule().fetchShortCuts(pageIndex, defaultPagesize);
//        if(notify.isSuccess()){
//            if(notify.isPager()){
//                int pageNum = notify.getPageNumber();
//                int pageSize = notify.getPageSize();
//                int pageCount = notify.getPageCount();
//                for (int page = pageNum + 1; page < pageCount + 1; page++){
//                    getModule().fetchShortCuts(page,pageSize);
//                }
//            }
//        }
//        LogUtil.e(TAG,notify.getMessage());
//    }

    private void downloadStoreProduct(int pageIndex, int defaultPagesize) {
        DownloadNotify notify = getModule().fetchStoreProduct(pageIndex, defaultPagesize);
        if(notify.isSuccess()){
            if(notify.isPager()){
                int pageNum = notify.getPageNumber();
                int pageSize = notify.getPageSize();
                int pageCount = notify.getPageCount();
                for (int page = pageNum + 1; page < pageCount + 1; page++){
                    getModule().fetchStoreProduct(page,pageSize);
                }
            }
        }
        LogUtil.e(TAG,notify.getMessage());
    }

    private void downloadProductSpec(int pageIndex, int defaultPagesize) {
        DownloadNotify notify = getModule().fetchProductSpec(pageIndex, defaultPagesize);
        if(notify.isSuccess()){
            if(notify.isPager()){
                int pageNum = notify.getPageNumber();
                int pageSize = notify.getPageSize();
                int pageCount = notify.getPageCount();
                for (int page = pageNum + 1; page < pageCount + 1; page++){
                    getModule().fetchProductSpec(page,pageSize);
                }
            }
        }
        LogUtil.e(TAG,notify.getMessage());
    }

    private void downloadProductContact(int pageIndex, int defaultPagesize) {
        DownloadNotify notify = getModule().fetchProductContact(pageIndex, defaultPagesize);
        if(notify.isSuccess()){
            if(notify.isPager()){
                int pageNum = notify.getPageNumber();
                int pageSize = notify.getPageSize();
                int pageCount = notify.getPageCount();
                for (int page = pageNum + 1; page < pageCount + 1; page++){
                    getModule().fetchProductContact(page,pageSize);
                }
            }
        }
        LogUtil.e(TAG,notify.getMessage());
    }

    private void downloadProductCode(int pageIndex, int defaultPagesize) {
        DownloadNotify notify = getModule().fetchProductCode(pageIndex, defaultPagesize);
        if(notify.isSuccess()){
            if(notify.isPager()){
                int pageNum = notify.getPageNumber();
                int pageSize = notify.getPageSize();
                int pageCount = notify.getPageCount();
                for (int page = pageNum + 1; page < pageCount + 1; page++){
                    getModule().fetchProductCode(page,pageSize);
                }
            }
        }
        LogUtil.e(TAG,notify.getMessage());
    }

    private void downloadProduct(int pageIndex, int defaultPagesize) {
        DownloadNotify notify = getModule().fetchProduct(pageIndex, defaultPagesize);
        if(notify.isSuccess()){
            if(notify.isPager()){
                int pageNum = notify.getPageNumber();
                int pageSize = notify.getPageSize();
                int pageCount = notify.getPageCount();
                for (int page = pageNum + 1; page < pageCount + 1; page++){
                    getModule().fetchProduct(page,pageSize);
                }
            }
        }

    }

    private void downloadProductUnit(int pageIndex, int defaultPagesize) {
        DownloadNotify notify = getModule().fetchProductUnit(pageIndex, defaultPagesize);
        if(notify.isSuccess()){
            if(notify.isPager()){
                int pageNum = notify.getPageNumber();
                int pageSize = notify.getPageSize();
                int pageCount = notify.getPageCount();
                for (int page = pageNum + 1; page < pageCount + 1; page++){
                    getModule().fetchProductUnit(page,pageSize);
                }
            }
        }
        LogUtil.e(TAG,notify.getMessage());
    }

    private void downloadProductCategory(int pageIndex, int defaultPagesize) {
        DownloadNotify notify = getModule().fetchProductCategroy(pageIndex, defaultPagesize);
        if(notify.isSuccess()){
            if(notify.isPager()){
                int pageNum = notify.getPageNumber();
                int pageSize = notify.getPageSize();
                int pageCount = notify.getPageCount();
                for (int page = pageNum + 1; page < pageCount + 1; page++){
                    getModule().fetchProductCategroy(page,pageSize);
                }
            }
        }
        LogUtil.e(TAG,notify.getMessage());
    }

    private void downloadProductBrand(int pageIndex,int size) {
        DownloadNotify notify = getModule().fetchProductBrand(pageIndex, size);
        if(notify.isSuccess()){
            if(notify.isPager()){
                int pageNum = notify.getPageNumber();
                int pageSize = notify.getPageSize();
                int pageCount = notify.getPageCount();
                for (int page = pageNum + 1; page < pageCount + 1; page++){
                    getModule().fetchProductBrand(page,pageSize);
                }
            }
        }
        LogUtil.e(TAG,notify.getMessage());
    }
}
