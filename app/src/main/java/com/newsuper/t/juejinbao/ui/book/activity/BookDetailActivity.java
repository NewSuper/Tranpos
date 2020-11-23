package com.newsuper.t.juejinbao.ui.book.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityBookDetailBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.book.adapter.BooKHotClassifyAdapter;
import com.newsuper.t.juejinbao.ui.book.adapter.BooKRecommendAdapter;
import com.newsuper.t.juejinbao.ui.book.adapter.BookChapterAdapter;
import com.newsuper.t.juejinbao.ui.book.adapter.BookDetailChapterAdapter;
import com.newsuper.t.juejinbao.ui.book.entity.BooKInfoEntity;
import com.newsuper.t.juejinbao.ui.book.entity.Book;
import com.newsuper.t.juejinbao.ui.book.entity.BookChapterEntity;
import com.newsuper.t.juejinbao.ui.book.entity.BookCommendEntity;
import com.newsuper.t.juejinbao.ui.book.presenter.impl.BookChapterPresenterImpl;
import com.newsuper.t.juejinbao.ui.book.presenter.impl.BookDetailPresenterImpl;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.utils.ClickUtil;
import com.newsuper.t.juejinbao.utils.NetUtil;
import com.newsuper.t.juejinbao.utils.TimeUtils;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.UUIDUtil;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;
import com.newsuper.t.juejinbao.view.BlurTransformation;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import io.paperdb.Paper;

/**
 * 小说详情页
 */
