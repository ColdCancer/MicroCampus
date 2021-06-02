package com.example.microcampus.demo.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "microcampus.db";
    private static final int DATABASE_VERSION = 1;

    private Context context;
    private static final String CREATE_LESSON = "create table lesson(" + "lessonName text," +
            "teacherName text," + "lessonCATS real," + "lessonAttibution text," + "place text," +
            "day integer," + "week integer," + "beginTime integer," + "endTime integer)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LESSON);
        Toast.makeText(context, "欢迎使用，所有功能已准备好！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
