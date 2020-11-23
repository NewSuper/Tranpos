package com.newsuper.t.juejinbao.ui.movie.bean;

//影视搜索解析列表加载状态
public class LoadState {
    private String kw;
    private boolean state;

    public LoadState(String kw, boolean state) {
        this.kw = kw;
        this.state = state;
    }

    public String getKw() {
        return kw;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
