package com.newsuper.t.consumer.function.vip.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.BalanceDetailBean;
import com.newsuper.t.consumer.utils.FormatUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/5 0005.
 * 余额明细
 */

public class BalanceAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<BalanceDetailBean.HistoryListBean> list;
    public BalanceAdapter(Context context, ArrayList<BalanceDetailBean.HistoryListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BalanceViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_balance_detail, null);
            holder = new BalanceViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (BalanceViewHolder)convertView.getTag();
        }
        BalanceDetailBean.HistoryListBean bean = list.get(position);
        holder.tvTime.setText(bean.change_date);
        double count = bean.new_balance - bean.old_balance;
        if (count >=0){
            if (count == 0){
                holder.tvMoney.setText(getFormatData(count));
            }else {
                holder.tvMoney.setText( "+" + getFormatData(count));
            }
        }else {
            holder.tvMoney.setText("-" + getFormatData(Math.abs(count)));
        }
        changeType(bean.change_type,holder.tvTitle,holder.ivBalance);
        return convertView;
    }

    private void changeType(int type,TextView tvTitle,ImageView imageView){
        tvTitle.setTextColor(ContextCompat.getColor(context,R.color.text_color_99));
        switch (type){
            //后台充值
            case 1:
                tvTitle.setText("后台充值");
                imageView.setImageResource(R.mipmap.icon_chongzhi_2x);
                break;
            //消费修改
            case 2:
                tvTitle.setText("消费修改");
                imageView.setImageResource(R.mipmap.icon_xiaofei_2x);
                break;
            //微信在线充值
            case 3:
                tvTitle.setText("微信在线充值");
                imageView.setImageResource(R.mipmap.icon_chongzhi_2x);
                break;
            //取消订单返回
            case 4:
                tvTitle.setText("取消订单返回");
                imageView.setImageResource(R.mipmap.icon_tuikuan_2x);
                break;
            //失败订单返回
            case 5:
                tvTitle.setText("失败订单返回");
                imageView.setImageResource(R.mipmap.icon_tuikuan_2x);
                break;
            //充值送金额
            case 6:
                tvTitle.setText("充值送金额");
                imageView.setImageResource(R.mipmap.icon_zengsong_2x);
                break;
            //退出拼单返还
            case 7:
                tvTitle.setText("退出拼单返还");
                imageView.setImageResource(R.mipmap.icon_tuikuan_2x);
                break;
            //提交拼单优惠
            case 8:
                tvTitle.setText("提交拼单优惠");
                imageView.setImageResource(R.mipmap.icon_youhui_2x);
                break;
            //后台人工退款
            case 9:
                tvTitle.setText("后台人工扣款");
                imageView.setImageResource(R.mipmap.icon_xiaofei_2x);
                break;
            //堂食下单
            case 10:
                tvTitle.setText("堂食下单");
                imageView.setImageResource(R.mipmap.balance_order);
                break;
            //堂食下单退款
            case 11:
                tvTitle.setText("堂食下单退款");
                imageView.setImageResource(R.mipmap.icon_tuikuan_2x);
                break;
            //团购下单
            case 12:
                tvTitle.setText("团购下单");
                imageView.setImageResource(R.mipmap.balance_order);
                break;
            //跑腿下单
            case 13:
                tvTitle.setText("跑腿下单");
                imageView.setImageResource(R.mipmap.balance_order);
                break;
            //取消跑腿下单返回
            case 14:
                tvTitle.setText("取消跑腿下单返回");
                imageView.setImageResource(R.mipmap.icon_tuikuan_2x);
                break;
            //智能机充值
            case 15:
                tvTitle.setText("智能机充值");
                imageView.setImageResource(R.mipmap.icon_chongzhi_2x);
                break;
            //智能机充值赠送
            case 16:
                tvTitle.setText("智能机充值赠送");
                imageView.setImageResource(R.mipmap.icon_zengsong_2x);
                break;
            //智能机刷卡消费
            case 17:
                tvTitle.setText("智能机刷卡消费");
                imageView.setImageResource(R.mipmap.icon_xiaofei_2x);
                break;
            //后台充值赠送金额
            case 18:
                tvTitle.setText("后台充值赠送金额");
                imageView.setImageResource(R.mipmap.icon_zengsong_2x);
                break;
            //顾客数据合并增加
            case 19:
                tvTitle.setText("顾客数据合并增加");
                imageView.setImageResource(R.mipmap.icon_youhui_2x);
                break;
            default:
                break;

        }
    }
    static class BalanceViewHolder {
        @BindView(R.id.iv_balance)
        ImageView ivBalance;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_time)
        TextView tvTime;

        BalanceViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    private DecimalFormat df = new DecimalFormat("#0.00");
    public String getFormatData( double d){
        String s = FormatUtil.numFormat(Double.parseDouble(df.format(d)) +"") ;
        return s;
    }
}
