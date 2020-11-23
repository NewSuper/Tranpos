package com.newsuper.t.juejinbao.ui.song.Event;

import com.lzx.starrysky.provider.SongInfo;

public class StopMusicEvent {
    SongInfo songInfo;

    public StopMusicEvent(SongInfo songInfo) {
        this.songInfo = songInfo;
    }

    public SongInfo getSongInfo() {
        return songInfo;
    }

    public void setSongInfo(SongInfo songInfo) {
        this.songInfo = songInfo;
    }
}
