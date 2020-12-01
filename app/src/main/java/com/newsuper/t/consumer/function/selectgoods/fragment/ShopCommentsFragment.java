package com.newsuper.t.consumer.function.selectgoods.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.CommentBean;
import com.newsuper.t.consumer.bean.ShopInfoBean;
import com.newsuper.t.consumer.function.TopActivity3;
import com.newsuper.t.consumer.function.selectgoods.activity.PhotoviewActivity;
import com.newsuper.t.consumer.function.selectgoods.activity.SelectGoodsActivity3;
import com.newsuper.t.consumer.function.selectgoods.adapter.CommentsAdapter;
import com.newsuper.t.consumer.function.selectgoods.inter.ICommentFragmentView;
import com.newsuper.t.consumer.function.selectgoods.inter.OnPhotoClickListener;
import com.newsuper.t.consumer.function.selectgoods.presenter.CommentFragmentPresenter;
import com.newsuper.t.consumer.function.selectgoods.request.CommentsRequest;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.UrlConst;
import com.newsuper.t.consumer.widget.FlowLayout.FlowLayout;
import com.newsuper.t.consumer.widget.FlowLayout.TagAdapter;
import com.newsuper.t.consumer.widget.FlowLayout.TagFlowLayout;
import com.newsuper.t.consumer.widget.RatingBar;
import com.newsuper.t.consumer.widget.recycleview.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopCommentsFragment extends Fragment implements ICommentFragmentView, OnPhotoClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.scl_no_comment)
    NestedScrollView sclNoComment;
    TextView tvCommentsNum;
    private View rootView;
    private String admin_id = Const.ADMIN_ID;
    private String token;
    private CommentsAdapter commentsAdapter;
    private CommentFragmentPresenter commentPresenter;
    private ArrayList<ShopInfoBean.TagInfo> tags = new ArrayList<>();
    private TagAdapter tagAdapter;
    private ShopInfoBean bean;
    private String shop_id;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private boolean isLoading;
    private int page;
    private int selectPos;//选中的标签
    private boolean isLoadMore = false;
    private List<CommentBean.Comment> commentList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commentPresenter = new CommentFragmentPresenter(this);
        token = SharedPreferencesUtil.getToken();
        Activity activity= getActivity();
        if(activity instanceof SelectGoodsActivity3){
            bean = ((SelectGoodsActivity3) activity).getShopInfoBean();
        }else{
            bean=((TopActivity3) activity).getShopInfoBean();
        }

        commentList = new ArrayList<>();
        if (null != bean.data) {
            shop_id = bean.data.shop.id;
            if (null != bean.data.tag_num) {
                tags.addAll(bean.data.tag_num);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("onCreateView", ".....shopCommmentsFragment ");
        rootView = inflater.inflate(R.layout.fragment_shop_comments, null);
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        commentsAdapter = new CommentsAdapter(getContext(), commentList);
        commentsAdapter.setOnPhotoClickListener(this);
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(commentsAdapter);
        View view = View.inflate(getContext(), R.layout.comment_head_view, null);
        RatingBar star1 = (RatingBar) view.findViewById(R.id.star_1);
        RatingBar star2 = (RatingBar) view.findViewById(R.id.star_2);
        RatingBar star3 = (RatingBar) view.findViewById(R.id.star_3);
        RatingBar star4 = (RatingBar) view.findViewById(R.id.star_4);
        RatingBar star5 = (RatingBar) view.findViewById(R.id.star_5);
        star1.setStar(1);
        star2.setStar(2);
        star3.setStar(3);
        star4.setStar(4);
        star5.setStar(5);
        TextView tvShopGrade = (TextView) view.findViewById(R.id.tv_shop_grade);
        tvCommentsNum = (TextView) view.findViewById(R.id.tv_comments_num);
        TextView star1Grade = (TextView) view.findViewById(R.id.star1_grade);
        TextView star2Grade = (TextView) view.findViewById(R.id.star2_grade);
        TextView star3Grade = (TextView) view.findViewById(R.id.star3_grade);
        TextView star4Grade = (TextView) view.findViewById(R.id.star4_grade);
        TextView star5Grade = (TextView) view.findViewById(R.id.star5_grade);
        TagFlowLayout tagFlowLayout = (TagFlowLayout) view.findViewById(R.id.tagFlowLayout);
        tvShopGrade.setText(bean.data.commentgrade);
        tvCommentsNum.setText(bean.data.shop.comment_num);
        star1Grade.setText(bean.data.commentgradePeople.grade_1);
        star2Grade.setText(bean.data.commentgradePeople.grade_2);
        star3Grade.setText(bean.data.commentgradePeople.grade_3);
        star4Grade.setText(bean.data.commentgradePeople.grade_4);
        star5Grade.setText(bean.data.commentgradePeople.grade_5);
        tags.clear();
        if (null != bean.data.tag_num&&bean.data.tag_num.size()>0) {
            for(ShopInfoBean.TagInfo tagInfo:bean.data.tag_num){
                if(null!=tagInfo.num&&Integer.parseInt(tagInfo.num)>0){
                    tags.add(tagInfo);
                }
            }
        }
        if(tags.size()==0||bean.data.shop.is_opencomment.equals("0")){
            //没有评论的时候只显示提示图
            sclNoComment.setVisibility(View.VISIBLE);
            return;
        }


        if(tags.size()>0){
            tagAdapter = new TagAdapter<ShopInfoBean.TagInfo>(tags) {
                @Override
                public View getView(FlowLayout parent, int position, ShopInfoBean.TagInfo tagInfo) {
                    TextView textView = (TextView) View.inflate(getActivity(), R.layout.item_tv, null);
                    textView.setText(tagInfo.name + "(" + tagInfo.num + ")");
                    return textView;
                }
            };
            tagFlowLayout.setAdapter(tagAdapter);
            tagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                @Override
                public void onSelected(Set<Integer> selectPosSet) {
                    Iterator<Integer> it = selectPosSet.iterator();
                    selectPos = it.next();
                    page = 0;
                    commentsAdapter.setIsLoadAll(false);
                    isLoadMore = false;
                    //通过标签请求评论列表
                    HashMap<String, String> request = CommentsRequest.commentsRequest(admin_id, shop_id, page + "", "20", tags.get(selectPos).id + "");
                    commentPresenter.loadData(UrlConst.SHOP_COMMENTS, request);
                }
            });

            tagFlowLayout.setSelected(0);
            mHeaderAndFooterWrapper.addHeaderView(view);
            final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    switch (newState) {
                        case 0:
                        case 1:
//                            Glide.with(getActivity()).resumeRequests();
                            break;
                        case 2:
//                            Glide.with(getActivity()).pauseRequests();
                            break;
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int visibleItemCount = recyclerView.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                    if (!isLoading && (totalItemCount - visibleItemCount <= firstVisibleItem && dy > 0)) {
                        Log.e("加载更多评论", "。。。。。 ");
                        showMoreShop();
                    }
                }
            });
           /* CommentDividerDecoration typeDivider = new CommentDividerDecoration();
            typeDivider.setDividerColor(Color.parseColor("#d5d5d5"));
            recyclerView.addItemDecoration(typeDivider);*/
            recyclerView.setAdapter(mHeaderAndFooterWrapper);
        }

    }


    //显示更多的评论
    private void showMoreShop() {
        isLoading = true;
        isLoadMore = true;
        //通过标签请求评论列表
        HashMap<String, String> request = CommentsRequest.commentsRequest(admin_id, shop_id, page + "", "20", tags.get(selectPos).id + "");
        commentPresenter.loadData(UrlConst.SHOP_COMMENTS, request);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void showDataToVIew(CommentBean bean) {
        isLoading = false;
        if (null != bean) {
            if (null != bean.data.comments && bean.data.comments.size() > 0) {
                sclNoComment.setVisibility(View.GONE);
                page++;
                if(bean.data.comments.size()%20==0){
                    commentsAdapter.setIsLoadAll(false);
                }else{
                    commentsAdapter.setIsLoadAll(true);
                }
                if (isLoadMore) {
                    commentList.addAll(bean.data.comments);
                } else {
                    commentList.clear();
                    commentList.addAll(bean.data.comments);
                }
//                tvCommentsNum.setText(commentList.size()+"");
                commentsAdapter.notifyDataSetChanged();
                mHeaderAndFooterWrapper.notifyDataSetChanged();
            } else {
                if (isLoadMore) {
                    commentsAdapter.setIsLoadAll(true);
                    mHeaderAndFooterWrapper.notifyDataSetChanged();
                }

            }
        }
    }

    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {

    }


    @Override
    public void onClickPhoto(int position, ArrayList<String> photoUrls) {
        Intent intent = new Intent(getContext(), PhotoviewActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("urlList", photoUrls);
        startActivity(intent);
    }

}
