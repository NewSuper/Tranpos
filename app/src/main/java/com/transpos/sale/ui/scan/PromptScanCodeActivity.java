package com.transpos.sale.ui.scan;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.trans.network.utils.GsonHelper;
import com.transpos.market.R;
import com.transpos.sale.adapter.ScanCodeQueryAdapter;
import com.transpos.sale.base.mvp.BaseMvpActivity;
import com.transpos.sale.db.manger.OrderObjectDbManger;
import com.transpos.sale.db.manger.OrderPayDbManger;
import com.transpos.sale.entity.AccountsBean;
import com.transpos.sale.entity.Member;
import com.transpos.sale.entity.MultipleQueryProduct;
import com.transpos.sale.entity.OrderObject;
import com.transpos.sale.entity.state.OrderStatusFlag;
import com.transpos.sale.ui.food.FoodActivity;
import com.transpos.sale.ui.navi.FaceLoginActivity;
import com.transpos.sale.ui.scan.manger.OrderItemManger;
import com.transpos.sale.ui.scan.manger.OrderManger;
import com.transpos.sale.ui.scan.mvp.PromptScanContract;
import com.transpos.sale.ui.scan.mvp.PromptScanPresenter;
import com.transpos.sale.ui.scan.vm.InputDialogVM;
import com.transpos.sale.utils.CountTimer;
import com.transpos.sale.utils.KeyConstrant;
import com.transpos.sale.utils.ToolScanner;
import com.transpos.sale.utils.UiUtils;
import com.transpos.sale.view.SpacesItemDecoration;
import com.transpos.sale.view.dialog.HandInputDialog;
import com.transpos.sale.view.dialog.SpecsSelectDialog;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.OnClick;

