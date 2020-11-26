package com.newsuper.t.juejinbao.ui.ad;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.cmcm.cmgame.CmGameSdk;
//import com.cmcm.cmgame.GameView;
//import com.cmcm.cmgame.IAppCallback;
//import com.cmcm.cmgame.IGameAdCallback;
//import com.cmcm.cmgame.IGamePlayTimeCallback;
//import com.cmcm.cmgame.view.CmGameTopView;


import com.newsuper.t.R;
import com.newsuper.t.juejinbao.bean.BaseEntity;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.home.ppw.TimeRewardPopup;
import com.newsuper.t.juejinbao.utils.MD5Utils;
import com.newsuper.t.juejinbao.utils.StringUtils;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;

import java.util.HashMap;
import java.util.Map;

//public class BaoquGameActivity extends AppCompatActivity implements IAppCallback, IGamePlayTimeCallback, IGameAdCallback {
public class BaoquGameActivity extends AppCompatActivity  {

//    BaseActivity<BaoquGamePrensenterImpl, ActivityBaoquGameBinding>
    public static final int REWARD_TIME = 20; //目前奖励时间为20秒
    private BaoquGamePrensenterImpl baoquGamePrensenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.white));
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        setContentView(R.layout.activity_baoqu_game);

        // 目前只支持anrdoid 5.0或以上
        if (Build.VERSION.SDK_INT < 21) {
            Toast.makeText(this, "不支持低版本，仅支持android 5.0或以上版本!", Toast.LENGTH_LONG).show();
        }

        // 把默认游戏中心view添加进媒体指定界面
//        GameView gameTabsClassifyView = findViewById(R.id.gameView);
//        gameTabsClassifyView.inflate(this);
//        // 初始化小游戏 sdk 的账号数据，用于存储游戏内部的用户数据，
//        // 为避免数据异常，这个方法建议在小游戏列表页面展现前（可以是二级页面）才调用
//        CmGameSdk.initCmGameAccount();
//
//        // 默认游戏中心页面，点击游戏试，触发回调
//        CmGameSdk.setGameClickCallback(this);
//
//        // 点击游戏右上角或物理返回键，退出游戏时触发回调，并返回游戏时长
//        CmGameSdk.setGamePlayTimeCallback(this);

        // 游戏内增加自定义view，提供产品多样性
        initMoveViewSwitch();

        // 所有广告类型的展示和点击事件回调，仅供参考，数据以广告后台为准
        // 建议不要使用，有阻塞行为会导致程序无法正常使用
        // CmGameSdk.INSTANCE.setGameAdCallback(this);

        initView();
    }

//    @Override
//    public void gameClickCallback(String gameName, String gameID) {
//        Log.d("cmgamesdk_Main2Activity", gameID + "----" + gameName);
//
//        commitAdData(ADCommitType.AD_TYPE_GAME_CLICK);
//    }

    /**
     * 签名算法：md5(md5(token+expire)+secretKey+version+ad_contribute_type)
     * version:1.8.7
     * user_token:5c2dafb101e0bb6d0da35669eb8341f2
     * nowTime:1565406392
     * expire:1565406692
     * ad_contribute_type:show_partner
     */
    public void commitAdData(String type) {
        String version = StringUtils.getVersionCode(this);
        String user_token = LoginEntity.getUserToken();
        long nowTime = System.currentTimeMillis()/1000;
        long expire = nowTime + 300;
        String ad_contribute_type = type;
        String encrypt = MD5Utils.md5(user_token + expire);
        String signature = MD5Utils.md5(encrypt + ADCommitType.AD_SECREKEY + version + ad_contribute_type);
        Map<String, String> param = new HashMap<>();
        param.put("signature",signature);
        param.put("expire",expire + "");
        param.put("ad_contribute_type",ad_contribute_type);
        baoquGamePrensenter.commitAdData(param, this, new OnDataBackLintener() {
            @Override
            public void onSuccess(BaseEntity loginEntity) {
                Log.i("zzz", "onSuccess: " );
            }

            @Override
            public void onEorr(String str) {

            }
        });
    }

    /**
     * @param playTimeInSeconds 玩游戏时长，单位为秒
     */
   // @Override
    public void gamePlayTimeCallback(String gameId, int playTimeInSeconds) {
        Log.d("cmgamesdk_Main2Activity", "play game ：" + gameId + "playTimeInSeconds : " + playTimeInSeconds);

        //埋点（统计游戏页面用户在线时间）
        Map<String, Object> time = new HashMap<>();
        time.put("playTimeInSeconds",playTimeInSeconds);
        //MobclickAgent.onEventObject(MyApplication.getContext(), EventID.MISSION_GAME_ONLINE_TIME, time);

         if(playTimeInSeconds > REWARD_TIME){
            Map<String, String> map = new HashMap<>();
            map.put("game_id",gameId);
             baoquGamePrensenter.playGameGetCoin(map, this, new OnDataBackLintener() {
                @Override
                public void onSuccess(BaseEntity entity) {
                    BaseEntity baseEntity = entity;
                    switch (baseEntity.getCode()){
                        case 0:
                            TimeRewardPopup timeRewardPopup = new TimeRewardPopup(BaoquGameActivity.this);
                            timeRewardPopup.setView("30");
                            timeRewardPopup.showPopupWindow();
                            break;
                        case 1:
                            ToastUtils.getInstance().show(BaoquGameActivity.this,"领取失败");
                            break;
                        case 2:
                            ToastUtils.getInstance().show(BaoquGameActivity.this,"当前游戏今天已经领取过");
                            break;
                        case 3:
                            ToastUtils.getInstance().show(BaoquGameActivity.this,"达到了今天领取上限");
                            break;
                        case 4:
                            ToastUtils.getInstance().show(BaoquGameActivity.this,"服务端异常，请稍后重试");
                            break;
                    }

                }

                @Override
                public void onEorr(String str) {

                }
            });
         } else {
             AdTipDialog dialog = new AdTipDialog(this);
             dialog.show();
        }
    }

    /**
     * 广告曝光/点击回调
     *
     * @paramgameId   游戏Id
     * @paramadType   广告类型：1：激励视频广告；2：Banner广告；3：原生Banner广告；4：全屏视频广告；5：插屏广告；6：开屏大卡广告
     * @paramadAction 广告操作：1：曝光；2：点击；3：关闭；4：跳过
     */
