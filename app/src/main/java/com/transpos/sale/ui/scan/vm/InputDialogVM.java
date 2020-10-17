package com.transpos.sale.ui.scan.vm;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class InputDialogVM extends ViewModel {

    private MutableLiveData<String> liveData = new MutableLiveData<>();
    private MutableLiveData<String> specsData = new MutableLiveData<>();
    private MutableLiveData<String> passwordData = new MutableLiveData<>();
    private MutableLiveData<String> cancelDialog = new MutableLiveData<>();

    public MutableLiveData<String> getLiveData() {
        return liveData;
    }

    public MutableLiveData<String> getSpecsData() {
        return specsData;
    }

    public MutableLiveData<String> getPasswordData() {
        return passwordData;
    }

    public MutableLiveData<String> getCancelDialog() {
        return cancelDialog;
    }
}
