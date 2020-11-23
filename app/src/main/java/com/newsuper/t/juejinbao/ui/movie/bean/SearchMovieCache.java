package com.newsuper.t.juejinbao.ui.movie.bean;

import java.util.List;

//保存JS抓取返回的数据
public class SearchMovieCache {

    private List<SearchMovieKwData> datas;

    public List<SearchMovieKwData> getDatas() {
        return datas;
    }

    public void setDatas(List<SearchMovieKwData> datas) {
        this.datas = datas;
    }

    public static class SearchMovieKwData {

        private String kw;
        private List<Cinema> cinemas;

        public String getKw() {
            return kw;
        }

        public void setKw(String kw) {
            this.kw = kw;
        }

        public List<Cinema> getCinemas() {
            return cinemas;
        }

        public void setCinemas(List<Cinema> cinemas) {
            this.cinemas = cinemas;
        }

        public static class Cinema {
            private String name;
            private String data;
            private long timastamp;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getData() {
                return data;
            }

            public void setData(String data) {
                this.data = data;
            }

            public long getTimastamp() {
                return timastamp;
            }

            public void setTimastamp(long timastamp) {
                this.timastamp = timastamp;
            }

        }

    }

}
