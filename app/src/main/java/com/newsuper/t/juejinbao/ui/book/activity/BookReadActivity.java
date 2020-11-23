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
import android.view.View;

import com.bumptech.glide.Glide;
import com.newsuper.t.R;
import com.newsuper.t.databinding.ActivityBookReadBinding;
import com.newsuper.t.juejinbao.base.BaseActivity;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.ui.book.adapter.BookChapterAdapter;
import com.newsuper.t.juejinbao.ui.book.bean.ChapterRead;
import com.newsuper.t.juejinbao.ui.book.entity.BooKInfoEntity;
import com.newsuper.t.juejinbao.ui.book.entity.Book;
import com.newsuper.t.juejinbao.ui.book.entity.BookChapterDetailEntity;
import com.newsuper.t.juejinbao.ui.book.entity.BookChapterEntity;
import com.newsuper.t.juejinbao.ui.book.presenter.impl.BookChapterPresenterImpl;
import com.newsuper.t.juejinbao.ui.book.presenter.impl.BookReadPresenterImpl;
import com.newsuper.t.juejinbao.ui.login.activity.GuideLoginActivity;
import com.newsuper.t.juejinbao.utils.ClickUtil;
import com.newsuper.t.juejinbao.utils.NetUtil;
import com.newsuper.t.juejinbao.utils.ScreenUtils;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.androidUtils.StatusBarUtil;
import com.newsuper.t.juejinbao.view.read.BaseReadView;
import com.newsuper.t.juejinbao.view.read.CacheManager;
import com.newsuper.t.juejinbao.view.read.NoAimWidget;
import com.newsuper.t.juejinbao.view.read.OnReadStateChangeListener;
import com.newsuper.t.juejinbao.view.read.OverlappedWidget;
import com.newsuper.t.juejinbao.view.read.PageWidget;
import com.newsuper.t.juejinbao.view.read.ThemeManager;
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
public class BookReadActivity extends BaseActivity<BookReadPresenterImpl, ActivityBookReadBinding> implements BookReadPresenterImpl.MvpView, BookChapterAdapter.OnItemClickListener {

    private int page;
    private String bookId;
    private String chapterId;
    private int currentChapter = 1;
    private int marked = 0; // 是否已加入了书架：1已加入，0未加入
    private List<BookChapterEntity.Chapter> mChapterList = new ArrayList<>();
    private BookChapterAdapter chapterAdapter;

