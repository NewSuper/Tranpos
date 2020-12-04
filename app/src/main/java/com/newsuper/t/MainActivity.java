package com.newsuper.t;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.newsuper.t.consumer.ConsumerActivity;
import com.newsuper.t.juejinbao.ui.JueJinBaoLaunchActivity;
import com.newsuper.t.juejinbao.utils.BaseExpandableRecyclerViewAdapter;
import com.newsuper.t.markert.ui.register.SplashActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRvList;
    private MainExpandableAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_enter);

        mRvList = (RecyclerView) findViewById(R.id.rvList);
        List<GroupBean> groupBeans = initGroupData();
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MainExpandableAdapter(groupBeans);
        mAdapter.setListener(new BaseExpandableRecyclerViewAdapter.ExpandableRecyclerViewOnClickListener<GroupBean, ChildBean>() {
            @Override
            public boolean onGroupLongClicked(GroupBean groupItem) {
                return false;
            }

            @Override
            public boolean onInterceptGroupExpandEvent(GroupBean groupItem, boolean isExpand) {
                return false;
            }

            @Override
            public void onGroupClicked(GroupBean groupItem) {
                mAdapter.setSelectedChildBean(groupItem);
            }

            @Override
            public void onChildClicked(GroupBean groupItem, ChildBean childItem) {
                Intent intent = new Intent(MainActivity.this, childItem.getTargetClass());
                intent.putExtra("TITLE", childItem.mName);
                intent.putExtra("TYPE", childItem.mType);
                MainActivity.this.startActivity(intent);
            }
        });
        mRvList.setAdapter(mAdapter);
    }

    private List<GroupBean> initGroupData() {
        List<GroupBean> groupList = new ArrayList<>();
        List<ChildBean> videoConnectChildList = new ArrayList<>();
        videoConnectChildList.add(new ChildBean("掘金宝", R.drawable.room_multi, 0, JueJinBaoLaunchActivity.class));
        videoConnectChildList.add(new ChildBean("消费者", R.drawable.room_multi, 0, ConsumerActivity.class));
        videoConnectChildList.add(new ChildBean("超市收银", R.drawable.room_multi, 0, SplashActivity.class));
        videoConnectChildList.add(new ChildBean("自助收银", R.drawable.room_multi, 0, com.newsuper.t.sale.ui.splash.SplashActivity.class));
        if (videoConnectChildList.size() != 0) {
            GroupBean videoConnectGroupBean = new GroupBean("主入口", R.drawable.room_multi, videoConnectChildList);
            groupList.add(videoConnectGroupBean);
        }

        return groupList;
    }


    private static class MainExpandableAdapter extends BaseExpandableRecyclerViewAdapter<GroupBean, ChildBean, GroupVH, ChildVH> {
        private List<GroupBean> mListGroupBean;
        private GroupBean mGroupBean;

        public void setSelectedChildBean(GroupBean groupBean) {
            boolean isExpand = isExpand(groupBean);
            if (mGroupBean != null) {
                GroupVH lastVH = getGroupViewHolder(mGroupBean);
                if (!isExpand)
                    mGroupBean = groupBean;
                else
                    mGroupBean = null;
                notifyItemChanged(lastVH.getAdapterPosition());
            } else {
                if (!isExpand)
                    mGroupBean = groupBean;
                else
                    mGroupBean = null;
            }
            if (mGroupBean != null) {
                GroupVH currentVH = getGroupViewHolder(mGroupBean);
                notifyItemChanged(currentVH.getAdapterPosition());
            }
        }

        public MainExpandableAdapter(List<GroupBean> list) {
            mListGroupBean = list;
        }

        @Override
        public int getGroupCount() {
            return mListGroupBean.size();
        }

        @Override
        public GroupBean getGroupItem(int groupIndex) {
            return mListGroupBean.get(groupIndex);
        }

        @Override
        public GroupVH onCreateGroupViewHolder(ViewGroup parent, int groupViewType) {
            return new GroupVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.module_entry_item, parent, false));
        }

        @Override
        public void onBindGroupViewHolder(GroupVH holder, GroupBean groupBean, boolean isExpand) {
            holder.textView.setText(groupBean.mName);
            holder.ivLogo.setImageResource(groupBean.mIconId);
            if (mGroupBean == groupBean) {
                holder.itemView.setBackgroundResource(R.color.main_item_selected_color);
            } else {
                holder.itemView.setBackgroundResource(R.color.main_item_unselected_color);
            }
        }

        @Override
        public ChildVH onCreateChildViewHolder(ViewGroup parent, int childViewType) {
            return new ChildVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.module_entry_child_item, parent, false));
        }

        @Override
        public void onBindChildViewHolder(ChildVH holder, GroupBean groupBean, ChildBean childBean) {
            holder.textView.setText(childBean.getName());

            if (groupBean.mChildList.indexOf(childBean) == groupBean.mChildList.size() - 1) {//说明是最后一个
                holder.divideView.setVisibility(View.GONE);
            } else {
                holder.divideView.setVisibility(View.VISIBLE);
            }

        }
    }


    public static class GroupVH extends BaseExpandableRecyclerViewAdapter.BaseGroupViewHolder {
        ImageView ivLogo;
        TextView textView;

        GroupVH(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.name_tv);
            ivLogo = (ImageView) itemView.findViewById(R.id.icon_iv);
        }

        @Override
        protected void onExpandStatusChanged(RecyclerView.Adapter relatedAdapter, boolean isExpanding) {
        }

    }

    public static class ChildVH extends RecyclerView.ViewHolder {
        TextView textView;
        View divideView;

        ChildVH(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.name_tv);
            divideView = itemView.findViewById(R.id.item_view_divide);
        }

    }

    private class GroupBean implements BaseExpandableRecyclerViewAdapter.BaseGroupBean<ChildBean> {
        private String mName;
        private List<ChildBean> mChildList;
        private int mIconId;

        public GroupBean(String name, int iconId, List<ChildBean> list) {
            mName = name;
            mChildList = list;
            mIconId = iconId;
        }

        @Override
        public int getChildCount() {
            return mChildList.size();
        }

        @Override
        public ChildBean getChildAt(int index) {
            return mChildList.size() <= index ? null : mChildList.get(index);
        }

        @Override
        public boolean isExpandable() {
            return getChildCount() > 0;
        }

        public String getName() {
            return mName;
        }

        public List<ChildBean> getChildList() {
            return mChildList;
        }

        public int getIconId() {
            return mIconId;
        }
    }

    private class ChildBean {
        public String mName;
        public int mIconId;
        public Class mTargetClass;
        public int mType;

        public ChildBean(String name, int iconId, int type, Class targetActivityClass) {
            this.mName = name;
            this.mIconId = iconId;
            this.mTargetClass = targetActivityClass;
            this.mType = type;
        }

        public String getName() {
            return mName;
        }


        public int getIconId() {
            return mIconId;
        }


        public Class getTargetClass() {
            return mTargetClass;
        }
    }

}
