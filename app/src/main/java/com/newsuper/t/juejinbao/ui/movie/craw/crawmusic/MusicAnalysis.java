package com.newsuper.t.juejinbao.ui.movie.craw.crawmusic;

import com.alibaba.fastjson.JSON;
import com.newsuper.t.juejinbao.ui.movie.craw.BaseAnalysis;
import com.newsuper.t.juejinbao.ui.movie.craw.CrawValue;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class MusicAnalysis extends BaseAnalysis {

    /**
     * 音乐列表搜索结果
     * @param key
     * @param data
     * @return
     */
    public List<BeanMusic> getBeanMusicList(final String key , String data){
        List<BeanMusic> items = new ArrayList<>();

        try {
//            CrawSearchMovieListValue doubanRecommendBean = JSON.parseObject(data, CrawSearchMovieListValue.class);
//            Document doc = Jsoup.connect(doubanRecommendBean.getUrl().replace("{key}" , key)).get();
            CrawMusicValue doubanRecommendBean = JSON.parseObject(data, CrawMusicValue.class);

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

                    BeanMusic beanMovieItem = new BeanMusic();
                    beanMovieItem.setLink(getFieldValue(ele, doubanRecommendBean.getItem().getLink(), new ArrayList<>()) );

                    String[] song = getFieldValue(ele, doubanRecommendBean.getItem().getSongName(), doubanRecommendBean.getIgnore()).split("-");

                    if(song.length == 2) {
                        beanMovieItem.setSongName(song[1]);
                        beanMovieItem.setSinger(song[0]);
                        items.add(beanMovieItem);
                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
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