    private boolean startRead = false;
    private BaseReadView mPageWidget;
    private int curTheme = 1;
    private int textSize = 18;

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
        return R.layout.activity_book_read;
    }

    public static void intentMe(Context context, String bookId,String chapterId ,int chapterPosition,int marked) {
        Intent intent = new Intent(context, BookReadActivity.class);
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
            mViewBinding.loadingProgressbar.setVisibility(View.GONE);
        }else{
            mViewBinding.rlBg.setVisibility(View.GONE);
            mViewBinding.loadingProgressbar.setVisibility(View.VISIBLE);
        }
        //禁止手势滑动
        mViewBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        curTheme = Paper.book().read("readTheme", ThemeManager.THEME2);

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
        mPresenter.getBookData(this, bookId);

        //textSize = SettingManager.getInstance().getReadFontSize(book.getNovel().getId());
        initPagerWidget();
        if(currentChapter!=0){
            mPageWidget.isPrepared = true;
        }else{
            mPageWidget.isPrepared = false;
            currentChapter = 1;
        }
        changedMode(Paper.book().read(Constant.ISNIGHT, false));
        page = 1;
        mPresenter.getChapterList(this, bookId, String.valueOf(page));
    }

    private void initPagerWidget() {
        switch (Paper.book().read(Constant.FLIP_STYLE, 1)) {
            case 0:
                mPageWidget = new PageWidget(this, bookId, mChapterList, new ReadListener());
                break;
            case 1:
                mPageWidget = new OverlappedWidget(this, bookId, mChapterList, new ReadListener());
                break;
            case 2:
                mPageWidget = new NoAimWidget(this, bookId, mChapterList, new ReadListener());
        }

        if (Paper.book().read(Constant.ISNIGHT, false)) {
            mPageWidget.setTextColor(ContextCompat.getColor(this, R.color.chapter_content_night),
                    ContextCompat.getColor(this, R.color.chapter_title_night));
        }else{
            mPageWidget.setTextColor(ContextCompat.getColor(this, R.color.chapter_content_day),
                    ContextCompat.getColor(this, R.color.chapter_title_day));
        }
        mViewBinding.flReadWidget.removeAllViews();
        mViewBinding.flReadWidget.addView(mPageWidget);
    }

    @OnClick({R.id.module_include_title_bar_return,R.id.rl_bg, R.id.iv_open_catalogue, R.id.iv_close_catalogue,
            R.id.tv_setting,R.id.tv_bookshelf,R.id.tv_night_sun,R.id.cover,R.id.tv_default,R.id.tv_add,
            R.id.tv_sub,R.id.tv_model1,R.id.tv_model2,R.id.tv_model3,R.id.bg1,R.id.bg2,R.id.bg3,R.id.bg4,R.id.bg5})
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
                hideAppBarAndBottomControl();
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
                    mPresenter.addToMyNovel(this, bookId,mChapterList.get(currentChapter-1).getId());
                }
                break;
            case R.id.tv_night_sun:
                boolean isNight = Paper.book().read(Constant.ISNIGHT, false);
                changedMode(!isNight);
                break;
            case R.id.cover:
                hideAppBarAndBottomControl();
                break;
            case R.id.tv_default:
                textSize = 18;
                mPageWidget.setFontSize(ScreenUtils.dpToPxInt(18));
                break;
            case R.id.tv_add:
                if(textSize >36){
                    mViewBinding.tvAdd.setTextColor(getResources().getColor(R.color.c_999999));
                }else{
                    textSize++;
                    mViewBinding.tvAdd.setTextColor(getResources().getColor(R.color.white));
                    mPageWidget.setFontSize(ScreenUtils.dpToPxInt(textSize));
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
                    mPageWidget.setFontSize(ScreenUtils.dpToPxInt(textSize));
                }

                if(textSize<=36)
                    mViewBinding.tvAdd.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.tv_model1:
                changeModel(0);
                break;
            case R.id.tv_model2:
                changeModel(1);
                break;
            case R.id.tv_model3:
                changeModel(2);
                break;
            case R.id.bg1:
                changeBg(0);
                break;
            case R.id.bg2:
                changeBg(1);
                break;
            case R.id.bg3:
                changeBg(2);
                break;
            case R.id.bg4:
                changeBg(3);
                break;
            case R.id.bg5:
                changeBg(4);
                break;
        }
    }

    @Override
    public void click(int position) {
        mViewBinding.drawerLayout.closeDrawer(Gravity.START);
        currentChapter = position+1;
        startRead = false;
        mPageWidget.isPrepared = true;
        readCurrentChapter();
    }

    @Override
    public void error(String error) {

    }

    @Override
    public void getBooKInfo(BooKInfoEntity data) {
        if(data.getData()==null)
            return;
        Book book = data.getData().getNovel_info();
        mViewBinding.title.moduleIncludeTitleBarTitle.setText(book.getNovel().getName());
        Glide.with(this).load(book.getNovel().getCover()).into(mViewBinding.ivBookBg);
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

        if(currentChapter!=1 && !TextUtils.isEmpty(chapterId)){
            for (int i = 0; i < mChapterList.size(); i++) {
                if(mChapterList.get(i).getId().equals(chapterId)){
                    currentChapter = i+1;
                    break;
                }
            }
        }
        readCurrentChapter();
    }

    public void readCurrentChapter() {
//        if (CacheManager.getInstance().getChapterFile(book.getNovel().getId(), currentChapter) != null) {
//            showChapterRead(null, currentChapter);
//        } else {
//            mPresenter.getChapterRead(this,book.getNovel().getId(), mChapterList.get(currentChapter-1).getId(),currentChapter);
//        }
        mPresenter.getChapterRead(this,bookId, mChapterList.get(currentChapter-1).getId(),currentChapter);
    }

    @Override
    public void showChapterRead(BookChapterDetailEntity data, int current) {
        mViewBinding.loadingProgressbar.setVisibility(View.GONE);
        if (data != null) {
            ChapterRead.Chapter chapter = new ChapterRead.Chapter();
            String content = data.getChapter().getContent();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                content = String.valueOf(Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY));
            } else {
                content = String.valueOf(Html.fromHtml(content));
            }
            chapter.body = content;
            chapter.title = data.getChapter().getName();
            CacheManager.getInstance().saveChapterFile(bookId, current, chapter);
        }

        if (!startRead) {
            startRead = true;
            currentChapter = current;
            if (!mPageWidget.isPrepared) {
                mPageWidget.init(Paper.book().read(Constant.ISNIGHT, false)?ThemeManager.NIGHT:curTheme);
            } else {
                mPageWidget.jumpToChapter(currentChapter);
            }
        }
    }

    @Override
    public void addToMyNovel() {
        mViewBinding.tvBookshelf.setText("已加入书架");
        ToastUtils.getInstance().show(this,"加入书架成功");
    }

    private class ReadListener implements OnReadStateChangeListener {
        @Override
        public void onChapterChanged(int chapter) {
            currentChapter = chapter;
//            // 加载前一节 与 后三节
//            for (int i = chapter - 1; i <= chapter + 3 && i <= mChapterList.size(); i++) {
//                if (i > 0 && i != chapter
//                        && CacheManager.getInstance().getChapterFile(book.getNovel().getId(), i) == null) {
//                    mPresenter.getChapterRead(BookReadActivity.this,book.getNovel().getId(), mChapterList.get(i - 1).getId(),i);
//                }
//            }

            mPresenter.getChapterRead(BookReadActivity.this,bookId, mChapterList.get(chapter - 1).getId(),chapter);
        }

        @Override
        public void onPageChanged(int chapter, int page) {
        }

        @Override
        public void onLoadChapterFailure(int chapter) {
            startRead = false;
//            if (CacheManager.getInstance().getChapterFile(book.getNovel().getId(), chapter) == null)
//                mPresenter.getChapterRead(BookReadActivity.this,book.getNovel().getId(), mChapterList.get(chapter - 1).getId(),chapter);
            if(chapter>0){
                mPresenter.getChapterRead(BookReadActivity.this,bookId, mChapterList.get(chapter - 1).getId(),chapter);
            }else{
                mPresenter.getChapterRead(BookReadActivity.this,bookId, mChapterList.get(0).getId(),1);
            }
        }

        @Override
        public void onCenterClick() {
            if (mViewBinding.llBottom.getVisibility() == View.VISIBLE) {
                hideAppBarAndBottomControl();
            } else {
                showAppBarAndBottomControl();
            }
        }

        @Override
        public void onFlip() {

        }
    }

    /**
     * 显示ToolBar和底部控制栏
     */
    private void showAppBarAndBottomControl() {
        mViewBinding.cover.setVisibility(View.VISIBLE);
        mViewBinding.ivOpenCatalogue.setVisibility(View.VISIBLE);
        mViewBinding.titleBar.setVisibility(View.VISIBLE);
        mViewBinding.llBottom.setVisibility(View.VISIBLE);
    }

    private void hideAppBarAndBottomControl() {
        mViewBinding.cover.setVisibility(View.GONE);
        mViewBinding.ivOpenCatalogue.setVisibility(View.GONE);
        mViewBinding.titleBar.setVisibility(View.GONE);
        mViewBinding.llBottom.setVisibility(View.GONE);
        mViewBinding.llBottomSetting.setVisibility(View.GONE);
    }

    private void changedMode(boolean isNight) {
        Paper.book().write(Constant.ISNIGHT, isNight);
        if(isNight){
            mPageWidget.setTheme(ThemeManager.NIGHT);
            mPageWidget.setTextColor(ContextCompat.getColor(this, R.color.chapter_title_night),
                    ContextCompat.getColor(this, R.color.chapter_title_night));
            ThemeManager.setReaderTheme(ThemeManager.NIGHT, mViewBinding.rlBookReadRoot);
        }else{
            mPageWidget.setTheme(curTheme);
            ThemeManager.setReaderTheme(curTheme, mViewBinding.rlBookReadRoot);
            switch (curTheme){
                case 0:
                case 1:
                    mPageWidget.setTextColor(ContextCompat.getColor(this, R.color.c_333333),
                            ContextCompat.getColor(this, R.color.c_333333));
                    break;
                case 2:
                    mPageWidget.setTextColor(ContextCompat.getColor(this, R.color.c_5A4431),
                            ContextCompat.getColor(this, R.color.c_5A4431));
                    break;
                case 3:
                    mPageWidget.setTextColor(ContextCompat.getColor(this, R.color.c_97A4A8),
                            ContextCompat.getColor(this, R.color.c_97A4A8));
                    break;
                case 4:
                    mPageWidget.setTextColor(ContextCompat.getColor(this, R.color.c_822F3E),
                            ContextCompat.getColor(this, R.color.c_822F3E));
                    break;
            }
        }
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

    private void changeModel(int i) {
        Paper.book().write(Constant.FLIP_STYLE, i);
        initPagerWidget();
        readCurrentChapter();
    }

    private void changeBg(int i) {
        curTheme = i;
        mPageWidget.setTheme(Paper.book().read(Constant.ISNIGHT, false) ? ThemeManager.NIGHT : curTheme);
        ThemeManager.setReaderTheme(curTheme, mViewBinding.rlBookReadRoot);
        if (Paper.book().read(Constant.ISNIGHT, false)) {
            mPageWidget.setTextColor(ContextCompat.getColor(this, R.color.chapter_content_night),
                    ContextCompat.getColor(this, R.color.chapter_title_night));
        }else{
            switch (curTheme){
                case 0:
                case 1:
                    mPageWidget.setTextColor(ContextCompat.getColor(this, R.color.c_333333),
                            ContextCompat.getColor(this, R.color.c_333333));
                    break;
                case 2:
                    mPageWidget.setTextColor(ContextCompat.getColor(this, R.color.c_5A4431),
                            ContextCompat.getColor(this, R.color.c_5A4431));
                    break;
                case 3:
                    mPageWidget.setTextColor(ContextCompat.getColor(this, R.color.c_97A4A8),
                            ContextCompat.getColor(this, R.color.c_97A4A8));
                    break;
                case 4:
                    mPageWidget.setTextColor(ContextCompat.getColor(this, R.color.c_822F3E),
                            ContextCompat.getColor(this, R.color.c_822F3E));
                    break;
            }
        }
    }
}
