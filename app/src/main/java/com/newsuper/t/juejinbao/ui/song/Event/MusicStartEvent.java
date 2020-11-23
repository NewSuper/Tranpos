package com.newsuper.t.juejinbao.ui.song.Event;

import com.lzx.starrysky.provider.SongInfo;

public class MusicStartEvent {
    SongInfo songInfo;

    public MusicStartEvent(SongInfo songInfo) {
        this.songInfo = songInfo;
    }

    public SongInfo getSongInfo() {
        return songInfo;
    }

    public void setSongInfo(SongInfo songInfo) {
        this.songInfo = songInfo;
    }
}
