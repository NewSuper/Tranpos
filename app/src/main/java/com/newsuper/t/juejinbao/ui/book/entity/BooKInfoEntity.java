package com.newsuper.t.juejinbao.ui.book.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;

public class BooKInfoEntity extends Entity {

    /**
     * {
     * 	"code": 1,
     * 	"data": {
     * 		"novel_info": {
     * 			"novel": {
     * 				"id": "1",
     * 				"name": "超神道术",
     * 				"intro": "你感觉自己力量不足，力量+1，\n你修炼了金钟罩，武功等级+1，\n你捡到了一本道经，悟性+1，\n你的对手力大无穷，金钟罩大圆满，你将金钟罩，铁布衫，十三横练全部+1，练成了金刚不坏神功，对手刀砍不动，崩溃自杀。\n……\n不知道从什么时候开始，你已经天下无敌！\n……\n书友群：181938106",
     * 				"cover": "http:\/\/r.m.b5200.net\/cover\/aHR0cDovL2Jvb2tjb3Zlci55dWV3ZW4uY29tL3FkYmltZy8zNDk1NzMvMTAxNDk1MjY0NS8xODA="
     *                        },
     * 			"author": {
     * 				"name": "当年烟火"
     *            },
     * 			"category": {
     * 				"id": "1",
     * 				"name": "玄幻"
     *            },
     * 			"last": {
     * 				"id": "497",
     * 				"name": "第四百九十一章 留下机缘",
     * 				"time": "1572975911"
     *            }*
     *       },
     * 		"marked": 0 // 是否已加入了书架：1已加入，0未加入
     *    }
     * }
     */

    private int code;
    private String msg;
    private BookInfo data;
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

    public BookInfo getData() {
        return data;
    }

    public void setData(BookInfo data) {
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

    public static class BookInfo{
        private Book novel_info;
        private int marked;

        public Book getNovel_info() {
            return novel_info;
        }

        public void setNovel_info(Book novel_info) {
            this.novel_info = novel_info;
        }

        public int getMarked() {
            return marked;
        }

        public void setMarked(int marked) {
            this.marked = marked;
        }
    }
}
