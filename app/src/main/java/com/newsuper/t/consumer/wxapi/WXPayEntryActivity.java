package com.newsuper.t.consumer.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.utils.WXResult;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import static com.tencent.mm.opensdk.modelbase.BaseResp.ErrCode.ERR_COMM;
import static com.tencent.mm.opensdk.modelbase.BaseResp.ErrCode.ERR_OK;
import static com.tencent.mm.opensdk.modelbase.BaseResp.ErrCode.ERR_USER_CANCEL;

/**
 * Created by Administrator on 2017/7/6 0006.
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    IWXAPI api;
    public static int errCode = -22222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);

        // 将该app注册到微信
//        api.registerApp(Const.WEIXIN_APP_ID);
        api.handleIntent(getIntent(),this);
    }
    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX){
            switch (baseResp.errCode){
                //成功 展示成功页面
                case ERR_OK: //0
                    ToastUtil.showTosat(WXPayEntryActivity.this,"支付成功");
                    errCode = 0;
                    WXResult.errCode = 0;
                    finish();
                    break;
                //错误 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
                case ERR_COMM:// -1
                    ToastUtil.showTosat(WXPayEntryActivity.this,"支付失败"+baseResp.errStr);
                    errCode = -1;
                    WXResult.errCode = -1;
                    finish();
                    break;
                //用户取消 无需处理。发生场景：用户不支付了，点击取消，返回APP。
                case ERR_USER_CANCEL://-2
                    errCode = -2;
                    WXResult.errCode = -2;
                    ToastUtil.showTosat(WXPayEntryActivity.this,"支付取消");
                    finish();
                    break;
            }
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(getIntent(),this);

    }
}
