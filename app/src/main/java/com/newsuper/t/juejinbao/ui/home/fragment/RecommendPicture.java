package com.newsuper.t.juejinbao.ui.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juejinchain.android.R;
import com.juejinchain.android.databinding.FragmentRecommendPictureBinding;
import com.juejinchain.android.module.home.activity.PictureViewPagerActivity;
import com.juejinchain.android.module.home.adapter.RecommendPictureAdapter;
import com.juejinchain.android.module.home.entity.PictureContentEntity;
import com.juejinchain.android.module.home.presenter.impl.PictureContentPresenterImpl;
import com.ys.network.base.BaseFragment;
import com.ys.network.base.PagerCons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.paperdb.Paper.book;


public class RecommendPicture extends BaseFragment<PictureContentPresenterImpl, FragmentRecommendPictureBinding> implements PictureContentPresenterImpl.View {
    private int tabId;
    private RecommendPictureAdapter adapter;
    private List<PictureContentEntity.DataBean> mList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend_picture, container, false);
        return view;

    }

    @Override
    public void initView() {
        mViewBinding.fragmentRecommendPictureClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        tabId = getArguments().getInt("tabId", 0);
        Map<String, String> map = new HashMap<>();
        map.put("column_id", String.valueOf(tabId));
        map.put("type", "picture");
        mPresenter.getPictureContentList(map, getActivity());
        initRe();
    }

    private void initRe() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int height = display.getHeight();
        adapter = new RecommendPictureAdapter(mActivity, mList, height);

        adapter.setOnPictureItmeOnCLick(new RecommendPictureAdapter.RecommendPictureItmeOnCLick() {
            @Override
            public void onItemClick(int position, int id) {
//                Intent intent = new Intent(getActivity(), PictureViewPagerActivity.class);
//                intent.putExtra("id", id);
//                intent.putExtra("tabId", tabId);
//                startActivity(intent);
                PictureViewPagerActivity.intentMe(getActivity() , id , tabId);

                getActivity().finish();
            }
        });
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_div_line_4));

        mViewBinding.fragmentRecommendPictureRecycler.addItemDecoration(dividerItemDecoration);
        mViewBinding.fragmentRecommendPictureRecycler.setLayoutManager(manager);
        mViewBinding.fragmentRecommendPictureRecycler.setAdapter(adapter);
        adapter.setTextSizeLevel(book().read(PagerCons.KEY_TEXTSET_SIZE,"middle"));
    }

    @Override
    public void getPictureContentSuccess(Serializable serializable) {
        PictureContentEntity pictureContentEntity = (PictureContentEntity) serializable;
        if (pictureContentEntity.getCode() == 0 && !pictureContentEntity.getData().isEmpty()) {
            if (pictureContentEntity.getData().size() <= 6) {
                adapter.reloadRecyclerView(pictureContentEntity.getData(), true);
            } else {
                for (int i = 0; i <= 6; i++) {
                    mList.add(pictureContentEntity.getData().get(i));
                }
                adapter.notifyDataSetChanged();
            }

        }


    }

    @Override
    public void showErrolr(String str) {

    }
}
