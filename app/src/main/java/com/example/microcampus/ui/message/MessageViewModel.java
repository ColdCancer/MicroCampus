package com.example.microcampus.ui.message;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MessageViewModel extends ViewModel {

    private MutableLiveData<Boolean> mLoginFlag;


    public MessageViewModel() {
        mLoginFlag = new MutableLiveData<>(false);
    }

    public LiveData<Boolean> getLoginFlag() {
        return mLoginFlag;
    }

    public void setLoginFlag(boolean flag) {
        mLoginFlag.setValue(flag);
    }

    public boolean checkLogin() {
        return mLoginFlag.getValue();
    }
}