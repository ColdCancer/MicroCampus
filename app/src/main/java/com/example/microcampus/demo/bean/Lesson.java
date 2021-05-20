package com.example.microcampus.demo.bean;

import java.util.Date;

public class Lesson {
    private String lessonName;
    private String teacherName;
    private String lessonCATS;
    private String lessonAttibution;
    private int week;
    private int day;
    private int beginTime;
    private int endTime;
    private String place;

    public Lesson(String lessonName, String teacherName, String lessonCATS, String lessonAttibution, int week, int day, int beginTime, int endTime, String place) {
        this.lessonName = lessonName;
        this.teacherName = teacherName;
        this.lessonCATS = lessonCATS;
        this.lessonAttibution = lessonAttibution;
        this.week = week;
        this.day = day;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.place = place;
    }

    public Lesson() { }

    @Override
    public String toString() {
        return "Lesson{" +
                "lessonName='" + lessonName + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", lessonCATS='" + lessonCATS + '\'' +
                ", lessonAttibution='" + lessonAttibution + '\'' +
                ", week=" + week +
                ", day=" + day +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", place='" + place + '\'' +
                '}';
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

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
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
