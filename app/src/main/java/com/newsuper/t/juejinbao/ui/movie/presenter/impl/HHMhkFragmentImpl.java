package com.newsuper.t.juejinbao.ui.movie.presenter.impl;

import android.content.Context;
import android.text.TextUtils;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.movie.entity.DependentResourcesDataEntity;
import com.newsuper.t.juejinbao.ui.movie.utils.Utils;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.ArrayList;

import rx.Subscriber;
import rx.Subscription;

import static io.paperdb.Paper.book;

public class HHMhkFragmentImpl extends BasePresenter<HHMhkFragmentImpl.MvpView> {

    //影视相关资源
    public void requestDependentResource(Context context , Integer page , String oldkw , String type){

//        kw = kw.replace("(" , "")
//                .replace("~" , "")
//                .replace("`" , "")
//                .replace("!" , "")
//                .replace(")" , "")
//                .replace("@" , "")
//                .replace("#" , "")
//                .replace("$" , "")
//                .replace("%" , "")
//                .replace("^" , "")
//                .replace("&" , "")
//                .replace("*" , "")
//                .replace("[" , "")
//                .replace("]" , "")
//                .replace("{" , "")
//                .replace("}" , "")
//                .replace("," , "")
//                .replace("." , "")
//                .replace("/" , "")
//                .replace(";" , "")
//                .replace(":" , "")
//                .replace("+" , "")
//                .replace("-" , "")
//                .replace("_" , "")
//                .replace("=" , "")
//                .replace("|" , "")
//                .replace("*" , "")
//                .replace("?" , "").trim();
        String kw = Utils.stringFilter(oldkw).trim();

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

                for(DependentResourcesDataEntity.DataBeanX.DataBean dataBean : testBean.getData().getData()){
                    dataBean.ckey = testBean.getData().getCkey();
                }

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

    public interface MvpView{
        public void requestDependentResource(DependentResourcesDataEntity dependentResourcesDataEntity, int page);
        void error();
    }
}
