package com.newsuper.t.juejinbao.ui.home.entity;

import java.util.List;


public class ArticleShowBigPicEntity {

    /**
     * position : 0
     * list : ["http://p3-tt.byteimg.com/large/pgc-image/b38d74f42b594c8b94d6e9d6a9a4338a?from=detail","http://p6-tt.byteimg.com/large/pgc-image/3cdef4ca9afa452f91af3a8596cccf21?from=detail","http://p6-tt.byteimg.com/large/pgc-image/728e8ec6937b4b549631d9c7245bcca4?from=detail"]
     */

    private int position;
    private List<String> list;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
