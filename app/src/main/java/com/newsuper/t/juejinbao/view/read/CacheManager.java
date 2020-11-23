
package com.newsuper.t.juejinbao.view.read;

import android.content.Context;
import android.text.TextUtils;

import com.newsuper.t.juejinbao.base.JJBApplication;
import com.newsuper.t.juejinbao.ui.book.bean.BookLists;
import com.newsuper.t.juejinbao.ui.book.bean.BookMixAToc;
import com.newsuper.t.juejinbao.ui.book.bean.ChapterRead;
import com.newsuper.t.juejinbao.utils.ACache;
import com.newsuper.t.juejinbao.utils.FileUtils;
import com.newsuper.t.juejinbao.utils.Utils;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class CacheManager {

    private static CacheManager manager;

    public static CacheManager getInstance() {
        return manager == null ? (manager = new CacheManager()) : manager;
    }

    public List<String> getSearchHistory() {
        return Paper.book().read(getSearchHistoryKey());
    }

    public void saveSearchHistory(Object obj) {
        Paper.book().write(getSearchHistoryKey(), obj);
    }

    private String getSearchHistoryKey() {
        return "searchHistory";
    }

    /**
     * 获取我收藏的书单列表
     *
     * @return
     */
    public List<BookLists.BookListsBean> getCollectionList() {
        List<BookLists.BookListsBean> list = (ArrayList<BookLists.BookListsBean>) ACache.get(
                JJBApplication.getInstance()).getAsObject(getCollectionKey());
        return list == null ? null : list;
    }

    public void removeCollection(String bookListId) {
        List<BookLists.BookListsBean> list = getCollectionList();
        if (list == null) {
            return;
        }
        for (BookLists.BookListsBean bean : list) {
            if (bean != null) {
                if (TextUtils.equals(bean._id, bookListId)) {
                    list.remove(bean);
                    ACache.get(JJBApplication.getInstance()).put(getCollectionKey(), (Serializable) list);
                    break;
                }
            }
        }
    }

    public void addCollection(BookLists.BookListsBean bean) {
        List<BookLists.BookListsBean> list = getCollectionList();
        if (list == null) {
            list = new ArrayList<>();
        }
        for (BookLists.BookListsBean data : list) {
            if (data != null) {
                if (TextUtils.equals(data._id, bean._id)) {
                    return;
                }
            }
        }
        list.add(bean);
        ACache.get(JJBApplication.getInstance()).put(getCollectionKey(), (Serializable) list);
    }

    private String getCollectionKey() {
        return "my_book_lists";
    }

    /**
     * 获取目录缓存
     *
     * @param mContext
     * @param bookId
     * @return
     */
    public List<BookMixAToc.mixToc.Chapters> getTocList(Context mContext, String bookId) {
        Object obj = ACache.get(mContext).getAsObject(getTocListKey(bookId));
        if (obj != null) {
            try {
                BookMixAToc data = (BookMixAToc) obj;
                List<BookMixAToc.mixToc.Chapters> list = data.mixToc.chapters;
                if (list != null && !list.isEmpty()) {
                    return list;
                }
            } catch (Exception e) {
                ACache.get(mContext).remove(getTocListKey(bookId));
            }
        }
        return null;
    }

    public void saveTocList(Context mContext, String bookId, BookMixAToc data) {
        ACache.get(mContext).put(getTocListKey(bookId), data);
    }

    public void removeTocList(Context mContext, String bookId) {
        ACache.get(mContext).remove(getTocListKey(bookId));
    }

    private String getTocListKey(String bookId) {
        return bookId + "-bookToc";
    }

    public File getChapterFile(String bookId, int chapter) {
        File file = FileUtils.getChapterFile(bookId, chapter);
        if (file != null && file.length() > 50)
            return file;
        return null;
    }

    public void saveChapterFile(String bookId, int chapter, ChapterRead.Chapter data) {
        File file = FileUtils.getChapterFile(bookId, chapter);
        FileUtils.writeFile(file.getAbsolutePath(), Utils.formatContent(data.body), false);
    }
}
