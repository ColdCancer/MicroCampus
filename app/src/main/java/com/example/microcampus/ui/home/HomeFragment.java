package com.example.microcampus.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.microcampus.MainActivity;
import com.example.microcampus.R;
import com.example.microcampus.demo.bean.Lesson;
import com.example.microcampus.demo.dao.ScheduleDAO;
import com.example.microcampus.demo.dao.impl.ScheduleDAOImpl;
import com.example.microcampus.spider.Spider;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {
    private float ITEM_HEIGHT;
    private float NORM_DIP;
    private final int DAY = 7;

    private HomeViewModel homeViewModel;
    private View root;

    private RelativeLayout[] schedule_days;
    private int[] schedule_day_ids = {R.id.schedule_day1, R.id.schedule_day2, R.id.schedule_day3,
            R.id.schedule_day4, R.id.schedule_day5, R.id.schedule_day6, R.id.schedule_day7};

//    private ScheduleDAO scheduleDAO;

//    private int[] schedule_section_ids = {R.id.section1, R.id.section2, R.id.section3, R.id.section4,
//            R.id.section5, R.id.section6, R.id.section7, R.id.section8};

//    private MutableLiveData<List<Lesson>> mLessons;
    private MutableLiveData<Integer> mCount;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
//        mLessons = homeViewModel.getLessons();

        init();

        homeViewModel.StartToAddOne(); // The first launch to update lessons
        if (Objects.equals(homeViewModel.getmCount().getValue(), 1)) {
            homeViewModel.updateLessons(Objects.requireNonNull(getActivity()));
        }

        homeViewModel.getmLessons().observe(getViewLifecycleOwner(), new Observer<List<Lesson>>() {
            @Override
            public void onChanged(final List<Lesson> lessons) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < lessons.size(); i++) {
                                    Lesson lesson = lessons.get(i);
                                    int begin = getBeginPositionByTime(lesson.getBeginTime());
                                    int end = getEndPositionByTime(lesson.getEndTime());

                                    View item = inflater.inflate(R.layout.schedule_item, null, false);
                                    TextView textView = item.findViewById(R.id.lesson);
                                    textView.setText(lesson.toString());

                                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT, end - begin);
                                    layoutParams.topMargin = begin;
                                    schedule_days[lesson.getDay() - 1].addView(item, layoutParams);
                                }
                            }
                        });
                    }
                }).start();
            }
        });

        return root;
    }


    private void init() {
//        scheduleDAO = new ScheduleDAOImpl();
        ITEM_HEIGHT = getResources().getDimensionPixelSize(R.dimen.schedule_section_height);
        NORM_DIP = getResources().getDimensionPixelSize(R.dimen.norm_dp);
        schedule_days = new RelativeLayout[DAY];
        for (int i = 0; i < DAY; i++) {
            schedule_days[i] = (RelativeLayout) root.findViewById(schedule_day_ids[i]);
        }

    }

    private int getEndPositionByTime(int time) {
        return (int) ((ITEM_HEIGHT + 2 * NORM_DIP) * (time - 1) + ITEM_HEIGHT);
    }

    private int getBeginPositionByTime(int time) {
        return (int) (ITEM_HEIGHT + 2 * NORM_DIP) * (time - 1);
    }

}