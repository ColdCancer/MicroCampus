package com.example.microcampus.demo.service.impl;

import android.util.Log;

import com.example.microcampus.demo.bean.Lesson;
import com.example.microcampus.demo.service.DataService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DataServiceImpl implements DataService {
    private final String[] palces = {"教-525(濂溪)", "教-423(濂溪)", "实-510 Windows Phone实训室(濂溪)"};
    private final String[] teachers = {"吕嘉言", "埃迪", "住户平", "立业", "曹辉", "玉峰"};
    private final String[] attributes = {"选修课", "必修课"};
    private final String[] names = {"大数据可视化技术", "移动应用开发"};

    @Override
    public boolean login(String username, String password) {
        return "student".equals(username) && "student".equals(password);
    }

    @Override
    public Map<String, String> getBaseInformation(String username) {
        Map<String, String> result = new HashMap<>();
        result.put("account", "201820800124");
        result.put("name", "汪星人");
        result.put("college", "信息工程学院");
        return result;
    }

    @Override
    public List<Lesson> getShceduleByWeek(int week) {
        Random random = new Random();
        int count = 6 + random.nextInt(10) % 5;

        List<Lesson> lessons = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Lesson lesson = new Lesson();
            int beginTime = 1 + (random.nextInt() % 5) * 2;
            lesson.setBeginTime(beginTime);
            lesson.setEndTime(beginTime + 1);
            lesson.setDay(random.nextInt(10) % 7);
            lesson.setLessonAttibution(attributes[random.nextInt(10) % attributes.length]);
            lesson.setWeek(week);
            lesson.setPlace(palces[random.nextInt(10) % palces.length]);
            lesson.setLessonName(names[random.nextInt(10) % names.length]);
            lesson.setTeacherName(teachers[random.nextInt(10) % teachers.length]);
            lesson.setLessonCATS(random.nextFloat());
            lessons.add(lesson);
        }
        return lessons;
    }
}
