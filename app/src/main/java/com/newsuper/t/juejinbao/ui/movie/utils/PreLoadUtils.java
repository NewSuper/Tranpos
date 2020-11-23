package com.newsuper.t.juejinbao.ui.movie.utils;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * 预加载工具
 * @param <T>
 */
public class PreLoadUtils<T> {
    RecyclerView recyclerView;

    PreLoadMoreListener preLoadMoreListener;

    LinearLayoutManager linearLayoutManager;
    GridLayoutManager gridLayoutManager;
    //item的宽/高
    private int itemW;
    private int itemH;
    private int iResult;
    //加载时的页码
    private int loadPage = 0;
    //预加载时的页码
    private int preLoadPage = -1;

    //倒计加载数
    private int preNum = 10;

    //每页加载数
//    private int pageItems = -1;
    //上一次的总条目数
    private int lastItemSize = 0;

    private List<T> items;

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {

//            Log.e("zy" , "首可见位置：" + linearLayoutManager.findFirstVisibleItemPosition());
//            Log.e("zy" , "尾可见位置：" + (linearLayoutManager.findLastVisibleItemPosition()));
//            Log.e("zy" , "滚动距离：" + getScollYDistance());

            try {
                getScollYDistance();

                if (linearLayoutManager != null) {
//                    Log.e("zy", items.size() + " - " + linearLayoutManager.findLastVisibleItemPosition() + "  预加载 lastItemSize = " + lastItemSize + " , items.size = " + items.size() + " , preNum = " + preNum);
                    if (items.size() - linearLayoutManager.findLastVisibleItemPosition() < preNum && loadPage != preLoadPage && lastItemSize != items.size()) {
//                        Log.e("zy", items.size() + " - " + linearLayoutManager.findLastVisibleItemPosition() + "  预加载");
                        lastItemSize = items.size();
                        preLoadPage = loadPage;
//                loadDate(false);
                        preLoadMoreListener.preLoad();

                    }
                } else if (gridLayoutManager != null) {
                    if (items.size() - gridLayoutManager.findLastVisibleItemPosition() < preNum && loadPage != preLoadPage && lastItemSize != items.size()) {
                        Log.e("zy", items.size() + " - " + gridLayoutManager.findLastVisibleItemPosition() + "  预加载");
                        lastItemSize = items.size();
                        preLoadPage = loadPage;
//                loadDate(false);
                        preLoadMoreListener.preLoad();

                    }
                }
            }catch (Exception e){
                //防空内容崩溃
                e.printStackTrace();
            }
            return true;
        }
    });


    /**
     *
     * @param recyclerView
     * @param items
     * @param preNum
     * @param preLoadMoreListener
     */
    public PreLoadUtils(RecyclerView recyclerView , List<T> items , int preNum , PreLoadMoreListener preLoadMoreListener){
        this.recyclerView = recyclerView;
        this.preNum = preNum;

        if(recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            this.linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        }else if(recyclerView.getLayoutManager() instanceof GridLayoutManager){
            this.gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        }
        this.items = items;
        this.preLoadMoreListener = preLoadMoreListener;

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int minLeftItemCount = 3;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
//                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                    int itemCount = layoutManager.getItemCount();
//                    int lastPosition = layoutManager.findLastCompletelyVisibleItemPosition();

                    mHandler.sendEmptyMessage(0);

//                }
            }


        });

    }

    /**
     *
     */
    public void loaded(){
        loadPage++;
//        if(pageItems == -1){
//            pageItems = items.size();
//        }
    }


    private int getScollYDistance() {
        if(linearLayoutManager != null) {
            //找到即将移出屏幕Item的position,position是移出屏幕item的数量
            int position = linearLayoutManager.findFirstVisibleItemPosition();
            //根据position找到这个Item
            View firstVisiableChildView = linearLayoutManager.findViewByPosition(position);
            //获取Item的高
            int itemHeight = firstVisiableChildView.getHeight();
            //算出该Item还未移出屏幕的高度
            int itemTop = firstVisiableChildView.getTop();
            //position移出屏幕的数量*高度得出移动的距离
            int iposition = position * itemHeight;
            //减去该Item还未移出屏幕的部分可得出滑动的距离
            iResult = iposition - itemTop;
            //item宽高
            itemW = firstVisiableChildView.getWidth();
            itemH = firstVisiableChildView.getHeight();

        }else if(gridLayoutManager != null){
            //得出spanCount几列或几排
            int itemSpanCount = gridLayoutManager.getSpanCount();
            //得出的position是一排或一列总和
            int position = gridLayoutManager.findFirstVisibleItemPosition();
            //需要算出才是即将移出屏幕Item的position
            int itemPosition = position / itemSpanCount ;
            //因为是相同的Item所以取那个都一样
            View firstVisiableChildView = gridLayoutManager.findViewByPosition(position);
            int itemHeight = firstVisiableChildView.getHeight();
            int itemTop = firstVisiableChildView.getTop();
            int iposition = itemPosition * itemHeight;
            iResult = iposition - itemTop;
            //item宽高
            itemW = firstVisiableChildView.getWidth();
            itemH = firstVisiableChildView.getHeight();
        }

        return iResult;
    }


    /**
     *
     */
    public void destory(){
        if(mHandler != null){
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }


    public interface PreLoadMoreListener{
        void preLoad();
    }



}
