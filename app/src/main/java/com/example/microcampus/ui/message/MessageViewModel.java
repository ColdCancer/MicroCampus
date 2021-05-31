package com.example.microcampus.ui.message;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.microcampus.demo.util.SharedHander;

import java.util.Map;

public class MessageViewModel extends ViewModel {
    private MutableLiveData<Boolean> mLoginFlag;
    private MutableLiveData<Map<String, String>> mBaseInformation;

    public MessageViewModel() {
        mLoginFlag = new MutableLiveData<>(false);
        mBaseInformation = new MutableLiveData<>();
    }

    public Map<String, String> getBaseInformation() {
        return mBaseInformation.getValue();
    }

    public void setBaseInformation(Map<String, String> baseInformation) {
        this.mBaseInformation.setValue(baseInformation);
    }

    public boolean checkLogin() {
        return mLoginFlag.getValue();
    }

    public void setLoginFlag(boolean flag) {
        mLoginFlag.setValue(flag);
    }
}