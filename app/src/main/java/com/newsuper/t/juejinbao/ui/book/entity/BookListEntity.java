package com.newsuper.t.juejinbao.ui.book.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;

import java.util.List;

public class BookListEntity extends Entity {

    /**
     * {
     * 	"code": 1,
     * 	"data": {
     * 		"page": 1, // 当前页码
     * 		"pagesize": "15", // 每页显示的数量
     * 		"totalnum": 20991, // 总数量
     * 		"list": [ // 小说列表（格式全接口一至）
     *                        {
     * 				"novel": {
     * 					"id": "2851",
     * 					"name": "独步成仙",
     * 					"intro": "一段凡人的成仙史\n一段仙界囚龙的秘辛\n陆小天最初的追求不过是踏上永生的仙道，但披荆斩棘得偿所愿之后，却发现仙远远不是尽头\n呃，书名从《仙武神煌》改成《独步成仙》了\n...................\nPS：推荐锤子已经完本书《抗战之钢铁风暴》种田文\n...................\n陆小天群：1**1**5**2**4**2**0**9**3",
     * 					"cover": "http:\/\/r.m.b5200.net\/cover\/aHR0cDovL3FpZGlhbi5xcGljLmNuL3FkYmltZy8zNDk1NzMvMTAwMzgwMjgwMy8xODA="
     *                },
     * 				"author": {
     * 					"name": "搞个锤子"
     *                },
     * 				"category": {
     * 					"id": "16",
     * 					"name": "文学"
     *                },
     * 				"last": {
     * 					"id": "2318",
     * 					"name": "2316章 商议",
     * 					"time": "1573012877"
     *                }
     *            }
     * 		],
     * 		"top": {
     * 			"key": "lastupdate",
     * 			"name": "最新更新"* 		}
     *    }
     * }
     */
    private int code;
    private String msg;
    private BookData data;
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

    public static class BookData{
        private int page;
        private int pagesize;
        private int totalnum;
        private List<Book> list;
        private Top top;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPagesize() {
            return pagesize;
        }

        public void setPagesize(int pagesize) {
            this.pagesize = pagesize;
        }

        public int getTotalnum() {
            return totalnum;
        }

        public void setTotalnum(int totalnum) {
            this.totalnum = totalnum;
        }

        public List<Book> getList() {
            return list;
        }

        public void setList(List<Book> list) {
            this.list = list;
        }

        public Top getTop() {
            return top;
        }

        public void setTop(Top top) {
            this.top = top;
        }
    }

    public static class Top{
        private String key;
        private String name;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
