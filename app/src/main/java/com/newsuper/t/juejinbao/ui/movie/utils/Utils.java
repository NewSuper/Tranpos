package com.newsuper.t.juejinbao.ui.movie.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.TrafficStats;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.newsuper.t.R;
import com.newsuper.t.juejinbao.base.ApiService;
import com.newsuper.t.juejinbao.base.Constant;
import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.base.RetrofitManager;
import com.newsuper.t.juejinbao.bean.LoginEntity;
import com.newsuper.t.juejinbao.bean.SettingLoginEvent;
import com.newsuper.t.juejinbao.ui.movie.entity.LeaveEntity;
import com.newsuper.t.juejinbao.ui.my.entity.LookOrOnTachEntity;
import com.newsuper.t.juejinbao.utils.ClickUtil;
import com.newsuper.t.juejinbao.utils.SubscriberOnResponseListenter;
import com.newsuper.t.juejinbao.utils.network.HttpRequestBody;
import com.newsuper.t.juejinbao.utils.network.HttpResultFunc;
import com.newsuper.t.juejinbao.utils.network.ProgressSubscriber;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.paperdb.Paper;
import rx.Subscriber;
import rx.Subscription;

public class Utils {

    //String转long
    public static long strToLong(String str) {
        long numl = 0;
        try {
            numl = Long.parseLong(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return numl;
    }

    //String转int
    public static int strToInt(String str) {
        int num = 0;
        try {
            num = Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getScreenWidth(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density = dm.density;
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        return width;
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static int getScreenHeight(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density = dm.density;
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        return height;
    }

    //dp转像素
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static float getTextViewLength(TextView textView) {
        TextPaint paint = textView.getPaint();
        return paint.measureText(textView.getText().toString());
    }


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    //字体高亮
    public static SpannableStringBuilder getSearchTitle(Context context, String title, String key) {
        try {
            //搜索关键字颜色
            int first = title.indexOf(key);

            int keyColor = context.getResources().getColor(R.color.text_yellow);

            SpannableStringBuilder style = new SpannableStringBuilder(title);
            style.setSpan(new ForegroundColorSpan(keyColor), first, first + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            return style;

//            return mSpanUtils.append(first).append(mSearchKey).setForegroundColor(keyColor).append(end).create();
        } catch (Exception e) {
            return new SpannableStringBuilder(title);
        }

    }

    //字体高亮
    public static SpannableStringBuilder getSearchTitle2(Context context, String title, String key) {
        try {
            key = key.trim();
            //搜索关键字颜色
            int first = title.indexOf(key);

            int keyColor = context.getResources().getColor(R.color.text_orangered);

            SpannableStringBuilder style = new SpannableStringBuilder(title);
            style.setSpan(new ForegroundColorSpan(keyColor), first, first + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            return style;

//            return mSpanUtils.append(first).append(mSearchKey).setForegroundColor(keyColor).append(end).create();
        } catch (Exception e) {
            return new SpannableStringBuilder(title);
        }

    }


    /**
     * 安装apk
     *
     * @param context 上下文
     * @param apkPath apk下载完成在手机中的路径
     */
    public static void installAPK(Context context, String apkPath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //版本在7.0以上是不能直接通过uri访问的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File file = (new File(apkPath));
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //参数1:上下文, 参数2:Provider主机地址 和配置文件中保持一致,参数3:共享的文件
            Uri apkUri = FileProvider.getUriForFile(context, "com.juejinchain.android.FileProvider", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(apkPath)),
                    "application/vnd.android.package-archive");
        }
        if (ClickUtil.isNotFastClick()) {
            context.startActivity(intent);
        }
    }


    //保留2位小数
    public static String float1(float number) {


        DecimalFormat df = new DecimalFormat();
        String style = "0.0";

        df.applyPattern(style);
        return df.format(number);
    }

    //以万为单位显示
    public static String FormatW(int number) {

        if (number < 10000) {
            return number + "";
        }

        float f = number / 10000f;

        DecimalFormat df = new DecimalFormat();
        String style = "#.##";

        df.applyPattern(style);
        return df.format(f) + "w";
    }


    //业务需求
    public static String FormatGold(double number) {

        DecimalFormat df = new DecimalFormat();
        String style = "#.##";

        df.applyPattern(style);
        return df.format(number);
    }

    //以过去几小时显示
    public static String experienceTime(long timeStamp) {

        long curTime = System.currentTimeMillis() / (long) 1000;
        long time = curTime - timeStamp;

        if (time < 60) {
            return "刚刚";
        } else if (time >= 60 && time < 3600) {
            return time / 60 + "分钟前";
        } else if (time >= 3600 && time < 3600 * 24) {
            return time / 3600 + "小时前";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
            return sdf.format(timeStamp * 1000L);
        }
    }

    /**
     * 保存图片到相册
     */
    public static void saveBmp2Gallery(Context mContext, Bitmap bmp, String picName) {

        String fileName = null;
        //系统相册目录
        String galleryPath = Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Camera" + File.separator;


        // 声明文件对象
        File file = null;
        // 声明输出流
        FileOutputStream outStream = null;

        try {
            // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
            file = new File(galleryPath, picName + ".jpg");

            // 获得文件相对路径
            fileName = file.toString();
            // 获得输出流，如果文件中有内容，追加内容
            outStream = new FileOutputStream(fileName);
            if (null != outStream) {
                bmp.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
            }

        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        MediaStore.Images.Media.insertImage(mContext.getContentResolver(), bmp, fileName, null);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        mContext.sendBroadcast(intent);


    }

    /**
     * base64转为bitmap
     *
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    //bitmap转base64
    public static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    //退出登录
    public static void logout(Context context) {
        rx.Observable<LoginEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).
                loginOut(HttpRequestBody.generateRequestQuery(new HashMap<String, String>())).map((new HttpResultFunc<LoginEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<LoginEntity>() {
            @Override
            public void next(LoginEntity loginEntity) {
//                MyToast.showToast("退出成功");
                Constant.IS_SHOW = 1;
                EventBus.getDefault().post(new SettingLoginEvent());
                Paper.book().delete(PagerCons.USER_DATA);
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
                Log.i("zzz", "error: " + errResponse);
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
    }

    //点击广告
    public static void scanOrClickWebAD(Context context, Map<String, String> StringMap) {
        rx.Observable<LookOrOnTachEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).
                getLookOrOnTach(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<LookOrOnTachEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<LookOrOnTachEntity>() {
            @Override
            public void next(LookOrOnTachEntity lookOrOnTachEntity) {
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
    }

    //离开web接口
    public static void leaveWeb(Context context, Map<String, String> StringMap) {
        rx.Observable<LeaveEntity> observable = RetrofitManager.getInstance(context).create(ApiService.class).
                leaveWeb(HttpRequestBody.generateRequestQuery(StringMap)).map((new HttpResultFunc<LeaveEntity>()));
        Subscription rxSubscription = new ProgressSubscriber<>(new SubscriberOnResponseListenter<LeaveEntity>() {
            @Override
            public void next(LeaveEntity lookOrOnTachEntity) {
            }

            @Override
            public void error(String target, Throwable e, String errResponse) {
            }
        }, context, false);
        RetrofitManager.getInstance(context).toSubscribe(observable, (Subscriber) rxSubscription);
    }


    //判断是否安装QQ
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
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

    //判断是否安装WX
    public static boolean isWXClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
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

    //判断是否安装APK
    public static boolean isAPKInstalled(Context context, String pkgName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(pkgName)) {
                    return true;
                }
            }
        }
        return false;
    }


    //drawable 转 bitmap
    public static final Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    //下载文本
    public static synchronized String downloadStr(String urlStr) {
        String result = "";
//        StringBuffer sb = new StringBuffer();
        String line = null;
        BufferedReader buffer = null;
        URL url = null;
        try {
            // 创建一个URL对象
            url = new URL(urlStr);
            // 创建一个Http连接
            HttpURLConnection urlConn = (HttpURLConnection) url
                    .openConnection();
//            // 使用IO流读取数据
//            buffer = new BufferedReader(new InputStreamReader(
//                    urlConn.getInputStream()));
//            while ((line = buffer.readLine()) != null) {
//                sb.append(line);
//            }
            byte[] buff = new byte[2048];
            ByteArrayOutputStream fromFile = new ByteArrayOutputStream();
            do {
                int numRead = urlConn.getInputStream().read(buff);
                if (numRead <= 0) {
                    break;
                }
                fromFile.write(buff, 0, numRead);
            } while (true);
            result = fromFile.toString();
            urlConn.getInputStream().close();
            fromFile.close();


        } catch (Exception e) {
            System.out.println("download:" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
            } catch (Exception e) {
                System.out.println("download buffer.close():" + e.getMessage());
                e.printStackTrace();
            }
        }
        return result;
    }

    //秒转时分秒
    public static String secondsToSFM(long seconds) {
        int hour = (int) (seconds / 3600);
        int min = (int) (seconds % (60 * 60) / 60);
        int second = (int) (seconds % 60);

        if (min != 0) {
            return (hour == 0 ? "" : hour + ":") + (min < 10 ? ("0" + min + ":") : (min + ":")) + (second < 10 ? ("0" + second) : (second + ""));
        } else {
            return (hour == 0 ? "00:" : hour + ":00:") + (second < 10 ? ("0" + second) : (second + ""));
        }

    }

    //过滤掉所有特殊字符
    public static String stringFilter(String str) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll(" ").trim();
    }


    //获取版本名
    public static String getAppVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }

    //获取APP versioncpde
    public static int getLocalVersion(Context ctx) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
            Log.d("TAG", "当前版本号：" + localVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    private static long rxtxTotal = 0;

    public synchronized static double getKByte() {

        long tempSum = TrafficStats.getTotalRxBytes()

                + TrafficStats.getTotalTxBytes();

        long rxtxLast = tempSum - rxtxTotal;

        double totalSpeed = rxtxLast * 1000 / 2000d;

        rxtxTotal = tempSum;


        return totalSpeed / 1024d;

    }

    //是否存在刘海屏
    public static boolean haveLHP(Activity activity) {
        try {
            return LHPUtils.hasNotchInScreen(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //判断是否存在SD卡
    private static boolean existSDCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }

    //保存字符串到文件
    public static boolean saveTxtFile(String fileName, String str) {
        try {
            if (!Utils.existSDCard()) {
                return false;
            } else {
                //TODO 创建文件夹
                String strFilePath = Constant.BASE_APP_PATH;
                File file = new File(strFilePath);
                if (!file.exists()) {
                    file.mkdirs();
                }
            }

            FileWriter fw = new FileWriter(Constant.BASE_APP_PATH + "/" + fileName);//SD卡中的路径
            fw.flush();
            fw.write(str);
            fw.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //地址转图片(子线程)
    public static Bitmap urlToBitMap(String url) {
        Bitmap bitmap = null;
        URL imageurl = null;
        InputStream is = null;
        try {
            imageurl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) imageurl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
            is = null;
        } catch (IOException e) {
            e.printStackTrace();
            if(is != null){
                try {
                    is.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                is = null;
            }
        }

        return bitmap;
    }

    //日期转时间戳
    public static long dateToTimestamp(String yyyy_MM_dd){
        try {
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;

            date = format.parse(yyyy_MM_dd);


        return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //是不是今天
    public static boolean sameDay(long day1, long day2) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(day1);
        int d1 = instance.get(Calendar.DAY_OF_YEAR);
        instance.setTimeInMillis(day2);
        int d2 = instance.get(Calendar.DAY_OF_YEAR);
        return d1 == d2;
    }

    //提取String中的数字并转为long
    public static long allStringToLong(String str){
        try {
            str = str.trim();
            String str2 = "";
            if (str != null && !"".equals(str)) {
                for (int i = 0; i < str.length(); i++) {
                    if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                        str2 += str.charAt(i);
                    }
                }

            }

            return Long.parseLong(str2);

        }catch (Exception e){
            e.printStackTrace();
        }
        return 0L;
    }

    //提取String中的数字并转为long
    public static String getyyyyMMddToday(){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(System.currentTimeMillis());


        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

//    @SuppressLint("NewApi")
//    public static boolean checkDeviceHasNavigationBar(Context activity) {
//
//        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
//        boolean hasMenuKey = ViewConfiguration.get(activity)
//                .hasPermanentMenuKey();
//        boolean hasBackKey = KeyCharacterMap
//                .deviceHasKey(KeyEvent.KEYCODE_BACK);
//
//        if (!hasMenuKey && !hasBackKey) {
//            // 做任何你需要做的,这个设备有一个导航栏
//            return true;
//        }
//        return false;
//    }




//    public static int getNavigationBarHeight(Context context) {
//        int vh = 0;
//        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        Display display = windowManager.getDefaultDisplay();
//        DisplayMetrics dm = new DisplayMetrics();
//        try {
//            @SuppressWarnings("rawtypes")
//            Class c = Class.forName("android.view.Display");
//            @SuppressWarnings("unchecked")
//            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
//            method.invoke(display, dm);
//            vh = dm.heightPixels - display.getHeight() - getStateBarHeight(context);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return vh;
//    }
//
//    /**
//     * 获取状态栏&刘海高度
//     */
//    private static int getStateBarHeight(Context context) {
//
//        //安卓9.0自带方法
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//
//        }
//        //厂商方法
//        else {
//            switch (NotchScreenUtil.getDeviceBrand()) {
//                //华为
//                case NotchScreenUtil.DEVICE_BRAND_HUAWEI:
//                    //存在刘海屏
//                    if (NotchScreenUtil.hasNotchInScreenAtHuawei(context)) {
//                        return NotchScreenUtil.getNotchSizeAtHuawei(context);
//                    }
//                    break;
//                //OPPO
//                case NotchScreenUtil.DEVICE_BRAND_OPPO:
//                    //存在刘海屏
//                    if (NotchScreenUtil.hasNotchInScreenAtOppo(context)) {
//                        return NotchScreenUtil.getNotchSizeAtOppo(context);
//                    }
//                    break;
//                //VIVO
//                case NotchScreenUtil.DEVICE_BRAND_VIVO:
//                    //存在刘海屏
//                    if (NotchScreenUtil.hasNotchInScreenAtVivo(context)) {
//                        return NotchScreenUtil.getNotchSizeAtVivo(context);
//                    }
//                    break;
//            }
//        }
//
//        //普通状态栏高度
////        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
////        if (resourceId > 0) {
////            return context.getResources().getDimensionPixelSize(resourceId);
////        }
//        return 0;
//    }
//
//


    public static int getNavigationBarHeightIfRoom(Context context) {
        if(navigationGestureEnabled(context)){
            return 0;
        }
        return getCurrentNavigationBarHeight(((Activity) context));
    }

    /**
     * 全面屏（是否开启全面屏开关 0 关闭  1 开启）
     *
     * @param context
     * @return
     */
    public static boolean navigationGestureEnabled(Context context) {
        int val = Settings.Global.getInt(context.getContentResolver(), getDeviceInfo(), 0);
        return val != 0;
    }

    /**
     * 获取设备信息（目前支持几大主流的全面屏手机，亲测华为、小米、oppo、魅族、vivo都可以）
     *
     * @return
     */
    public static String getDeviceInfo() {
        String brand = Build.BRAND;
        if(TextUtils.isEmpty(brand)) return "navigationbar_is_min";

        if (brand.equalsIgnoreCase("HUAWEI")) {
            return "navigationbar_is_min";
        } else if (brand.equalsIgnoreCase("XIAOMI")) {
            return "force_fsg_nav_bar";
        } else if (brand.equalsIgnoreCase("VIVO")) {
            return "navigation_gesture_on";
        } else if (brand.equalsIgnoreCase("OPPO")) {
            return "navigation_gesture_on";
        } else {
            return "navigationbar_is_min";
        }
    }

    /**
     * 非全面屏下 虚拟键实际高度(隐藏后高度为0)
     * @param activity
     * @return
     */
    public static int getCurrentNavigationBarHeight(Activity activity){
        if(isNavigationBarShown(activity)){
            return getNavigationBarHeight(activity);
        } else{
            return 0;
        }
    }

    /**
     * 非全面屏下 虚拟按键是否打开
     * @param activity
     * @return
     */
    public static boolean isNavigationBarShown(Activity activity){
        //虚拟键的view,为空或者不可见时是隐藏状态
        View view  = activity.findViewById(android.R.id.navigationBarBackground);
        if(view == null){
            return false;
        }
        int visible = view.getVisibility();
        if(visible == View.GONE || visible == View.INVISIBLE){
            return false ;
        }else{
            return true;
        }
    }

    /**
     * 非全面屏下 虚拟键高度(无论是否隐藏)
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context){
        int result = 0;
        int resourceId = context.getResources().getIdentifier("navigation_bar_height","dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
