package com.newsuper.t.juejinbao.ui.book.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityBookReadingBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.book.adapter.BookChapterAdapter;
import com.newsuper.t.juejinbao.ui.book.entity.Book;
import com.newsuper.t.juejinbao.ui.book.entity.BookChapterEntity;
import com.newsuper.t.juejinbao.ui.book.presenter.impl.BookChapterPresenterImpl;
import com.newsuper.t.juejinbao.ui.book.presenter.impl.BookReadPresenterImpl;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.utils.ClickUtil;
import com.newsuper.t.juejinbao.utils.NetUtil;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;
import com.newsuper.t.juejinbao.view.page.PageLoader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import io.paperdb.Paper;

/**
 * 小说阅读页
 */
public class BookReadingActivity extends BaseActivity<BookReadPresenterImpl, ActivityBookReadingBinding> implements BookReadPresenterImpl.MvpView, BookChapterAdapter.OnItemClickListener {

    private Book book;
    private String bookId;
    private String chapterId;
    private int currentChapter = 0;
    private int marked = 0; // 是否已加入了书架：1已加入，0未加入
    private List<BookChapterEntity.Chapter> mChapterList = new ArrayList<>();
    private BookChapterAdapter chapterAdapter;

    private static final int DEFAULT_TEXT_SIZE = 18;
    private int textSize = DEFAULT_TEXT_SIZE;
    private Animation mLeftInAnim;
    private Animation mLeftOutAnim;
    private Animation mTopInAnim;
    private Animation mTopOutAnim;
    private Animation mBottomInAnim;
    private Animation mBottomOutAnim;
    private PageLoader mPageLoader;

    @Override
    public boolean setStatusBarColor() {
        return false;
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_reading;
    }

    public static void intentMe(Context context, String bookId,String chapterId ,int chapterPosition,int marked) {
        Intent intent = new Intent(context, BookReadingActivity.class);
        intent.putExtra("bookId", bookId);
        intent.putExtra("chapterId", chapterId);
        intent.putExtra("chapterPosition", chapterPosition);
        intent.putExtra("marked", marked);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarDarkTheme(this, true);
        if(Paper.book().read(PagerCons.KEY_IS_READ_FIRST,true)){
            mViewBinding.rlBg.setVisibility(View.VISIBLE);
        }else{
            mViewBinding.rlBg.setVisibility(View.GONE);
        }
        //禁止手势滑动
        mViewBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        mViewBinding.rvChapter.setLayoutManager(new LinearLayoutManager(this));
        chapterAdapter = new BookChapterAdapter(this,this);
        mViewBinding.rvChapter.setAdapter(chapterAdapter);
    }

    @Override
    public void initData() {
        bookId = getIntent().getStringExtra("bookId");
        chapterId = getIntent().getStringExtra("chapterId");
        currentChapter = getIntent().getIntExtra("chapterPosition",-1) + 1;
        marked = getIntent().getIntExtra("marked",0);
        if(TextUtils.isEmpty(bookId))
            return;
        book = new Book();
        Book.Novel novel = new Book.Novel();
        novel.setId(bookId);
        book.setNovel(novel);
        //获取页面加载器
        mPageLoader = mViewBinding.readPage.getPageLoader(book);
        //获取翻页模式
        initPageModel(ReadSettingManager.getInstance().getPageMode());
        //获取日夜间模式
        changedMode(ReadSettingManager.getInstance().isNightMode());
        //获取小说的详情
        mPresenter.getBookData(this, bookId);
        //获取目录
        mPresenter.getChapterList(this, bookId, "1");

        mPageLoader.setOnPageChangeListener(
                new PageLoader.OnPageChangeListener() {

                    @Override
                    public void onChapterChange(int pos) {
                    }

                    @Override
                    public void requestChapters(List<BookChapterEntity.Chapter> requestChapters) {
                        for (BookChapterEntity.Chapter chapter: requestChapters) {
                            mPresenter.getChapterRead(mActivity,bookId, chapter.getId(),0);
                        }
                    }

                    @Override
                    public void onCategoryFinish(List<BookChapterEntity.Chapter> chapters) {
                    }

                    @Override
                    public void onPageCountChange(int count) {
                    }

                    @Override
                    public void onPageChange(int pos) {
                    }
                }
        );

        mViewBinding.readPage.setTouchListener(new PageView.TouchListener() {
            @Override
            public boolean onTouch() {
                if(mViewBinding.llBottom.getVisibility() == View.VISIBLE)
                    toggleMenu(true);
                return mViewBinding.llBottom.getVisibility() != View.VISIBLE;
            }

            @Override
            public void center() {
                toggleMenu(mViewBinding.llBottom.getVisibility() == View.VISIBLE);
            }

            @Override
            public void prePage() {
            }

            @Override
            public void nextPage() {
            }

            @Override
            public void cancel() {
            }
        });
    }

