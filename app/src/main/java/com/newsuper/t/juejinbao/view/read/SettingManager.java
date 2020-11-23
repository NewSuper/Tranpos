
package com.newsuper.t.juejinbao.view.read;

import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.ui.book.bean.BookMark;
import com.newsuper.t.juejinbao.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class SettingManager {

    private volatile static SettingManager manager;

    public static SettingManager getInstance() {
        return manager != null ? manager : (manager = new SettingManager());
    }

    /**
     * 保存书籍阅读字体大小
     *
     * @param bookId     需根据bookId对应，避免由于字体大小引起的分页不准确
     * @param fontSizePx
     * @return
     */
    public void saveFontSize(String bookId, int fontSizePx) {
        // 书籍对应
        Paper.book().write(getFontSizeKey(bookId), fontSizePx);
    }

    /**
     * 保存全局生效的阅读字体大小
     *
     * @param fontSizePx
     */
    public void saveFontSize(int fontSizePx) {
        saveFontSize("", fontSizePx);
    }

    public int getReadFontSize(String bookId) {
        return Paper.book().read(getFontSizeKey(bookId), ScreenUtils.dpToPxInt(18));
    }

    public int getReadFontSize() {
        return getReadFontSize("");
    }

    private String getFontSizeKey(String bookId) {
        return bookId + "-readFontSize";
    }

    public int getReadBrightness() {
        return ScreenUtils.getScreenBrightness();
    }

    /**
     * 保存阅读界面屏幕亮度
     *
     * @param percent 亮度比例 0~100
     */
    public void saveReadBrightness(int percent) {
        if(percent > 100){
            percent = 100;
        }
        Paper.book().write(getLightnessKey(), percent);
    }

    private String getLightnessKey() {
        return "readLightness";
    }

    public synchronized void saveReadProgress(String bookId, int currentChapter, int m_mbBufBeginPos, int m_mbBufEndPos) {
        Paper.book().write(getChapterKey(bookId), currentChapter);
        Paper.book().write(getStartPosKey(bookId), m_mbBufBeginPos);
        Paper.book().write(getEndPosKey(bookId), m_mbBufEndPos);
    }

    /**
     * 获取上次阅读章节及位置
     *
     * @param bookId
     * @return
     */
    public int[] getReadProgress(String bookId) {
        int lastChapter = Paper.book().read(getChapterKey(bookId), 1);
        int startPos = Paper.book().read(getStartPosKey(bookId), 0);
        int endPos = Paper.book().read(getEndPosKey(bookId), 0);

        return new int[]{lastChapter, startPos, endPos};
    }

    public void removeReadProgress(String bookId) {
        Paper.book().delete(getChapterKey(bookId));
        Paper.book().delete(getStartPosKey(bookId));
        Paper.book().delete(getEndPosKey(bookId));
    }

    private String getChapterKey(String bookId) {
        return bookId + "-chapter";
    }

    private String getStartPosKey(String bookId) {
        return bookId + "-startPos";
    }

    private String getEndPosKey(String bookId) {
        return bookId + "-endPos";
    }


    public boolean addBookMark(String bookId, BookMark mark) {
        List<BookMark> marks =  Paper.book().read(getBookMarksKey(bookId));
        if (marks != null && marks.size() > 0) {
            for (BookMark item : marks) {
                if (item.chapter == mark.chapter && item.startPos == mark.startPos) {
                    return false;
                }
            }
        } else {
            marks = new ArrayList<>();
        }
        marks.add(mark);
        Paper.book().write(getBookMarksKey(bookId), marks);
        return true;
    }

    public List<BookMark> getBookMarks(String bookId) {
        return Paper.book().read(getBookMarksKey(bookId));
    }

    public void clearBookMarks(String bookId) {
        Paper.book().delete(getBookMarksKey(bookId));
    }

    private String getBookMarksKey(String bookId) {
        return bookId + "-marks";
    }

    public void saveReadTheme(int theme) {
        Paper.book().write("readTheme", theme);
    }

    public int getReadTheme() {
        if (Paper.book().read(Constant.ISNIGHT, false)) {
            return ThemeManager.NIGHT;
        }
        return Paper.book().read("readTheme", ThemeManager.THEME2);
    }

    /**
     * 是否可以使用音量键翻页
     *
     * @param enable
     */
    public void saveVolumeFlipEnable(boolean enable) {
        Paper.book().write("volumeFlip", enable);
    }

    public boolean isVolumeFlipEnable() {
        return Paper.book().read("volumeFlip", true);
    }

    public void saveAutoBrightness(boolean enable) {
        Paper.book().write("autoBrightness", enable);
    }

    public boolean isAutoBrightness() {
        return Paper.book().read("autoBrightness", false);
    }


    /**
     * 保存用户选择的性别
     *
     * @param sex male female
     */
    public void saveUserChooseSex(String sex) {
        Paper.book().write("userChooseSex", sex);
    }
}
