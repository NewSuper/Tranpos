package com.newsuper.t.juejinbao.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TResult;

public class SelectPictureActivity extends TakePhotoActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TakePhoto takePhoto = getTakePhoto();

        takePhoto.onCreate(savedInstanceState);
        takePhoto.onPickFromGallery();
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    public static void intentMe(Context context){
        Intent intent = new Intent(context , SelectPictureActivity.class);
        context.startActivity(intent);
    }
}
