package com.example.microcampus.ui.home;

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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private View root;

    private float ITEM_HEIGHT;
    private float NORM_DIP;
    private final int DAY = 7;

    private RelativeLayout[] schedule_days;
    private int[] schedule_day_ids = {R.id.schedule_day1, R.id.schedule_day2, R.id.schedule_day3,
            R.id.schedule_day4, R.id.schedule_day5, R.id.schedule_day6, R.id.schedule_day7};
    private int[] schedule_section_ids = {R.id.section1, R.id.section2, R.id.section3, R.id.section4,
            R.id.section5, R.id.section6, R.id.section7, R.id.section8};


    private MutableLiveData<List<Lesson>> mLessons;


    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        mLessons = homeViewModel.getLessons();

        init();

        Lesson lesson = new Lesson("移动应用开发1", "Aide", "6",
                "选修", 12, 1, 1, 2, "教-423(濂溪)");
        View item = inflater.inflate(R.layout.schedule_item, null, false);
        TextView textView = item.findViewById(R.id.lesson);
        int begin = getBeginPositionByTime(lesson.getBeginTime());
        int end = getEndPositionByTime(lesson.getEndTime());
        textView.setText(lesson.toString());

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, end - begin);
        layoutParams.setMargins(0, begin, 0, 0);
        schedule_days[lesson.getDay() - 1].addView(item, layoutParams);
//
//        Log.i("debug", begin + " - " + end);
//
//        RelativeLayout.LayoutParams layoutParams = new Rel
//        // TODO: 2021/5/19 setting position by aligns
//        TextView textView1 = root.findViewById(R.id.day3);
//        System.out.println("_:" + textView1.getId());
//        layoutParams.addRule(RelativeLayout.ABOVE, textView1.getId());
//        item.setLayoutParams(layoutParams);
//        schedule_main.addView(item);

//        homeViewModel.getLessons().observe(getViewLifecycleOwner(), new Observer<List<Lesson>>() {
//            @Override
//            public void onChanged(final List<Lesson> lessons) {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Thread.sleep(2000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//
//                        for (int i = 0; i < lessons.size(); i++) {
//                            View item = inflater.inflate(R.layout.schedule_item, null, false);
//
//                            TextView textView = item.findViewById(R.id.lesson);
//                            textView.setText(lessons.get(i).getLessonName());
//
//                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
//                                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                            // TODO: 2021/5/19 setting position by aligns
//                            layoutParams.addRule(RelativeLayout.BELOW, R.id.day4);
//
//                            schedule_main.addView(item, layoutParams);
//                        }
//                    }
//                });
//            }
//        });

        return root;
    }

    private int getEndPositionByTime(int time) {
        return (int) ((ITEM_HEIGHT + 2 * NORM_DIP) * (time - 1) + ITEM_HEIGHT);
    }

    private int getBeginPositionByTime(int time) {
        return (int) (ITEM_HEIGHT + 2 * NORM_DIP) * (time - 1);
    }



    private void buildLayout() {
//        Resources resources = getResources();
//        String[] days = resources.getStringArray(R.array.days);
//        int preId = R.id.head;
//
//        for (int i = 0; i < 2; i++) {
//            View schedule_day = layoutInflater.inflate(R.layout.schedule_day, null, false);
//
//            TextView content = schedule_day.findViewById(R.id.content);
//            content.setId(dayId[i]);
//            content.setText(days[i]);
//
//
//
//            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
//                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            layoutParams.addRule(RelativeLayout.RIGHT_OF, preId);
//            schedule_day.setLayoutParams(layoutParams);
//            schedule_main.addView(schedule_day);
//
//            System.out.println("[*] ==> " + dayId[i] + " - " + preId);
//            preId = dayId[i];
//            Log.i("debug", "" + content.getId());
//        }

//        days = resources.getStringArray(R.array.cases);
//        for (int i = 0; i < 6; i++) {
//            View schedule_case = layoutInflater.inflate(R.layout.schedule_case, null, false);
//            TextView content = schedule_case.findViewById(R.id.content);
//            content.setId(sectionId[i]);
//            content.setText(days[i]);
//            schedule_row.addView(schedule_case);
////            Log.i("debug", "" + content.getId());
//        }
    }

    private void init() {
        ITEM_HEIGHT = getResources().getDimensionPixelSize(R.dimen.schedule_section_height);
        NORM_DIP = getResources().getDimensionPixelSize(R.dimen.norm_dp);
        schedule_days = new RelativeLayout[DAY];
        for (int i = 0; i < DAY; i++) {
            schedule_days[i] = (RelativeLayout) root.findViewById(schedule_day_ids[i]);
        }
//        schedule_col = (LinearLayout) root.findViewById(R.id.schedule_col);
//        schedule_row = (LinearLayout) root.findViewById(R.id.schedule_row);
//        schedule_main = (RelativeLayout) root.findViewById(R.id.schedule_main);
//        layoutInflater = LayoutInflater.from(root.getContext());

    }

}