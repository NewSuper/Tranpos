package com.newsuper.t.juejinbao.ui.book.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newsuper.t.R;
import com.newsuper.t.databinding.FragmentBookAddListBinding;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.bean.BaseEntity;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.book.activity.BookDetailActivity;
import com.newsuper.t.juejinbao.ui.book.activity.BookReadingActivity;
import com.newsuper.t.juejinbao.ui.book.adapter.BookshelfListAdapter;
import com.newsuper.t.juejinbao.ui.book.entity.Book;
import com.newsuper.t.juejinbao.ui.book.entity.BookshelfEntity;
import com.newsuper.t.juejinbao.ui.book.presenter.impl.BookshelfPresenterImpl;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import java.util.ArrayList;
import java.util.List;

/**
 * 我的书架
 */
public class BookAddListFragment extends BaseFragment<BookshelfPresenterImpl, FragmentBookAddListBinding> implements BookshelfPresenterImpl.MvpView,
        BookshelfListAdapter.OnItemChildClickListener {

    private List<Book> bookShelfList = new ArrayList<>();
    private BookshelfListAdapter bookshelfAdapter;
    private int position;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book_add_list, container, false);
    }

    @Override
    public void initView() {
        mViewBinding.rvBook.setLayoutManager(new LinearLayoutManager(mActivity));
        bookshelfAdapter = new BookshelfListAdapter(mActivity,this);
        mViewBinding.rvBook.setAdapter(bookshelfAdapter);

        mViewBinding.refresh.setEnableLoadMore(false);
        mViewBinding.refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.getMyNovelList(mActivity);
            }
        });
    }

    @Override
    public void initData() {
        mPresenter.getMyNovelList(mActivity);
    }

    @Override
    public void click(View view) {
        if (!LoginEntity.getIsLogin()) {
            startActivity(new Intent(mActivity, GuideLoginActivity.class));
            return;
        }
        position = (int) view.getTag();
        switch (view.getId()){
            case R.id.iv_delete:
                new AlertDialog.Builder(mActivity).setMessage("是否将该小说从书架移除?").setCancelable(false)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.delFromMyNovel(mActivity, bookShelfList.get(position).getNovel().getId());
                        dialog.dismiss();
                    }
                }).create().show();
                break;
            case R.id.tv_read:
                BookReadingActivity.intentMe(mActivity, bookShelfList.get(position).getNovel().getId(), "",-1,1);
                break;
            case R.id.rl_item:
                BookDetailActivity.intentMe(mActivity,  bookShelfList.get(position).getNovel().getId());
                break;
        }
    }

    @Override
    public void error(String str) {
    }

    @Override
    public void getMyNovelList(BookshelfEntity data) {
        mViewBinding.refresh.finishRefresh();
        bookShelfList.clear();
        if (data.getData() != null && data.getData().size() != 0) {
            mViewBinding.rlEmpty.setVisibility(View.GONE);
            bookShelfList.addAll(data.getData());
        } else {
            mViewBinding.rlEmpty.setVisibility(View.VISIBLE);
        }
        bookshelfAdapter.update(bookShelfList);
    }

    @Override
    public void getMyNovelRecordList(BookshelfEntity data) {
    }

    @Override
    public void delFromMyNovel(BaseEntity data) {
        mPresenter.getMyNovelList(mActivity);
//        bookShelfList.remove(position);
//        bookshelfAdapter.update(bookShelfList);
//        mViewBinding.rlEmpty.setVisibility(bookShelfList == null || bookShelfList.size() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void delFromMyNovelRecord(BaseEntity data) {
    }
}
