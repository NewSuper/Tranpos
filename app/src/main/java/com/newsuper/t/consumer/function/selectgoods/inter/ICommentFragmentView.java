package com.newsuper.t.consumer.function.selectgoods.inter;

import com.newsuper.t.consumer.bean.CommentBean;
import com.newsuper.t.consumer.bean.TopBean;
import com.newsuper.t.consumer.function.top.internal.IBaseView;

/**
 * Created by Administrator on 2017/5/3 0003.
 */

public interface ICommentFragmentView extends IBaseView {

    void showDataToVIew(CommentBean bean);

}
