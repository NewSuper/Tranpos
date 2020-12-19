
package com.newsuper.t.juejinbao.basepop;

import android.view.View;

public interface BasePopup {

    /**
     * <p>
     * 返回一个contentView以作为PopupWindow的contentView
     * </p>
     * <br>
     * <strong>强烈建议使用{@link BasePopupWindow#createPopupById(int)}，该方法支持读取View的xml布局参数，否则可能会出现与布局不一样的展示从而必须手动传入宽高等参数</strong>
     */
    View onCreateContentView();
}
