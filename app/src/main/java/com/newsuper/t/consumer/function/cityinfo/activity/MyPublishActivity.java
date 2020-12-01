package com.newsuper.t.consumer.function.cityinfo.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;
import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseActivity;
import com.newsuper.t.consumer.bean.MyPublishListBean;
import com.newsuper.t.consumer.bean.SetTopInfoBean;
import com.newsuper.t.consumer.bean.SetTopPayBean;
import com.newsuper.t.consumer.function.cityinfo.adapter.MyPublishAdapter;
import com.newsuper.t.consumer.function.cityinfo.internal.IMyPublishView;
import com.newsuper.t.consumer.function.cityinfo.presenter.MyPublishPresenter;
import com.newsuper.t.consumer.utils.DialogUtils;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.widget.CustomToolbar;
import com.newsuper.t.consumer.widget.LoadingDialog2;
import com.newsuper.t.consumer.wxapi.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyPublishActivity extends BaseActivity implements IMyPublishView {

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.lv_my_publish)
    ListView lvMyPublish;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    private static final int EDITE_CODE = 123;
    private MyPublishPresenter publishPresenter;
    private MyPublishAdapter publishAdapter;
    private ArrayList<MyPublishListBean.MyPublishListData> listData;
    private int page = 1;
    private View footerView;
    private TextView tvFooter;
    private boolean isBottom,isLoadingMore = true;
    private LoadingDialog2 loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_publish);
        ButterKnife.bind(this);
        toolbar.setBackImageViewVisibility(View.VISIBLE);
        toolbar.setTitleText("我的发布");
        toolbar.setMenuText("");
        publishPresenter = new MyPublishPresenter(this);
        listData = new ArrayList<>();
        publishAdapter = new MyPublishAdapter(this,listData);
        publishAdapter.setPublishListener(new MyPublishAdapter.MyPublishListener() {
            @Override
            public void onDeleted(int position,String id) {
                showDelDialog(id);
            }

            @Override
            public void onEdited(int position) {
                MyPublishListBean.MyPublishListData  data = listData.get(position);
                Intent intent = new Intent(MyPublishActivity.this, PublishInfoActivity.class);
                intent.putExtra("data",data);
                intent.putExtra("type","re_edit");
                startActivityForResult(intent,EDITE_CODE);
            }

            @Override
            public void onSetTop(int position) {
                select_info_id = listData.get(position).id;
                publishPresenter.getSetTopInfo(listData.get(position).id);
            }

            @Override
            public void onRepublish(int position) {
                MyPublishListBean.MyPublishListData  data = listData.get(position);
                Intent intent = new Intent(MyPublishActivity.this, PublishInfoActivity.class);
                intent.putExtra("data",data);
                intent.putExtra("type","re_publish");
                startActivityForResult(intent,EDITE_CODE);
            }
        });
        lvMyPublish.setAdapter(publishAdapter);
        footerView = LayoutInflater.from(MyPublishActivity.this).inflate(R.layout.listview_footer_load_more, null);
        tvFooter = (TextView) footerView.findViewById(R.id.tv_load_more);
