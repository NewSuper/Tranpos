package com.newsuper.t.consumer.widget.CustomNoScrollListView;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.GoodsListBean;
import com.newsuper.t.consumer.bean.WShopCart2;
import com.newsuper.t.consumer.function.selectgoods.inter.IGoodsToDetailPage;
import com.newsuper.t.consumer.function.selectgoods.inter.IWShopCart;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.MemberUtil;
import com.newsuper.t.consumer.utils.UIUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Administrator on 2017/11/13 0013.
 */

public class GoodsListAdapter extends CustomAdapter {

    private Context mContext;
    private ArrayList<GoodsListBean.GoodsInfo> data;
    private DecimalFormat df;
    private IGoodsToDetailPage iToGoodsDetailPage;
    private WShopCart2 wShopCart;
    private IWShopCart iWShopCart;
    private String flag;
    private CustomNoScrollListView parent;

    public GoodsListAdapter(Context mContext, ArrayList<GoodsListBean.GoodsInfo> data, WShopCart2 wShopCart, String flag) {
        this.mContext = mContext;
        this.data = data;
        this.wShopCart = wShopCart;
        df = new DecimalFormat("#0.00");
        this.flag = flag;
    }
    public void setWShopCartListener(IWShopCart iWShopCart) {
        this.iWShopCart = iWShopCart;
    }

    public void setIToDetailPage(IGoodsToDetailPage iToGoodsDetailPage) {
        this.iToGoodsDetailPage = iToGoodsDetailPage;
    }

