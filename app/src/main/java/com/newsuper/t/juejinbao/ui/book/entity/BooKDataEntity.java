package com.newsuper.t.juejinbao.ui.book.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;

import java.util.List;

public class BooKDataEntity extends Entity {

    private int code;
    private String msg;
    private BookData data;
    private int time;
    private String vsn;

    public static class BookData{

        private List<Book> zztj;
        private List<Book> rmtj;
        private List<Recommend> category_recommend;
        private List<Book> category_last;

        public List<Book> getZztj() {
            return zztj;
        }

        public void setZztj(List<Book> zztj) {
            this.zztj = zztj;
        }

        public List<Book> getRmtj() {
            return rmtj;
        }

        public void setRmtj(List<Book> rmtj) {
            this.rmtj = rmtj;
        }

        public List<Recommend> getCategory_recommend() {
            return category_recommend;
        }

        public void setCategory_recommend(List<Recommend> category_recommend) {
            this.category_recommend = category_recommend;
        }

        public List<Book> getCategory_last() {
            return category_last;
        }

        public void setCategory_last(List<Book> category_last) {
            this.category_last = category_last;
        }
    }

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

    public BookData getData() {
        return data;
    }

    public void setData(BookData data) {
        this.data = data;
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

    /**
     * {
     * 	'category_name' => '都市',
     * 	'list' => [{ // 分类推荐：动态，每一块显示4个分类推荐
     * 		"novel": {
     * 			"id": "60",
     * 			"name": "王者风暴",
     * 			"intro": "王者风暴最新章节由网友提供，《王者风暴》情节跌宕起伏、扣人心弦，是一本情节与文笔俱佳的武侠修真，本站免费提供王者风暴最新清爽干净的文字章节在线阅读。",
     * 			"cover": "http:\/\/img.bichi.me\/480\/480308\/480308s.jpg"
     *                },
     * 		"author": {
     * 			"name": "古剑锋"
     *        },
     * 		"category": {
     * 			"id": "3",
     * 			"name": "武侠"
     *        },
     * 		"last": {
     * 			"id": "1543",
     * 			"name": "第1522章 区块链游戏",
     * 			"time": "1568044820"
     *        }* 	}],
     * }
     */
    public static class Recommend extends Entity {

        private String category_name;
        private List<Book> list;

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public List<Book> getList() {
            return list;
        }

        public void setList(List<Book> list) {
            this.list = list;
        }
    }
}