public class BookDetailActivity extends BaseActivity<BookDetailPresenterImpl, ActivityBookDetailBinding> implements BookDetailPresenterImpl.MvpView,
        BooKHotClassifyAdapter.OnItemClickListener, BooKRecommendAdapter.OnItemClickListener, BookDetailChapterAdapter.OnItemClickListener {

    private String novelId;
    private Book book;
    private List<BookChapterEntity.Chapter> chapterList = new ArrayList<>();
    private List<BookChapterEntity.Chapter> allChapterList = new ArrayList<>();
    private BookDetailChapterAdapter chapterAdapter;

    private List<Book> recommendList = new ArrayList<>();
    private List<Book> hotList = new ArrayList<>();
    private BooKRecommendAdapter recommendAdapter;
    private BooKHotClassifyAdapter bookHotAdapter;
    private int marked = 0; // 是否已加入了书架：1已加入，0未加入

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_detail;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initView();
        initData();
    }

    @Override
    public void initView() {
        if(NetUtil.isNetworkAvailable(mActivity)){
            mViewBinding.loading.showContent();
            showAD();
        }else{
            mViewBinding.loading.showError();
        }
        //章节列表
        mViewBinding.rvCatalogue.setLayoutManager(new LinearLayoutManager(this));
        chapterAdapter = new BookDetailChapterAdapter(this,this);
        mViewBinding.rvCatalogue.setAdapter(chapterAdapter);
        //猜你喜歡
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mViewBinding.rvLike.setLayoutManager(linearLayoutManager);
        recommendAdapter = new BooKRecommendAdapter(this,this);
        mViewBinding.rvLike.setAdapter(recommendAdapter);
        //好物推荐
        mViewBinding.rvRecommend.setLayoutManager(new LinearLayoutManager(this));
        mViewBinding.rvRecommend.setNestedScrollingEnabled(false);
        bookHotAdapter = new BooKHotClassifyAdapter(this,this);
        mViewBinding.rvRecommend.setAdapter(bookHotAdapter);

        mViewBinding.llAdd.setClickable(true);

        mViewBinding.loading.setOnErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NetUtil.isNetworkAvailable(mActivity)){
                    showAD();
                    mPresenter.getBookData(mActivity,novelId);
                    mPresenter.getChapterList(mActivity,novelId);
                    //好物推荐
                    mPresenter.getCommendList(mActivity,novelId,2,null);
                    mViewBinding.loading.showContent();
                }else{
                    ToastUtils.getInstance().show(mActivity,"网络未连接");
                }
            }
        });
    }

    private void showAD() {
        int ad_show = Paper.book().read("ad_show",0);
        if(ad_show == 1){
            mViewBinding.ivBookshelf.setVisibility(View.VISIBLE);
            Glide.with(mActivity).load(R.mipmap.icon_story_adv_enter).into(mViewBinding.ivBookshelf);
        }else{
            mViewBinding.ivBookshelf.setVisibility(View.GONE);
        }
    }

    @Override
    public void initData() {
        novelId = getIntent().getStringExtra("novel_id");
        mPresenter.getChapterList(this,novelId);
        //好物推荐
        mPresenter.getCommendList(this,novelId,2,null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getBookData(this,novelId);
    }

    public static void intentMe(Context context, String novelId) {
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra("novel_id",novelId);
        context.startActivity(intent);
    }

    @Override
    public void error(String str) {
    }

    @Override
    public void getBooKInfo(BooKInfoEntity data) {
        if(data.getData() == null || data.getData().getNovel_info()==null)
            return;
        book = data.getData().getNovel_info();

        Glide.with(mActivity)
                .load(book.getNovel().getCover())
                .apply(new RequestOptions().placeholder(R.mipmap.bg_book_default).error(R.mipmap.bg_book_default))
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(5, 25)))
                .into(mViewBinding.ivBg);

        Glide.with(this).load(book.getNovel().getCover()).into(mViewBinding.ivBookBg);
        mViewBinding.tvBookName.setText(book.getNovel().getName());
        mViewBinding.tvWriter.setText(String.format("作者：%s", book.getAuthor().getName()));
        mViewBinding.tvClassics.setText(String.format("分类：%s", book.getCategory().getName()));
        mViewBinding.tvTime.setText(String.format("时间：%s", TimeUtils.getTime(book.getLast().getTime()*1000,TimeUtils.DATE_FORMAT_DATE)));
        mViewBinding.tvNew.setText(String.format("最新：%s", book.getLast().getName()));
        mViewBinding.tvFrom.setText(String.format("来源：%s", book.getSource().getSitename()));
        mViewBinding.tvContent.setText(book.getNovel().getIntro().replaceAll("\n",""));
        // 是否已加入了书架：1已加入，0未加入
        marked = data.getData().getMarked();
        if(data.getData().getMarked() == 1){
            mViewBinding.ivAdd.setVisibility(View.GONE);
            mViewBinding.tvAdd.setText("已加入书架");
            mViewBinding.tvAdd.setTextColor(getResources().getColor(R.color.c_999999));
            mViewBinding.llAdd.setClickable(false);
        }else{
            mViewBinding.ivAdd.setVisibility(View.VISIBLE);
            mViewBinding.tvAdd.setText("加入书架");
            mViewBinding.tvAdd.setTextColor(getResources().getColor(R.color.c_FF9900));
            mViewBinding.llAdd.setClickable(true);
        }
        // 猜你喜欢
        mPresenter.getCommendList(this,novelId,1,book.getCategory().getId());

        mViewBinding.tvContent.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取省略的字符数，0表示没和省略
                    if (mViewBinding.tvContent.getLayout() != null) {
                        int ellipsisCount = mViewBinding.tvContent.getLayout().getEllipsisCount(mViewBinding.tvContent.getLineCount() - 1);
                        mViewBinding.tvContent.getLayout().getEllipsisCount(mViewBinding.tvContent.getLineCount() - 1);
                        if (ellipsisCount > 0) {
                            String title = mViewBinding.tvContent.getText().toString();
                            String substring = title.substring(0, title.length() - ellipsisCount - 8);
                            String str = substring + " ... " + "<font color='#FF6600'>展开</font>";
                            mViewBinding.tvContent.setText(Html.fromHtml(str));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void getChapterList(BookChapterEntity data) {
        chapterList.clear();
        allChapterList.clear();
        if(data.getData()!=null && data.getData().size()!=0){
            if(data.getData().size()>5){
                for (int i = 0; i < 5; i++) {
                    chapterList.add(data.getData().get(i));
                }
            }else{
                chapterList.addAll(data.getData());  
            }
            allChapterList.addAll(data.getData());
        }
        chapterAdapter.update(chapterList);
    }

    @Override
    public void getCommendList(BookCommendEntity data , int type) {
        // 1为猜你喜欢，2为好书推荐
        switch (type){
            case 1:
                recommendList.clear();
                if(data.getData()!=null && data.getData().size()!=0)
                    recommendList.addAll(data.getData());
                recommendAdapter.update(recommendList);
                break;
            case 2:
                hotList.clear();
                if(data.getData()!=null && data.getData().size()!=0)
                    hotList.addAll(data.getData());
                bookHotAdapter.update(hotList);
                break;
        }
    }

    @Override
    public void addToMyNovel() {
        ToastUtils.getInstance().show(this,"加入书架成功");
        mViewBinding.ivAdd.setVisibility(View.GONE);
        mViewBinding.tvAdd.setText("已加入书架");
        mViewBinding.tvAdd.setTextColor(getResources().getColor(R.color.c_999999));
        mViewBinding.llAdd.setClickable(false);
        marked = 1;
    }

    @Override
    public void click(Book book) {
        BookDetailActivity.intentMe(mActivity,book.getNovel().getId());
        finish();
    }

    @OnClick({R.id.iv_bookshelf,R.id.iv_back,R.id.tv_content, R.id.tv_read, R.id.ll_add,R.id.tv_catalogue})
    public void onClick(View view) {
        if(!ClickUtil.isNotFastClick()){
            return;
        }
        switch (view.getId()) {
            case R.id.iv_bookshelf:
                //初始化推啊广告
                showAd();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_content:
                mViewBinding.tvContent.setMaxLines(10);
                mViewBinding.tvContent.setText(book.getNovel().getIntro());
                break;
            case R.id.tv_read:
                if (!LoginEntity.getIsLogin()) {
                    startActivity(new Intent(this, GuideLoginActivity.class));
                    return;
                }
                BookReadingActivity.intentMe(this, book.getNovel().getId(),"", -1, marked);
                break;
            case R.id.ll_add:
                if (!LoginEntity.getIsLogin()) {
                    startActivity(new Intent(this, GuideLoginActivity.class));
                    return;
                }
                if(chapterList!=null && chapterList.size()!=0)
                    new AlertDialog.Builder(this).setMessage("是否加入书架?").setCancelable(false)
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPresenter.addToMyNovel(BookDetailActivity.this, novelId);
                            dialog.dismiss();
                        }
                    }).create().show();
                break;
            case R.id.tv_catalogue:
                if(book!=null)
                    BookChapterActivity.intentMe(this,book.getNovel().getId(),book.getNovel().getName(),marked);
                break;
        }
    }

    @Override
    public void click(int position) {

        if (!LoginEntity.getIsLogin()) {
            startActivity(new Intent(mActivity, GuideLoginActivity.class));
            return;
        }

        String id = allChapterList.get(position).getId();
        int index = allChapterList.size()-1-position;
        if(book!=null)
            BookReadingActivity.intentMe(this, book.getNovel().getId(),id, index,marked);
    }

    private void showAd() {
//        Ad ad = new Ad("4BVnp1k1bnNGyf8ynrpC1r9KoLfJ", "304292", LoginEntity.getUserToken(), UUIDUtil.getMyUUID(mActivity));
//
//        ad.init(mActivity, null, new AdCallBack() {
//            @Override
//            public void onActivityClose() {
//                Log.i("tuia", "onActivityClose: ");
//            }
//
//            @Override
//            public void onActivityShow() {
//                Log.i("tuia", "onActivityShow: ");
//            }
//
//            @Override
//            public void onRewardClose() {
//                Log.i("tuia", "onRewardClose: ");
//            }
//
//            @Override
//            public void onRewardShow() {
//                Log.i("tuia", "onRewardShow: ");
//            }
//
//            @Override
//            public void onPrizeClose() {
//                Log.i("tuia", "onPrizeClose: ");
//            }
//
//            @Override
//            public void onPrizeShow() {
//                Log.i("tuia", "onPrizeShow: ");
//            }
//        });
//        ad.show();
    }
}
