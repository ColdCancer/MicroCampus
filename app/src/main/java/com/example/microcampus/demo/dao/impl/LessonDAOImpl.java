package com.example.microcampus.demo.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.microcampus.demo.bean.Lesson;
import com.example.microcampus.demo.dao.LessonDAO;
import com.example.microcampus.demo.util.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class LessonDAOImpl implements LessonDAO {
//    private DatabaseHelper databaseHelper;
    private final String TABLE_NAME = "lesson";
    private SQLiteDatabase db;

    public LessonDAOImpl(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getWritableDatabase();
    }

    @Override
    public void close() {
        db.close();
    }

    @Override
    public void deletaLessons() {
        String delete_lessons = "delete from " + TABLE_NAME;
        db.execSQL(delete_lessons);
    }

    @Override
    public List<Lesson> selectLessonsByWeek(int week) {
        List<Lesson> lessons = new ArrayList<>();
        String where = "week=?";
        String[] values = {Integer.toString(week)};
        Cursor cursor = db.query(TABLE_NAME, null, where, values, null, null, null);

        while (cursor.moveToNext()) {
            Lesson lesson = new Lesson();
            lesson.setLessonName(cursor.getString(0));
            lesson.setTeacherName(cursor.getString(1));
            lesson.setLessonCATS(cursor.getFloat(2));
            lesson.setLessonAttibution(cursor.getString(3));
            lesson.setPlace(cursor.getString(4));
            lesson.setDay(cursor.getInt(5));
            lesson.setWeek(cursor.getInt(6));
            lesson.setBeginTime(cursor.getInt(7));
            lesson.setEndTime(cursor.getInt(8));
            lessons.add(lesson);
        }

        return lessons;
    }

    @Override
    public void deletaLessonsByWeek(int week) {
        String where = "week=?";
        String[] values = {Integer.toString(week)};
        db.delete(TABLE_NAME, where, values);
    }

    @Override
    public void insertLessons(List<Lesson> lessons) {
        for (int j = 0; j < lessons.size(); j++) {
            ContentValues contentValues = new ContentValues();
            Lesson lesson = lessons.get(j);
            contentValues.put("lessonName", lesson.getLessonName());
            contentValues.put("teacherName", lesson.getTeacherName());
            contentValues.put("lessonCATS", lesson.getLessonCATS() * 100);
            contentValues.put("lessonAttibution", lesson.getLessonAttibution());
            contentValues.put("place", lesson.getPlace());
            contentValues.put("day", lesson.getDay());
            contentValues.put("week", lesson.getWeek());
            contentValues.put("beginTime", lesson.getBeginTime());
            contentValues.put("endTime", lesson.getEndTime());
            db.insert(TABLE_NAME, null, contentValues);
            contentValues.clear();
        }
    }
}
