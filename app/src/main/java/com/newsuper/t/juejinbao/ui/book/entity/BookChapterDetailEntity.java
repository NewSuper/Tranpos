package com.newsuper.t.juejinbao.ui.book.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;

import java.util.List;

public class BookChapterDetailEntity extends Entity {

    private int code;
    private String msg;
    private UserInfo userinfo;
    private Novel novel;
    private Author author;
    private Category category;
    private Last last;
    private Data data;
    private ChapterConfig chapterconfig;
    private Chapter chapter;
    private int time;
    private String vsn;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserInfo getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserInfo userinfo) {
        this.userinfo = userinfo;
    }

    public Novel getNovel() {
        return novel;
    }

    public void setNovel(Novel novel) {
        this.novel = novel;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Last getLast() {
        return last;
    }

    public void setLast(Last last) {
        this.last = last;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public ChapterConfig getChapterconfig() {
        return chapterconfig;
    }

    public void setChapterconfig(ChapterConfig chapterconfig) {
        this.chapterconfig = chapterconfig;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getVsn() {
        return vsn;
    }

    public void setVsn(String vsn) {
        this.vsn = vsn;
    }

    public static class UserInfo{
        private String id;
        private String name;
        private String password;
        private String salt;
        private String reg_ip;
        private String reg_time;
        private String login_ip;
        private String qqid;
        private String weiboid;
        private String status;
        private String login_day;
        private String login_num;
        private String sign_day;
        private String sign_num;
        private String point;
        private String exp;
        private String author;
        private String jjb_uid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }

        public String getReg_ip() {
            return reg_ip;
        }

        public void setReg_ip(String reg_ip) {
            this.reg_ip = reg_ip;
        }

        public String getReg_time() {
            return reg_time;
        }

        public void setReg_time(String reg_time) {
            this.reg_time = reg_time;
        }

        public String getLogin_ip() {
            return login_ip;
        }

        public void setLogin_ip(String login_ip) {
            this.login_ip = login_ip;
        }

        public String getQqid() {
            return qqid;
        }

        public void setQqid(String qqid) {
            this.qqid = qqid;
        }

        public String getWeiboid() {
            return weiboid;
        }

        public void setWeiboid(String weiboid) {
            this.weiboid = weiboid;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLogin_day() {
            return login_day;
        }

        public void setLogin_day(String login_day) {
            this.login_day = login_day;
        }

        public String getLogin_num() {
            return login_num;
        }

        public void setLogin_num(String login_num) {
            this.login_num = login_num;
        }

        public String getSign_day() {
            return sign_day;
        }

        public void setSign_day(String sign_day) {
            this.sign_day = sign_day;
        }

        public String getSign_num() {
            return sign_num;
        }

        public void setSign_num(String sign_num) {
            this.sign_num = sign_num;
        }

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        public String getExp() {
            return exp;
        }

        public void setExp(String exp) {
            this.exp = exp;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getJjb_uid() {
            return jjb_uid;
        }

        public void setJjb_uid(String jjb_uid) {
            this.jjb_uid = jjb_uid;
        }
    }

    public static class Novel extends Entity{
        private String id;
        private String name;
        private String intro;
        private String cover;
        private String pinyin;
        private String initial;
        private String caption;
        private long postdate;
        private String isgood;
        private String status;
        private String isover;
        private String url_info;
        private String url_chapterlist;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

        public String getInitial() {
            return initial;
        }

        public void setInitial(String initial) {
            this.initial = initial;
        }

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public long getPostdate() {
            return postdate;
        }

        public void setPostdate(long postdate) {
            this.postdate = postdate;
        }

        public String getIsgood() {
            return isgood;
        }

        public void setIsgood(String isgood) {
            this.isgood = isgood;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIsover() {
            return isover;
        }

        public void setIsover(String isover) {
            this.isover = isover;
        }

        public String getUrl_info() {
            return url_info;
        }

        public void setUrl_info(String url_info) {
            this.url_info = url_info;
        }

        public String getUrl_chapterlist() {
            return url_chapterlist;
        }

        public void setUrl_chapterlist(String url_chapterlist) {
            this.url_chapterlist = url_chapterlist;
        }
    }

    public static class Author extends Entity{
        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class Category extends Entity{

        private String id;
        private String name;
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class Last extends Entity{
        private String id;
        private String name;
        private long time;
        private String siteid;
        private String sign;
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getSiteid() {
            return siteid;
        }

        public void setSiteid(String siteid) {
            this.siteid = siteid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class Data extends Entity{
        private String allvisit;
        private String monthvisit;
        private String weekvisit;
        private String dayvisit;
        private String marknum;
        private String votenum;
        private String downnum;

        public String getAllvisit() {
            return allvisit;
        }

        public void setAllvisit(String allvisit) {
            this.allvisit = allvisit;
        }

        public String getMonthvisit() {
            return monthvisit;
        }

        public void setMonthvisit(String monthvisit) {
            this.monthvisit = monthvisit;
        }

        public String getWeekvisit() {
            return weekvisit;
        }

        public void setWeekvisit(String weekvisit) {
            this.weekvisit = weekvisit;
        }

        public String getDayvisit() {
            return dayvisit;
        }

        public void setDayvisit(String dayvisit) {
            this.dayvisit = dayvisit;
        }

        public String getMarknum() {
            return marknum;
        }

        public void setMarknum(String marknum) {
            this.marknum = marknum;
        }

        public String getVotenum() {
            return votenum;
        }

        public void setVotenum(String votenum) {
            this.votenum = votenum;
        }

        public String getDownnum() {
            return downnum;
        }

        public void setDownnum(String downnum) {
            this.downnum = downnum;
        }
    }

    public static class ChapterConfig{
        private Info info;
        private NovelInfo novel;
        private From from;
        private Next next;
        private Prev prev;

        public Info getInfo() {
            return info;
        }

        public void setInfo(Info info) {
            this.info = info;
        }

        public NovelInfo getNovel() {
            return novel;
        }

        public void setNovel(NovelInfo novel) {
            this.novel = novel;
        }

        public From getFrom() {
            return from;
        }

        public void setFrom(From from) {
            this.from = from;
        }

        public Next getNext() {
            return next;
        }

        public void setNext(Next next) {
            this.next = next;
        }

        public Prev getPrev() {
            return prev;
        }

        public void setPrev(Prev prev) {
            this.prev = prev;
        }
    }

    public static class Info{
        private String id;
        private String title;
        private String url;
        private String content;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class NovelInfo extends Entity{
        private String id;
        private String name;
        private String marked;
        private String num;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMarked() {
            return marked;
        }

        public void setMarked(String marked) {
            this.marked = marked;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }

    public static class From{
        private String id;
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class Next{
        private String id;
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class Prev{
        private String id;
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class Chapter{

        private String id;
        private String oid;
        private String novelid;
        private String siteid;
        private String name;
        private String url;
        private String time;
        private String type;
        private String url_mark;
        private String url_green;
        private String url_frame;
        private String url_read;
        private PreInfo preinfo;
        private NextInfo nextinfo;
        private String sitename;
        private String url_dir;
        private List<Same> samelist;
        private String content;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        public String getNovelid() {
            return novelid;
        }

        public void setNovelid(String novelid) {
            this.novelid = novelid;
        }

        public String getSiteid() {
            return siteid;
        }

        public void setSiteid(String siteid) {
            this.siteid = siteid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl_mark() {
            return url_mark;
        }

        public void setUrl_mark(String url_mark) {
            this.url_mark = url_mark;
        }

        public String getUrl_green() {
            return url_green;
        }

        public void setUrl_green(String url_green) {
            this.url_green = url_green;
        }

        public String getUrl_frame() {
            return url_frame;
        }

        public void setUrl_frame(String url_frame) {
            this.url_frame = url_frame;
        }

        public String getUrl_read() {
            return url_read;
        }

        public void setUrl_read(String url_read) {
            this.url_read = url_read;
        }

        public PreInfo getPreinfo() {
            return preinfo;
        }

        public NextInfo getNextinfo() {
            return nextinfo;
        }

        public void setNextinfo(NextInfo nextinfo) {
            this.nextinfo = nextinfo;
        }

        public List<Same> getSamelist() {
            return samelist;
        }

        public void setSamelist(List<Same> samelist) {
            this.samelist = samelist;
        }

        public String getSitename() {
            return sitename;
        }

        public void setSitename(String sitename) {
            this.sitename = sitename;
        }

        public String getUrl_dir() {
            return url_dir;
        }

        public void setUrl_dir(String url_dir) {
            this.url_dir = url_dir;
        }

        public void setPreinfo(PreInfo preinfo) {
            this.preinfo = preinfo;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class PreInfo{
        private String name;
        private String id;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class NextInfo{
        private String name;
        private String id;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class Same{
        private String style;
        private String name;
        private String url;
        private String desc;

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
