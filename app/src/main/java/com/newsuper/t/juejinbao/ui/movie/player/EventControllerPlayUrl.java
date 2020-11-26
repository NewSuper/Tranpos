package com.newsuper.t.juejinbao.ui.movie.player;


import com.newsuper.t.juejinbao.ui.movie.craw.moviedetail.BeanMovieDetail;

import java.util.List;

public class EventControllerPlayUrl {
    public static final int TYPE_NOPLAY = 0;
    public static final int TYPE_PLAY = 1;


    private int tyep;
    private BeanMovieDetail.PlaybackSource.Drama drama;
    private List<BeanMovieDetail.PlaybackSource.Drama> dramaList;

    public EventControllerPlayUrl(int tyep, BeanMovieDetail.PlaybackSource.Drama drama , List<BeanMovieDetail.PlaybackSource.Drama> dramaList) {
        this.tyep = tyep;
        this.drama = drama;
        this.dramaList = dramaList;
    }

    public int getTyep() {
        return tyep;
    }

    public void setTyep(int tyep) {
        this.tyep = tyep;
    }

    public BeanMovieDetail.PlaybackSource.Drama getDrama() {
        return drama;
    }

    public void setDrama(BeanMovieDetail.PlaybackSource.Drama drama) {
        this.drama = drama;
    }

    public List<BeanMovieDetail.PlaybackSource.Drama> getDramaList() {
        return dramaList;
    }

    public void setDramaList(List<BeanMovieDetail.PlaybackSource.Drama> dramaList) {
        this.dramaList = dramaList;
    }
}
