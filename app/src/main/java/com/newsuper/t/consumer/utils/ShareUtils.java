package com.newsuper.t.consumer.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseApplication;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.tauth.Tencent;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;


public class ShareUtils {
    public static final int WX_SEESSION = 0;//微信聊天
    public static final int WX_TIME_LINE = 1;//微信朋友圈

    //微信分享文本
    public static void WXShareText(IWXAPI api, String text, int toWhere) {
        WXTextObject textObject = new WXTextObject();
        textObject.text = text;

        WXMediaMessage mediaMessage = new WXMediaMessage();
        mediaMessage.mediaObject = textObject;
        mediaMessage.description = text;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = System.currentTimeMillis() + "text";
        req.message = mediaMessage;
        if (toWhere == WX_SEESSION) {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        if (api != null) {
            api.sendReq(req);
        }

    }

    //微信分享图片
    public void WXShareImg(IWXAPI api, String text, int toWhere) {
        WXTextObject textObject = new WXTextObject();
        textObject.text = text;

        WXMediaMessage mediaMessage = new WXMediaMessage();
        mediaMessage.mediaObject = textObject;
        mediaMessage.description = text;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = System.currentTimeMillis() + "text";
        req.message = mediaMessage;
        if (toWhere == WX_SEESSION) {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }

        if (api != null) {
            api.sendReq(req);
        }

    }

    //微信分享商品连接
    public static void WXShareUrl(IWXAPI api, String url, Bitmap bitmap, int toWhere) {
        WXWebpageObject textObject = new WXWebpageObject();
        textObject.webpageUrl = url;

        WXMediaMessage mediaMessage = new WXMediaMessage();
        mediaMessage.mediaObject = textObject;
        mediaMessage.description = "描述";
        mediaMessage.title = "标题";
        mediaMessage.thumbData = Bitmap2Bytes(bitmap, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = System.currentTimeMillis() + "webpage";
        req.message = mediaMessage;
        if (toWhere == WX_SEESSION) {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        if (api != null) {
            api.sendReq(req);
        }
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e("msg...", "执行msg。。。。");
            Bitmap thumbBmp=(Bitmap)msg.obj;
            WXWebpageObject textObject = new WXWebpageObject();
            textObject.webpageUrl = url;
            WXMediaMessage mediaMessage = new WXMediaMessage(textObject);
            mediaMessage.mediaObject = textObject;
            mediaMessage.title = title;
            mediaMessage.description = desc;
            mediaMessage.thumbData = Bitmap2Bytes(thumbBmp, true);
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = "webpage" + System.currentTimeMillis();
            req.message = mediaMessage;
            if (toWhere == WX_SEESSION) {
                req.scene = SendMessageToWX.Req.WXSceneSession;
            } else {
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
            }
            if (api != null) {
                api.sendReq(req);
            }
        }
    };
    String url;
    String title;
    String desc;
    int toWhere;
    IWXAPI api;
    //微信分享网页连接
    public void WXShareUrl(IWXAPI api, String url, final String picUrl, String title, String desc, int toWhere) {
        this.url=url;
        this.title=title;
        this.desc=desc;
        this.toWhere=toWhere;
        this.api=api;

        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap thumbBmp=null;
                try{
                   thumbBmp = Bitmap.createScaledBitmap( BitmapFactory.decodeStream(new URL(picUrl).openStream()), 120, 120, true);
                }catch (Exception e){
                   thumbBmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(BaseApplication.getContext().getResources(), R.mipmap.app_logo), 120, 120, true);
                }
                Message msg = new Message();
                msg.obj=thumbBmp;
                handler.sendMessage(msg);
            }
        }).start();
    }

    public static Bitmap GetLocalOrNetBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new URL(url).openStream(), 1024);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, 1024);
            copy(in, out);
            out.flush();
            byte[] data = dataStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            data = null;
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void copy(InputStream in, OutputStream out)
            throws IOException {
        byte[] b = new byte[1024];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }

    public static void QQshare(Tencent mTencent, Activity activity, String title, String url, String content, String imageUrl) {
        //分享类型
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE,title);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,url);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, content);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,imageUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "乐外卖");
        if (mTencent != null) {
            mTencent.shareToQQ(activity, params, null);
        }
    }

    /**
     * 把Bitmap转Byte
     */
    public static byte[] Bitmap2Bytes(Bitmap bmp, boolean needRecycle) {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