    @OnClick({R.id.module_include_title_bar_return,R.id.rl_bg, R.id.iv_open_catalogue, R.id.iv_close_catalogue,
            R.id.tv_setting,R.id.tv_bookshelf,R.id.tv_night_sun,R.id.cover,R.id.tv_default,R.id.tv_add,
            R.id.tv_sub,R.id.tv_model1,R.id.tv_model2,R.id.bg1,R.id.bg2,R.id.bg3,R.id.bg4,R.id.bg5})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.module_include_title_bar_return:
                finish();
                break;
            case R.id.rl_bg:
                mViewBinding.rlBg.setVisibility(View.GONE);
                Paper.book().write(PagerCons.KEY_IS_READ_FIRST,false);
                break;
            case R.id.iv_open_catalogue:
                mViewBinding.drawerLayout.openDrawer(Gravity.START);
                toggleMenu(true);
                break;
            case R.id.iv_close_catalogue:
                mViewBinding.drawerLayout.closeDrawer(Gravity.START);
                mViewBinding.ivOpenCatalogue.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_setting:
                mViewBinding.llBgSetting.setVisibility(Paper.book().read(Constant.ISNIGHT, false) ? View.GONE : View.VISIBLE);
                mViewBinding.llBottomSetting.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_bookshelf:
                if(marked == 1){
                    ToastUtils.getInstance().show(mActivity,"已加入书架");
                }else{
                    mPresenter.addToMyNovel(this, bookId,mChapterList.get(currentChapter).getId());
                }
                break;
            case R.id.tv_night_sun:
                boolean isNight = Paper.book().read(Constant.ISNIGHT, false);
                changedMode(!isNight);
                break;
            case R.id.cover:
                toggleMenu(true);
                break;
            case R.id.tv_default:
                textSize = DEFAULT_TEXT_SIZE;
                mPageLoader.setTextSize(ScreenUtils.dpToPxInt(textSize));
                break;
            case R.id.tv_add:
                if(textSize >36){
                    mViewBinding.tvAdd.setTextColor(getResources().getColor(R.color.c_999999));
                }else{
                    textSize++;
                    mViewBinding.tvAdd.setTextColor(getResources().getColor(R.color.white));
                    mPageLoader.setTextSize(ScreenUtils.dpToPxInt(textSize));
                }

                if(textSize>=14)
                    mViewBinding.tvSub.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.tv_sub:
                if(textSize <14){
                    mViewBinding.tvSub.setTextColor(getResources().getColor(R.color.c_999999));
                }else{
                    textSize--;
                    mViewBinding.tvSub.setTextColor(getResources().getColor(R.color.white));
                    mPageLoader.setTextSize(ScreenUtils.dpToPxInt(textSize));
                }

