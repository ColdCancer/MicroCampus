package com.example.microcampus;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.microcampus.demo.bean.Lesson;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainViewModel extends ViewModel {
    private MutableLiveData<Boolean> mLoginFlag;
    private MutableLiveData<Map<String, String>> mBaseInformation;
    private MutableLiveData<List<Lesson>> mLessons;

    public MainViewModel() {
        mLoginFlag = new MutableLiveData<>(false);
        mBaseInformation = new MutableLiveData<>();
        mLessons = new MutableLiveData<>();
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

    public MutableLiveData<List<Lesson>> getmLessons() {
        return mLessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.mLessons.setValue(lessons);
    }

    public Lesson getLessonByIndex(int index) {
        return Objects.requireNonNull(mLessons.getValue()).get(index);
    }
}
