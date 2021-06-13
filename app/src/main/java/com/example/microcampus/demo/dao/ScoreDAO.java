package com.example.microcampus.demo.dao;

import com.example.microcampus.demo.bean.Score;

import java.util.List;

public interface ScoreDAO {
    List<Score> SelectScores(int startYear, int endYear, int term);
    void insertScores(List<Score> scores);
    void close();
    void deleteLessons();
}
