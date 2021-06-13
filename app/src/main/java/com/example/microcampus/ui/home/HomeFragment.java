package com.example.microcampus.ui.home;

import android.app.AlertDialog;
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
import com.example.microcampus.demo.util.SharedHander;
import com.example.microcampus.MainViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {
    private float ITEM_HEIGHT;
    private float NORM_DIP;
    private final int ITEM_DAY = 7;
    private final String STARTDATE = "2021-03-01"; // 确保起始时间在周一

    MainViewModel mainViewModel;

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
    private int select_item, now_week, now_day;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        mainViewModel = ViewModelProviders.
                of(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

        initVar();

        // 观察课表信息变化，有变化则更新课表布局
        mainViewModel.getmLessons().observe(getViewLifecycleOwner(), new Observer<List<Lesson>>() {
            @Override
            public void onChanged(final List<Lesson> lessons) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // running something ……
                        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // running end, ans then modify layout

                                // 清空上次的每天课程安排布局
                                for (int i = 0; i < ITEM_DAY; i++) {
                                    schedule_days[i].removeAllViews();
                                }

                                // 处理每个课程布局
                                for (int i = 0; i < lessons.size(); i++) {
                                    // 获取课程，并计算其课表中的位置跨度
                                    Lesson lesson = lessons.get(i);
                                    int begin = getBeginPositionByTime(lesson.getBeginTime());
                                    int end = getEndPositionByTime(lesson.getEndTime());

                                    // 新建课程布局对象，并设置点击监听事件
                                    View item = inflater.inflate(R.layout.schedule_item, null, false);
                                    item.setOnClickListener(new OnClickItem());

                                    // 初始化课程布局信息
                                    TextView name = item.findViewById(R.id.lesson_name);
                                    name.setText(lesson.getLessonName());                           // 课程名称
                                    TextView message = item.findViewById(R.id.lesson_message);
                                    message.setText(lesson.getPlace());                             // 课程地点
                                    TextView index = item.findViewById(R.id.lesson_index);
                                    String number = Integer.toString(i);
                                    index.setText(number);                                          // 课程数组下标（在布局中不显示）

                                    // 把课程加入到对应星期的布局中，并定位好位置
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

        // Spinner 控件的选择监听
        schedule_option.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 对 Calendar 对象计算出学期开始到现在的时间差
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startDate);
                calendar.add(Calendar.DATE, position * ITEM_DAY);

                // 更新星期标题信息
                for (int i = 0; i < ITEM_DAY; i++) {
                    String message = schedule_day_str[i] + "\n" + (calendar.get(Calendar.MONTH) + 1)
                            + "/" + calendar.get(Calendar.DATE);
                    schedule_tips[i].setText(message);
                    calendar.add(Calendar.DATE, 1);
                }

                // 保存选择 Spinner 的下标信息，并加载课程信息
                select_item = position;
                loadingScheduleInformation(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // 设置下拉刷新逻辑事件
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mainViewModel.checkLogin()) {
                    dataService.updataLessonByWeek(select_item + 1);
                    mainViewModel.setLessons(dataService.getShceduleByWeek(select_item));
                    Toast.makeText(getActivity(), "该周课程信息刷新成功!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "请先登录账号、密码！", Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        showingDateLayout();

        return root;
    }

    private void showingDateLayout() {
        final long DAY = 24 * 60 * 60 * 1000;
        Date now = new Date();

        // now_week 从开始周次（第 0 周开始）到现在为第几周
        now_week = (int)((now.getTime() - startDate.getTime()) / DAY) / ITEM_DAY;
        // now_day 现在为星期几（0 代表星期一，以此类推）
        now_day = (now.getDay() + 6) % 7; // 7->0, 1->1
//        Log.i("debug", " " + now_day);
        // 选择 now_week 周为默认显示的周
        schedule_option.setSelection(now_week);
    }

    private void loadingScheduleInformation(int week) {
        // 判断是否合法，先判断是否登录过，在判断账号、密码是否还有效
        if (!mainViewModel.checkLogin()) {
            noLoginState("请先登录账号、密码！");
            return;
        }
        String account = sharedHander.getString("account");
        String password = sharedHander.getString("password");
        if (!dataService.login(account, password)) {
            noLoginState("账号、密码已失效，请重新登录！");
            return;
        }

        // 更新课程信息
        mainViewModel.setLessons(dataService.getShceduleByWeek(week));
        // 当选择周是当前周时，设置高亮条，否则不设
        if (week == now_week) {
            schedule_days[now_day].setBackgroundResource(R.color.schedule_items_backgroud);
        } else {
            schedule_days[now_day].setBackgroundResource(R.color.schedule_background);
        }
    }

    private void noLoginState(String message) {
        for (int i = 0; i < ITEM_DAY; i++) {
            schedule_days[i].removeAllViews();
        }
        mainViewModel.setLessons(new ArrayList<Lesson>());
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void initVar() {
        ITEM_HEIGHT = getResources().getDimensionPixelSize(R.dimen.schedule_section_height);
        NORM_DIP = getResources().getDimensionPixelSize(R.dimen.norm_dp);

        dataService = new DataServiceImpl(getActivity());
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

    @Override
    public void onDestroy() {
        dataService.closeDB();
        super.onDestroy();
    }

    class OnClickItem implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            TextView textView = v.findViewById(R.id.lesson_index);
            int index = Integer.parseInt(textView.getText().toString());

            Lesson lesson = mainViewModel.getLessonByIndex(index);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(lesson.toString());
            builder.setPositiveButton("确定", null);
            final AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}