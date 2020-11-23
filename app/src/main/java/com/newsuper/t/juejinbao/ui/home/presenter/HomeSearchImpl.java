package com.newsuper.t.juejinbao.ui.home.presenter;

import com.juejinchain.android.module.home.entity.TodayHotEntity;
import com.ys.network.base.BasePresenter;
import com.ys.network.base.PagerCons;

import java.util.ArrayList;
import java.util.List;

import static io.paperdb.Paper.book;

public class HomeSearchImpl extends BasePresenter<HomeSearchImpl.MvpView> {

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

    public interface MvpView{
    }
}
