package com.newsuper.t.juejinbao.view.page;

import android.support.annotation.ColorRes;

import com.newsuper.t.R;


/**
 * 作用：页面的展示风格。
 */

public enum PageStyle {
    BG_0(R.color.c_333333, R.color.theme1),
    BG_1(R.color.c_333333, R.color.theme2),
    BG_2(R.color.c_5A4431, R.color.theme3),
    BG_3(R.color.c_97A4A8, R.color.theme4),
    BG_4(R.color.c_822F3E, R.color.theme5),
    NIGHT(R.color.chapter_content_night, R.color.c_1d1d1f),;

    private int fontColor;
    private int bgColor;

    PageStyle(@ColorRes int fontColor, @ColorRes int bgColor) {
        this.fontColor = fontColor;
        this.bgColor = bgColor;
    }

    public int getFontColor() {
        return fontColor;
    }

    public int getBgColor() {
        return bgColor;
    }
}
