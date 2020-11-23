package com.newsuper.t.juejinbao.ui.ad;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityShanHuVideoBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.bean.LoginEntity;


public class ShanHuVideoActivity extends BaseActivity<BasePresenter, ActivityShanHuVideoBinding> {

    private static final String TAG = "ShanHuVideoActivity";
    private ProgressDialog waitingDialog = null;
//    private Map<StyleAdEntity, CoinTask> mAdKeyTaskValue = new HashMap<StyleAdEntity, CoinTask>();
//    private CoinManager mCoinManager;
//    private Handler mMainHandler;
//    private AdManager mAdManager;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_shan_hu_video;
    }

    @Override
    public void initView() {
//        mAdManager = ManagerCreator.getManager(AdManager.class);
//        mAdManager.init();
//
//        mCoinManager = ManagerCreator.getManager(CoinManager.class);
//        mMainHandler = new Handler(getMainLooper());
//

        mViewBinding.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAd();
            }
        });
    }

    @Override
    public void initData() {

    }

    public void getAd(){
        LoginEntity loginEntity = new LoginEntity();
//        new Thread() {
//            public void run() {
//                showDialog();
//                mAdKeyTaskValue = new HashMap<StyleAdEntity, CoinTask>();
//
//                // 第一步：先拉取下载任务
//                int taskType = 104; // 下载任务的ID
//                CoinRequestInfo coinRequestInfo = new CoinRequestInfo();
//
//                coinRequestInfo.accountId = LoginEntity.getUid()+"";
//                coinRequestInfo.loginKey = LoginEntity.getUserToken();
//                ArrayList<CoinTaskType> coinTaskTypes = new ArrayList<CoinTaskType>();
//                final Coin coin = new Coin();
//                ArrayList<Integer> taskTypes = new ArrayList<Integer>();
//                taskTypes.add(taskType);
//
//                int ret =  mCoinManager.GetTasks(coinRequestInfo, taskTypes, coin, coinTaskTypes);
//                if (ret != ErrorCode.EC_SUCCESS) {
//                    dismissDialog();
//                    showToast("拉取任务出错");
//                    return;
//                }
//
//                // 第二步：拉取未做任务的广告资源，可以多拉一点，因为广告有填充率问题
//                List<AdConfig> adConfigs = new ArrayList<AdConfig>();
//                Bundle inBundle1 = new Bundle();
//                inBundle1.putInt(AdConfig.AD_KEY.AD_NUM.name(), 10);
//                inBundle1.putString(AdConfig.AD_KEY.AD_CHANNEL_NO.name(), "渠道号");
//                AdConfig aAdConfig1 = new AdConfig(taskType, inBundle1);
//                adConfigs.add(aAdConfig1);
//                HashMap<AdConfig, List<StyleAdEntity>> result = mAdManager.getMultPositionAdByList(adConfigs, 5 * 1000L);
//
//                final List<StyleAdEntity> tmpList = new ArrayList<StyleAdEntity>();
//                // 第三步：任务和资源关联起来
//                List<StyleAdEntity> adList = result.get(aAdConfig1);
//                CoinTaskType coinTaskType = coinTaskTypes.get(0);
//                try {
//
//                    for (CoinTask coinTask : coinTaskType.coinTasks) {
//                        // 没做的任务
//                        if (coinTask.task_status == CoinTask.TaskStatus.NEW) {
//                            for (StyleAdEntity ad : adList) {
//                                if (!CommonUtil.isPkgInstalled(ShanHuVideoActivity.this, ad.mPkgName) && !mAdKeyTaskValue.containsKey(ad)) {
//                                    mAdKeyTaskValue.put(ad, coinTask);
//                                    tmpList.add(ad);
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                } catch (Throwable t) {
//                    t.printStackTrace();
//                }
//
//                dismissDialog();
//
//                if (tmpList == null || tmpList.size() == 0) {
//                    showToast("未请求到广告信息");
//                    return;
//                }
//                for (StyleAdEntity mStyleAdEntity : tmpList) {
//                    Log.d(TAG, "StyleAdEntity : " + mStyleAdEntity.toString());
//                }
//                mMainHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
////                        mStyleAdEntityList.clear();
////                        mStyleAdEntityList.addAll(tmpList);
////                        mStyleAdListAdapter.notifyDataSetChanged();
//                    }
//                });
//
//            }
//        }.start();
    }

    private synchronized void showDialog() {
        new Handler(getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (waitingDialog == null) {
                    waitingDialog = new ProgressDialog(mActivity);
                    waitingDialog.setTitle("广告信息");
                    waitingDialog.setMessage("加载中...");
                    waitingDialog.setIndeterminate(true);
                    waitingDialog.setCancelable(false);
                }
                if (waitingDialog != null && !waitingDialog.isShowing()
                        && ! ShanHuVideoActivity.this.isDestroyed()) {
                    waitingDialog.show();
                }
            }
        });
    }
    private synchronized void dismissDialog() {
        new Handler(getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (waitingDialog != null && waitingDialog.isShowing()
                        && !ShanHuVideoActivity.this.isDestroyed()) {
                    waitingDialog.dismiss();
                }
            }
        });
    }
}
