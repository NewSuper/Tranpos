package com.newsuper.t.juejinbao.ui.movie.entity;

import java.io.Serializable;
import java.util.List;

public class VideoProgressBean implements Serializable {

    private List<ProgressBean> progressBeanList;

    public List<ProgressBean> getProgressBeanList() {
        return progressBeanList;
    }

    public void setProgressBeanList(List<ProgressBean> progressBeanList) {
        this.progressBeanList = progressBeanList;
    }

    public static class ProgressBean{
        private String title;
        private int index;
        private int progress;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getProgress() {
            return progress;
        }

        public void setProgress(int progress) {
            this.progress = progress;
        }
    }

}
