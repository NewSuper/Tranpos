package com.newsuper.t.consumer.function.inter;


import com.newsuper.t.consumer.function.top.internal.IBaseView;

public interface IComment extends IBaseView {
    void comment(String order_id, String shop_id, String order_no);
}
