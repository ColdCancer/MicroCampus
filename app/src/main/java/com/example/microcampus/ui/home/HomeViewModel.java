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

    public void setmLessons(MutableLiveData<List<Lesson>> mLessons) {
        this.mLessons = mLessons;
    }

    public void updateLessons(FragmentActivity activity) {
        if (activity == null) return;
//        SharedPreferences sharedPreferences = activity.getSharedPreferences("userInform", Context.MODE_PRIVATE);
//        String account = sharedPreferences.getString("account", "");
//        String password = sharedPreferences.getString("password", "");
//        Spider spider = new Spider();
//        // TODO: 2021/5/20 Spider Thread for geting lesson information
//        spider.login(account, password);

        List<Lesson> lessons = new ArrayList<>();
        lessons.add(new Lesson("移动应用开发1", "Aide", "6",
                "选修", 5, 12, 12, 1, 5, 6, "教-423(濂溪)"));
        lessons.add(new Lesson("移动应用开发2", "Aide", "6",
                "选修", 5, 13, 12, 3, 3, 4, "教-423(濂溪)"));
        lessons.add(new Lesson("移动应用开发3", "Aide", "6",
                "选修", 5, 14, 12, 5, 1, 2, "教-423(濂溪)"));
        mLessons.setValue(lessons);

//        mLessons.setValue(spider.getOneWeekLessonsByDate(new Date()));
    }

    public MutableLiveData<List<Lesson>> getmLessons() {
        return mLessons;
    }
}