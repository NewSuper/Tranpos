package com.newsuper.t.juejinbao.ui.book.bean;

public class BookChapterBean {

    /**
     * title : 第一章 他叫白小纯
     * link : http://read.qidian.com/chapter/rJgN8tJ_cVdRGoWu-UQg7Q2/6jr-buLIUJSaGfXRMrUjdw2
     * unreadble : false
     */
    private String id;

    private String link;

    private String title;

    //所属的下载任务
    private String taskName;

    private boolean unreadble;

    //所属的书籍
    private String bookId;

    //本地书籍参数

    //在书籍文件中的起始位置
    private long start;

    //在书籍文件中的终止位置
    private long end;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isUnreadble() {
        return unreadble;
    }

    public void setUnreadble(boolean unreadble) {
        this.unreadble = unreadble;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public boolean getUnreadble() {
        return this.unreadble;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "BookChapterBean{" +
                "id='" + id + '\'' +
                ", link='" + link + '\'' +
                ", title='" + title + '\'' +
                ", taskName='" + taskName + '\'' +
                ", unreadble=" + unreadble +
                ", bookId='" + bookId + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
