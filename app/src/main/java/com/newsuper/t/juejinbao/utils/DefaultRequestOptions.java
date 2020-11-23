package com.newsuper.t.juejinbao.utils;

import android.support.annotation.NonNull;

import com.bumptech.glide.request.RequestOptions;
import com.newsuper.t.R;

public class DefaultRequestOptions extends RequestOptions {
    @NonNull
    @Override
    public RequestOptions placeholder(int resourceId) {
        return super.placeholder(R.mipmap.default_img);
    }


}
