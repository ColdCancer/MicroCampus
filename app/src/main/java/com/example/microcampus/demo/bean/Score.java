package com.example.microcampus.demo.bean;

public class Score {
    private int startYear;
    private int endYear;
    private int term;
    private String lessonName;
    private float lessonScore;

    public Score() { }

    public Score(int startYear, int endYear, int term, String lessonName, float lessonScore) {
        this.startYear = startYear;
        this.endYear = endYear;
        this.term = term;
        this.lessonName = lessonName;
        this.lessonScore = lessonScore;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public float getLessonScore() {
        return lessonScore;
    }

    public void setLessonScore(float lessonScore) {
        this.lessonScore = lessonScore;
    }
}
