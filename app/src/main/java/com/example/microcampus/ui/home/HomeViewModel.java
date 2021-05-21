package com.example.microcampus.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.microcampus.demo.bean.Lesson;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<Lesson>> mLessons;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mLessons = new MutableLiveData<>();
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(new Lesson("移动应用开发1", "Aide", "6",
                "选修", 12, 1, 1, 2, "教-423(濂溪)"));
        lessons.add(new Lesson("移动应用开发2", "Aide", "6",
                "选修", 12, 3, 3, 4, "教-423(濂溪)"));
        lessons.add(new Lesson("移动应用开发3", "Aide", "6",
                "选修", 12, 5, 5, 6, "教-423(濂溪)"));
        mLessons.setValue(lessons);
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