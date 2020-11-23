package com.newsuper.t.juejinbao.ui.movie.bean;

import java.io.Serializable;


public class HuaweiPushEntity implements Serializable {


    /**
     * jsonData : {"action_type":"12","action_url":"6043755","show_title":"内部浏览器展示标题","desc":"描述","imgurl":"http://p1-tt.byteimg.com/large/pgc-image/RXrOdURDrloHTt?from=detail","show_type":"1"}
     */

    private String jsonData;

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }
}