    public void setParentLayout(CustomNoScrollListView parent){
        this.parent=parent;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position) {
        final GoodsListBean.GoodsInfo goods = data.get(position);
        final View convertView = LayoutInflater.from(mContext).inflate(R.layout.item_goods_list, null);
        ImageView iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
        TextView tvQi = (TextView) convertView.findViewById(R.id.tvQi);
        TextView tvUnit = (TextView) convertView.findViewById(R.id.tvUnit);
        final TextView tvCount = (TextView) convertView.findViewById(R.id.tvCount);
        TextView tv_limit = (TextView) convertView.findViewById(R.id.tv_limit);
        final TextView tvShowCount = (TextView) convertView.findViewById(R.id.tvShowCount);
        LinearLayout ll_sale_out = (LinearLayout) convertView.findViewById(R.id.ll_sale_out);
        LinearLayout ll_stop_sale = (LinearLayout) convertView.findViewById(R.id.ll_stop_sale);
        LinearLayout ll_edit_goods = (LinearLayout) convertView.findViewById(R.id.ll_edit_goods);
        final LinearLayout llMinus = (LinearLayout) convertView.findViewById(R.id.llMinus);
        LinearLayout llAdd = (LinearLayout) convertView.findViewById(R.id.llAdd);
        LinearLayout llTip = (LinearLayout) convertView.findViewById(R.id.llTip);
        LinearLayout item_root = (LinearLayout) convertView.findViewById(R.id.item_root);
        RelativeLayout rl_select_nature = (RelativeLayout) convertView.findViewById(R.id.rl_select_nature);
        TextView tv_min_buy = (TextView) convertView.findViewById(R.id.tv_min_buy);
        tv_name.setText(goods.name);
        //加载网络图片
        UIUtils.glideAppLoad(mContext,goods.img,R.mipmap.common_def_food,iv_img);
        item_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != iToGoodsDetailPage) {
                    iToGoodsDetailPage.toGoodsDetailPage(goods, position);
                }
            }
        });

        int count = wShopCart.getGoodsCount(goods.id);

        String price = goods.price ;
        if ("1".equals(goods.member_price_used)) {
            price = MemberUtil.getMemberPriceStringWPage(goods);
        }
        if (null != goods.nature && goods.nature.size() > 0) {
            //商品默认属性价格
            if (!TextUtils.isEmpty(goods.min_price)) {
                tvPrice.setText(FormatUtil.numFormat(Float.parseFloat(price) + Float.parseFloat(goods.min_price) + ""));
            } else {
                tvPrice.setText(FormatUtil.numFormat(price));
            }
            tvQi.setVisibility(View.VISIBLE);
        } else {
            tvPrice.setText(FormatUtil.numFormat(price));
            tvQi.setVisibility(View.GONE);
        }

        //添加单位
        if (!TextUtils.isEmpty(goods.unit)) {
            tvUnit.setVisibility(View.VISIBLE);
            tvUnit.setText("/" + goods.unit);
        } else {
            tvUnit.setVisibility(View.GONE);
        }

        //先判断店铺是否正常营业
        if (1 == goods.worktime) {
            llTip.setVisibility(View.GONE);
            //判断限购活动是否开启
            if ("1".equals(goods.is_limitfood)) {
                tv_limit.setVisibility(View.VISIBLE);
                rl_select_nature.setVisibility(View.GONE);
                ll_edit_goods.setVisibility(View.GONE);
            } else {
                tv_limit.setVisibility(View.GONE);
                if ("taocan".equals(goods.type_id)) {
                    if (count < 1) {
                        tvShowCount.setVisibility(View.GONE);
                    } else {
                        tvShowCount.setVisibility(View.VISIBLE);
                        tvShowCount.setText(count + "");
                    }
                    rl_select_nature.setVisibility(View.VISIBLE);
                    ll_edit_goods.setVisibility(View.GONE);
                } else {
                    if (null != goods.nature && goods.nature.size() > 0 && goods.is_nature.equals("1")) {
                        rl_select_nature.setVisibility(View.VISIBLE);
                        ll_edit_goods.setVisibility(View.GONE);
                        if (count < 1) {
                            tvShowCount.setVisibility(View.GONE);
                        } else {
                            tvShowCount.setVisibility(View.VISIBLE);
                           tvShowCount.setText(count + "");
                        }
                    } else {
                        rl_select_nature.setVisibility(View.GONE);
                        ll_edit_goods.setVisibility(View.VISIBLE);
                        if (count < 1) {
                            llMinus.setVisibility(View.GONE);
                            tvCount.setVisibility(View.GONE);
                        } else {
                            llMinus.setVisibility(View.VISIBLE);
                            tvCount.setVisibility(View.VISIBLE);
                            tvCount.setText(count + "");
                        }
                    }
                }
            }
            //判断商品状态
            if ("taocan".equals(goods.type_id)) {
                ll_stop_sale.setVisibility(View.GONE);
            } else {
                if (goods.status.equals("NORMAL")) {
                    ll_stop_sale.setVisibility(View.GONE);
                } else {
                    ll_stop_sale.setVisibility(View.VISIBLE);
                    tv_limit.setVisibility(View.GONE);
                    rl_select_nature.setVisibility(View.GONE);
                    ll_edit_goods.setVisibility(View.GONE);
                }
            }
        } else {
            tv_limit.setVisibility(View.GONE);
            rl_select_nature.setVisibility(View.GONE);
            ll_edit_goods.setVisibility(View.GONE);
            llTip.setVisibility(View.VISIBLE);
            //判断商品状态
            if (!"taocan".equals(goods.type_id)) {
                if (goods.status.equals("NORMAL")) {
                    ll_stop_sale.setVisibility(View.GONE);
                } else {
                    ll_stop_sale.setVisibility(View.VISIBLE);
                    tv_limit.setVisibility(View.GONE);
                }
            } else {
                ll_stop_sale.setVisibility(View.GONE);
            }
        }

        //判断库存
        if (!"taocan".equals(goods.type_id)){
            if ("1".equals(goods.stockvalid)) {
                if (goods.stock ==0) {
                    if (goods.status.equals("NORMAL")){
                        ll_sale_out.setVisibility(View.VISIBLE);
                        tv_limit.setVisibility(View.GONE);
                        rl_select_nature.setVisibility(View.GONE);
                        ll_edit_goods.setVisibility(View.GONE);
                    }
                }
            }
        }

        //最小起购数
        if (!StringUtils.isEmpty(goods.min_buy_count) && Integer.parseInt(goods.min_buy_count) > 1){
            int c = Integer.parseInt(goods.min_buy_count);
            tv_min_buy.setText(c + "份起购");
            tv_min_buy.setVisibility(View.VISIBLE);
        }else {
            tv_min_buy.setVisibility(View.GONE);
        }
        llTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goods.worktime==2){
                    if(null!=iWShopCart){
                        iWShopCart.showTipInfo("店铺暂停营业，该商品暂停购买");
                    }
                }else{
                    if(null!=iWShopCart){
                        iWShopCart.showTipInfo("店铺休息中，该商品暂停购买");
                    }
                }
            }
        });

        rl_select_nature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("iWShopCart"," setOnClickListener rl_select_nature === ");
                if ("taocan".equals(goods.type_id)) {
                    //显示套餐弹框
                    if (iWShopCart != null) {
                        iWShopCart.showNatureDialog(goods, position, flag);
                    }
                } else {
                    if ("1".equals(goods.stockvalid)) {
                        if (goods.stock > 0) {
                            //显示属性弹框
                            if (iWShopCart != null) {
                                iWShopCart.showNatureDialog(goods, position, flag);
                            }
                        } else {
                            UIUtils.showToast("库存不足");
                        }
                    } else {
                        //显示属性弹框
                        if (iWShopCart != null) {
                            iWShopCart.showNatureDialog(goods, position, flag);
                        }
                    }
                }
            }
        });

        llAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("iWShopCart"," setOnClickListener llAdd === ");
                if (wShopCart.addShoppingSingle(goods)) {
                    //刷新单条数据
                    notifyItemView(position);
                    if (iWShopCart != null)
                      iWShopCart.add(goods);
                }
            }
        });

        llMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wShopCart.subShoppingSingle(goods)) {
                    Log.i("iWShopCart"," setOnClickListener llMinus === ");
                    //刷新单条数据
                    notifyItemView(position);
                    if (iWShopCart != null)
                        iWShopCart.remove(goods);
                }
            }
        });

        //点击item跳到商品详情页
        item_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != iToGoodsDetailPage) {
                    iToGoodsDetailPage.toGoodsDetailPage(goods, position);
                }
            }
        });

        return convertView;
    }


    public void notifyItemView(int pos) {
        View view=parent.getChildAt(pos);
        if(null!=view){
            TextView tvShowCount=(TextView)view.findViewById(R.id.tvShowCount);
            TextView tvCount=(TextView)view.findViewById(R.id.tvCount);
            LinearLayout llMinus=(LinearLayout)view.findViewById(R.id.llMinus);
            GoodsListBean.GoodsInfo goods = data.get(pos);
            int count = wShopCart.getGoodsCount(goods.id);
            if ("taocan".equals(goods.type_id)) {
                if (count < 1) {
                    tvShowCount.setVisibility(View.GONE);
                } else {
                    tvShowCount.setVisibility(View.VISIBLE);
                    tvShowCount.setText(count + "");
                }
            } else {
                if (null != goods.nature && goods.nature.size() > 0 && goods.is_nature.equals("1")) {
                    if (count < 1) {
                        tvShowCount.setVisibility(View.GONE);
                    } else {
                        tvShowCount.setVisibility(View.VISIBLE);
                        tvShowCount.setText(count + "");
                    }
                } else {
                    Log.i("iWShopCart","  Visibility ");
                    if (count < 1) {
                        llMinus.setVisibility(View.GONE);
                        tvCount.setVisibility(View.GONE);
                    } else {
                        llMinus.setVisibility(View.VISIBLE);
                        tvCount.setVisibility(View.VISIBLE);
                        tvCount.setText(count + "");
                    }
                }
            }
        }
    }

    public void notifyItemCount(){
        for(int i = 0;i < data.size();i++){
            if (wShopCart.getGoodsMap().containsKey(data.get(i).id)){
                notifyItemView(i);
                break;
            }else {
                notifyItemView(i);
            }
        }
    }

}
