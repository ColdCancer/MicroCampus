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
import com.example.microcampus.demo.util.DatabaseHelper;

import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {
    private float ITEM_HEIGHT;
    private float NORM_DIP;
    private final int DAY = 7;

    private View root;

    private RelativeLayout[] schedule_days;
    private int[] schedule_day_ids = {R.id.schedule_day1, R.id.schedule_day2, R.id.schedule_day3,
            R.id.schedule_day4, R.id.schedule_day5, R.id.schedule_day6, R.id.schedule_day7};

    private TextView[] schedule_tips;
    private int[] schedule_tip_ids = {R.id.schedule_tip1, R.id.schedule_tip2, R.id.schedule_tip3,
            R.id.schedule_tip4, R.id.schedule_tip5, R.id.schedule_tip6, R.id.schedule_tip7};

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

        init();

        final DatabaseHelper databaseHelper = new DatabaseHelper(getActivity(), "microcampus", null, 1);

        for (int i = 0; i < DAY; i++) {
            String message = schedule_tips[i].getText() + "\n" + "5/" + (10 + i);
            schedule_tips[i].setText(message);
        }

        homeViewModel.getmLessons().observe(getViewLifecycleOwner(), new Observer<List<Lesson>>() {
            @Override
            public void onChanged(final List<Lesson> lessons) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                databaseHelper.getReadableDatabase();
                                for (int i = 0; i < lessons.size(); i++) {
                                    Lesson lesson = lessons.get(i);
                                    int begin = getBeginPositionByTime(lesson.getBeginTime());
                                    int end = getEndPositionByTime(lesson.getEndTime());

                                    View item = inflater.inflate(R.layout.schedule_item, null, false);
                                    TextView name = item.findViewById(R.id.lesson_name);
                                    name.setText(lesson.getLessonName());
                                    TextView message = item.findViewById(R.id.lesson_message);
                                    message.setText(lesson.getPlace());

                                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT, end - begin);
                                    layoutParams.topMargin = begin;
                                    schedule_days[lesson.getxDay() - 1].addView(item, layoutParams);
                                }
                            }
                        });
                    }
                }).start();
            }
        });

        homeViewModel.updateLessons(getActivity());

        return root;
    }


    private void init() {
        ITEM_HEIGHT = getResources().getDimensionPixelSize(R.dimen.schedule_section_height);
        NORM_DIP = getResources().getDimensionPixelSize(R.dimen.norm_dp);

        schedule_days = new RelativeLayout[DAY];
        for (int i = 0; i < DAY; i++) {
            schedule_days[i] = (RelativeLayout) root.findViewById(schedule_day_ids[i]);
        }

        schedule_tips = new TextView[DAY];
        for (int i= 0; i < DAY; i++) {
            schedule_tips[i] = (TextView) root.findViewById(schedule_tip_ids[i]);
        }

    }

    private int getEndPositionByTime(int time) {
        return (int) ((ITEM_HEIGHT + 2 * NORM_DIP) * (time - 1) + ITEM_HEIGHT);
    }

    private int getBeginPositionByTime(int time) {
        return (int) (ITEM_HEIGHT + 2 * NORM_DIP) * (time - 1);
    }

}