//    @Override
//    public void onGameAdAction(String gameId, int adType, int adAction) {
//        Log.d("cmgamesdk_Main2Activity", "onGameAdAction gameId: " + gameId + " adType: " + adType + " adAction: " + adAction);
//
//        if(adAction==2){
//            commitAdData(ADCommitType.AD_TYPE_CSJ_CLICK);
//        }else {
//            commitAdData(ADCommitType.AD_TYPE_CSJ_SHOW);
//        }
//
//    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//
//        CmGameSdk.removeGameClickCallback();
//        CmGameSdk.setMoveView(null);
//        CmGameSdk.removeGamePlayTimeCallback();
//        CmGameSdk.removeGameAdCallback();
//
//    }

    public void initView() {
        baoquGamePrensenter = new BaoquGamePrensenterImpl();
       TextView moduleIncludeTitleBarTitle =  findViewById(R.id.activity_login_bar).findViewById(R.id.module_include_title_bar_title);
       ImageView moduleIncludeTitleBarReturn =  findViewById(R.id.activity_login_bar).findViewById(R.id.module_include_title_bar_return);

       moduleIncludeTitleBarTitle.setText("边玩游戏边赚金币");
        moduleIncludeTitleBarReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initMoveViewSwitch() {
        CheckBox moveViewSwitch = findViewById(R.id.move_view_switch);
        moveViewSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    initMoveView();
                }
//                else {
//                    CmGameSdk.setMoveView(null);
//                }
            }
        });
    }

    // 请确保在游戏界面展示前传入目标View，并在豹趣游戏模块关闭后控制自己View的回收避免泄漏
    private void initMoveView() {
        final View view = LayoutInflater.from(this).inflate(R.layout.test_move_layout, null);
        //不能在根布局设置点击事件，否则影响拖动，我们会进行点击回调，在回调中处理即可
//        CmGameTopView cmGameTopView = new CmGameTopView(view, new CmGameTopView.CmViewClickCallback() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(BaoquGameActivity.this, "这里被点击了", Toast.LENGTH_SHORT).show();
//                view.findViewById(R.id.button_two).setVisibility(View.VISIBLE);
//            }
//        });
        //目前不支持多个可点击View，可以设置类似菜单格式，在顶层View点击后再进行展示，菜单内的点击自己控制，但是记得用完后隐藏
        view.findViewById(R.id.button_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.findViewById(R.id.button_two).setVisibility(View.GONE);
            }
        });

        //控件初始位置位于游戏界面右上角,距离顶部100dp，右边距10dp，这里可以进行设定布局格式，参数必须为FrameLayout.LayoutParam
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.END;
        layoutParams.rightMargin = 100; //注意这里是px，记得dp转化
        layoutParams.topMargin = 350;
//        cmGameTopView.setLayoutParams(layoutParams);
//
//        // 控件是否可滑动，默认可滑动
//        cmGameTopView.setMoveEnable(true);
//        // 顶层View是否需要等到游戏加载成功再显示，默认是
//        cmGameTopView.setNeedShowAfterGameShow(true);
//
//        //设置屏幕事件监听
//        cmGameTopView.setScreenCallback(new CmGameTopView.ScreenEventCallback() {
//            @Override
//            public void onDrag(MotionEvent event) {
//                //TopView被拖拽时候的回调
//                Log.d("cmgamesdk_Main2Activity", "控件拖拽：" + event.getAction() + ":" + event.getX() + "," + event.getY());
//            }
//
//            @Override
//            public void onScreenTouch(MotionEvent event) {
//                //游戏界面被触摸时候的回调
//                Log.d("cmgamesdk_Main2Activity", "屏幕点击：" + event.getAction() + ":" + event.getX() + "," + event.getY());
//            }
//
//            @Override
//            public void onViewVisible() {
//
//            }
//        });
//
//        CmGameSdk.setMoveView(cmGameTopView);
    }


}
