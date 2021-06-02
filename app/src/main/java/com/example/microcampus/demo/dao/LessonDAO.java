package com.example.microcampus.demo.dao;

import com.example.microcampus.demo.bean.Lesson;

import java.util.List;

public interface LessonDAO {
    void insertLessons(List<Lesson> lessons);
    void close();
    void deletaLessons();
    List<Lesson> selectLessonsByWeek(int week);
}
