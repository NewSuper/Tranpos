package com.newsuper.t.sale.ui.navi.mvp;

import android.content.Context;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.wxpayface.IWxPayfaceCallback;
import com.tencent.wxpayface.WxPayFace;
import com.trans.network.HttpManger;
import com.trans.network.callback.JsonObjectCallback;
import com.trans.network.callback.StringCallback;
import com.trans.network.model.Response;
import com.trans.network.utils.GsonHelper;
import com.newsuper.t.sale.base.BaseApp;
import com.newsuper.t.sale.callback.WxFaceCallback;
import com.newsuper.t.sale.db.manger.PaymentParameterDbManger;
import com.newsuper.t.sale.entity.PaymentParameter;
import com.newsuper.t.sale.entity.RegistrationCode;
import com.newsuper.t.sale.entity.Worker;
import com.newsuper.t.sale.http.path.HttpUrl;
import com.newsuper.t.sale.utils.DeviceUtils;
import com.newsuper.t.sale.utils.Global;
import com.newsuper.t.sale.utils.KeyConstrant;
import com.newsuper.t.sale.utils.OpenApiUtils;
import com.newsuper.t.sale.utils.Tools;
import com.newsuper.t.sale.utils.UiUtils;
import com.newsuper.t.sale.utils.WxConstant;
import com.transpos.tools.tputils.TPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FaceModel implements FaceContract.Model {

    PaymentParameter paymentParameter = null;

    public FaceModel(){
        List<PaymentParameter> list = PaymentParameterDbManger.getInstance().loadAll();
        if(list != null && list.size() > 0){
            paymentParameter = list.get(0);
        }
    }


    @Override
    public void getAuthInfo(Context context, WxFaceCallback callback) {
        WxPayFace.getInstance().getWxpayfaceRawdata(new IWxPayfaceCallback() {
            @Override
            public void response(Map map) throws RemoteException {
                if (Tools.isSuccessInfo(map)) {
                    //获取到RAW_DATA
                    if (map.get(WxConstant.RAW_DATA) != null) {
                        String rawdata = map.get(WxConstant.RAW_DATA).toString();
                        getLePosFacePayVoucher(rawdata,context,callback);
                    }
                }
            }
        });
    }

    @Override
    public void queryMemberInfo(String openid, JsonObjectCallback callback) {
        Map<String, Object> parameters = OpenApiUtils.getInstance().newApiParameters();
        parameters.put("name", "member.query");
        RegistrationCode api = TPUtils.getObject(BaseApp.getApplication(), KeyConstrant.KEY_AUTH_REGISTER, RegistrationCode.class);
        Worker worker = TPUtils.getObject(BaseApp.getApplication(), KeyConstrant.KEY_WORKER, Worker.class);
        Map<String, Object> reqData = new HashMap<>();
        reqData.put("property", "openId");
        reqData.put("keyword", openid);
        reqData.put("storeNo", api.getStoreNo());
        reqData.put("posNo", api.getPosNo());
        reqData.put("workerNo", worker.getNo());
        reqData.put("sourceSign", Global.TERMINAL_TYPE);
        parameters.put("data", GsonHelper.toJson(reqData));
        parameters.put("sign", OpenApiUtils.getInstance().sign(api, parameters));
        HttpManger.getSingleton().postJsonObject(HttpUrl.BASE_API_URL, parameters,null,callback);
    }

    void getLePosFacePayVoucher(String rawdata,Context context,WxFaceCallback callback){
        Map<String, String> map = new LinkedHashMap<>();
        String pbody = paymentParameter != null ? paymentParameter.getPbody() : null;
        String sub_mch_id = "";
        String merchant_id = "";
        if(!TextUtils.isEmpty(pbody)){
            try {
                JSONObject jsonObject = new JSONObject(pbody);
                sub_mch_id = TPUtils.get(context,KeyConstrant.KEY_WX_MECHID,"");
                merchant_id = jsonObject.optString("merchant_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        map.put("sub_appid",TPUtils.get(context,KeyConstrant.KEY_WX_APPID,WxConstant.LE_APP_ID));
        map.put("sub_mch_id",sub_mch_id );
        map.put("merchant_id",merchant_id );
        map.put("raw_data",rawdata);
        map.put("store_id",paymentParameter != null ? paymentParameter.getStoreId() : "055039");
        map.put("store_name","shop");
        map.put("device_id", DeviceUtils.getInstance().getMotherboardNumber());

        //按字典顺序排序
        List<Map.Entry<String, String>> infoIds = new ArrayList<>(map.entrySet());
        Collections.sort(infoIds, (o1, o2) -> (o1.getKey()).compareTo(o2.getKey()));
        //使用&符号进行频率
//        String sbR = Tools.getStringBuffer(infoIds) + "&key=" + WxConstant.MCH_KEY_ID;

        String payKey = null;
        if(!TextUtils.isEmpty(pbody)){
            try {
                JSONObject jsonObject = new JSONObject(pbody);
                payKey = jsonObject.optString("payKey").trim();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(payKey == null){
            payKey = WxConstant.LE_KEY;
        }

        String sbR = Tools.getStringBuffer(infoIds) + "&key=" + payKey;
        Log.e("OkHttp","签名参数"+sbR);
        //进行MD5加密之后  转大写
        String sign = Tools.hex_md5(sbR).toUpperCase();
        map.put("sign", sign);

        HttpManger.getSingleton().postFromString(HttpUrl.getLePosAuthInfo, map, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                super.onSuccess(response);
                String body = response.body();
                try {
                    String resp_msg = Tools.parseGetAuthInfoXML(body, "resp_msg");
                    String return_code = Tools.parseGetAuthInfoXML(body, "resp_code");
                    if(return_code.equals("0")){
                        String auth_info = Tools.parseGetAuthInfoXML(body, "auth_info");
                        getWxpayfaceUserInfo(auth_info,context,callback);
                    } else {
                        UiUtils.showToastShort(resp_msg+":"+return_code);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    UiUtils.showToastShort("parseGetAuthInfoXML error");
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Log.e("debug", "onError: "+response.message() );
            }
        });
    }

    private void getWxpayfaceUserInfo(String authinfo,Context context,WxFaceCallback callback) {
        Map<String, String> m1 = new HashMap<String, String>();
        m1.put("appid", TPUtils.get(context,KeyConstrant.KEY_WX_APPID,"")); // 公众号，必填
        m1.put("mch_id", TPUtils.get(context,KeyConstrant.KEY_WX_MECHID,"")); // 商户号，必填
//        m1.put("sub_appid", "xxxxxxxxxxxxxx"); // 子商户公众账号ID(非服务商模式不填)
//        m1.put("sub_mch_id", "填您的子商户号"); // 子商户号(非服务商模式不填)
        m1.put("store_id",paymentParameter != null ? paymentParameter.getStoreId() : "055039"); // 门店编号，必填
        m1.put("face_authtype", "FACEID-ONCE"); // 人脸识别模式， FACEID-ONCE`: 人脸识别(单次模式) FACEID-LOOP`: 人脸识别(循环模式), 必填
        m1.put("authinfo", authinfo); // 调用凭证，详见上方的接口参数
        WxPayFace.getInstance().getWxpayfaceUserInfo(m1, new IWxPayfaceCallback() {
            @Override
            public void response(Map info) throws RemoteException {
                if (info == null) {
                    callback.onAuthInfoError();
                    UiUtils.showToastShort("获取信息失败");
                    return;
                }
                String code = (String) info.get("return_code"); // 错误码
                Integer errcode = (Integer) info.get("err_code"); // 二级错误码
                String msg = (String) info.get("return_msg"); // 错误码描述
                String openid = info.get("sub_openid").toString(); // openid
                TPUtils.put(context,KeyConstrant.KEY_WX_OPENID,openid);
                callback.onAuthInfoSuccess(info);
                String sub_openid = "";
                if (info.get("sub_openid") != null) sub_openid = info.get("sub_openid").toString(); // 子商户号下的openid(服务商模式)
                String nickName = info.get("nickname").toString(); // 微信昵称
                String token = "";
                if (info.get("token") != null) token = info.get("token").toString(); // facesid,用户获取unionid
                if (code == null || openid == null || nickName == null || !code.equals("SUCCESS")) {
                    new RuntimeException("调用返回非成功信息,return_msg:" + msg + "   ").printStackTrace();
                    return ;
                }

            }
        });
    }
}
