package com.newsuper.t.juejinbao.ui.movie.craw;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.bean.BaseConfigEntity;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class Analysis extends BaseAnalysis {


    /**
     * 获取单个影院搜索结果列表
     * @param key
     * @param data
     * @return
     */
    public List<BeanMovieSearchItem> getBeanMovieSearchList(final String key , String data){
        List<BeanMovieSearchItem> items = new ArrayList<>();
        CrawSearchMovieListValue doubanRecommendBean = null;
        try {
//            CrawSearchMovieListValue doubanRecommendBean = JSON.parseObject(data, CrawSearchMovieListValue.class);
//            Document doc = Jsoup.connect(doubanRecommendBean.getUrl().replace("{key}" , key)).get();
            doubanRecommendBean = JSON.parseObject(data, CrawSearchMovieListValue.class);

            BaseConfigEntity.DataBean dataBean = Paper.book().read(PagerCons.KEY_JJB_CONFIG);
            if(dataBean != null && dataBean.getMovie_parse_delay() != null){
                for(BaseConfigEntity.DataBean.MovieParseDelayBean movieParseDelayBean : dataBean.getMovie_parse_delay()){
                    if(doubanRecommendBean.getUrl().contains(movieParseDelayBean.getHost())){
                        Log.e("zy" , movieParseDelayBean.getHost() + " " + movieParseDelayBean.getDelay());
                        Thread.sleep(movieParseDelayBean.getDelay());
                    }
                }

            }





            Document doc = null;
            if(doubanRecommendBean.getRequestType() != null && doubanRecommendBean.getRequestType().equals("post")){
                Connection connection = Jsoup.connect(doubanRecommendBean.getUrl())
                        .userAgent("Mozilla/5.0")
                        .timeout(10 * 1000)
                        .method(Connection.Method.POST);

                if(doubanRecommendBean.getRequestFormData() != null) {
                    for (List<String> stringList : doubanRecommendBean.getRequestFormData()) {
                        if(stringList.size() == 2){
                            connection.data(stringList.get(0) , stringList.get(1).equals("key") ? key : stringList.get(1));
                        }

                    }
                }

                Connection.Response response = connection.followRedirects(true)
                        .execute();

                doc = response.parse();

            }else{
                doc = Jsoup.connect(doubanRecommendBean.getUrl().replace("{key}" , key)).timeout(10000).get();
            }

            Object objectList = doc;
            for (CrawValue listBean : doubanRecommendBean.getList()) {
                objectList = analysis(objectList, listBean);
            }

            if (objectList instanceof Elements) {
                //遍历list
//                for (Element ele : (Elements) objectList) {
                for(int i = doubanRecommendBean.getSourceListStartIndex() ; i < ((Elements) objectList).size() - doubanRecommendBean.getSourceListEndIndex() ; i++){
                    Element ele = ((Elements) objectList).get(i);

                    BeanMovieSearchItem beanMovieItem = new BeanMovieSearchItem();
                    beanMovieItem.setImg(getFieldValue(ele ,doubanRecommendBean.getItem().getImg() , doubanRecommendBean.getIgnore()));
                    beanMovieItem.setTitle(getFieldValue(ele ,doubanRecommendBean.getItem().getTitle() , doubanRecommendBean.getIgnore()));
                    beanMovieItem.setActor(getFieldValue(ele ,doubanRecommendBean.getItem().getActor() , doubanRecommendBean.getIgnore()));
                    beanMovieItem.setIntro(getFieldValue(ele ,doubanRecommendBean.getItem().getIntro() , doubanRecommendBean.getIgnore()));
                    beanMovieItem.setForm(getFieldValue(ele ,doubanRecommendBean.getItem().getForm() , doubanRecommendBean.getIgnore()));
                    beanMovieItem.setLink(CrawUtils.getClickUrl(doubanRecommendBean.getUrl().replace("{key}" , key)  , getFieldValue(ele ,doubanRecommendBean.getItem().getLink() , doubanRecommendBean.getIgnore()))  );

                    beanMovieItem.setCinema(doubanRecommendBean.getName());
                    beanMovieItem.setOrigin(doubanRecommendBean.getUrl().replace("{key}" , key));
                    beanMovieItem.setKey(key);
                    beanMovieItem.setLevel(doubanRecommendBean.getLevel());

                    items.add(beanMovieItem);
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
            if(doubanRecommendBean != null) {
                try {
                    final String host = new URI(doubanRecommendBean.getUrl().replace("{","").replace("}","")).toURL().getHost();
                    EventBus.getDefault().post(new EventCrawMovieError(host));
                }catch (Exception e2){
                    e2.printStackTrace();
                }
            }

        }

        return items;
    }

    private String getFieldValue(Element ele , List<CrawValue> crawValues , List<String> ignore){
        Object obj = ele;

        if(crawValues != null && ele != null && crawValues.size() > 0) {
            for (CrawValue pictureBean : crawValues) {
                obj = analysis(obj, pictureBean);
            }
            String str = obj.toString();

            if(ignore != null){
                for(String ignoreStr : ignore){
                    if(str.contains(ignoreStr)) {
                        str = str.replaceAll(ignoreStr, "");
                    }
                }
            }

            return str;
        }else{
            return "暂无数据";
        }
    }





}
