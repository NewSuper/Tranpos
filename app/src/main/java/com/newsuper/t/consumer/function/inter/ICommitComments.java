package com.newsuper.t.consumer.function.inter;


import com.newsuper.t.consumer.bean.ShopCommentInfo;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

public interface ICommitComments extends IBaseView {

    void completeComments();
    void getCommentInfo(ShopCommentInfo info);
    void getFail();
}
