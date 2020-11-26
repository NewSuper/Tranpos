package com.newsuper.t.juejinbao.ui.movie.craw.crawmusic;


import com.newsuper.t.juejinbao.ui.movie.adapter.EasyAdapter;

import java.io.Serializable;

public class BeanMusic extends EasyAdapter.TypeBean implements Serializable {

    private String songName;

    private String singer;

    private String link;

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
