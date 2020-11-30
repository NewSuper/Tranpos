package com.newsuper.t.consumer.bean;

import java.util.List;

/**
 * Create by Administrator on 2019/5/5 0005
 */
public class DredgeAreaBean {

    /**
     * error_msg :
     * error_code : 0
     * data : [{"id":"15","area_name":"测试分区1324"}]
     */

    public String error_msg;
    public int error_code;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 15
         * area_name : 测试分区1324
         */

        public String id;
        public String area_name;
    }
}
