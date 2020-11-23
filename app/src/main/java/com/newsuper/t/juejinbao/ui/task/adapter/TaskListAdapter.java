package com.newsuper.t.juejinbao.ui.task.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.juejinchain.android.R;
import com.juejinchain.android.module.ad.BaoquGameActivity;
import com.juejinchain.android.module.login.activity.GuideLoginActivity;
import com.juejinchain.android.module.movie.adapter.MyViewHolder;
import com.juejinchain.android.module.movie.utils.Utils;
import com.juejinchain.android.module.task.entity.TaskListEntity;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.ys.network.base.LoginEntity;
import com.ys.network.base.PagerCons;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class TaskListAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<TaskListEntity.DayListBean> items = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private onLastItemClickListener onLastItemClickListener;


    public void setOnLastItemClickListener(TaskListAdapter.onLastItemClickListener onLastItemClickListener) {
        this.onLastItemClickListener = onLastItemClickListener;
    }

    public TaskListAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public void update(List<TaskListEntity.DayListBean> itemList) {
        this.items = itemList;
        notifyDataSetChanged();
    }

    public void notifyItem(List<TaskListEntity.DayListBean> itemList, int position) {
        this.items = itemList;
        notifyItemChanged(position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemViewHolder(viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(items.get(i));
    }

    @Override
    public int getItemCount() {
        return null != items ? items.size() : 0;
    }

    public class ItemViewHolder extends MyViewHolder implements View.OnClickListener {

        private TaskListEntity.DayListBean bean;

        private LinearLayout llTitle;
        private TextView tvName;
        private TextView tvCoin;
        private ImageView ivCoin;
        private ImageView ivMore;
        private TextView tvFlag;
        private LinearLayout llDesc;
        private TextView tvDesc;
        private TextView tvTag;

        ItemViewHolder(ViewGroup parent) {
            super((ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_task_list, parent, false));
            llTitle = itemView.findViewById(R.id.ll_title);
            tvName = itemView.findViewById(R.id.tv_name);
            tvFlag = itemView.findViewById(R.id.tv_flag);
            tvCoin = itemView.findViewById(R.id.tv_coin);
            ivCoin = itemView.findViewById(R.id.iv_coin);
            ivMore = itemView.findViewById(R.id.iv_more);
            llDesc = itemView.findViewById(R.id.ll_desc);
            tvDesc = itemView.findViewById(R.id.tv_desc);
            tvTag = itemView.findViewById(R.id.tv_tag);
            llTitle.setOnClickListener(this);
            tvTag.setOnClickListener(this);
        }

        @Override
        public void setData(Object object) {
            super.setData(object);
            bean = (TaskListEntity.DayListBean) object;

            if (bean == null) {
                return;
            }

            if (bean.getType() == 100) {
                tvName.setText(bean.getTitle());
                tvFlag.setVisibility(View.VISIBLE);
                tvFlag.setText(bean.getAd_sign());
                tvCoin.setText(bean.getSub_title() == null ? "" : bean.getSub_title());
                ivCoin.setVisibility(View.GONE);
                return;
            } else {
                tvName.setText(bean.getName());
                tvFlag.setVisibility(View.GONE);
                if (bean.getReward_type() == 1) {
                    tvCoin.setText(String.format("+%s", bean.getValue()));
                    ivCoin.setVisibility(View.VISIBLE);
                } else {
                    tvCoin.setText(String.format("+%s 掘金宝", bean.getValue()));
                    ivCoin.setVisibility(View.GONE);
                }
                tvDesc.setText(bean.getDesc());
            }

            String selectTag = Paper.book().read(PagerCons.SELECT_TAG);
            // 展开显示标记的任务
            if (!TextUtils.isEmpty(selectTag) && !TextUtils.isEmpty(bean.getTag()) && selectTag.equals(bean.getTag())) {
                llDesc.setVisibility(View.VISIBLE);
                ivMore.setImageResource(R.mipmap.ic_more_down);
            } else {
                llDesc.setVisibility(View.GONE);
                ivMore.setImageResource(R.mipmap.ic_more_right);
            }

            if (bean.getDoX() == 1) {
                tvTag.setText("已完成");
                tvTag.setBackground(context.getResources().getDrawable(R.drawable.shape_999999_round_20));
                return;
            }
            tvTag.setBackground(context.getResources().getDrawable(R.drawable.shape_gradient_radius_20));
            switch (bean.getTag() == null ? "" : bean.getTag()) {
                case "wealth_plan":// 阅读赚车赚房
                    tvTag.setText("立即阅读");
                    break;
                case "wechat_certification"://微信认证
                    tvTag.setText("立即认证");
                    break;
                case "qq_certification"://关联QQ
                    tvTag.setText("立即关联");
                    break;
                case "fill_invitation_code"://填邀请码
                    tvTag.setText("立即填写");
                    break;
                case "inviting_friend_first"://首次邀请好友
                    tvTag.setText("立即邀请");
                    break;
                case "inviting_friend_mobile_first"://首次邀请手机好友
                    tvTag.setText("立即邀请");
                    break;
                case "questionnaire_investigation"://问卷调查
                    tvTag.setText("去做问卷");
                    break;
                case "phone_bind"://手机号认证
                    tvTag.setText("立即认证");
                    break;
                case "play_games_on_trial"://试玩任务奖励
                    tvTag.setText("立即试玩");
                    break;
                case "inviting_friend"://邀请好友
                    tvTag.setText("立即邀请");
                    break;
                case "inviting_friend_mobile"://邀请手机好友
                    tvTag.setText("立即邀请");
                    break;
                case "treasure_box"://开宝箱
                    tvTag.setText("立即开启");
                    break;
                case "reading_article"://浏览奖励
                    tvTag.setText("立即浏览");
                    break;
                case "read_60minute"://每日阅读时长65分钟
                    tvTag.setText("立即阅读");
                    break;
                case "sharing_article"://分享文章
                    tvTag.setText("立即分享");
                    break;
                case "exposure_to_income"://晒收入
                    tvTag.setText("晒收入");
                    break;
                case "walk_make_coin"://走路赚钱
                    tvTag.setText("立即查看");
                    break;
                case "view_ad_movie"://完成视频任务赚金币
                    tvTag.setText("去看视频");
                    break;
                case "page_view_reward_by_ip"://分享页被浏览
                    tvTag.setText("立即参与");
                    break;
                case "finish_profile_info":// 完善个人信息
                    tvTag.setText("去打开");
                    break;
                case "download_app_task": //下载试玩领金币
                    tvTag.setText("立即下载");
                    break;
                case "wx_applet_task": //小程序任务
                    tvTag.setText("立即赚金币");
                    break;
            }
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ll_title:
                    if (bean.getType() == 100) {
                        onItemClickListener.click(bean);
                    } else {

                        if (items.indexOf(bean) == items.size() - 1) {
                            if(onLastItemClickListener!=null){
                                onLastItemClickListener.onClick();
                            }
                        }
                        llDesc.setVisibility(llDesc.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                        boolean desc = llDesc.getVisibility() == View.VISIBLE;
                        ivMore.setImageResource(desc ? R.mipmap.ic_more_down : R.mipmap.ic_more_right);
                        // 关闭展开标记的任务
                        String selectTag = Paper.book().read(PagerCons.SELECT_TAG);
                        if (!TextUtils.isEmpty(selectTag) && selectTag.equals(bean.getTag()) && !desc)
                            Paper.book().write(PagerCons.SELECT_TAG, "");
                    }
                    break;
                case R.id.tv_tag:
                    if (bean.getDoX() == 1) {
                        return;
                    }
                    onItemClickListener.click(bean);
                    break;
            }
        }
    }

    public interface OnItemClickListener {
        void click(TaskListEntity.DayListBean bean);
    }

    public interface onLastItemClickListener {
        void onClick();
    }
}
