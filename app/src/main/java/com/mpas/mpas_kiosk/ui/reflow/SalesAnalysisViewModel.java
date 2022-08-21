package com.mpas.mpas_kiosk.ui.reflow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SalesAnalysisViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SalesAnalysisViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is reflow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}