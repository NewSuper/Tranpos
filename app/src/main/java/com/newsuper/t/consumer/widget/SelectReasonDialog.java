package com.newsuper.t.consumer.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.QuitOrderResason;
import com.newsuper.t.consumer.function.inter.ISelectQuitReason;
import com.newsuper.t.consumer.function.selectgoods.adapter.DividerDecoration;
import com.newsuper.t.consumer.function.selectgoods.adapter.MyItemAnimator;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectReasonDialog extends Dialog implements View.OnClickListener {

    @BindView(R.id.rcl_reason)
    RecyclerView rclReason;
    @BindView(R.id.ll_quit)
    LinearLayout llQuit;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;

    private ArrayList<QuitOrderResason> resasons;
    private Context mContext;
    private ISelectQuitReason iSelectQuitReason;

    public SelectReasonDialog(Context context, int themeResId, ArrayList<QuitOrderResason> resasons, ISelectQuitReason iSelectQuitReason) {
        super(context, themeResId);
        this.mContext = context;
        this.resasons = resasons;
        this.iSelectQuitReason = iSelectQuitReason;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_reason);
        ButterKnife.bind(this);
        rclReason.setItemAnimator(new MyItemAnimator());
        rclReason.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerDecoration cartDivider = new DividerDecoration();
        cartDivider.setDividerColor(Color.parseColor("#EBEBEB"));
        rclReason.addItemDecoration(cartDivider);
        ReasonAdapter adapter = new ReasonAdapter(resasons);
        rclReason.setAdapter(adapter);
        llQuit.setOnClickListener(this);
    }


    public class ReasonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        public ArrayList<QuitOrderResason> list;
        public ReasonAdapter(ArrayList<QuitOrderResason> list){
            this.list=list;
            Log.e("list....", new Gson().toJson(list));
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View viewReason = LayoutInflater.from(mContext).inflate(R.layout.item_reason, parent, false);
            ReasonViewHolder goodsHolder = new ReasonViewHolder(viewReason);
            return goodsHolder;
        }

        public class ReasonViewHolder extends RecyclerView.ViewHolder {
            public TextView tv_content;
            public ImageView iv_select;
            public RelativeLayout rl_root;

            public ReasonViewHolder(View itemView) {
                super(itemView);
                tv_content = (TextView) itemView.findViewById(R.id.tv_content);
                iv_select = (ImageView) itemView.findViewById(R.id.iv_select);
                rl_root = (RelativeLayout) itemView.findViewById(R.id.rl_root);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
            if (payloads.isEmpty()) {
                onBindViewHolder(holder, position);
            } else {
                ReasonViewHolder reasonHolder = (ReasonViewHolder) holder;
                QuitOrderResason resason = list.get(position);
                if (resason.isSelected) {
                    reasonHolder.iv_select.setImageResource(R.mipmap.icon_select);
                } else {
                    reasonHolder.iv_select.setImageResource(R.mipmap.icon_unselect);
                }
            }
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            ReasonViewHolder reasonHolder = (ReasonViewHolder) holder;
            if (null != reasonHolder) {
                QuitOrderResason resason = list.get(position);
                reasonHolder.tv_content.setText(resason.content);
                if (resason.isSelected) {
                    reasonHolder.iv_select.setImageResource(R.mipmap.icon_select);
                } else {
                    reasonHolder.iv_select.setImageResource(R.mipmap.icon_unselect);
                }
                reasonHolder.rl_root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != iSelectQuitReason) {
                            iSelectQuitReason.quitReason(position + 1);
                        }
                        for(int i=0;i<list.size();i++){
                            QuitOrderResason tmp=list.get(i);
                            if(i==position){
                                tmp.isSelected=true;
                            }else{
                                tmp.isSelected=false;
                            }
                            notifyDataSetChanged();
                        }
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }


    }

    @Override
    public void show() {
        super.show();
        animationShow(300);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        animationHide(300);
    }

    private void animationShow(int mDuration) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(llRoot, "translationY", 1000, 0).setDuration(mDuration)
        );
        animatorSet.start();
    }

    private void animationHide(int mDuration) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(llRoot, "translationY", 0, 1000).setDuration(mDuration)
        );
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                SelectReasonDialog.super.dismiss();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_quit:
                dismiss();
                break;
        }
    }


}
