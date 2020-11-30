package com.newsuper.t.consumer.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/8 0008.
 */

public class VipCommitInfoBean extends BaseBean{
    public VipCommitData data;
    public class VipCommitData implements Serializable{
       public String member_id;
    }

}
