package com.transpos.market.view;

import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.widget.TextView;

import com.transpos.market.R;
import com.transpos.market.adapter.PresentationAdapter;
import com.transpos.market.entity.MultipleQueryProduct;

import java.util.ArrayList;
import java.util.List;

public class PayMoneyPresentation extends Presentation {
    private Context context;
    PresentationAdapter presentationAdapter;
    List<MultipleQueryProduct> productList = new ArrayList<>();

    public PayMoneyPresentation(Context outerContext, Display display) {
        super(outerContext, display);
        this.context = outerContext;
    }

    public PayMoneyPresentation(Context outerContext, Display display, int theme) {
        super(outerContext, display, theme);
        this.context = outerContext;
    }

    TextView tv_pnum;
    TextView tv_ptotalPrice;
    TextView tv_pvipNumber;
    TextView tv_pvipName;
    TextView tv_pvipoint;
    TextView tv_ptotalMoney;
    TextView tv_pcouponMoney;
    TextView tv_pyingshou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presentation_goods);

        RecyclerView rvPresentation = findViewById(R.id.rv_presentation);
        tv_pnum = findViewById(R.id.tv_pnum);
        tv_ptotalPrice = findViewById(R.id.tv_ptotalPrice);
        tv_pvipNumber = findViewById(R.id.tv_pvipNumber);
        tv_pvipName = findViewById(R.id.tv_pvipName);
        tv_pvipoint = findViewById(R.id.tv_pvipoint);
        tv_ptotalMoney = findViewById(R.id.tv_ptotalMoney);
        tv_pcouponMoney = findViewById(R.id.tv_pcouponMoney);
        tv_pyingshou = findViewById(R.id.tv_pyingshou);
        rvPresentation.setLayoutManager(new LinearLayoutManager(context));
        rvPresentation.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        presentationAdapter = new PresentationAdapter(context);
        rvPresentation.setAdapter(presentationAdapter);
    }

    public void setDisplay(List<MultipleQueryProduct> list, String num, String totalPrice, String youhui, String cardNo, String cardName, String cardPoint) {
        if (list != null) {
            productList.clear();
            productList.addAll(list);
            productList = presentationAdapter.getData();
            presentationAdapter.notifyDataSetChanged();
            tv_pnum.setText("总件数：" + num + "件");
            tv_ptotalPrice.setText("￥" + totalPrice);
            tv_pvipName.setText("会员姓名："+cardName);
            tv_pvipNumber.setText("会员号："+cardNo);
            tv_pvipoint.setText("会员积分："+cardPoint);
            tv_ptotalMoney.setText("￥" + totalPrice);
            tv_pcouponMoney.setText("￥" + youhui);
            tv_pyingshou.setText("￥" + totalPrice);
        }
    }
}
