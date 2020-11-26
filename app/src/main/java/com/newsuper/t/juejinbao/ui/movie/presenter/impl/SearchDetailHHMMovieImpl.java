package com.newsuper.t.juejinbao.ui.movie.presenter.impl;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.movie.bean.SearchMovieCache;
import com.newsuper.t.juejinbao.ui.movie.entity.DependentResourcesDataEntity;
import com.newsuper.t.juejinbao.ui.movie.entity.MovieCinamesEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;

import static io.paperdb.Paper.book;

public class SearchDetailHHMMovieImpl extends BasePresenter<SearchDetailHHMMovieImpl.MvpView> {


    //影院列表
    public void requestCinemas(Context context){

        rx.Observable<MovieCinamesEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).getCinemas().map((new HttpResultFunc<MovieCinamesEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<MovieCinamesEntity>() {
            @Override
            public void next(MovieCinamesEntity testBean) {
                getView().requestCinemas(testBean);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error();
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }


    //影视相关资源
    public void requestDependentResource(Context context , Integer page , String kw , String type){

        kw = kw.replace("(" , "")
                .replace("~" , "")
                .replace("`" , "")
                .replace("!" , "")
                .replace(")" , "")
                .replace("@" , "")
                .replace("#" , "")
                .replace("$" , "")
                .replace("%" , "")
                .replace("^" , "")
                .replace("&" , "")
                .replace("*" , "")
                .replace("[" , "")
                .replace("]" , "")
                .replace("{" , "")
                .replace("}" , "")
                .replace("," , "")
                .replace("." , "")
                .replace("/" , "")
                .replace(";" , "")
                .replace(":" , "")
                .replace("+" , "")
                .replace("-" , "")
                .replace("_" , "")
                .replace("=" , "")
                .replace("|" , "")
                .replace("*" , "")
                .replace("?" , "");

//        if(!TextUtils.isEmpty(kw)){
//            //储存标签
//            ArrayList<String> labels = book().read(BaseConstants.MOVIE_SEARCH_HISTORY , new ArrayList<String>());
//
//            if(!labels.contains(kw)) {
//                labels.add(0 ,kw);
//            }
//
//            book().write(BaseConstants.MOVIE_SEARCH_HISTORY, labels);
//        }



        rx.Observable<DependentResourcesDataEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).getDependentResources(page , kw , type).map((new HttpResultFunc<DependentResourcesDataEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<DependentResourcesDataEntity>() {
            @Override
            public void next(DependentResourcesDataEntity testBean) {
                getView().requestDependentResource(testBean , page);
            }

            @Override
            public void error(String target ,Throwable e,String errResponse) {
                getView().error();
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }


    //保存数据
    public synchronized void saveCache(String kw , String name , String json){
        String cache = book().read(PagerCons.KEY_MOVIESEARCH_JS_DATA);
        //不存在缓存对象
        if(TextUtils.isEmpty(cache)){
            //新建-填充

            List<SearchMovieCache.SearchMovieKwData.Cinema> cinemas = new ArrayList<>();
            SearchMovieCache.SearchMovieKwData.Cinema cinema = new SearchMovieCache.SearchMovieKwData.Cinema();
            cinema.setName(name);
            cinema.setData(json);
            cinemas.add(cinema);


            SearchMovieCache.SearchMovieKwData searchMovieKwData = new SearchMovieCache.SearchMovieKwData();
            List<SearchMovieCache.SearchMovieKwData> datas = new ArrayList<>();
            searchMovieKwData.setKw(kw);
            searchMovieKwData.setCinemas(cinemas);
            datas.add(searchMovieKwData);

            SearchMovieCache searchMovieCache = new SearchMovieCache();
            searchMovieCache.setDatas(datas);

            book().write(PagerCons.KEY_MOVIESEARCH_JS_DATA , JSON.toJSONString(searchMovieCache));


        }
        //存在缓存对象
        else{
            SearchMovieCache searchMovieCache = JSON.parseObject(cache, SearchMovieCache.class);

            //是否存在此kw
            SearchMovieCache.SearchMovieKwData searchMovieKwData = null;

            for(SearchMovieCache.SearchMovieKwData searchMovieKwData1 : searchMovieCache.getDatas()){
                if(searchMovieKwData1.getKw().equals(kw)){
                    searchMovieKwData = searchMovieKwData1;
                    break;
                }
            }

            //存在kw
            if(searchMovieKwData != null ){

                //是否存在此cinema
                SearchMovieCache.SearchMovieKwData.Cinema cinema = null;
                for(SearchMovieCache.SearchMovieKwData.Cinema cinema1 : searchMovieKwData.getCinemas()){
                    if(cinema1.getName().equals(name)){
                        cinema = cinema1;
                        break;
                    }
                }

                //存在影院
                if(cinema != null){
                    cinema.setData(json);
                    cinema.setTimastamp(System.currentTimeMillis());
                }
                //添加影院
                else{
                    SearchMovieCache.SearchMovieKwData.Cinema newCinema = new SearchMovieCache.SearchMovieKwData.Cinema();
                    newCinema.setName(name);
                    newCinema.setData(json);
                    newCinema.setTimastamp(System.currentTimeMillis());
                    searchMovieKwData.getCinemas().add(newCinema);
                }

                //保存
                book().write(PagerCons.KEY_MOVIESEARCH_JS_DATA , JSON.toJSONString(searchMovieCache));
            }
            //不存在
            else{

                //到达kw数量上限，移除最旧的
                while (searchMovieCache.getDatas().size() >= 10){
                    searchMovieCache.getDatas().remove(0);
                }

                List<SearchMovieCache.SearchMovieKwData.Cinema> cinemas = new ArrayList<>();
                SearchMovieCache.SearchMovieKwData.Cinema cinema = new SearchMovieCache.SearchMovieKwData.Cinema();
                cinema.setName(name);
                cinema.setData(json);
                cinema.setTimastamp(System.currentTimeMillis());
                cinemas.add(cinema);


                SearchMovieCache.SearchMovieKwData searchMovieKwData1 = new SearchMovieCache.SearchMovieKwData();
                List<SearchMovieCache.SearchMovieKwData> datas = new ArrayList<>();
                searchMovieKwData1.setKw(kw);
                searchMovieKwData1.setCinemas(cinemas);
                datas.add(searchMovieKwData1);


                searchMovieCache.getDatas().add(searchMovieKwData1);

                //保存
                book().write(PagerCons.KEY_MOVIESEARCH_JS_DATA , JSON.toJSONString(searchMovieCache));
            }


        }
    }

    //读取缓存
    public synchronized SearchMovieCache.SearchMovieKwData.Cinema readCache(String kw , String url){
        String cache = book().read(PagerCons.KEY_MOVIESEARCH_JS_DATA);
        if(!TextUtils.isEmpty(cache)){
            SearchMovieCache searchMovieCache = JSON.parseObject(cache, SearchMovieCache.class);


            for(SearchMovieCache.SearchMovieKwData searchMovieKwData : searchMovieCache.getDatas()){
                //存在kw
                if(searchMovieKwData.getKw().equals(kw)){

                    for(SearchMovieCache.SearchMovieKwData.Cinema cinema : searchMovieKwData.getCinemas()){
                        //存在影院
                        if(!TextUtils.isEmpty(url) && url.contains(cinema.getName())){
//                            String json = cinema.getData();
                            return cinema;
                        }
                    }

                    break;
                }
            }
        }

        return null;
    }



    public interface MvpView{
        public void requestDependentResource(DependentResourcesDataEntity dependentResourcesDataEntity, int page);
        void requestCinemas(MovieCinamesEntity movieCinamesEntity);
        void error();
    }

}
