package com.newsuper.t.consumer.function.order.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseFragment;
import com.newsuper.t.consumer.bean.OrderBean;
import com.newsuper.t.consumer.function.inter.IComment;
import com.newsuper.t.consumer.function.inter.IOnPay;
import com.newsuper.t.consumer.function.order.activity.MyOrderActivity;
import com.newsuper.t.consumer.function.order.callback.OrderCallBack;
import com.newsuper.t.consumer.function.order.activity.SelectPayTypeActivity;
import com.newsuper.t.consumer.function.selectgoods.activity.SelectGoodsActivity3;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.UIUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private BaseFragment fragment;
    private ArrayList<OrderBean.OrderInfo> orderList;
    private IShowEditDialog iShowEditDialog;
    private IOnPay iOnPay;
    private IFindMonthAgoOrder iFindMonthAgoOrder;
    private IComment iComment;
    private IOrderAgain iOrderAgain;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_FOOTER = 2;
    private boolean isLoadAll = false;
    private String isFetchHistory;

    public OrderAdapter(BaseFragment fragment, ArrayList<OrderBean.OrderInfo> orderList) {
        super();
        this.fragment = fragment;
        this.orderList = orderList;
    }

    @Override
    public int getItemCount() {
        return orderList.size() + 1;
    }


    public void setIShowEditDialog(IShowEditDialog iShowEditDialog) {
        this.iShowEditDialog = iShowEditDialog;
    }

    public void setiOrderAgain(IOrderAgain iOrderAgain) {
        this.iOrderAgain = iOrderAgain;
    }

    public void setIFindMonthAgoOrder(IFindMonthAgoOrder iFindMonthAgoOrder) {
        this.iFindMonthAgoOrder = iFindMonthAgoOrder;
    }


    public void setIOnPay(IOnPay iOnPay) {
        this.iOnPay = iOnPay;
    }

    public void setIComment(IComment iComment) {
        this.iComment = iComment;
    }


    public void setOrderListData(ArrayList<OrderBean.OrderInfo> list) {
        if (list != null) {
            refreshItemData(list);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() - 1 == position) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }

    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_root)
        LinearLayout llRoot;
        @BindView(R.id.tv_load_more)
        TextView tvLoadMore;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            View footView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_footer_order, parent, false);
            return new FooterViewHolder(footView);
        } else {
            View view = LayoutInflater.from(fragment.getContext()).inflate(R.layout.adapter_order, null);
            return new ShopViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (holder instanceof FooterViewHolder) {
            FooterViewHolder holder1 = (FooterViewHolder) holder;
            if (isLoadAll) {
                if("0".equals(isFetchHistory)){
                    holder1.tvLoadMore.setText("点击查看一个月以前的订单");
                    holder1.tvLoadMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (null != iFindMonthAgoOrder) {
                                iFindMonthAgoOrder.findMonthAgoOrder();
                            }
                        }
                    });
                }else{
                    holder1.tvLoadMore.setText("没有更多订单啦");
                }
            } else {
                holder1.tvLoadMore.setText("加载中...");
            }
            return;
        }

        ShopViewHolder mHolder = (ShopViewHolder) holder;
        final OrderBean.OrderInfo orderInfo = orderList.get(position);
        mHolder.tvShopName.setText(orderInfo.shopname);
        mHolder.order_time.setText(orderInfo.init_date);
        View payView = View.inflate(fragment.getContext(), R.layout.item_textview, null);
        ((TextView) payView.findViewById(R.id.content)).setText("继续支付");
        payView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fragment.getContext(), SelectPayTypeActivity.class);
                intent.putExtra("shop_name",orderInfo.shopname);
                intent.putExtra("money",orderInfo.total_price);
                intent.putExtra("order_id",orderInfo.id);
                intent.putExtra("shop_id", orderInfo.shop_id);
                fragment.startActivity(intent);
            }
        });
        View cancelView = View.inflate(fragment.getContext(), R.layout.item_textview_del, null);
        ((TextView) cancelView.findViewById(R.id.content)).setText("取消订单");
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != iShowEditDialog) {
                    iShowEditDialog.showEditDialog(orderInfo.order_no, "quit_order",position);
                }
            }
        });
        View delView = View.inflate(fragment.getContext(), R.layout.item_textview_del, null);
        ((TextView) delView.findViewById(R.id.content)).setText("删除");

        delView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除订单
                if (null != iShowEditDialog) {
                    iShowEditDialog.showEditDialog(orderInfo.order_no, "delete",position);
                }
            }
        });
        View caommentView = View.inflate(fragment.getContext(), R.layout.item_textview_conment, null);
        ((TextView) caommentView.findViewById(R.id.content)).setText("评价");
        caommentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //评价
                if (null != iComment) {
                    iComment.comment(orderInfo.id, orderInfo.shop_id,orderInfo.order_no);
                }
            }
        });

        View againView= View.inflate(fragment.getContext(), R.layout.item_textview_again, null);
        againView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iOrderAgain != null){
                    iOrderAgain.onAgain(orderInfo.shop_id,orderInfo.id);
                }
            }
        });
        switch (orderInfo.order_status) {
            case "NOTPAID":
                //动态添加按钮
                mHolder.ll_add_button.removeAllViews();
                View viewp = new View(fragment.getActivity());
                viewp.setLayoutParams(new ViewGroup.LayoutParams(UIUtils.dip2px(8), ViewGroup.LayoutParams.WRAP_CONTENT));
                mHolder.ll_add_button.addView(viewp);
                mHolder.ll_add_button.addView(againView);
                mHolder.ll_add_button.addView(payView);
                break;
            case "OPEN":
                mHolder.ll_add_button.removeAllViews();
                mHolder.ll_add_button.addView(cancelView);
                View view = new View(fragment.getActivity());
                view.setLayoutParams(new ViewGroup.LayoutParams(UIUtils.dip2px(8), ViewGroup.LayoutParams.WRAP_CONTENT));
                mHolder.ll_add_button.addView(view);
                mHolder.ll_add_button.addView(againView);
                break;
            case "CONFIRMED":
                mHolder.ll_add_button.removeAllViews();
                mHolder.ll_add_button.addView(againView);
                break;
            case "SUCCEEDED":
                mHolder.ll_add_button.removeAllViews();
                mHolder.ll_add_button.addView(delView);
                View sview = new View(fragment.getActivity());
                sview.setLayoutParams(new ViewGroup.LayoutParams(UIUtils.dip2px(8), ViewGroup.LayoutParams.WRAP_CONTENT));
                View sview1 = new View(fragment.getActivity());
                sview1.setLayoutParams(new ViewGroup.LayoutParams(UIUtils.dip2px(8), ViewGroup.LayoutParams.WRAP_CONTENT));
                mHolder.ll_add_button.addView(sview);
                mHolder.ll_add_button.addView(againView);
                if ("1".equals(orderInfo.is_opencomment)) {
                    if ("1".equals(orderInfo.is_comment)) {
                        mHolder.ll_add_button.removeView(caommentView);
                    } else {
                        mHolder.ll_add_button.addView(sview1);
                        mHolder.ll_add_button.addView(caommentView);
                    }
                } else {
                    mHolder.ll_add_button.removeView(caommentView);
                }
                break;
            case "PAYCANCEL":
            case "FAILED":
            case "CANCELLED":
                mHolder.ll_add_button.removeAllViews();
                mHolder.ll_add_button.addView(delView);
                View sview2 = new View(fragment.getActivity());
                sview2.setLayoutParams(new ViewGroup.LayoutParams(UIUtils.dip2px(8), ViewGroup.LayoutParams.WRAP_CONTENT));
                mHolder.ll_add_button.addView(sview2);
                mHolder.ll_add_button.addView(againView);
                break;
            case "REFUNDING":
                mHolder.ll_add_button.removeAllViews();
                mHolder.ll_add_button.addView(againView);
                break;
        }
        mHolder.tvOrderStatus.setText(orderInfo.statusname);
        if (null != orderInfo.fooddetail && orderInfo.fooddetail.size() > 0) {
            if (orderInfo.fooddetail.size() > 5) {
                mHolder.llGoodsList.removeAllViews();
                for (int i = 0; i < 5; i++) {
                    OrderBean.Goods goods = orderInfo.fooddetail.get(i);
                    View view = View.inflate(fragment.getContext(), R.layout.item_goods, null);
                    ((TextView) view.findViewById(R.id.goods_name)).setText(goods.foodname);
                    ((TextView) view.findViewById(R.id.goods_num)).setText("X" + goods.quantity);
                    mHolder.llGoodsList.addView(view);
                }
                View view = View.inflate(fragment.getContext(), R.layout.item_goods, null);
                ((TextView) view.findViewById(R.id.goods_name)).setText("......");
                mHolder.llGoodsList.addView(view);
            } else {
                mHolder.llGoodsList.removeAllViews();
                for (int i = 0; i < orderInfo.fooddetail.size(); i++) {
                    OrderBean.Goods goods = orderInfo.fooddetail.get(i);
                    View view = View.inflate(fragment.getContext(), R.layout.item_goods, null);
                    ((TextView) view.findViewById(R.id.goods_name)).setText(goods.foodname);
                    ((TextView) view.findViewById(R.id.goods_num)).setText("X" + goods.quantity);
                    mHolder.llGoodsList.addView(view);
                }
            }
        }
        mHolder.tvGoodsNum.setText("共" + orderInfo.foodcount + "件商品，实付");
        mHolder.shifu.setText(FormatUtil.numFormat(orderInfo.total_price));
        mHolder.ll_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fragment.getContext(), SelectGoodsActivity3.class);
                intent.putExtra("from_page", "order_list");
                intent.putExtra("shop_id", orderInfo.shop_id);
                fragment.startActivity(intent);
            }
        });
        mHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fragment.getContext(), MyOrderActivity.class);
                intent.putExtra("order_id", orderInfo.id);
                intent.putExtra("order_no", orderInfo.order_no);
                fragment.startActivityForResult(intent, 2);
            }
        });
    }

    public void setIsLoadAll(boolean b,String isFetchHistory) {
        this.isLoadAll = b;
        this.isFetchHistory=isFetchHistory;
        notifyDataSetChanged();
    }

    public interface IShowEditDialog {
        void showEditDialog(String order_id, String flag, int position);
    }

    public interface IFindMonthAgoOrder {
        void findMonthAgoOrder();
    }
    public interface IOrderAgain {
        void onAgain(String shop_id, String order_id);
    }

    public class ShopViewHolder extends RecyclerView.ViewHolder {
        boolean isMore = false;
        View mView;
        @BindView(R.id.tv_shop_name)
        TextView tvShopName;
        @BindView(R.id.tv_order_status)
        TextView tvOrderStatus;
        @BindView(R.id.ll_goods_list)
        LinearLayout llGoodsList;
        @BindView(R.id.tv_goods_num)
        TextView tvGoodsNum;
        @BindView(R.id.shifu)
        TextView shifu;
        @BindView(R.id.order_time)
        TextView order_time;
        @BindView(R.id.ll_add_button)
        LinearLayout ll_add_button;
        @BindView(R.id.ll_shop)
        LinearLayout ll_shop;

        public ShopViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    public void refreshItemData(ArrayList<OrderBean.OrderInfo> lists) {
        if (lists != null) {
            orderList.clear();
            orderList.addAll(lists);
            final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new OrderCallBack(orderList, lists));
            diffResult.dispatchUpdatesTo(this);
        }
    }

}