package com.newsuper.t.juejinbao.inittask;

import com.newsuper.t.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

public class SmartRefreshLayoutTask extends Task {
    @Override
    public void run() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((mContext, layout) -> {
            return new ClassicsHeader(mContext).setPrimaryColorId(R.color.white).setAccentColorId(R.color.status_background);
            //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((mContext, layout) ->
                new ClassicsFooter(mContext).setPrimaryColorId(R.color.white)
                        .setAccentColorId(R.color.status_background));
    }
}

