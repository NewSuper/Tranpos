package com.newsuper.t.juejinbao.ui.home.presenter;

import android.app.Activity;

import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.BasePresenter;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.ui.home.entity.TodayHotEntity;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.ToastUtils;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;

import static io.paperdb.Paper.book;

public class HomeSearchHistoryImpl extends BasePresenter<HomeSearchHistoryImpl.MvpView> {

    public void getHotWordRank(Activity activity) {
        final MvpView baseView = getView();
        if (baseView == null) {
            return;
        }
        Map<String, String> StringMap = new HashMap<>();
        rx.Observable<TodayHotEntity> observable = RetrofitManager.getInstance(activity).create(ApiService.class).
                getHotWordRank(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<TodayHotEntity>() {
            @Override
            public void next(TodayHotEntity entity) {
                if(entity.getCode()==0){
                    baseView.getHotWordRankSuccess(entity);
                }else{
                    ToastUtils.getInstance().show(activity,entity.getMsg());
                }
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
            }
        }, activity, false);
        RetrofitManager.getInstance(activity).toSubscribe(observable, (Subscriber) rxSubscription);
        addSubscrebe(rxSubscription);
    }

    public void addHistory(String title , String word){
        TodayHotEntity.DataBean hotWordsBean = new TodayHotEntity.DataBean();
        hotWordsBean.setTitle(title);
        hotWordsBean.setEncode_hot_word(word);

        //获取之前的历史
        List<TodayHotEntity.DataBean> history = new ArrayList<>();
        Object object = book().read(PagerCons.KEY_HOME_SEARCH_HISTORY);
        if(object != null){
            history = (List<TodayHotEntity.DataBean>) object;
        }
        //删除之前的
        for(int i = 0 ; i < history.size() ; i++){
            TodayHotEntity.DataBean hotWordsBean1 = history.get(i);
            if(hotWordsBean.getTitle().equals(hotWordsBean1.getTitle())){
                history.remove(hotWordsBean1);
                break;
            }
        }
        //添加
        history.add(0 , hotWordsBean);
        while (history.size() > 10){
            history.remove(10);
        }
        //保存
        book().write(PagerCons.KEY_HOME_SEARCH_HISTORY , history);
    }

    public List<TodayHotEntity.DataBean> getHistory(){
        //获取之前的历史
        List<TodayHotEntity.DataBean> history = new ArrayList<>();
        Object object = book().read(PagerCons.KEY_HOME_SEARCH_HISTORY);
        if(object != null){
            history = (List<TodayHotEntity.DataBean>) object;
        }
        return history;
    }

    public void removeHistory(){
        book().write(PagerCons.KEY_HOME_SEARCH_HISTORY , new ArrayList<>());
    }

    public interface MvpView{
        void getHotWordRankSuccess(TodayHotEntity entity);
    }
}
