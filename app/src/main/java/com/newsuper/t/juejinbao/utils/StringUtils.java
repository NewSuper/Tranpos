package com.newsuper.t.juejinbao.utils;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.LeadingMarginSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.newsuper.t.juejinbao.bean.WeekDay;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    /**
     * 比较两个日期的大小，日期格式为yyyy-MM-dd
     *
     * @param str1 the first date
     * @param str2 the second date
     * @return true <br/>false
     */
    public static boolean isDate2Bigger(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() > dt2.getTime()) {
            isBigger = false;
        } else if (dt1.getTime() <= dt2.getTime()) {
            isBigger = true;
        }
        return isBigger;
    }

    public static String getRent(double rent, int type) {
        if (rent >= 10000) {
            return rent / 10000 + (type == 1 ? "万/月" : "万/m²");
        } else {
            return rent + (type == 1 ? "元/月" : "元/m²");
        }
    }

    public static void calculateTag1(final TextView first, final TextView second, final String text) {
        ViewTreeObserver observer = first.getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                Layout layout = first.getLayout();
                int lineEnd = layout.getLineEnd(0);
                String substring = text.substring(0, lineEnd);
                String substring1 = text.substring(lineEnd, text.length());
                Log.i("TAG", "onGlobalLayout:" + "+end:" + lineEnd);
                Log.i("TAG", "onGlobalLayout: 第一行的内容：：" + substring);
                Log.i("TAG", "onGlobalLayout: 第二行的内容：：" + substring1);
                if (TextUtils.isEmpty(substring1)) {
                    second.setVisibility(View.GONE);
                    second.setText(null);
                } else {
                    second.setVisibility(View.VISIBLE);
                    second.setText(substring1);
                }
                first.getViewTreeObserver().removeOnPreDrawListener(
                        this);
                return false;
            }
        });

    }

    /**
     * 将文本中的半角字符，转换成全角字符
     * @param input
     * @return
     */
    public static String halfToFull(String input)
    {
        char[] c = input.toCharArray();
        for (int i = 0; i< c.length; i++)
        {
            if (c[i] == 32) //半角空格
            {
                c[i] = (char) 12288;
                continue;
            }
            //根据实际情况，过滤不需要转换的符号
            //if (c[i] == 46) //半角点号，不转换
            // continue;

            if (c[i]> 32 && c[i]< 127)    //其他符号都转换为全角
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }

    /**
     * 检测是否安装微信
     *
     * @param context
     * @return
     */
    public static boolean isWxInstall(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 检测是否安装QQ
     *
     * @param context
     * @return
     */
    public static boolean isQQ(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 检测是否安装微博
     *
     * @param context
     * @return
     */
    public static boolean isweibo(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.sina.weibo")) {
                    return true;
                }
            }
        }

        return false;
    }

    public static List<WeekDay> getWeekDay() {
        Calendar calendar = Calendar.getInstance();
        // 获取本周的第一天
        int firstDayOfWeek = calendar.getFirstDayOfWeek();
        List<WeekDay> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            calendar.set(Calendar.DAY_OF_WEEK, firstDayOfWeek + i);
            WeekDay weekDay = new WeekDay();
            // 获取星期的显示名称，例如：周一、星期一、Monday等等
            weekDay.week = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.ENGLISH);
            weekDay.day = new SimpleDateFormat("MM-dd").format(calendar.getTime());

            list.add(weekDay);
        }

        return list;
    }

    public static int compare_date(String DATE1, String DATE2) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }


    public static String getNowTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    //方案二：动态设置缩进距离的方式
    public static void calculateTag2(final TextView tag, final TextView title, final String text) {
        ViewTreeObserver observer = tag.getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                SpannableString spannableString = new SpannableString(text);
                //这里没有获取margin的值，而是直接写死的
                LeadingMarginSpan.Standard what = new LeadingMarginSpan.Standard(tag.getWidth() + dip2px(tag.getContext(), 15), 0);
                spannableString.setSpan(what, 0, spannableString.length(), SpannableString.SPAN_INCLUSIVE_INCLUSIVE);
                title.setText(spannableString);
                tag.getViewTreeObserver().removeOnPreDrawListener(
                        this);
                return false;
            }
        });

    }

    public static void calculateTag(final RelativeLayout tag, final TextView title, final String text) {
        ViewTreeObserver observer = tag.getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                SpannableString spannableString = new SpannableString(text);
                //这里没有获取margin的值，而是直接写死的
                LeadingMarginSpan.Standard what = new LeadingMarginSpan.Standard(tag.getWidth() + dip2px(tag.getContext(), 5), 0);
                spannableString.setSpan(what, 0, spannableString.length(), SpannableString.SPAN_INCLUSIVE_INCLUSIVE);
                title.setText(spannableString);
                tag.getViewTreeObserver().removeOnPreDrawListener(
                        this);
                return false;
            }
        });

    }


    public static double getRent(double rent) {
        if (rent >= 10000) {
            return rent / 10000;
        } else {
            return rent;
        }

    }

    public static int animateDip2px(Context context, float dipValue) {
        float sDensity = context.getResources().getDisplayMetrics().density;
        final float scale = sDensity;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int getStatusBarHeight(Context mContext) {

        int height = 0;
        int resId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resId > 0) {
            height = mContext.getResources().getDimensionPixelSize(resId);
        }
        return height;

    }

    /**
     * 获取手机IMEI号
     * <p>
     * 需要动态权限: android.permission.READ_PHONE_STATE
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        String imei = telephonyManager.getDeviceId();

        return imei;
    }


    /**
     * 判断相对应的APP是否存在
     *
     * @param context
     * @param packageName(包名)(若想判断QQ，则改为com.tencent.mobileqq，若想判断微信，则改为com.tencent.mm)
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();

        //获取手机系统的所有APP包名，然后进行一一比较
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (((PackageInfo) pinfo.get(i)).packageName
                    .equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }

    /**
     * dp转为px
     *
     * @param context  上下文
     * @param dipValue dp值
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dipValue, r.getDisplayMetrics());
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized String getVersionPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 获取当前本地apk的版本
     *
     * @param mContext
     * @return
     */
    private static String VERSION;
    public static String getVersionCode(Context mContext) {

        if (VERSION != null) return VERSION;
        Context ctx =  mContext;
        try {
            VERSION = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return VERSION;
    }


    /**
     * 验证用户名，支持中英文（包括全角字符）、数字、下划线和减号 （全角及汉字算两位）,长度为4-20位,中文按二位计数
     *
     * @param userName
     * @return
     * @author www.zuidaima.com
     */
    public static boolean validateUserName(String userName) {
        String validateStr = "^[\\w\\-－＿[０-９]\u4e00-\u9fa5\uFF21-\uFF3A\uFF41-\uFF5A]+$";
        boolean rs = false;
        rs = matcher(validateStr, userName);
        if (rs) {
            int strLenth = getStrLength(userName);
            if (strLenth < 4 || strLenth > 16) {
                rs = false;
            }
        }
        return rs;
    }

    public static String getVersionName(Activity activity) throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = activity.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(activity.getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
    }


    /**
     * 获取字符串的长度，对双字符（包括汉字）按两位计数
     *
     * @param value
     * @return
     */
    private static int getStrLength(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        return valueLength;
    }

    private static boolean matcher(String reg, String string) {
        boolean tem = false;
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(string);
        tem = matcher.matches();
        return tem;
    }

    /**
     * 获取版本号名称
     *
     * @param context 上下文
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    public static boolean isDate1Bigger(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() > dt2.getTime()) {
            isBigger = false;
        } else if (dt1.getTime() <= dt2.getTime()) {
            isBigger = true;
        }
        return isBigger;
    }

    /**
     * 判断2个时间大小
     * yyyy-MM-dd HH:mm 格式（自己可以修改成想要的时间格式）
     *
     * @return
     */
    public static int getTimeCompareSize(Date date1, Date date2) {
        int i = 0;

        // 1 结束时间小于开始时间 2 开始时间与结束时间相同 3 结束时间大于开始时间
        if (date2.getTime() < date1.getTime()) {
            i = 1;
        } else if (date2.getTime() == date1.getTime()) {
            i = 2;
        } else if (date2.getTime() > date1.getTime()) {
            //正常情况下的逻辑操作.
            i = 3;
        }
        return i;
    }

    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    public static void callPhone(Activity activity, String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for ic_more3 details.
            return;
        }
        activity.startActivity(intent);
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public static void diallPhone(String phoneNum, Activity activity) {
        Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for ic_more3 details.
            return;
        }
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent1);
//        Uri data = Uri.parse("tel:" + phoneNum);
//        intent.setData(data);
//        activity.startActivity(intent);
    }

    public static String getTimeLongYY(long time) {
        SimpleDateFormat format2 = new SimpleDateFormat("MM-dd HH:mm");
        return format2.format(new Date(time));
    }


    public static String getTimeLong(long time) {
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        return format2.format(new Date(time));
    }



    public static int getWeek() {
        // 再转换为时间
        Calendar c = Calendar.getInstance();
        int week = 0;
        int cweek = c.get(Calendar.DAY_OF_WEEK);
        c.setTime(new Date());
        switch (cweek) {
            case 2:
                c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                week = 1;
                break;
            case 3:
                c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                week = 2;
                break;
            case 4:
                c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                week = 3;
                break;
            case 5:
                c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                week = 4;
                break;
            case 6:
                c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                week = 5;
                break;
            case 7:
                c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                week = 6;
                break;
            case 1:
                c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                week = 7;
                break;

            default:
                break;
        }
        return week;
    }

    /**
     * 规则1：至少包含大小写字母及数字中的一种
     * 是否包含
     *
     * @param str
     * @return
     */
    public static boolean isLetterOrDigit(String str) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            } else if (Character.isLetter(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isDigit && isLetter && str.matches(regex);
        return isRight;
    }


    /**
     * 验证手机格式是否正确
     */
    public static boolean checkPhone(String phone) {
//        Pattern pattern = Pattern.compile("(130|131|132|145|155|156|185|186|134|135|136|137|138|139|147|150|151|152|157|158|159|182|183|187|188|133|153|189|180)\\d{8}");
        Pattern pattern = Pattern.compile("(13|14|15|17|18|16|19)\\d{9}");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public static boolean isEmail(String strEmail) {
        String strPattern = "^[a-zA-Z0-9][\\w.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z.]*[a-zA-Z]$";
        if (TextUtils.isEmpty(strPattern)) {
            return false;
        } else {
            return strEmail.matches(strPattern);
        }
    }
    /**
     * 0 = "01-13+Sun"
     * 1 = "01-14+Mon"
     * 2 = "01-15+Tue"
     * 3 = "01-16+Wed"
     * 4 = "01-17+Thu"
     * 5 = "01-18+Fri"
     * 6 = "01-19+Sat"
     */


    /**
     * 获得星期几转化成int数字
     *
     * @param week 字符串格式的星期几
     */
    public static String weekToNum(String week) {
        // 再转换为时间
        switch (week) {
            case "Mon":
                return "周一";
            case "Tue":
                return "周二";
            case "Wed":
                return "周三";
            case "Thu":
                return "周四";
            case "Fri":
                return "周五";
            case "Sat":
                return "周六";
            case "Sun":
                return "周天";
            default:
                break;
        }
        return "";
    }

    /**
     * 获得星期几转化成int数字
     *
     * @param week 字符串格式的星期几
     */
    public static String numToWeek(String week) {
        if (!TextUtils.isEmpty(week)){
            try {
                return numToWeek(Integer.valueOf(week));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 获得星期几转化成int数字
     *
     * @param week 字符串格式的星期几
     */
    public static String numToWeek(int week) {
        // 再转换为时间
        switch (week) {
            case 0:
                return "周天";
            case 1:
                return "周一";
            case 2:
                return "周二";
            case 3:
                return "周三";
            case 4:
                return "周四";
            case 5:
                return "周五";
            case 6:
                return "周六";
            case 7:
                return "周天";
            default:
                break;
        }
        return "";
    }

    public static String getDataMM(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        return format.format(date);
    }


    public static String getDataMMDD(String date) {
        String dateStr = "";
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        try {
            Date date1 = format.parse(date);
//            dateStr = format.format(date1);
            // 获取日期实例
            Calendar calendar = Calendar.getInstance();
// 将日历设置为指定的时间
            calendar.setTime(date1);
// 获取年
            int year = calendar.get(Calendar.YEAR);
// 这里要注意，月份是从0开始。
            int month = calendar.get(Calendar.MONTH);
// 获取天
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            return month + "-" + day;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateStr;
    }

    public static String getData(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static String getDataHM(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    public static String getDataDM(Date date, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(date);
    }

    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public static Date stringToMM(String strTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public static Date stringToDateHH(String strTime) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public static String stringToDateDD(String strTime, String formatStr) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getDataDM(date, formatStr);
    }


    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("slidingTabIndicator");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    public static String stringFormatter(String str, String formatter) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getDataDM(date, formatter);
    }

    public static Date stringToDateMD(String str) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }


    public static Date stringToDateYY(String strTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    public static String getTimeShort() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date currentTime = new Date();
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String getDataHH(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }

    public static String getTimeLongMM(long time) {
        SimpleDateFormat format2 = new SimpleDateFormat("MM-dd");
        return format2.format(new Date(time));
    }

    public static String getTimeLongHH(long time) {
        SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");
        return format2.format(new Date(time));
    }

    public static String getDateToString(long milSecond) {
        return getDateToString(milSecond, "MM/dd   HH:mm");
    }

    public static String getDateToString(long milSecond, String formatStr) {
        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(date);
    }
    public static String chargeSecondsToNowTime(String seconds) {
        long time = Long.parseLong(seconds) * 1000;
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return format2.format(new Date(time));
    }

    public static String getDateTo(long milSecond) {
        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String getWeek(String time) {
        String Week = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int wek = c.get(Calendar.DAY_OF_WEEK);

        if (wek == 1) {
            Week += "周日";
        }
        if (wek == 2) {
            Week += "周一";
        }
        if (wek == 3) {
            Week += "周二";
        }
        if (wek == 4) {
            Week += "周三";
        }
        if (wek == 5) {
            Week += "周四";
        }
        if (wek == 6) {
            Week += "周五";
        }
        if (wek == 7) {
            Week += "周六";
        }
        return Week;
    }

    private static StringBuilder mFormatBuilder;
    private static Formatter mFormatter;

    public static String stringForTime(int timeMs) {
        //转换成字符串的时间
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        int seconds = timeMs % 60;
        int minutes = (timeMs / 60) % 60;
        int hours = timeMs / 3600;
        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    public static String getDateToYY(long milSecond) {
        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }


    public static String getDateToHour(long milSecond) {
        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat("HH");
        return format.format(date);
    }


    public static String getDate(long milSecond, String forMat) {
        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat(forMat);
        return format.format(date);
    }

    public static String getPercent(int x, int total) {
        String result;//接受百分比的值
        double tempresult = (float) x / (float) total;
        DecimalFormat df1 = new DecimalFormat("0.00%");    //##.00%   百分比格式，后面不足2位的用0补齐
        result = df1.format(tempresult);
        return result;
    }

    public static float getTotal(int x, int total) {
        return (float) x / (float) total;
    }

    public static String getDateToHH(long milSecond) {
        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 秒数转化为日期
     */
    public static String getDateFromSeconds(long seconds) {
        if (seconds == 0)
            return " ";
        else {
            Date date = new Date();
            try {
                date.setTime(seconds * 1000);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
            return sdf.format(date);
        }
    }

    /**
     * 秒数转化为日期
     */
    public static String getDateFrom(long seconds) {
        if (seconds == 0)
            return " ";
        else {
            Date date = new Date();
            try {
                date.setTime(seconds * 1000);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
        }
    }

    /**
     * 秒数转化为日期
     */
    public static String getDateFromTest(long seconds) {
        if (seconds == 0)
            return " ";
        else {
            Date date = new Date();
            try {
                date.setTime(seconds * 1000);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
            return sdf.format(date);
        }
    }

    /**
     * 秒数转化为日期
     */
    public static String getDateFrom2(long seconds) {
        if (seconds == 0)
            return " ";
        else {
            Date date = new Date();
            try {
                date.setTime(seconds * 1000);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            return sdf.format(date);
        }
    }

    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime)
            throws ParseException {
        Date date = stringToDate(strTime, "HH:mm"); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    private static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    /**
     * 将毫秒转化为 分钟：秒 的格式
     *
     * @param millisecond 毫秒
     * @return
     */
    public static String formatTime(long millisecond) {
        int minute;//分钟
        int second;//秒数
        minute = (int) ((millisecond / 1000) / 60);
        second = (int) ((millisecond / 1000) % 60);
        if (minute < 10) {
            if (second < 10) {
                return "0" + minute + ":" + "0" + second;
            } else {
                return "0" + minute + ":" + second;
            }
        } else {
            if (second < 10) {
                return minute + ":" + "0" + second;
            } else {
                return minute + ":" + second;
            }
        }
    }

    /**
     * 将毫秒转化为 小时分钟秒 的格式
     *
     * @param millisecond 毫秒
     * @return
     */
    public static String formatTime2(long millisecond) {
        int hour;//小时
        int minute;//分钟
        int second;//秒数
        hour = (int) ((millisecond / 1000) / 60 / 60);
        minute = (int) ((millisecond / 1000) %(60 * 60) / 60);
        second = (int) ((millisecond / 1000) % 60);

        return hour + "小时" + minute + "分钟" + second + "秒";
//        if (minute < 10) {
//            if (second < 10) {
//                return "0" + minute + ":" + "0" + second;
//            } else {
//                return "0" + minute + ":" + second;
//            }
//        } else {
//            if (second < 10) {
//                return minute + ":" + "0" + second;
//            } else {
//                return minute + ":" + second;
//            }
//        }
    }

    public static String StringToDate(String dateStr) {
        DateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return sdf.format(date);
    }

    /**
     * 改变TextView部分字体的颜色
     *
     * @param originalValue
     * @param indexValue
     * @return
     */

    public static SpannableStringBuilder setTextViewColor(String originalValue, String indexValue, int color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(originalValue);
        if (TextUtils.isEmpty(originalValue)) {
            return builder;
        }
        for (int j = 0; j < originalValue.length(); j++) {
            if (j + indexValue.length() <= originalValue.length()) {
                String name = (String) originalValue.subSequence(j, j + indexValue.length());
                if (name.equals(indexValue)) {
                    ForegroundColorSpan redSpan = new ForegroundColorSpan(color);
                    builder.setSpan(redSpan, j, j + indexValue.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        return builder;
    }

    /**
     * 改变TextView部分字体的颜色和大小
     *
     * @param originalValue
     * @param indexValue
     * @return
     */
    public static SpannableStringBuilder setTextViewColorSize(String originalValue, String indexValue , int color, int size) {
        SpannableStringBuilder builder = new SpannableStringBuilder(originalValue);
        if (TextUtils.isEmpty(originalValue)) {
            return builder;
        }
        for (int j = 0; j < originalValue.length(); j++) {
            if (j + indexValue.length() <= originalValue.length()) {
                String name = (String) originalValue.subSequence(j, j + indexValue.length());
                if (name.equals("亿") ||name.equals("万") ||name.equals("元")) {
                    ForegroundColorSpan redSpan = new ForegroundColorSpan(color);
                    builder.setSpan(new AbsoluteSizeSpan(size), j, j + indexValue.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    builder.setSpan(redSpan, j, j + indexValue.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        return builder;
    }

    /**
     * 加载本地图片
     *
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        Bitmap output = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    public static SpannableString zhuanHuanTelUrl(final Activity context, final String strTel) {
        SpannableString ss = new SpannableString(strTel);
        final List<String> list = getNumbers(strTel);
        if (list.size() > 0) {
            ss.setSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.WHITE);       //设置文件颜色
                    ds.setUnderlineText(true);      //设置下划线
                }

                @Override
                public void onClick(View widget) {
//                    TurnUtil.openActivityCall(context, list.get(0));
                }
            }, strTel.indexOf(list.get(0)), strTel.indexOf(list.get(0)) + 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }

    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年

    public static String getTimeFormatText(long date) {
        long diff = new Date().getTime() - date;
        long r = 0;
        if (diff > year) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(date);
            return dateString;
        }

        if (diff > day && diff < year) {
            r = (diff / day);
            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
            String dateString = formatter.format(date);
            return dateString;
        }
        if (diff > hour && diff < day) {
            r = (diff / hour);
            return r + "个小时前";
        }
        if (diff > minute && diff < hour) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }


    /**
     * 将字符串中的电话号码设置点击事件和下划线
     *
     * @param context
     * @param tv
     * @param strTel
     */
    public static void setTelUrl(Activity context, TextView tv, String strTel) {
        zhuanHuanTelUrl(context, strTel);
        tv.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
        tv.setText(strTel);
        tv.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
    }

    public static Uri getUri(Context context, int res) {
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + context.getResources().getResourcePackageName(res) + "/"
                + context.getResources().getResourceTypeName(res) + "/"
                + context.getResources().getResourceEntryName(res));

        return uri;
    }

    /**
     * 从字符串中查找电话号码字符串
     */
    private static List<String> getNumbers(String content) {
        List<String> digitList = new ArrayList<String>();
        Pattern p = Pattern.compile("((^(13|15|18)[0-9]{9}$)|(^0[1,2]\\d-?\\d{8}$)|(^0[3-9] \\d{2}-?\\d{7,8}$)|(^0[1,2]\\d-?\\d{8}-(\\d{1,4})$)|(^0[3-9]\\d{2}-? \\d{7,8}-(\\d{1,4})$))");
        Matcher m = p.matcher(content);
        while (m.matches()) {
            String find = m.group(1).toString();
            digitList.add(find);
        }
        return digitList;
    }

    public static String NumericScaleByFloor(String numberValue, int scale) {
        return new BigDecimal(numberValue).setScale(scale, BigDecimal.ROUND_FLOOR).toString();
    }

    public static String getFirstString(String str){
        if (!TextUtils.isEmpty(str) && str.length() > 0){
            return str.substring(0, 1);
        }
        return str;
    }

    //毫秒换成00:00:00
    public static String getCountTimeByLong(long finishTime) {
        int totalTime = (int) (finishTime / 1000);//秒
        int hour = 0, minute = 0, second = 0;

        if (3600 <= totalTime) {
            hour = totalTime / 3600;
            totalTime = totalTime - 3600 * hour;
        }
        if (60 <= totalTime) {
            minute = totalTime / 60;
            totalTime = totalTime - 60 * minute;
        }
        if (0 <= totalTime) {
            second = totalTime;
        }
        StringBuilder sb = new StringBuilder();

        if (hour < 10) {
            sb.append("0").append(hour).append(":");
        } else {
            sb.append(hour).append(":");
        }
        if (minute < 10) {
            sb.append("0").append(minute).append(":");
        } else {
            sb.append(minute).append(":");
        }
        if (second < 10) {
            sb.append("0").append(second);
        } else {
            sb.append(second);
        }
        return sb.toString();

    }
    //毫秒换成时分秒
    public static String getCountStringTimeByLong(long finishTime) {
        int totalTime = (int) (finishTime / 1000);//秒
        int hour = 0, minute = 0, second = 0;

        if (3600 <= totalTime) {
            hour = totalTime / 3600;
            totalTime = totalTime - 3600 * hour;
        }
        if (60 <= totalTime) {
            minute = totalTime / 60;
            totalTime = totalTime - 60 * minute;
        }
        if (0 <= totalTime) {
            second = totalTime;
        }
        StringBuilder sb = new StringBuilder();
        if(hour>0){
            if (hour < 10) {
                sb.append("0").append(hour).append("时");
            } else {
                sb.append(hour).append("时");
            }
        }

        if (minute < 10) {
            sb.append("0").append(minute).append("分");
        } else {
            sb.append(minute).append("分");
        }
        if (second < 10) {
            sb.append("0").append(second).append("秒");
        } else {
            sb.append(second).append("秒");
        }
        return sb.toString();

    }

    /***
     * 获取url 指定name的value;
     * @param url
     * @param key
     * @return
     */
    public static String getUrlParamValueByKey(String url, String key) {
        String result = "";
        if(TextUtils.isEmpty(url) || TextUtils.isEmpty(key)){
            return result;
        }
        try {
            int index = url.indexOf("?");
            String temp = url.substring(index + 1);
            String[] keyValue = temp.split("&");
            for (String str : keyValue) {
                if (!TextUtils.isEmpty(str)) {
                    String[] kV = str.split("=");
                    if (kV[0].equals(key)) {
                        result = kV[1];
                        break;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static String filterHTMLTag(String htmlStr) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式
        // 定义一些特殊字符的正则表达式 如：&nbsp;&nbsp;
        String regEx_special = "\\&[a-zA-Z]{1,10};";

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); //过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); //过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); //过滤html标签

        Pattern p_htmlSpec = Pattern.compile(regEx_special, Pattern.CASE_INSENSITIVE);
        Matcher m_htmlSpec = p_htmlSpec.matcher(htmlStr);
        htmlStr = m_htmlSpec.replaceAll(""); //过滤html转义符号
        // 去除字符串中的空格 回车 换行符 制表符 等
//        htmlStr = htmlStr.replaceAll("\\s*|\t|\r|\n", "");

        return htmlStr.trim(); //返回文本字符串
    }
}
