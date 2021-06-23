package com.newchao.imlib.message;

import android.os.Parcelable;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;

public abstract class MessageContent implements Parcelable,Cloneable {

    @NonNull
    @NotNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
