package com.newsuper.t.juejinbao.ui.movie.craw;


import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class MovieSearchManager {

    //计算完成时机
    int cinemaCompleteSize = 0;
    /**
     * 爬取影院搜索结果
     *
     * @param key
     */
    public void crawCinemaSearch(String key, List<String> datas, int max, final String tag) {
//        ThreadPoolInstance.getInstance().add(new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int maxItems = max;
//                int index = 0;
//                if(datas != null) {
//                    while (datas.size() != 0 && maxItems > 0 && datas.size() > index) {
//                        String data = datas.get(index);
//                        List<BeanMovieSearchItem> items = new Analysis().getBeanMovieSearchList(key, data);
//                        maxItems -= items.size();
//                        index++;
//
//                        for(BeanMovieSearchItem item : items){
//                            EventBus.getDefault().post(new EventCrawMovieList(tag , item , false));
//
//                            try {
//                                Thread.sleep(100);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//
//                        if(!(datas.size() > index)){
//                            EventBus.getDefault().post(new EventCrawMovieList(tag , null , true));
//                        }
//                    }
//                }
//
//            }
//        }));

        final int dataSize = datas.size();
        for (String data : datas) {
            ThreadPoolInstance.getInstance().add(new Thread(new Runnable() {
                @Override
                public void run() {

                    List<BeanMovieSearchItem> items = new Analysis().getBeanMovieSearchList(key, data);


                    for (BeanMovieSearchItem item : items) {
                        EventBus.getDefault().post(new EventCrawMovieList(tag, item, false));

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                    cinemaCompleteSize++;

                    if(cinemaCompleteSize == dataSize){
                        EventBus.getDefault().post(new EventCrawMovieList(tag, null, true));

                    }

                }
            }));
        }
    }


    public void destory() {

    }


}
