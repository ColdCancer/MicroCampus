package com.example.microcampus.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.microcampus.R;
import com.example.microcampus.demo.bean.Lesson;
import com.example.microcampus.demo.service.DataService;
import com.example.microcampus.demo.service.impl.DataServiceImpl;
import com.example.microcampus.demo.util.DatabaseHelper;
import com.example.microcampus.demo.util.SharedHander;
import com.example.microcampus.ui.message.MessageViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {
    private float ITEM_HEIGHT;
    private float NORM_DIP;
    private final int ITEM_DAY = 7;
    private final String STARTDATE = "2021-03-01"; // 确保起始时间在周一

    HomeViewModel homeViewModel;
    private View root;
    private Spinner schedule_option;
    private SwipeRefreshLayout swipeRefreshLayout;

    private RelativeLayout[] schedule_days;
    private int[] schedule_day_ids = {R.id.schedule_day1, R.id.schedule_day2, R.id.schedule_day3,
            R.id.schedule_day4, R.id.schedule_day5, R.id.schedule_day6, R.id.schedule_day7};
    private String[] schedule_day_str = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};

    private TextView[] schedule_tips;
    private int[] schedule_tip_ids = {R.id.schedule_tip1, R.id.schedule_tip2, R.id.schedule_tip3,
            R.id.schedule_tip4, R.id.schedule_tip5, R.id.schedule_tip6, R.id.schedule_tip7};

    private DataService dataService;
    private Date startDate;
    private SharedHander sharedHander;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.
                of(Objects.requireNonNull(getActivity())).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

        initVar();

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
                                for (int i = 0; i < ITEM_DAY; i++) {
                                    schedule_days[i].removeAllViews();
                                }
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
                                    schedule_days[lesson.getDay()].addView(item, layoutParams);
                                }
                            }
                        });
                    }
                }).start();
            }
        });

        schedule_option.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Calendar calendar = Calendar.getInstance();

                calendar.setTime(startDate);
                calendar.add(Calendar.DATE, position * ITEM_DAY);

                for (int i = 0; i < ITEM_DAY; i++) {
                    String message = schedule_day_str[i] + "\n" + (calendar.get(Calendar.MONTH) + 1)
                            + "/" + calendar.get(Calendar.DATE);
                    schedule_tips[i].setText(message);
                    calendar.add(Calendar.DATE, 1);
                }

                loadingScheduleInformation(position + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(), "Refresh", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        showingDateLayout();

        return root;
    }

    private void showingDateLayout() {
        final long DAY = 24 * 60 * 60 * 1000;
        Date now = new Date();

        int weekth = (int)((now.getTime() - startDate.getTime()) / DAY) / ITEM_DAY;
        schedule_option.setSelection(weekth);
    }

    private void loadingScheduleInformation(int week) {
        MessageViewModel messageViewModel = ViewModelProviders.
                of(Objects.requireNonNull(getActivity())).get(MessageViewModel.class);

        if (!messageViewModel.checkLogin()) {
            noLoginState("请先登录账号、密码！");
            return;
        }

        String account = sharedHander.getString("account");
        String password = sharedHander.getString("password");
        if (!dataService.login(account, password)) {
            noLoginState("账号、密码已失效，请重新登录！");
            return;
        }

        homeViewModel.setLessons(dataService.getShceduleByWeek(week));
    }

    private void noLoginState(String message) {
        for (int i = 0; i < ITEM_DAY; i++) {
            schedule_days[i].removeAllViews();
        }
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void initVar() {
        ITEM_HEIGHT = getResources().getDimensionPixelSize(R.dimen.schedule_section_height);
        NORM_DIP = getResources().getDimensionPixelSize(R.dimen.norm_dp);

        dataService = new DataServiceImpl();
        schedule_option = root.findViewById(R.id.schedule_option);
        sharedHander = new SharedHander(getActivity(), "student");
        swipeRefreshLayout = root.findViewById(R.id.swipeRefresh);

        try {
            startDate = new SimpleDateFormat("yyyy-MM-dd").parse(STARTDATE);
        } catch (ParseException e) {
            Log.i("debug", "学期开始周期不合法！");
        }

        schedule_days = new RelativeLayout[ITEM_DAY];
        for (int i = 0; i < ITEM_DAY; i++) {
            schedule_days[i] = root.findViewById(schedule_day_ids[i]);
        }
        schedule_tips = new TextView[ITEM_DAY];
        for (int i = 0; i < ITEM_DAY; i++) {
            schedule_tips[i] = root.findViewById(schedule_tip_ids[i]);
        }
    }

    private int getEndPositionByTime(int time) {
        return (int) ((ITEM_HEIGHT + 2 * NORM_DIP) * (time - 1) + ITEM_HEIGHT);
    }

    private int getBeginPositionByTime(int time) {
        return (int) (ITEM_HEIGHT + 2 * NORM_DIP) * (time - 1);
    }

}