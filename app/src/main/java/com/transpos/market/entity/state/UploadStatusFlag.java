package com.transpos.market.entity.state;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({UploadStatusFlag.NOUPLOAD,UploadStatusFlag.SUCCESS,UploadStatusFlag.FAILED})
@Retention(RetentionPolicy.SOURCE)
public @interface UploadStatusFlag {
    int NOUPLOAD = 0;
    int SUCCESS = 1;
    int FAILED = 2;
}
