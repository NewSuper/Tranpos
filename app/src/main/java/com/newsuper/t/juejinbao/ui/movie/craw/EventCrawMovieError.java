package com.newsuper.t.juejinbao.ui.movie.craw;

public class EventCrawMovieError {
    private String host;;

    public EventCrawMovieError(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
