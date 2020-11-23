package com.newsuper.t.juejinbao.ui.book.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;

import java.util.List;

public class BookChapterEntity extends Entity {

    /**
     * {
     * 	"code": 1,
     * 	"msg": "",
     * 	"data": [{ // 小说的章节列表
     * 			"id": "288912", // 章节ID
     * 			"index_num": 15,
     * 			"name": "第四百九十一章 留下机缘", // 章节名称
     *                },
     *        {
     * 			"id": "288898",
     * 			"index_num": 1,
     * 			"name": "第四百七十八章 练习九气归一炼丹法",
     *        }
     * 	]
     * }
     */

    private int code;
    private String msg;
    private List<Chapter> data;
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

    public List<Chapter> getData() {
        return data;
    }

    public void setData(List<Chapter> data) {
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

    public static class Chapter{

        private String id;
        private int index_num;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getIndex_num() {
            return index_num;
        }

        public void setIndex_num(int index_num) {
            this.index_num = index_num;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
