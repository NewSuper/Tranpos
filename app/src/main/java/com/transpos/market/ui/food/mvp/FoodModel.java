package com.transpos.market.ui.food.mvp;

import com.google.gson.reflect.TypeToken;
import com.trans.network.HttpManger;
import com.trans.network.callback.FileCallback;
import com.trans.network.callback.JsonObjectCallback;
import com.trans.network.callback.StringCallback;
import com.trans.network.utils.GsonHelper;
import com.transpos.market.base.BaseApp;
import com.transpos.market.db.manger.LineSalesSettingDbManger;
import com.transpos.market.db.manger.LineSystemSetDbManger;
import com.transpos.market.db.manger.MemberLevelCategoryDiscountDbManger;
import com.transpos.market.db.manger.MemberLevelDbManger;
import com.transpos.market.db.manger.MemberPointRuleBrandDbManger;
import com.transpos.market.db.manger.MemberPointRuleCategoryDbManger;
import com.transpos.market.db.manger.MemberPointRuleDbManger;
import com.transpos.market.db.manger.PayModeDbManger;
import com.transpos.market.db.manger.PaymentParameterDbManger;
import com.transpos.market.db.manger.ProductBrandDbManger;
import com.transpos.market.db.manger.ProductCategroyDbManger;
import com.transpos.market.db.manger.ProductCodeDbManger;
import com.transpos.market.db.manger.ProductContractDbManger;
import com.transpos.market.db.manger.ProductDbManger;
import com.transpos.market.db.manger.ProductSpecDbManger;
import com.transpos.market.db.manger.ProductUnitDbManger;
import com.transpos.market.db.manger.StoreInfoDbManger;
import com.transpos.market.db.manger.StoreProductDbManger;
import com.transpos.market.db.manger.SupplierDbManger;
import com.transpos.market.db.manger.WorkerDataDbManager;
import com.transpos.market.db.manger.WorkerDbManger;
import com.transpos.market.db.manger.WorkerModuleDbManager;
import com.transpos.market.db.manger.WorkerRoleDbManager;
import com.transpos.market.entity.BaseListResponse;
import com.transpos.market.entity.BasePagerResponse;
import com.transpos.market.entity.BaseResponse;
import com.transpos.market.entity.DownloadCacheName;
import com.transpos.market.entity.DownloadNotify;
import com.transpos.market.entity.LineSalesSetting;
import com.transpos.market.entity.LineSystemSet;
import com.transpos.market.entity.MemberLevel;
import com.transpos.market.entity.MemberLevelCategoryDiscount;
import com.transpos.market.entity.MemberPointRule;
import com.transpos.market.entity.MemberPointRuleBrand;
import com.transpos.market.entity.MemberPointRuleCategory;
import com.transpos.market.entity.PayMode;
import com.transpos.market.entity.PaymentParameter;
import com.transpos.market.entity.Product;
import com.transpos.market.entity.ProductBrand;
import com.transpos.market.entity.ProductCategory;
import com.transpos.market.entity.ProductCode;
import com.transpos.market.entity.ProductContact;
import com.transpos.market.entity.ProductSpec;
import com.transpos.market.entity.ProductUnit;
import com.transpos.market.entity.RegistrationCode;
import com.transpos.market.entity.StoreInfo;
import com.transpos.market.entity.StoreProduct;
import com.transpos.market.entity.Supplier;
import com.transpos.market.entity.WorkData;
import com.transpos.market.entity.WorkModule;
import com.transpos.market.entity.WorkRole;
import com.transpos.market.entity.Worker;
import com.transpos.market.base.HttpUrl;
import com.transpos.market.utils.DeviceUtils;
import com.transpos.market.utils.Global;
import com.transpos.market.utils.KeyConstrant;
import com.transpos.market.utils.LogUtil;
import com.transpos.market.utils.OpenApiUtils;
import com.transpos.tools.tputils.TPUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodModel implements FoodContract.Model {

    @Override
    public void obtainServerDataVersion(StringCallback callback) {
        String tips = "新数据发掘";
        Map<String, Object> parameters = OpenApiUtils.getInstance().newApiParameters();
        parameters.put("name", "server.data.version");
        RegistrationCode auth = TPUtils.getObject(BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);

        Map<String, Object> reqData = new HashMap<>();
        reqData.put("storeId", auth.getStoreId());
        parameters.put("data", GsonHelper.toJson(reqData));
        parameters.put("sign", OpenApiUtils.getInstance().sign(auth, parameters));
        HttpManger.getSingleton().postString(HttpUrl.BASE_API_URL, parameters, callback);
    }

    @Override
    public void getApkVersion(JsonObjectCallback callback) {
        Map<String, Object> parameters = OpenApiUtils.getInstance().newApiParameters();
        parameters.put("name", "server.version.get");
        RegistrationCode auth = TPUtils.getObject(BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);

        Map<String, Object> reqData = new HashMap<>();
        reqData.put("terminalType" , Global.terminalType);
        reqData.put("storeNo", auth.getStoreNo());
        reqData.put("posNo" , auth.getPosNo());
        reqData.put("appSign" , Global.appSign);
        reqData.put("versionType" , "1");
        reqData.put("versionNum" , DeviceUtils.getInstance().getAppVersion());
        parameters.put("data", GsonHelper.toJson(reqData));
        parameters.put("sign", OpenApiUtils.getInstance().sign(auth, parameters));
        HttpManger.getSingleton().postJsonObject(HttpUrl.BASE_API_URL,parameters,null,callback);
    }

    @Override
    public void startApkDownload(String url, FileCallback callback) {
        HttpManger.getSingleton().getFile(url,callback);
    }

    public void getWXID() {
        Map<String, Object> parameters = OpenApiUtils.getInstance().newApiParameters();
        parameters.put("name", "scan.face.setting");
        RegistrationCode api = TPUtils.getObject(BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);

        Map<String, Object> reqData = new HashMap<>();
        reqData.put("storeCode", api.getStoreNo());
        parameters.put("data", GsonHelper.toJson(reqData));
        parameters.put("sign", OpenApiUtils.getInstance().sign(api, parameters));
        String result = HttpManger.getSingleton().postString(HttpUrl.BASE_API_URL, parameters);
//        EntityResponse<WXFaceInfo> resp = GsonHelper.fromJson(result,new TypeToken<EntityResponse<WXFaceInfo>>(){}.getType());
//        if(resp.getCode() == BaseResponse.SUCCESS){
//            TPUtils.put(BaseApp.getApplication(),KeyConstrant.KEY_WX_APPID,resp.getData().getAppid());
//            TPUtils.put(BaseApp.getApplication(),KeyConstrant.KEY_WX_MECHID,resp.getData().getMchId());
//        }

    }

    @Override
    public DownloadNotify fetchProductBrand(int pageNum, int pageSize) {
        String tips = "商品品牌";
        DownloadNotify notify = new DownloadNotify();
        notify.setSuccess(false);
        notify.setOperate(DownloadCacheName.PRODUCT_BRAND);
        notify.setPager(false);
        notify.setPageCount(1);
        notify.setPageNumber(pageNum);

        Map<String, Object> parameters = OpenApiUtils.getInstance().newApiParameters();
        parameters.put("name", "product.brand.lists");
        RegistrationCode api = TPUtils.getObject(BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);

        Map<String, Object> reqData = new HashMap<>();
        reqData.put("pageNumber", pageNum);
        reqData.put("pageSize", pageSize);
        parameters.put("data", GsonHelper.toJson(reqData));
        parameters.put("sign", OpenApiUtils.getInstance().sign(api, parameters));
        String result = HttpManger.getSingleton().postString(HttpUrl.BASE_API_URL, parameters);
        BasePagerResponse<ProductBrand> resp = GsonHelper.fromJson(result, new TypeToken<BasePagerResponse<ProductBrand>>() {
        }.getType());
        if (resp.getCode() == BaseResponse.SUCCESS) {
            boolean firstPageFlag = pageNum < 2;
            if (cacheProductBrand(resp.getData().getList(), firstPageFlag)) {
                notify.setSuccess(true);
                notify.setOperate(DownloadCacheName.PRODUCT_BRAND);
                notify.setPager(resp.getData().getPageCount() > 1);
                notify.setPageCount(resp.getData().getPageCount());
                notify.setPageSize(resp.getData().getPageSize());
                notify.setPageNumber(resp.getData().getPageNumber());

                if (resp.getData().getPageCount() > 1) {
                    notify.setMessage(String.format("第%s页%s下载成功......", pageNum, tips));
                } else {
                    notify.setMessage(String.format("%s下载成功......", tips));
                }
            } else {
                notify.setSuccess(false);
                notify.setOperate(DownloadCacheName.PRODUCT_BRAND_EXCEPTION);
                notify.setMessage(String.format("下载%s时数据库发生异常", tips));
            }

        } else {
            notify.setSuccess(false);
            notify.setOperate(DownloadCacheName.PRODUCT_BRAND_EXCEPTION);
            notify.setMessage(String.format("下载%s时发生异常", tips));
        }
        return notify;
    }

    @Override
    public DownloadNotify fetchProductCategroy(int pageNum, int pageSize) {
        String tips = "商品分类";
        DownloadNotify notify = new DownloadNotify();
        notify.setSuccess(false);
        notify.setOperate(DownloadCacheName.PRODUCT_CATEGORY);
        notify.setPager(false);
        notify.setPageCount(1);
        notify.setPageNumber(pageNum);
        Map<String, Object> parameters = OpenApiUtils.getInstance().newApiParameters();
        parameters.put("name", "product.category.lists");

        RegistrationCode api = TPUtils.getObject(BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);
        Map<String, Object> reqData = new HashMap<>();
        reqData.put("pageNumber", pageNum);
        reqData.put("pageSize", pageSize);
        parameters.put("data", GsonHelper.toJson(reqData));
        parameters.put("sign", OpenApiUtils.getInstance().sign(api, parameters));
        String result = HttpManger.getSingleton().postString(HttpUrl.BASE_API_URL, parameters);
        BasePagerResponse<ProductCategory> resp = GsonHelper.fromJson(result, new TypeToken<BasePagerResponse<ProductCategory>>() {
        }.getType());
        if (resp.getCode() == BaseResponse.SUCCESS) {
            boolean firstPageFlag = pageNum < 2;
            if (cacheProductCategroy(resp.getData().getList(), firstPageFlag)) {
                notify.setSuccess(true);
                notify.setOperate(DownloadCacheName.PRODUCT_CATEGORY);
                notify.setPager(resp.getData().getPageCount() > 1);
                notify.setPageCount(resp.getData().getPageCount());
                notify.setPageSize(resp.getData().getPageSize());
                notify.setPageNumber(resp.getData().getPageNumber());
                if (resp.getData().getPageCount() > 1) {
                    notify.setMessage(String.format("第%s页%s下载成功......", pageNum, tips));
                } else {
                    notify.setMessage(String.format("%s下载成功......", tips));
                }
            } else {
                notify.setSuccess(false);
                notify.setOperate(DownloadCacheName.PRODUCT_CATEGORY_EXCEPTION);
                notify.setMessage(String.format("下载%s时发生异常", tips));
            }
        } else {
            notify.setSuccess(false);
            notify.setOperate(DownloadCacheName.PRODUCT_CATEGORY_ERROR);
            notify.setMessage(String.format("下载%s时发生异常", tips));
        }
        return notify;
    }

    @Override
    public DownloadNotify fetchProductUnit(int pageNum, int pageSize) {
        String tips = "商品单位";
        DownloadNotify notify = new DownloadNotify();
        notify.setSuccess(false);
        notify.setOperate(DownloadCacheName.PRODUCT_UNIT);
        notify.setPager(false);
        notify.setPageCount(1);
        notify.setPageNumber(pageNum);

        Map<String, Object> parameters = OpenApiUtils.getInstance().newApiParameters();
        parameters.put("name", "product.unit.lists");
        RegistrationCode api = TPUtils.getObject(BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);

        Map<String, Object> reqData = new HashMap<>();
        reqData.put("pageNumber", pageNum);
        reqData.put("pageSize", pageSize);
        parameters.put("data", GsonHelper.toJson(reqData));
        parameters.put("sign", OpenApiUtils.getInstance().sign(api, parameters));
        String result = HttpManger.getSingleton().postString(HttpUrl.BASE_API_URL, parameters);
        BasePagerResponse<ProductUnit> resp = GsonHelper.fromJson(result, new TypeToken<BasePagerResponse<ProductUnit>>() {
        }.getType());
        if (resp.getCode() == BaseResponse.SUCCESS) {
            boolean firstPageFlag = pageNum < 2;
            if (cacheProductUnit(resp.getData().getList(), firstPageFlag)) {
                notify.setSuccess(true);
                notify.setOperate(DownloadCacheName.PRODUCT_UNIT);
                notify.setPager(resp.getData().getPageCount() > 1);
                notify.setPageCount(resp.getData().getPageCount());
                notify.setPageSize(resp.getData().getPageSize());
                notify.setPageNumber(resp.getData().getPageNumber());
                if (resp.getData().getPageCount() > 1) {
                    notify.setMessage(String.format("第%s页%s下载成功......", pageNum, tips));
                } else {
                    notify.setMessage(String.format("%s下载成功......", tips));
                }
            } else {
                notify.setSuccess(false);
                notify.setOperate(DownloadCacheName.PRODUCT_UNIT_EXCEPTION);
                notify.setMessage(String.format("下载%s时发生异常", tips));
            }
        } else {
            notify.setSuccess(false);
            notify.setOperate(DownloadCacheName.PRODUCT_UNIT_ERROR);
            notify.setMessage(String.format("%s：<%s>-<%s>", tips, resp.getCode(), resp.getMsg()));
            LogUtil.e(this, String.format("下载<%s>第<%s>下载出错:%s", tips, pageNum, resp.getMsg()));
        }

        return notify;
    }

    @Override
    public DownloadNotify fetchProduct(int pageNum, int pageSize) {
        String tips = "商品档案";
        DownloadNotify notify = new DownloadNotify();
        notify.setSuccess(false);
        notify.setOperate(DownloadCacheName.PRODUCT);
        notify.setPager(false);
        notify.setPageCount(1);
        notify.setPageNumber(pageNum);

        Map<String, Object> parameters = OpenApiUtils.getInstance().newApiParameters();
        parameters.put("name", "product.lists");
        RegistrationCode api = TPUtils.getObject(BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);

        Map<String, Object> reqData = new HashMap<>();
        reqData.put("pageNumber", pageNum);
        reqData.put("pageSize", pageSize);
        parameters.put("data", GsonHelper.toJson(reqData));
        parameters.put("sign", OpenApiUtils.getInstance().sign(api, parameters));
        String result = HttpManger.getSingleton().postString(HttpUrl.BASE_API_URL, parameters);
        BasePagerResponse<Product> resp = GsonHelper.fromJson(result, new TypeToken<BasePagerResponse<Product>>() {
        }.getType());
        if (resp.getCode() == BaseResponse.SUCCESS) {
            boolean firstPageFlag = pageNum < 2;
            if (cacheProduct(resp.getData().getList(), firstPageFlag)) {
                notify.setSuccess(true);
                notify.setOperate(DownloadCacheName.PRODUCT);
                notify.setPager(resp.getData().getPageCount() > 1);
                notify.setPageCount(resp.getData().getPageCount());
                notify.setPageSize(resp.getData().getPageSize());
                notify.setPageNumber(resp.getData().getPageNumber());
                if (resp.getData().getPageCount() > 1) {
                    notify.setMessage(String.format("第%s页%s下载成功......", pageNum, tips));
                } else {
                    notify.setMessage(String.format("%s下载成功......", tips));
                }

            } else {
                notify.setSuccess(false);
                notify.setOperate(DownloadCacheName.PRODUCT_EXCEPTION);
                notify.setMessage(String.format("下载%s时发生异常", tips));

            }
        } else {
            notify.setSuccess(false);
            notify.setOperate(DownloadCacheName.PRODUCT_ERROR);
            notify.setMessage(String.format("%s：<%s>-<%s>", tips, resp.getCode(), resp.getMsg()));
            LogUtil.e(this, String.format("下载<%s>第<%s>下载出错:%s", tips, pageNum, resp.getMsg()));
        }
        return notify;
    }

    @Override
    public DownloadNotify fetchProductCode(int pageNum, int pageSize) {
        String tips = "商品附加码";
        DownloadNotify notify = new DownloadNotify();
        notify.setSuccess(false);
        notify.setOperate(DownloadCacheName.PRODUCT_CODE);
        notify.setPager(false);
        notify.setPageCount(1);
        notify.setPageNumber(pageNum);
        Map<String, Object> parameters = OpenApiUtils.getInstance().newApiParameters();
        parameters.put("name", "product.code.lists");
        RegistrationCode api = TPUtils.getObject(BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);

        Map<String, Object> reqData = new HashMap<>();
        reqData.put("pageNumber", pageNum);
        reqData.put("pageSize", pageSize);
        parameters.put("data", GsonHelper.toJson(reqData));
        parameters.put("sign", OpenApiUtils.getInstance().sign(api, parameters));
        String result = HttpManger.getSingleton().postString(HttpUrl.BASE_API_URL, parameters);
        BasePagerResponse<ProductCode> resp = GsonHelper.fromJson(result, new TypeToken<BasePagerResponse<ProductCode>>() {
        }.getType());
        if (resp.getCode() == BaseResponse.SUCCESS) {
            boolean firstPageFlag = pageNum < 2;
            if (cacheProductCode(resp.getData().getList(), firstPageFlag)) {
                notify.setSuccess(true);
                notify.setOperate(DownloadCacheName.PRODUCT_CODE);
                notify.setPager(resp.getData().getPageCount() > 1);
                notify.setPageCount(resp.getData().getPageCount());
                notify.setPageSize(resp.getData().getPageSize());
                notify.setPageNumber(resp.getData().getPageNumber());
                if (resp.getData().getPageCount() > 1) {
                    notify.setMessage(String.format("第%s页%s下载成功......", pageNum, tips));
                } else {
                    notify.setMessage(String.format("%s下载成功......", tips));
                }
            } else {
                notify.setSuccess(false);
                notify.setOperate(DownloadCacheName.PRODUCT_CODE_EXCEPTION);
                notify.setMessage(String.format("下载%s时发生异常", tips));
            }
        } else {
            notify.setSuccess(false);
            notify.setOperate(DownloadCacheName.PRODUCT_CODE_ERROR);
            notify.setMessage(String.format("%s：<%s>-<%s>", tips, resp.getCode(), resp.getMsg()));
            LogUtil.e(this, String.format("下载<%s>第<%s>下载出错:%s", tips, pageNum, resp.getMsg()));
        }

        return notify;
    }

    @Override
    public DownloadNotify fetchProductContact(int pageNum, int pageSize) {
        String tips = "商品关联信息";
        DownloadNotify notify = new DownloadNotify();
        notify.setSuccess(false);
        notify.setOperate(DownloadCacheName.PRODUCT_CONTACT);
        notify.setPager(false);
        notify.setPageCount(1);
        notify.setPageNumber(pageNum);

        Map<String, Object> parameters = OpenApiUtils.getInstance().newApiParameters();
        parameters.put("name", "product.contact.lists");
        RegistrationCode api = TPUtils.getObject(BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);

        Map<String, Object> reqData = new HashMap<>();
        reqData.put("pageNumber", pageNum);
        reqData.put("pageSize", pageSize);
        parameters.put("data", GsonHelper.toJson(reqData));
        parameters.put("sign", OpenApiUtils.getInstance().sign(api, parameters));
        String result = HttpManger.getSingleton().postString(HttpUrl.BASE_API_URL, parameters);
        BasePagerResponse<ProductContact> resp = GsonHelper.fromJson(result, new TypeToken<BasePagerResponse<ProductContact>>() {
        }.getType());
        if (resp.getCode() == BaseResponse.SUCCESS) {
            boolean firstPageFlag = pageNum < 2;
            if (cacheProductContact(resp.getData().getList(), firstPageFlag)) {
                notify.setSuccess(true);
                notify.setOperate(DownloadCacheName.PRODUCT_CONTACT);
                notify.setPager(resp.getData().getPageCount() > 1);
                notify.setPageCount(resp.getData().getPageCount());
                notify.setPageSize(resp.getData().getPageSize());
                notify.setPageNumber(resp.getData().getPageNumber());
                if (resp.getData().getPageCount() > 1) {
                    notify.setMessage(String.format("第%s页%s下载成功......", pageNum, tips));
                } else {
                    notify.setMessage(String.format("%s下载成功......", tips));
                }
            } else {
                notify.setSuccess(false);
                notify.setOperate(DownloadCacheName.PRODUCT_CONTACT_EXCEPTION);
                notify.setMessage(String.format("下载%s时发生异常", tips));
            }
        } else {
            notify.setSuccess(false);
            notify.setOperate(DownloadCacheName.PRODUCT_CONTACT_ERROR);
            notify.setMessage(String.format("%s：<%s>-<%s>", tips, resp.getCode(), resp.getMsg()));
            LogUtil.e(this, String.format("下载<%s>第<%s>下载出错:%s", tips, pageNum, resp.getMsg()));
        }

        return notify;
    }

    @Override
    public DownloadNotify fetchProductSpec(int pageNum, int pageSize) {
        String tips = "商品规格信息";
        DownloadNotify notify = new DownloadNotify();
        notify.setSuccess(false);
        notify.setOperate(DownloadCacheName.PRODUCT_SPEC);
        notify.setPager(false);
        notify.setPageCount(1);
        notify.setPageNumber(pageNum);

        Map<String, Object> parameters = OpenApiUtils.getInstance().newApiParameters();
        parameters.put("name", "product.spec.lists");
        RegistrationCode api = TPUtils.getObject(BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);

        Map<String, Object> reqData = new HashMap<>();
        reqData.put("pageNumber", pageNum);
        reqData.put("pageSize", pageSize);
        parameters.put("data", GsonHelper.toJson(reqData));
        parameters.put("sign", OpenApiUtils.getInstance().sign(api, parameters));
        String result = HttpManger.getSingleton().postString(HttpUrl.BASE_API_URL, parameters);
        BasePagerResponse<ProductSpec> resp = GsonHelper.fromJson(result, new TypeToken<BasePagerResponse<ProductSpec>>() {
        }.getType());
        if (resp.getCode() == BaseResponse.SUCCESS) {
            boolean firstPageFlag = pageNum < 2;
            if (cacheProductSpec(resp.getData().getList(), firstPageFlag)) {
                notify.setSuccess(true);
                notify.setOperate(DownloadCacheName.PRODUCT_SPEC);
                notify.setPager(resp.getData().getPageCount() > 1);
                notify.setPageCount(resp.getData().getPageCount());
                notify.setPageSize(resp.getData().getPageSize());
                notify.setPageNumber(resp.getData().getPageNumber());
                if (resp.getData().getPageCount() > 1) {
                    notify.setMessage(String.format("第%s页%s下载成功......", pageNum, tips));
                } else {
                    notify.setMessage(String.format("%s下载成功......", tips));
                }
            } else {
                notify.setSuccess(false);
                notify.setOperate(DownloadCacheName.PRODUCT_SPEC_EXCEPTION);
                notify.setMessage(String.format("下载%s时发生异常", tips));
            }
        } else {
            notify.setSuccess(false);
            notify.setOperate(DownloadCacheName.PRODUCT_SPEC_ERROR);
            notify.setMessage(String.format("%s：<%s>-<%s>", tips, resp.getCode(), resp.getMsg()));
            LogUtil.e(this, String.format("下载<%s>第<%s>下载出错:%s", tips, pageNum, resp.getMsg()));
        }

        return notify;
    }

    @Override
    public DownloadNotify fetchStoreProduct(int pageNum, int pageSize) {
        String tips = "门店关联商品";
        DownloadNotify notify = new DownloadNotify();
        notify.setSuccess(false);
        notify.setOperate(DownloadCacheName.STORE_PRODUCT);
        notify.setPager(false);
        notify.setPageCount(1);
        notify.setPageNumber(pageNum);

        Map<String, Object> parameters = OpenApiUtils.getInstance().newApiParameters();
        parameters.put("name", "store.product.lists");
        RegistrationCode api = TPUtils.getObject(BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);

        Map<String, Object> reqData = new HashMap<>();
        reqData.put("storeId", api.getStoreId());
        reqData.put("pageNumber", pageNum);
        reqData.put("pageSize", pageSize);
        parameters.put("data", GsonHelper.toJson(reqData));
        parameters.put("sign", OpenApiUtils.getInstance().sign(api, parameters));
        String result = HttpManger.getSingleton().postString(HttpUrl.BASE_API_URL, parameters);
        BasePagerResponse<StoreProduct> resp = GsonHelper.fromJson(result, new TypeToken<BasePagerResponse<StoreProduct>>() {
        }.getType());
        if (resp.getCode() == BaseResponse.SUCCESS) {
            boolean firstPageFlag = pageNum < 2;
            if (cacheStoreProduct(resp.getData().getList(), firstPageFlag)) {
                notify.setSuccess(true);
                notify.setOperate(DownloadCacheName.STORE_PRODUCT);
                notify.setPager(resp.getData().getPageCount() > 1);
                notify.setPageCount(resp.getData().getPageCount());
                notify.setPageSize(resp.getData().getPageSize());
                notify.setPageNumber(resp.getData().getPageNumber());
                if (resp.getData().getPageCount() > 1) {
                    notify.setMessage(String.format("第%s页%s下载成功......", pageNum, tips));
                } else {
                    notify.setMessage(String.format("%s下载成功......", tips));
                }

            } else {
                notify.setSuccess(false);
                notify.setOperate(DownloadCacheName.STORE_PRODUCT_EXCEPTION);
                notify.setMessage(String.format("下载%s时发生异常", tips));
            }
        } else {
            notify.setSuccess(false);
            notify.setOperate(DownloadCacheName.STORE_PRODUCT_ERROR);
            notify.setMessage(String.format("%s：<%s>-<%s>", tips, resp.getCode(), resp.getMsg()));
            LogUtil.e(this, String.format("下载<%s>第<%s>下载出错:%s", tips, pageNum, resp.getMsg()));
        }

        return notify;
    }

    @Override
    public DownloadNotify fetchWorker(int pageNum, int pageSize) {
        String tips = "门店员工";
        DownloadNotify notify = new DownloadNotify();
        notify.setSuccess(false);
        notify.setOperate(DownloadCacheName.WORKER);
        notify.setPager(false);
        notify.setPageCount(1);
        notify.setPageNumber(pageNum);
        Map<String, Object> parameters = OpenApiUtils.getInstance().newApiParameters();
        parameters.put("name", "worker.lists");
        RegistrationCode api = TPUtils.getObject(BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);

        Map<String, Object> reqData = new HashMap<>();
        reqData.put("pageNumber", pageNum);
        reqData.put("pageSize", pageSize);
        reqData.put("storeId", api.getStoreId());
        parameters.put("data", GsonHelper.toJson(reqData));
        parameters.put("sign", OpenApiUtils.getInstance().sign(api, parameters));
        String result = HttpManger.getSingleton().postString(HttpUrl.BASE_API_URL, parameters);
        BasePagerResponse<Worker> resp = GsonHelper.fromJson(result, new TypeToken<BasePagerResponse<Worker>>() {
        }.getType());
        if (resp.getCode() == BaseResponse.SUCCESS) {
            boolean firstPageFlag = pageNum < 2;
            if (cacheWorker(resp.getData().getList(), firstPageFlag)) {
                notify.setSuccess(true);
                notify.setOperate(DownloadCacheName.WORKER);
                notify.setPager(resp.getData().getPageCount() > 1);
                notify.setPageCount(resp.getData().getPageCount());
                notify.setPageSize(resp.getData().getPageSize());
                notify.setPageNumber(resp.getData().getPageNumber());
                if (resp.getData().getPageCount() > 1) {
                    notify.setMessage(String.format("第%s页%s下载成功......", pageNum, tips));
                } else {
                    notify.setMessage(String.format("%s下载成功......", tips));
                }

            } else {
                notify.setSuccess(false);
                notify.setOperate(DownloadCacheName.WORKER_EXCEPTION);
                notify.setMessage(String.format("下载%s时发生异常", tips));
            }
        } else {
            notify.setSuccess(false);
            notify.setOperate(DownloadCacheName.WORKER_ERROR);
            notify.setMessage(String.format("%s：<%s>-<%s>", tips, resp.getCode(), resp.getMsg()));
            LogUtil.e(this, String.format("下载<%s>第<%s>下载出错:%s", tips, pageNum, resp.getMsg()));
        }

        return notify;
    }

//    @Override
//    public DownloadNotify fetchWorkerData(int pageNum, int pageSize) {
//        String tips = "员工权限列表";
//        DownloadNotify notify = new DownloadNotify();
//        notify.setSuccess(false);
//        notify.setOperate(DownloadCacheName.WORKER_DATA);
//        notify.setPager(false);
//        notify.setPageCount(1);
//        notify.setPageNumber(pageNum);
//        Map<String, Object> parameters = OpenApiUtils.getInstance().newApiParameters();
//        parameters.put("name", "worker.rule.list");
//        RegistrationCode api = TPUtils.getObject(BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);
//
//        Map<String, Object> reqData = new HashMap<>();
//        reqData.put("pageNumber", pageNum);
//        reqData.put("pageSize", pageSize);
//        reqData.put("storeId", api.getStoreId());
//        parameters.put("data", GsonHelper.toJson(reqData));
//        parameters.put("sign", OpenApiUtils.getInstance().sign(api, parameters));
//        String result = HttpManger.getSingleton().postString(HttpUrl.BASE_API_URL, parameters);
//        BasePagerResponse<WorkData> resp = GsonHelper.fromJson(result, new TypeToken<BasePagerResponse<WorkData>>() {
//        }.getType());
//        if (resp.getCode() == BaseResponse.SUCCESS) {
//            boolean firstPageFlag = pageNum < 2;
//            if (cacheWorkerData(resp.getData().getList(), firstPageFlag)) {
//                notify.setSuccess(true);
//                notify.setOperate(DownloadCacheName.WORKER_DATA);
//                notify.setPager(resp.getData().getPageCount() > 1);
//                notify.setPageCount(resp.getData().getPageCount());
//                notify.setPageSize(resp.getData().getPageSize());
//                notify.setPageNumber(resp.getData().getPageNumber());
//                if (resp.getData().getPageCount() > 1) {
//                    notify.setMessage(String.format("第%s页%s下载成功......", pageNum, tips));
//                } else {
//                    notify.setMessage(String.format("%s下载成功......", tips));
//                }
//            } else {
//                notify.setSuccess(false);
//                notify.setOperate(DownloadCacheName.WORKER__DATA_EXCEPTION);
//                notify.setMessage(String.format("下载%s时发生异常", tips));
//            }
//        } else {
//            notify.setSuccess(false);
//            notify.setOperate(DownloadCacheName.WORKER_DATA_ERROR);
//            notify.setMessage(String.format("%s：<%s>-<%s>", tips, resp.getCode(), resp.getMsg()));
//            LogUtil.e(this, String.format("下载<%s>第<%s>下载出错:%s", tips, pageNum, resp.getMsg()));
//        }
//
//        return notify;
//    }
//
//    @Override
//    public DownloadNotify fetchWorkerModule(int pageNum, int pageSize) {
//        String tips = "员工POS模块权限";
//        DownloadNotify notify = new DownloadNotify();
//        notify.setSuccess(false);
//        notify.setOperate(DownloadCacheName.WORKER_POSMODULE);
//        notify.setPager(false);
//        notify.setPageCount(1);
//        notify.setPageNumber(pageNum);
//        Map<String, Object> parameters = OpenApiUtils.getInstance().newApiParameters();
//        parameters.put("name", "worker.posmodule.lists");
//        RegistrationCode api = TPUtils.getObject(BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);
//
//        Map<String, Object> reqData = new HashMap<>();
//        reqData.put("pageNumber", pageNum);
//        reqData.put("pageSize", pageSize);
//        reqData.put("storeId", api.getStoreId());
//        parameters.put("data", GsonHelper.toJson(reqData));
//        parameters.put("sign", OpenApiUtils.getInstance().sign(api, parameters));
//        String result = HttpManger.getSingleton().postString(HttpUrl.BASE_API_URL, parameters);
//        BasePagerResponse<WorkModule> resp = GsonHelper.fromJson(result, new TypeToken<BasePagerResponse<WorkModule>>() {
//        }.getType());
//        if (resp.getCode() == BaseResponse.SUCCESS) {
//            boolean firstPageFlag = pageNum < 2;
//            if (cacheWorkerModule(resp.getData().getList(), firstPageFlag)) {
//                notify.setSuccess(true);
//                notify.setOperate(DownloadCacheName.WORKER_POSMODULE);
//                notify.setPager(resp.getData().getPageCount() > 1);
//                notify.setPageCount(resp.getData().getPageCount());
//                notify.setPageSize(resp.getData().getPageSize());
//                notify.setPageNumber(resp.getData().getPageNumber());
//                if (resp.getData().getPageCount() > 1) {
//                    notify.setMessage(String.format("第%s页%s下载成功......", pageNum, tips));
//                } else {
//                    notify.setMessage(String.format("%s下载成功......", tips));
//                }
//
//            } else {
//                notify.setSuccess(false);
//                notify.setOperate(DownloadCacheName.WORKER_POSMODULE_EXCEPTION);
//                notify.setMessage(String.format("下载%s时发生异常", tips));
//            }
//        } else {
//            notify.setSuccess(false);
//            notify.setOperate(DownloadCacheName.WORKER_POSMODULE_ERROR);
//            notify.setMessage(String.format("%s：<%s>-<%s>", tips, resp.getCode(), resp.getMsg()));
//            LogUtil.e(this, String.format("下载<%s>第<%s>下载出错:%s", tips, pageNum, resp.getMsg()));
//        }
//
//        return notify;
//    }
//
//    @Override
//    public DownloadNotify fetchWorkerRole(int pageNum, int pageSize) {
//        String tips = "收银员角色";
//        DownloadNotify notify = new DownloadNotify();
//        notify.setSuccess(false);
//        notify.setOperate(DownloadCacheName.WORKER_ROLE);
//        notify.setPager(false);
//        notify.setPageCount(1);
//        notify.setPageNumber(pageNum);
//        Map<String, Object> parameters = OpenApiUtils.getInstance().newApiParameters();
//        parameters.put("name", "worker.pos.role.lists");
//        RegistrationCode api = TPUtils.getObject(BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);
//
//        Map<String, Object> reqData = new HashMap<>();
//        reqData.put("pageNumber", pageNum);
//        reqData.put("pageSize", pageSize);
//        reqData.put("storeId", api.getStoreId());
//        parameters.put("data", GsonHelper.toJson(reqData));
//        parameters.put("sign", OpenApiUtils.getInstance().sign(api, parameters));
//        String result = HttpManger.getSingleton().postString(HttpUrl.BASE_API_URL, parameters);
//        BasePagerResponse<WorkRole> resp = GsonHelper.fromJson(result, new TypeToken<BasePagerResponse<WorkRole>>() {
//        }.getType());
//        if (resp.getCode() == BaseResponse.SUCCESS) {
//            boolean firstPageFlag = pageNum < 2;
//            if (cacheWorkerRole(resp.getData().getList(), firstPageFlag)) {
//                notify.setSuccess(true);
//                notify.setOperate(DownloadCacheName.WORKER_ROLE);
//                notify.setPager(resp.getData().getPageCount() > 1);
//                notify.setPageCount(resp.getData().getPageCount());
//                notify.setPageSize(resp.getData().getPageSize());
//                notify.setPageNumber(resp.getData().getPageNumber());
//                if (resp.getData().getPageCount() > 1) {
//                    notify.setMessage(String.format("第%s页%s下载成功......", pageNum, tips));
//                } else {
//                    notify.setMessage(String.format("%s下载成功......", tips));
//                }
//
//            } else {
//                notify.setSuccess(false);
//                notify.setOperate(DownloadCacheName.WORKER_ROLE_EXCEPTION);
//                notify.setMessage(String.format("下载%s时发生异常", tips));
//            }
//        } else {
//            notify.setSuccess(false);
//            notify.setOperate(DownloadCacheName.WORKER_ROLE_ERROR);
//            notify.setMessage(String.format("%s：<%s>-<%s>", tips, resp.getCode(), resp.getMsg()));
//            LogUtil.e(this, String.format("下载<%s>第<%s>下载出错:%s", tips, pageNum, resp.getMsg()));
//        }
//
//        return notify;
//    }
//
//    @Override   //todo
//    public DownloadNotify fetchShortCuts(int pageNum, int pageSize) {
//        return null;
//    }

    @Override
    public DownloadNotify fetchSupplier(int pageNum, int pageSize) {
        String tips = "供应商";
        DownloadNotify notify = new DownloadNotify();
        notify.setSuccess(false);
        notify.setOperate(DownloadCacheName.SUPPLIER);
        notify.setPager(false);
        notify.setPageCount(1);
        notify.setPageNumber(pageNum);
        Map<String, Object> parameters = OpenApiUtils.getInstance().newApiParameters();
        parameters.put("name", "supplier.lists");
        RegistrationCode api = TPUtils.getObject(BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);

        Map<String, Object> reqData = new HashMap<>();
        reqData.put("pageNumber", pageNum);
        reqData.put("pageSize", pageSize);
        reqData.put("storeId", api.getStoreId());
        parameters.put("data", GsonHelper.toJson(reqData));
        parameters.put("sign", OpenApiUtils.getInstance().sign(api, parameters));
        String result = HttpManger.getSingleton().postString(HttpUrl.BASE_API_URL, parameters);
        BasePagerResponse<Supplier> resp = GsonHelper.fromJson(result, new TypeToken<BasePagerResponse<Supplier>>() {
        }.getType());
        if (resp.getCode() == BaseResponse.SUCCESS) {
            boolean firstPageFlag = pageNum < 2;
            if (cacheSupplier(resp.getData().getList(), firstPageFlag)) {
                notify.setSuccess(true);
                notify.setOperate(DownloadCacheName.SUPPLIER);
                notify.setPager(resp.getData().getPageCount() > 1);
                notify.setPageCount(resp.getData().getPageCount());
                notify.setPageSize(resp.getData().getPageSize());
                notify.setPageNumber(resp.getData().getPageNumber());
                if (resp.getData().getPageCount() > 1) {
                    notify.setMessage(String.format("第%s页%s下载成功......", pageNum, tips));
                } else {
                    notify.setMessage(String.format("%s下载成功......", tips));
                }
            } else {
                notify.setSuccess(false);
                notify.setOperate(DownloadCacheName.SUPPLIER_ERROR);
                notify.setMessage(String.format("缓存%s第%s页发生错误", tips, pageNum));

                LogUtil.e(this, notify.getMessage());
            }
        } else {
            notify.setSuccess(false);
            notify.setOperate(DownloadCacheName.SUPPLIER_ERROR);
            notify.setMessage(String.format("%s：<%s>-<%s>", tips, resp.getCode(), resp.getMsg()));
            LogUtil.e(this, String.format("下载<%s>第<%s>下载出错:%s", tips, pageNum, resp.getMsg()));
        }

        return notify;
    }

    @Override
    public DownloadNotify fetchPayMode(int pageNum, int pageSize) {
        String tips = "付款方式";
        DownloadNotify notify = new DownloadNotify();
        notify.setSuccess(false);
        notify.setOperate(DownloadCacheName.PAYMODE);
        notify.setPager(false);
        notify.setPageCount(1);
        notify.setPageNumber(pageNum);

        Map<String, Object> parameters = OpenApiUtils.getInstance().newApiParameters();
        parameters.put("name", "payMode.lists");
        RegistrationCode api = TPUtils.getObject(BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);

        Map<String, Object> reqData = new HashMap<>();
        reqData.put("pageNumber", pageNum);
        reqData.put("pageSize", pageSize);
        parameters.put("data", GsonHelper.toJson(reqData));
        parameters.put("sign", OpenApiUtils.getInstance().sign(api, parameters));
        String result = HttpManger.getSingleton().postString(HttpUrl.BASE_API_URL, parameters);
        BasePagerResponse<PayMode> resp = GsonHelper.fromJson(result, new TypeToken<BasePagerResponse<PayMode>>() {
        }.getType());
        if (resp.getCode() == BaseResponse.SUCCESS) {
            boolean firstPageFlag = pageNum < 2;
            if (cachePayMode(resp.getData().getList(), firstPageFlag)) {
                notify.setSuccess(true);
                notify.setOperate(DownloadCacheName.PAYMODE);
                notify.setPager(resp.getData().getPageCount() > 1);
                notify.setPageCount(resp.getData().getPageCount());
                notify.setPageSize(resp.getData().getPageSize());
                notify.setPageNumber(resp.getData().getPageNumber());
                if (resp.getData().getPageCount() > 1) {
                    notify.setMessage(String.format("第%s页%s下载成功......", pageNum, tips));
                } else {
                    notify.setMessage(String.format("%s下载成功......", tips));
                }
            } else {
                notify.setSuccess(false);
                notify.setOperate(DownloadCacheName.PAYMODE_ERROR);
                notify.setMessage(String.format("缓存%s第%s页发生错误", tips, pageNum));

                LogUtil.e(this, notify.getMessage());
            }
        } else {
            notify.setSuccess(false);
            notify.setOperate(DownloadCacheName.PAYMODE_ERROR);
            notify.setMessage(String.format("%s：<%s>-<%s>", tips, resp.getCode(), resp.getMsg()));
            LogUtil.e(this, String.format("下载<%s>第<%s>下载出错:%s", tips, pageNum, resp.getMsg()));
        }

        return notify;
    }

    @Override
    public DownloadNotify fetchStoreInfo(int pageNum, int pageSize) {
        String tips = "门店列表";
        DownloadNotify notify = new DownloadNotify();
        notify.setSuccess(false);
        notify.setOperate(DownloadCacheName.STORE_INFO);
        notify.setPager(false);
        notify.setPageCount(1);
        notify.setPageNumber(pageNum);

        Map<String, Object> parameters = OpenApiUtils.getInstance().newApiParameters();
        parameters.put("name", "store.list");
        RegistrationCode api = TPUtils.getObject(BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);

        Map<String, Object> reqData = new HashMap<>();
        reqData.put("storeId", api.getStoreId());
        reqData.put("pageNumber", pageNum);
        reqData.put("pageSize", pageSize);
        parameters.put("data", GsonHelper.toJson(reqData));
        parameters.put("sign", OpenApiUtils.getInstance().sign(api, parameters));
        String result = HttpManger.getSingleton().postString(HttpUrl.BASE_API_URL, parameters);
        BasePagerResponse<StoreInfo> resp = GsonHelper.fromJson(result, new TypeToken<BasePagerResponse<StoreInfo>>() {
        }.getType());
        if (resp.getCode() == BaseResponse.SUCCESS) {
            boolean firstPageFlag = pageNum < 2;
            if (cacheStoreInfo(resp.getData().getList(), firstPageFlag)) {
                notify.setSuccess(true);
                notify.setOperate(DownloadCacheName.STORE_INFO);
                notify.setPager(resp.getData().getPageCount() > 1);
                notify.setPageCount(resp.getData().getPageCount());
                notify.setPageSize(resp.getData().getPageSize());
                notify.setPageNumber(resp.getData().getPageNumber());
                if (resp.getData().getPageCount() > 1) {
                    notify.setMessage(String.format("第%s页%s下载成功......", pageNum, tips));
                } else {
                    notify.setMessage(String.format("%s下载成功......", tips));
                }
            } else {
                notify.setSuccess(false);
                notify.setOperate(DownloadCacheName.STORE_INFO_ERROR);
                notify.setMessage(String.format("缓存%s第%s页发生错误", tips, pageNum));

                LogUtil.e(this, notify.getMessage());
            }
        } else {
            notify.setSuccess(false);
            notify.setOperate(DownloadCacheName.STORE_INFO_ERROR);
            notify.setMessage(String.format("%s：<%s>-<%s>", tips, resp.getCode(), resp.getMsg()));
            LogUtil.e(this, String.format("下载<%s>第<%s>下载出错:%s", tips, pageNum, resp.getMsg()));
        }

        return notify;
    }

    @Override
    public DownloadNotify fetchMemberSetting() {
        String tips = "会员设置";
        DownloadNotify notify = new DownloadNotify();
        notify.setSuccess(false);
        notify.setOperate(DownloadCacheName.MEMBER_SETTING);
        notify.setPager(false);

        Map<String, Object> parameters = OpenApiUtils.getInstance().newApiParameters();
        parameters.put("name", "member.setting");
        RegistrationCode api = TPUtils.getObject(BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);

        Map<String, Object> reqData = new HashMap<>();
        reqData.put("storeId", api.getStoreId());
        parameters.put("data", GsonHelper.toJson(reqData));
        parameters.put("sign", OpenApiUtils.getInstance().sign(api, parameters));
        String result = HttpManger.getSingleton().postString(HttpUrl.BASE_API_URL, parameters);
        BaseListResponse<LineSystemSet> resp = GsonHelper.fromJson(result, new TypeToken<BaseListResponse<LineSystemSet>>() {
        }.getType());
        if (resp.getCode() == BaseResponse.SUCCESS) {
            if (cacheMemberSetting(resp.getData())) {
                notify.setSuccess(true);
                notify.setOperate(DownloadCacheName.MEMBER_SETTING);
                notify.setMessage(String.format("%s下载成功......", tips));
            } else {
                notify.setSuccess(false);
                notify.setOperate(DownloadCacheName.MEMBER_SETTING_EXCEPTION);
                notify.setMessage(String.format("下载%s时发生异常", tips));
                LogUtil.e(this, String.format("下载%s时发生异常", tips));
            }
        } else {
            notify.setSuccess(false);
            notify.setOperate(DownloadCacheName.MEMBER_SETTING_ERROR);
            notify.setMessage(String.format("%s：<%s>-<%s>", tips, resp.getCode(), resp.getMsg()));
            LogUtil.e(this, String.format("下载<%s>下载出错:%s", tips, resp.getMsg()));
        }

        return notify;
    }

    @Override
    public DownloadNotify fetchMemberLevel() {
        String tips = "会员等级";
        DownloadNotify notify = new DownloadNotify();
        notify.setSuccess(false);
        notify.setOperate(DownloadCacheName.MEMBER_LEVEL);
        notify.setPager(false);

        Map<String, Object> parameters = OpenApiUtils.getInstance().newApiParameters();
        parameters.put("name", "member.level");
        RegistrationCode api = TPUtils.getObject(BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);

        Map<String, Object> reqData = new HashMap<>();
        reqData.put("storeId", api.getStoreId());
        parameters.put("data", GsonHelper.toJson(reqData));
        parameters.put("sign", OpenApiUtils.getInstance().sign(api, parameters));
        String result = HttpManger.getSingleton().postString(HttpUrl.BASE_API_URL, parameters);
        BaseListResponse<MemberLevel> resp = GsonHelper.fromJson(result, new TypeToken<BaseListResponse<MemberLevel>>() {
        }.getType());
        if (resp.getCode() == BaseResponse.SUCCESS) {
            if (cacheMemberLevel(resp.getData())) {
                notify.setSuccess(true);
                notify.setOperate(DownloadCacheName.MEMBER_LEVEL);
                notify.setMessage(String.format("%s下载成功......", tips));
                LogUtil.i(this, notify.getMessage());
            } else {
                notify.setSuccess(false);
                notify.setOperate(DownloadCacheName.MEMBER_LEVEL_EXCEPTION);
                notify.setMessage(String.format("下载%s时发生异常", tips));
            }
        } else {
            notify.setSuccess(false);
            notify.setOperate(DownloadCacheName.MEMBER_LEVEL_ERROR);
            notify.setMessage(String.format("%s：<%s>-<%s>", tips, resp.getCode(), resp.getMsg()));
            LogUtil.e(this, String.format("下载<%s>下载出错:%s", tips, resp.getMsg()));
        }

        return notify;
    }

    @Override
    public DownloadNotify fetchMemberLevelCategoryDiscount() {
        String tips = "会员等级商品类别折扣";
        DownloadNotify notify = new DownloadNotify();
        notify.setSuccess(false);
        notify.setOperate(DownloadCacheName.MEMBER_LEVEL_CATEGORY_DISCOUNT);
        notify.setPager(false);

        Map<String, Object> parameters = OpenApiUtils.getInstance().newApiParameters();
        parameters.put("name", "member.level.category.discount");
        RegistrationCode api = TPUtils.getObject(BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);

        Map<String, Object> reqData = new HashMap<>();
        reqData.put("storeId", api.getStoreId());
        parameters.put("data", GsonHelper.toJson(reqData));
        parameters.put("sign", OpenApiUtils.getInstance().sign(api, parameters));
        String result = HttpManger.getSingleton().postString(HttpUrl.BASE_API_URL, parameters);
        BaseListResponse<MemberLevelCategoryDiscount> resp = GsonHelper.fromJson(result, new TypeToken<BaseListResponse<MemberLevelCategoryDiscount>>() {
        }.getType());
        if (resp.getCode() == BaseResponse.SUCCESS) {
            if (cacheMemberLevelCategoryDiscount(resp.getData())) {
                notify.setSuccess(true);
                notify.setOperate(DownloadCacheName.MEMBER_LEVEL_CATEGORY_DISCOUNT);
                notify.setMessage(String.format("%s下载成功......", tips));
                LogUtil.i(this, notify.getMessage());
            } else {
                notify.setSuccess(false);
                notify.setOperate(DownloadCacheName.MEMBER_LEVEL_CATEGORY_DISCOUNT_EXCEPTION);
                notify.setMessage(String.format("下载%s时发生异常", tips));

                LogUtil.e(this, tips + "下载发生异常");
            }
        } else {
            notify.setSuccess(false);
            notify.setOperate(DownloadCacheName.MEMBER_LEVEL_CATEGORY_DISCOUNT_ERROR);
            notify.setMessage(String.format("%s：<%s>-<%s>", tips, resp.getCode(), resp.getMsg()));
            LogUtil.e(this, String.format("下载<%s>下载出错:%s", tips, resp.getMsg()));
        }
        return notify;
    }

    @Override
    public DownloadNotify fetchMemberPointRule() {
        String tips = "会员积分规则";
        DownloadNotify notify = new DownloadNotify();
        notify.setSuccess(false);
        notify.setOperate(DownloadCacheName.MEMBER_POINT_RULE);
        notify.setPager(false);

        Map<String, Object> parameters = OpenApiUtils.getInstance().newApiParameters();
        parameters.put("name", "member.point.rule");
        RegistrationCode api = TPUtils.getObject(BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);

        Map<String, Object> reqData = new HashMap<>();
        reqData.put("storeId", api.getStoreId());
        parameters.put("data", GsonHelper.toJson(reqData));
        parameters.put("sign", OpenApiUtils.getInstance().sign(api, parameters));
        String result = HttpManger.getSingleton().postString(HttpUrl.BASE_API_URL, parameters);
        BaseListResponse<MemberPointRule> resp = GsonHelper.fromJson(result, new TypeToken<BaseListResponse<MemberPointRule>>() {
        }.getType());
        if (resp.getCode() == BaseResponse.SUCCESS) {
            if (cacheMemberPointRule(resp.getData())) {
                notify.setSuccess(true);
                notify.setOperate(DownloadCacheName.MEMBER_POINT_RULE);
                notify.setMessage(String.format("%s下载成功......", tips));
                LogUtil.i(this, notify.getMessage());
            } else {
                notify.setSuccess(false);
                notify.setOperate(DownloadCacheName.MEMBER_POINT_RULE_EXCEPTION);
                notify.setMessage(String.format("下载%s时发生异常", tips));

                LogUtil.e(this, tips + "下载发生异常");
            }
        } else {
            notify.setSuccess(false);
            notify.setOperate(DownloadCacheName.MEMBER_POINT_RULE_ERROR);
            notify.setMessage(String.format("%s：<%s>-<%s>", tips, resp.getCode(), resp.getMsg()));
            LogUtil.e(this, String.format("下载<%s>下载出错:%s", tips, resp.getMsg()));
        }

        return notify;
    }

    @Override
    public DownloadNotify fetchMemberPointRuleCategory() {
        String tips = "会员积分规则-分类";
        DownloadNotify notify = new DownloadNotify();
        notify.setSuccess(false);
        notify.setOperate(DownloadCacheName.MEMBER_POINT_RULE_CATEGORY);
        notify.setPager(false);

        Map<String, Object> parameters = OpenApiUtils.getInstance().newApiParameters();
        parameters.put("name", "member.point.rule.category");
        RegistrationCode api = TPUtils.getObject(BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);

        Map<String, Object> reqData = new HashMap<>();
        reqData.put("storeId", api.getStoreId());
        parameters.put("data", GsonHelper.toJson(reqData));
        parameters.put("sign", OpenApiUtils.getInstance().sign(api, parameters));
        String result = HttpManger.getSingleton().postString(HttpUrl.BASE_API_URL, parameters);
        BaseListResponse<MemberPointRuleCategory> resp = GsonHelper.fromJson(result, new TypeToken<BaseListResponse<MemberPointRuleCategory>>() {
        }.getType());
        if (resp.getCode() == BaseResponse.SUCCESS) {
            if (cacheMemberPointRuleCategroy(resp.getData())) {
                notify.setSuccess(true);
                notify.setOperate(DownloadCacheName.MEMBER_POINT_RULE_CATEGORY);
                notify.setMessage(String.format("%s下载成功......", tips));
                LogUtil.i(this, notify.getMessage());
            } else {
                notify.setSuccess(false);
                notify.setOperate(DownloadCacheName.MEMBER_POINT_RULE_CATEGORY_EXCEPTION);
                notify.setMessage(String.format("下载%s时发生异常", tips));

                LogUtil.e(this, tips + "下载发生异常");
            }
        } else {
            notify.setSuccess(false);
            notify.setOperate(DownloadCacheName.MEMBER_POINT_RULE_CATEGORY_ERROR);
            notify.setMessage(String.format("%s：<%s>-<%s>", tips, resp.getCode(), resp.getMsg()));
            LogUtil.e(this, String.format("下载<%s>下载出错:%s", tips, resp.getMsg()));
        }

        return notify;
    }

    @Override
    public DownloadNotify fetchMemberPointRuleBrand() {
        String tips = "会员积分规则-品牌";
        DownloadNotify notify = new DownloadNotify();
        notify.setSuccess(false);
        notify.setOperate(DownloadCacheName.MEMBER_POINT_RULE_BRAND);
        notify.setPager(false);
        Map<String, Object> parameters = OpenApiUtils.getInstance().newApiParameters();
        parameters.put("name", "member.point.rule.brand");
        RegistrationCode api = TPUtils.getObject(BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);

        Map<String, Object> reqData = new HashMap<>();
        reqData.put("storeId", api.getStoreId());
        parameters.put("data", GsonHelper.toJson(reqData));
        parameters.put("sign", OpenApiUtils.getInstance().sign(api, parameters));
        String result = HttpManger.getSingleton().postString(HttpUrl.BASE_API_URL, parameters);
        BaseListResponse<MemberPointRuleBrand> resp = GsonHelper.fromJson(result, new TypeToken<BaseListResponse<MemberPointRuleBrand>>() {
        }.getType());
        if (resp.getCode() == BaseResponse.SUCCESS) {
            if (cacheMemberPointRuleBrand(resp.getData())) {
                notify.setSuccess(true);
                notify.setOperate(DownloadCacheName.MEMBER_POINT_RULE_BRAND);
                notify.setMessage(String.format("%s下载成功......", tips));
                LogUtil.i(this, notify.getMessage());
            } else {
                notify.setSuccess(false);
                notify.setOperate(DownloadCacheName.MEMBER_POINT_RULE_BRAND_EXCEPTION);
                notify.setMessage(String.format("下载%s时发生异常", tips));

                LogUtil.e(this, tips + "下载发生异常");
            }
        } else {
            notify.setSuccess(false);
            notify.setOperate(DownloadCacheName.MEMBER_POINT_RULE_BRAND_ERROR);
            notify.setMessage(String.format("%s：<%s>-<%s>", tips, resp.getCode(), resp.getMsg()));
            LogUtil.e(this, String.format("下载<%s>下载出错:%s", tips, resp.getMsg()));
        }

        return notify;
    }

    @Override
    public DownloadNotify fetchPaymentParameter() {
        String tips = "移动支付设置";
        DownloadNotify notify = new DownloadNotify();
        notify.setSuccess(false);
        notify.setOperate(DownloadCacheName.PAY_SETTING);
        notify.setPager(false);

        Map<String, Object> parameters = OpenApiUtils.getInstance().newApiParameters();
        parameters.put("name", "pay.setting.list");
        RegistrationCode api = TPUtils.getObject(BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);

        Map<String, Object> reqData = new HashMap<>();
        reqData.put("storeCode", api.getStoreNo());
        parameters.put("data", GsonHelper.toJson(reqData));
        parameters.put("sign", OpenApiUtils.getInstance().sign(api, parameters));
        String result = HttpManger.getSingleton().postString(HttpUrl.BASE_API_URL, parameters);
        BaseListResponse<PaymentParameter> resp = GsonHelper.fromJson(result, new TypeToken<BaseListResponse<PaymentParameter>>() {
        }.getType());
        if (resp.getCode() == BaseResponse.SUCCESS) {
            if (cachePaymentParameter(resp.getData())) {
                notify.setSuccess(true);
                notify.setOperate(DownloadCacheName.PAY_SETTING);
                notify.setMessage(String.format("%s下载成功......", tips));
                LogUtil.i(this, notify.getMessage());
            } else {
                notify.setSuccess(false);
                notify.setOperate(DownloadCacheName.PAY_SETTING_EXCEPTION);
                notify.setMessage(String.format("下载%s时发生异常", tips));

                LogUtil.e(this, tips + "下载发生异常");
            }
        } else {
            notify.setSuccess(false);
            notify.setOperate(DownloadCacheName.PAY_SETTING_ERROR);
            notify.setMessage(String.format("%s：<%s>-<%s>", tips, resp.getCode(), resp.getMsg()));
            LogUtil.e(this, String.format("下载<%s>下载出错:%s", tips, resp.getMsg()));
        }

        return notify;
    }

    @Override
    public DownloadNotify fetchSalesSetting() {
        String tips = "销售设置";
        DownloadNotify notify = new DownloadNotify();
        notify.setSuccess(false);
        notify.setOperate(DownloadCacheName.LINE_SALES_SETTING);
        notify.setPager(false);
        Map<String, Object> parameters = OpenApiUtils.getInstance().newApiParameters();
        parameters.put("name", "pos.sale.setting.template");
        RegistrationCode api = TPUtils.getObject(BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);

        Map<String, Object> reqData = new HashMap<>();
        reqData.put("storeId", api.getStoreId());
        parameters.put("data", GsonHelper.toJson(reqData));
        parameters.put("sign", OpenApiUtils.getInstance().sign(api, parameters));
        String result = HttpManger.getSingleton().postString(HttpUrl.BASE_API_URL, parameters);
        BaseListResponse<LineSalesSetting> resp = GsonHelper.fromJson(result, new TypeToken<BaseListResponse<LineSalesSetting>>() {
        }.getType());
        if (resp.getCode() == BaseResponse.SUCCESS) {
            if (cacheSalesSetting(resp.getData())) {
                notify.setSuccess(true);
                notify.setOperate(DownloadCacheName.LINE_SALES_SETTING);
                notify.setMessage(String.format("%s下载成功......", tips));
                LogUtil.i(this, notify.getMessage());
            } else {
                notify.setSuccess(false);
                notify.setOperate(DownloadCacheName.LINE_SALES_SETTING_EXCEPTION);
                notify.setMessage(String.format("下载%s时发生异常", tips));

                LogUtil.e(this, tips + "下载发生异常");
            }
        } else {
            notify.setSuccess(false);
            notify.setOperate(DownloadCacheName.LINE_SALES_SETTING_ERROR);
            notify.setMessage(String.format("%s：<%s>-<%s>", tips, resp.getCode(), resp.getMsg()));
            LogUtil.e(this, String.format("下载<%s>下载出错:%s", tips, resp.getMsg()));
        }

        return notify;
    }

    private boolean cacheSalesSetting(List<LineSalesSetting> data) {
        boolean isSuccess = true;
        try {
            LineSalesSettingDbManger.getInstance().deleteAll();
            if (data.size() > 0) {
                LineSalesSettingDbManger.getInstance().insert(data);
            }
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }

        return isSuccess;
    }

    private boolean cachePaymentParameter(List<PaymentParameter> data) {
        boolean isSuccess = true;
        try {
            PaymentParameterDbManger.getInstance().deleteAll();
            if (data.size() > 0) {
                PaymentParameterDbManger.getInstance().insert(data);
            }
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }

        return isSuccess;
    }

    private boolean cacheMemberPointRuleBrand(List<MemberPointRuleBrand> data) {
        boolean isSuccess = true;
        try {
            MemberPointRuleBrandDbManger.getInstance().deleteAll();
            if (data.size() > 0) {
                MemberPointRuleBrandDbManger.getInstance().insert(data);
            }
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }
        return isSuccess;
    }

    private boolean cacheMemberPointRuleCategroy(List<MemberPointRuleCategory> data) {
        boolean isSuccess = true;
        try {
            MemberPointRuleCategoryDbManger.getInstance().deleteAll();
            if (data.size() > 0) {
                MemberPointRuleCategoryDbManger.getInstance().insert(data);
            }
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }
        return isSuccess;
    }

    private boolean cacheMemberPointRule(List<MemberPointRule> data) {
        boolean isSuccess = true;
        try {
            MemberPointRuleDbManger.getInstance().deleteAll();
            if (data.size() > 0) {
                MemberPointRuleDbManger.getInstance().insert(data);
            }
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }

        return isSuccess;
    }

    private boolean cacheMemberLevelCategoryDiscount(List<MemberLevelCategoryDiscount> list) {
        boolean isSuccess = true;
        try {
            MemberLevelCategoryDiscountDbManger.getInstance().deleteAll();
            if (list.size() > 0) {
                MemberLevelCategoryDiscountDbManger.getInstance().insert(list);
            }
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }
        return isSuccess;
    }

    private boolean cacheMemberLevel(List<MemberLevel> data) {
        boolean isSuccess = true;
        try {
            MemberLevelDbManger.getInstance().deleteAll();
            if (data.size() > 0) {
                MemberLevelDbManger.getInstance().insert(data);
            }
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }
        return isSuccess;
    }

    private boolean cacheMemberSetting(List<LineSystemSet> datas) {
        boolean isSuccess = true;
        try {
            LineSystemSetDbManger.getInstance().deleteAll();
            if (datas.size() > 0) {
                LineSystemSetDbManger.getInstance().insert(datas);
            }
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }
        return isSuccess;
    }

    private boolean cacheStoreInfo(List<StoreInfo> list, boolean firstPageFlag) {
        boolean isSuccess = true;
        try {
            if (firstPageFlag) {
                StoreInfoDbManger.getInstance().deleteAll();
            }
            if (list.size() > 0) {
                StoreInfoDbManger.getInstance().insert(list);
            }
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }
        return isSuccess;
    }

    private boolean cachePayMode(List<PayMode> list, boolean firstPageFlag) {
        boolean isSuccess = true;
        try {
            if (firstPageFlag) {
                PayModeDbManger.getInstance().deleteAll();
            }
            if (list.size() > 0) {
                PayModeDbManger.getInstance().insert(list);
            }
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }

        return isSuccess;
    }

    private boolean cacheSupplier(List<Supplier> list, boolean firstPageFlag) {
        boolean isSuccess = true;
        try {
            if (firstPageFlag) {
                SupplierDbManger.getInstance().deleteAll();
            }
            if (list.size() > 0) {
                SupplierDbManger.getInstance().insert(list);
            }
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }
        return isSuccess;
    }

    private boolean cacheWorker(List<Worker> list, boolean firstPageFlag) {
        boolean isSuccess = true;
        try {
            if (firstPageFlag) {
                WorkerDbManger.getInstance().deleteAll();
            }
            if (list.size() > 0) {
                WorkerDbManger.getInstance().insert(list);
            }
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }
        return isSuccess;
    }

    private boolean cacheWorkerData(List<WorkData> list, boolean firstPageFlag) {
        boolean isSuccess = true;
        try {
            if (firstPageFlag) {
                WorkerDataDbManager.getInstance().deleteAll();
            }
            if (list.size() > 0) {
                WorkerDataDbManager.getInstance().insert(list);
            }
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }
        return isSuccess;
    }

    private boolean cacheWorkerModule(List<WorkModule> list, boolean firstPageFlag) {
        boolean isSuccess = true;
        try {
            if (firstPageFlag) {
                WorkerModuleDbManager.getInstance().deleteAll();
            }
            if (list.size() > 0) {
                WorkerModuleDbManager.getInstance().insert(list);
            }
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }
        return isSuccess;
    }

    private boolean cacheWorkerRole(List<WorkRole> list, boolean firstPageFlag) {
        boolean isSuccess = true;
        try {
            if (firstPageFlag) {
                WorkerRoleDbManager.getInstance().deleteAll();
            }
            if (list.size() > 0) {
                WorkerRoleDbManager.getInstance().insert(list);
            }
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }
        return isSuccess;
    }

    private boolean cacheStoreProduct(List<StoreProduct> list, boolean firstPageFlag) {
        boolean isSuccess = true;
        try {
            if (firstPageFlag) {
                StoreProductDbManger.getInstance().deleteAll();
            }
            if (list.size() > 0) {
                StoreProductDbManger.getInstance().insert(list);
            }
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }

        return isSuccess;
    }

    private boolean cacheProductSpec(List<ProductSpec> list, boolean firstPageFlag) {
        boolean isSuccess = true;
        try {
            if (firstPageFlag) {
                ProductSpecDbManger.getInstance().deleteAll();
            }
            if (list.size() > 0) {
                ProductSpecDbManger.getInstance().insert(list);
            }
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }

        return isSuccess;
    }

    private boolean cacheProductContact(List<ProductContact> list, boolean firstPageFlag) {
        boolean isSuccess = true;
        try {
            if (firstPageFlag) {
                ProductContractDbManger.getInstance().deleteAll();
            }
            if (list.size() > 0) {
                ProductContractDbManger.getInstance().insert(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }
        return isSuccess;
    }

    private boolean cacheProductCode(List<ProductCode> list, boolean firstPageFlag) {
        boolean isSuccess = true;
        try {
            if (firstPageFlag) {
                ProductCodeDbManger.getInstance().deleteAll();
            }
            if (list.size() > 0) {
                ProductCodeDbManger.getInstance().insert(list);
            }
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }

        return isSuccess;
    }

    private boolean cacheProduct(List<Product> list, boolean firstPageFlag) {
        boolean isSuccess = true;
        try {
            if (firstPageFlag) {
                ProductDbManger.getInstance().deleteAll();
            }
            if (list.size() > 0) {
                ProductDbManger.getInstance().insert(list);
            }
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }

        return isSuccess;
    }

    private boolean cacheProductUnit(List<ProductUnit> list, boolean firstPageFlag) {
        boolean isSuccess = true;
        try {
            if (firstPageFlag) {
                ProductUnitDbManger.getInstance().deleteAll();
            }
            if (list.size() > 0) {
                ProductUnitDbManger.getInstance().insert(list);
            }
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }
        return isSuccess;
    }

    private boolean cacheProductCategroy(List<ProductCategory> list, boolean firstPageFlag) {
        boolean isSuccess = true;
        try {
            if (firstPageFlag) {
                ProductCategroyDbManger.getInstance().deleteAll();
            }
            if (list.size() > 0) {
                ProductCategroyDbManger.getInstance().insert(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }
        return isSuccess;
    }

    private boolean cacheProductBrand(List<ProductBrand> list, boolean firstPager) {
        boolean isSuccess = true;
        try {
            if (firstPager) {
                ProductBrandDbManger.getInstance().deleteAll();
            }
            if (list.size() > 0) {
                ProductBrandDbManger.getInstance().insert(list);
            }
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }
        return isSuccess;
    }
}
