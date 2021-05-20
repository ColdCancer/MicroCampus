package com.example.microcampus.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.microcampus.demo.bean.Lesson;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<Lesson>> mLessons;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mLessons = new MutableLiveData<>();
    }

    public void setmLessons(MutableLiveData<List<Lesson>> mLessons) {
        this.mLessons = mLessons;
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<List<Lesson>> getLessons() {
        return mLessons;
    }
}