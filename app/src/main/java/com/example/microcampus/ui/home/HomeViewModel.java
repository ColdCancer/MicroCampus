package com.example.microcampus.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.microcampus.demo.bean.Lesson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<Lesson>> mLessons = new MutableLiveData<>();

    public MutableLiveData<List<Lesson>> getmLessons() {
        return mLessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.mLessons.setValue(lessons);
    }
}