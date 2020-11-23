package com.newsuper.t.juejinbao.ui.book.entity;
import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;

import java.util.List;

public class BookCategoryEntity extends Entity {

    /**
     * {
     * 	"code": 1,
     * 	"data": [{
     * 			"id": 0, // 分类ID
     * 			"name": "全部", // 分类名称
     * 			"num": 20991
     *                },
     *        {
     * 			"id": "16",
     * 			"name": "文学",
     * 			"num": 2183
     *        }
     * 	]
     * }
     */

    private int code;
    private String msg;
    private List<Category> data;
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

    public List<Category> getData() {
        return data;
    }

    public void setData(List<Category> data) {
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

    public static class Category{
        private String id;
        private String name;
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

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }
}
