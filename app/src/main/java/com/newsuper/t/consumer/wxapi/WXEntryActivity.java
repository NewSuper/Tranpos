package com.newsuper.t.consumer.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.utils.WXResult;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


import static com.tencent.mm.opensdk.modelbase.BaseResp.ErrCode.ERR_AUTH_DENIED;
import static com.tencent.mm.opensdk.modelbase.BaseResp.ErrCode.ERR_OK;
import static com.tencent.mm.opensdk.modelbase.BaseResp.ErrCode.ERR_USER_CANCEL;

/**
 * Created by Administrator on 2017/6/2 0002.
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	IWXAPI api;
	String TAG = "WXEntryActivity";
	Handler handler  = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("wx回调", "..........onCreat。。。");
		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
		// 将该app注册到微信
		api.registerApp(Constants.APP_ID);
		api.handleIntent(getIntent(),this);
	}
	@Override
	public void onReq(BaseReq baseReq) {
		Log.e("wx回调", "..........onReq");
		switch (baseReq.getType()) {
			case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
				Log.e("wx回调", "..........onReq。。。COMMAND_GETMESSAGE_FROM_WX");
				this.finish();
				break;
			case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
				Log.e("wx回调", "..........onReq。。。COMMAND_SHOWMESSAGE_FROM_WX");
				this.finish();
				break;
			default:
				break;
		}
	}

	@Override
	public void onResp(BaseResp baseResp) {
		Log.e("wx回调", "..........onResp");
		Log.e("wx回调", "..........."+baseResp.errCode);
		switch (baseResp.errCode){
			//用户同意
			case ERR_OK:
				if(baseResp instanceof SendAuth.Resp){
					WXResult.code =  ((SendAuth.Resp)baseResp).code;
				}
				Log.e("wx回调", "..........onReq。。。ERR_OK");
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						finish();
					}
				},500);
				break;
			//用户拒绝授权
			case ERR_AUTH_DENIED:
				ToastUtil.showTosat(this,"用户拒绝授权");
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						finish();
					}
				},500);
				break;
			//用户取消
			case ERR_USER_CANCEL:
				ToastUtil.showTosat(this,"用户取消");
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						finish();
					}
				},500);
				break;
			default:
				Log.e("wx回调", "..........default");
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						finish();
					}
				},500);
				break;
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		handler.removeCallbacksAndMessages(null);
		Log.e("wx回调", "..........onDestroy");
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(getIntent(),this);

	}
}