public class PromptScanCodeActivity extends BaseMvpActivity<PromptScanPresenter> implements ToolScanner.OnScanSuccessListener, PromptScanContract.View, BaseQuickAdapter.OnItemChildClickListener {
    private CountTimer mTimeCount;
    @BindView(R.id.tv_deal)
    TextView tv_deal;
    @BindView(R.id.group)
    Group mGroup;
    @BindView(R.id.container_croup)
    Group mContainerGroup;
    @BindView(R.id.layout_product)
    View layout_product;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_total_num)
    TextView tv_total_num;
    @BindView(R.id.tv_total_price)
    TextView tv_total_price;
    @BindView(R.id.tv_member_name)
    TextView tv_member_name;


    private boolean isAddProduct;
    private ScanCodeQueryAdapter adapter;
    private Member mMember;
    private float[] vipPriceArr;
    private HandInputDialog mHandInputDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_prompt_scan_code;
    }

    @Override
    protected void initView() {
        super.initView();
//        mGroup.setVisibility(View.GONE);
        adapter = new ScanCodeQueryAdapter( this){
            @Override
            protected void convert(@NotNull BaseViewHolder helper, @org.jetbrains.annotations.Nullable MultipleQueryProduct item) {
                super.convert(helper, item);
            }
        };
        adapter.setOnItemChildClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(UiUtils.dp2px(5,this)));
        mRecyclerView.setAdapter(adapter);
        InputDialogVM vm = ViewModelProviders.of(this).get(InputDialogVM.class);
        vm.getLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String barcode) {
                if(mTimeCount != null && mTimeCount.getCurSec() < 20){
                    restartCount();
                }
                presenter.queryProductByBarcode(barcode);
            }
        });
        vm.getSpecsData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String json) {
                MultipleQueryProduct product = GsonHelper.fromJson(json, MultipleQueryProduct.class);
                animationEnd(product);
            }
        });
    }


    @Override
    protected void initData() {
        super.initData();
        String reslut = getString(R.string.txt_actionbar_content);
        tv_deal.setVisibility(View.VISIBLE);
        tv_deal.setText(String.format(reslut,"300"));
        mTimeCount = new CountTimer(300*1000L,1000L,this,tv_deal,false){
            @Override
            public void onFinish() {
                OrderManger.INSTANCE.getOrderBean().setOrderStatus(OrderStatusFlag.CANCEL_STATE);
                OrderObjectDbManger.INSTANCE.update(OrderManger.INSTANCE.getOrderBean());
                OrderManger.INSTANCE.clear();
                super.onFinish();
            }
        };
        mTimeCount.start();
        String member_str = getIntent().getStringExtra(KeyConstrant.KEY_MEMBER);
        if(!TextUtils.isEmpty(member_str)){
            mMember = GsonHelper.fromJson(member_str,Member.class);
            adapter.setMMember(mMember);
            tv_member_name.setVisibility(View.VISIBLE);
            tv_member_name.setText(com.transpos.sale.utils.StringUtils.Companion.getLast2str(mMember.getName()));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mTimeCount != null){
            mTimeCount.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTimeCount != null){
            mTimeCount.cancel();
            mTimeCount = null;
        }
    }

    @Override
    protected ToolScanner getScanner() {
        return new ToolScanner(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        restartCount();
    }

    private void restartCount(){
        if(mTimeCount != null){
            mTimeCount.cancel();
            mTimeCount = new CountTimer(300*1000L,1000L,this,tv_deal,false);
            mTimeCount.start();
        }
    }

    @OnClick({R.id.tv_deal,R.id.tv_hand_input,R.id.btn_pay})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.tv_deal:
                //更新取消订单状态
                OrderManger.INSTANCE.getOrderBean().setOrderStatus(OrderStatusFlag.CANCEL_STATE);
                OrderObjectDbManger.INSTANCE.update(OrderManger.INSTANCE.getOrderBean());
                OrderManger.INSTANCE.clear();
                startActivity(FoodActivity.class);
                break;
            case R.id.tv_hand_input:
                mHandInputDialog = new HandInputDialog
                        .Builder()
                        .setHeightParams(ViewGroup.LayoutParams.MATCH_PARENT)
                        .build();
                mHandInputDialog.show(getSupportFragmentManager(),"input");
                break;
            case R.id.btn_pay:
                if(adapter.getData().isEmpty()){
                    UiUtils.showToastShort("请添加商品");
                    return;
                }
                Intent intent = new Intent(this, AccountsActivity.class);
                AccountsBean accountsBean = new AccountsBean()
                        .setTotalCount((Integer) tv_total_num.getTag())
                        .setTotalMoney((String) tv_total_price.getTag())
                        .setReduction(vipPriceArr == null ? "0" : com.transpos.sale.utils.StringUtils.Companion.formatPrice(vipPriceArr[1]));
                intent.putExtra(KeyConstrant.KEY_ACCOUNT_BEAN,accountsBean);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onScanSuccess(String barcode) {
        if(StringUtils.isNoneBlank(barcode)){
            if(mTimeCount != null && mTimeCount.getCurSec() < 20){
                restartCount();
            }
            presenter.queryProductByBarcode(barcode);
        }
    }

    @Override
    protected PromptScanPresenter createPresenter() {
        PromptScanPresenter p = new PromptScanPresenter();
        p.createOrder();
        return  p;
    }

    @Override
    public void queryProductSuccess(List<MultipleQueryProduct> products) {
        if(!isAddProduct){
            isAddProduct = true;
            mContainerGroup.setVisibility(View.VISIBLE);
            mGroup.setVisibility(View.GONE);
        }
        if(products != null){
            if(products.size() == 1){
                Log.e("debug", "queryProductSuccess: "+products.get(0) );
                presenter.startAnimationAndLoad(layout_product,products.get(0));
            } else {
                //多规格
                SpecsSelectDialog selectDialog = new SpecsSelectDialog.Builder().build();
                Bundle bundle = new Bundle();
                bundle.putString(KeyConstrant.KEY_MULTIPLE_SPECS_PRODUCT, GsonHelper.toJson(products));
                selectDialog.setArguments(bundle);
                selectDialog.show(getSupportFragmentManager(),"specs");
            }
            if(mHandInputDialog != null){
                mHandInputDialog.dismiss();
                mHandInputDialog = null;
            }
        }
    }

    @Override
    public void animationEnd(MultipleQueryProduct product) {
        List<MultipleQueryProduct> list = adapter.getData();
        if(list.contains(product)){
            int i = 0;
            for (MultipleQueryProduct bean : list) {
                if(bean.equals(product)){
                    bean.setmAmount(bean.getmAmount()+1);
                    updateTotal(product,OrderItemManger.AddItemState.MODIFY,i);
                    break;
                }
                i ++;
            }

        } else {
            list.add(product);
            updateTotal(product,OrderItemManger.AddItemState.ADD,0);
        }
        adapter.notifyDataSetChanged();
    }

    private void updateTotal(MultipleQueryProduct product, OrderItemManger.AddItemState state, int position){
        List<MultipleQueryProduct> list = adapter.getData();
        vipPriceArr = presenter.getVipPrice(mMember, list);
        String totalPrice = com.transpos.sale.utils.StringUtils.Companion.formatPrice(vipPriceArr[0]);
        tv_total_price.setText(getString(R.string.txt_scan_total_price, totalPrice));
        tv_total_num.setText(getString(R.string.txt_scan_total_num,presenter.getTotalAmount(list)+""));
        tv_total_num.setTag(presenter.getTotalAmount(list));
        tv_total_price.setTag(totalPrice);
        presenter.setOrderValue(list,vipPriceArr[1],Float.parseFloat(totalPrice),mMember);
        presenter.addOrderItem(product,state,position,mMember,OrderItemManger.JoinTypeEnmu.SCANCODE);
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        List<MultipleQueryProduct> list = adapter.getData();
        MultipleQueryProduct item = list.get(position);
        switch (view.getId()){
            case R.id.iv_add:
                if(item.getWeightFlag() == 1){
                    //称重商品不能编辑
                    return;
                }
                item.setmAmount(item.getmAmount()+1);
                updateTotal(item,OrderItemManger.AddItemState.MODIFY,position);
                break;
            case R.id.iv_sub:
                int count = item.getmAmount() - 1;
                if(count == 0){
                    list.remove(position);
                    updateTotal(item,OrderItemManger.AddItemState.REMOVE,position);
                } else {
                    item.setmAmount(count);
                    updateTotal(item,OrderItemManger.AddItemState.MODIFY,position);
                }
                break;
            case R.id.iv_delete:
                list.remove(position);
                updateTotal(item,OrderItemManger.AddItemState.REMOVE,position);
                break;
        }
        adapter.notifyDataSetChanged();
    }
}
