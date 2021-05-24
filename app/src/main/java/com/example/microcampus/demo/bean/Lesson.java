package com.example.microcampus.demo.bean;

import java.util.Date;

public class Lesson {
    private String lessonName;
    private String teacherName;
    private String lessonCATS;
    private String lessonAttibution;
    private int month;
    private int day;
    private int week;
    private int xDay;
    private int beginTime;
    private int endTime;
    private String place;

    public Lesson() { }

    public Lesson(String lessonName, String teacherName, String lessonCATS, String lessonAttibution,
                  int month, int day, int week, int xDay, int beginTime, int endTime, String place) {
        this.lessonName = lessonName;
        this.teacherName = teacherName;
        this.lessonCATS = lessonCATS;
        this.lessonAttibution = lessonAttibution;
        this.month = month;
        this.day = day;
        this.week = week;
        this.xDay = xDay;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.place = place;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getLessonCATS() {
        return lessonCATS;
    }

    public void setLessonCATS(String lessonCATS) {
        this.lessonCATS = lessonCATS;
    }

    public String getLessonAttibution() {
        return lessonAttibution;
    }

    public void setLessonAttibution(String lessonAttibution) {
        this.lessonAttibution = lessonAttibution;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getxDay() {
        return xDay;
    }

    public void setxDay(int xDay) {
        this.xDay = xDay;
    }

    public int getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(int beginTime) {
        this.beginTime = beginTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
