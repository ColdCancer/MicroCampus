package com.example.microcampus.demo.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.microcampus.demo.bean.Lesson;
import com.example.microcampus.demo.bean.Score;
import com.example.microcampus.demo.dao.ScoreDAO;
import com.example.microcampus.demo.util.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ScoreDAOImpl implements ScoreDAO {
    private final String TABLE_NAME = "score";
    private SQLiteDatabase db;

    public ScoreDAOImpl(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getWritableDatabase();
    }

    @Override
    public void deleteLessons() {
        String delete_lessons = "delete from " + TABLE_NAME;
        db.execSQL(delete_lessons);
    }

    @Override
    public void close() {
        db.close();
    }

    @Override
    public void insertScores(List<Score> scores) {
        for (int j = 0; j < scores.size(); j++) {
            ContentValues contentValues = new ContentValues();
            Score score = scores.get(j);
            contentValues.put("startyear", score.getStartYear());
            contentValues.put("endYear", score.getEndYear());
            contentValues.put("term", score.getTerm());
            contentValues.put("LessonName", score.getLessonName());
            contentValues.put("LessonScore", score.getLessonScore());
            contentValues.put("lessonName", score.getLessonName());
            db.insert(TABLE_NAME, null, contentValues);
            contentValues.clear();
        }
    }

    @Override
    public List<Score> SelectScores(int startYear, int endYear, int term) {
        List<Score> scores = new ArrayList<>();
        String where = "startYear=? and endYear=? and term=?";
        String[] values = {Integer.toString(startYear), Integer.toString(endYear), Integer.toString(term)};
        Cursor cursor = db.query(TABLE_NAME, null, where, values, null, null, null);

        while (cursor.moveToNext()) {
            Score score = new Score();
            score.setStartYear(cursor.getInt(0));
            score.setEndYear(cursor.getInt(1));
            score.setTerm(cursor.getInt(2));
            score.setLessonName(cursor.getString(3));
            score.setLessonScore(cursor.getInt(4));
            scores.add(score);
        }

        return scores;
    }
}
