package com.example.microcampus.ui.home;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
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

import com.example.microcampus.R;
import com.example.microcampus.demo.bean.Lesson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private View root;
    private LinearLayout schedule_col, schedule_row;
    private RelativeLayout schedule_main;
    private LayoutInflater layoutInflater;
    private MutableLiveData<List<Lesson>> mLessons;
    private int[] dayId = {R.id.day1, R.id.day2, R.id.day3, R.id.day4, R.id.day5, R.id.day6, R.id.day7};
    private int[] caseId = {R.id.case1, R.id.case2, R.id.case3, R.id.case4, R.id.case5, R.id.case6};


    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        mLessons = homeViewModel.getLessons();

        init();
        buildLayout();

        homeViewModel.getLessons().observe(getViewLifecycleOwner(), new Observer<List<Lesson>>() {
            @Override
            public void onChanged(final List<Lesson> lessons) {
                new Thread() {
                    @Override
                    public void run() {
                        for (int i = 0; i < lessons.size(); i++) {
                            View item = inflater.inflate(R.layout.schedule_item, null, false);
                            TextView textView = item.findViewById(R.id.lesson);
                            textView.setText(lessons.get(i).getLessonName());
//                            textView.setLeftTopRightBottom(100, 100, 100, 100);
                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            // TODO: 2021/5/19 setting position by aligns
                            schedule_main.addView(item, layoutParams);
                        }
                    }
                }.start();
            }
        });

        getLessonsInformation();
        return root;
    }

    private void getLessonsInformation() {
        List<Lesson> lessons = new ArrayList<>();
//        课程学分：6<br></p>课程属性：选修<br/>课程名称：移动应用开发<br/>上课时间：第12周 星期一 [01-02]节<br/>上课地点：教-423(濂溪)
        lessons.add(new Lesson("移动应用开发1", "Aide", "6",
                "选修", "第12周 星期一 [01-02]节", "教-423(濂溪)"));
        lessons.add(new Lesson("移动应用开发2", "Aide", "6",
                "选修", "第12周 星期三 [03-04]节", "教-423(濂溪)"));
        lessons.add(new Lesson("移动应用开发3", "Aide", "6",
                "选修", "第12周 星期五 [05-06]节", "教-423(濂溪)"));

        mLessons.setValue(lessons);
    }

    private void buildLayout() {
        Resources resources = getResources();
        String[] days = resources.getStringArray(R.array.days);

        for (int i = 0; i < 7; i++) {
            View schedule_day = layoutInflater.inflate(R.layout.schedule_day, null, false);
            TextView content = schedule_day.findViewById(R.id.content);
            content.setId(dayId[i]);
            content.setText(days[i]);
            schedule_col.addView(schedule_day);
        }

        days = resources.getStringArray(R.array.cases);
        for (int i = 0; i < 6; i++) {
            View schedule_case = layoutInflater.inflate(R.layout.schedule_case, null, false);
            TextView content = schedule_case.findViewById(R.id.content);
            content.setId(caseId[i]);
            content.setText(days[i]);
            schedule_row.addView(schedule_case);
        }
    }

    private void init() {
        schedule_col = (LinearLayout) root.findViewById(R.id.schedule_col);
        schedule_row = (LinearLayout) root.findViewById(R.id.schedule_row);
        schedule_main = (RelativeLayout) root.findViewById(R.id.schedule_main);
        layoutInflater = LayoutInflater.from(root.getContext());

    }

}