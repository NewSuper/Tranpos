package com.newsuper.t.juejinbao.ui.home.entity;

import java.io.Serializable;

public class HomeSearchDetailEvent implements Serializable {
    private String kw;

    public String getKw() {
        return kw;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }
}
