package com.example.microcampus.demo.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String CREATE_LESSON = "create table lesson(" + "lessonName text," +
            "teacherName text," + "lessonCATS text," + "lessonAttibution text," +
            "month integer," + "day integer," + "week integer," + "xDay integer," +
            "beginTime integer," + "endTime integer," + "place text)";

    public DatabaseHelper(@Nullable Context context, @Nullable String name,
                          @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LESSON);
        Toast.makeText(context, "Create finish", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