//        lvMyPublish.addFooterView(footerView);
        lvMyPublish.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_IDLE:
                        if (isBottom && !isLoadingMore) {
                            loadMoreData();
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isBottom = (firstVisibleItem + visibleItemCount == totalItemCount);
            }
        });
        lvMyPublish.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String info_id = listData.get(position).id;
                Intent intent = new Intent(MyPublishActivity.this, PublishDetailActivity.class);
                intent.putExtra("info_id",info_id);
                startActivity(intent);

            }
        });
        loadingDialog = new LoadingDialog2(this);
        loadingDialog.show();
        loadData();
    }
    private void loadData(){
        page = 1;
        publishPresenter.getPublishList(page);
    }
    private void loadMoreData(){
        tvFooter.setText("加载中...");
        isLoadingMore = true;
        int p = page + 1;
        publishPresenter.getPublishList(p);
    }
    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void dialogDismiss() {
        loadingDialog.dismiss();
    }

    @Override
    public void showToast(String s) {
        ToastUtil.showTosat(this,s);
    }

    @Override
    public void getPublishList(MyPublishListBean bean) {
        loadingDialog.dismiss();
        if (bean.data != null && bean.data.size() > 0 ){
            tvTip.setVisibility(View.GONE);
            listData.clear();
            listData.addAll(bean.data);
            publishAdapter.notifyDataSetChanged();
            if (bean.data.size() > 5){
                lvMyPublish.addFooterView(footerView);
                isLoadingMore = false;
            }
        }else {
            tvTip.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getMorePublishList(MyPublishListBean bean) {
        isLoadingMore = false;
        if (bean.data != null && bean.data.size() > 0){
            page ++ ;
            listData.addAll(bean.data);
            publishAdapter.notifyDataSetChanged();
        }else {
            tvFooter.setText("已加载完");
        }
    }

    @Override
    public void getPublishFail() {
        loadingDialog.dismiss();
        isLoadingMore = false;
    }

    @Override
    public void onDeleteSuccess() {
        ToastUtil.showTosat(this,"删除成功");
        loadData();
    }

    @Override
    public void onDeleteFail() {
        ToastUtil.showTosat(this,"删除失败");
    }

    @Override
    public void onEditSuccess() {

    }

    @Override
    public void onEditFail() {

    }

    @Override
    public void onSetTopSuccess(SetTopPayBean bean) {
        if (bean.data != null){
            weixinPay(bean.data.zhifuParameters);
        }
    }

    @Override
    public void onSetTopFail() {

    }

    @Override
    public void getTopInfoSuccess(SetTopInfoBean bean) {
        if (bean.data != null){
            showTopDialog(bean);
        }
    }

    @Override
    public void getTopInfoFail() {

    }
    private AlertDialog delDialog;
    private void showDelDialog(final String id){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_publish_del, null);
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delDialog.dismiss();
            }
        });
        view.findViewById(R.id.tv_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delDialog.dismiss();
                publishPresenter.getPublishListDel(id);
            }
        });
        builder.setView(view);
        delDialog = builder.create();
        delDialog.show();
    }

    private Dialog topDialog ;
    private String select_info_id,top_num,create_fee;
    private void showTopDialog(final SetTopInfoBean bean) {
        if (topDialog == null){
            ArrayList<String> list = new ArrayList<>();
            final float fee = FormatUtil.numFloat(bean.data.top_fee);
            for (int i = 0;i < 50;i++){
                Float f = (i + 1) * fee;
                String m = FormatUtil.numFormat(f+"");
                String s = ( i + 1 ) + "天/" + m +"元";
                list.add(s);
            }
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_publish_set_top, null);
            view.findViewById(R.id.tv_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    topDialog.dismiss();
                }
            });
            view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    topDialog.dismiss();
                    if (bean.data.pay_type.size() > 0){
                        if ("online".equals(bean.data.pay_type.get(0))){
                            publishPresenter.setToTop(select_info_id,top_num,create_fee,bean.data.pay_type.get(0),bean.data.weixinzhifu_type);
                        }
                    }

                }
            });
            WheelView wheelView = (WheelView)view.findViewById(R.id.wheel_top);
            wheelView.setSelection(3);
            wheelView.setWheelAdapter(new ArrayWheelAdapter(this)); // 文本数据源
            wheelView.setSkin(WheelView.Skin.Holo); // common皮肤
            wheelView.setWheelData(list);  // 数据集合
            wheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
                @Override
                public void onItemSelected(int position, Object o) {
                    top_num = (position + 1) + "";
                    create_fee = FormatUtil.numFormat(((position + 1) * fee)+"");
                }
            });
            top_num = (wheelView.getCurrentPosition() + 1) + "";
            create_fee = FormatUtil.numFormat(((wheelView.getCurrentPosition() + 1) * fee)+"");
            topDialog = DialogUtils.BottonDialog(this,view);
        }
        topDialog.show();
    }
    //微信支付
    private IWXAPI msgApi;//微信支付
    private void weixinPay(SetTopPayBean.ZhiFuParameters parameters) {
        if (msgApi == null){
            msgApi = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
            msgApi.registerApp(Constants.APP_ID);
        }
        if (msgApi.isWXAppInstalled()) {
            PayReq req = new PayReq();
            req.appId = parameters.appid;
            req.partnerId = parameters.partnerid;//微信支付分配的商户号
            req.prepayId = parameters.prepayid;//微信返回的支付交易会话ID
            req.nonceStr = parameters.noncestr;//32位随机字符
            req.timeStamp = parameters.timestamp;// 10位时间戳
            req.packageValue = "Sign=WXPay";//暂填写固定值Sign=WXPay
            req.sign = parameters.sign; //签名
            msgApi.registerApp(Constants.APP_ID);
            msgApi.sendReq(req);
        } else {
            UIUtils.showToast("未安装微信！");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == EDITE_CODE){
                loadData();
            }
        }
    }
}

