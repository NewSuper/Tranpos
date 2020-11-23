package com.newsuper.t.juejinbao.ui.movie.craw.moviedetail;

import com.alibaba.fastjson.JSON;
import com.juejinchain.android.module.movie.craw.BaseAnalysis;
import com.juejinchain.android.module.movie.craw.CrawValue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 电影详情爬取
 */
public class MovieDetailAnlysis extends BaseAnalysis {

    /**
     * 爬取影视详情
     *
     * @param url
     * @param value
     * @return
     */
    public BeanMovieDetail getMovieDetail(String url, String value) {
        BeanMovieDetail beanMovieDetail = new BeanMovieDetail();

        try {
            CrawMovieDetailValue crawMovieDetailValue = JSON.parseObject(value, CrawMovieDetailValue.class);
            Document doc = Jsoup.connect(url).timeout(10000).get();

            beanMovieDetail.setOrigin(url);
            beanMovieDetail.setImg(getFieldValue(doc, crawMovieDetailValue.getImg(), crawMovieDetailValue.getIgnore()));
            beanMovieDetail.setNow(getFieldValue(doc, crawMovieDetailValue.getNow(), crawMovieDetailValue.getIgnore()));
            beanMovieDetail.setTitle(getFieldValue(doc, crawMovieDetailValue.getTitle(), crawMovieDetailValue.getIgnore()));
            beanMovieDetail.setActor(getFieldValue(doc, crawMovieDetailValue.getActor(), crawMovieDetailValue.getIgnore()));
            beanMovieDetail.setForm(getFieldValue(doc, crawMovieDetailValue.getForm(), crawMovieDetailValue.getIgnore()));
            beanMovieDetail.setDirector(getFieldValue(doc, crawMovieDetailValue.getDirector(), crawMovieDetailValue.getIgnore()));
            beanMovieDetail.setYear(getFieldValue(doc, crawMovieDetailValue.getYear(), crawMovieDetailValue.getIgnore()));
            beanMovieDetail.setLanguage(getFieldValue(doc, crawMovieDetailValue.getLanguage(), crawMovieDetailValue.getIgnore()));
            beanMovieDetail.setArea(getFieldValue(doc, crawMovieDetailValue.getArea(), crawMovieDetailValue.getIgnore()));
            beanMovieDetail.setDetail(getFieldValue(doc, crawMovieDetailValue.getDetail(), crawMovieDetailValue.getIgnore()));

            //播放源名称
            List<BeanMovieDetail.PlaybackSource> playbackSourceList = new ArrayList<>();
            Object object2 = getCrawListObject(doc, crawMovieDetailValue.getSourceList());
            if (object2 instanceof Elements) {
                for (int i = crawMovieDetailValue.getSourceListStartIndex(); i < ((Elements) object2).size() - crawMovieDetailValue.getSourceListEndIndex(); i++) {
                    BeanMovieDetail.PlaybackSource playbackSource = new BeanMovieDetail.PlaybackSource();
                    playbackSource.setName(getFieldValue(((Elements) object2).get(i), crawMovieDetailValue.getSourceItemText(), crawMovieDetailValue.getIgnore()));
                    playbackSourceList.add(playbackSource);
                }
            }

            Object object3 = getCrawListObject(doc, crawMovieDetailValue.getPathList());
            if (object3 instanceof Elements) {
                if (((Elements) object3).size() == playbackSourceList.size()) {
                    for (int i = 0; i < ((Elements) object3).size(); i++) {
                        //播放源对应的资源列表
                        List<BeanMovieDetail.PlaybackSource.Drama> dramaList = new ArrayList<>();

                        Object object4 = getCrawListObject(((Elements) object3).get(i), crawMovieDetailValue.getPathItemList());
                        if (object4 instanceof Elements) {
                            for (Element element : (Elements) object4) {
                                //添加资源
                                BeanMovieDetail.PlaybackSource.Drama drama = new BeanMovieDetail.PlaybackSource.Drama();
                                drama.setName(getFieldValue(element, crawMovieDetailValue.getPathItemName(), null));

                                if(crawMovieDetailValue.getPlayCrawType() == 2){
                                    String[] strings = drama.getName().split(crawMovieDetailValue.getPlayCrawEx1());
                                    if(strings.length > 0){
                                        drama.setName(strings[0]);
                                    }
                                }

                                drama.setLink(createHttpUrl(url, getFieldValue(element, crawMovieDetailValue.getPathItemLink(), null)));
                                dramaList.add(drama);
                            }
                        }
                        playbackSourceList.get(i).setDramaSeries(dramaList);
                    }
                }

            }

            if(crawMovieDetailValue.getIgnoreSource() != null){
                for(String ignoreSource : crawMovieDetailValue.getIgnoreSource()){

                    Iterator<BeanMovieDetail.PlaybackSource> it = playbackSourceList.iterator();
                    while(it.hasNext()){
                        BeanMovieDetail.PlaybackSource x = it.next();
                        if(x.getName().contains(ignoreSource)){
                            it.remove();
                        }
                    }

//                    for(BeanMovieDetail.PlaybackSource playbackSource : playbackSourceList){
//                        if(playbackSource.getName().contains(ignoreSource)){
//
//                        }
//                    }
                }
            }

            beanMovieDetail.setPlaybackSources(playbackSourceList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return beanMovieDetail;
    }

    /**
     * 获取播放地址
     *
     * @param url
     * @param value
     * @return
     */
    public String getPlayPath(String url, String value) {
        try {
            CrawMovieDetailValue crawMovieDetailValue = JSON.parseObject(value, CrawMovieDetailValue.class);


            if (crawMovieDetailValue.getPlayCrawType() == 0) {
                Document doc = Jsoup.connect(url).timeout(10000).get();
                return createPlayUrl(getFieldValue(doc, crawMovieDetailValue.getPlay(), crawMovieDetailValue.getIgnore()), crawMovieDetailValue.getPlayHttpIndex());
            }
            else if(crawMovieDetailValue.getPlayCrawType() == 1){
                Document doc = Jsoup.connect(url).timeout(10000).get();
                String string = getFieldValue(doc, crawMovieDetailValue.getPlay(), crawMovieDetailValue.getIgnore());
                String[] strs1 = string.split(crawMovieDetailValue.getPlayCrawEx1());
                if(strs1.length == 2){
                    return strs1[1].split(crawMovieDetailValue.getPlayCrawEx2())[0];
                }
            }else if(crawMovieDetailValue.getPlayCrawType() == 2){
                String[] strs1 = url.split(crawMovieDetailValue.getPlayCrawEx1());
                if(strs1.length == 2){
                    return strs1[1];
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 获取字段的objectvalue
     *
     * @param object
     * @param crawValues
     * @return
     */
    private Object getCrawListObject(Object object, List<CrawValue> crawValues) {
        Object obj = object;
        if (crawValues != null && obj != null) {
            for (CrawValue pictureBean : crawValues) {
                obj = analysis(obj, pictureBean);
            }
        }
        return obj;

    }

    /**
     * 获取字段的tringvalue
     *
     * @param object
     * @param crawValues
     * @param ignore
     * @return
     */
    private String getFieldValue(Object object, List<CrawValue> crawValues, List<String> ignore) {
        Object obj = object;

        if (crawValues != null && obj != null && crawValues.size() > 0) {
            for (CrawValue pictureBean : crawValues) {
                obj = analysis(obj, pictureBean);
            }
            String str = obj.toString();


            if (ignore != null) {
                for (String ignoreStr : ignore) {
                    if (str.contains(ignoreStr)) {
                        str = str.replaceAll(ignoreStr, "");
                    }
                }
            }
            return str;
        } else {
            return "暂无数据";
        }
    }

    /**
     * 转化为可识别的url
     *
     * @param head
     * @param tail
     * @return
     */
    private String createHttpUrl(String head, String tail) {
        if (tail == null) {
            return null;
        }

        if (head == null) {
            return null;
        }

        if (tail.startsWith("http")) {
            return tail;
        } else {
            try {
                URL url = new URI(head).toURL();
                if (head.startsWith("http")) {
                    return "http://" + url.getHost() + tail;
                } else {
                    return "https://" + url.getHost() + tail;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 生成播放器可播放的地址
     *
     * @param playUrl
     * @param playHttpIndex
     * @return
     */
    private String createPlayUrl(String playUrl, int playHttpIndex) {
        if(playHttpIndex == 0){
            return playUrl;
        }


        if(playHttpIndex >= 10000){
            String[] sub = playUrl.split("http");
            return "http" + sub[sub.length - 1];

        }

        if (playUrl.startsWith("http")) {

            String[] sub = playUrl.split("http");
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < sub.length; i++) {
                if (i >= playHttpIndex + 1) {
                    sb.append("http");
                    sb.append(sub[i]);
                }
            }
            return sb.toString();

        }

        return playUrl;
    }


}
