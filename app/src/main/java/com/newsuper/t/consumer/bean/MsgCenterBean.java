package com.newsuper.t.consumer.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/7/23 0023.
 */

public class MsgCenterBean extends BaseBean {

    public ArrayList<MsgCenterData> data;
    public static class MsgCenterData{
        /**
         * info_id : 1
         * push_title : 好好的
         * push_content : 更好好傲娇<img src="http://lenew1.com/public/fck/editor/plugins/emoticons/images/0.gif" alt="" border="0" /><img src="http://img.lewaimai.com/upload_files/image/20180627/ykxXBDJ8YCC3N8OgPBOE4dPX5UBvP0Yp.png" alt="" />
         * date : 07-06
         * is_read : 1
         */

        public String info_id;
        public String push_title;
        public String push_content;
        public String date;
        public String is_read;
    }

}
