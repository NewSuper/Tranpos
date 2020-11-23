package com.newsuper.t.juejinbao.ui.book.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.newsuper.t.R;
import com.newsuper.t.databinding.FragmentBookRecommendBinding;
import com.newsuper.t.juejinbao.base.BaseFragment;
import com.newsuper.t.juejinbao.ui.book.activity.BookDetailActivity;
import com.newsuper.t.juejinbao.ui.book.adapter.BooKHotClassifyAdapter;
import com.newsuper.t.juejinbao.ui.book.entity.Book;
import com.newsuper.t.juejinbao.ui.home.presenter.impl.PublicPresenterImpl;

import java.util.ArrayList;
import java.util.List;

public class BookRecommendFragment  extends BaseFragment<PublicPresenterImpl, FragmentBookRecommendBinding> implements BooKHotClassifyAdapter.OnItemClickListener {

    private List<Book> bookList = new ArrayList<>();
    private int index;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book_recommend, container, false);
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        bookList.clear();
        Bundle bundle = getArguments();
        if (bundle != null) {
            bookList = (List<Book>) bundle.getSerializable("list");
            index = bundle.getInt("index", 0);
        }
        BooKHotClassifyAdapter adapter = new BooKHotClassifyAdapter(mActivity,this);
        mViewBinding.rvBook.setLayoutManager(new LinearLayoutManager(mActivity));
        mViewBinding.rvBook.setNestedScrollingEnabled(false);
        mViewBinding.rvBook.setAdapter(adapter);
        adapter.update(bookList);
    }

    @Override
    public void click(Book book) {
        BookDetailActivity.intentMe(mActivity,book.getNovel().getId());
    }
}
