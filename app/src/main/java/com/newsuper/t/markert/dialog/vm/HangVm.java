package com.newsuper.t.markert.dialog.vm;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class HangVm extends ViewModel {
    private MutableLiveData<String> hangOption = new MutableLiveData<>();
    private MutableLiveData<String> deleteOption = new MutableLiveData<>();

    public MutableLiveData<String> getHangOption() {
        return hangOption;
    }

    public MutableLiveData<String> getDeleteOption() {
        return deleteOption;
    }
}
