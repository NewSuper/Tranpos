package com.newsuper.t.juejinbao.utils;

/**
 * 数据模拟 - 自测
 */

import com.alibaba.fastjson.JSON;
import com.newsuper.t.juejinbao.ui.home.entity.ADConfigEntity;

public class DataService {
    /**
     * 广告配置
     *
     * @return
     */
    public static ADConfigEntity getAdConfig() {

        String json = "{\"code\":0,\"msg\":\"success\",\"data\":{\"ad_type1\":{\"platform\":1},\"ad_type2\":{\"platform\":1},\"ad_type3\":{\"type\":0,\"platform\":0,\"platform_list\":[{\"platform\":0,\"radio_num\":0},{\"platform\":1,\"radio_num\":0}]},\"ad_type4\":{\"type\":0,\"platform\":0,\"platform_list\":[{\"platform\":0,\"radio_num\":3},{\"platform\":1,\"radio_num\":2}]}},\"time\":1586227960,\"vsn\":\"1.8.8.2\"}";

        ADConfigEntity adConfigEntity = JSON.parseObject(json, ADConfigEntity.class);

        return adConfigEntity;
    }
}
