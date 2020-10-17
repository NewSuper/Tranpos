package com.transpos.market.utils;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTextUtil {
    //判断字符串是否为数字
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

//    public static boolean isNumeric2(String str) {
//        Pattern pattern = Pattern.compile("[0-9]*\\.?[0-9]+");
//        Matcher isNum = pattern.matcher(str);
//        if (!isNum.matches()) {
//            return false;
//        }
//        return true;
//    }
//    public static boolean isNumber(String str){
//        String reg = "^[0-9]+(.[0-9]+)?$";
//        return str.matches(reg);
//    }

    /**
     * 格式化数字（实数，非实数都默认转成0.00显示）
     * 供25sp 13sp使用
     *
     * @param text 只能传数字
     * @return
     */
    public static SpannableString formatTextNumString(String text) {
        if (text == null || text.equals("")) {//为空
            return formatTextNumString("0.00");
        }
        if (!Pattern.compile("^[-+]?\\d+(\\.\\d+)?$").matcher(text).matches()) {//不是实数（带字母或者汉字）
            return formatTextNumString("0.00");
        }

        //设置文本大小
        SpannableString spanText = new SpannableString(text);
        if (text.contains(".")) {
            String[] list = text.split("\\.");
            if (list[0] != null) {//整数字体较大
                spanText.setSpan(new AbsoluteSizeSpan(25, true), 0, list[0].length(),
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            if (list[1] != null && !"".equals(list[1])) {//小数字体较小
                spanText.setSpan(new AbsoluteSizeSpan(13, true), list[0].length(), text.length(),
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            } else {//如：12. ，就补零回调
                return formatTextNumString(text + "00");
            }

        } else {//整数
            text = text + ".00";
            return formatTextNumString(text);
        }

        //设置文本颜色
//        spanText.setSpan(new ForegroundColorSpan(Color.parseColor("#03A9F4")), 0, spanText.length(),
//                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        //字体（正常）
        StyleSpan span = new StyleSpan(Typeface.NORMAL);
        spanText.setSpan(span, 0, spanText.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        return spanText;
    }

    //供13sp  10sp使用
    public static SpannableString formatTextNumString2(String text) {
        if (text == null || text.equals("")) {//为空
            return formatTextNumString2("0.00");
        }
        if (!Pattern.compile("^[-+]?\\d+(\\.\\d+)?$").matcher(text).matches()) {//不是实数（带字母或者汉字）
            return formatTextNumString2("0.00");
        }
        //设置文本大小
        SpannableString spanText = new SpannableString(text);
        if (text.contains(".")) {
            String[] list = text.split("\\.");
            if (list[0] != null) {//整数字体较大
                spanText.setSpan(new AbsoluteSizeSpan(13, true), 0, list[0].length(),
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            if (list[1] != null && !"".equals(list[1])) {//小数字体较小
                spanText.setSpan(new AbsoluteSizeSpan(10, true), list[0].length(), text.length(),
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            } else {//如：12. ，就补零回调
                return formatTextNumString2(text + "00");
            }

        } else {//整数
            text = text + ".00";
            return formatTextNumString2(text);
        }
        //字体（正常）
        StyleSpan span = new StyleSpan(Typeface.NORMAL);
        spanText.setSpan(span, 0, spanText.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return spanText;
    }

}
