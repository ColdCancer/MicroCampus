package com.example.microcampus.demo.service.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.microcampus.demo.bean.Lesson;
import com.example.microcampus.demo.bean.Score;
import com.example.microcampus.demo.dao.LessonDAO;
import com.example.microcampus.demo.dao.ScoreDAO;
import com.example.microcampus.demo.dao.impl.LessonDAOImpl;
import com.example.microcampus.demo.dao.impl.ScoreDAOImpl;
import com.example.microcampus.demo.service.DataService;
import com.example.microcampus.demo.util.DatabaseHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DataServiceImpl implements DataService {
    private LessonDAO lessonDAO;
    private ScoreDAO scoreDAO;

    private final String[] palces = {"教-525(濂溪)", "教-423(濂溪)", "教-135(濂溪)", "实-510 Windows Phone实训室(濂溪)",
                    "实-505 嵌入式MCU开发实训室(濂溪)", "实-612 前端开发实训室(濂溪)"};
    private final String[] teachers = {"张伟", "王芳", "王秀英", "李娜", "王静", "李强", "李军", "王磊",
                    "李敏", "刘洋", "张杰", "王超", "李霞", "王平", "王刚", "李桂英"};
    private final String[] attributes = {"选修课", "必修课"};
    private final String[] names = {"大数据可视化技术", "移动应用开发", "C语言程序设计", "Java课程设计",
                    "电工电子设计性实验", "编译原理", "数据结构", " 数字电路", "操作系统原理", "计算机网络",
                    "数据库原理与应用", "算法设计", "人工智能AI", "Linux系统及应用", "软件工程", " WEB开发技术",
                    "Python网络爬虫", "JavaWeb应用开发"};

    public DataServiceImpl(Context context) {
        lessonDAO = new LessonDAOImpl(context);
        scoreDAO = new ScoreDAOImpl(context);
    }

    @Override
    public boolean login(String username, String password) {
        return "student".equals(username) && "student".equals(password);
    }

    @Override
    public List<Map<String, Object>> getScoresBySemester(int semester) {
        List<Map<String, Object>> data = new ArrayList<>();
        int startYear, endYear, term;

        if (semester == 1 || semester == 2) {
            startYear = 2016;
            endYear = 2017;
        } else if (semester == 3 || semester == 4) {
            startYear = 2018;
            endYear = 2019;
        } else {
            startYear = 2020;
            endYear = 2021;
        }
        term = (semester + 1) % 2 + 1;

        List<Score> scores = scoreDAO.SelectScores(startYear, endYear, term);
        for (int i = 0; i < scores.size(); i++) {
            Score score = scores.get(i);
            Map<String, Object> item = new HashMap<>();
            item.put("name", score.getLessonName());
            item.put("score", score.getLessonScore());
            data.add(item);
        }
        return data;
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
    public void closeDB() {
        lessonDAO.close();
        scoreDAO.close();
    }

    @Override
    public void updataLessonByWeek(int week) {
        lessonDAO.deletaLessonsByWeek(week);
        lessonDAO.insertLessons(randomGeneration(week));
    }

    @Override
    public void deleteAllInformation() {
        lessonDAO.deletaLessons();
        scoreDAO.deleteLessons();
    }

    @Override
    public void updataAllInformation() {
        for (int i = 1; i <= 20; i++) {
            List<Lesson> lessons = randomGeneration(i);
            lessonDAO.insertLessons(lessons);
        }
        for (int startYear = 2016; startYear <= 2020; startYear += 2) {
            int endYear = startYear + 1;
            for (int term = 1; term <= 2; term++) {
                List<Score> scores = randomSemester(startYear, endYear, term);
                scoreDAO.insertScores(scores);
            }
        }
    }

    private List<Score> randomSemester(int startYear, int endYear, int term) {
        List<Score> scores = new ArrayList<>();
        Random random = new Random();
        int index = 0;
        while(true) {
            index += random.nextInt(3) + 1;
            if (names.length <= index) break;;
            String name = names[index];
            float value = random.nextFloat() * 100;
            Score score = new Score(startYear, endYear, term, name, value);
            scores.add(score);
        }
        return scores;
    }

    private List<Lesson> randomGeneration(int week) {
        Random random = new Random();
        int count = 6 + random.nextInt(10) % 5;

        List<Lesson> lessons = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Lesson lesson = new Lesson();
            int beginTime = (random.nextInt(10) % 5) * 2 + 1;
            lesson.setBeginTime(beginTime); // [1, 3, 5, ...
            lesson.setEndTime(beginTime + 1); // [2, 4, 6, ...
            lesson.setDay(random.nextInt(10) % 7); // [0, 1, 2, 3...
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

    @Override
    public List<Lesson> getShceduleByWeek(int week) {
        return lessonDAO.selectLessonsByWeek(week);
    }
}
