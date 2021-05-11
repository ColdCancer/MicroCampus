package com.example.microcampus.demo.bean;

import java.util.Date;

public class Lesson {
    private String lessonName;
    private String teacherName;
    private String lessonCATS;
    private String lessonAttibution;
    private Date lessonDate;
    private String place;
    private int day;

    public Lesson(String lessonName, String teacherName, String lessonCATS, String lessonAttibution, Date lessonDate, String place) {
        this.lessonName = lessonName;
        this.teacherName = teacherName;
        this.lessonCATS = lessonCATS;
        this.lessonAttibution = lessonAttibution;
        this.lessonDate = lessonDate;
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

    public Date getLessonDate() {
        return lessonDate;
    }

    public void setLessonDate(Date lessonDate) {
        this.lessonDate = lessonDate;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
