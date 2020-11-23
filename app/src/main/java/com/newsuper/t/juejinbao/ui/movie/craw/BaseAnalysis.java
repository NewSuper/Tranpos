package com.newsuper.t.juejinbao.ui.movie.craw;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BaseAnalysis {

    protected Object analysis(Object object, CrawValue analysisBean) {
        if (object instanceof Document) {
            if (analysisBean.getT().equals("select")) {
                return ((Document) object).select(analysisBean.getN());
            }
        } else if (object instanceof Element) {
            if (analysisBean.getT().equals("select")) {
                return getChildren(((Element) object).select(analysisBean.getN()), analysisBean.getI());
            } else if (analysisBean.getT().equals("attr")) {
                return ((Element) object).attr(analysisBean.getN());
            }else if (analysisBean.getT().equals("text")) {
                return ((Element) object).text();
            }
        } else if (object instanceof Elements) {
            if (analysisBean.getT().equals("select")) {
                return getChildren(((Elements) object).select(analysisBean.getN()), analysisBean.getI());
            } else if (analysisBean.getT().equals("attr")) {
                return ((Elements) object).attr(analysisBean.getN());
            } else if (analysisBean.getT().equals("element")) {
                return ((Elements) object).get(analysisBean.getI());
            }else if (analysisBean.getT().equals("text")) {
                return getText((Elements) object, analysisBean.getI());
            }
        }
        return null;
    }

    protected Object getChildren(Elements elements, int index) {

        if (index == -1) {
            return elements;
        }

        else if(index >= 10000){
            if(elements.size() > ((elements.size() - 1) - (index - 10000))) {
                if( elements.get((elements.size() - 1) - (index - 10000)).children().size() == 0){
                    return elements.get((elements.size() - 1) - (index - 10000));
                }else {
                    return elements.get((elements.size() - 1) - (index - 10000)).children();
                }
            }
        }
        else {
            if(elements.size() > index) {
                if(elements.get(index).children().size() == 0){
                    return elements.get(index);
                }else {
                    return elements.get(index).children();
                }
            }
        }

        return elements;
    }

    protected String getText(Elements elements, int index) {
        if (index == -1) {
            return elements.text();
        } else {
            if(elements.size() > index) {
                return elements.get(index).text();
            }
        }
        return elements.text();
    }



}
