package com.newsuper.t.juejinbao.ui.ad;


import com.newsuper.t.juejinbao.bean.BaseEntity;

public interface OnDataBackLintener {
    void onSuccess(BaseEntity loginEntity);
    void onEorr(String str);
}
