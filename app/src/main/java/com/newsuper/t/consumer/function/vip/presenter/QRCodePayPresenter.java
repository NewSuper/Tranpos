package com.newsuper.t.consumer.function.vip.presenter;

import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.newsuper.t.consumer.bean.CheckQRCodePayBean;
import com.newsuper.t.consumer.bean.QRCodePayBean;
import com.newsuper.t.consumer.function.vip.inter.IQRCodePayView;
import com.newsuper.t.consumer.function.vip.request.VipCardRequest;
import com.newsuper.t.consumer.manager.HttpManager;
import com.newsuper.t.consumer.manager.listener.HttpRequestListener;
import com.newsuper.t.consumer.utils.UrlConst;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/12/5 0005.
 */

public class QRCodePayPresenter {
    IQRCodePayView iqrCodePayView;
    public QRCodePayPresenter(IQRCodePayView iqrCodePayView){
        this.iqrCodePayView = iqrCodePayView;
    }
    public void getData(String token,String admin_id){
        HashMap<String,String> map = VipCardRequest.qrCodePayRequest(token, admin_id);
        HttpManager.sendRequest(UrlConst.QRCODE_PAY, map, new HttpRequestListener() {
            @Override

            public void onRequestSuccess(String response) {
                iqrCodePayView.dialogDismiss();
                QRCodePayBean bean = new Gson().fromJson(response,QRCodePayBean.class);
                iqrCodePayView.loadData(bean);

            }

            @Override
            public void onRequestFail(String result, String code) {
                iqrCodePayView.dialogDismiss();
                iqrCodePayView.showToast(result);
                iqrCodePayView.loadFail();
            }

            @Override
            public void onCompleted() {
                iqrCodePayView.dialogDismiss();
            }
        });
    }
    //检测会员是否付款成功接口
    public void checkQRCodePayStatus(String token,String admin_id,String member_id,String weixin_password){
        HashMap<String,String> map = VipCardRequest.checkPayStatusRequest(token, admin_id,member_id,weixin_password);
        HttpManager.sendRequest(UrlConst.QRCODE_PAY_CHECK, map, new HttpRequestListener() {
            @Override

            public void onRequestSuccess(String response) {
                iqrCodePayView.dialogDismiss();
                CheckQRCodePayBean bean = new Gson().fromJson(response,CheckQRCodePayBean.class);
                iqrCodePayView.checkPayStatus(bean);
            }

            @Override
            public void onRequestFail(String result, String code) {
                iqrCodePayView.dialogDismiss();
                iqrCodePayView.showToast(result);
                iqrCodePayView.checkFail();
            }

            @Override
            public void onCompleted() {
                iqrCodePayView.dialogDismiss();
            }
        });
    }

    public Bitmap createQRCode(String str) throws WriterException {
        //生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 300, 300);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        //二维矩阵转为一维像素数组,也就是一直横着排了
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                }
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