                if(textSize<=36)
                    mViewBinding.tvAdd.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.tv_model1:
                mPageLoader.setPageMode(PageMode.SCROLL);
                toggleMenu(true);
                initPageModel(PageMode.SCROLL);
                break;
            case R.id.tv_model2:
                mPageLoader.setPageMode(PageMode.COVER);
                toggleMenu(true);
                initPageModel(PageMode.COVER);
                break;
            case R.id.bg1:
                mPageLoader.setPageStyle(PageStyle.values()[0]);
                break;
            case R.id.bg2:
                mPageLoader.setPageStyle(PageStyle.values()[1]);
                break;
            case R.id.bg3:
                mPageLoader.setPageStyle(PageStyle.values()[2]);
                break;
            case R.id.bg4:
                mPageLoader.setPageStyle(PageStyle.values()[3]);
                break;
            case R.id.bg5:
                mPageLoader.setPageStyle(PageStyle.values()[4]);
                break;
        }
    }

    @Override
    public void click(int position) {
        mViewBinding.drawerLayout.closeDrawer(Gravity.START);
        mPageLoader.skipToChapter(position);
    }

    @Override
    public void error(String error) {
        if (mPageLoader.getPageStatus() == PageLoader.STATUS_LOADING) {
            mPageLoader.chapterError();
        }
    }

    @Override
    public void getBooKInfo(BooKInfoEntity data) {
        if(data.getData()==null)
            return;
        book = data.getData().getNovel_info();
        mViewBinding.title.moduleIncludeTitleBarTitle.setText(book.getNovel().getName());
        com.bumptech.glide.Glide.with(this).load(book.getNovel().getCover()).into(mViewBinding.ivBookBg);
        mViewBinding.tvBookName.setText(book.getNovel().getName());
        mViewBinding.tvWriter.setText(book.getAuthor().getName());
        marked = data.getData().getMarked();
        // 是否已加入了书架：1已加入，0未加入
        if(marked == 1){
            mViewBinding.tvBookshelf.setText("已加入书架");
        }else{
            mViewBinding.tvBookshelf.setText("加入书架");
        }
    }

    @Override
    public void getChapterList(BookChapterEntity data) {
        if(data.getData()==null)
            return;
        mChapterList.clear();
        mChapterList.addAll(data.getData());
        chapterAdapter.update(mChapterList);

        mPageLoader.getCollBook().setBookChapters(mChapterList);
        mPageLoader.refreshChapterList();

        if(!TextUtils.isEmpty(chapterId)){
            for (int i = 0; i < mChapterList.size(); i++) {
                if(mChapterList.get(i).getId().equals(chapterId)){
                    currentChapter = i;
                    mPageLoader.skipToChapter(currentChapter);
                    break;
                }
            }
        }
    }

    @Override
    public void showChapterRead(BookChapterDetailEntity data, int current) {
        if(current==-1)
            return;
        if (data != null) {
            String content = data.getChapter().getContent();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                content = String.valueOf(Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY));
            } else {
                content = String.valueOf(Html.fromHtml(content));
            }
            BookManager.getInstance().saveChapterInfo(bookId, data.getChapter().getName(), content);
            if (mPageLoader.getPageStatus() == PageLoader.STATUS_LOADING) {
                mPageLoader.openChapter();
            }
        }
    }

    @Override
    public void addToMyNovel() {
        mViewBinding.tvBookshelf.setText("已加入书架");
        ToastUtils.getInstance().show(this,"加入书架成功");
    }

    /**
     * 显示隐藏ToolBar和底部控制栏
     */
    private void toggleMenu(boolean hideStatusBar) {
        initMenuAnim();
        if(hideStatusBar){
            hideAppBarAndBottomControl();
        }else{
            showAppBarAndBottomControl();
        }
    }

    private void showAppBarAndBottomControl() {
        mViewBinding.cover.setVisibility(View.VISIBLE);
        mViewBinding.ivOpenCatalogue.startAnimation(mLeftInAnim);
        mViewBinding.ivOpenCatalogue.setVisibility(View.VISIBLE);
        mViewBinding.titleBar.startAnimation(mTopInAnim);
        mViewBinding.titleBar.setVisibility(View.VISIBLE);
        mViewBinding.llBottom.startAnimation(mBottomInAnim);
        mViewBinding.llBottom.setVisibility(View.VISIBLE);
    }

    private void hideAppBarAndBottomControl() {
        mViewBinding.cover.setVisibility(View.GONE);
        mViewBinding.ivOpenCatalogue.startAnimation(mLeftOutAnim);
        mViewBinding.ivOpenCatalogue.setVisibility(View.GONE);
        mViewBinding.titleBar.startAnimation(mTopOutAnim);
        mViewBinding.titleBar.setVisibility(View.GONE);
        mViewBinding.llBottom.startAnimation(mBottomOutAnim);
        mViewBinding.llBottom.setVisibility(View.GONE);
        mViewBinding.llBottomSetting.setVisibility(View.GONE);
    }

    private void changedMode(boolean isNight) {
        Paper.book().write(Constant.ISNIGHT, isNight);
        mPageLoader.setNightMode(isNight);

        mViewBinding.llBgSetting.setVisibility(isNight ? View.GONE : View.VISIBLE);
        mViewBinding.llBottom.setBackgroundColor(ContextCompat.getColor(this, isNight ? R.color.black : R.color.white));
        mViewBinding.title.llBar.setBackgroundColor(ContextCompat.getColor(this, isNight ? R.color.black : R.color.white));
        mViewBinding.title.moduleIncludeTitleBarReturn.setImageResource(isNight ? R.mipmap.ic_back : R.mipmap.icon_bar_title_return);
        mViewBinding.title.moduleIncludeTitleBarTitle.setTextColor(isNight ? getResources().getColor(R.color.white) : getResources().getColor(R.color.c_333333));

        Drawable drawable;
        if(isNight){
            drawable = getResources().getDrawable(R.mipmap.ic_sun);
            mViewBinding.tvNightSun.setText("开灯");
        }else{
            drawable = getResources().getDrawable(R.mipmap.ic_night);
            mViewBinding.tvNightSun.setText("关灯");
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());
        mViewBinding.tvNightSun.setCompoundDrawables(null, drawable, null, null);
    }

    //初始化菜单动画
    private void initMenuAnim() {
        if (mTopInAnim != null) return;

        mTopInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_top_in);
        mTopOutAnim = AnimationUtils.loadAnimation(this, R.anim.slide_top_out);
        mBottomInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_bottom_in);
        mBottomOutAnim = AnimationUtils.loadAnimation(this, R.anim.slide_bottom_out);
        mLeftInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_left_in);
        mLeftOutAnim = AnimationUtils.loadAnimation(this, R.anim.slide_left_out);
        //退出的速度要快
        mTopOutAnim.setDuration(200);
        mBottomOutAnim.setDuration(200);
        mLeftOutAnim.setDuration(200);
    }

    private void initPageModel(PageMode pageMode){
        if(pageMode == PageMode.SCROLL){
            mViewBinding.tvModel1.setBackgroundResource(R.drawable.shape_ff9900_radius_5);
            mViewBinding.tvModel2.setBackgroundResource(R.drawable.shape_ffffff_radius_5);
        }else{
            mViewBinding.tvModel1.setBackgroundResource(R.drawable.shape_ffffff_radius_5);
            mViewBinding.tvModel2.setBackgroundResource(R.drawable.shape_ff9900_radius_5);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //currentChapter = mPageLoader.getChapterPos();
        //mPresenter.getChapterRead(mActivity,bookId,mChapterList.get(currentChapter).getId(),-1);
        mPageLoader.saveRecord();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPageLoader.closeBook();
        mPageLoader = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isVolumeTurnPage = ReadSettingManager
                .getInstance().isVolumeTurnPage();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (isVolumeTurnPage) {
                    return mPageLoader.skipToPrePage();
                }
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (isVolumeTurnPage) {
                    return mPageLoader.skipToNextPage();
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
