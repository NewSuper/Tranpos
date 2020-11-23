package com.newsuper.t.juejinbao.ui.book.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;

import java.util.List;

public class BookshelfEntity extends Entity {

    /**
     * {
     * 	"code": 1,
     * 	"data": [{ // 无数据时为空数组
     * 		"novel": {
     * 			"id": "60", // 小说ID
     * 			"name": "过眼皆不及你", // 小说名称
     * 			"pinyin": "guoyanjiebujini",
     * 			"initial": "g",
     * 			"caption": "",
     * 			"intro": "四年前，她心灰意冷带着孩子远走他乡，\n四年后，她携女归来，这份爱情，\n能否战胜现实的重重桎梏，\n能否重新温暖他们的家庭？\n她原本已炼成坚硬盔甲无所不侵，\n可他轻抚脸颊击溃她的防线：\n你可知，世间美人无数，可过眼，皆不及你？\n---------------------------------------\n《过眼皆不及你》作者：丹秋",
     * 			"postdate": "1569667691",
     * 			"isgood": "0",
     * 			"status": "1",
     * 			"isover": "0",
     * 			"cover": "http:\/\/www.ddshu.net\/rscs\/2019_09\/1569604307_ddvip_3162.jpg",
     * 			"url_info": "\/novel\/guoyanjiebujini\/",
     * 			"url_chapterlist": "\/novel\/chapterlist\/guoyanjiebujini.html"
     *                },
     * 		"author": {
     * 			"name": "席绢作品集",
     * 			"url": "\/author\/%E5%B8%AD%E7%BB%A2%E4%BD%9C%E5%93%81%E9%9B%86"
     *        },
     * 		"category": {
     * 			"id": "13",
     * 			"name": "女生",
     * 			"url": "\/sort\/nvsheng.html"
     *        },
     * 		"last": {
     * 			"id": "16",
     * 			"name": "第十六章",
     * 			"time": "1569667692",
     * 			"siteid": "53",
     * 			"sign": "0",
     * 			"url": "\/novel\/guoyanjiebujini\/read_16.html"
     *        },
     * 		"data": {
     * 			"allvisit": "434",
     * 			"monthvisit": "238",
     * 			"weekvisit": "42",
     * 			"dayvisit": "0",
     * 			"marknum": "5",
     * 			"votenum": "0",
     * 			"downnum": "0"
     *        },
     * 		"hasnew": 15
     * 		}]
     * }
     */
    private int code;
    private String msg;
    private List<Book> data;
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

    public List<Book> getData() {
        return data;
    }

    public void setData(List<Book> data) {
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
}
